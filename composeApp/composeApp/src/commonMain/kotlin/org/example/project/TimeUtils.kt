package org.example.project

import kotlinx.datetime.Instant

// Small cross-platform abstractions for current local time.
// Implemented per-platform in *Main source sets.
expect fun currentHourOfDay(): Int

// Returns the current wall-clock instant in UTC.
@kotlin.OptIn(kotlin.time.ExperimentalTime::class)
expect fun currentInstant(): Instant

// Formatting helpers used by UI to present timestamps consistently across targets.
expect fun formatTime(timestamp: Long): String
expect fun formatDate(timestamp: Long): String
