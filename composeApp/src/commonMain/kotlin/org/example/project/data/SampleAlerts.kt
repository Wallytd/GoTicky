package org.example.project.data

data class PriceAlert(
    val id: String,
    val eventId: String,
    val title: String,
    val venue: String,
    val section: String,
    val targetPrice: Int,
    val currentPrice: Int,
    val dropPercent: Int,
    val expiresInDays: Int,
    val status: String,
    val delivery: String = "Push + Email",
)

data class Recommendation(
    val id: String,
    val eventId: String,
    val title: String,
    val reason: String,
    val tag: String,
    val city: String,
    val priceFrom: String,
)

val sampleAlerts = listOf(
    PriceAlert(
        id = "alert-1",
        eventId = "1",
        title = "Midnight Neon Fest",
        venue = "LA Live • Downtown",
        section = "Lower Bowl A • Row 3",
        targetPrice = 120,
        currentPrice = 138,
        dropPercent = 12,
        expiresInDays = 6,
        status = "Watching"
    ),
    PriceAlert(
        id = "alert-2",
        eventId = "2",
        title = "Courtside Classics",
        venue = "MSG • New York",
        section = "Courtside • Seat 12",
        targetPrice = 480,
        currentPrice = 520,
        dropPercent = 8,
        expiresInDays = 3,
        status = "Active drop"
    ),
    PriceAlert(
        id = "alert-3",
        eventId = "3",
        title = "Laugh Lab Live",
        venue = "Chicago Theater",
        section = "Center Mezz • Row G",
        targetPrice = 75,
        currentPrice = 78,
        dropPercent = 5,
        expiresInDays = 2,
        status = "Near target"
    )
)

val sampleRecommendations = listOf(
    Recommendation(
        id = "rec-1",
        eventId = "1",
        title = "Midnight Neon Fest",
        reason = "Friends nearby • EDM • Past purchases",
        tag = "For you",
        city = "Los Angeles",
        priceFrom = "From $68"
    ),
    Recommendation(
        id = "rec-2",
        eventId = "4",
        title = "Family Lights Parade",
        reason = "Family-friendly • Outdoors • Weekend",
        tag = "Family pick",
        city = "San Francisco",
        priceFrom = "From $30"
    ),
    Recommendation(
        id = "rec-3",
        eventId = "2",
        title = "Courtside Classics",
        reason = "High value seat score • Price watch set",
        tag = "Trending",
        city = "New York",
        priceFrom = "From $120"
    )
)