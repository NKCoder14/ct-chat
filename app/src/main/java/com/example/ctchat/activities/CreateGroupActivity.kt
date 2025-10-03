package com.example.ctchat.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var selectUserAdapter: SelectUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_group)
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView()
        fetchUsers()

        binding.btnCreateGroup.setOnClickListener {
            createGroup()
        }
    }

    private fun setupRecyclerView() {
        selectUserAdapter = SelectUserAdapter()
        binding.usersRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CreateGroupActivity)
            adapter = selectUserAdapter
        }
    }

    private fun fetchUsers() {
        firestore.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val users = result.toObjects(User::class.java)
                val otherUsers = users.filter { it.uid != auth.currentUser?.uid }
                selectUserAdapter.submitList(otherUsers)
            }
    }

    private fun createGroup() {
        val groupName = binding.etGroupName.text.toString().trim()
        val selectedUsers = selectUserAdapter.getSelectedUsers()

        if (groupName.isEmpty()) {
            Toast.makeText(this, "Please enter a group name", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedUsers.size < 2) {
            Toast.makeText(this, "Please select at least two members for the group", Toast.LENGTH_SHORT).show()
            return
        }

        val currentUserUid = auth.currentUser!!.uid
        val memberIds = selectedUsers.mapNotNull { it.uid }.toMutableList()
        memberIds.add(currentUserUid)

        val group = hashMapOf(
            "name" to groupName,
            "members" to memberIds,
            "createdAt" to System.currentTimeMillis()
        )

        firestore.collection("groups").add(group)
            .addOnSuccessListener {
                Toast.makeText(this, "Group '$groupName' created successfully", Toast.LENGTH_SHORT).show()
                finish() 
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to create group", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}