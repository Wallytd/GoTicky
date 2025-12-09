package org.example.project

// Small cross-platform abstraction for current local hour of day.
// Implemented per-platform in *Main source sets.
expect fun currentHourOfDay(): Int
