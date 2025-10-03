package com.example.ctchat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ctchat.R
import com.example.ctchat.databinding.ItemReceivedMessageBinding
import com.example.ctchat.databinding.ItemSentMessageBinding
import com.example.ctchat.models.ChatMessage
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(private val currentUserId: String) :
    ListAdapter<ChatMessage, RecyclerView.ViewHolder>(MessageDiffCallback()) {
    private val VIEW_TYPE_SENT = 1
    private val VIEW_TYPE_RECEIVED = 2

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return if (message.senderId == currentUserId) {
            VIEW_TYPE_SENT
        } else {
            VIEW_TYPE_RECEIVED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT) {
            val binding: ItemSentMessageBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_sent_message,
                parent,
                false
            )
            SentMessageViewHolder(binding)
        } else {
            val binding: ItemReceivedMessageBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_received_message,
                parent,
                false
            )
            ReceivedMessageViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        if (holder.itemViewType == VIEW_TYPE_SENT) {
            (holder as SentMessageViewHolder).bind(message)
        } else {
            (holder as ReceivedMessageViewHolder).bind(message)
        }
    }

    inner class SentMessageViewHolder(private val binding: ItemSentMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatMessage: ChatMessage) {
            binding.tvMessage.text = chatMessage.text
            binding.tvTimestamp.text = formatTimestamp(chatMessage.timestamp)
        }
    }

    inner class ReceivedMessageViewHolder(private val binding: ItemReceivedMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatMessage: ChatMessage) {
            binding.tvMessage.text = chatMessage.text
            binding.tvTimestamp.text = formatTimestamp(chatMessage.timestamp)
        }
    }

    private fun formatTimestamp(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("h:mm a", Locale.getDefault())
        return format.format(date)
    }

    class MessageDiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem.timestamp == newItem.timestamp
        }

        override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem == newItem
        }
    }
}