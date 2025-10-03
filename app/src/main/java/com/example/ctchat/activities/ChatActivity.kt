package com.example.ctchat.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ctchat.R
import com.example.ctchat.adapters.ChatAdapter
import com.example.ctchat.databinding.ActivityChatBinding
import com.example.ctchat.models.ChatMessage
import com.example.ctchat.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var chatAdapter: ChatAdapter
    private var otherUser: User? = null
    private var chatRoomId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        otherUser = User(
            uid = intent.getStringExtra("USER_ID"),
            username = intent.getStringExtra("USER_NAME")
        )

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = otherUser?.username ?: "Chat"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView()
        determineChatRoomId()

        binding.btnSend.setOnClickListener {
            val messageText = binding.etMessage.text.toString().trim()
            if (messageText.isNotEmpty()) {
                sendMessage(messageText)
            }
        }
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(auth.currentUser!!.uid)
        binding.chatRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity).apply {
                stackFromEnd = true
            }
            adapter = chatAdapter
        }
    }

    private fun determineChatRoomId() {
        val currentUserId = auth.currentUser?.uid
        val otherUserId = otherUser?.uid

        if (currentUserId != null && otherUserId != null) {
            chatRoomId = if (currentUserId < otherUserId) {
                "${currentUserId}_${otherUserId}"
            } else {
                "${otherUserId}_${currentUserId}"
            }
            listenForMessages()
        }
    }

    private fun listenForMessages() {
        chatRoomId?.let {
            firestore.collection("chats").document(it).collection("messages")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        return@addSnapshotListener
                    }

                    val messages = snapshots?.toObjects(ChatMessage::class.java)
                    if (messages != null) {
                        chatAdapter.submitList(messages)
                        binding.chatRecyclerView.scrollToPosition(messages.size - 1)
                    }
                }
        }
    }

    private fun sendMessage(messageText: String) {
        val currentUserId = auth.currentUser?.uid ?: return
        val receiverId = otherUser?.uid ?: return

        val message = ChatMessage(
            senderId = currentUserId,
            receiverId = receiverId,
            text = messageText,
            timestamp = System.currentTimeMillis()
        )

        chatRoomId?.let {
            firestore.collection("chats").document(it).collection("messages").add(message)
                .addOnSuccessListener {
                    binding.etMessage.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}