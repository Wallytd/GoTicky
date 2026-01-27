@file:OptIn(kotlin.time.ExperimentalTime::class)

package org.example.project.data

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import org.example.project.ui.theme.IconCategory

data class NewsFlash(
    val id: String,
    val title: String,
    val summary: String,
    val source: String,
    val tag: String,
    val category: IconCategory,
    val accentHex: String? = null,
    val imageUrl: String? = null,
    val imageKey: String? = null,
    val ctaLabel: String? = null,
    val ctaLink: String? = null,
    val publishedAt: Instant,
    val expiresAt: Instant? = null,
    val priority: Int = 0,
    val pinned: Boolean = false,
    val status: String = "draft", // draft | scheduled | published | archived
    val region: String? = null,
    val locale: String? = null,
    val author: String? = null,
    val createdBy: String? = null,
    val updatedAt: Instant? = null,
    val impressions: Long = 0,
    val clicks: Long = 0,
)

fun NewsFlash.toEntertainmentNewsItem(now: Instant): EntertainmentNewsItem {
    val minutesAgo = ((now.toEpochMilliseconds() - publishedAt.toEpochMilliseconds()) / 60_000)
        .coerceAtLeast(0)
    return EntertainmentNewsItem(
        id = id,
        title = title,
        summary = summary,
        source = source,
        tag = tag,
        minutesAgo = minutesAgo.toInt(),
        category = category,
        accentHex = accentHex,
        imageUrl = this.imageUrl,
        imageKey = imageKey
    )
}

fun Instant.prettyDate(): String {
    val local = toLocalDateTime(TimeZone.currentSystemDefault())
    return "${local.year}-${local.monthNumber.toString().padStart(2, '0')}-${local.dayOfMonth.toString().padStart(2, '0')}"
}
