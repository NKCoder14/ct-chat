package com.example.ctchat.models

sealed class Conversation {
    data class UserConversation(val user: User) : Conversation()
    data class GroupConversation(val group: Group) : Conversation()
}
