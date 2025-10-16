package com.example.ctchat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ctchat.adapters.ConversationAdapter
import com.example.ctchat.databinding.FragmentChatsBinding
import com.example.ctchat.models.Conversation
import com.example.ctchat.models.ChatSession
import com.example.ctchat.models.Group
import com.example.ctchat.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatsFragment : Fragment() {

    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!
    private lateinit var conversationAdapter: ConversationAdapter
    private var allConversations = mutableListOf<Conversation>()
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var allUsersMap = mapOf<String, User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchAllUsersThenConversations()
    }

    private fun setupRecyclerView() {
        conversationAdapter = ConversationAdapter(requireContext())
        binding.usersRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = conversationAdapter
        }
    }

    private fun fetchAllUsersThenConversations() {
        db.collection("users").addSnapshotListener { userSnapshot, _ ->
            allUsersMap = userSnapshot?.toObjects(User::class.java)?.associateBy { it.uid!! } ?: emptyMap()
            fetchConversations()
        }
    }

    private fun fetchConversations() {
        val currentUserId = auth.currentUser?.uid ?: return

        db.collection("chats")
            .whereArrayContains("members", currentUserId)
            .addSnapshotListener { chatSnapshot, _ ->
                val activeChatSessions = chatSnapshot?.toObjects(ChatSession::class.java)
                    ?.mapNotNull { session ->
                        val otherUserId = session.members.find { it != currentUserId }
                        allUsersMap[otherUserId]?.let { otherUser ->
                            Conversation.ChatSessionConversation(session, otherUser)
                        }
                    } ?: emptyList()

                db.collection("groups")
                    .whereArrayContains("members", currentUserId)
                    .addSnapshotListener { groupSnapshot, _ ->
                        val activeGroups = groupSnapshot?.toObjects(Group::class.java)
                            ?.map { Conversation.GroupConversation(it) } ?: emptyList()

                        val userIdsInActiveChats = activeChatSessions.map { it.otherUser?.uid }
                        val usersNotInActiveChats = allUsersMap.values
                            .filter { it.uid != currentUserId && !userIdsInActiveChats.contains(it.uid) }
                            .map { user ->
                                val dummySession = ChatSession(lastMessage = null, lastMessageTimestamp = 0)
                                Conversation.ChatSessionConversation(dummySession, user)
                            }

                        val combinedList = (activeChatSessions + activeGroups + usersNotInActiveChats)
                            .sortedByDescending { it.lastMessageTimestamp }

                        allConversations = combinedList.toMutableList()
                        conversationAdapter.submitList(allConversations)
                    }
            }
    }

    fun filterUsers(query: String?) {
        val filteredList = if (query.isNullOrBlank()) {
            allConversations
        } else {
            allConversations.filter { conversation ->
                when (conversation) {
                    is Conversation.ChatSessionConversation ->
                        conversation.otherUser?.username?.contains(query, ignoreCase = true) == true
                    is Conversation.GroupConversation ->
                        conversation.group.name?.contains(query, ignoreCase = true) == true
                }
            }
        }
        conversationAdapter.submitList(filteredList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
