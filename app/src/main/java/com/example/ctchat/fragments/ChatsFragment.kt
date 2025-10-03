package com.example.ctchat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ctchat.adapters.UsersAdapter
import com.example.ctchat.databinding.FragmentChatsBinding
import com.example.ctchat.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class ChatsFragment : Fragment() {

    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!
    private lateinit var usersAdapter: UsersAdapter
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var allUsers = mutableListOf<User>()

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
        listenForUsers()
    }

    private fun setupRecyclerView() {
        usersAdapter = UsersAdapter()
        binding.usersRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = usersAdapter
        }
    }

    private fun listenForUsers() {
        val currentUserId = auth.currentUser?.uid
        db.collection("users")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                allUsers.clear()
                snapshot?.documents?.forEach { document ->
                    val user = document.toObject<User>()
                    if (user != null && user.uid != currentUserId) {
                        allUsers.add(user)
                    }
                }
                usersAdapter.submitList(allUsers)
            }
    }

    fun filterUsers(query: String?) {
        if (query.isNullOrBlank()) {
            usersAdapter.submitList(allUsers)
        } else {
            val filteredList = allUsers.filter {
                it.username?.contains(query, ignoreCase = true) == true ||
                        it.email?.contains(query, ignoreCase = true) == true
            }
            usersAdapter.submitList(filteredList)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}