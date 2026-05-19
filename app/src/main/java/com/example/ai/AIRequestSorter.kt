package com.example.ai

import com.example.facultyflow.faculty.models.BookingRequest

object AIRequestSorter {

    fun sortRequests(requests: List<BookingRequest>): List<BookingRequest> {
        // Sorting by date and timeSlot as a stable alternative to the missing urgency/preferredTime
        return requests.sortedWith(
            compareBy<BookingRequest> { it.date }
                .thenBy { it.timeSlot }
        )
    }
}
