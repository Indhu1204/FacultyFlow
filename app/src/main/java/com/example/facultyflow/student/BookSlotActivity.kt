package com.example.facultyflow.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.facultyflow.R
import com.example.facultyflow.databinding.ActivityBookSlotBinding
import com.example.facultyflow.databinding.ItemDateChipBinding
import com.example.facultyflow.student.adapters.TimeSlotAdapter
import com.example.facultyflow.student.models.TimeSlot
import com.example.facultyflow.utils.PreferencesManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class BookSlotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookSlotBinding
    private lateinit var timeSlotAdapter: TimeSlotAdapter

    private var selectedTimeSlot: TimeSlot? = null
    private var selectedDate: String = ""

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var preferencesManager: PreferencesManager

    private var facultyId: String = ""
    private var facultyName: String = ""
    private var facultyDesignation: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookSlotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        preferencesManager = PreferencesManager(this)

        facultyId = intent.getStringExtra("faculty_id") ?: ""
        facultyName = intent.getStringExtra("faculty_name") ?: "Dr. John Smith"
        facultyDesignation = intent.getStringExtra("faculty_designation") ?: "Professor"

        setupUI()
        setupTimeSlots()      // MUST come before date chips
        setupDateChips()
        setupClickListeners()
        loadFacultyData()
    }

    private fun setupUI() {
        binding.ivBack.setOnClickListener { finish() }
    }

    // ================= DATE CHIPS =================
    private fun setupDateChips() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEE", Locale.getDefault())
        val dayFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())

        for (i in 0..6) {
            val chip = ItemDateChipBinding.inflate(LayoutInflater.from(this))

            val date = calendar.time
            val formattedDate = dayFormat.format(date)

            chip.tvDay.text = dateFormat.format(date).uppercase()
            chip.tvDate.text = SimpleDateFormat("d", Locale.getDefault()).format(date)

            chip.root.setOnClickListener {
                selectDate(chip, formattedDate)
            }

            if (i == 0) {
                selectDate(chip, formattedDate)
            }

            binding.dateContainer.addView(chip.root)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    private fun selectDate(chip: ItemDateChipBinding, date: String) {

        // reset all chips
        for (i in 0 until binding.dateContainer.childCount) {
            val child = binding.dateContainer.getChildAt(i)

            if (child is androidx.cardview.widget.CardView) {
                child.setCardBackgroundColor(getColor(R.color.ios_gray6))

                val day = child.findViewById<android.widget.TextView>(R.id.tvDay)
                val d = child.findViewById<android.widget.TextView>(R.id.tvDate)

                day.setTextColor(getColor(R.color.apple_secondary_label))
                d.setTextColor(getColor(R.color.apple_label))
            }
        }

        // highlight selected
        chip.root.setCardBackgroundColor(getColor(R.color.ios_blue))
        chip.tvDay.setTextColor(getColor(R.color.white))
        chip.tvDate.setTextColor(getColor(R.color.white))

        selectedDate = date
        loadTimeSlotsForDate()
    }

    // ================= TIME SLOTS =================
    private fun setupTimeSlots() {

        timeSlotAdapter = TimeSlotAdapter { slot ->
            selectTimeSlot(slot)
        }

        binding.rvTimeSlots.apply {
            layoutManager = GridLayoutManager(this@BookSlotActivity, 3)
            adapter = timeSlotAdapter
        }

        loadTimeSlotsForDate()
    }

    // 🔥 MAIN AI + FIREBASE FILTER
    private fun loadTimeSlotsForDate() {

        if (facultyId.isEmpty()) return

        db.collection("timetables")
            .document(facultyId)
            .get()
            .addOnSuccessListener { doc ->

                val busySlots = doc.get("slots") as? List<*> ?: emptyList<Any>()
                val busySlotsString = busySlots.mapNotNull { it as? String }

                val allSlots = generateTimeSlots()

                val timeSlots = allSlots.map { slot ->
                TimeSlot(
                    time = slot,
                    duration = "30 min",
                    isAvailable = !busySlotsString.contains(slot)
                )
            }

                timeSlotAdapter.submitList(timeSlots)

                if (timeSlots.isEmpty()) {
                    Toast.makeText(
                        this,
                        "No slots available (faculty busy)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load timetable", Toast.LENGTH_SHORT).show()
            }
    }

    // 🔥 DYNAMIC SLOT GENERATOR (9AM → 5PM)
    private fun generateTimeSlots(): List<String> {

        val slots = mutableListOf<String>()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 9)
        calendar.set(Calendar.MINUTE, 0)

        val end = Calendar.getInstance()
        end.set(Calendar.HOUR_OF_DAY, 17)
        end.set(Calendar.MINUTE, 0)

        val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())

        while (calendar.before(end)) {
            slots.add(formatter.format(calendar.time))
            calendar.add(Calendar.MINUTE, 30)
        }

        return slots
    }

    private fun selectTimeSlot(slot: TimeSlot) {
        selectedTimeSlot = slot

        binding.tvSelectedTime.visibility = View.VISIBLE
        binding.tvSelectedTime.text = "Slot selected: ${slot.time}"

        binding.btnConfirmBooking.isEnabled = true
    }

    private fun setupClickListeners() {
        binding.btnConfirmBooking.setOnClickListener {
            confirmBooking()
        }
    }

    private fun loadFacultyData() {
        binding.tvFacultyName.text = facultyName
        binding.tvFacultyDesignation.text = facultyDesignation
    }

    // ================= BOOKING =================
    private fun confirmBooking() {

        val slot = selectedTimeSlot ?: return
        val studentId = auth.currentUser?.uid ?: return
        val studentName = preferencesManager.userName
        val note = binding.etNote.text.toString().trim()

        binding.btnConfirmBooking.isEnabled = false
        binding.btnConfirmBooking.text = "Sending Request..."

        val bookingData = hashMapOf(
            "studentId" to studentId,
            "studentName" to studentName,
            "facultyId" to facultyId,
            "facultyName" to facultyName,
            "facultyDesignation" to facultyDesignation,
            "date" to selectedDate,
            "timeSlot" to slot.time,
            "status" to "pending",
            "studentNote" to note,
            "timestamp" to com.google.firebase.Timestamp.now()
        )

        db.collection("bookings")
            .add(bookingData)
            .addOnSuccessListener {
                showSuccessAnimation()
            }
            .addOnFailureListener { e ->
                binding.btnConfirmBooking.isEnabled = true
                binding.btnConfirmBooking.text = "Confirm Booking"

                Toast.makeText(
                    this,
                    "Failed: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun showSuccessAnimation() {
        binding.layoutSuccess.successOverlay.visibility = View.VISIBLE

        val anim = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        binding.layoutSuccess.successCard.startAnimation(anim)

        binding.root.postDelayed({
            finish()
        }, 2000)
    }
}