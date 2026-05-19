package com.example.facultyflow.student.models

data class TimeSlot(
    val time: String,
    val duration: String,
    val isAvailable: Boolean = true
)