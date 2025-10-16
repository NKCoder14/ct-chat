package com.example.ctchat.models

data class Group(
    val groupId: String? = null,
    val name: String? = null,
    val members: List<String> = emptyList(),
    val lastMessage: String? = null,
    val lastMessageTimestamp: Long = 0
) {
    constructor() : this(null, null, emptyList(), null, 0)
}
