package org.example.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.OrderSummary
import org.example.project.data.TicketPass
import org.example.project.ui.theme.goTickyShapes

@Composable
fun TicketCard(
    ticket: TicketPass,
    onClick: () -> Unit
) {
    GlowCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(ticket.eventTitle, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text(ticket.venue, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Pill(
                    text = ticket.status,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.16f),
                    textColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                )
            }
            Text(ticket.dateLabel, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
            Text(ticket.seat, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                PrimaryButton(text = "Open", modifier = Modifier.weight(1f)) { onClick() }
                GhostButton(text = "Transfer", modifier = Modifier.weight(1f)) { onClick() }
            }
        }
    }
}

@Composable
fun TicketDetailScreen(
    ticket: TicketPass,
    onBack: () -> Unit,
    onAddToWallet: () -> Unit,
    onTransfer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        GlowCard(modifier = Modifier.fillMaxWidth()) {
            TopBar(
                title = ticket.eventTitle,
                onBack = onBack,
                actions = null,
                backgroundColor = Color.Transparent
            )
        }
        GlowCard {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(ticket.venue, style = MaterialTheme.typography.titleMedium)
                Text(ticket.dateLabel, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                Text(ticket.seat, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                StatusBadge(ticket.status)
                QRPlaceholder()
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    PrimaryButton(text = "Add to Wallet", modifier = Modifier.weight(1f)) { onAddToWallet() }
                    GhostButton(text = "Transfer", modifier = Modifier.weight(1f)) { onTransfer() }
                }
            }
        }
    }
}

@Composable
private fun StatusBadge(status: String) {
    Pill(
        text = status,
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.16f),
        textColor = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun QRPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clip(goTickyShapes.large)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            LoadingSpinner(size = 32)
            Text("Dynamic QR will appear here", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

private fun parsePrice(raw: String): Int = raw.filter { it.isDigit() }.toIntOrNull() ?: 0

@Composable
fun CheckoutScreen(
    order: OrderSummary,
    selectedTicketType: String,
    onBack: () -> Unit,
    onPlaceOrder: () -> Unit
) {
    var ticketQty by remember { mutableStateOf(1) }
    val baseTicketPrice = order.items.firstOrNull()?.let { parsePrice(it.price) } ?: 0
    val addOnsTotal = order.items.drop(1).sumOf { parsePrice(it.price) }
    val feesTotal = order.fees.sumOf { parsePrice(it.price) }
    val totalAmount = baseTicketPrice * ticketQty + addOnsTotal + feesTotal

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        GlowCard(modifier = Modifier.fillMaxWidth()) {
            TopBar(
                title = "Checkout",
                onBack = onBack,
                actions = null,
                backgroundColor = Color.Transparent
            )
        }
        GlowCard {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Review", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                Text(
                    text = "Selected: $selectedTicketType",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "Tickets",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Text(
                            "Adjust ticket count",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .pressAnimated()
                                .clickable(enabled = ticketQty > 1) {
                                    if (ticketQty > 1) ticketQty--
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.KeyboardArrowDown,
                                contentDescription = "Decrease tickets",
                                tint = if (ticketQty > 1) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            ticketQty.toString(),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                                .pressAnimated()
                                .clickable {
                                    ticketQty++
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.KeyboardArrowUp,
                                contentDescription = "Increase tickets",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
                order.items.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val displayQty = if (index == 0) ticketQty else item.qty
                        Text("${item.label} x$displayQty", style = MaterialTheme.typography.bodyMedium)
                        Text(item.price, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Spacer(Modifier.height(6.dp))
                order.fees.forEach {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(it.label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(it.price, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                Spacer(Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
                    Text("$" + totalAmount.toString(), style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.primary)
                }
                AnimatedProgressBar(progress = 0.66f, modifier = Modifier.fillMaxWidth())
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    PrimaryButton(text = "Place order", modifier = Modifier.weight(1f)) { onPlaceOrder() }
                    GhostButton(text = "Edit", modifier = Modifier.weight(1f)) { onBack() }
                }
            }
        }
    }
}
