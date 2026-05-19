package com.example.facultyflow

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.facultyflow.databinding.ActivityMainBinding
import com.example.facultyflow.faculty.FacultyHomeActivity
import com.example.facultyflow.student.FacultyDirectoryActivity
import com.example.facultyflow.utils.Constants
import com.example.facultyflow.utils.PreferencesManager
<<<<<<< HEAD
=======
import com.google.firebase.auth.FirebaseAuth
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
<<<<<<< HEAD
=======
    private lateinit var auth: FirebaseAuth
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
<<<<<<< HEAD
        val preferencesManager = PreferencesManager(this)
        
        // Reset login state to show Login/Register screens
        preferencesManager.isLoggedIn = false
=======
        auth = FirebaseAuth.getInstance()
        val preferencesManager = PreferencesManager(this)
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59

        // Fade in animation
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)

        binding.mainContent.startAnimation(fadeIn)

        binding.root.postDelayed({
            binding.mainContent.startAnimation(fadeOut)
            
            binding.root.postDelayed({
<<<<<<< HEAD
                if (preferencesManager.isLoggedIn) {
                    val intent = if (preferencesManager.userType == Constants.USER_TYPE_STUDENT) {
                        Intent(this, FacultyDirectoryActivity::class.java)
                    } else {
                        Intent(this, FacultyHomeActivity::class.java)
                    }
                    startActivity(intent)
                } else {
=======
                val currentUser = auth.currentUser
                
                // CRITICAL FIX: Verify Firebase user exists and preferences match
                if (currentUser != null && preferencesManager.isLoggedIn) {
                    // Check email verification status on app launch
                    if (currentUser.isEmailVerified) {
                        val intent = if (preferencesManager.userType == Constants.USER_TYPE_STUDENT) {
                            Intent(this, FacultyDirectoryActivity::class.java)
                        } else {
                            Intent(this, FacultyHomeActivity::class.java)
                        }
                        startActivity(intent)
                    } else {
                        // Not verified, force login
                        auth.signOut()
                        preferencesManager.clearAll()
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                } else {
                    // No user or local session cleared
                    auth.signOut()
                    preferencesManager.clearAll()
>>>>>>> 5e233c7c3562890288bc3be70aaab896d23edf59
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                finish()
            }, 1000)
        }, 2000)
    }
}
