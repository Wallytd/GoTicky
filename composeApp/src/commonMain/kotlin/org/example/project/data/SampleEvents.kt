package org.example.project.data

import org.example.project.ui.theme.IconCategory

data class EventItem(
    val id: String,
    val title: String,
    val city: String,
    val dateLabel: String,
    val priceFrom: String,
    val category: IconCategory,
    val badge: String? = null,
    val tag: String? = null,
    val month: String = "January",
)

val sampleEvents = listOf(
    EventItem(
        id = "1",
        title = "Midnight Neon Fest",
        city = "Los Angeles",
        dateLabel = "Tonight • 9:00 PM",
        priceFrom = "From $68",
        category = IconCategory.Discover,
        badge = "Hot",
        tag = "EDM",
        month = "January",
    ),
    EventItem(
        id = "2",
        title = "Courtside Classics",
        city = "New York",
        dateLabel = "Sat • 7:30 PM",
        priceFrom = "From $120",
        category = IconCategory.Calendar,
        badge = "Limited",
        tag = "Basketball",
        month = "January",
    ),
    EventItem(
        id = "3",
        title = "Laugh Lab Live",
        city = "Chicago",
        dateLabel = "Fri • 8:00 PM",
        priceFrom = "From $42",
        category = IconCategory.Alerts,
        badge = "Trending",
        tag = "Comedy",
        month = "February",
    ),
    EventItem(
        id = "4",
        title = "Family Lights Parade",
        city = "San Francisco",
        dateLabel = "Sun • 6:00 PM",
        priceFrom = "From $30",
        category = IconCategory.Profile,
        badge = "Family",
        tag = "Outdoors",
        month = "March",
    )
)
