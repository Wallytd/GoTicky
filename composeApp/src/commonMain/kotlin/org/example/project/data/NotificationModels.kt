package org.example.project.data

data class NotificationItem(
    val id: String,
    val userId: String,
    val title: String,
    val body: String,
    val type: String,
    val eventId: String? = null,
    val createdAt: String,
    val readAt: String? = null,
    val status: String = "unread",
    val actionUrl: String? = null,
    val icon: String? = null,
)
