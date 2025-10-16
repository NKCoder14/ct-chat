package com.example.ctchat.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ctchat.R
import com.example.ctchat.adapters.SelectUserAdapter
import com.example.ctchat.databinding.ActivityCreateGroupBinding
import com.example.ctchat.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CreateGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateGroupBinding
    private lateinit var adapter: SelectUserAdapter
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var allUsers = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_group)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        setupRecyclerView()
        fetchUsers()

        binding.btnCreateGroup.setOnClickListener {
            createGroup()
        }
    }

    private fun setupRecyclerView() {
        adapter = SelectUserAdapter()
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.usersRecyclerView.adapter = adapter
    }

    private fun fetchUsers() {
        val currentUserId = auth.currentUser?.uid
        db.collection("users").get().addOnSuccessListener { snapshot ->
            allUsers.clear()
            for (document in snapshot.documents) {
                val user = document.toObject(User::class.java)
                if (user != null && user.uid != currentUserId) {
                    allUsers.add(user)
                }
            }
            adapter.submitList(allUsers)
        }
    }

    private fun createGroup() {
        val groupName = binding.etGroupName.text.toString().trim()
        val selectedUserIds = adapter.getSelectedUserIds()

        if (groupName.isEmpty()) {
            Toast.makeText(this, "Please enter a group name", Toast.LENGTH_SHORT).show()
            return
        }
        if (selectedUserIds.isEmpty()) {
            Toast.makeText(this, "Please select at least one member", Toast.LENGTH_SHORT).show()
            return
        }

        val currentUserId = auth.currentUser?.uid
        if (currentUserId == null) return

        val members = selectedUserIds.toMutableList()
        members.add(currentUserId)

        val groupId = db.collection("groups").document().id
        val groupData = mapOf(
            "groupId" to groupId,
            "name" to groupName,
            "members" to members,
            "lastMessage" to "Group created",
            "lastMessageTimestamp" to System.currentTimeMillis()
        )

        db.collection("groups").document(groupId).set(groupData)
            .addOnSuccessListener {
                Toast.makeText(this, "Group '$groupName' created successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to create group", Toast.LENGTH_SHORT).show()
            }
    }
}
