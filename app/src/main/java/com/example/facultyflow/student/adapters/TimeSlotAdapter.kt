package com.example.facultyflow.student.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.facultyflow.databinding.ItemTimeSlotBinding
import com.example.facultyflow.student.models.TimeSlot
<<<<<<< HEAD
import com.example.facultyflow.R
=======
import com.example.facultyflow.R // Add this line
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59

class TimeSlotAdapter(
    private val onTimeSlotClick: (TimeSlot) -> Unit
) : ListAdapter<TimeSlot, TimeSlotAdapter.TimeSlotViewHolder>(TimeSlotDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSlotViewHolder {
<<<<<<< HEAD
        val binding = ItemTimeSlotBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
=======
        val binding = ItemTimeSlotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        return TimeSlotViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeSlotViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

<<<<<<< HEAD
    inner class TimeSlotViewHolder(
        private val binding: ItemTimeSlotBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(slot: TimeSlot) {

            binding.tvTime.text = slot.time
            binding.tvDuration.text = slot.duration

            val context = binding.root.context

            if (slot.isAvailable) {
                // 🟢 AVAILABLE SLOT
                binding.root.setCardBackgroundColor(
                    context.getColor(R.color.ios_blue)
                )

                binding.tvTime.setTextColor(
                    context.getColor(R.color.white)
                )

                binding.tvDuration.setTextColor(
                    context.getColor(R.color.white)
                )

                binding.root.alpha = 1f
                binding.root.isClickable = true

                binding.root.setOnClickListener {
                    onTimeSlotClick(slot)
                }

            } else {
                // 🔴 BUSY SLOT
                binding.root.setCardBackgroundColor(
                    context.getColor(R.color.ios_gray4)
                )

                binding.tvTime.setTextColor(
                    context.getColor(R.color.apple_secondary_label)
                )

                binding.tvDuration.setTextColor(
                    context.getColor(R.color.apple_secondary_label)
                )

                binding.root.alpha = 0.5f
                binding.root.isClickable = false

                binding.root.setOnClickListener(null)
=======
    inner class TimeSlotViewHolder(private val binding: ItemTimeSlotBinding) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(timeSlot: TimeSlot) {
            binding.tvTime.text = timeSlot.time
            binding.tvDuration.text = timeSlot.duration

            // Set click listener
            binding.root.setOnClickListener {
                onTimeSlotClick(timeSlot)
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
            }
        }
    }
}

class TimeSlotDiffCallback : DiffUtil.ItemCallback<TimeSlot>() {
<<<<<<< HEAD

=======
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
    override fun areItemsTheSame(oldItem: TimeSlot, newItem: TimeSlot): Boolean {
        return oldItem.time == newItem.time
    }

    override fun areContentsTheSame(oldItem: TimeSlot, newItem: TimeSlot): Boolean {
        return oldItem == newItem
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
