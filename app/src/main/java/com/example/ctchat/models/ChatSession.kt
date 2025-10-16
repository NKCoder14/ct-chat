package com.example.ctchat.models

data class ChatSession(
    val chatRoomId: String? = null,
    val members: List<String> = emptyList(),
    val lastMessage: String? = null,
    val lastMessageTimestamp: Long = 0
) {
    constructor() : this(null, emptyList(), null, 0)
}
