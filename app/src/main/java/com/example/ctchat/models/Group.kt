package com.example.ctchat.models

data class Group(
    val groupId: String? = null,
    val name: String? = null,
    val members: List<String> = emptyList()
) {
    constructor() : this(null, null, emptyList())
}