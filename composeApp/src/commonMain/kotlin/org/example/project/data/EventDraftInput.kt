package org.example.project.data

data class EventDraftInput(
    val title: String,
    val city: String,
    val venue: String,
    val dateLabel: String,
    val country: String,
    val priceFrom: String,
    val status: String,
    val isApproved: Boolean = false,
    val ticketCount: Int,
    val ticketEarlyBird: String,
    val ticketGeneral: String,
    val ticketVip: String,
    val ticketStudent: String,
    val flyerUrl: String,
    val companyName: String,
    val lat: Double?,
    val lng: Double?,
)
