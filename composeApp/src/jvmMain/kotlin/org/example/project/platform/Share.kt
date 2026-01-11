package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.example.project.data.TicketPass
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

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
            runCatching {
                val clipboard = Toolkit.getDefaultToolkit().systemClipboard
                val selection = StringSelection(shareText)
                clipboard.setContents(selection, selection)
            }
        }
    }
}
