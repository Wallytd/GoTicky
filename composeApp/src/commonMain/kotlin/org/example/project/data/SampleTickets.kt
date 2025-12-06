package org.example.project.data

data class TicketPass(
    val id: String,
    val eventTitle: String,
    val venue: String,
    val dateLabel: String,
    val seat: String,
    val status: String,
    val qrCodePlaceholder: String = "QR",
)

data class OrderSummary(
    val id: String,
    val items: List<OrderItem>,
    val fees: List<FeeItem>,
    val total: String
)

data class OrderItem(
    val label: String,
    val price: String,
    val qty: Int = 1
)

data class FeeItem(
    val label: String,
    val price: String
)

val sampleTickets = listOf(
    TicketPass(
        id = "T1",
        eventTitle = "Midnight Neon Fest",
        venue = "LA Live",
        dateLabel = "Tonight • 9:00 PM",
        seat = "Section A • Row 2 • Seat 4",
        status = "Ready"
    ),
    TicketPass(
        id = "T2",
        eventTitle = "Courtside Classics",
        venue = "MSG",
        dateLabel = "Sat • 7:30 PM",
        seat = "Courtside • Seat 12",
        status = "Ready"
    )
)

val sampleOrder = OrderSummary(
    id = "ORD-2048",
    items = listOf(
        OrderItem("Midnight Neon Fest • GA", "$68", 2),
        OrderItem("VIP Lounge Add-on", "$30", 1)
    ),
    fees = listOf(
        FeeItem("Service fee", "$12"),
        FeeItem("Venue fee", "$6")
    ),
    total = "$184"
)
