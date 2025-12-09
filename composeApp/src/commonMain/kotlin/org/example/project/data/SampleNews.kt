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
    val imageKey: String? = null,
)

val sampleEntertainmentNews = listOf(
    EntertainmentNewsItem(
        id = "n1",
        title = "Harare NYE countdown almost sold out",
        summary = "Final Golden Circle tickets for HICC New Year’s Eve concert are disappearing fast this week.",
        source = "Harare Live",
        tag = "Concert",
        minutesAgo = 9,
        category = IconCategory.Discover,
        imageKey = "news_harare_nye",
    ),
    EntertainmentNewsItem(
        id = "n2",
        title = "Vic Falls carnival adds Zim dancehall stage",
        summary = "New late-night stage confirmed for Victoria Falls carnival with top Zim dancehall and amapiano acts.",
        source = "ZimEvents",
        tag = "Festival",
        minutesAgo = 24,
        category = IconCategory.Calendar,
        imageKey = "news_vic_falls_carnival",
    ),
    EntertainmentNewsItem(
        id = "n3",
        title = "Gaborone comedy double-header announced",
        summary = "Botswana’s top comics join a new late show at GICC after the first night sold out.",
        source = "BW Tonight",
        tag = "Comedy",
        minutesAgo = 38,
        category = IconCategory.Alerts,
        imageKey = "news_gaborone_comedy",
    ),
    EntertainmentNewsItem(
        id = "n4",
        title = "Bulawayo night market extends summer run",
        summary = "Food, live art and DJs at the Bulawayo street market confirmed for extra weekends this season.",
        source = "Byo Culture Desk",
        tag = "Lifestyle",
        minutesAgo = 61,
        category = IconCategory.Profile,
        imageKey = "news_byo_night_market",
    ),
)
