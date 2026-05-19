package com.example.facultyflow.faculty.models

<<<<<<< HEAD
import com.google.firebase.firestore.PropertyName

=======
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
data class BookingRequest(
    val id: String = "",
    val studentId: String = "",
    val studentName: String = "",
<<<<<<< HEAD
    val date: String = "",
    val timeSlot: String = "",
    @get:PropertyName("studentNote") @set:PropertyName("studentNote") var note: String = "",
    val status: String = "pending"
) {
    // Computed property to match the UI expectation
    val requestedTime: String
        get() = if (date.isNotEmpty() && timeSlot.isNotEmpty()) "$date, $timeSlot" else ""
}
=======
    val requestedTime: String = "",
    val note: String = "",
    val status: String = "pending" // "pending", "accepted", "declined"
)
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
