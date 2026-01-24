package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.browser.window
import org.example.project.data.TicketPass

// JS/Web implementation for sharing ticket details. Uses Web Share API when available,
// falls back to clipboard + alert otherwise.
@Composable
actual fun rememberTicketShareAction(ticket: TicketPass): () -> Unit {
    val shareText = remember(ticket) {
        buildString {
            appendLine("Ticket: ${ticket.eventTitle}")
            appendLine("Venue: ${ticket.venue}")
            appendLine("When: ${ticket.dateLabel}")
            appendLine("Seat: ${ticket.seat}")
            appendLine("Status: ${ticket.status}")
        }
    }

    return remember(shareText) {
        {
            val navigator = window.navigator.asDynamic()
            when {
                navigator.share != undefined -> {
                    val payload = js("{}")
                    payload.text = shareText
                    navigator.share(payload)
                }
                navigator.clipboard != undefined -> {
                    navigator.clipboard.writeText(shareText)
                    window.alert("Copied ticket details to clipboard")
                }
                else -> window.alert(shareText)
            }
        }
    }
}
