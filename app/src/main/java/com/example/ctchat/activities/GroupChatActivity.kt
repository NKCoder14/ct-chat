package com.example.ctchat.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ctchat.R
import com.example.ctchat.adapters.ChatAdapter
import com.example.ctchat.databinding.ActivityGroupChatBinding
import com.example.ctchat.models.ChatMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class GroupChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupChatBinding
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private var groupId: String? = null
    private var groupName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_chat)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        groupId = intent.getStringExtra("GROUP_ID")
        groupName = intent.getStringExtra("GROUP_NAME")

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = groupName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        setupRecyclerView()
        listenForMessages()

        binding.btnSend.setOnClickListener {
            val messageText = binding.messageBox.text.toString().trim()
            if (messageText.isNotEmpty()) {
                sendMessage(messageText)
            }
        }
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(auth.currentUser?.uid ?: "")
        binding.chatRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@GroupChatActivity).apply {
                stackFromEnd = true
            }
            adapter = chatAdapter
        }
    }

    private fun listenForMessages() {
        if (groupId == null) return

        db.collection("groups").document(groupId!!)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                snapshot?.let {
                    val messages = it.toObjects(ChatMessage::class.java)
                    chatAdapter.submitList(messages)
                    if (messages.isNotEmpty()) {
                        binding.chatRecyclerView.scrollToPosition(messages.size - 1)
                    }
                }
            }
    }

    private fun sendMessage(text: String) {
        val currentUser = auth.currentUser
        val currentUserName = currentUser?.displayName ?: "Unknown User"
        if (currentUser == null || groupId == null) return

        val message = ChatMessage(
            senderId = currentUser.uid,
            senderName = currentUserName,
            text = text,
            timestamp = System.currentTimeMillis()
        )

        db.collection("groups").document(groupId!!)
            .collection("messages")
            .add(message)
            .addOnSuccessListener {
                binding.messageBox.text.clear()
            }
    }
}