package com.example.ctchat.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ctchat.R
import com.example.ctchat.activities.ChatActivity
import com.example.ctchat.activities.GroupChatActivity
import com.example.ctchat.databinding.ItemGroupBinding
import com.example.ctchat.databinding.ItemUserBinding
import com.example.ctchat.models.Conversation

class ConversationAdapter(private val context: Context) :
    ListAdapter<Conversation, RecyclerView.ViewHolder>(ConversationDiffCallback()) {

    private val VIEW_TYPE_USER = 1
    private val VIEW_TYPE_GROUP = 2

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Conversation.UserConversation -> VIEW_TYPE_USER
            is Conversation.GroupConversation -> VIEW_TYPE_GROUP
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_USER -> {
                val binding = ItemUserBinding.inflate(inflater, parent, false)
                UserViewHolder(binding)
            }
            VIEW_TYPE_GROUP -> {
                val binding = ItemGroupBinding.inflate(inflater, parent, false)
                GroupViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val conversation = getItem(position)) {
            is Conversation.UserConversation -> (holder as UserViewHolder).bind(conversation)
            is Conversation.GroupConversation -> (holder as GroupViewHolder).bind(conversation)
        }
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(conversation: Conversation.UserConversation) {
            val user = conversation.user
            binding.tvUsername.text = user.username
            Glide.with(context).load(R.drawable.ic_profile).into(binding.imgProfile)

            itemView.setOnClickListener {
                val intent = Intent(context, ChatActivity::class.java).apply {
                    putExtra("USER_ID", user.uid)
                    putExtra("USER_NAME", user.username)
                }
                context.startActivity(intent)
            }
        }
    }

    inner class GroupViewHolder(private val binding: ItemGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(conversation: Conversation.GroupConversation) {
            val group = conversation.group
            binding.tvGroupName.text = group.name

            itemView.setOnClickListener {
                val intent = Intent(context, GroupChatActivity::class.java).apply {
                    putExtra("GROUP_ID", group.groupId)
                    putExtra("GROUP_NAME", group.name)
                }
                context.startActivity(intent)
            }
        }
    }
}

class ConversationDiffCallback : DiffUtil.ItemCallback<Conversation>() {
    override fun areItemsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
        return when {
            oldItem is Conversation.UserConversation && newItem is Conversation.UserConversation ->
                oldItem.user.uid == newItem.user.uid
            oldItem is Conversation.GroupConversation && newItem is Conversation.GroupConversation ->
                oldItem.group.groupId == newItem.group.groupId
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
        return oldItem == newItem
    }
}