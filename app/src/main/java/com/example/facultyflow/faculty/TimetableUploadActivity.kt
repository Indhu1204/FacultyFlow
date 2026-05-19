package com.example.facultyflow.faculty

<<<<<<< HEAD
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ai.SmartEngine
import com.example.ai.TimetableSlot
import com.example.facultyflow.R
import com.example.facultyflow.databinding.ActivityTimetableUploadBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
=======
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.facultyflow.R // <--- Add this line
import com.example.facultyflow.databinding.ActivityTimetableUploadBinding

>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59

class TimetableUploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTimetableUploadBinding
<<<<<<< HEAD
    private var detectedSlots = mutableListOf<TimetableSlot>()

    private val pickFileLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { handleFile(it) }
        }
=======
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimetableUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
<<<<<<< HEAD
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvDetectedSlots.layoutManager = LinearLayoutManager(this)
        binding.rvDetectedSlots.adapter = DetectedSlotsAdapter(detectedSlots)
    }

    private fun setupClickListeners() {
        binding.ivBack.setOnClickListener { finish() }

        binding.uploadArea.setOnClickListener {
            pickFileLauncher.launch("*/*")
        }

        binding.cameraOption.setOnClickListener {
            // In a real app, this would launch a camera intent. 
            // Here we reuse the file picker for simplicity as per your existing logic.
            pickFileLauncher.launch("image/*")
        }

        binding.btnSaveSlots.setOnClickListener {
            saveToFirestore()
        }

        binding.btnRetry.setOnClickListener {
            resetUI()
        }
    }

    private fun handleFile(uri: Uri) {
        showLoadingState(true)

        SmartEngine.scanTimetable(this, uri) { slots ->
            if (isFinishing) return@scanTimetable
            
            showLoadingState(false)
            if (slots.isEmpty()) {
                Toast.makeText(this, "No busy slots detected. Please try a clearer image.", Toast.LENGTH_LONG).show()
                resetUI()
            } else {
                displayResults(slots)
            }
        }
    }

    private fun displayResults(slots: List<TimetableSlot>) {
        detectedSlots.clear()
        detectedSlots.addAll(slots)
        binding.rvDetectedSlots.adapter?.notifyDataSetChanged()

        binding.selectionArea.visibility = View.GONE
        binding.resultsArea.visibility = View.VISIBLE
        binding.tvHeader.text = "Review Slots"
        binding.tvSubHeader.text = "We found ${slots.size} busy periods. Sync them to your schedule?"
    }

    private fun saveToFirestore() {
        val facultyId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        
        binding.btnSaveSlots.isEnabled = false
        binding.btnSaveSlots.text = "Syncing..."

        val slotStrings = detectedSlots.map { it.time }

        FirebaseFirestore.getInstance()
            .collection("timetables")
            .document(facultyId)
            .set(mapOf("slots" to slotStrings))
            .addOnSuccessListener {
                showSuccessState()
            }
            .addOnFailureListener { e ->
                binding.btnSaveSlots.isEnabled = true
                binding.btnSaveSlots.text = "Sync to My Schedule"
                Toast.makeText(this, "Sync failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showLoadingState(isLoading: Boolean) {
        binding.selectionArea.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.progressArea.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.resultsArea.visibility = View.GONE
    }

    private fun resetUI() {
        binding.selectionArea.visibility = View.VISIBLE
        binding.resultsArea.visibility = View.GONE
        binding.progressArea.visibility = View.GONE
        binding.successState.visibility = View.GONE
        binding.tvHeader.text = "Update Your Schedule"
        binding.tvSubHeader.text = "Upload your weekly timetable (PDF or Image) to sync your availability."
    }

    private fun showSuccessState() {
        binding.resultsArea.visibility = View.GONE
        binding.successState.visibility = View.VISIBLE
        binding.tvHeader.visibility = View.GONE
        binding.tvSubHeader.visibility = View.GONE

        binding.root.postDelayed({
            finish()
        }, 2500)
    }

    // Inner Adapter class for simplicity in this replacement
    private class DetectedSlotsAdapter(private val slots: List<TimetableSlot>) :
        RecyclerView.Adapter<DetectedSlotsAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val timeText: TextView = view.findViewById(android.R.id.text1)
            val subjectText: TextView = view.findViewById(android.R.id.text2)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_2, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val slot = slots[position]
            holder.timeText.text = slot.time
            holder.subjectText.text = slot.subject ?: "Busy Period"
            
            // Apply some styling
            holder.timeText.setTextColor(holder.itemView.context.getColor(R.color.apple_label))
            holder.subjectText.setTextColor(holder.itemView.context.getColor(R.color.apple_secondary_label))
        }

        override fun getItemCount() = slots.size
=======
    }

    private fun setupClickListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.uploadArea.setOnClickListener {
            simulateFileUpload()
        }

        binding.cameraOption.setOnClickListener {
            simulateCameraCapture()
        }
    }

    private fun simulateFileUpload() {
        // Show progress
        binding.uploadArea.visibility = View.GONE
        binding.cameraOption.visibility = View.GONE
        binding.progressArea.visibility = View.VISIBLE
        binding.tvProgressText.text = getString(R.string.uploading)

        // Simulate upload progress
        binding.progressIndicator.progress = 0
        val progressUpdate = object : Runnable {
            override fun run() {
                val currentProgress = binding.progressIndicator.progress + 20
                binding.progressIndicator.progress = currentProgress

                if (currentProgress < 100) {
                    binding.progressIndicator.postDelayed(this, 500)
                } else {
                    // Upload complete, show scanning
                    binding.tvProgressText.text = getString(R.string.scanning)
                    binding.progressIndicator.postDelayed({
                        showSuccessState()
                    }, 2000)
                }
            }
        }
        binding.progressIndicator.postDelayed(progressUpdate, 500)
    }

    private fun simulateCameraCapture() {
        // In a real app, this would open the camera
        simulateFileUpload()
    }

    private fun showSuccessState() {
        binding.progressArea.visibility = View.GONE
        binding.successState.visibility = View.VISIBLE

        // Auto navigate back after 2 seconds
        binding.successState.postDelayed({
            finish()
        }, 2000)
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
    }
}
