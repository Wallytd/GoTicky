package org.example.project.platform

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import org.example.project.data.TicketPass

@Composable
actual fun rememberTicketShareAction(ticket: TicketPass): () -> Unit {
    val context = LocalContext.current
    val shareText = remember(ticket) {
        buildString {
            appendLine("Ticket: ${ticket.eventTitle}")
            appendLine("Venue: ${ticket.venue}")
            appendLine("When: ${ticket.dateLabel}")
            appendLine("Seat: ${ticket.seat}")
            appendLine("Status: ${ticket.status}")
        }
    }
    return remember(context, shareText) {
        {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Share your ticket")
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            val chooser = Intent.createChooser(intent, "Share ticket")
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooser)
        }
    }
}
