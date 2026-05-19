package com.example.facultyflow.student.models

data class Booking(
<<<<<<< HEAD
    val id: String = "",
    val studentId: String = "",
    val studentName: String = "",
    val facultyId: String = "",
    val facultyName: String = "",
    val facultyDesignation: String = "",
    val date: String = "",
    val timeSlot: String = "",
    val status: String = "",
    val studentNote: String = "",
    val facultyReply: String = "",
    val timestamp: com.google.firebase.Timestamp? = null
)
=======
    val id: String,
    val facultyName: String,
    val facultyDesignation: String,
    val bookingTime: String,
    val status: String, // "pending", "confirmed", "done"
    val studentNote: String,
    val facultyReply: String?
)
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
