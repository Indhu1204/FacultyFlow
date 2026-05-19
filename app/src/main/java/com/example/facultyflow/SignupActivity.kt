package com.example.facultyflow

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.facultyflow.databinding.ActivitySignupBinding
<<<<<<< HEAD
import com.example.facultyflow.faculty.FacultyHomeActivity
import com.example.facultyflow.student.FacultyDirectoryActivity
=======
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
import com.example.facultyflow.utils.Constants
import com.example.facultyflow.utils.PreferencesManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var isStudent = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
<<<<<<< HEAD

=======
        
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        preferencesManager = PreferencesManager(this)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        updateUserTypeUI()
<<<<<<< HEAD
        setupUserTypeSelection()
        setupFormFields()
        setupSignupButton()
=======
        
        setupUserTypeSelection()
        setupFormFields()
        setupSignupButton()
        
        binding.tvLoginToggle.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
    }

    private fun setupUserTypeSelection() {
        binding.btnStudent.setOnClickListener {
            if (!isStudent) {
                isStudent = true
                updateUserTypeUI()
            }
        }

        binding.btnFaculty.setOnClickListener {
            if (isStudent) {
                isStudent = false
                updateUserTypeUI()
            }
        }
    }

    private fun updateUserTypeUI() {
        if (isStudent) {
            binding.btnStudent.apply {
                setBackgroundResource(R.drawable.segmented_control_selected)
                setTypeface(null, android.graphics.Typeface.BOLD)
            }
            binding.btnFaculty.apply {
                background = null
                setTypeface(null, android.graphics.Typeface.NORMAL)
            }
            binding.studentFieldsContainer.visibility = View.VISIBLE
<<<<<<< HEAD
=======
            binding.facultyFieldsContainer.visibility = View.GONE
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        } else {
            binding.btnFaculty.apply {
                setBackgroundResource(R.drawable.segmented_control_selected)
                setTypeface(null, android.graphics.Typeface.BOLD)
            }
            binding.btnStudent.apply {
                background = null
                setTypeface(null, android.graphics.Typeface.NORMAL)
            }
            binding.studentFieldsContainer.visibility = View.GONE
<<<<<<< HEAD
=======
            binding.facultyFieldsContainer.visibility = View.VISIBLE
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        }
    }

    private fun setupFormFields() {
<<<<<<< HEAD
        val degrees = arrayOf(
            "B.Tech Computer Science",
            "B.Tech Electronics",
            "B.Tech Mechanical",
            "B.Tech Civil",
            "M.Tech Computer Science",
            "M.Tech Electronics"
        )
        val degreeAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, degrees)
        binding.etDegree.setAdapter(degreeAdapter)

        val semesters = arrayOf(
            "1st Semester",
            "2nd Semester",
            "3rd Semester",
            "4th Semester",
            "5th Semester",
            "6th Semester",
            "7th Semester",
            "8th Semester"
        )
        val semesterAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, semesters)
=======
        val degreeAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, Constants.DEGREES)
        binding.etDegree.setAdapter(degreeAdapter)

        val semesterAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, Constants.SEMESTERS)
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        binding.etSemester.setAdapter(semesterAdapter)
    }

    private fun setupSignupButton() {
        binding.btnSignup.setOnClickListener {
            if (validateForm()) {
                performFirebaseSignup()
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true
<<<<<<< HEAD

=======
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (name.isEmpty()) {
            binding.tilName.error = "Name is required"
            isValid = false
        } else {
            binding.tilName.error = null
        }

        if (email.isEmpty()) {
            binding.tilEmail.error = "Email is required"
            isValid = false
<<<<<<< HEAD
        } else if (!isValidEmail(email)) {
=======
        } else if (!email.endsWith(Constants.EMAIL_DOMAIN)) {
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
            binding.tilEmail.error = "Please use your @rvu.edu.in email"
            isValid = false
        } else {
            binding.tilEmail.error = null
        }
<<<<<<< HEAD

=======
        
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        if (password.length < 6) {
            binding.tilPassword.error = "Password must be at least 6 characters"
            isValid = false
        } else {
            binding.tilPassword.error = null
        }

        if (isStudent) {
<<<<<<< HEAD
            val degree = binding.etDegree.text.toString().trim()
            val semester = binding.etSemester.text.toString().trim()

            if (degree.isEmpty()) {
=======
            if (binding.etDegree.text.toString().isEmpty()) {
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
                binding.tilDegree.error = "Degree is required"
                isValid = false
            } else {
                binding.tilDegree.error = null
            }

<<<<<<< HEAD
            if (semester.isEmpty()) {
=======
            if (binding.etSemester.text.toString().isEmpty()) {
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
                binding.tilSemester.error = "Semester is required"
                isValid = false
            } else {
                binding.tilSemester.error = null
            }
<<<<<<< HEAD
=======
        } else {
            val accessCode = binding.etAccessCode.text.toString().trim()
            if (accessCode != Constants.FACULTY_ACCESS_CODE) {
                binding.tilAccessCode.error = "Invalid Faculty Access Code"
                isValid = false
            } else {
                binding.tilAccessCode.error = null
            }
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
        }

        return isValid
    }

<<<<<<< HEAD
    private fun isValidEmail(email: String): Boolean {
        return email.endsWith("@rvu.edu.in") &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

=======
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
    private fun performFirebaseSignup() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val userType = if (isStudent) Constants.USER_TYPE_STUDENT else Constants.USER_TYPE_FACULTY

        binding.btnSignup.isEnabled = false
        binding.btnSignup.text = "Creating account..."

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
<<<<<<< HEAD

                    val userId = auth.currentUser?.uid ?: ""

                    val userData = mutableMapOf<String, Any>(
                        "uid" to userId,
                        "name" to name,
                        "email" to email,
                        "userType" to userType
                    )

                    if (isStudent) {
                        userData["degree"] = binding.etDegree.text.toString()
                        userData["semester"] = binding.etSemester.text.toString()
                    }

                    db.collection("users").document(userId)
                        .set(userData)
                        .addOnSuccessListener {

                            preferencesManager.userName = name
                            preferencesManager.userEmail = email
                            preferencesManager.userType = userType
                            preferencesManager.isLoggedIn = true

                            navigateToDashboard()
                        }
                        .addOnFailureListener { e ->
                            resetButton()
                            Toast.makeText(this, "Error saving user: ${e.message}", Toast.LENGTH_SHORT).show()
                        }

                } else {
                    resetButton()

                    val errorMsg = when {
                        task.exception?.message?.contains("email address is already in use", true) == true ->
                            "Email already registered"
                        task.exception?.message?.contains("badly formatted", true) == true ->
                            "Invalid email format"
                        task.exception?.message?.contains("password", true) == true ->
                            "Weak password"
                        else ->
                            "Signup failed: ${task.exception?.message}"
                    }

                    Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun resetButton() {
        binding.btnSignup.isEnabled = true
        binding.btnSignup.text = "Create Account"
    }

    private fun navigateToDashboard() {
        if (isStudent) {
            startActivity(Intent(this, FacultyDirectoryActivity::class.java))
        } else {
            startActivity(Intent(this, FacultyHomeActivity::class.java))
        }
        finish()
    }
}
=======
                    val user = auth.currentUser
                    user?.sendEmailVerification()?.addOnCompleteListener { verificationTask ->
                        if (verificationTask.isSuccessful) {
                            val userId = user.uid

                            val userData = mutableMapOf<String, Any>(
                                "uid" to userId,
                                "name" to name,
                                "email" to email,
                                "userType" to userType,
                                "isVerified" to false
                            )

                            if (isStudent) {
                                userData["degree"] = binding.etDegree.text.toString()
                                userData["semester"] = binding.etSemester.text.toString()
                            } else {
                                userData["availability"] = Constants.AVAILABILITY_AVAILABLE
                            }

                            db.collection("users").document(userId)
                                .set(userData)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Verification email sent to $email. Please verify before logging in.", Toast.LENGTH_LONG).show()
                                    auth.signOut()
                                    startActivity(Intent(this, LoginActivity::class.java))
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    user.delete()
                                    binding.btnSignup.isEnabled = true
                                    binding.btnSignup.text = "Create Account"
                                    Toast.makeText(this, "Database error: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            binding.btnSignup.isEnabled = true
                            binding.btnSignup.text = "Create Account"
                            Toast.makeText(this, "Failed to send verification email.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    binding.btnSignup.isEnabled = true
                    binding.btnSignup.text = "Create Account"
                    Toast.makeText(this, "Auth error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
