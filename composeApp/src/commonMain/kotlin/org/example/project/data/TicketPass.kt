package org.example.project.data

/**
 * Represents a purchased or generated ticket pass for the user.
 */
enum class TicketType {
    EarlyBird,
    General,
    VIP,
    GoldenCircle,
}

data class TicketPass(
    val id: String,
    val eventTitle: String,
    val venue: String,
    val dateLabel: String,
    val seat: String,
    val status: String,
    val type: TicketType,
    val holderName: String,
    val holderInitials: String,
    val qrSeed: String,
)
