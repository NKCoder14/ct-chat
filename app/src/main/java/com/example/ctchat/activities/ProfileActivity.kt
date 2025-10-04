package com.example.ctchat.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ctchat.R
import com.example.ctchat.databinding.ActivityProfileBinding
import com.example.ctchat.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        fetchUserData()

        binding.btnSave.setOnClickListener {
            val newUsername = binding.etUsername.text.toString().trim()
            if (newUsername.isNotEmpty()) {
                saveUserData(newUsername)
            } else {
                Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchUserData() {
        binding.progressBar.visibility = View.VISIBLE
        val currentUser = auth.currentUser
        if (currentUser == null) {
            finish()
            return
        }

        binding.etEmail.setText(currentUser.email)

        db.collection("users").document(currentUser.uid).get()
            .addOnSuccessListener { document ->
                binding.progressBar.visibility = View.GONE
                if (document != null) {
                    val user = document.toObject(User::class.java)
                    binding.etUsername.setText(user?.username)
                }
            }
            .addOnFailureListener {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Failed to load profile data.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveUserData(newUsername: String) {
        binding.progressBar.visibility = View.VISIBLE
        val currentUser = auth.currentUser
        if (currentUser == null) {
            binding.progressBar.visibility = View.GONE
            return
        }

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(newUsername)
            .build()

        currentUser.updateProfile(profileUpdates)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    db.collection("users").document(currentUser.uid)
                        .update("username", newUsername)
                        .addOnSuccessListener {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener { e ->
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, "Firestore update failed: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Auth update failed: ${authTask.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}