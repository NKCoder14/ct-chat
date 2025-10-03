package com.example.ctchat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ctchat.R
import com.example.ctchat.databinding.ItemUserSelectableBinding
import com.example.ctchat.models.User

class SelectUserAdapter : ListAdapter<User, SelectUserAdapter.UserViewHolder>(UserDiffCallback()) {

    private val selectedUsers = mutableSetOf<User>()

    fun getSelectedUsers(): List<User> {
        return selectedUsers.toList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding: ItemUserSelectableBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_user_selectable,
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class UserViewHolder(private val binding: ItemUserSelectableBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvUsername.text = user.username ?: user.email
            binding.checkbox.isChecked = selectedUsers.contains(user)

            itemView.setOnClickListener {
                binding.checkbox.isChecked = !binding.checkbox.isChecked
            }

            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedUsers.add(user)
                } else {
                    selectedUsers.remove(user)
                }
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