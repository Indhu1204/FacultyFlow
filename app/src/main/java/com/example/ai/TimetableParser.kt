package com.example.ai

import java.util.regex.Pattern

data class TimetableSlot(
    val time: String,
    val subject: String? = null
)

object TimetableParser {

    // Regex to detect various time formats: 9 AM, 09:00, 9:30, 09:00 - 10:00, etc.
    private val timeRegex = Pattern.compile(
        "(\\d{1,2}(?::\\d{2})?\\s*(?:AM|PM|am|pm)?)\\s*(?:-|–|to)?\\s*(\\d{1,2}(?::\\d{2})?\\s*(?:AM|PM|am|pm)?)?",
        Pattern.CASE_INSENSITIVE
    )

    private val commonSubjects = listOf(
        "math", "science", "physics", "chemistry", "biology", "history", "english",
        "computer", "lab", "lecture", "seminar", "workshop", "tutorial", "data", "algorithms", "eng"
    )

    fun parse(rawText: String): List<TimetableSlot> {
        val slots = mutableListOf<TimetableSlot>()
        val lines = rawText.split("\n")

        for (line in lines) {
            val trimmedLine = line.trim()
            if (trimmedLine.isEmpty()) continue

            val timeMatcher = timeRegex.matcher(trimmedLine)
            if (timeMatcher.find()) {
                val startTime = timeMatcher.group(1)?.trim() ?: ""
                val endTime = timeMatcher.group(2)?.trim()
                
                val timeDisplay = if (endTime != null) "$startTime - $endTime" else startTime
                
                // Try to find a subject in the same line or nearby (simplified for this MVP)
                val subject = findSubject(trimmedLine)
                
                if (timeDisplay.isNotEmpty()) {
                    slots.add(TimetableSlot(timeDisplay, subject))
                }
            } else {
                // If no time found, check if it's a high-confidence subject line
                val subject = findSubject(trimmedLine)
                if (subject != null && subject.length > 3) {
                    // If we found a subject without a time, we might want to associate it with the next found time
                    // but for simplicity, we just look for time + subject together or nearby.
                }
            }
        }

        // De-duplicate and filter noise
        return slots.distinctBy { it.time.lowercase() }
    }

    private fun findSubject(line: String): String? {
        val lowerLine = line.lowercase()
        for (subject in commonSubjects) {
            if (lowerLine.contains(subject)) {
                // Return the whole line as the subject context if a keyword is found
                return line.replace(timeRegex.toRegex(), "").trim()
            }
        }
        return null
    }
}
