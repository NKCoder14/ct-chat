package com.example.ctchat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ctchat.databinding.ItemUserSelectableBinding
import com.example.ctchat.models.User

class SelectUserAdapter : ListAdapter<User, SelectUserAdapter.UserViewHolder>(UserDiffCallback()) {
    private val selectedUserIds = mutableSetOf<String>()

    fun getSelectedUserIds(): Set<String> {
        return selectedUserIds
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserSelectableBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(private val binding: ItemUserSelectableBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.tvUsername.text = user.username
            binding.checkboxSelectUser.isChecked = selectedUserIds.contains(user.uid)

            binding.checkboxSelectUser.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    user.uid?.let { selectedUserIds.add(it) }
                } else {
                    user.uid?.let { selectedUserIds.remove(it) }
                }
            }

            itemView.setOnClickListener {
                binding.checkboxSelectUser.isChecked = !binding.checkboxSelectUser.isChecked
            }
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}
