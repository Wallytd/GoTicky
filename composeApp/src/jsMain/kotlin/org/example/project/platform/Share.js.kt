package org.example.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.example.project.data.TicketPass

@Composable
actual fun rememberTicketShareAction(ticket: TicketPass): () -> Unit {
    // JS/web stub: no native share; return a no-op handler.
    return remember { { /* no-op */ } }
}
