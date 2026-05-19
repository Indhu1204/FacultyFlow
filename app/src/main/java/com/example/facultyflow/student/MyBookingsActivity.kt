package com.example.facultyflow.student

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.facultyflow.databinding.ActivityMyBookingsBinding
import com.example.facultyflow.student.adapters.MyBookingsAdapter
import com.example.facultyflow.student.models.Booking
import com.example.ai.SmartEngine
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyBookingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyBookingsBinding
    private lateinit var adapter: MyBookingsAdapter
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyBookingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycler()
        loadBookings()
    }

    private fun setupRecycler() {
        adapter = MyBookingsAdapter()
        binding.rvBookings.layoutManager = LinearLayoutManager(this)
        binding.rvBookings.adapter = adapter
    }

    private fun loadBookings() {

        val studentId = auth.currentUser?.uid ?: return

        db.collection("bookings")
            .whereEqualTo("studentId", studentId)
            .get()
            .addOnSuccessListener { result ->

                val bookings = result.map {
                    it.toObject(Booking::class.java)
                }

                // ✅ CORRECT PLACE TO CALL AI
                val sortedBookings = SmartEngine.sortStudentBookings(bookings)

                adapter.submitList(sortedBookings)
            }
    }
}