package com.example.facultyflow.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
<<<<<<< HEAD
import com.example.facultyflow.R
=======
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
import com.example.facultyflow.databinding.ActivityBookSlotBinding
import com.example.facultyflow.databinding.ItemDateChipBinding
import com.example.facultyflow.student.adapters.TimeSlotAdapter
import com.example.facultyflow.student.models.TimeSlot
<<<<<<< HEAD
import com.example.facultyflow.utils.PreferencesManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
=======
import java.text.SimpleDateFormat
import java.util.*
import com.example.facultyflow.R
import com.example.facultyflow.utils.PreferencesManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59

class BookSlotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookSlotBinding
    private lateinit var timeSlotAdapter: TimeSlotAdapter
<<<<<<< HEAD

    private var selectedTimeSlot: TimeSlot? = null
    private var selectedDate: String = ""

=======
    private var selectedTimeSlot: TimeSlot? = null
    private var selectedDate: String = ""
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var preferencesManager: PreferencesManager

    private var facultyId: String = ""
    private var facultyName: String = ""
    private var facultyDesignation: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< HEAD

=======
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        binding = ActivityBookSlotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        preferencesManager = PreferencesManager(this)

        facultyId = intent.getStringExtra("faculty_id") ?: ""
<<<<<<< HEAD
=======
        // In a real app, we'd fetch faculty details from DB if only ID is passed
        // or pass all info via intent for speed.
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        facultyName = intent.getStringExtra("faculty_name") ?: "Dr. John Smith"
        facultyDesignation = intent.getStringExtra("faculty_designation") ?: "Professor"

        setupUI()
<<<<<<< HEAD
        setupTimeSlots()      // MUST come before date chips
        setupDateChips()
=======
        setupDateChips()
        setupTimeSlots()
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        setupClickListeners()
        loadFacultyData()
    }

    private fun setupUI() {
<<<<<<< HEAD
        binding.ivBack.setOnClickListener { finish() }
    }

    // ================= DATE CHIPS =================
=======
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
    private fun setupDateChips() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEE", Locale.getDefault())
        val dayFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())

        for (i in 0..6) {
<<<<<<< HEAD
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
=======
            val dateChipBinding = ItemDateChipBinding.inflate(LayoutInflater.from(this))
            
            val date = calendar.time
            val formattedDate = dayFormat.format(date)
            dateChipBinding.tvDay.text = dateFormat.format(date).uppercase()
            dateChipBinding.tvDate.text = SimpleDateFormat("d", Locale.getDefault()).format(date)

            dateChipBinding.root.setOnClickListener {
                selectDate(dateChipBinding, formattedDate)
            }

            if (i == 0) {
                selectDate(dateChipBinding, formattedDate)
            }

            binding.dateContainer.addView(dateChipBinding.root)
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
    }

<<<<<<< HEAD
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
=======
    private fun selectDate(dateChipBinding: ItemDateChipBinding, date: String) {
        for (i in 0 until binding.dateContainer.childCount) {
            val child = binding.dateContainer.getChildAt(i)
            if (child is androidx.cardview.widget.CardView) {
                child.setCardBackgroundColor(getColor(R.color.ios_gray6))
                val dayText = child.findViewById<android.widget.TextView>(R.id.tvDay)
                val dateText = child.findViewById<android.widget.TextView>(R.id.tvDate)
                dayText.setTextColor(getColor(R.color.apple_secondary_label))
                dateText.setTextColor(getColor(R.color.apple_label))
            }
        }

        dateChipBinding.root.setCardBackgroundColor(getColor(R.color.ios_blue))
        dateChipBinding.tvDay.setTextColor(getColor(R.color.white))
        dateChipBinding.tvDate.setTextColor(getColor(R.color.white))
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59

        selectedDate = date
        loadTimeSlotsForDate()
    }

<<<<<<< HEAD
    // ================= TIME SLOTS =================
    private fun setupTimeSlots() {

        timeSlotAdapter = TimeSlotAdapter { slot ->
            selectTimeSlot(slot)
=======
    private fun setupTimeSlots() {
        timeSlotAdapter = TimeSlotAdapter { timeSlot ->
            selectTimeSlot(timeSlot)
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        }

        binding.rvTimeSlots.apply {
            layoutManager = GridLayoutManager(this@BookSlotActivity, 3)
            adapter = timeSlotAdapter
        }

        loadTimeSlotsForDate()
    }

<<<<<<< HEAD
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

=======
    private fun loadTimeSlotsForDate() {
        val timeSlots = listOf(
            TimeSlot("9:00 AM", "30 min"),
            TimeSlot("9:30 AM", "30 min"),
            TimeSlot("10:00 AM", "30 min"),
            TimeSlot("10:30 AM", "30 min"),
            TimeSlot("11:00 AM", "30 min"),
            TimeSlot("11:30 AM", "30 min")
        )
        timeSlotAdapter.submitList(timeSlots)
    }

    private fun selectTimeSlot(timeSlot: TimeSlot) {
        selectedTimeSlot = timeSlot
        binding.tvSelectedTime.visibility = View.VISIBLE
        binding.tvSelectedTime.text = "Slot selected: ${timeSlot.time}"
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
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

<<<<<<< HEAD
    // ================= BOOKING =================
    private fun confirmBooking() {

=======
    private fun confirmBooking() {
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
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
<<<<<<< HEAD

                Toast.makeText(
                    this,
                    "Failed: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
=======
                Toast.makeText(this, "Failed to send request: ${e.message}", Toast.LENGTH_SHORT).show()
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
            }
    }

    private fun showSuccessAnimation() {
        binding.layoutSuccess.successOverlay.visibility = View.VISIBLE
<<<<<<< HEAD

        val anim = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        binding.layoutSuccess.successCard.startAnimation(anim)
=======
        val scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        binding.layoutSuccess.successCard.startAnimation(scaleUp)
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59

        binding.root.postDelayed({
            finish()
        }, 2000)
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
