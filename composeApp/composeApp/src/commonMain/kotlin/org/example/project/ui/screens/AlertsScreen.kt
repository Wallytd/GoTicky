package org.example.project.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Instant
import org.example.project.data.NotificationItem
import org.example.project.ui.components.*
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import androidx.compose.foundation.interaction.MutableInteractionSource
import kotlin.math.absoluteValue

@Composable
fun AlertsScreen(
    notifications: List<NotificationItem>,
    onBack: () -> Unit,
    onMarkRead: (NotificationItem) -> Unit,
    onToggleStar: (NotificationItem, Boolean) -> Unit
) {
    var selectedFilter by remember { mutableStateOf("Unread") }
    val listState = rememberLazyListState()

    // Filter logic
    val filteredNotifications = remember(notifications, selectedFilter) {
        when (selectedFilter) {
            "Unread" -> notifications.filter { it.status == "unread" }
            "Read" -> notifications.filter { it.status == "read" }
            "Starred" -> notifications.filter { it.starred }
            else -> notifications
        }
    }

    // Dynamic Title Logic
    val titleStart = when (selectedFilter) {
        "Unread" -> "New"
        "Read" -> "Past"
        "Starred" -> "Starred"
        else -> "Alerts"
    }

    // Show "Mark all read" only for Unread tab
    val showMarkAllRead = selectedFilter == "Unread"

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Static Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Interactive Animated Tabs
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                   AlertsTabRow(
                       selectedFilter = selectedFilter,
                       onSelect = { selectedFilter = it },
                       counts = mapOf(
                           "Unread" to notifications.count { it.status == "unread" },
                           "Read" to notifications.count { it.status == "read" },
                           "Starred" to notifications.count { it.starred }
                       )
                   )
                }

                // Title and Actions
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = titleStart,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    
                    // Count badge for the list - Fade transition on change
                    AnimatedContent(
                        targetState = filteredNotifications.size,
                        transitionSpec = { fadeIn() togetherWith fadeOut() }
                    ) { count ->
                        if (count > 0) {
                             Surface(
                                 color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
                                 shape = RoundedCornerShape(8.dp)
                             ) {
                                 Text(
                                     "$count ${selectedFilter.lowercase()}",
                                     style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                     color = MaterialTheme.colorScheme.primary,
                                     modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                 )
                             }
                        }
                    }

                    // Mark all read button - Only if applicable
                    if (showMarkAllRead) {
                        TextButton(onClick = { /* TODO: Mark all read */ }) {
                            Text("Mark all read", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                        }
                    } else {
                        Spacer(Modifier.width(1.dp)) // Maintain height
                    }
                }
            }

            // Scrollable List with Transition
            // Using AnimatedContent to cross-fade lists when changing filters
            AnimatedContent(
                targetState = selectedFilter,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(220, delayMillis = 90)) +
                            scaleIn(initialScale = 0.98f, animationSpec = tween(220, delayMillis = 90)))
                        .togetherWith(fadeOut(animationSpec = tween(90)))
                },
                modifier = Modifier.weight(1f)
            ) { currentFilter -> 
                
                // Recalculate for the currently animating block (crucial for correctness logic during transition)
                val currentList = when (currentFilter) {
                    "Unread" -> notifications.filter { it.status == "unread" }
                    "Read" -> notifications.filter { it.status == "read" }
                    "Starred" -> notifications.filter { it.starred }
                    else -> notifications
                }

                LazyColumn(
                     state = listState,
                     contentPadding = PaddingValues(bottom = 180.dp),
                     verticalArrangement = Arrangement.spacedBy(14.dp),
                     modifier = Modifier
                         .fillMaxSize()
                         .padding(horizontal = 16.dp)
                ) {
                    itemsIndexed(currentList, key = { _, item -> item.id }) { index, item ->
                        // Per-item entry animation (staggered)
                        // Triggered when the parent AnimatedContent composes this list
                        var visible by remember { mutableStateOf(false) }
                        LaunchedEffect(Unit) {
                            visible = true
                        }
                        
                        AnimatedVisibility(
                           visible = visible,
                           enter = slideInVertically(
                               initialOffsetY = { 50 * (index + 1) },
                               animationSpec = tween(durationMillis = 500, delayMillis = index * 50)
                           ) + fadeIn(animationSpec = tween(durationMillis = 500, delayMillis = index * 50))
                       ) {
                           NotificationCard(
                               item = item,
                               onMarkRead = { onMarkRead(item) },
                               onToggleStar = { onToggleStar(item, !item.starred) }
                           )
                       }
                    }
                    
                    if (currentList.isEmpty()) {
                         item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Icon(
                                        Icons.Outlined.Notifications,
                                        contentDescription = null,
                                        modifier = Modifier.size(64.dp).alpha(0.5f),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        "No alerts found",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AlertsTabRow(
    selectedFilter: String,
    onSelect: (String) -> Unit,
    counts: Map<String, Int>
) {
    val tabs = listOf("Unread", "Read", "Starred")
    
    // Background Pill Transition
    // We can't easily animate a single shared pill background without a custom Layout or coordinates.
    // However, giving each tab a nice interaction and selected state is "interactive" enough for a "pro" feel
    // unless we build a full SegmentedControl.
    // Let's build a nice Segmented Control style row.
    
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 4.dp)
    ) {
        tabs.forEach { tab ->
            val isSelected = selectedFilter == tab
            val count = counts[tab] ?: 0
            
            val backgroundColor by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else Color.Transparent,
                animationSpec = tween(durationMillis = 300)
            )
            val borderColor by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                animationSpec = tween(durationMillis = 300)
            )
            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.05f else 1f,
                animationSpec = tween(durationMillis = 300, easing = EaseOutBack)
            )

            Surface(
                onClick = { onSelect(tab) },
                shape = RoundedCornerShape(12.dp),
                color = backgroundColor,
                border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
                modifier = Modifier
                    .height(36.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(horizontal = 14.dp)
                ) {
                    Text(
                        tab,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (count > 0) {
                         Box(
                             modifier = Modifier
                                 .background(
                                     color = if(isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant, 
                                     shape = CircleShape
                                 )
                                 .padding(horizontal = 6.dp, vertical = 2.dp)
                         ) {
                             Text(
                                 count.toString(),
                                 style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                 color = MaterialTheme.colorScheme.surface
                             )
                         }
                    }
                }
            }
        }
    }
}

@OptIn(kotlin.time.ExperimentalTime::class)
@Composable
fun NotificationCard(
    item: NotificationItem,
    onMarkRead: () -> Unit,
    onToggleStar: () -> Unit
) {
    val isUnread = item.status == "unread"
    
    // Parse date for nice display: 26 Jan 2026 at 10.06 am
    val formattedDate = remember(item.createdAt) {
        try {
            val instant = Instant.parse(item.createdAt)
            val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            val month = localDateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
            // Format time: hh.mm a
            val hour = localDateTime.hour
            val minute = localDateTime.minute.toString().padStart(2, '0')
            val amPm = if (hour < 12) "am" else "pm"
            val hour12 = if (hour == 0 || hour == 12) 12 else hour % 12
            
            "${localDateTime.dayOfMonth} $month ${localDateTime.year} at $hour12.$minute $amPm"
        } catch (e: Exception) {
            item.createdAt // Fallback
        }
    }
    
    // Generate subtle color for icon based on item ID
    val iconColor = remember(item.id) {
        val hash = item.id.hashCode()
        val hue = (hash.absoluteValue % 360).toFloat()
        Color.hsl(hue, 0.6f, 0.5f)
    }
    
    val iconBackgroundColor = remember(item.id) {
        val hash = item.id.hashCode()
        val hue = (hash.absoluteValue % 360).toFloat()
        Color.hsl(hue, 0.4f, 0.85f, 0.3f)
    }

    // Outer container with 1dp padding and semi-transparent background for wrap effect
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            .padding(1.dp)
    ) {
        // Content container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(19.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                             MaterialTheme.colorScheme.surface,
                             MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                        )
                    )
                )
                .clickable { onMarkRead() }
                .padding(16.dp)
        ) {
            
            // Main Row for Icon + Text
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 24.dp), // Reduced padding to give more space to text
                verticalAlignment = Alignment.CenterVertically, // Center the bell vertically
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Icon with subtle color variation
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(iconBackgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Notifications,
                        contentDescription = null,
                        tint = iconColor
                    )
                }

                // Text Content
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        item.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        item.body,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    // Date + Badge Row
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            formattedDate, 
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        
                        if (isUnread) {
                            Surface(
                                color = Color(0xFFCCFF00), // Updated to vibrant yellow as requested/implied
                                shape = RoundedCornerShape(6.dp),
                            ) {
                                Text(
                                    "NEW",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    ),
                                    color = Color.Black,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }

            // --- Right Side Actions ---
            
            // 1. Star at Center End
            val starScale by animateFloatAsState(
                targetValue = if (item.starred) 1.2f else 1.0f,
                animationSpec = spring(dampingRatio = 0.5f, stiffness = Spring.StiffnessMedium)
            )
             val starColor by animateColorAsState(
                targetValue = if (item.starred) Color(0xFFFFC107) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
            )

            IconButton(
                onClick = onToggleStar,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .graphicsLayer {
                        scaleX = starScale
                        scaleY = starScale
                    }
            ) {
                Icon(
                    if (item.starred) Icons.Outlined.Star else Icons.Outlined.StarOutline,
                    contentDescription = "Star",
                    tint = starColor
                )
            }

            // 2. Mark as Read at Bottom End with enhanced animation
             if (isUnread) {
                 var isPressed by remember { mutableStateOf(false) }
                 val scale by animateFloatAsState(
                     targetValue = if (isPressed) 0.85f else 1f,
                     animationSpec = spring(dampingRatio = 0.6f, stiffness = Spring.StiffnessMedium)
                 )
                 val alpha by animateFloatAsState(
                     targetValue = if (isPressed) 0.6f else 1f,
                     animationSpec = tween(150)
                 )
                 
                 Text(
                    "Mark read",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(top = 8.dp)
                        .graphicsLayer { 
                            scaleX = scale
                            scaleY = scale
                            this.alpha = alpha
                        }
                        .clickable(
                             interactionSource = remember { MutableInteractionSource() },
                             indication = null
                         ) { 
                             isPressed = true
                             onMarkRead()
                         }
                )
            }
        }
    }
}
