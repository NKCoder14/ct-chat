package com.example.ctchat.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private var otherUser: User? = null
    private var chatRoomId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val otherUserId = intent.getStringExtra("USER_ID")
        val otherUserName = intent.getStringExtra("USER_NAME")
        otherUser = User(uid = otherUserId, username = otherUserName)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = otherUser?.username ?: "Chat"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        setupRecyclerView()

        binding.btnSend.setOnClickListener {
            val messageText = binding.messageBox.text.toString().trim()
            if (messageText.isNotEmpty()) {
                sendMessage(messageText)
            }
        }

        determineChatRoomId()
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(auth.currentUser?.uid ?: "")
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
            // Create a consistent chat room ID by ordering the UIDs alphabetically
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
            db.collection("chats").document(it)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        return@addSnapshotListener
                    }
                    snapshot?.let { querySnapshot ->
                        val messages = querySnapshot.toObjects(ChatMessage::class.java)
                        chatAdapter.submitList(messages)
                        binding.chatRecyclerView.scrollToPosition(messages.size - 1)
                    }
                }
        }
    }

    private fun sendMessage(text: String) {
        val currentUser = auth.currentUser
        if (currentUser == null || chatRoomId == null) return

        val message = ChatMessage(
            senderId = currentUser.uid,
            senderName = null, 
            text = text,
            timestamp = System.currentTimeMillis()
        )

        chatRoomId?.let {
            db.collection("chats").document(it)
                .collection("messages")
                .add(message)
                .addOnSuccessListener {
                    binding.messageBox.text.clear()
                }
        }
    }
}