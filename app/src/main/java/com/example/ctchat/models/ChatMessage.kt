package com.example.ctchat.models

data class ChatMessage(
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    val timestamp: Long = 0
) {
    constructor() : this("", "", "", 0)
}