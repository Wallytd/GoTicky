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
    val imagePath: String? = null,
)

val sampleEvents = listOf(
    EventItem(
        id = "1",
        title = "Midnight Neon Fest",
        city = "Harare",
        dateLabel = "Tonight • 9:00 PM",
        priceFrom = "From $68",
        category = IconCategory.Discover,
        badge = "Hot",
        tag = "EDM",
        month = "January",
        imagePath = "midnight_neon_fest",
    ),
    EventItem(
        id = "2",
        title = "Courtside Classics",
        city = "Bulawayo",
        dateLabel = "Sat • 7:30 PM",
        priceFrom = "From $120",
        category = IconCategory.Calendar,
        badge = "Limited",
        tag = "Basketball",
        month = "January",
        imagePath = "courtside_classics",
    ),
    EventItem(
        id = "3",
        title = "Laugh Lab Live",
        city = "Gaborone",
        dateLabel = "Fri • 8:00 PM",
        priceFrom = "From $42",
        category = IconCategory.Alerts,
        badge = "Trending",
        tag = "Comedy",
        month = "February",
        imagePath = "laugh_lab_live",
    ),
    EventItem(
        id = "4",
        title = "Family Lights Parade",
        city = "Victoria Falls",
        dateLabel = "Sun • 6:00 PM",
        priceFrom = "From $30",
        category = IconCategory.Profile,
        badge = "Family",
        tag = "Outdoors",
        month = "March",
        imagePath = "family_lights_parade",
    ),
    EventItem(
        id = "5",
        title = "Savanna Sky Sessions",
        city = "Maun",
        dateLabel = "Next Sat • 8:30 PM",
        priceFrom = "From $55",
        category = IconCategory.Discover,
        badge = "New",
        tag = "Live band",
        month = "April",
        imagePath = "savanna_sky_sessions",
    ),
    EventItem(
        id = "6",
        title = "Francistown Street Vibes",
        city = "Francistown",
        dateLabel = "Fri • 7:00 PM",
        priceFrom = "From $25",
        category = IconCategory.Calendar,
        badge = "Local",
        tag = "Festival",
        month = "April",
        imagePath = "francistown_street_vibes",
    )
)

data class DistanceSample(
    val eventId: String,
    val fromLabel: String,
    val distanceKm: Double,
) {
    val formatted: String
        get() = "${((distanceKm * 10.0).toInt() / 10.0)} km away"
}

data class NearbyEvent(
    val event: EventItem,
    val distance: DistanceSample,
)

val sampleNearbyEvents: List<NearbyEvent> = run {
    // TODO: Replace with real location-based distances once location services and backend are wired.
    val samples = listOf(
        DistanceSample(
            eventId = "1",
            fromLabel = "Harare CBD (sample)",
            distanceKm = 1.2,
        ),
        DistanceSample(
            eventId = "2",
            fromLabel = "Harare CBD (sample)",
            distanceKm = 3.8,
        ),
        DistanceSample(
            eventId = "3",
            fromLabel = "Harare CBD (sample)",
            distanceKm = 7.5,
        ),
        DistanceSample(
            eventId = "4",
            fromLabel = "Harare CBD (sample)",
            distanceKm = 12.0,
        ),
        DistanceSample(
            eventId = "5",
            fromLabel = "Maun Centre (sample)",
            distanceKm = 2.5,
        ),
        DistanceSample(
            eventId = "6",
            fromLabel = "Francistown Centre (sample)",
            distanceKm = 4.1,
        ),
    )
    samples.mapNotNull { sample ->
        val event = sampleEvents.firstOrNull { it.id == sample.eventId }
        if (event != null) {
            NearbyEvent(event = event, distance = sample)
        } else {
            null
        }
    }
}
