package com.example.ctchat.models

sealed class Conversation {
    abstract val lastMessageTimestamp: Long
    data class ChatSessionConversation(val session: ChatSession, val otherUser: User?) : Conversation() {
        override val lastMessageTimestamp: Long get() = session.lastMessageTimestamp
    }
    data class GroupConversation(val group: Group) : Conversation() {
        override val lastMessageTimestamp: Long get() = group.lastMessageTimestamp
    }
}
