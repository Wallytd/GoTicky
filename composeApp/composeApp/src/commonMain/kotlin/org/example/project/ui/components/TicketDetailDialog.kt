package org.example.project.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.example.project.data.TicketPass
import org.example.project.ui.theme.goTickyShapes

/**
 * Detailed ticket view dialog for admins.
 * Shows full ticket information, scan history, and admin actions.
 */
@Composable
fun TicketDetailDialog(
    ticket: TicketPass,
    onDismiss: () -> Unit,
    onManualCheckIn: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismiss) {
        GlowCard(
            modifier = modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.9f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header with close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ticket Details",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                
                Divider()
                
                // Ticket information
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DetailSection("Event Information") {
                        DetailItem("Event", ticket.eventTitle)
                        DetailItem("Venue", ticket.venue)
                        DetailItem("City", ticket.city)
                        DetailItem("Date", ticket.dateLabel)
                    }
                    
                    DetailSection("Ticket Information") {
                        DetailItem("Ticket ID", ticket.id)
                        DetailItem("Holder", ticket.holderName)
                        DetailItem("Seat", ticket.seat)
                        DetailItem("Type", ticket.type.name)
                        DetailItem("Status", ticket.status)
                    }
                    
                    if (ticket.scanned) {
                        DetailSection("Scan Information") {
                            DetailItem("Scanned", "Yes")
                            ticket.scannedAt?.let {
                                DetailItem("Scanned At", it)
                            }
                            ticket.scannedBy?.let {
                                DetailItem("Scanned By", it)
                            }
                            DetailItem("Scan Count", ticket.scanCount.toString())
                        }
                    }
                    
                    DetailSection("Purchase Information") {
                        ticket.purchaseAt?.let {
                            DetailItem("Purchased At", it)
                        }
                        ticket.orderId?.let {
                            DetailItem("Order ID", it)
                        }
                    }
                }
                
                // Actions
                if (!ticket.scanned && onManualCheckIn != null) {
                    PrimaryButton(
                        text = "Manual Check-In",
                        onClick = onManualCheckIn,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                
                GhostButton(
                    text = "Close",
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun DetailSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.primary
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            content = content
        )
    }
}

@Composable
private fun DetailItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}
