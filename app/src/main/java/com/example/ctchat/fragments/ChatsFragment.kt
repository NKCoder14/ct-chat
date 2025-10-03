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
import com.example.ctchat.models.Group
import com.example.ctchat.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatsFragment : Fragment() {

    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!
    private lateinit var conversationAdapter: ConversationAdapter
    private var allConversations = mutableListOf<Conversation>()

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
        fetchConversations()
    }

    private fun setupRecyclerView() {
        conversationAdapter = ConversationAdapter(requireContext())
        binding.usersRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = conversationAdapter
        }
    }

    private fun fetchConversations() {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("users").addSnapshotListener { userSnapshot, _ ->
            val users = userSnapshot?.toObjects(User::class.java)
                ?.filter { it.uid != currentUserId }
                ?.map { Conversation.UserConversation(it) } ?: emptyList()

            fetchGroups(currentUserId, users)
        }
    }

    private fun fetchGroups(currentUserId: String, users: List<Conversation>) {
        FirebaseFirestore.getInstance().collection("groups")
            .whereArrayContains("members", currentUserId)
            .addSnapshotListener { groupSnapshot, _ ->
                val groups = groupSnapshot?.toObjects(Group::class.java)
                    ?.map { Conversation.GroupConversation(it) } ?: emptyList()

                allConversations = (groups + users).toMutableList()
                conversationAdapter.submitList(allConversations)
            }
    }


    fun filterUsers(query: String?) {
        val filteredList = if (query.isNullOrBlank()) {
            allConversations
        } else {
            allConversations.filter { conversation ->
                when (conversation) {
                    is Conversation.UserConversation ->
                        conversation.user.username?.contains(query, ignoreCase = true) == true ||
                                conversation.user.email?.contains(query, ignoreCase = true) == true
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