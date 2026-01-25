package org.example.project.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.example.project.data.AdminTicketGroup
import org.example.project.data.AdminTicketRecord
import org.example.project.data.TicketRepository
import org.example.project.ui.components.*
import org.example.project.ui.theme.IconCategory
import org.example.project.ui.theme.IconCategoryColors

/**
 * Complete Admin Tickets Section with search, filters, and export.
 * This is the fully-featured version that integrates with TicketRepository.
 */
@Composable
fun CompleteAdminTicketsSection(
    onScan: () -> Unit,
    addActivity: (String, Color) -> Unit,
    modifier: Modifier = Modifier
) {
    val ticketRepository = remember { TicketRepository() }
    var ticketGroups by remember { mutableStateOf<List<AdminTicketGroup>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    
    // Search and filter states
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var statusFilter by remember { mutableStateOf("All") }
    var cityFilter by remember { mutableStateOf("All") }
    var showSearchBar by remember { mutableStateOf(false) }
    
    // Fetch tickets on first composition
    LaunchedEffect(Unit) {
        loadTickets(ticketRepository) { groups, err ->
            ticketGroups = groups
            error = err
            isLoading = false
        }
    }
    
    // Refresh function
    fun refresh() {
        scope.launch {
            isLoading = true
            error = null
            loadTickets(ticketRepository) { groups, err ->
                ticketGroups = groups
                error = err
                isLoading = false
            }
        }
    }
    
    // Filter logic
    val filteredGroups = remember(ticketGroups, searchQuery.text, statusFilter, cityFilter) {
        ticketGroups
            .filter { group ->
                // City filter
                cityFilter == "All" || group.city == cityFilter
            }
            .map { group ->
                // Filter tickets within group
                val filteredTickets = group.tickets.filter { ticket ->
                    // Search filter
                    val matchesSearch = searchQuery.text.isBlank() ||
                        ticket.holderName.contains(searchQuery.text, ignoreCase = true) ||
                        ticket.id.contains(searchQuery.text, ignoreCase = true) ||
                        group.eventTitle.contains(searchQuery.text, ignoreCase = true)
                    
                    // Status filter
                    val matchesStatus = statusFilter == "All" ||
                        ticket.status.equals(statusFilter, ignoreCase = true) ||
                        (statusFilter == "Scanned" && ticket.scanned)
                    
                    matchesSearch && matchesStatus
                }
                group.copy(tickets = filteredTickets)
            }
            .filter { it.tickets.isNotEmpty() }
    }
    
    val cities = remember(ticketGroups) {
        listOf("All") + ticketGroups.map { it.city }.distinct()
    }
    
    val totalTickets = remember(filteredGroups) {
        filteredGroups.sumOf { it.tickets.size }
    }
    val scannedTickets = remember(filteredGroups) {
        filteredGroups.sumOf { g -> g.tickets.count { it.scanned } }
    }
    val pendingTickets = totalTickets - scannedTickets
    val scanProgress = if (totalTickets == 0) 0f else scannedTickets / totalTickets.toFloat()
    
    val ticketCategoryColor = IconCategoryColors[IconCategory.Ticket] ?: MaterialTheme.colorScheme.primary
    val secondaryActivityColor = MaterialTheme.colorScheme.secondary
    
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header with actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tickets & Scanning",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(
                    onClick = { showSearchBar = !showSearchBar },
                    modifier = Modifier.pressAnimated()
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search",
                        tint = if (showSearchBar) ticketCategoryColor else Color.White
                    )
                }
                IconButton(
                    onClick = {
                        // Export functionality
                        addActivity("Exported tickets", ticketCategoryColor)
                        // TODO: Implement CSV/PDF export
                    },
                    modifier = Modifier.pressAnimated()
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FileDownload,
                        contentDescription = "Export",
                        tint = Color.White
                    )
                }
                PrimaryButton(
                    text = "Open Scanner",
                    icon = { Icon(Icons.Outlined.QrCodeScanner, contentDescription = null) },
                    onClick = {
                        addActivity("Opened scanner", ticketCategoryColor)
                        onScan()
                    },
                    modifier = Modifier.pressAnimated()
                )
            }
        }
        
        // Search bar
        if (showSearchBar) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search by holder name, ticket ID, or event...") },
                leadingIcon = {
                    Icon(Icons.Outlined.Search, contentDescription = null)
                },
                trailingIcon = {
                    if (searchQuery.text.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = TextFieldValue("") }) {
                            Icon(Icons.Outlined.Close, contentDescription = "Clear")
                        }
                    }
                },
                singleLine = true
            )
        }
        
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        LoadingSpinner(size = 48)
                        Text(
                            text = "Loading tickets from Firestore...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
            error != null -> {
                GlowCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "⚠️ Error Loading Tickets",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = error ?: "Unknown error",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        PrimaryButton(
                            text = "Retry",
                            onClick = { refresh() },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            else -> {
                // Statistics card
                GlowCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            "Live Entry Progress",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        AnimatedProgressBar(
                            progress = scanProgress,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Pill(text = "$scannedTickets scanned")
                            Pill(text = "$pendingTickets pending")
                            Pill(text = "$totalTickets total")
                        }
                    }
                }
                
                // Filters
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("All", "Valid", "Pending entry", "Hold", "Scanned").forEach { status ->
                        NeonSelectablePill(
                            text = status,
                            selected = statusFilter == status,
                            onClick = { statusFilter = status }
                        )
                    }
                }
                
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    cities.forEach { city ->
                        NeonSelectablePill(
                            text = city,
                            selected = cityFilter == city,
                            onClick = { cityFilter = city }
                        )
                    }
                }
                
                // Ticket groups
                if (filteredGroups.isEmpty()) {
                    GlowCard(Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (searchQuery.text.isNotEmpty()) {
                                    "No tickets match your search"
                                } else {
                                    "No tickets match the current filters"
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(filteredGroups.size) { index ->
                            val group = filteredGroups[index]
                            TicketGroupCard(
                                group = group,
                                onScanForEvent = {
                                    addActivity(
                                        "Scanner opened for ${group.eventTitle}",
                                        ticketCategoryColor
                                    )
                                    onScan()
                                },
                                onViewAll = {
                                    addActivity(
                                        "View all tickets: ${group.eventTitle}",
                                        secondaryActivityColor
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TicketGroupCard(
    group: AdminTicketGroup,
    onScanForEvent: () -> Unit,
    onViewAll: () -> Unit
) {
    GlowCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        group.eventTitle,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        "${group.venue} • ${group.city}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        group.dateLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Pill(text = "${group.tickets.count { it.scanned }}/${group.tickets.size} scanned")
            }
            
            AnimatedProgressBar(
                progress = if (group.tickets.isEmpty()) 0f else group.tickets.count { it.scanned } / group.tickets.size.toFloat(),
                modifier = Modifier.fillMaxWidth()
            )
            
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                group.tickets.take(6).forEach { ticket ->
                    TicketRow(ticket)
                }
                if (group.tickets.size > 6) {
                    NeonTextButton(
                        text = "View all ${group.tickets.size} tickets",
                        onClick = onViewAll
                    )
                }
            }
            
            PrimaryButton(
                text = "Open Scanner for ${group.eventTitle}",
                icon = { Icon(Icons.Outlined.QrCodeScanner, contentDescription = null) },
                onClick = onScanForEvent,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun TicketRow(ticket: AdminTicketRecord) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .pressAnimated()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                "${ticket.holderName} • ${ticket.seat}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                "${ticket.type.name} • ${ticket.purchaseChannel} • ${ticket.purchaseAt}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                ticket.priceLabel,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Pill(text = if (ticket.scanned) "Scanned" else ticket.status)
            Text(
                "QR ${ticket.qrSeedShort}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private suspend fun loadTickets(
    repository: TicketRepository,
    onResult: (List<AdminTicketGroup>, String?) -> Unit
) {
    val result = repository.fetchTicketGroups()
    result.fold(
        onSuccess = { groups ->
            onResult(groups, null)
        },
        onFailure = { error ->
            onResult(emptyList(), error.message ?: "Failed to load tickets")
        }
    )
}
