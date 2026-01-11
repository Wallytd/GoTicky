package org.example.project.data

// Lightweight sample order used across Checkout/Success screens when no live order is present.
val sampleOrder = OrderSummary(
    id = "ORD-1001",
    items = listOf(
        OrderItem(label = "Midnight Neon Fest • General / Standard", price = "$68.00", qty = 1),
        OrderItem(label = "Merch voucher", price = "$12.00", qty = 1)
    ),
    fees = listOf(
        FeeItem(label = "Service fee", price = "$6.20"),
        FeeItem(label = "Venue fee", price = "$2.10"),
    ),
    total = "$88.30"
)
