package com.example.ctchat.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
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
import java.text.SimpleDateFormat
import java.util.*

class ConversationAdapter(private val context: Context) :
    ListAdapter<Conversation, RecyclerView.ViewHolder>(ConversationDiffCallback()) {

    private val VIEW_TYPE_USER = 1
    private val VIEW_TYPE_GROUP = 2

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Conversation.ChatSessionConversation -> VIEW_TYPE_USER
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
            is Conversation.ChatSessionConversation -> (holder as UserViewHolder).bind(conversation)
            is Conversation.GroupConversation -> (holder as GroupViewHolder).bind(conversation)
        }
    }

    private fun formatTimestamp(timestamp: Long): String {
        if (timestamp == 0L) return ""

        val messageCalendar = Calendar.getInstance().apply { timeInMillis = timestamp }
        val now = Calendar.getInstance()

        return if (now.get(Calendar.YEAR) == messageCalendar.get(Calendar.YEAR) &&
            now.get(Calendar.DAY_OF_YEAR) == messageCalendar.get(Calendar.DAY_OF_YEAR)) {
            SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date(timestamp))
        } else {
            SimpleDateFormat("MMM d", Locale.getDefault()).format(Date(timestamp))
        }
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(conversation: Conversation.ChatSessionConversation) {
            binding.tvUsername.text = conversation.otherUser?.username
            Glide.with(context).load(R.drawable.ic_profile).into(binding.imgProfile)

            // --- FIX IS HERE: Handle users with no message history ---
            if (conversation.session.lastMessage.isNullOrEmpty()) {
                binding.tvLastMessage.visibility = View.GONE
                binding.tvTimestamp.visibility = View.GONE
            } else {
                binding.tvLastMessage.visibility = View.VISIBLE
                binding.tvTimestamp.visibility = View.VISIBLE
                binding.tvLastMessage.text = conversation.session.lastMessage
                binding.tvTimestamp.text = formatTimestamp(conversation.session.lastMessageTimestamp)
            }

            itemView.setOnClickListener {
                val intent = Intent(context, ChatActivity::class.java).apply {
                    putExtra("USER_ID", conversation.otherUser?.uid)
                    putExtra("USER_NAME", conversation.otherUser?.username)
                }
                context.startActivity(intent)
            }
        }
    }

    inner class GroupViewHolder(private val binding: ItemGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(conversation: Conversation.GroupConversation) {
            binding.tvGroupName.text = conversation.group.name

            if (conversation.group.lastMessage.isNullOrEmpty()) {
                binding.tvLastMessage.visibility = View.GONE
                binding.tvTimestamp.visibility = View.GONE
            } else {
                binding.tvLastMessage.visibility = View.VISIBLE
                binding.tvTimestamp.visibility = View.VISIBLE
                binding.tvLastMessage.text = conversation.group.lastMessage
                binding.tvTimestamp.text = formatTimestamp(conversation.group.lastMessageTimestamp)
            }

            itemView.setOnClickListener {
                val intent = Intent(context, GroupChatActivity::class.java).apply {
                    putExtra("GROUP_ID", conversation.group.groupId)
                    putExtra("GROUP_NAME", conversation.group.name)
                }
                context.startActivity(intent)
            }
        }
    }
}

class ConversationDiffCallback : DiffUtil.ItemCallback<Conversation>() {
    override fun areItemsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
        return when {
            oldItem is Conversation.ChatSessionConversation && newItem is Conversation.ChatSessionConversation ->
                oldItem.otherUser?.uid == newItem.otherUser?.uid
            oldItem is Conversation.GroupConversation && newItem is Conversation.GroupConversation ->
                oldItem.group.groupId == newItem.group.groupId
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
        return oldItem == newItem
    }
}
