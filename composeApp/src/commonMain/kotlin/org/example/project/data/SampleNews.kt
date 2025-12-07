package org.example.project.data

import org.example.project.ui.theme.IconCategory

data class EntertainmentNewsItem(
    val id: String,
    val title: String,
    val summary: String,
    val source: String,
    val tag: String,
    val minutesAgo: Int,
    val category: IconCategory,
)

val sampleEntertainmentNews = listOf(
    EntertainmentNewsItem(
        id = "n1",
        title = "Neon Fest after-movie drops tonight",
        summary = "Relive last weekend's Midnight Neon Fest with a behind-the-scenes cut and surprise guest moments.",
        source = "GoTicky Studio",
        tag = "Highlights",
        minutesAgo = 12,
        category = IconCategory.Discover,
    ),
    EntertainmentNewsItem(
        id = "n2",
        title = "Courtside Classics adds VIP warm‑up access",
        summary = "A limited batch of floor passes unlocks pre-game tunnel access and on-court photo ops.",
        source = "League Insider",
        tag = "Sports",
        minutesAgo = 27,
        category = IconCategory.Calendar,
    ),
    EntertainmentNewsItem(
        id = "n3",
        title = "Laugh Lab Live announces midnight encore",
        summary = "Extra late show added after the first run sold out in under 6 minutes.",
        source = "Comedy Wire",
        tag = "Comedy",
        minutesAgo = 43,
        category = IconCategory.Alerts,
    ),
    EntertainmentNewsItem(
        id = "n4",
        title = "Family Lights Parade goes fully cashless",
        summary = "Scan your GoTicky code at food stalls, merch, and pop-up art installations along the route.",
        source = "City Desk",
        tag = "Family",
        minutesAgo = 65,
        category = IconCategory.Profile,
    ),
)
