package org.example.project

import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import org.example.project.data.TicketPass
import org.example.project.data.Recommendation
import org.example.project.data.PriceAlert
import org.example.project.data.OrganizerEvent
import org.example.project.data.sampleAlerts
import org.example.project.data.sampleEvents
import org.example.project.data.sampleOrder
import org.example.project.data.sampleRecommendations
import org.example.project.data.sampleTickets
import org.example.project.data.sampleOrganizerEvents
import org.example.project.analytics.Analytics
import org.example.project.analytics.AnalyticsEvent
import org.example.project.ui.components.AnimatedProgressBar
import org.example.project.ui.components.CheckoutScreen
import org.example.project.ui.components.EventCard
import org.example.project.ui.components.FabGlow
import org.example.project.ui.components.GhostButton
import org.example.project.ui.components.GlowCard
import org.example.project.ui.components.LoadingRow
import org.example.project.ui.components.LoadingSpinner
import org.example.project.ui.components.NeonBanner
import org.example.project.ui.components.NeonTextButton
import org.example.project.ui.components.Pill
import org.example.project.ui.components.PrimaryButton
import org.example.project.ui.components.TicketCard
import org.example.project.ui.components.TicketDetailScreen
import org.example.project.ui.components.TopBar
import org.example.project.ui.components.GoTickyMotion
import org.example.project.ui.components.infinitePulseAmplitude
import org.example.project.ui.components.pressAnimated
import org.example.project.ui.theme.GoTickyGradients
import org.example.project.ui.theme.GoTickyTextures
import org.example.project.ui.theme.GoTickyTheme
import org.example.project.ui.theme.IconCategory
import org.example.project.ui.theme.IconCategoryColors
import org.example.project.ui.theme.goTickyShapes
import kotlinx.coroutines.delay
import kotlinx.coroutines.Job
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.example.project.GoTickyFeatures
import androidx.compose.runtime.mutableStateMapOf

private enum class MainScreen {
    Home, Browse, Tickets, Alerts, Profile, Organizer
}

private data class NavItem(
    val screen: MainScreen,
    val label: String,
    val icon: @Composable () -> Unit,
    val category: IconCategory,
)

private data class PersonalizationPrefs(val genres: List<String>, val city: String)
private data class LaunchCheck(val id: String, val title: String, val desc: String)

private val launchChecklist = listOf(
    LaunchCheck("palette", "Palette frozen", "Final brand + semantic tokens locked in Color.kt; icon tints distinct."),
    LaunchCheck("motion", "Motion verified", "Buttons, FAB, progress, spinners, back animation follow visible motion spec."),
    LaunchCheck("screens", "MVP screens compile", "Home, Search/Filters, Detail, Seat stub, Checkout, Tickets, Alerts, Profile, Organizer stub."),
    LaunchCheck("analytics", "Analytics + flags", "Key events logged; payments guarded by feature flag."),
    LaunchCheck("backend", "Firestore draft", "Schemas/rules drafted for events/tickets/orders/alerts/recs.")
)

private val PersonalizationPrefsSaver = listSaver<PersonalizationPrefs, Any>(
    save = { listOf(it.genres, it.city) },
    restore = {
        val genres = it[0] as List<String>
        val city = it[1] as String
        PersonalizationPrefs(genres, city)
    }
)

@Composable
fun App() {
    GoTickyTheme {
        GoTickyRoot()
    }
}

@Composable
private fun MapPreview() {
    GlowCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Map view", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                Text("See what's close by with live availability.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.16f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.Place, contentDescription = "Map", tint = MaterialTheme.colorScheme.primary)
            }
        }
        Spacer(Modifier.height(10.dp))
        PrimaryButton(text = "Open map", onClick = { /* TODO map screen */ })
    }
}

@Composable
private fun SeatPreview() {
    GlowCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Seat preview", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            Text("Interactive seat map coming up—highlighted value scores and price alerts.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(goTickyShapes.large)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
            ) {
                // Simple visual placeholder with glowing rows
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    repeat(3) { row ->
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            repeat(10) { col ->
                                val alpha = 0.3f + (row + col) % 2 * 0.15f
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(12.dp)
                                        .clip(goTickyShapes.small)
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = alpha))
                                )
                            }
                        }
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                PrimaryButton(text = "Select seats", onClick = { /* TODO seat map */ })
                GhostButton(text = "Set price alert", onClick = { /* TODO alert */ })
            }
        }
    }
}

@Composable
private fun FlyerPreviewCard(
    isUploading: Boolean,
    hasFlyer: Boolean,
    flyerLabel: String,
    onReplace: () -> Unit,
    onClear: () -> Unit,
) {
    val pulse = infinitePulseAmplitude(minScale = 0.995f, maxScale = 1.02f, durationMillis = 2400)
    val accent = MaterialTheme.colorScheme.secondary
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(goTickyShapes.large)
            .background(GoTickyGradients.CardGlow)
            .background(GoTickyGradients.GlassWash)
            .drawBehind { drawRect(GoTickyTextures.GrainTint) }
            .border(1.dp, GoTickyGradients.EdgeHalo, goTickyShapes.large)
            .graphicsLayer(scaleX = pulse, scaleY = pulse)
            .pressAnimated()
            .clickable { onReplace() },
        contentAlignment = Alignment.Center
    ) {
        if (isUploading) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                LoadingSpinner(size = 36)
                Text(
                    text = "Uploading flyer...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                AnimatedProgressBar(progress = 0.78f, modifier = Modifier.fillMaxWidth(0.86f))
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(accent.copy(alpha = 0.18f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Image,
                        contentDescription = "Flyer",
                        tint = accent
                    )
                }
                Text(
                    text = if (hasFlyer) "Flyer ready" else "Tap to add a flyer",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = flyerLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (hasFlyer) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        NeonTextButton(text = "Replace", onClick = onReplace)
                        NeonTextButton(text = "Remove", onClick = onClear)
                    }
                } else {
                    NeonTextButton(text = "Upload flyer", onClick = onReplace)
                }
            }
        }
    }
}

@Composable
private fun GoTickyRoot() {
    var currentScreen by remember { mutableStateOf(MainScreen.Home) }
    var detailEvent by remember { mutableStateOf<org.example.project.data.EventItem?>(null) }
    var selectedTicket by remember { mutableStateOf<TicketPass?>(null) }
    var showCheckout by remember { mutableStateOf(false) }
    var selectedTicketType by remember { mutableStateOf("General / Standard") }
    val alerts = remember { mutableStateListOf<PriceAlert>(*sampleAlerts.toTypedArray()) }
    val recommendations = remember { sampleRecommendations }
    val organizerEvents = remember { mutableStateListOf<OrganizerEvent>(*sampleOrganizerEvents.toTypedArray()) }
    var showCreateEvent by remember { mutableStateOf(false) }
    var isScrolling by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var relaxJob by remember { mutableStateOf<Job?>(null) }
    val checklistState = remember {
        mutableStateMapOf<String, Boolean>().apply {
            launchChecklist.forEach { put(it.id, false) }
        }
    }
    val scrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y != 0f) {
                    isScrolling = true
                    relaxJob?.cancel()
                    relaxJob = scope.launch {
                        delay(300)
                        isScrolling = false
                    }
                }
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (consumed.y != 0f || available.y != 0f) {
                    isScrolling = true
                    relaxJob?.cancel()
                    relaxJob = scope.launch {
                        delay(300)
                        isScrolling = false
                    }
                }
                return Offset.Zero
            }
        }
    }
    val fabAlpha by animateFloatAsState(
        targetValue = if (isScrolling) 0.2f else 1f,
        animationSpec = tween(200, easing = LinearEasing),
        label = "fabAlpha"
    )
    var personalizationPrefs by rememberSaveable(stateSaver = PersonalizationPrefsSaver) {
        mutableStateOf(PersonalizationPrefs(genres = listOf("Concerts", "Sports", "Comedy", "Family"), city = "Los Angeles"))
    }
    fun openEventById(id: String) {
        sampleEvents.firstOrNull { it.id == id }?.let { detailEvent = it }
    }
    fun personalize(recs: List<Recommendation>): List<Recommendation> {
        fun score(rec: Recommendation): Int {
            var score = 0
            if (rec.city == personalizationPrefs.city) score += 3
            if (personalizationPrefs.genres.any { tag ->
                    rec.tag.contains(tag, ignoreCase = true) || rec.reason.contains(tag, ignoreCase = true)
                }
            ) {
                score += 2
            }
            // Treat as "past purchase" if any ticket we hold has the same event title
            if (sampleTickets.any { it.eventTitle == rec.title }) {
                score += 2
            }
            if (rec.tag.contains("Trending", ignoreCase = true) || rec.tag.contains("Hot", ignoreCase = true) || rec.tag.contains("For you", ignoreCase = true)) {
                score += 1
            }
            return score
        }
        val scored = recs.map { rec -> rec to score(rec) }
        val prioritized = scored
            .sortedByDescending { it.second }
            .map { it.first }
        val withSignal = scored
            .filter { it.second > 0 }
            .sortedByDescending { it.second }
            .map { it.first }
        return if (withSignal.isNotEmpty()) withSignal else prioritized
    }
    val navItems = remember {
        listOf(
            NavItem(MainScreen.Home, "Discover", { Icon(Icons.Outlined.Explore, null) }, IconCategory.Discover),
            NavItem(MainScreen.Browse, "Browse", { Icon(Icons.Outlined.Event, null) }, IconCategory.Calendar),
            NavItem(MainScreen.Tickets, "My Tickets", { Icon(Icons.Outlined.ReceiptLong, null) }, IconCategory.Ticket),
            NavItem(MainScreen.Alerts, "Alerts", { Icon(Icons.Outlined.Notifications, null) }, IconCategory.Alerts),
            NavItem(MainScreen.Profile, "Profile", { Icon(Icons.Outlined.AccountCircle, null) }, IconCategory.Profile),
        )
    }

    val showRootChrome = currentScreen == MainScreen.Home &&
        !showCheckout &&
        selectedTicket == null &&
        detailEvent == null

	LaunchedEffect(currentScreen) {
		Analytics.log(
			AnalyticsEvent(
				name = "screen_view",
				params = mapOf("screen" to currentScreen.name)
			)
		)
	}

	LaunchedEffect(detailEvent?.id) {
		detailEvent?.let { event ->
			Analytics.log(
				AnalyticsEvent(
					name = "event_view",
					params = mapOf(
						"event_id" to event.id,
						"title" to event.title
					)
				)
			)
		}
	}

    Scaffold(
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0),
        floatingActionButton = {
            if (showRootChrome) {
                FabGlow(
                    modifier = Modifier.graphicsLayer(alpha = fabAlpha),
                    icon = { Icon(Icons.Outlined.Notifications, contentDescription = "Alerts", tint = MaterialTheme.colorScheme.onPrimary) },
                    onClick = { currentScreen = MainScreen.Alerts }
                )
            }
        },
        bottomBar = {
            if (showRootChrome) {
                Box(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(bottom = 15.dp)
                ) {
                    BottomBar(
                        navItems = navItems,
                        current = currentScreen,
                        chromeAlpha = fabAlpha,
                    ) { tapped -> currentScreen = tapped }
                }
            }
        }
    ) { inner ->
        val layoutDirection = LocalLayoutDirection.current
        val contentPadding = PaddingValues(
            start = inner.calculateStartPadding(layoutDirection),
            end = inner.calculateEndPadding(layoutDirection),
            top = inner.calculateTopPadding(),
            bottom = 0.dp
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .nestedScroll(scrollConnection)
        ) {
			if (showCheckout) {
				CheckoutScreen(
					 order = sampleOrder,
					 selectedTicketType = selectedTicketType,
					 onBack = {
						 showCheckout = false
					 },
					 onPlaceOrder = {
						 if (GoTickyFeatures.EnableRealPayments) {
							 // TODO: integrate real payment processor; for now keep mock success flow for demos.
						 }
						 Analytics.log(
							 AnalyticsEvent(
								 name = "checkout_success",
								 params = mapOf(
									 "order_id" to sampleOrder.id
								 )
							 )
						 )
						 showCheckout = false
						 detailEvent = null
						 selectedTicket = null
						 currentScreen = MainScreen.Tickets
					 }
				)
			} else if (selectedTicket != null) {
				TicketDetailScreen(
					 ticket = selectedTicket!!,
					 onBack = { selectedTicket = null },
					 onAddToWallet = { /* TODO wallet */ },
					 onTransfer = { /* TODO transfer */ }
				)
			} else if (detailEvent != null) {
				EventDetailScreen(
					 event = detailEvent!!,
					 onBack = { detailEvent = null },
					 onProceedToCheckout = { ticketType ->
						 selectedTicketType = ticketType
						 detailEvent?.let { event ->
							 Analytics.log(
								 AnalyticsEvent(
									 name = "select_seat",
									 params = mapOf(
										 "event_id" to event.id,
										 "title" to event.title,
										 "ticket_type" to ticketType
									 )
								 )
							 )
							 Analytics.log(
								 AnalyticsEvent(
									 name = "checkout_start",
									 params = mapOf(
										 "event_id" to event.id,
										 "title" to event.title,
										 "ticket_type" to ticketType
									 )
								 )
							 )
						 }
						 if (GoTickyFeatures.EnableRealSeatMap) {
							 // TODO: navigate to real seat map screen; for now fall through to checkout for demos.
						 }
						 showCheckout = true
					 },
					 onAlert = {
						 detailEvent?.let { event ->
							 val numericPrice = event.priceFrom.filter { it.isDigit() }.toIntOrNull() ?: 100
							 val targetPrice = (numericPrice * 0.9f).toInt().coerceAtLeast(1)
							 alerts.add(
								 PriceAlert(
									 id = "alert-${alerts.size + 1}",
									 eventId = event.id,
									 title = event.title,
									 venue = "${event.city} • Venue",
									 section = "Best available",
									 targetPrice = targetPrice,
									 currentPrice = numericPrice,
									 dropPercent = 0,
									 expiresInDays = 7,
									 status = "Watching"
								 )
							 )
							 Analytics.log(
								 AnalyticsEvent(
									 name = "alert_create",
									 params = mapOf(
										 "source" to "event_detail",
										 "event_id" to event.id,
										 "title" to event.title
									 )
								 )
							 )
						 }
						 currentScreen = MainScreen.Alerts
					 }
				)
			} else {
                when (currentScreen) {
                    MainScreen.Home -> HomeScreen(
                        onOpenAlerts = { currentScreen = MainScreen.Alerts },
                        onEventSelected = { event ->
							Analytics.log(
								AnalyticsEvent(
									name = "event_tap",
									params = mapOf(
										"event_id" to event.id,
										"title" to event.title
									)
								)
							)
							detailEvent = event
						},
                        recommendations = personalize(recommendations)
                    )
                    MainScreen.Browse -> PlaceholderScreen("Browse events") { currentScreen = MainScreen.Home }
                    MainScreen.Tickets -> TicketsScreen(
                        tickets = sampleTickets,
                        onTicketSelected = { ticket ->
							Analytics.log(
								AnalyticsEvent(
									name = "ticket_view",
									params = mapOf(
										"ticket_id" to ticket.id,
										"event_title" to ticket.eventTitle
									)
								)
							)
							selectedTicket = ticket
						},
                        onCheckout = { showCheckout = true }
                    )
                    MainScreen.Alerts -> AlertsScreen(
                        alerts = alerts,
                        recommendations = personalize(recommendations),
                        personalizationPrefs = personalizationPrefs,
                        onBack = { currentScreen = MainScreen.Home },
                        onOpenEvent = { eventId ->
							Analytics.log(
								AnalyticsEvent(
									name = "event_from_alert",
									params = mapOf("event_id" to eventId)
								)
							)
							openEventById(eventId)
						},
                        onCreateAlert = {
                            alerts.add(
                                PriceAlert(
                                    id = "alert-${alerts.size + 1}",
                                    eventId = "1",
                                    title = "New drop watch",
                                    venue = "Any city",
                                    section = "Auto-best available",
                                    targetPrice = 99,
                                    currentPrice = 120,
                                    dropPercent = 0,
                                    expiresInDays = 7,
                                    status = "Watching"
                                )
                            )
                            Analytics.log(
                                AnalyticsEvent(
                                    name = "alert_create",
                                    params = mapOf(
                                        "source" to "alerts_screen"
                                    )
                                )
                            )
                        },
                        onAdjustAlert = { updated ->
                            val idx = alerts.indexOfFirst { it.id == updated.id }
                            if (idx != -1) {
                                alerts[idx] = updated
                            }
                            Analytics.log(
                                AnalyticsEvent(
                                    name = "alert_update",
                                    params = mapOf(
                                        "alert_id" to updated.id,
                                        "event_id" to updated.eventId
                                    )
                                )
                            )
                        },
                        onUpdatePersonalization = { newPrefs ->
                            personalizationPrefs = newPrefs
                        }
                    )
                    MainScreen.Profile -> ProfileScreen(
                        checklistState = checklistState,
                        onToggleCheck = { id -> checklistState[id]?.let { checklistState[id] = !it } },
                        onOpenOrganizer = {
						Analytics.log(
							AnalyticsEvent(
								name = "organizer_open",
								params = emptyMap()
							)
						)
						currentScreen = MainScreen.Organizer
					}
                    )
                    MainScreen.Organizer -> if (showCreateEvent) {
                        CreateEventScreen(
                            onBack = { showCreateEvent = false },
                            onSaveDraft = { title, city, venue, dateLabel, priceFrom, status ->
                                val index = organizerEvents.size + 1
                                val newEvent = OrganizerEvent(
                                    id = "org-$index",
                                    eventId = "new-$index",
                                    title = if (title.isNotBlank()) title else "Untitled event $index",
                                    city = city.ifBlank { "City TBD" },
                                    venue = venue.ifBlank { "Venue TBD" },
                                    dateLabel = dateLabel.ifBlank { "Date TBD" },
                                    priceFrom = if (priceFrom.isNotBlank()) priceFrom else "From $0",
                                    status = status,
                                    views = 0,
                                    saves = 0,
                                    sales = 0,
                                    isVerified = false
                                )
                                organizerEvents.add(0, newEvent)
                                Analytics.log(
                                    AnalyticsEvent(
                                        name = "organizer_event_saved",
                                        params = mapOf(
                                            "event_id" to newEvent.eventId,
                                            "status" to newEvent.status
                                        )
                                    )
                                )
                                showCreateEvent = false
                            }
                        )
                    } else {
                        OrganizerDashboardScreen(
                            events = organizerEvents,
                            onBack = { currentScreen = MainScreen.Profile },
                            onCreateEvent = {
							Analytics.log(
								AnalyticsEvent(
									name = "organizer_create_event_start",
									params = emptyMap()
								)
							)
							showCreateEvent = true
						}
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeScreen(
    onOpenAlerts: () -> Unit,
    onEventSelected: (org.example.project.data.EventItem) -> Unit,
    recommendations: List<Recommendation>,
) {
    var searchQuery by remember { mutableStateOf("") }
    val filters = remember { mutableStateListOf("Concerts", "Sports", "Family") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(GoTickyGradients.CardGlow)
            .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 18.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        GlowCard(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TopBar(
                title = "GoTicky Live",
                onBack = null,
                actions = { NeonTextButton(text = "Alerts", onClick = { onOpenAlerts() }) },
                backgroundBrush = null,
                backgroundColor = Color.Transparent
            )
        }
        HeroSection()
        QuickSearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            filters = filters,
            onSearch = {
                Analytics.log(
                    AnalyticsEvent(
                        name = "search",
                        params = mapOf(
                            "query" to searchQuery,
                            "filters" to filters.joinToString(",")
                        )
                    )
                )
            }
        )
        CategoryRow()
        SectionHeader(title = "Tonight's heat", action = { NeonTextButton(text = "See all", onClick = { /* TODO */ }) })
        HighlightCard()
        NeonBanner(
            title = "VIP Drop",
            subtitle = "Exclusive pre-sale seats with transparent fees."
        )
        SectionHeader(title = "For you", action = { NeonTextButton(text = "Personalize", onClick = { onOpenAlerts() }) })
        RecommendationsRow(
            recommendations = recommendations,
            onOpen = { rec ->
                val event = sampleEvents.firstOrNull { it.id == rec.eventId }
                event?.let { onEventSelected(it) }
            }
        )
        MapPreview()
        SectionHeader(title = "Popular near you", action = { NeonTextButton(text = "See all", onClick = { /* TODO */ }) })
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            val filtered = sampleEvents.filter {
                searchQuery.isBlank() || it.title.contains(searchQuery, ignoreCase = true) || it.city.contains(searchQuery, true)
            }
            filtered.forEach {
                EventCard(item = it) { onEventSelected(it) }
            }
        }
        
        SectionHeader("Progress preview", action = null)
        LoadingRow(Modifier.fillMaxWidth())
    }
}

@Composable
private fun HeroSection() {
	val transition = rememberInfiniteTransition(label = "heroCtaPulse")
	val primaryPulse by transition.animateFloat(
		initialValue = 0.97f,
		targetValue = 1.03f,
		animationSpec = infiniteRepeatable(
			animation = tween(durationMillis = 2600, easing = LinearEasing)
		),
		label = "heroPrimaryPulse"
	)
	val secondaryPulse by transition.animateFloat(
		initialValue = 1.02f,
		targetValue = 0.98f,
		animationSpec = infiniteRepeatable(
			animation = tween(durationMillis = 3800, easing = LinearEasing)
		),
		label = "heroSecondaryPulse"
	)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(16.dp, goTickyShapes.extraLarge)
            .clip(goTickyShapes.extraLarge)
            .background(GoTickyGradients.Hero)
            .padding(18.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(28.dp)
                        .graphicsLayer(
                            scaleX = 0.8f,
                            scaleY = 0.8f,
                            rotationZ = -45f
                        )
                        .clip(CircleShape)
                )
                Text(
                    "GoTicky",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Text(
                text = "Experience events like never before",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.ExtraBold),
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "Discover and book tickets to concerts, sports, comedy, family shows and more.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PrimaryButton(
                    text = "Find events",
                    modifier = Modifier.graphicsLayer(scaleX = primaryPulse, scaleY = primaryPulse),
                    onClick = { /* TODO */ }
                )
                GhostButton(
                    text = "I'm feeling lucky",
                    modifier = Modifier.graphicsLayer(scaleX = secondaryPulse, scaleY = secondaryPulse),
                    onClick = { /* TODO */ }
                )
            }
        }
    }
}

@Composable
private fun SearchChip(label: String, leadingIcon: (@Composable () -> Unit)? = null, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .pressAnimated()
            .clip(goTickyShapes.small)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        leadingIcon?.invoke()
        Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
private fun CategoryRow() {
    val categories = listOf(
        "Discover" to IconCategory.Discover,
        "Calendar" to IconCategory.Calendar,
        "Map" to IconCategory.Map,
        "Tickets" to IconCategory.Ticket,
        "Alerts" to IconCategory.Alerts,
    )
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(categories) { (label, category) ->
            val tint = IconCategoryColors[category] ?: MaterialTheme.colorScheme.primary
            Row(
                modifier = Modifier
                    .pressAnimated(scaleDown = 0.92f)
                    .clip(goTickyShapes.medium)
                    .background(tint.copy(alpha = 0.12f))
                    .clickable { }
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(tint)
                )
                Text(label, style = MaterialTheme.typography.labelMedium, color = tint)
            }
        }
    }
}

@Composable
private fun QuickSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    filters: MutableList<String>,
    onSearch: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(goTickyShapes.large)
            .background(MaterialTheme.colorScheme.surface)
            .shadow(8.dp, goTickyShapes.large)
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Instant search",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSurface
        )
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            SearchChip("Location or venue", leadingIcon = { Icon(Icons.Outlined.Place, contentDescription = null, tint = MaterialTheme.colorScheme.primary) })
            SearchChip("Date", leadingIcon = { Icon(Icons.Outlined.Event, contentDescription = null, tint = MaterialTheme.colorScheme.primary) })
            PrimaryButton(text = "Find events", onClick = { onSearch() })
        }
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = "Search") },
            placeholder = { Text("Search events, artists, venues") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = goTickyShapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )
        AnimatedProgressBar(progress = 0.32f, modifier = Modifier.fillMaxWidth())
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterPill("Concerts", filters)
            FilterPill("Sports", filters)
            FilterPill("Family", filters)
        }
    }
}

@Composable
private fun HighlightCard() {
    GlowCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(GoTickyGradients.Cta)
                )
                Column {
                    Text("Marquee Night", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text("LA • Tonight • Limited VIP", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Text(
                "Best seats highlighted with neon glow. See transparent fees before checkout.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PrimaryButton(text = "Select seats", onClick = { /* TODO */ })
                GhostButton(text = "Price alerts", onClick = { /* TODO */ })
            }
        }
    }
}

@Composable
private fun FilterPill(text: String, filters: MutableList<String>) {
    val selected = filters.contains(text)
    val bg = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.18f) else MaterialTheme.colorScheme.surfaceVariant
    val fg = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
    Pill(text = text, color = bg, textColor = fg, modifier = Modifier.clickable { 
        if (selected) filters.remove(text) else filters.add(text)
    })
}

@Composable
private fun TicketsScreen(
    tickets: List<TicketPass>,
    onTicketSelected: (TicketPass) -> Unit,
    onCheckout: () -> Unit
) {
    var loading by remember { mutableStateOf(true) }
    val shimmerTransition = rememberInfiniteTransition(label = "ticketsShimmer")
    val shimmerOffset by shimmerTransition.animateFloat(
        initialValue = -200f,
        targetValue = 400f,
        animationSpec = infiniteRepeatable(tween(1100, easing = LinearEasing)),
        label = "ticketsShimmerAnim"
    )
    LaunchedEffect(Unit) {
        delay(450)
        loading = false
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(GoTickyGradients.CardGlow)
            .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            GlowCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                TopBar(
                    title = "My Tickets",
                    onBack = null,
                    actions = { NeonTextButton(text = "Checkout", onClick = { onCheckout() }) },
                    backgroundColor = Color.Transparent
                )
            }
        }
        if (loading) {
            items(2) { _ ->
                GlowCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(84.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(12.dp)
                                    .clip(goTickyShapes.medium)
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(
                                                MaterialTheme.colorScheme.surfaceVariant,
                                                MaterialTheme.colorScheme.surface,
                                                MaterialTheme.colorScheme.surfaceVariant
                                            ),
                                            start = androidx.compose.ui.geometry.Offset(shimmerOffset, 0f),
                                            end = androidx.compose.ui.geometry.Offset(shimmerOffset + 200f, 0f)
                                        )
                                    )
                            )
                        }
                    }
                }
            }
        } else {
        items(tickets) { ticket ->
            TicketCard(ticket = ticket) { onTicketSelected(ticket) }
        }
        }
    }
}

@Composable
private fun ProfileScreen(
    checklistState: MutableMap<String, Boolean>,
    onToggleCheck: (String) -> Unit,
    onOpenOrganizer: () -> Unit,
) {
	val legalPulse = infinitePulseAmplitude(
		minScale = 0.985f,
		maxScale = 1.015f,
		durationMillis = 3200,
	)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(GoTickyGradients.CardGlow)
            .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        GlowCard(modifier = Modifier.fillMaxWidth()) {
            TopBar(
                title = "Profile & preferences",
                onBack = null,
                actions = null,
                backgroundColor = Color.Transparent
            )
        }
        GlowCard {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "Your account",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    "Signed in as guest@goticky.app",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        GlowCard {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    "Organizer tools",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    "Create events, track performance, and manage drops.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                PrimaryButton(
                    text = "Open organizer dashboard",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onOpenOrganizer
                )
            }
        }
        GlowCard {
            val completed = checklistState.values.count { it }
            val total = launchChecklist.size
            val progress = if (total == 0) 0f else (completed.toFloat() / total).coerceIn(0f, 1f)
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Launch checklist (Stage 10)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Pill(
                        text = "$completed/$total",
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.16f),
                        textColor = MaterialTheme.colorScheme.primary
                    )
                }
                AnimatedProgressBar(progress = progress, modifier = Modifier.fillMaxWidth())
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    launchChecklist.forEach { item ->
                        val checked = checklistState[item.id] == true
                        Row(
                            modifier = Modifier
                                .clip(goTickyShapes.medium)
                                .background(
                                    if (checked) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                                    else MaterialTheme.colorScheme.surfaceVariant
                                )
                                .pressAnimated()
                                .clickable { onToggleCheck(item.id) }
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (checked) Icons.Outlined.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                                contentDescription = null,
                                tint = if (checked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text(item.title, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                                Text(item.desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
        GlowCard {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "Legal",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                NeonTextButton(
                    text = "Privacy policy",
                    modifier = Modifier.graphicsLayer(scaleX = legalPulse, scaleY = legalPulse),
                    onClick = { /* TODO open privacy URL */ }
                )
                NeonTextButton(
                    text = "Terms of service",
                    modifier = Modifier.graphicsLayer(scaleX = legalPulse, scaleY = legalPulse),
                    onClick = { /* TODO open terms URL */ }
                )
            }
        }
    }
}

@Composable
private fun OrganizerDashboardScreen(
    events: List<OrganizerEvent>,
    onBack: () -> Unit,
    onCreateEvent: () -> Unit,
) {
    val totalViews = events.sumOf { it.views }
    val totalSaves = events.sumOf { it.saves }
    val totalSales = events.sumOf { it.sales }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(GoTickyGradients.CardGlow)
            .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        GlowCard(modifier = Modifier.fillMaxWidth()) {
            TopBar(
                title = "Organizer dashboard",
                onBack = onBack,
                actions = null,
                backgroundColor = Color.Transparent
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OrganizerMetricCard(
                title = "Views",
                value = totalViews,
                accent = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )
            OrganizerMetricCard(
                title = "Saves",
                value = totalSaves,
                accent = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.weight(1f)
            )
            OrganizerMetricCard(
                title = "Sales",
                value = totalSales,
                accent = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.weight(1f)
            )
        }
        SectionHeader(
            title = "Your events",
            action = { NeonTextButton(text = "Create", onClick = onCreateEvent) }
        )
        events.forEach { event ->
            OrganizerEventCard(event = event)
        }
        PrimaryButton(
            text = "Create new event",
            modifier = Modifier.fillMaxWidth(),
            onClick = onCreateEvent
        )
    }
}

@Composable
private fun OrganizerEventCard(event: OrganizerEvent) {
    GlowCard(modifier = Modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.weight(1f))
                if (event.isVerified) {
					val verifiedPulse = infinitePulseAmplitude(
						minScale = 0.96f,
						maxScale = 1.04f,
						durationMillis = 2400,
					)
                    Pill(
                        text = "Verified",
                        modifier = Modifier.graphicsLayer(scaleX = verifiedPulse, scaleY = verifiedPulse),
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.16f),
                        textColor = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            Text(
                text = "${event.city} • ${event.venue}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = event.dateLabel,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = event.priceFrom,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.primary
                )
                val statusColor = when (event.status) {
                    "Live" -> MaterialTheme.colorScheme.primary
                    "Draft" -> MaterialTheme.colorScheme.outline
                    else -> MaterialTheme.colorScheme.tertiary
                }
                Pill(
                    text = event.status,
                    color = statusColor.copy(alpha = 0.16f),
                    textColor = statusColor
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "${event.views} views",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${event.saves} saves",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${event.sales} sales",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun OrganizerMetricCard(
	title: String,
	value: Int,
	accent: Color,
	modifier: Modifier = Modifier,
) {
	    val pulse by rememberInfiniteTransition(label = "metricPulse").animateFloat(
	        initialValue = 0.97f,
	        targetValue = 1.03f,
	        animationSpec = infiniteRepeatable(
	            animation = tween(durationMillis = 2600, easing = LinearEasing)
	        ),
	        label = "metricPulseAnim"
	    )
	    GlowCard(
	        modifier = modifier.graphicsLayer(scaleX = pulse, scaleY = pulse)
	    ) {
	        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
	            Text(
	                text = title,
	                style = MaterialTheme.typography.labelMedium,
	                color = MaterialTheme.colorScheme.onSurfaceVariant
	            )
	            Text(
	                text = value.toString(),
	                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
	                color = accent
	            )
	        }
	    }
	}

@Composable
private fun RecommendationsRow(
    recommendations: List<Recommendation>,
    onOpen: (Recommendation) -> Unit
) {
    var loading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(600)
        loading = false
    }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        if (loading) {
            RecommendationSkeleton()
        }
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            items(recommendations) { rec ->
                GlowCard(
                    modifier = Modifier
                        .width(240.dp)
                        .clickable { onOpen(rec) }
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Pill(text = rec.tag, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.16f), textColor = MaterialTheme.colorScheme.primary)
                        Text(rec.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                        Text(rec.city, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(rec.reason, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text(rec.priceFrom, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.primary)
                            NeonTextButton(text = "Open", onClick = { onOpen(rec) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RecommendationSkeleton() {
    val shimmer by rememberInfiniteTransition(label = "recShimmer").animateFloat(
        initialValue = -200f,
        targetValue = 400f,
        animationSpec = infiniteRepeatable(tween(1000, easing = LinearEasing)),
        label = "recShimmerAnim"
    )
    GlowCard {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            repeat(2) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(14.dp)
                        .clip(goTickyShapes.medium)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    MaterialTheme.colorScheme.surface,
                                    MaterialTheme.colorScheme.surfaceVariant
                                ),
                                start = androidx.compose.ui.geometry.Offset(shimmer, 0f),
                                end = androidx.compose.ui.geometry.Offset(shimmer + 200f, 0f)
                            )
                        )
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(goTickyShapes.small)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
        }
    }
}

@Composable
private fun SocialShareRow(onInvite: () -> Unit, onShare: () -> Unit) {
    GlowCard {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Group plan", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            Text("Invite friends to lock seats together and sync alerts.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                PrimaryButton(text = "Invite friends", modifier = Modifier.weight(1f)) { onInvite() }
                GhostButton(text = "Share link", modifier = Modifier.weight(1f)) { onShare() }
            }
        }
    }
}

@Composable
private fun AlertsScreen(
    alerts: List<PriceAlert>,
    recommendations: List<Recommendation>,
    personalizationPrefs: PersonalizationPrefs,
    onBack: () -> Unit,
    onOpenEvent: (String) -> Unit,
    onCreateAlert: () -> Unit,
    onAdjustAlert: (PriceAlert) -> Unit,
    onUpdatePersonalization: (PersonalizationPrefs) -> Unit,
) {
    var alertToShare by remember { mutableStateOf<PriceAlert?>(null) }
    var alertToAdjust by remember { mutableStateOf<PriceAlert?>(null) }
    var showPersonalize by remember { mutableStateOf(false) }
    var copiedLink by remember { mutableStateOf(false) }
    val clipboard = LocalClipboardManager.current
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(GoTickyGradients.CardGlow)
            .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        GlowCard(modifier = Modifier.fillMaxWidth()) {
            TopBar(
                title = "Alerts & drops",
                onBack = onBack,
                actions = { NeonTextButton(text = "Create", onClick = { onCreateAlert() }) },
                backgroundColor = Color.Transparent
            )
        }
        SectionHeader(title = "Price alerts", action = { NeonTextButton(text = "Manage", onClick = { alertToAdjust = alerts.firstOrNull() }) })
        alerts.forEach {
            AlertCard(
                alert = it,
                onOpen = { alert -> onOpenEvent(alert.eventId) },
                onShare = { alertToShare = it },
                onAdjust = { alert -> alertToAdjust = alert }
            )
        }
        SectionHeader(title = "Recommendations", action = { NeonTextButton(text = "Improve", onClick = { showPersonalize = true }) })
        RecommendationsRow(recommendations = recommendations, onOpen = { onOpenEvent(it.eventId) })
        SocialShareRow(
            onInvite = { uriHandler.openUri("https://goticky.app/group/invite") },
            onShare = { uriHandler.openUri("https://goticky.app/share/app") }
        )
    }

    alertToShare?.let { alert ->
        val shareLink = "https://goticky.app/alerts/${alert.id}"
        val schemeLink = "goticky://alert/${alert.id}"
        AlertDialog(
            onDismissRequest = {
                alertToShare = null
                copiedLink = false
            },
            title = { Text("Share alert") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Send this deep link to friends or yourself:")
                    GlowCard {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(shareLink, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                            Text(schemeLink, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            if (copiedLink) {
                                Text("Copied!", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    PrimaryButton(text = "Copy web link") {
                        clipboard.setText(AnnotatedString(shareLink))
                        copiedLink = true
                    }
                    GhostButton(text = "Copy app link") {
                        clipboard.setText(AnnotatedString(schemeLink))
                        copiedLink = true
                    }
                    NeonTextButton(text = "Open", onClick = { uriHandler.openUri(shareLink) })
                    GhostButton(text = "Done") { alertToShare = null; copiedLink = false }
                }
            }
        )
    }

    alertToAdjust?.let { alert ->
        var target by remember(alert) { mutableStateOf(alert.targetPrice.toFloat()) }
        var pushEnabled by remember(alert) { mutableStateOf(true) }
        var emailEnabled by remember(alert) { mutableStateOf(true) }
        AlertDialog(
            onDismissRequest = { alertToAdjust = null },
            title = { Text("Adjust alert") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(alert.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
                    Text(alert.section, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Target \$${target.toInt()}", style = MaterialTheme.typography.bodySmall)
                        Text("Current \$${alert.currentPrice}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                    }
                    Slider(
                        value = target,
                        onValueChange = { target = it },
                        valueRange = 20f..(alert.currentPrice + 200).toFloat()
                    )
                    Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Text("Push notifications")
                        Switch(checked = pushEnabled, onCheckedChange = { pushEnabled = it })
                    }
                    Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Text("Email updates")
                        Switch(checked = emailEnabled, onCheckedChange = { emailEnabled = it })
                    }
                }
            },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    GhostButton(text = "Cancel") { alertToAdjust = null }
                    PrimaryButton(text = "Save") {
                        val delivery = when {
                            pushEnabled && emailEnabled -> "Push + Email"
                            pushEnabled -> "Push only"
                            emailEnabled -> "Email only"
                            else -> "Muted"
                        }
                        onAdjustAlert(alert.copy(targetPrice = target.toInt(), delivery = delivery))
                        alertToAdjust = null
                    }
                }
            }
        )
    }

    if (showPersonalize) {
        var genres by remember(personalizationPrefs) { mutableStateOf(personalizationPrefs.genres.toSet()) }
        var city by remember(personalizationPrefs) { mutableStateOf(personalizationPrefs.city) }
        AlertDialog(
            onDismissRequest = { showPersonalize = false },
            title = { Text("Personalize recommendations") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Select your favorite vibes and a preferred city to refine alerts.")
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Concerts", "Sports", "Comedy", "Family", "Theater").forEach { tag ->
                            val selected = genres.contains(tag)
                            Pill(
                                text = tag,
                                color = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.16f) else MaterialTheme.colorScheme.surfaceVariant,
                                textColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.clickable {
                                    genres = if (selected) genres - tag else genres + tag
                                }
                            )
                        }
                    }
                    Text("Home city: $city", style = MaterialTheme.typography.bodySmall)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Los Angeles", "New York", "Chicago").forEach { option ->
                            val selected = city == option
                            Pill(
                                text = option,
                                color = if (selected) MaterialTheme.colorScheme.secondary.copy(alpha = 0.16f) else MaterialTheme.colorScheme.surfaceVariant,
                                textColor = if (selected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.clickable { city = option }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    GhostButton(text = "Cancel") { showPersonalize = false }
                    PrimaryButton(text = "Save prefs") {
                        onUpdatePersonalization(PersonalizationPrefs(genres = genres.toList(), city = city))
                        showPersonalize = false
                    }
                }
            }
        )
    }
}

@Composable
private fun PlaceholderScreen(title: String, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlowCard(modifier = Modifier.fillMaxWidth()) {
            TopBar(
                title = title,
                onBack = onBack,
                actions = null,
                backgroundColor = Color.Transparent
            )
        }
        Text(title, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.onSurface)
        Spacer(Modifier.height(12.dp))
        NeonTextButton(text = "Back to Discover", onClick = { onBack() })
    }
}

@Composable
private fun EventDetailScreen(
    event: org.example.project.data.EventItem,
    onBack: () -> Unit,
    onProceedToCheckout: (String) -> Unit,
    onAlert: () -> Unit
) {
    var showReport by remember { mutableStateOf(false) }
    var selectedReason by remember { mutableStateOf("Wrong information") }
    val ticketOptions = listOf("Early Bird", "General / Standard", "VIP", "Golden Circle")
    var selectedTicketType by remember { mutableStateOf(ticketOptions[1]) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(GoTickyGradients.CardGlow)
            .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GlowCard(modifier = Modifier.fillMaxWidth()) {
            TopBar(
                title = event.title,
                onBack = onBack,
                actions = {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        NeonTextButton(text = "Alerts", onClick = { onAlert() })
                        NeonTextButton(text = "Report", onClick = { showReport = true })
                    }
                },
                backgroundColor = Color.Transparent
            )
        }
        GlowCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(event.city, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(event.dateLabel, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                Text(event.priceFrom, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Ticket options",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    ticketOptions.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            RadioButton(
                                selected = option == selectedTicketType,
                                onClick = { selectedTicketType = option }
                            )
                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    PrimaryButton(text = "Proceed to checkout") { onProceedToCheckout(selectedTicketType) }
                    GhostButton(text = "Price alert") { onAlert() }
                }
            }
        }
        GlowCard {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Highlights", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
                val points = listOf(
                    "Interactive seat map with neon highlights.",
                    "Transparent fees before checkout.",
                    "Transfers and resale supported.",
                    "Add-ons: parking, merch, VIP lounge."
                )
                points.forEach {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                        )
                        Text(it, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
        SeatPreview()
    }
    if (showReport) {
        AlertDialog(
            onDismissRequest = { showReport = false },
            title = { Text("Report event") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "Help us keep GoTicky safe. What feels off about this event?",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    val reasons = listOf("Wrong information", "Fraudulent listing", "Offensive content", "Other")
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        reasons.forEach { reason ->
                            val selected = selectedReason == reason
                            Pill(
                                text = reason,
                                color = if (selected) MaterialTheme.colorScheme.error.copy(alpha = 0.16f) else MaterialTheme.colorScheme.surfaceVariant,
                                textColor = if (selected) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.clickable { selectedReason = reason }
                            )
                        }
                    }
                    Text(
                        "We’ll review this event and may follow up with organizers.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    GhostButton(text = "Cancel") { showReport = false }
                    PrimaryButton(text = "Submit") {
                        showReport = false
                    }
                }
            }
        )
    }
}

@Composable
private fun CreateEventScreen(
    onBack: () -> Unit,
    onSaveDraft: (String, String, String, String, String, String) -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var venue by remember { mutableStateOf("") }
    var dateLabel by remember { mutableStateOf("") }
    var priceFrom by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Draft") }
    var flyerUrl by remember { mutableStateOf("") }
    var flyerUploading by remember { mutableStateOf(false) }
    var flyerUploaded by remember { mutableStateOf(false) }
    val completedFields = listOf(title, city, venue, dateLabel, priceFrom).count { it.isNotBlank() } +
        if (flyerUploaded || flyerUrl.isNotBlank()) 1 else 0
    val progress = (completedFields / 6f).coerceIn(0f, 1f)

    LaunchedEffect(flyerUploading) {
        if (flyerUploading) {
            delay(900)
            if (flyerUrl.isBlank()) {
                flyerUrl = "Flyer_${title.ifBlank { "New Event" }}.png"
            }
            flyerUploaded = true
            flyerUploading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(GoTickyGradients.CardGlow)
            .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        GlowCard(modifier = Modifier.fillMaxWidth()) {
            TopBar(
                title = "Create event",
                onBack = onBack,
                actions = null,
                backgroundColor = Color.Transparent
            )
        }
        GlowCard {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    "Basics",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Event title") },
                    shape = goTickyShapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("City") },
                    shape = goTickyShapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                OutlinedTextField(
                    value = venue,
                    onValueChange = { venue = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Venue") },
                    shape = goTickyShapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                OutlinedTextField(
                    value = dateLabel,
                    onValueChange = { dateLabel = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Date & time label") },
                    placeholder = { Text("e.g. Fri · 8:00 PM") },
                    shape = goTickyShapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                OutlinedTextField(
                    value = priceFrom,
                    onValueChange = { priceFrom = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Price from") },
                    placeholder = { Text("From $50") },
                    shape = goTickyShapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
            }
        }
        GlowCard {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    "Event flyer",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    "Upload a flyer or paste a link so attendees recognize your event instantly.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                OutlinedTextField(
                    value = flyerUrl,
                    onValueChange = {
                        flyerUrl = it
                        flyerUploaded = it.isNotBlank()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Flyer link or name") },
                    placeholder = { Text("Paste a share link or describe the file") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Upload,
                            contentDescription = "Upload flyer",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    shape = goTickyShapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    PrimaryButton(
                        text = if (flyerUploading) "Uploading..." else "Upload flyer",
                        modifier = Modifier.weight(1f)
                    ) { flyerUploading = true }
                    GhostButton(
                        text = "Clear",
                        modifier = Modifier.weight(1f)
                    ) {
                        flyerUrl = ""
                        flyerUploaded = false
                        flyerUploading = false
                    }
                }
                FlyerPreviewCard(
                    isUploading = flyerUploading,
                    hasFlyer = flyerUploaded || flyerUrl.isNotBlank(),
                    flyerLabel = if (flyerUrl.isBlank()) "Your flyer will be shown here" else flyerUrl,
                    onReplace = { flyerUploading = true },
                    onClear = {
                        flyerUrl = ""
                        flyerUploaded = false
                        flyerUploading = false
                    }
                )
            }
        }
        GlowCard {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    "Status",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Draft", "Live").forEach { option ->
                        val selected = status == option
                        Pill(
                            text = option,
                            color = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.16f) else MaterialTheme.colorScheme.surfaceVariant,
                            textColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.clickable { status = option }
                        )
                    }
                }
            }
        }
        AnimatedProgressBar(progress = progress, modifier = Modifier.fillMaxWidth())
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            GhostButton(text = "Cancel", modifier = Modifier.weight(1f)) { onBack() }
            PrimaryButton(text = "Save draft", modifier = Modifier.weight(1f)) {
                onSaveDraft(title, city, venue, dateLabel, priceFrom, status)
            }
        }
    }
}

@Composable
private fun BottomBar(
    navItems: List<NavItem>,
    current: MainScreen,
    chromeAlpha: Float,
    onSelected: (MainScreen) -> Unit,
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
    ) {
        val slotWidth = maxWidth / navItems.size

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer(alpha = chromeAlpha),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEach { item ->
                val selected = item.screen == current
                val highlight = IconCategoryColors[item.category] ?: MaterialTheme.colorScheme.primary
                val interactionSource = remember { MutableInteractionSource() }
                val pressed by interactionSource.collectIsPressedAsState()
                val selectedScale by animateFloatAsState(
                    targetValue = if (selected) 1.08f else 1f,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
                    label = "navSelectedScale-${item.label}"
                )
                val pressScale by animateFloatAsState(
                    targetValue = if (pressed) 0.94f else 1f,
                    animationSpec = tween(durationMillis = GoTickyMotion.Quick, easing = EaseOutBack),
                    label = "navPressScale-${item.label}"
                )
                val labelAlpha by animateFloatAsState(
                    targetValue = if (selected) 1f else 0.92f,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard),
                    label = "navLabel-${item.label}"
                )
                val chipHighlightAlpha by animateFloatAsState(
                    targetValue = if (selected) 0.42f else 0.22f,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard),
                    label = "navChipHighlight-${item.label}"
                )
                val chipSurfaceAlpha by animateFloatAsState(
                    targetValue = if (selected) 0.98f else 0.8f,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard),
                    label = "navChipSurface-${item.label}"
                )
                val borderAlpha by animateFloatAsState(
                    targetValue = if (selected) 0.9f else 0.35f,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard),
                    label = "navChipBorder-${item.label}"
                )
                val borderWidth by animateDpAsState(
                    targetValue = if (selected) 1.dp else 0.5.dp,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard),
                    label = "navChipBorderWidth-${item.label}"
                )

                Box(
                    modifier = Modifier.width(slotWidth),
                    contentAlignment = Alignment.Center
                ) {
                    val mistStrength = if (selected) 0.9f else 0.6f
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .drawBehind {
                                val radius = size.minDimension / 1.05f
                                val haloBrush = Brush.radialGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = mistStrength),
                                        Color.Transparent
                                    ),
                                    center = center,
                                    radius = radius
                                )
                                drawCircle(
                                    brush = haloBrush,
                                    radius = radius
                                )
                            }
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) { onSelected(item.screen) },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val chipBrush = Brush.linearGradient(
                            colors = listOf(
                                highlight.copy(alpha = chipHighlightAlpha),
                                MaterialTheme.colorScheme.surface.copy(alpha = chipSurfaceAlpha)
                            )
                        )
                        val labelBaseColor = MaterialTheme.colorScheme.onSurface
                        val labelStyle = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
                        )
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .graphicsLayer(scaleX = selectedScale * pressScale, scaleY = selectedScale * pressScale)
                                .clip(CircleShape)
                                .background(chipBrush)
                                .border(
                                    width = borderWidth,
                                    color = highlight.copy(alpha = borderAlpha),
                                    shape = CircleShape
                                )
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CompositionLocalProvider(LocalContentColor provides highlight) {
                                item.icon()
                            }
                        }
                        Text(
                            text = item.label,
                            color = labelBaseColor.copy(alpha = labelAlpha),
                            style = labelStyle
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    action: (@Composable () -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSurface
        )
        action?.invoke()
    }
}

@Composable
private fun AlertCard(
    alert: PriceAlert,
    onOpen: (PriceAlert) -> Unit,
    onShare: (PriceAlert) -> Unit,
    onAdjust: (PriceAlert) -> Unit,
) {
    val dropColor = when {
        alert.dropPercent >= 10 -> MaterialTheme.colorScheme.primary
        alert.dropPercent >= 5 -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    val dropPulse by rememberInfiniteTransition(label = "alertDropPulse").animateFloat(
        initialValue = 0.94f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(tween(800, easing = LinearEasing)),
        label = "alertDropPulseAnim"
    )
    GlowCard {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(alert.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                Pill(
                    text = "${alert.dropPercent}% drop",
                    modifier = Modifier.graphicsLayer(scaleX = dropPulse, scaleY = dropPulse),
                    color = dropColor.copy(alpha = 0.16f),
                    textColor = dropColor
                )
                Spacer(Modifier.weight(1f))
                Pill(
                    text = alert.status,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    textColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(alert.venue, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(alert.section, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
            AnimatedProgressBar(
                progress = (alert.targetPrice.toFloat() / alert.currentPrice.toFloat()).coerceIn(0f, 1f),
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Target \$${alert.targetPrice}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                Text("Now \$${alert.currentPrice}", style = MaterialTheme.typography.bodySmall, color = dropColor)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                PrimaryButton(text = "View event", modifier = Modifier.weight(1f)) { onOpen(alert) }
                GhostButton(text = "Adjust", modifier = Modifier.weight(1f)) { onAdjust(alert) }
            }
            NeonTextButton(text = "Share alert", onClick = { onShare(alert) })
        }
    }
}