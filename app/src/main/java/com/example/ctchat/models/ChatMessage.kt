package com.example.ctchat.models

data class ChatMessage(
    val senderId: String? = null,
    val senderName: String? = null,
    val text: String? = null,
    val timestamp: Long = 0
) {
    constructor() : this(null, null, null, 0)
}