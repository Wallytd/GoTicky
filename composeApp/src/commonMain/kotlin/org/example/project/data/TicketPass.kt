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
    val city: String = "",
    val country: String = "",
    val dateLabel: String,
    val seat: String,
    val status: String,
    val type: TicketType,
    val holderName: String,
    val holderInitials: String,
    val qrSeed: String,
    val purchaseAt: String? = null,
    // Scan tracking fields
    val eventId: String = "",
    val ownerId: String = "",
    val orderId: String? = null,
    val scanned: Boolean = false,
    val scannedAt: String? = null,
    val scannedBy: String? = null,
    val scanCount: Int = 0,
    val scanProhibited: Boolean = false,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
