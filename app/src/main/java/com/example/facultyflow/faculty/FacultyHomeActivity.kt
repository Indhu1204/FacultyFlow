package com.example.facultyflow.faculty

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.facultyflow.databinding.ActivityFacultyHomeBinding
import com.example.facultyflow.faculty.adapters.ScheduleAdapter
import com.example.facultyflow.faculty.models.ScheduleItem
import com.example.facultyflow.R
import com.example.facultyflow.utils.PreferencesManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FacultyHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFacultyHomeBinding
    private lateinit var scheduleAdapter: ScheduleAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var preferencesManager: PreferencesManager
<<<<<<< HEAD

=======
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
    private var isUpdatingFromDb = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< HEAD

        binding = ActivityFacultyHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔥 INIT FIRST (IMPORTANT)
=======
        binding = ActivityFacultyHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        preferencesManager = PreferencesManager(this)

        setupUI()
        setupSchedule()
        setupNavigation()
        setupClickListeners()
        fetchFacultyData()
        listenForPendingBookings()
    }

    private fun setupUI() {
<<<<<<< HEAD
        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)

        val greeting = when {
            hour < 12 -> "Good Morning"
            hour < 17 -> "Good Afternoon"
            else -> "Good Evening"
        }

=======
        val greeting = when {
            java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY) < 12 -> "Good Morning"
            java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY) < 17 -> "Good Afternoon"
            else -> "Good Evening"
        }
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        binding.tvGreeting.text = greeting
        binding.tvFacultyName.text = preferencesManager.userName
    }

    private fun fetchFacultyData() {
        val userId = auth.currentUser?.uid ?: return
<<<<<<< HEAD

        db.collection("users").document(userId)
            .addSnapshotListener { snapshot, _ ->

                if (snapshot != null && snapshot.exists()) {

                    val name = snapshot.getString("name") ?: ""
                    val availability = snapshot.getString("availability") ?: "green"

                    binding.tvFacultyName.text = name

                    isUpdatingFromDb = true
                    binding.switchBusy.isChecked = availability == "red"
                    isUpdatingFromDb = false

                    updateStatusUI(availability == "red")

                    preferencesManager.userName = name
                }
            }
    }

    // 🔥 BLOCK SLOT FUNCTION (SAFE + NO DUPLICATES)
    private fun blockSlot(time: String) {

        val facultyId = auth.currentUser?.uid ?: return

        val docRef = db.collection("timetables").document(facultyId)

        docRef.get().addOnSuccessListener { doc ->

            val currentSlots =
                (doc.get("slots") as? MutableList<String>) ?: mutableListOf()

            if (!currentSlots.contains(time)) {
                currentSlots.add(time)
            }

            docRef.set(mapOf("slots" to currentSlots))
                .addOnSuccessListener {
                    Toast.makeText(this, "$time blocked", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to block slot", Toast.LENGTH_SHORT).show()
                }
=======
        db.collection("users").document(userId).addSnapshotListener { snapshot, _ ->
            if (snapshot != null && snapshot.exists()) {
                val name = snapshot.getString("name") ?: ""
                val availability = snapshot.getString("availability") ?: "green"
                
                binding.tvFacultyName.text = name
                
                isUpdatingFromDb = true
                binding.switchBusy.isChecked = availability == "red"
                isUpdatingFromDb = false

                updateStatusUI(availability == "red")
                
                // Update local pref if name changed
                preferencesManager.userName = name
            }
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        }
    }

    private fun updateStatusUI(isBusy: Boolean) {
<<<<<<< HEAD

        val color = if (isBusy) R.color.ios_red else R.color.ios_green

        val colorValue = ContextCompat.getColor(this, color)

        binding.statusIndicator.backgroundTintList = ColorStateList.valueOf(colorValue)
        binding.switchBusy.trackTintList = ColorStateList.valueOf(colorValue)
    }

    private fun listenForPendingBookings() {

        val userId = auth.currentUser?.uid ?: return

=======
        val color = if (isBusy) R.color.ios_red else R.color.ios_green
        binding.statusIndicator.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, color))
        binding.switchBusy.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(this, color))
    }

    private fun listenForPendingBookings() {
        val userId = auth.currentUser?.uid ?: return
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        db.collection("bookings")
            .whereEqualTo("facultyId", userId)
            .whereEqualTo("status", "pending")
            .addSnapshotListener { snapshot, _ ->
<<<<<<< HEAD

                val count = snapshot?.size() ?: 0

                binding.tvPendingCount.text = count.toString()
                binding.cardPendingBookings.visibility =
                    if (count > 0) View.VISIBLE else View.GONE
=======
                val count = snapshot?.size() ?: 0
                binding.tvPendingCount.text = count.toString()
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
            }
    }

    private fun setupSchedule() {
<<<<<<< HEAD

        scheduleAdapter = ScheduleAdapter()

=======
        scheduleAdapter = ScheduleAdapter()
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        binding.rvSchedule.apply {
            layoutManager = LinearLayoutManager(this@FacultyHomeActivity)
            adapter = scheduleAdapter
        }

        val scheduleItems = listOf(
<<<<<<< HEAD
            ScheduleItem("09:00 AM", "10:00 AM", "Data Structures", "Room 301", "In Class", "grey"),
            ScheduleItem("10:00 AM", "11:00 AM", "Free Period", "Office", "Free", "green"),
            ScheduleItem("11:00 AM", "12:00 PM", "Algorithms", "Room 205", "In Class", "grey"),
            ScheduleItem("02:00 PM", "03:00 PM", "Office Hours", "Office", "Busy", "red")
        )

=======
            ScheduleItem("09:00 AM", "10:00 AM", "Data Structures", "Room 301, Block A", "In Class", "grey"),
            ScheduleItem("10:00 AM", "11:00 AM", "Free Period", "Office", "Free", "green"),
            ScheduleItem("11:00 AM", "12:00 PM", "Algorithms", "Room 205, Block B", "In Class", "grey"),
            ScheduleItem("02:00 PM", "03:00 PM", "Office Hours", "Office", "Busy", "red")
        )
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        scheduleAdapter.submitList(scheduleItems)
    }

    private fun setupNavigation() {
<<<<<<< HEAD

        binding.navHome.setOnClickListener {}

        binding.navTimetable.setOnClickListener {
            startActivity(Intent(this, TimetableUploadActivity::class.java))
        }

        binding.navBookings.setOnClickListener {
            startActivity(Intent(this, BookingInboxActivity::class.java))
        }

=======
        binding.navHome.setOnClickListener {
            // Already home
        }
        binding.navTimetable.setOnClickListener {
            startActivity(Intent(this, TimetableUploadActivity::class.java))
        }
        binding.navBookings.setOnClickListener {
            startActivity(Intent(this, BookingInboxActivity::class.java))
        }
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        binding.navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileEditorActivity::class.java))
        }
    }

    private fun setupClickListeners() {
<<<<<<< HEAD

=======
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        binding.cardPendingBookings.setOnClickListener {
            startActivity(Intent(this, BookingInboxActivity::class.java))
        }

        binding.switchBusy.setOnCheckedChangeListener { _, isChecked ->
<<<<<<< HEAD

            if (isUpdatingFromDb) return@setOnCheckedChangeListener

            val userId = auth.currentUser?.uid ?: return@setOnCheckedChangeListener

            val newStatus = if (isChecked) "red" else "green"

=======
            if (isUpdatingFromDb) return@setOnCheckedChangeListener
            
            val userId = auth.currentUser?.uid ?: return@setOnCheckedChangeListener
            val newStatus = if (isChecked) "red" else "green"
            
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
            db.collection("users").document(userId)
                .update("availability", newStatus)
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update status", Toast.LENGTH_SHORT).show()
                }
<<<<<<< HEAD

            updateStatusUI(isChecked)
        }

=======
        }
        
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        binding.ivProfile.setOnClickListener {
            startActivity(Intent(this, ProfileEditorActivity::class.java))
        }
    }
}
