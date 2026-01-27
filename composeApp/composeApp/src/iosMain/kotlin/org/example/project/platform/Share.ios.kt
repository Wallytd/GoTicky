package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.example.project.data.TicketPass
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

@Composable
actual fun rememberTicketShareAction(ticket: TicketPass): () -> Unit {
    val shareText = remember(ticket) {
        buildString {
            appendLine("Ticket: ${ticket.eventTitle}")
            appendLine("When: ${ticket.dateLabel}")
            appendLine("Where: ${ticket.venue}")
            appendLine("Holder: ${ticket.holderName} (${ticket.holderInitials})")
            append("Status: ${ticket.status}")
        }
    }

    return remember(shareText) {
        {
            val controller = topViewController() ?: return@remember
            val activityItems = listOf(shareText)
            val activityVC = UIActivityViewController(activityItems = activityItems, applicationActivities = null)
            controller.presentViewController(activityVC, animated = true, completion = null)
        }
    }
}

private fun topViewController(controller: UIViewController? = UIApplication.sharedApplication.keyWindow?.rootViewController): UIViewController? {
    var current = controller
    while (current?.presentedViewController != null) {
        current = current.presentedViewController
    }
    return current
}
