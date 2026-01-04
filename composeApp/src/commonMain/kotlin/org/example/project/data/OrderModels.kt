package org.example.project.data

data class OrderItem(
    val label: String,
    val price: String,
    val qty: Int = 1,
)

data class FeeItem(
    val label: String,
    val price: String,
)

data class OrderSummary(
    val id: String,
    val items: List<OrderItem>,
    val fees: List<FeeItem>,
    val total: String,
)
