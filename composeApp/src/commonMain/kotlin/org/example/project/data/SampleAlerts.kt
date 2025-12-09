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
    val imageKey: String? = null,
)

val sampleAlerts = listOf(
    PriceAlert(
        id = "alert-1",
        eventId = "1",
        title = "Midnight Neon Fest",
        venue = "HICC • Harare CBD",
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
        venue = "Barbourfields Stadium • Bulawayo",
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
        venue = "GICC • Gaborone",
        section = "Center Mezz • Row G",
        targetPrice = 75,
        currentPrice = 78,
        dropPercent = 5,
        expiresInDays = 2,
        status = "Near target"
    ),
    PriceAlert(
        id = "alert-4",
        eventId = "5",
        title = "Savanna Sky Sessions",
        venue = "Maun Riverside Stage • Maun",
        section = "Sky Deck • Row 1",
        targetPrice = 90,
        currentPrice = 105,
        dropPercent = 15,
        expiresInDays = 4,
        status = "Watching"
    ),
    PriceAlert(
        id = "alert-5",
        eventId = "6",
        title = "Francistown Street Vibes",
        venue = "Francistown Civic Centre • Francistown",
        section = "GA • Near stage",
        targetPrice = 30,
        currentPrice = 34,
        dropPercent = 10,
        expiresInDays = 5,
        status = "Active drop"
    )
)

val sampleRecommendations = listOf(
    Recommendation(
        id = "rec-1",
        eventId = "1",
        title = "Midnight Neon Fest",
        reason = "Friends nearby • EDM • Past purchases",
        tag = "For you",
        city = "Harare",
        priceFrom = "From $68"
    ),
    Recommendation(
        id = "rec-2",
        eventId = "4",
        title = "Family Lights Parade",
        reason = "Family-friendly • Outdoors • Weekend",
        tag = "Family pick",
        city = "Victoria Falls",
        priceFrom = "From $30"
    ),
    Recommendation(
        id = "rec-3",
        eventId = "2",
        title = "Courtside Classics",
        reason = "High value seat score • Price watch set",
        tag = "Trending",
        city = "Bulawayo",
        priceFrom = "From $120"
    ),
    Recommendation(
        id = "rec-4",
        eventId = "5",
        title = "Savanna Sky Sessions",
        reason = "Concerts • Sunset views • Live band",
        tag = "Concerts",
        city = "Maun",
        priceFrom = "From $55"
    ),
    Recommendation(
        id = "rec-5",
        eventId = "6",
        title = "Francistown Street Vibes",
        reason = "Festival • Local DJs • Street food",
        tag = "Festival",
        city = "Francistown",
        priceFrom = "From $25"
    )
)