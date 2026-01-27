package org.example.project.platform

import androidx.compose.runtime.Composable
import org.example.project.data.TicketPass

// Provides a platform-aware share action for tickets.
// Actual implementations live in androidMain and jvmMain.
@Composable
expect fun rememberTicketShareAction(ticket: TicketPass): () -> Unit
