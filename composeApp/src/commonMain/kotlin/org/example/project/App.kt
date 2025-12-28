@file:OptIn(kotlin.time.ExperimentalTime::class)

package org.example.project

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateSet
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.util.lerp
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.compose.ui.window.Dialog
import org.example.project.data.TicketPass
import org.example.project.data.OrderSummary
import org.example.project.data.Recommendation
import org.example.project.data.PriceAlert
import org.example.project.data.OrganizerEvent
import org.example.project.data.EventItem
import org.example.project.data.sampleAlerts
import org.example.project.data.sampleEvents
import org.example.project.data.sampleOrder
import org.example.project.data.sampleRecommendations
import org.example.project.data.sampleTickets
import org.example.project.data.sampleOrganizerEvents
import org.example.project.data.sampleNearbyEvents
import org.example.project.data.EntertainmentNewsItem
import org.example.project.data.sampleEntertainmentNews
import org.example.project.data.AdminSeed
import org.example.project.data.adminSeedByCredentials
import org.example.project.data.seedAdminProfilesIfMissing
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
import org.example.project.ui.components.ProfileAvatar
import org.example.project.ui.components.GoTickyMotion
import org.example.project.ui.components.infinitePulseAmplitude
import org.example.project.ui.components.pressAnimated
import org.example.project.ui.theme.GoTickyGradients
import org.example.project.ui.theme.GoTickyTextures
import org.example.project.ui.theme.GoTickyTheme
import org.example.project.ui.theme.IconCategory
import org.example.project.ui.theme.IconCategoryColors
import org.example.project.ui.theme.goTickyShapes
import org.example.project.platform.ImagePickerLauncher
import org.example.project.platform.rememberBannerImageStorage
import org.example.project.platform.rememberImagePicker
import org.example.project.platform.BiometricPromptConfig
import org.example.project.platform.BiometricResult
import org.example.project.platform.rememberBiometricLauncher
import org.example.project.platform.rememberProfileImageStorage
import org.example.project.platform.rememberUriPainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.Job
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import org.example.project.GoTickyFeatures
import androidx.compose.runtime.mutableStateMapOf
import kotlin.math.abs
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.random.Random
import goticky.composeapp.generated.resources.Res
import goticky.composeapp.generated.resources.allDrawableResources
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.SpanStyle
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.ZERO
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.days
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import org.example.project.data.adminSeeds

private enum class MainScreen {
    Home, Tickets, Alerts, Profile, Organizer, Admin, Map, PrivacyTerms, FAQ, Settings
}

@Composable
private fun NeonGuestProgressBar(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val clamped = progress.coerceIn(0f, 1f)
    val neonColors = listOf(Color(0xFF63FFA6), Color(0xFF4BE8FF), Color(0xFF63FFA6))
    val trackColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
    val shimmer = rememberInfiniteTransition(label = "guestProgressShimmer")
    val drift by shimmer.animateFloat(
        initialValue = -200f,
        targetValue = 320f,
        animationSpec = infiniteRepeatable(tween(durationMillis = 900, easing = LinearEasing)),
        label = "guestProgressDrift"
    )
    Canvas(
        modifier
            .height(14.dp)
            .fillMaxWidth()
    ) {
        val trackRadius = size.height / 2f

        // Track background
        drawRoundRect(
            color = trackColor,
            size = size,
            cornerRadius = CornerRadius(trackRadius, trackRadius)
        )

        // Neon bar whose width strictly follows clamped progress
        if (clamped > 0f) {
            val barWidth = size.width * clamped
            val barSize = Size(width = barWidth, height = size.height)

            // Soft glow behind the bar
            drawRoundRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        neonColors[1].copy(alpha = 0.5f),
                        Color.Transparent
                    ),
                    center = Offset(barWidth / 2f, size.height / 2f),
                    radius = barWidth.coerceAtLeast(size.height * 1.2f)
                ),
                size = barSize,
                cornerRadius = CornerRadius(trackRadius, trackRadius)
            )

            // Main neon gradient with shimmer drift
            drawRoundRect(
                brush = Brush.linearGradient(
                    colors = neonColors,
                    start = Offset(drift, 0f),
                    end = Offset(drift + 240f, 0f)
                ),
                size = barSize,
                cornerRadius = CornerRadius(trackRadius, trackRadius)
            )
        }
    }
}

@Composable
private fun SignInWarmupOverlay(progress: Float) {
    val clamped = progress.coerceIn(0f, 1f)
    val percent = (clamped * 100).roundToInt()
    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 420, easing = EaseOutBack),
        label = "signInWarmupAlpha",
    )

    LaunchedEffect(Unit) { visible = true }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
            .graphicsLayer(alpha = alpha)
            .zIndex(20f),
        contentAlignment = Alignment.Center,
    ) {
        GlowCard(
            modifier = Modifier
                .fillMaxWidth(0.82f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 22.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val badgeTint = IconCategoryColors[IconCategory.Ticket]
                        ?: MaterialTheme.colorScheme.primary
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(badgeTint.copy(alpha = 0.18f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ReceiptLong,
                            contentDescription = null,
                            tint = badgeTint,
                        )
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = "Loading your tickets",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = "Syncing your wallet, favorites & alerts…",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                NeonGuestProgressBar(
                    progress = clamped,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "$percent%",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

private fun currentGreeting(): String {
    val hour = currentHourOfDay()
    return when (hour) {
        in 5..11 -> "Good morning"
        in 12..16 -> "Good afternoon"
        in 17..21 -> "Good evening"
        else -> "Good night"
    }
}

@Composable
private fun SwipeEdgeHint(
    icon: ImageVector,
    text: String,
    alpha: Float,
) {
    if (alpha <= 0f) return
    Row(
        modifier = Modifier
            .graphicsLayer(alpha = alpha)
            .clip(goTickyShapes.medium)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.3f))
            .border(1.dp, GoTickyGradients.EdgeHalo, goTickyShapes.medium)
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun currentGreetingColor(): Color {
    val hour = currentHourOfDay()
    return when (hour) {
        // Soft sunrise / morning amber
        in 5..11 -> Color(0xFFFFC94A)
        // Fresh midday teal (daylight, energetic but less neon than morning/night)
        in 12..16 -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.96f)
        // Warm sunset / evening subtle brown
        in 17..21 -> Color(0xFFC49A6C)
        // Deep night violet accent
        else -> Color(0xFF7C5BFF)
    }
}

@Composable
private fun GuestIntroOverlay(progress: Float) {
    val clamped = progress.coerceIn(0f, 1f)
    val percent = (clamped * 100).roundToInt()
    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 420, easing = EaseOutBack),
        label = "guestIntroAlpha",
    )

    LaunchedEffect(Unit) { visible = true }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
            .graphicsLayer(alpha = alpha)
            .zIndex(20f),
        contentAlignment = Alignment.Center,
    ) {
        GlowCard(
            modifier = Modifier
                .fillMaxWidth(0.82f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 22.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val badgeTint = IconCategoryColors[IconCategory.Profile]
                        ?: MaterialTheme.colorScheme.primary
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(badgeTint.copy(alpha = 0.18f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null,
                            tint = badgeTint,
                        )
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = "Guest mode",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = "Warming up your home feed…",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                NeonGuestProgressBar(
                    progress = clamped,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "$percent%",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun AdminGateScreen(
    role: String,
    flagEnabled: Boolean,
    onBack: () -> Unit,
    onRequestAccess: () -> Unit
) {
    val tint = IconCategoryColors[IconCategory.Admin] ?: MaterialTheme.colorScheme.primary
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GoTickyGradients.CardGlow)
            .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        GlowCard(modifier = Modifier.fillMaxWidth()) {
            TopBar(
                title = "Admin access",
                onBack = onBack,
                actions = null,
                backgroundColor = Color.Transparent
            )
        }
        GlowCard(modifier = Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(tint.copy(alpha = 0.18f))
                            .border(1.dp, tint.copy(alpha = 0.4f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Shield,
                            contentDescription = "Admin shield",
                            tint = tint,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = if (flagEnabled) "Restricted role" else "Feature disabled",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (flagEnabled) {
                                "Current role: $role. Only Admin accounts can enter the control room."
                            } else {
                                "Admin feature is turned off by feature flag right now."
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                AnimatedProgressBar(progress = 0.55f)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    PrimaryButton(
                        text = "Request access",
                        modifier = Modifier.weight(1f),
                        onClick = onRequestAccess
                    )
                    GhostButton(
                        text = "Back home",
                        modifier = Modifier.weight(1f),
                        onClick = onBack
                    )
                }
                NeonTextButton(
                    text = "Why is this locked?",
                    onClick = onRequestAccess
                )
            }
        }

    }
}

@Composable
private fun EventMapScreen(
    onBack: () -> Unit,
    onOpenEvent: (String) -> Unit,
) {
    val events = remember {
        sampleNearbyEvents.map { nearby ->
            // Sample coordinates approximating real cities; replace with backend data when available.
            val (lat, lng) = when (nearby.event.city) {
                "Harare" -> -17.8292 to 31.0522
                "Bulawayo" -> -20.1325 to 28.6265
                "Gaborone" -> -24.6282 to 25.9231
                "Victoria Falls" -> -17.9243 to 25.8562
                "Maun" -> -19.9833 to 23.4167
                "Francistown" -> -21.1700 to 27.5072
                else -> -17.8292 to 31.0522
            }
            MapEvent(
                id = nearby.event.id,
                title = nearby.event.title,
                city = "${nearby.event.city} • ${nearby.distance.formatted}",
                lat = lat,
                lng = lng,
            )
        }
    }
    var selected by remember { mutableStateOf<MapEvent?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GoTickyGradients.CardGlow),
        verticalArrangement = Arrangement.Top
    ) {
        GlowCard(modifier = Modifier.fillMaxWidth()) {
            TopBar(
                title = "Live map",
                onBack = onBack,
                actions = null,
                backgroundColor = Color.Transparent
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            EventMapView(
                events = events,
                modifier = Modifier.fillMaxSize(),
                onEventSelected = { event ->
                    selected = event
                }
            )
        }
        selected?.let { event ->
            GlowCard(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = event.city,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        PrimaryButton(text = "Open details") {
                            onOpenEvent(event.id)
                        }
                        GhostButton(text = "Close card") {
                            selected = null
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HeroPagerIndicator(
    count: Int,
    listState: LazyListState,
    modifier: Modifier = Modifier,
) {
    if (count <= 1) return
    val layoutInfo = listState.layoutInfo
    val viewportWidth = layoutInfo.viewportSize.width
    val viewportCenter = layoutInfo.viewportStartOffset + viewportWidth / 2
    val currentIndex = layoutInfo.visibleItemsInfo.minByOrNull { info ->
        val itemCenter = info.offset + info.size / 2
        abs(itemCenter - viewportCenter)
    }?.index ?: listState.firstVisibleItemIndex

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(count) { i ->
            val active = i == currentIndex
            val width by animateDpAsState(
                targetValue = if (active) 24.dp else 10.dp,
                animationSpec = tween(260),
                label = "heroDotWidth-$i"
            )
            val dotAlpha by animateFloatAsState(
                targetValue = if (active) 1f else 0.45f,
                animationSpec = tween(260),
                label = "heroDotAlpha-$i"
            )
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(width)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.6f * dotAlpha))
            )
        }
    }
}

private data class NavItem(
    val screen: MainScreen,
    val label: String,
    val icon: @Composable () -> Unit,
    val category: IconCategory,
)

private data class PersonalizationPrefs(val genres: List<String>, val city: String)
private data class LaunchCheck(val id: String, val title: String, val desc: String)
private data class AdminKpi(val title: String, val value: String, val delta: String, val accent: Color)
private data class AdminAttention(val title: String, val subtitle: String, val severity: String)
private data class AdminActivity(val text: String, val time: String, val accent: Color)
private enum class AdminSurface { Dashboard, Applications, Moderation, Organizer, Catalog, Banners, Settings }
private enum class BottomNavVisibility { Visible, Hidden }
private enum class GuestGateTarget { Checkout, Organizer }
data class AdminApplication(
    val id: String,
    val eventId: String,
    val title: String,
    val organizer: String,
    val city: String,
    val category: String,
    var status: String,
    val risk: String,
    val ageHours: Int,
    val completeness: Int,
    val attachmentsReady: Boolean,
    val notes: String,
    val eventDateTime: String = "",
    val pricingTiers: List<String> = emptyList(),
    var approvedAt: Instant? = null,
    val earlyBirdEnabled: Boolean = true,
    val earlyBirdDiscountPercent: Int = 20,
    val earlyBirdDurationHours: Int = 72,
    val earlyBirdManualStartAt: Instant? = null,
    val earlyBirdPaused: Boolean = false,
)
private data class EarlyBirdWindow(
    val start: Instant,
    val end: Instant,
    val discountPercent: Int,
    val basePriceLabel: String,
    val earlyPriceLabel: String,
)

@kotlin.OptIn(kotlin.time.ExperimentalTime::class)
private fun formatDurationShort(duration: Duration): String {
    if (duration <= ZERO) return "0m"
    var rem = duration
    val days = rem.inWholeDays
    rem -= days.days
    val hours = rem.inWholeHours
    rem -= hours.hours
    val minutes = (rem.inWholeMinutes).coerceAtLeast(0)
    return buildString {
        if (days > 0) append("${days}d ")
        if (hours > 0) append("${hours}h ")
        append("${minutes}m")
    }.trim()
}

private fun parsePrice(value: String): Double? {
    val numeric = Regex("[0-9]+(\\.[0-9]+)?").find(value)?.value ?: return null
    return numeric.toDoubleOrNull()
}

private fun formatPriceTwoDecimals(value: Double): String {
    val roundedCents = (value * 100.0).roundToInt()
    val major = roundedCents / 100
    val minor = roundedCents % 100
    return "$major.${minor.toString().padStart(2, '0')}"
}

private fun buildEarlyBirdWindow(app: AdminApplication?, approvedAt: Instant?): EarlyBirdWindow? {
    if (app == null) return null
    // Only allow time window once admin has approved to avoid selling before review.
    if (app.status != "Approved") return null
    if (!app.earlyBirdEnabled || app.earlyBirdDurationHours <= 0 || app.earlyBirdDiscountPercent <= 0) return null
    if (app.earlyBirdPaused) return null
    val startAnchor = app.earlyBirdManualStartAt ?: approvedAt ?: currentInstant()
    val durationHours = app.earlyBirdDurationHours.coerceIn(1, 24 * 14)
    val end = startAnchor + durationHours.hours
    val baseLabel = app.pricingTiers.firstOrNull {
        it.contains("GA", ignoreCase = true) || it.contains("General", ignoreCase = true)
    } ?: app.pricingTiers.firstOrNull().orEmpty()
    val base = parsePrice(baseLabel) ?: 0.0
    val discount = app.earlyBirdDiscountPercent.coerceIn(5, 80)
    val earlyPrice = (base * (1 - discount / 100.0)).coerceAtLeast(1.0)
    val earlyLabel = if (earlyPrice % 1.0 == 0.0) "$$${earlyPrice.toInt()}" else "$$${formatPriceTwoDecimals(earlyPrice)}"
    val baseDisplay = if (base > 0) {
        if (base % 1.0 == 0.0) "$$${base.toInt()}" else "$$${formatPriceTwoDecimals(base)}"
    } else baseLabel.ifBlank { "Base price" }
    return EarlyBirdWindow(
        start = startAnchor,
        end = end,
        discountPercent = discount,
        basePriceLabel = baseDisplay,
        earlyPriceLabel = earlyLabel
    )
}

private fun isEventPublic(eventId: String, apps: List<AdminApplication>): Boolean {
    val app = apps.firstOrNull { it.eventId == eventId } ?: return true
    return app.status == "Approved"
}

private fun buildEarlyBirdBadgeForEvent(
    event: EventItem,
    apps: List<AdminApplication>,
): Pair<String?, Boolean> {
    if (!isEventPublic(event.id, apps)) return null to false
    val app = apps.firstOrNull { it.eventId == event.id } ?: return null to false
    val window = buildEarlyBirdWindow(app, app.approvedAt) ?: return null to false
    val now = currentInstant()
    if (now < window.start || now > window.end) return null to false
    val remaining = (window.end - now).coerceAtLeast(ZERO)
    val highlight = remaining <= 6.hours
    val label = "Early Bird ${window.earlyPriceLabel} • ${formatDurationShort(remaining)} left"
    return label to highlight
}
private data class AdminReport(
    val id: String,
    val target: String,
    val reason: String,
    val severity: String,
    val ageHours: Int,
    var state: String,
)
private data class AdminFeatureFlag(
    val key: String,
    val label: String,
    var enabled: Boolean,
    val audience: String,
)
private data class AdminOrganizer(
    val id: String,
    val name: String,
    val kycStatus: String,
    val trustScore: Int,
    val strikes: Int,
    val frozen: Boolean,
    val notes: String,
)
private data class AdminRoleEntry(
    val id: String,
    val name: String,
    var role: String,
    val email: String,
)
private data class HeroSlide(
    val id: String,
    val title: String,
    val subtitle: String,
    val cta: String,
    val tag: String,
    val accent: Color,
    val imageHint: String, // descriptive hint for the “image background”
    val location: String,
    val heroImageKey: String? = null,
    val imageUrl: String? = null,
    val accentHex: String? = null,
    val order: Int = 0,
    val active: Boolean = true,
)
private data class BannerDraft(
    val id: String = "",
    val title: String = "",
    val subtitle: String = "",
    val cta: String = "",
    val tag: String = "",
    val location: String = "",
    val imageHint: String = "",
    val accent: Color = Color(0xFF9C7BFF),
    val accentHex: String = "#9C7BFF",
    val order: Int = 0,
    val active: Boolean = true,
    val imageUrl: String? = null,
    val localImageUri: String? = null,
)
private data class MenuItemSpec(
    val label: String,
    val icon: ImageVector,
    val tint: Color,
    val trailing: (@Composable (() -> Unit))? = null,
    val onClick: () -> Unit,
)

data class UserProfile(
    val fullName: String,
    val email: String,
    val countryName: String,
    val countryFlag: String,
    val phoneCode: String,
    val phoneNumber: String,
    val birthday: String,
    val gender: String,
    val photoResKey: String? = null,
    val photoUri: String? = null,
    val favorites: List<String> = emptyList(),
    val role: String = "customer",
)

private val UserProfileSaver = listSaver<UserProfile, Any>(
    save = {
        listOf(
            it.fullName,
            it.email,
            it.countryName,
            it.countryFlag,
            it.phoneCode,
            it.phoneNumber,
            it.birthday,
            it.gender,
            it.photoResKey ?: "",
            it.photoUri ?: "",
            it.favorites,
            it.role
        )
    },
    restore = {
        UserProfile(
            fullName = it[0] as String,
            email = it[1] as String,
            countryName = it[2] as String,
            countryFlag = it[3] as String,
            phoneCode = it[4] as String,
            phoneNumber = it[5] as String,
            birthday = it[6] as String,
            gender = it[7] as String,
            photoResKey = (it[8] as? String).orEmpty().ifBlank { null },
            photoUri = (it[9] as? String).orEmpty().ifBlank { null },
            favorites = (it.getOrNull(10) as? List<String>).orEmpty(),
            role = (it.getOrNull(11) as? String).orEmpty().ifBlank { "customer" },
        )
    }
)

private val launchChecklist = listOf(
    LaunchCheck("palette", "Palette frozen", "Final brand + semantic tokens locked in Color.kt; icon tints distinct."),
    LaunchCheck("motion", "Motion verified", "Buttons, FAB, progress, spinners, back animation follow visible motion spec."),
    LaunchCheck("screens", "MVP screens compile", "Home, Search/Filters, Detail, Seat stub, Checkout, Tickets, Alerts, Profile, Organizer stub."),
    LaunchCheck("analytics", "Analytics + flags", "Key events logged; payments guarded by feature flag."),
    LaunchCheck("backend", "Firestore draft", "Schemas/rules drafted for events/tickets/orders/alerts/recs."),
)

private val defaultHeroSlides = listOf(
    HeroSlide(
        id = "hero1",
        title = "Vic Falls Midnight Lights",
        subtitle = "NYE mega festival under the spray • DJs + fireworks on the gorge deck.",
        cta = "Book premium",
        tag = "Event of the Year",
        accent = Color(0xFF5CF0FF),
        imageHint = "Victoria Falls bridge at night with fireworks and crowd silhouettes",
        location = "Victoria Falls, ZW",
        heroImageKey = "hero_vic_falls_midnight_lights",
    ),
    HeroSlide(
        id = "hero2",
        title = "Harare Jazz Nights",
        subtitle = "Golden Circle early birds: 20% off until Friday • Smooth & soul live.",
        cta = "Unlock early bird",
        tag = "Early Bird",
        accent = Color(0xFFF5C94C),
        imageHint = "Harare skyline dusk with intimate jazz stage lighting",
        location = "Harare, ZW",
        heroImageKey = "hero_harare_jazz_nights",
    ),
    HeroSlide(
        id = "hero3",
        title = "Bulawayo Food & Arts Market",
        subtitle = "Night market • street eats, live mural painting, vinyl DJ courtyard.",
        cta = "Explore lineup",
        tag = "Culture",
        accent = Color(0xFF9C7BFF),
        imageHint = "Bulawayo street market with food stalls and neon art",
        location = "Bulawayo, ZW",
        heroImageKey = "hero_byo_food_arts",
    ),
)

private fun currentTimestampIsoString(): String = currentInstant().toString()

private fun colorFromHex(hex: String?): Color {
    val cleaned = hex?.trim()?.removePrefix("#") ?: return Color(0xFF9C7BFF)
    return runCatching { cleaned.toLong(16) }
        .map { value ->
            val long = if (cleaned.length <= 6) (0xFF000000L or value) else value
            Color(long.toInt())
        }
        .getOrElse { Color(0xFF9C7BFF) }
}

private fun colorToHex(color: Color): String {
    val r = (color.red * 255).roundToInt().coerceIn(0, 255)
    val g = (color.green * 255).roundToInt().coerceIn(0, 255)
    val b = (color.blue * 255).roundToInt().coerceIn(0, 255)
    fun Int.toHex2(): String = this.toString(16).padStart(2, '0').uppercase()
    return "#" + r.toHex2() + g.toHex2() + b.toHex2()
}

private suspend fun fetchHeroBannersFromFirestore(): List<HeroSlide> {
    return try {
        ensureSettingsSession()
        val snap = Firebase.firestore
            .collection("banners")
            .orderBy("order")
            .get()
        snap.documents.mapNotNull { doc ->
            val id = doc.id
            val title = doc.get<String?>("title") ?: return@mapNotNull null
            val subtitle = doc.get<String?>("subtitle") ?: ""
            val cta = doc.get<String?>("cta") ?: "Explore"
            val tag = doc.get<String?>("tag") ?: ""
            val accentHex = doc.get<String?>("accentHex") ?: "#9C7BFF"
            val accent = colorFromHex(accentHex)
            val imageHint = doc.get<String?>("imageHint") ?: ""
            val location = doc.get<String?>("location") ?: ""
            val heroImageKey = doc.get<String?>("heroImageKey")
            val imageUrl = doc.get<String?>("imageUrl")
            val order = doc.get<Long?>("order")?.toInt() ?: 0
            val active = doc.get<Boolean?>("active") ?: true
            HeroSlide(
                id = id,
                title = title,
                subtitle = subtitle,
                cta = cta,
                tag = tag,
                accent = accent,
                imageHint = imageHint,
                location = location,
                heroImageKey = heroImageKey,
                imageUrl = imageUrl,
                accentHex = accentHex,
                order = order,
                active = active,
            )
        }
    } catch (_: Throwable) {
        emptyList()
    }
}

private suspend fun saveHeroBannerToFirestore(slide: HeroSlide) {
    runCatching {
        ensureSettingsSession()
        Firebase.firestore
            .collection("banners")
            .document(slide.id)
            .set(
                mapOf(
                    "title" to slide.title,
                    "subtitle" to slide.subtitle,
                    "cta" to slide.cta,
                    "tag" to slide.tag,
                    "accentHex" to (slide.accentHex ?: colorToHex(slide.accent)),
                    "imageHint" to slide.imageHint,
                    "location" to slide.location,
                    "heroImageKey" to slide.heroImageKey,
                    "imageUrl" to slide.imageUrl,
                    "order" to slide.order,
                    "active" to slide.active,
                ),
                merge = true
            )
    }
}

private suspend fun deleteHeroBannerFromFirestore(id: String) {
    runCatching {
        ensureSettingsSession()
        Firebase.firestore
            .collection("banners")
            .document(id)
            .delete()
    }
}

private fun resolveProfilePhotoRes() =
    Res.allDrawableResources["gotickypic"]
        ?: Res.allDrawableResources["hero_vic_falls_midnight_lights"]

private fun defaultUserProfile() = UserProfile(
    fullName = "GoTicky Guest",
    email = "guest@goticky.app",
    countryName = "Zimbabwe",
    countryFlag = "🇿🇼",
    phoneCode = "+263",
    phoneNumber = "",
    birthday = "",
    gender = "Guest",
    photoResKey = "gotickypic",
    photoUri = null,
    favorites = emptyList(),
    role = "customer",
)

@Composable
private fun profilePainter(profile: UserProfile): Painter? {
    profile.photoUri?.let { uri ->
        rememberUriPainter(uri)?.let { return it }
    }
    val key = profile.photoResKey ?: "gotickypic"
    val res = Res.allDrawableResources[key] ?: resolveProfilePhotoRes()
    return res?.let { painterResource(it) }
}

private val PersonalizationPrefsSaver = listSaver<PersonalizationPrefs, Any>(
    save = { listOf(it.genres, it.city) },
    restore = {
        val genres = (it.getOrNull(0) as? List<String>) ?: emptyList()
        val city = (it.getOrNull(1) as? String) ?: ""
        PersonalizationPrefs(genres, city)
    }
)

private data class SettingsPrefs(
    val pushEnabled: Boolean = true,
    val emailUpdates: Boolean = true,
    val dataSaver: Boolean = false,
    val hapticsEnabled: Boolean = true,
    val theme: String = "Auto", // Auto | Light | Dark
    val rememberMe: Boolean = false,
)

private data class AdminSecureConfig(
    val email: String,
    val password: String,
    val rememberMe: Boolean = true,
    // Human-friendly admin profile key, e.g. "Kate Mula".
    // This lets us store admin app_settings under /adminProfiles/{adminName}/app_settings/admin_secure.
    val adminName: String? = null,
)

private val SettingsPrefsSaver = listSaver<SettingsPrefs, Any>(
    save = { listOf(it.pushEnabled, it.emailUpdates, it.dataSaver, it.hapticsEnabled, it.theme, it.rememberMe) },
    restore = {
        SettingsPrefs(
            pushEnabled = (it.getOrNull(0) as? Boolean) ?: true,
            emailUpdates = (it.getOrNull(1) as? Boolean) ?: true,
            dataSaver = (it.getOrNull(2) as? Boolean) ?: false,
            hapticsEnabled = (it.getOrNull(3) as? Boolean) ?: true,
            theme = (it.getOrNull(4) as? String) ?: "Auto",
            rememberMe = (it.getOrNull(5) as? Boolean) ?: false,
        )
    }
)

private val SearchHistorySaver = listSaver<SnapshotStateList<String>, String>(
    save = { stateList -> stateList.toList() },
    restore = { saved -> mutableStateListOf(*saved.toTypedArray()) }
)

private suspend fun ensureSettingsSession() {
    val auth = Firebase.auth
    if (auth.currentUser == null) {
        runCatching { auth.signInAnonymously() }
    }
}

private suspend fun loadUserSettingsFromFirestore(uid: String): SettingsPrefs? {
    return try {
        val doc = Firebase.firestore
            .collection("users")
            .document(uid)
            .collection("app_settings")
            .document("preferences")
            .get()

        if (!doc.exists) {
            null
        } else {
            SettingsPrefs(
                pushEnabled = doc.get<Boolean?>("pushEnabled") ?: true,
                emailUpdates = doc.get<Boolean?>("emailUpdates") ?: true,
                dataSaver = doc.get<Boolean?>("dataSaver") ?: false,
                hapticsEnabled = doc.get<Boolean?>("hapticsEnabled") ?: true,
                theme = doc.get<String?>("theme") ?: "Auto",
                rememberMe = doc.get<Boolean?>("rememberMe") ?: false,
            )
        }
    } catch (_: Throwable) {
        null
    }
}

private suspend fun saveUserSettingsToFirestore(uid: String, prefs: SettingsPrefs) {
    try {
        Firebase.firestore
            .collection("users")
            .document(uid)
            .collection("app_settings")
            .document("preferences")
            .set(
                mapOf(
                    "pushEnabled" to prefs.pushEnabled,
                    "emailUpdates" to prefs.emailUpdates,
                    "dataSaver" to prefs.dataSaver,
                    "hapticsEnabled" to prefs.hapticsEnabled,
                    "theme" to prefs.theme,
                    "rememberMe" to prefs.rememberMe,
                ),
                merge = true
            )
    } catch (_: Throwable) {
        // Best-effort only: settings persistence issues should not break the app.
    }
}

private suspend fun fetchAdminSecureConfig(): Result<AdminSecureConfig> {
    return runCatching {
        ensureSettingsSession()
        val firestore = Firebase.firestore

        // 1) Prefer per-admin app_settings under /adminProfiles/{adminName}/app_settings/admin_secure.
        // We iterate over the known seeds and return the first valid config we find.
        val fromPerAdmin: AdminSecureConfig? = adminSeeds.firstNotNullOfOrNull { seed ->
            val doc = firestore
                .collection("adminProfiles")
                .document(seed.fullName)
                .collection("app_settings")
                .document("admin_secure")
                .get()

            if (!doc.exists) return@firstNotNullOfOrNull null

            val email = doc.get<String?>("email")?.trim().orEmpty()
            val password = doc.get<String?>("password").orEmpty()
            val rememberMe = doc.get<Boolean?>("rememberMe") ?: true
            if (email.isBlank() || password.isBlank()) null
            else AdminSecureConfig(email, password, rememberMe, adminName = seed.fullName)
        }

        // 2) Legacy fallback: global /app_settings/admin_secure (for backwards compatibility).
        val fromLegacy: AdminSecureConfig? = runCatching {
            val legacyDoc = firestore
                .collection("app_settings")
                .document("admin_secure")
                .get()

            if (!legacyDoc.exists) return@runCatching null

            val email = legacyDoc.get<String?>("email")?.trim().orEmpty()
            val password = legacyDoc.get<String?>("password").orEmpty()
            val rememberMe = legacyDoc.get<Boolean?>("rememberMe") ?: true

            if (email.isBlank() || password.isBlank()) null else {
                // Try to infer the adminName from seeds based on email.
                val seedMatch = adminSeeds.firstOrNull { it.email.equals(email, ignoreCase = true) }
                AdminSecureConfig(
                    email = email,
                    password = password,
                    rememberMe = rememberMe,
                    adminName = seedMatch?.fullName
                ).also {
                    // Best-effort one-way migration into the new per-admin location.
                    runCatching { saveAdminSecureConfig(it) }
                }
            }
        }.getOrNull()

        // 3) Seed-based default if nothing is configured yet.
        val fromSeed = adminSeeds.firstOrNull()?.let { seed ->
            AdminSecureConfig(
                email = seed.email,
                password = seed.password,
                rememberMe = true,
                adminName = seed.fullName,
            ).also {
                // Best-effort seed into Firestore so subsequent secure clicks have a stored config.
                runCatching { saveAdminSecureConfig(it) }
            }
        }

        fromPerAdmin
            ?: fromLegacy
            ?: fromSeed
            ?: throw IllegalStateException("admin_secure settings unavailable.")
    }
}

private suspend fun seedAdminSecureConfig(): AdminSecureConfig? {
    val seed = adminSeeds.firstOrNull() ?: return null
    val config = AdminSecureConfig(
        email = seed.email,
        password = seed.password,
        rememberMe = true,
        adminName = seed.fullName,
    )
    runCatching { saveAdminSecureConfig(config) }
    return config
}

private suspend fun saveAdminSecureConfig(config: AdminSecureConfig) {
    runCatching {
        ensureSettingsSession()
        val firestore = Firebase.firestore

        // Persist under the admin's profile document: /adminProfiles/{adminName}/app_settings/admin_secure
        val adminName = config.adminName
            ?: adminSeeds.firstOrNull { it.email.equals(config.email, ignoreCase = true) }?.fullName

        if (adminName != null) {
            firestore
                .collection("adminProfiles")
                .document(adminName)
                .collection("app_settings")
                .document("admin_secure")
                .set(
                    mapOf(
                        "email" to config.email,
                        "password" to config.password,
                        "rememberMe" to config.rememberMe,
                    ),
                    merge = true
                )
        }
    }
}

private suspend fun loadAdminSecureConfigForPrefill(): AdminSecureConfig? {
    ensureSettingsSession()
    val firestore = Firebase.firestore

    return adminSeeds.firstNotNullOfOrNull { seed ->
        val doc = firestore
            .collection("adminProfiles")
            .document(seed.fullName)
            .collection("app_settings")
            .document("admin_secure")
            .get()

        if (!doc.exists) return@firstNotNullOfOrNull null

        val rememberMe = doc.get<Boolean?>("rememberMe") ?: true
        if (!rememberMe) return@firstNotNullOfOrNull null

        val email = doc.get<String?>("email")?.trim().orEmpty()
        val password = doc.get<String?>("password").orEmpty()
        if (email.isBlank() || password.isBlank()) null
        else AdminSecureConfig(email, password, rememberMe, adminName = seed.fullName)
    }
}

@Composable
fun App() {
    LaunchedEffect(Unit) { initFirebase() }
    GoTickyTheme {
        GoTickyRoot()
    }
}

@Composable
private fun MapPreview(
    onOpenMap: () -> Unit,
) {
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
        PrimaryButton(text = "Open map", onClick = onOpenMap)
    }
}

@Composable
private fun SeatPreview() {
    var showComingSoon by remember { mutableStateOf(false) }

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
            }
        }
    }
    if (showComingSoon) {
        AlertDialog(
            onDismissRequest = { showComingSoon = false },
            title = { Text("Coming soon") },
            text = {
                var visible by remember { mutableStateOf(false) }
                val scale by animateFloatAsState(
                    targetValue = if (visible) 1f else 0.9f,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
                    label = "seatPreviewComingSoonScale"
                )
                LaunchedEffect(Unit) { visible = true }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale)
                ) {
                    Text(
                        text = "Coming soon: deeper seat maps, VIP flows and alerts in this GoTicky demo.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "For now, explore tonight's heat and alerts as interactive previews.",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                NeonTextButton(text = "Nice", onClick = { showComingSoon = false })
            }
        )
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
    var checkoutReturnEvent by remember { mutableStateOf<org.example.project.data.EventItem?>(null) }
    var selectedTicket by remember { mutableStateOf<TicketPass?>(null) }
    var showCheckout by remember { mutableStateOf(false) }
    var checkoutSuccess by remember { mutableStateOf(false) }
    var lastCheckoutMethod by remember { mutableStateOf<String?>(null) }
    var lastCheckoutAmount by remember { mutableStateOf<Int?>(null) }
    var showLogoutConfirm by remember { mutableStateOf(false) }
    var selectedTicketType by remember { mutableStateOf("General / Standard") }
    var userProfile by rememberSaveable(stateSaver = UserProfileSaver) { mutableStateOf(defaultUserProfile()) }
    var currentUserRole by rememberSaveable { mutableStateOf("customer") } // Admin shell gate: Admin only
    val adminAccessRoles = remember { setOf("Admin") }
    val adminFeatureFlagEnabled = GoTickyFeatures.EnableAdmin
    val hasAdminAccess by remember(adminFeatureFlagEnabled, currentUserRole) {
        derivedStateOf { adminFeatureFlagEnabled && adminAccessRoles.contains(currentUserRole) }
    }
    var showAdminGateDialog by remember { mutableStateOf(false) }
    var adminGateMessage by remember { mutableStateOf("Admin access is restricted to Admin accounts.") }
    var showGuestGateDialog by remember { mutableStateOf(false) }
    var guestGateTarget by remember { mutableStateOf<GuestGateTarget?>(null) }
    var guestGateMessage by remember { mutableStateOf("Sign up to unlock this action.") }
    val alerts = remember { mutableStateListOf<PriceAlert>(*sampleAlerts.toTypedArray()) }
    val recommendations = remember { sampleRecommendations }
    val organizerEvents = remember { mutableStateListOf<OrganizerEvent>(*sampleOrganizerEvents.toTypedArray()) }
    var showCreateEvent by remember { mutableStateOf(false) }
    var selectedOrganizerEvent by remember { mutableStateOf<OrganizerEvent?>(null) }
    var isScrolling by remember { mutableStateOf(false) }
    var showIntro by rememberSaveable { mutableStateOf(true) }
    var isAuthenticated by rememberSaveable { mutableStateOf(false) }
    var isGuestMode by rememberSaveable { mutableStateOf(false) }
    var showAdminSignIn by rememberSaveable { mutableStateOf(false) }
    var isGuestIntroActive by rememberSaveable { mutableStateOf(false) }
    var guestIntroProgress by remember { mutableStateOf(0f) }
    var isSignInWarmupActive by rememberSaveable { mutableStateOf(false) }
    var signInWarmupProgress by remember { mutableStateOf(0f) }
    var secureSignInInProgress by remember { mutableStateOf(false) }
    val authRepo = remember { FirebaseAuthRepository() }
    val profileImageStorage = rememberProfileImageStorage()
    val bannerImageStorage = rememberBannerImageStorage()
    val biometricLauncher = rememberBiometricLauncher()
    val scope = rememberCoroutineScope()
    var logoutInProgress by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    var postSignUpUploadInProgress by remember { mutableStateOf(false) }
    var postSignUpUploadProgress by remember { mutableStateOf(0f) }
    var postSignUpUploadMessage by remember { mutableStateOf<String?>(null) }
    var postSignUpUploadError by remember { mutableStateOf<String?>(null) }
    var pendingUploadProfile by remember { mutableStateOf<UserProfile?>(null) }
    var autoRetryAttempted by remember { mutableStateOf(false) }
    val heroSlides = remember {
        mutableStateListOf<HeroSlide>().apply { addAll(defaultHeroSlides) }
    }
    var loadingHeroBanners by remember { mutableStateOf(false) }
    var simulatedHeroBannerId by remember { mutableStateOf<String?>(null) }
    var bannerDraft by remember {
        mutableStateOf(
            BannerDraft(
                id = "banner-${currentTimestampIsoString()}",
                accent = Color(0xFF9C7BFF),
                accentHex = "#9C7BFF",
                order = 0,
                active = true
            )
        )
    }
    var bannerSaving by remember { mutableStateOf(false) }
    var bannerSavingProgress by remember { mutableStateOf(0f) }
    val bannerPicker = rememberImagePicker { uri ->
        uri?.let { bannerDraft = bannerDraft.copy(localImageUri = it) }
    }
    var relaxJob by remember { mutableStateOf<Job?>(null) }

    fun requireAuth(
        target: GuestGateTarget,
        message: String,
        onAllowed: () -> Unit,
    ) {
        if (!isAuthenticated || isGuestMode) {
            guestGateTarget = target
            guestGateMessage = message
            showGuestGateDialog = true
        } else {
            onAllowed()
        }
    }

    var postSignUpMessage by remember { mutableStateOf<String?>(null) }
    val favoriteEvents = rememberSaveable { mutableStateListOf<String>() }
    fun loadFavoritesFromProfile(profile: UserProfile) {
        favoriteEvents.clear()
        favoriteEvents.addAll(profile.favorites)
    }
    suspend fun syncFavoritesFromBackend(profile: UserProfile? = null) {
        val remoteFavorites = profile?.favorites ?: authRepo.fetchFavorites()
        favoriteEvents.clear()
        favoriteEvents.addAll(remoteFavorites)
        userProfile = (profile ?: userProfile).copy(favorites = remoteFavorites)
    }
    suspend fun persistFavorites() {
        val updatedFavorites = favoriteEvents.toList().distinct()
        userProfile = userProfile.copy(favorites = updatedFavorites)
        when (val result = authRepo.updateFavorites(updatedFavorites)) {
            is AuthResult.Error -> snackbarHostState.showSnackbar(result.message)
            else -> Unit
        }
    }
    fun toggleFavorite(eventId: String) {
        val signedInUser = authRepo.currentUser()
        if (!isAuthenticated || signedInUser == null) {
            scope.launch { snackbarHostState.showSnackbar("Sign in to save favorites") }
            return
        }
        if (favoriteEvents.contains(eventId)) favoriteEvents.remove(eventId) else favoriteEvents.add(eventId)
        scope.launch { persistFavorites() }
    }
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

    // Seed admin auth accounts and Firestore profiles once when the app boots.
    LaunchedEffect(Unit) {
        runCatching { seedAdminProfilesIfMissing() }
    }
    var personalizationPrefs by rememberSaveable(stateSaver = PersonalizationPrefsSaver) {
        mutableStateOf(
            PersonalizationPrefs(
                genres = listOf("Concerts", "Sports", "Comedy", "Family"),
                city = "Harare"
            )
        )
    }
    val searchHistory = rememberSaveable(saver = SearchHistorySaver) { mutableStateListOf<String>() }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var forceOpenSearchDialog by remember { mutableStateOf(false) }
    var settingsPrefs by rememberSaveable(stateSaver = SettingsPrefsSaver) { mutableStateOf(SettingsPrefs()) }
    var authInitDone by rememberSaveable { mutableStateOf(false) }
    var adminSecurePrefill by remember { mutableStateOf<AdminSecureConfig?>(null) }
    LaunchedEffect(Unit) {
        val existing = authRepo.currentUser()
        val uid = existing?.uid
        if (uid != null) {
            val remote = loadUserSettingsFromFirestore(uid)
            if (remote != null) {
                settingsPrefs = remote
            }

            if (settingsPrefs.rememberMe && !isAuthenticated) {
                val fetched = authRepo.fetchProfile()
                if (fetched != null) {
                    val role = fetched.role.ifBlank { "customer" }
                    userProfile = fetched
                    currentUserRole = role
                    syncFavoritesFromBackend(fetched)

                    if (!role.equals("admin", ignoreCase = true)) {
                        // For customers, honor remember-me by auto-restoring the session.
                        isAuthenticated = true
                        isGuestMode = false
                        currentScreen = MainScreen.Home
                        isSignInWarmupActive = true
                    }
                    // For admins with remember-me enabled, keep isAuthenticated=false so the
                    // app still flows through Intro -> Sign-in -> Secure admin click.
                } else {
                    syncFavoritesFromBackend()
                }
            } else {
                syncFavoritesFromBackend()
            }
        }
        authInitDone = true
    }
    LaunchedEffect(Unit) {
        Analytics.log(
            AnalyticsEvent(
                name = "app_open",
                params = mapOf("role" to currentUserRole, "admin_flag" to adminFeatureFlagEnabled.toString())
            )
        )
    }

    val adminApplications = remember {
        mutableStateListOf(
            AdminApplication(
                id = "app-1",
                eventId = "1",
                title = "Midnight Neon Fest",
                organizer = "Neon Live",
                city = "Harare",
                category = "EDM",
                status = "Approved",
                risk = "Low",
                ageHours = 4,
                completeness = 82,
                attachmentsReady = true,
                notes = "All docs present",
                eventDateTime = "Dec 22, 2025 • 20:00",
                pricingTiers = listOf("Early bird $25", "GA $40", "VIP $90")
            ),
            AdminApplication(
                id = "app-2",
                eventId = "6",
                title = "Bulawayo Street Fest",
                organizer = "City Beats",
                city = "Bulawayo",
                category = "Festival",
                status = "In Review",
                risk = "High",
                ageHours = 14,
                completeness = 54,
                attachmentsReady = false,
                notes = "Missing permit upload",
                eventDateTime = "Jan 10, 2026 • 14:00",
                pricingTiers = listOf("Free entry", "Food court tokens $10")
            ),
            AdminApplication(
                id = "app-3",
                eventId = "4",
                title = "Family Lights Parade",
                organizer = "Sunrise Events",
                city = "Victoria Falls",
                category = "Family",
                status = "Needs Info",
                risk = "Medium",
                ageHours = 28,
                completeness = 70,
                attachmentsReady = true,
                notes = "Clarify road closure",
                eventDateTime = "Dec 29, 2025 • 18:30",
                pricingTiers = listOf("Adult $15", "Child $8", "Family pack $40")
            ),
            AdminApplication(
                id = "app-4",
                eventId = "5",
                title = "Savanna Sky Sessions",
                organizer = "VibeCo",
                city = "Maun",
                category = "Live band",
                status = "New",
                risk = "Medium",
                ageHours = 6,
                completeness = 66,
                attachmentsReady = true,
                notes = "Check pricing rationale",
                eventDateTime = "Jan 4, 2026 • 19:00",
                pricingTiers = listOf("GA $35", "VIP deck $75")
            ),
        )
    }

    suspend fun startAdminSessionFromSeed(seed: AdminSeed, rememberMe: Boolean): AuthResult {
        // Ensure we have some Firebase auth session for Firestore rules when possible.
        val auth = Firebase.auth
        if (auth.currentUser == null) {
            runCatching { auth.signInAnonymously() }
        }
        val adminUid = auth.currentUser?.uid ?: seed.email.trim().lowercase()

        // Build a local admin profile shell from the seed.
        val adminProfile = UserProfile(
            fullName = seed.fullName,
            email = seed.email,
            countryName = seed.country,
            countryFlag = "\uD83C\uDDF8\uD83C\uDDFF", // Zimbabwe flag
            phoneCode = "+263",
            phoneNumber = seed.phoneNumber,
            birthday = seed.birthday,
            gender = seed.gender,
            photoResKey = null,
            photoUri = seed.photoUri,
            favorites = emptyList(),
            role = "admin",
        )

        userProfile = adminProfile
        currentUserRole = "Admin"
        isAuthenticated = true
        isGuestMode = false
        currentScreen = MainScreen.Admin
        showAdminSignIn = false

        // Best-effort persistence of an admin user shell; may be rejected by rules if auth
        // is not configured, but this should never break the local session.
        runCatching {
            Firebase.firestore.collection("users").document(adminUid).set(
                mapOf(
                    "uid" to adminUid,
                    "displayName" to adminProfile.fullName,
                    "email" to adminProfile.email,
                    "role" to adminProfile.role,
                    "countryName" to adminProfile.countryName,
                    "countryFlag" to adminProfile.countryFlag,
                    "phoneCode" to adminProfile.phoneCode,
                    "phoneNumber" to adminProfile.phoneNumber,
                    "birthday" to adminProfile.birthday,
                    "gender" to adminProfile.gender,
                    "photoResKey" to adminProfile.photoResKey,
                    "photoUri" to adminProfile.photoUri,
                    "favorites" to adminProfile.favorites
                ),
                merge = true
            )
        }

        // Persist secure admin config under the admin profile document so future secure clicks
        // can auto-fill and sign in.
        runCatching {
            saveAdminSecureConfig(
                AdminSecureConfig(
                    email = seed.email,
                    password = seed.password,
                    rememberMe = rememberMe,
                    adminName = seed.fullName,
                )
            )
        }

        // Admin-specific remember-me is handled via AdminSecureConfig only; we intentionally do
        // not toggle the global SettingsPrefs.rememberMe here so startup behavior for customers
        // remains predictable.
        syncFavoritesFromBackend()
        isSignInWarmupActive = true
        return AuthResult.Success
    }

    suspend fun autoAdminSecureSignIn(): AuthResult {
        val config = fetchAdminSecureConfig().getOrElse {
            // Hide low-level Firestore errors and guide the user back to the manual admin flow.
            return AuthResult.Error(
                "Secure admin sign-in isn't fully configured yet. Use your admin email and passcode on the next screen."
            )
        }

        val seed = adminSeedByCredentials(config.email, config.password)
            ?: return AuthResult.Error(
                "Secure admin sign-in failed. Use your admin email and passcode on the next screen."
            )

        return startAdminSessionFromSeed(seed, config.rememberMe)
    }

    fun recordSearch(query: String) {
        val normalized = query.trim()
        if (normalized.isEmpty()) return
        searchHistory.remove(normalized)
        searchHistory.add(0, normalized)
        if (searchHistory.size > 10) {
            searchHistory.removeAt(searchHistory.size - 1)
        }
    }

    fun openEventById(id: String) {
        val event = sampleEvents.firstOrNull { it.id == id }
        if (event != null && isEventPublic(event.id, adminApplications)) {
            detailEvent = event
        }
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
        val base = if (withSignal.isNotEmpty()) withSignal else prioritized
        return base.filter { isEventPublic(it.eventId, adminApplications) }
    }

    val adminPrimaryAccent = MaterialTheme.colorScheme.primary
    val adminSecondaryAccent = MaterialTheme.colorScheme.secondary
    val adminAlertsAccent = IconCategoryColors[IconCategory.Alerts] ?: adminPrimaryAccent
    val adminWarningAccent = Color(0xFFFFC94A)

    val adminKpis = remember(adminAlertsAccent, adminPrimaryAccent) {
        listOf(
            AdminKpi("Pending apps", "18", "+4 vs. avg", adminAlertsAccent),
            AdminKpi("Approvals today", "12", "+8%", adminPrimaryAccent),
            AdminKpi("SLA risk", "3", "under 6h", Color(0xFFFFC94A)),
            AdminKpi("Flagged orgs", "2", "review now", Color(0xFFFF6B6B)),
        )
    }
    val adminAttention = remember {
        listOf(
            AdminAttention("Bulawayo Street Fest", "Missing permit • 14h old", "High"),
            AdminAttention("Savanna Sky Sessions", "Pricing mismatch vs. last run", "Medium"),
            AdminAttention("New Organizer: VibeCo", "KYC pending • needs ID upload", "Medium"),
        )
    }
    val adminActivity = remember(adminPrimaryAccent, adminSecondaryAccent) {
        mutableStateListOf(
            AdminActivity("Tari approved “Midnight Neon Fest”", "2m ago", adminPrimaryAccent),
            AdminActivity("Rudo requested info on “Family Lights Parade”", "18m ago", Color(0xFFFFC94A)),
            AdminActivity("Kuda verified organizer “Courtside Group”", "45m ago", adminSecondaryAccent),
        )
    }
    fun addAdminActivity(text: String, accent: Color) {
        adminActivity.add(0, AdminActivity(text, "Just now", accent))
        if (adminActivity.size > 30) adminActivity.removeLast()
    }
    var adminSurface by remember { mutableStateOf(AdminSurface.Dashboard) }
    val adminReports = remember {
        mutableStateListOf(
            AdminReport("rep-1", "Courtside Classics", "Image inappropriate", "High", 3, "Open"),
            AdminReport("rep-2", "Laugh Lab Live", "Misleading price", "Medium", 9, "Open"),
            AdminReport("rep-3", "Family Lights Parade", "Copy plagiarism", "Low", 22, "Open"),
        )
    }
    val adminFlags = remember {
        mutableStateListOf(
            AdminFeatureFlag("ff-alerts-beta", "Alerts beta", true, "organizer+admin"),
            AdminFeatureFlag("ff-social-share", "Social share", true, "all"),
            AdminFeatureFlag("ff-map-live", "Live map", false, "all"),
            AdminFeatureFlag("ff-seat-stub", "Seat map stub", true, "all"),
        )
    }
    val adminReviewers = remember { listOf("Tari", "Rudo", "Kuda", "Amina") }
    val reviewerByApp = remember { mutableStateMapOf<String, String>() }
    val commentsByApp = remember { mutableStateMapOf<String, SnapshotStateList<String>>() }
    val adminOrganizers = remember {
        mutableStateListOf(
            AdminOrganizer("org-1", "Neon Live", "Verified", 86, 0, false, "Strong past performance; timely payouts."),
            AdminOrganizer("org-2", "City Beats", "Pending", 62, 1, false, "Missing permit for Bulawayo Street Fest."),
            AdminOrganizer("org-3", "VibeCo", "Pending", 55, 2, false, "Needs KYC refresh; pricing mismatch flagged."),
            AdminOrganizer("org-4", "Sunrise Events", "Verified", 78, 0, false, "Family-friendly focus; clean history."),
        )
    }
    val adminRoles = remember {
        mutableStateListOf(
            AdminRoleEntry("user-1", "Tari D.", "Admin", "tari@goticky.com"),
            AdminRoleEntry("user-2", "Rudo M.", "Admin", "rudo@goticky.com"),
            AdminRoleEntry("user-3", "Kuda P.", "Organizer", "kuda@goticky.com"),
            AdminRoleEntry("user-4", "Amina S.", "Support", "amina@goticky.com"),
        )
    }
    val featuredSlots = remember { mutableStateListOf<String>().apply { addAll(defaultHeroSlides.take(2).map { it.id }) } }
    LaunchedEffect(Unit) {
        loadingHeroBanners = true
        val remote = fetchHeroBannersFromFirestore().filter { it.active }
        if (remote.isNotEmpty()) {
            heroSlides.clear()
            heroSlides.addAll(remote.sortedBy { it.order })
            featuredSlots.clear()
            featuredSlots.addAll(remote.sortedBy { it.order }.take(3).map { it.id })
        }
        loadingHeroBanners = false
    }
    fun resetBannerDraft(seedOrder: Int = heroSlides.size) {
        bannerDraft = BannerDraft(
            id = "banner-${currentTimestampIsoString()}",
            title = "",
            subtitle = "",
            cta = "Explore lineup",
            tag = "Spotlight",
            location = "Zimbabwe",
            imageHint = "Describe the visual vibe (e.g., neon food market)",
            accent = Color(0xFF9C7BFF),
            accentHex = "#9C7BFF",
            order = seedOrder,
            active = true,
            imageUrl = null,
            localImageUri = null,
        )
    }
    fun saveBannerFromDraft(draft: BannerDraft) {
        scope.launch {
            if (draft.title.isBlank()) {
                snackbarHostState.showSnackbar("Add a banner title before saving.")
                return@launch
            }
            bannerSaving = true
            bannerSavingProgress = 0.15f
            val id = draft.id.ifBlank { "banner-${currentTimestampIsoString()}" }
            val accentHex = draft.accentHex.ifBlank { colorToHex(draft.accent) }
            var imageUrl = draft.imageUrl
            if (!draft.localImageUri.isNullOrBlank()) {
                bannerSavingProgress = 0.35f
                val uploaded = bannerImageStorage.uploadBannerImage(id, draft.localImageUri) { p ->
                    bannerSavingProgress = 0.3f + 0.6f * p
                }
                if (uploaded == null) {
                    bannerSaving = false
                    bannerSavingProgress = 0f
                    snackbarHostState.showSnackbar("Banner upload failed. Please retry.")
                    return@launch
                } else {
                    imageUrl = uploaded
                }
            }
            bannerSavingProgress = 0.85f
            val slide = HeroSlide(
                id = id,
                title = draft.title,
                subtitle = draft.subtitle,
                cta = draft.cta.ifBlank { "Explore" },
                tag = draft.tag.ifBlank { "Spotlight" },
                accent = draft.accent,
                imageHint = draft.imageHint,
                location = draft.location,
                heroImageKey = null,
                imageUrl = imageUrl,
                accentHex = accentHex,
                order = draft.order,
                active = draft.active,
            )
            saveHeroBannerToFirestore(slide)
            val existingIdx = heroSlides.indexOfFirst { it.id == id }
            if (existingIdx >= 0) {
                heroSlides[existingIdx] = slide
            } else {
                heroSlides.add(slide)
            }
            heroSlides.sortBy { it.order }
            if (slide.active && !featuredSlots.contains(id)) {
                featuredSlots.add(id)
            }
            addAdminActivity("Banner saved: ${slide.title}", adminPrimaryAccent)
            bannerSavingProgress = 1f
            bannerSaving = false
            resetBannerDraft(seedOrder = heroSlides.size)
        }
    }
    fun deleteBanner(id: String) {
        scope.launch {
            bannerSaving = true
            bannerSavingProgress = 0.15f
            deleteHeroBannerFromFirestore(id)
            heroSlides.removeAll { it.id == id }
            featuredSlots.remove(id)
            bannerSaving = false
            bannerSavingProgress = 0f
            addAdminActivity("Banner removed: $id", adminWarningAccent)
        }
    }
    fun updateApplicationStatus(id: String, status: String, log: (String, Color) -> Unit) {
        val idx = adminApplications.indexOfFirst { it.id == id }
        if (idx != -1) {
            val now = currentInstant()
            val app = adminApplications[idx]
            val newApprovedAt = if (status == "Approved" && app.approvedAt == null) now else app.approvedAt
            adminApplications[idx] = app.copy(status = status, approvedAt = newApprovedAt)
            val accent = when (status) {
                "Approved" -> adminPrimaryAccent
                "Rejected" -> Color(0xFFFF6B6B)
                "Needs Info" -> Color(0xFFFFC94A)
                else -> adminSecondaryAccent
            }
            val suffix = if (status == "Approved") " + Early Bird opens for 72h" else ""
            log("Set $status for ${adminApplications[idx].title}$suffix", accent)
        }
    }
    fun updateEarlyBirdConfig(
        id: String,
        enabled: Boolean,
        discountPercent: Int,
        durationHours: Int,
        startNow: Boolean,
        paused: Boolean,
        log: (String, Color) -> Unit,
    ) {
        val idx = adminApplications.indexOfFirst { it.id == id }
        if (idx != -1) {
            val now = currentInstant()
            val app = adminApplications[idx]
            val clampedDiscount = discountPercent.coerceIn(5, 80)
            val clampedDuration = durationHours.coerceIn(1, 24 * 14)
            val newManualStart = if (startNow) now else app.earlyBirdManualStartAt
            val updated = app.copy(
                earlyBirdEnabled = enabled,
                earlyBirdDiscountPercent = clampedDiscount,
                earlyBirdDurationHours = clampedDuration,
                earlyBirdManualStartAt = newManualStart,
                earlyBirdPaused = paused
            )
            adminApplications[idx] = updated
            val statusLabel = when {
                !updated.earlyBirdEnabled -> "disabled"
                updated.earlyBirdPaused -> "paused"
                else -> "live"
            }
            val accent = when {
                !updated.earlyBirdEnabled -> Color(0xFFFFC94A)
                updated.earlyBirdPaused -> Color(0xFFFFC94A)
                else -> adminPrimaryAccent
            }
            log(
                "Early Bird $statusLabel for ${updated.title} — ${updated.earlyBirdDiscountPercent}% for ${updated.earlyBirdDurationHours}h",
                accent
            )
        }
    }
    fun resolveReport(id: String, log: (String, Color) -> Unit) {
        val idx = adminReports.indexOfFirst { it.id == id }
        if (idx != -1) {
            adminReports[idx] = adminReports[idx].copy(state = "Resolved")
            log("Resolved report on ${adminReports[idx].target}", adminPrimaryAccent)
        }
    }
    fun warnReport(id: String, template: String, note: String, log: (String, Color) -> Unit) {
        val idx = adminReports.indexOfFirst { it.id == id }
        if (idx != -1) {
            adminReports[idx] = adminReports[idx].copy(state = "Warned")
            val suffix = if (note.isBlank()) "" else " — ${note.trim()}"
            log("Warned organizer for ${adminReports[idx].target}: $template$suffix", adminWarningAccent)
        }
    }
    fun toggleFlag(key: String, enabled: Boolean, log: (String, Color) -> Unit) {
        val idx = adminFlags.indexOfFirst { it.key == key }
        if (idx != -1) {
            adminFlags[idx] = adminFlags[idx].copy(enabled = enabled)
            log("Flag ${adminFlags[idx].label}: ${if (enabled) "enabled" else "disabled"}", adminSecondaryAccent)
        }
    }
    fun verifyOrganizer(id: String, verified: Boolean, log: (String, Color) -> Unit) {
        val idx = adminOrganizers.indexOfFirst { it.id == id }
        if (idx != -1) {
            adminOrganizers[idx] = adminOrganizers[idx].copy(kycStatus = if (verified) "Verified" else "Pending")
            log("${adminOrganizers[idx].name}: ${if (verified) "Verified" else "Set to pending"}", adminPrimaryAccent)
        }
    }
    fun freezeOrganizer(id: String, frozen: Boolean, log: (String, Color) -> Unit) {
        val idx = adminOrganizers.indexOfFirst { it.id == id }
        if (idx != -1) {
            adminOrganizers[idx] = adminOrganizers[idx].copy(frozen = frozen)
            log("${adminOrganizers[idx].name}: ${if (frozen) "Frozen publishing" else "Unfrozen"}", adminWarningAccent)
        }
    }
    fun requestReKyc(id: String, log: (String, Color) -> Unit) {
        val idx = adminOrganizers.indexOfFirst { it.id == id }
        if (idx != -1) {
            log("Requested re-KYC from ${adminOrganizers[idx].name}", adminSecondaryAccent)
        }
    }
    fun changeRole(id: String, role: String, log: (String, Color) -> Unit) {
        val idx = adminRoles.indexOfFirst { it.id == id }
        if (idx != -1) {
            adminRoles[idx] = adminRoles[idx].copy(role = role)
            log("Role updated: ${adminRoles[idx].name} → $role", adminSecondaryAccent)
        }
    }
    fun toggleFeaturedSlot(id: String, enabled: Boolean, log: (String, Color) -> Unit) {
        val exists = featuredSlots.contains(id)
        if (enabled && !exists) {
            featuredSlots.add(id)
            log("Featured slot added: ${heroSlides.firstOrNull { it.id == id }?.title ?: id}", adminPrimaryAccent)
        } else if (!enabled && exists) {
            featuredSlots.remove(id)
            log("Featured slot removed: ${heroSlides.firstOrNull { it.id == id }?.title ?: id}", adminWarningAccent)
        }
    }
    fun moveFeaturedSlot(id: String, delta: Int, log: (String, Color) -> Unit) {
        val idx = featuredSlots.indexOf(id)
        if (idx != -1) {
            val newIdx = (idx + delta).coerceIn(0, featuredSlots.lastIndex)
            if (newIdx != idx) {
                featuredSlots.removeAt(idx)
                featuredSlots.add(newIdx, id)
                log("Featured order updated for ${heroSlides.firstOrNull { it.id == id }?.title ?: id}", adminSecondaryAccent)
            }
        }
    }

    val navItems = remember(hasAdminAccess) {
        buildList {
            add(NavItem(MainScreen.Home, "Home", { Icon(Icons.Outlined.Home, null) }, IconCategory.Discover))
            // Removed "Browse" from bottom navigation – no functionality wired yet.
            add(NavItem(MainScreen.Tickets, "My Tickets", { Icon(Icons.Outlined.ReceiptLong, null) }, IconCategory.Ticket))
            if (hasAdminAccess) {
                add(NavItem(MainScreen.Admin, "Admin", { Icon(Icons.Outlined.Shield, null) }, IconCategory.Admin))
            }
            add(NavItem(MainScreen.Alerts, "Alerts", { Icon(Icons.Outlined.Notifications, null) }, IconCategory.Alerts))
            add(NavItem(MainScreen.Profile, "Profile", { Icon(Icons.Outlined.AccountCircle, null) }, IconCategory.Profile))
        }
    }

    val showChromeOnScreen = currentScreen in setOf(
        MainScreen.Home,
        MainScreen.Tickets,
        MainScreen.Alerts,
        MainScreen.Admin,
        MainScreen.Profile,
    )
    val showRootChrome = isAuthenticated &&
        showChromeOnScreen &&
        !showCheckout &&
        !checkoutSuccess &&
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

    LaunchedEffect(postSignUpMessage, isAuthenticated) {
        if (!isAuthenticated && postSignUpMessage != null) {
            snackbarHostState.showSnackbar(postSignUpMessage!!)
            postSignUpMessage = null
        }
    }

    // Ensure logout confirmation UI never leaks across auth state changes.
    // Whenever we transition between authenticated/unauthenticated, reset the dialog flags.
    LaunchedEffect(isAuthenticated) {
        showLogoutConfirm = false
        logoutInProgress = false
        showGuestGateDialog = false
        if (!isAuthenticated) {
            isGuestIntroActive = false
            guestIntroProgress = 0f
            isSignInWarmupActive = false
            signInWarmupProgress = 0f
            isGuestMode = false
        }
    }

    LaunchedEffect(isGuestIntroActive) {
        if (isGuestIntroActive) {
            guestIntroProgress = 0f
            animate(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = tween(durationMillis = 6_000, easing = LinearEasing)
            ) { value, _ ->
                guestIntroProgress = value
            }
            guestIntroProgress = 1f
            isGuestIntroActive = false
        } else {
            guestIntroProgress = 0f
        }
    }

    LaunchedEffect(isSignInWarmupActive, showIntro) {
        if (isSignInWarmupActive && !showIntro) {
            signInWarmupProgress = 0f
            animate(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = tween(durationMillis = 6_000, easing = LinearEasing)
            ) { value, _ ->
                signInWarmupProgress = value
            }
            signInWarmupProgress = 1f
            isSignInWarmupActive = false
        } else if (!isSignInWarmupActive) {
            signInWarmupProgress = 0f
        }
    }

    AnimatedContent(
        targetState = showIntro,
        transitionSpec = {
            fadeIn(animationSpec = tween(durationMillis = 900)) togetherWith
                fadeOut(animationSpec = tween(durationMillis = 900))
        },
        label = "introCrossfade",
    ) { introShowing ->
        if (introShowing) {
            IntroScreen(onContinue = { showIntro = false })
        } else {
            if (!isAuthenticated) {
                AnimatedContent(
                    targetState = showAdminSignIn,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(GoTickyMotion.Standard)) togetherWith
                                fadeOut(animationSpec = tween(GoTickyMotion.Standard))
                    },
                    label = "auth-admin-toggle"
                ) { adminMode ->
                    if (adminMode) {
                        AdminSignInScreen(
                            flagEnabled = adminFeatureFlagEnabled,
                            onBack = { showAdminSignIn = false },
                            prefillEmail = adminSecurePrefill?.email.orEmpty(),
                            prefillPasscode = adminSecurePrefill?.password.orEmpty(),
                            prefillRememberMe = adminSecurePrefill?.rememberMe ?: false,
                            onSubmit = { email, passcode, rememberMe ->
                                val seed = adminSeedByCredentials(email, passcode)
                                if (seed == null) {
                                    // Avoid leaking which field is wrong.
                                    AuthResult.Error("Incorrect admin email or passcode.")
                                } else {
                                    startAdminSessionFromSeed(seed, rememberMe)
                                }
                            }
                        )
                    } else {
                        AuthScreen(
                            onSignIn = { email, password, rememberMe ->
                                val result = authRepo.signIn(email, password)
                                if (result is AuthResult.Success) {
                                    val authUser = authRepo.currentUser()
                                    if (authUser != null) {
                                        val remote = loadUserSettingsFromFirestore(authUser.uid)
                                        val merged = (remote ?: SettingsPrefs()).copy(rememberMe = rememberMe)
                                        settingsPrefs = merged
                                        saveUserSettingsToFirestore(authUser.uid, merged)
                                    } else {
                                        settingsPrefs = settingsPrefs.copy(rememberMe = rememberMe)
                                    }
                                    val fetched = authRepo.fetchProfile()
                                    if (fetched != null) {
                                        userProfile = fetched
                                        currentUserRole = fetched.role.ifBlank { "customer" }
                                        syncFavoritesFromBackend(fetched)
                                    } else {
                                        syncFavoritesFromBackend()
                                    }
                                    isAuthenticated = true
                                    isGuestMode = false
                                    currentScreen = MainScreen.Home
                                    isSignInWarmupActive = true
                                }
                                result
                            },
                            onSkip = {
                                isGuestMode = true
                                isAuthenticated = true
                                userProfile = defaultUserProfile()
                                currentUserRole = "customer"
                                favoriteEvents.clear()
                                selectedTicket = null
                                detailEvent = null
                                showCheckout = false
                                checkoutSuccess = false
                                currentScreen = MainScreen.Home
                                isGuestIntroActive = true
                            },
                            onBiometricSignIn = {
                                when (val bio = biometricLauncher.authenticate(
                                    BiometricPromptConfig(
                                        title = "Unlock GoTicky",
                                        subtitle = "Use your fingerprint to continue",
                                        description = "Securely resume your saved session without typing your password."
                                    )
                                )) {
                                    BiometricResult.Success -> {
                                        val authUser = authRepo.currentUser()
                                        if (authUser != null) {
                                            val remote = loadUserSettingsFromFirestore(authUser.uid)
                                            val merged = (remote ?: SettingsPrefs()).copy(rememberMe = true)
                                            settingsPrefs = merged
                                            saveUserSettingsToFirestore(authUser.uid, merged)
                                            val fetched = authRepo.fetchProfile()
                                            if (fetched != null) {
                                                userProfile = fetched
                                                currentUserRole = fetched.role.ifBlank { "customer" }
                                                syncFavoritesFromBackend(fetched)
                                            } else {
                                                syncFavoritesFromBackend()
                                            }
                                            isAuthenticated = true
                                            isGuestMode = false
                                            currentScreen = MainScreen.Home
                                            isSignInWarmupActive = true
                                            AuthResult.Success
                                        } else {
                                            AuthResult.Error("No saved session found. Please sign in once to enable biometric unlock.")
                                        }
                                    }
                                    BiometricResult.NotAvailable -> AuthResult.Error("Biometric unlock is not available on this device.")
                                    is BiometricResult.Error -> AuthResult.Error(bio.message)
                                }
                            },
                            onSignUp = { profile, password ->
                                // Create the account first, then upload the photo (now authenticated) and persist the download URL.
                                postSignUpUploadError = null
                                postSignUpUploadMessage = null
                                postSignUpUploadProgress = 0f
                                pendingUploadProfile = null
                                autoRetryAttempted = false

                                suspend fun uploadOnce(target: UserProfile): Boolean {
                                    postSignUpUploadInProgress = true
                                    postSignUpUploadError = null
                                    postSignUpUploadMessage = "Uploading profile photo…"
                                    postSignUpUploadProgress = 0f
                                    val downloadUrl = profileImageStorage.uploadProfileImage(target.photoUri!!) { p ->
                                        postSignUpUploadProgress = p
                                    }
                                    val success = if (downloadUrl != null) {
                                        val updateResult = authRepo.updateProfile(target.copy(photoUri = downloadUrl, photoResKey = null))
                                        if (updateResult is AuthResult.Error) {
                                            postSignUpUploadError = updateResult.message
                                            postSignUpUploadMessage = "Photo saved locally, but profile update failed."
                                            false
                                        } else {
                                            postSignUpUploadProgress = 1f
                                            postSignUpUploadMessage = "Profile photo saved."
                                            true
                                        }
                                    } else {
                                        postSignUpUploadError = "Upload failed. Please retry after signing in."
                                        postSignUpUploadMessage = "Upload failed."
                                        postSignUpUploadProgress = 0f
                                        false
                                    }
                                    postSignUpUploadInProgress = false
                                    return success
                                }
                                val baseResult = authRepo.signUp(profile.copy(photoUri = null), password)
                                if (baseResult is AuthResult.Success && profile.photoUri != null) {
                                    pendingUploadProfile = profile
                                    var success = uploadOnce(profile)
                                    if (!success && !autoRetryAttempted) {
                                        autoRetryAttempted = true
                                        postSignUpUploadMessage = "Retrying upload…"
                                        delay(900)
                                        success = uploadOnce(profile)
                                    }
                                }
                                if (baseResult is AuthResult.Success) {
                                    // Keep user on auth screen to sign in, but finish optional photo upload first.
                                    // Explicitly sign out to avoid auto-login as an anonymous/guest session.
                                    authRepo.signOut()
                                    settingsPrefs = settingsPrefs.copy(rememberMe = false)
                                    userProfile = defaultUserProfile()
                                    currentUserRole = "customer"
                                    favoriteEvents.clear()
                                    selectedTicket = null
                                    detailEvent = null
                                    showCheckout = false
                                    checkoutSuccess = false
                                    isAuthenticated = false
                                    isGuestMode = false
                                    currentScreen = MainScreen.Home
                                    postSignUpMessage = "Account created. Please sign in."
                                }
                                baseResult
                            },
                            onAdminSignIn = {
                                if (secureSignInInProgress) return@AuthScreen
                                secureSignInInProgress = true
                                scope.launch {
                                    try {
                                        adminSecurePrefill = loadAdminSecureConfigForPrefill()
                                        showAdminSignIn = true
                                    } finally {
                                        secureSignInInProgress = false
                                    }
                                }
                            },
                            findProfileByEmail = { email -> authRepo.findProfileByEmail(email) },
                            externalUploadInProgress = postSignUpUploadInProgress,
                            externalUploadProgress = postSignUpUploadProgress,
                            externalUploadMessage = postSignUpUploadMessage ?: postSignUpUploadError
                        )
                    }
                }
            } else {
                val introActive = (isGuestMode && isGuestIntroActive) || isSignInWarmupActive

                Box(modifier = Modifier.fillMaxSize()) {
                    Scaffold(
                        containerColor = Color.Transparent,
                        contentWindowInsets = WindowInsets(0, 0, 0, 0),
                        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
                                        .blur(if (introActive) 22.dp else 0.dp)
                                ) {
                                    BottomBar(
                                        navItems = navItems,
                                        current = currentScreen,
                                        chromeAlpha = fabAlpha,
                                    ) { tapped ->
                                        when (tapped) {
                                            MainScreen.Admin -> {
                                                if (hasAdminAccess) {
                                                    Analytics.log(
                                                        AnalyticsEvent(
                                                            name = "admin_gate_allowed",
                                                            params = mapOf(
                                                                "role" to currentUserRole,
                                                                "source" to "bottom_nav",
                                                                "admin_flag" to adminFeatureFlagEnabled.toString()
                                                            )
                                                        )
                                                    )
                                                    currentScreen = tapped
                                                } else {
                                                    adminGateMessage = if (!adminFeatureFlagEnabled) {
                                                        "Admin feature is currently disabled by feature flag."
                                                    } else {
                                                        "Your role ($currentUserRole) does not have access. Switch to an Admin account."
                                                    }
                                                    showAdminGateDialog = true
                                                    Analytics.log(
                                                        AnalyticsEvent(
                                                            name = "admin_gate_denied",
                                                            params = mapOf(
                                                                "role" to currentUserRole,
                                                                "source" to "bottom_nav",
                                                                "admin_flag" to adminFeatureFlagEnabled.toString()
                                                            )
                                                        )
                                                    )
                                                }
                                            }
                                            MainScreen.Organizer -> {
                                                requireAuth(
                                                    target = GuestGateTarget.Organizer,
                                                    message = "Sign up to create and manage events as an organizer.",
                                                ) {
                                                    currentScreen = tapped
                                                }
                                            }
                                            MainScreen.Tickets -> {
                                                requireAuth(
                                                    target = GuestGateTarget.Checkout,
                                                    message = "This screen is only accessible to registered customers. Sign up to view and manage your tickets.",
                                                ) {
                                                    currentScreen = tapped
                                                }
                                            }
                                            else -> currentScreen = tapped
                                        }
                                    }
                                }
                            }
                        },
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
                            when {
                                showLogoutConfirm -> {
                                    AlertDialog(
                                        onDismissRequest = { if (!logoutInProgress) showLogoutConfirm = false },
                                        icon = { Icon(Icons.Outlined.Logout, contentDescription = null) },
                                        title = { Text("Sign out of GoTicky?") },
                                        text = {
                                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                                Text(
                                                    "You’ll return to the home feed and can sign back in anytime.",
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                                if (logoutInProgress) {
                                                    Text(
                                                        "Signing out…",
                                                        style = MaterialTheme.typography.labelMedium,
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                }
                                            }
                                        },
                                        confirmButton = {
                                            PrimaryButton(
                                                text = if (logoutInProgress) "Signing out…" else "Yes, logout",
                                                modifier = Modifier.pressAnimated(),
                                                icon = {
                                                    Icon(
                                                        imageVector = Icons.Outlined.ThumbUp,
                                                        contentDescription = null,
                                                        tint = Color(0xFF7CFF7A)
                                                    )
                                                }
                                            ) {
                                                if (logoutInProgress) return@PrimaryButton
                                                logoutInProgress = true
                                                scope.launch {
                                                    runCatching { authRepo.signOut() }
                                                    isAuthenticated = false
                                                    userProfile = defaultUserProfile()
                                                    currentUserRole = "customer"
                                                    favoriteEvents.clear()
                                                    selectedTicket = null
                                                    detailEvent = null
                                                    showCheckout = false
                                                    checkoutSuccess = false
                                                    currentScreen = MainScreen.Home
                                                    showAdminSignIn = false
                                                    showIntro = false
                                                    isGuestMode = false
                                                    logoutInProgress = false
                                                    showLogoutConfirm = false
                                                }
                                            }
                                        },
                                        dismissButton = {
                                            NeonTextButton(
                                                text = "Cancel",
                                                onClick = { if (!logoutInProgress) showLogoutConfirm = false },
                                                modifier = Modifier.pressAnimated(),
                                                icon = {
                                                    Icon(
                                                        imageVector = Icons.Outlined.ThumbDown,
                                                        contentDescription = null,
                                                        tint = Color(0xFFFF5C5C)
                                                    )
                                                }
                                            )
                                        }
                                    )
                                }
                                showGuestGateDialog -> {
                                    AlertDialog(
                                        onDismissRequest = { showGuestGateDialog = false },
                                        icon = { Icon(Icons.Outlined.Fingerprint, contentDescription = null, tint = IconCategoryColors[IconCategory.Profile] ?: MaterialTheme.colorScheme.primary) },
                                        title = { Text("Sign up to continue") },
                                        text = { Text(guestGateMessage) },
                                        confirmButton = {
                                            PrimaryButton(
                                                text = "Sign up free",
                                                modifier = Modifier.pressAnimated()
                                            ) {
                                                showGuestGateDialog = false
                                                // Move to auth flow; keep state clean for a fresh sign-up/sign-in.
                                                isAuthenticated = false
                                                isGuestMode = false
                                                detailEvent = null
                                                showCheckout = false
                                                checkoutReturnEvent = null
                                                currentScreen = MainScreen.Home
                                                scope.launch {
                                                    snackbarHostState.showSnackbar("Create your account to unlock tickets and organizer tools.")
                                                }
                                            }
                                        },
                                        dismissButton = {
                                            NeonTextButton(
                                                text = "Keep browsing",
                                                onClick = {
                                                    showGuestGateDialog = false
                                                    if (guestGateTarget == GuestGateTarget.Organizer) {
                                                        currentScreen = MainScreen.Home
                                                    }
                                                }
                                            )
                                        }
                                    )
                                }
                                showAdminGateDialog -> {
                                    AlertDialog(
                                        onDismissRequest = { showAdminGateDialog = false },
                                        icon = { Icon(Icons.Outlined.Shield, contentDescription = null) },
                                        title = { Text("Admin access required") },
                                        text = { Text(adminGateMessage) },
                                        confirmButton = {
                                            PrimaryButton(text = "Request access", modifier = Modifier.pressAnimated()) {
                                                showAdminGateDialog = false
                                                currentScreen = MainScreen.Profile
                                            }
                                        },
                                        dismissButton = {
                                            NeonTextButton(
                                                text = "Back to Home",
                                                onClick = {
                                                    showAdminGateDialog = false
                                                    currentScreen = MainScreen.Home
                                                }
                                            )
                                        }
                                    )
                                }
                                else -> {
                                    val blurredContentModifier = if (introActive) Modifier.blur(22.dp) else Modifier

                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .then(blurredContentModifier)
                                    ) {
                                        when {
                                            detailEvent != null -> {
                                                val event = detailEvent!!
                                                EventDetailScreen(
                                                    event = event,
                                                    isFavorite = favoriteEvents.contains(event.id),
                                                    onToggleFavorite = { toggleFavorite(event.id) },
                                                    onBack = { detailEvent = null },
                                                    onProceedToCheckout = { ticketType ->
                                                        requireAuth(
                                                            target = GuestGateTarget.Checkout,
                                                            message = "Create a free account to unlock checkout and manage your tickets.",
                                                        ) {
                                                            selectedTicketType = ticketType
                                                            checkoutReturnEvent = event
                                                            detailEvent = null
                                                            showCheckout = true
                                                        }
                                                    },
                                                    onAlert = { currentScreen = MainScreen.Alerts },
                                                )
                                            }
                                            showCheckout -> {
                                                CheckoutScreen(
                                                    order = sampleOrder,
                                                    selectedTicketType = selectedTicketType,
                                                    onBack = {
                                                        showCheckout = false
                                                        if (checkoutReturnEvent != null) {
                                                            detailEvent = checkoutReturnEvent
                                                        }
                                                        checkoutReturnEvent = null
                                                    },
                                                    onPlaceOrder = { paymentMethod, totalAmount ->
                                                        Analytics.log(
                                                            AnalyticsEvent(
                                                                name = "checkout_place_order",
                                                                params = mapOf(
                                                                    "method" to paymentMethod,
                                                                    "amount" to totalAmount.toString(),
                                                                    "ticket_type" to selectedTicketType
                                                                )
                                                            )
                                                        )
                                                        showCheckout = false
                                                        checkoutReturnEvent = null
                                                        lastCheckoutMethod = paymentMethod
                                                        lastCheckoutAmount = totalAmount
                                                        checkoutSuccess = true
                                                    }
                                                )
                                            }
                                            checkoutSuccess -> {
                                                CheckoutSuccessScreen(
                                                    amount = lastCheckoutAmount,
                                                    method = lastCheckoutMethod,
                                                    ticketType = selectedTicketType,
                                                    onViewTickets = {
                                                        checkoutSuccess = false
                                                        requireAuth(
                                                            target = GuestGateTarget.Checkout,
                                                            message = "This screen is only accessible to registered customers. Sign up to view and manage your tickets.",
                                                        ) {
                                                            currentScreen = MainScreen.Tickets
                                                        }
                                                    },
                                                    onBackHome = {
                                                        checkoutSuccess = false
                                                        currentScreen = MainScreen.Home
                                                    },
                                                )
                                            }
                                            else -> {
                                                when (currentScreen) {
                                                    MainScreen.Home -> {
                                                        HomeScreen(
                                                            userProfile = userProfile,
                                                            isGuest = isGuestMode,
                                                            onOpenAlerts = { currentScreen = MainScreen.Alerts },
                                                            onEventSelected = { event ->
                                                                detailEvent = event
                                                            },
                                                            heroSlides = heroSlides,
                                                            simulatedHeroBannerId = simulatedHeroBannerId,
                                                            recommendations = personalize(recommendations),
                                                            adminApplications = adminApplications,
                                                            onOpenMap = { currentScreen = MainScreen.Map },
                                                            searchQuery = searchQuery,
                                                            onSearchQueryChange = { searchQuery = it },
                                                            forceOpenSearchDialog = forceOpenSearchDialog,
                                                            onConsumeForceOpenSearchDialog = { forceOpenSearchDialog = false },
                                                            onSearchExecuted = { query -> recordSearch(query) },
                                                            favoriteEvents = favoriteEvents,
                                                            onToggleFavorite = { id -> toggleFavorite(id) },
                                                        )
                                                    }
                                                    MainScreen.Tickets -> {
                                                        TicketsScreen(
                                                            tickets = sampleTickets,
                                                            onTicketSelected = { ticket ->
                                                                selectedTicket = ticket
                                                            },
                                                            onCheckout = {
                                                                requireAuth(
                                                                    target = GuestGateTarget.Checkout,
                                                                    message = "Sign up to buy tickets and keep them in your wallet.",
                                                                ) {
                                                                    checkoutReturnEvent = null
                                                                    showCheckout = true
                                                                }
                                                            }
                                                        )
                                                    }
                                                    MainScreen.Alerts -> {
                                                        AlertsScreen(
                                                            alerts = alerts,
                                                            recommendations = personalize(recommendations),
                                                            personalizationPrefs = personalizationPrefs,
                                                            onBack = { currentScreen = MainScreen.Home },
                                                            onOpenEvent = { eventId -> openEventById(eventId) },
                                                            onCreateAlert = { },
                                                            onAdjustAlert = { },
                                                            onUpdatePersonalization = { newPrefs ->
                                                                personalizationPrefs = newPrefs
                                                            },
                                                        )
                                                    }
                                                    MainScreen.Profile -> {
                                                        ProfileScreen(
                                                            userProfile = userProfile,
                                                            onUpdateProfile = { updated -> userProfile = updated },
                                                            checklistState = checklistState,
                                                            onToggleCheck = { id ->
                                                                val current = checklistState[id] ?: false
                                                                checklistState[id] = !current
                                                            },
                                                            favorites = favoriteEvents,
                                                            onToggleFavorite = { id -> toggleFavorite(id) },
                                                            adminApplications = adminApplications,
                                                            onOpenEvent = { event ->
                                                                detailEvent = event
                                                            },
                                                            onOpenOrganizer = {
                                                                requireAuth(
                                                                    target = GuestGateTarget.Organizer,
                                                                    message = "Sign up to create and manage events as an organizer.",
                                                                ) {
                                                                    currentScreen = MainScreen.Organizer
                                                                }
                                                            },
                                                            onGoHome = { currentScreen = MainScreen.Home },
                                                            onOpenPrivacyTerms = { currentScreen = MainScreen.PrivacyTerms },
                                                            onOpenFaq = { currentScreen = MainScreen.FAQ },
                                                            searchHistory = searchHistory,
                                                            onClearSearchHistory = { searchHistory.clear() },
                                                            onSelectHistoryQuery = { query ->
                                                                searchQuery = query
                                                                recordSearch(query)
                                                            },
                                                            onOpenSettings = { currentScreen = MainScreen.Settings },
                                                            onLogout = { showLogoutConfirm = true },
                                                            isGuest = isGuestMode,
                                                        )
                                                    }
                                                    MainScreen.Organizer -> {
                                                        when {
                                                            selectedOrganizerEvent != null -> {
                                                                OrganizerEventDetailScreen(
                                                                    event = selectedOrganizerEvent!!,
                                                                    onBack = { selectedOrganizerEvent = null }
                                                                )
                                                            }
                                                            showCreateEvent -> {
                                                                CreateEventScreen(
                                                                    userProfile = userProfile,
                                                                    onBack = { showCreateEvent = false },
                                                                    onSaveDraft = { title, city, venue, dateLabel, priceFrom, status, ticketCountStr ->
                                                                        val index = organizerEvents.size + 1
                                                                        val newEvent = OrganizerEvent(
                                                                            id = "org-$index",
                                                                            eventId = "new-$index",
                                                                            title = if (title.isNotBlank()) title else "Untitled event $index",
                                                                            city = city.ifBlank { "City TBD" },
                                                                            venue = venue.ifBlank { "Venue TBD" },
                                                                            dateLabel = dateLabel,
                                                                            priceFrom = priceFrom.ifBlank { "From $0" },
                                                                            status = status,
                                                                            views = 0,
                                                                            saves = 0,
                                                                            sales = 0,
                                                                            ticketCount = ticketCountStr.toIntOrNull() ?: 0,
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
                                                            }
                                                            else -> {
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
                                                                    },
                                                                    onOpenEvent = { event ->
                                                                        selectedOrganizerEvent = event
                                                                    },
                                                                    chromeAlpha = fabAlpha
                                                                )
                                                            }
                                                        }
                                                    }
                                                    MainScreen.Admin -> {
                                                        if (hasAdminAccess) {
                                                            AdminDashboardScreen(
                                                                kpis = adminKpis,
                                                                attention = adminAttention,
                                                                activity = adminActivity,
                                                                applications = adminApplications,
                                                                reports = adminReports,
                                                                flags = adminFlags,
                                                                organizers = adminOrganizers,
                                                                roles = adminRoles,
                                                                heroSlides = heroSlides,
                                                                bannerDraft = bannerDraft,
                                                                onBannerDraftChange = { bannerDraft = it },
                                                                onPickBannerImage = { bannerPicker.pickFromGallery() },
                                                                onSaveBanner = { saveBannerFromDraft(bannerDraft) },
                                                                onDeleteBanner = { id -> deleteBanner(id) },
                                                                onResetBanner = { resetBannerDraft(seedOrder = heroSlides.size) },
                                                                bannerSaving = bannerSaving,
                                                                bannerSavingProgress = bannerSavingProgress,
                                                                loadingHeroBanners = loadingHeroBanners,
                                                                featuredSlots = featuredSlots,
                                                                simulatedHeroBannerId = simulatedHeroBannerId,
                                                                onSimulatedHeroBannerChange = { simulatedHeroBannerId = it },
                                                                adminSurface = adminSurface,
                                                                onSurfaceChange = { adminSurface = it },
                                                                onBack = { currentScreen = MainScreen.Home },
                                                                onUpdateApplicationStatus = { id, status -> updateApplicationStatus(id, status, ::addAdminActivity) },
                                                                onUpdateEarlyBird = { id, enabled, discount, hours, startNow, paused ->
                                                                    updateEarlyBirdConfig(id, enabled, discount, hours, startNow, paused, ::addAdminActivity)
                                                                },
                                                                onResolveReport = { id -> resolveReport(id, ::addAdminActivity) },
                                                                onWarnReport = { id, template, note -> warnReport(id, template, note, ::addAdminActivity) },
                                                                onToggleFlag = { key, enabled -> toggleFlag(key, enabled, ::addAdminActivity) },
                                                                onOpenApplications = { adminSurface = AdminSurface.Applications },
                                                                onOpenModeration = { adminSurface = AdminSurface.Moderation },
                                                                onOpenCatalog = { adminSurface = AdminSurface.Catalog },
                                                                onVerifyOrganizer = { id, verified -> verifyOrganizer(id, verified, ::addAdminActivity) },
                                                                onFreezeOrganizer = { id, frozen -> freezeOrganizer(id, frozen, ::addAdminActivity) },
                                                                onRequestReKyc = { id -> requestReKyc(id, ::addAdminActivity) },
                                                                onChangeRole = { id, role -> changeRole(id, role, ::addAdminActivity) },
                                                                onToggleFeaturedSlot = { id, enabled -> toggleFeaturedSlot(id, enabled, ::addAdminActivity) },
                                                                onMoveFeaturedSlot = { id, delta -> moveFeaturedSlot(id, delta, ::addAdminActivity) },
                                                                addActivity = ::addAdminActivity,
                                                                activityLog = adminActivity,
                                                                reviewers = adminReviewers,
                                                                reviewerByApp = reviewerByApp,
                                                                commentsByApp = commentsByApp,
                                                            )
                                                            LaunchedEffect(Unit) {
                                                                Analytics.log(
                                                                    AnalyticsEvent(
                                                                        name = "admin_view",
                                                                        params = mapOf(
                                                                            "role" to currentUserRole,
                                                                            "admin_flag" to adminFeatureFlagEnabled.toString(),
                                                                            "surface" to adminSurface.name
                                                                        )
                                                                    )
                                                                )
                                                            }
                                                        } else {
                                                            AdminGateScreen(
                                                                role = currentUserRole,
                                                                flagEnabled = adminFeatureFlagEnabled,
                                                                onBack = { currentScreen = MainScreen.Home },
                                                                onRequestAccess = {
                                                                    showAdminGateDialog = false
                                                                    currentScreen = MainScreen.Profile
                                                                }
                                                            )
                                                        }
                                                    }
                                                    MainScreen.Settings -> {
                                                        SettingsScreen(
                                                            prefs = settingsPrefs,
                                                            onPrefsChange = { newPrefs ->
                                                                settingsPrefs = newPrefs
                                                                val user = authRepo.currentUser()
                                                                if (user != null) {
                                                                    scope.launch {
                                                                        saveUserSettingsToFirestore(user.uid, newPrefs)
                                                                    }
                                                                }
                                                            },
                                                            onBack = { currentScreen = MainScreen.Profile },
                                                            onOpenPrivacyTerms = { currentScreen = MainScreen.PrivacyTerms },
                                                            onOpenFaq = { currentScreen = MainScreen.FAQ },
                                                        )
                                                    }
                                                    MainScreen.PrivacyTerms -> {
                                                        LegalScreen(onBack = { currentScreen = MainScreen.Settings })
                                                    }
                                                    MainScreen.FAQ -> {
                                                        FaqScreen(onBack = { currentScreen = MainScreen.Settings })
                                                    }
                                                    MainScreen.Map -> {
                                                        EventMapScreen(
                                                            onBack = { currentScreen = MainScreen.Home },
                                                            onOpenEvent = { eventId -> openEventById(eventId) }
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

                    if (isGuestMode && isGuestIntroActive) {
                        GuestIntroOverlay(progress = guestIntroProgress)
                    }
                    if (!isGuestMode && isSignInWarmupActive) {
                        SignInWarmupOverlay(progress = signInWarmupProgress)
                    }
                }
            }
        }
    }
}

@Composable
private fun AdminDashboardScreen(
    kpis: List<AdminKpi>,
    attention: List<AdminAttention>,
    activity: List<AdminActivity>,
    applications: List<AdminApplication>,
    reports: List<AdminReport>,
    flags: List<AdminFeatureFlag>,
    organizers: List<AdminOrganizer>,
    roles: List<AdminRoleEntry>,
    heroSlides: List<HeroSlide>,
    bannerDraft: BannerDraft,
    onBannerDraftChange: (BannerDraft) -> Unit,
    onPickBannerImage: () -> Unit,
    onSaveBanner: () -> Unit,
    onDeleteBanner: (String) -> Unit,
    onResetBanner: () -> Unit,
    bannerSaving: Boolean,
    bannerSavingProgress: Float,
    loadingHeroBanners: Boolean,
    featuredSlots: List<String>,
    simulatedHeroBannerId: String?,
    onSimulatedHeroBannerChange: (String?) -> Unit,
    adminSurface: AdminSurface,
    onSurfaceChange: (AdminSurface) -> Unit,
    onBack: () -> Unit,
    onUpdateApplicationStatus: (String, String) -> Unit,
    onUpdateEarlyBird: (String, Boolean, Int, Int, Boolean, Boolean) -> Unit,
    onResolveReport: (String) -> Unit,
    onWarnReport: (String, String, String) -> Unit,
    onToggleFlag: (String, Boolean) -> Unit,
    onOpenModeration: () -> Unit,
    onOpenApplications: () -> Unit,
    onOpenCatalog: () -> Unit,
    onVerifyOrganizer: (String, Boolean) -> Unit,
    onFreezeOrganizer: (String, Boolean) -> Unit,
    onRequestReKyc: (String) -> Unit,
    onChangeRole: (String, String) -> Unit,
    onToggleFeaturedSlot: (String, Boolean) -> Unit,
    onMoveFeaturedSlot: (String, Int) -> Unit,
    addActivity: (String, Color) -> Unit,
    activityLog: List<AdminActivity> = activity,
    reviewers: List<String>,
    reviewerByApp: MutableMap<String, String>,
    commentsByApp: MutableMap<String, SnapshotStateList<String>>,
) {
    val scrollState = rememberScrollState()
    val baseModifier = Modifier
        .fillMaxSize()
        .background(GoTickyGradients.CardGlow)
        .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 120.dp)

    val centeredAdminTitle: @Composable () -> Unit = {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Admin Home",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

    if (adminSurface == AdminSurface.Applications) {
        Column(
            modifier = baseModifier,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            GlowCard(modifier = Modifier.fillMaxWidth()) {
                TopBar(
                    title = "Admin dashboard",
                    onBack = null,
                    actions = null,
                    titleContent = centeredAdminTitle,
                    backgroundColor = Color.Transparent
                )
            }
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    AdminSurface.Dashboard to "Admin Home",
                    AdminSurface.Applications to "Applications",
                    AdminSurface.Moderation to "Moderation",
                    AdminSurface.Organizer to "Organizer",
                    AdminSurface.Catalog to "Catalog",
                    AdminSurface.Banners to "Banners",
                    AdminSurface.Settings to "Settings"
                ).forEach { (surface, label) ->
                    val selected = adminSurface == surface
                    NeonSelectablePill(
                        text = label,
                        selected = selected,
                        onClick = { onSurfaceChange(surface) }
                    )
                }
            }
            ApplicationsSection(
                applications = applications,
                onUpdateStatus = onUpdateApplicationStatus,
                onUpdateEarlyBird = onUpdateEarlyBird,
                addActivity = addActivity,
                activityLog = activityLog,
                reviewers = reviewers,
                reviewerByApp = reviewerByApp,
                commentsByApp = commentsByApp,
                organizers = organizers
            )
        }
        return
    }

    Column(
        modifier = baseModifier
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        GlowCard(modifier = Modifier.fillMaxWidth()) {
            TopBar(
                title = "Admin dashboard",
                onBack = null,
                actions = null,
                titleContent = centeredAdminTitle,
                backgroundColor = Color.Transparent
            )
        }
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(
                AdminSurface.Dashboard to "Admin Home",
                AdminSurface.Applications to "Applications",
                AdminSurface.Moderation to "Moderation",
                AdminSurface.Organizer to "Organizer",
                AdminSurface.Catalog to "Catalog",
                AdminSurface.Banners to "Banners",
                AdminSurface.Settings to "Settings"
            ).forEach { (surface, label) ->
                val selected = adminSurface == surface
                NeonSelectablePill(
                    text = label,
                    selected = selected,
                    onClick = { onSurfaceChange(surface) }
                )
            }
        }

        when (adminSurface) {
            AdminSurface.Dashboard -> {
                val searchPrimary = MaterialTheme.colorScheme.primary
                val searchSecondary = MaterialTheme.colorScheme.secondary
                GlowCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Global search", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                        var query by remember { mutableStateOf("") }
                        OutlinedTextField(
                            value = query,
                            onValueChange = { query = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Search events, organizers, users") },
                            singleLine = true,
                            shape = goTickyShapes.medium,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline
                            )
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            NeonTextButton(text = "Search", onClick = { addActivity("Searched: $query", searchPrimary) })
                            NeonTextButton(text = "Advanced filters", onClick = { addActivity("Open advanced search", searchSecondary) })
                        }
                    }
                }

                GlowCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Top risk items", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                        val highRiskApps = applications.filter { it.risk == "High" }.take(3)
                        val highSeverityReports = reports.filter { it.severity == "High" }.take(3)
                        if (highRiskApps.isEmpty() && highSeverityReports.isEmpty()) {
                            Text("No high-risk items right now.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        } else {
                            highRiskApps.forEach { app ->
                                Text("App: ${app.title} (${app.status}) • ${app.city}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                            }
                            highSeverityReports.forEach { rep ->
                                Text("Report: ${rep.target} • ${rep.reason}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }

                GlowCard(modifier = Modifier.fillMaxWidth()) {
                    val approvedCount = applications.count { it.status == "Approved" }
                    val liveRatio = if (applications.isNotEmpty()) approvedCount / applications.size.toFloat() else 0f
                    val earlyBirdLive = applications.count { it.earlyBirdEnabled && !it.earlyBirdPaused }
                    val publishAccent = IconCategoryColors[IconCategory.Ticket] ?: MaterialTheme.colorScheme.primary
                    val catalogAccent = IconCategoryColors[IconCategory.Discover] ?: MaterialTheme.colorScheme.secondary
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text("Customer sync", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                        Text(
                            "These toggles directly impact what customers see on Home and Tickets.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("Published to customer view", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            AnimatedProgressBar(progress = liveRatio.coerceIn(0f, 1f), modifier = Modifier.fillMaxWidth())
                            Text("$approvedCount of ${applications.size} events visible", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface)
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("Early Bird coverage", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            AnimatedProgressBar(
                                progress = if (applications.isNotEmpty()) earlyBirdLive / applications.size.toFloat() else 0f,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text("$earlyBirdLive using Early Bird", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface)
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            applications.take(3).forEach { app ->
                                val isApproved = app.status == "Approved"
                                val toggleLabel = if (isApproved) "Unpublish" else "Publish"
                                val toggleAccent = if (isApproved) MaterialTheme.colorScheme.onSurfaceVariant else publishAccent
                                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Text(app.title, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                                        Pill(text = app.status, modifier = Modifier)
                                        Spacer(Modifier.weight(1f))
                                        NeonTextButton(text = "Preview public", onClick = { addActivity("Preview as customer: ${app.title}", catalogAccent) })
                                    }
                                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                        PrimaryButton(
                                            text = toggleLabel,
                                            modifier = Modifier.weight(1f),
                                            onClick = {
                                                val next = if (isApproved) "In Review" else "Approved"
                                                onUpdateApplicationStatus(app.id, next)
                                                val suffix = if (isApproved) "hidden from" else "published to"
                                                addActivity("${app.title} $suffix customers", toggleAccent)
                                            }
                                        )
                                        GhostButton(
                                            text = "Catalog",
                                            modifier = Modifier.weight(1f),
                                            onClick = onOpenCatalog
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                SectionHeader(
                    title = "Health KPIs",
                    action = { NeonTextButton(text = "View all", onClick = onOpenApplications) }
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(kpis) { kpi ->
                        AdminKpiCard(kpi)
                    }
                }

                SectionHeader(
                    title = "Needs attention",
                    action = { NeonTextButton(text = "Open queue", onClick = onOpenApplications) }
                )
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    attention.forEach { item ->
                        AdminAttentionCard(item)
                    }
                }

                SectionHeader(
                    title = "Recent activity",
                    action = null
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    activity.forEach { item ->
                        AdminActivityRow(item)
                    }
                }
            }

            AdminSurface.Applications -> {
                ApplicationsSection(
                    applications = applications,
                    onUpdateStatus = onUpdateApplicationStatus,
                    onUpdateEarlyBird = onUpdateEarlyBird,
                    addActivity = addActivity,
                    activityLog = activityLog,
                    reviewers = reviewers,
                    reviewerByApp = reviewerByApp,
                    commentsByApp = commentsByApp,
                    organizers = organizers
                )
            }

            AdminSurface.Moderation -> {
                ModerationSection(
                    reports = reports,
                    addActivity = addActivity,
                    onResolve = onResolveReport,
                    onWarn = onWarnReport
                )
            }

            AdminSurface.Organizer -> {
                OrganizerSection(
                    organizers = organizers,
                    onVerify = onVerifyOrganizer,
                    onFreeze = onFreezeOrganizer,
                    onRequestReKyc = onRequestReKyc,
                    addActivity = addActivity
                )
            }

            AdminSurface.Catalog -> {
                CatalogSection(
                    flags = flags,
                    onToggleFlag = onToggleFlag,
                    onOpenModeration = onOpenModeration,
                    onOpenApplications = onOpenApplications,
                    addActivity = addActivity,
                )
            }

            AdminSurface.Banners -> {
                BannersSection(
                    addActivity = addActivity,
                    heroSlides = heroSlides,
                    featuredSlots = featuredSlots,
                    onToggleFeaturedSlot = onToggleFeaturedSlot,
                    onMoveFeaturedSlot = onMoveFeaturedSlot,
                    bannerDraft = bannerDraft,
                    onBannerDraftChange = onBannerDraftChange,
                    onPickBannerImage = onPickBannerImage,
                    onSaveBanner = onSaveBanner,
                    onDeleteBanner = onDeleteBanner,
                    onResetBanner = onResetBanner,
                    bannerSaving = bannerSaving,
                    bannerSavingProgress = bannerSavingProgress,
                    loadingHeroBanners = loadingHeroBanners,
                    simulatedHeroBannerId = simulatedHeroBannerId,
                    onSimulatedHeroBannerChange = onSimulatedHeroBannerChange,
                )
            }

            AdminSurface.Settings -> {
                SettingsSection(
                    flags = flags,
                    onToggleFlag = onToggleFlag,
                    roles = roles,
                    onChangeRole = onChangeRole,
                    auditLog = activity
                )
            }
        }
    }
}

@Composable
private fun ApplicationsSection(
    applications: List<AdminApplication>,
    onUpdateStatus: (String, String) -> Unit,
    onUpdateEarlyBird: (String, Boolean, Int, Int, Boolean, Boolean) -> Unit,
    addActivity: (String, Color) -> Unit,
    activityLog: List<AdminActivity>,
    reviewers: List<String>,
    reviewerByApp: MutableMap<String, String>,
    commentsByApp: MutableMap<String, SnapshotStateList<String>>,
    organizers: List<AdminOrganizer>,
) {
    var selectedAppId by remember { mutableStateOf(applications.firstOrNull()?.id) }
    var detailAppId by remember { mutableStateOf<String?>(null) }
    var statusFilter by remember { mutableStateOf("All") }
    var riskFilter by remember { mutableStateOf("All") }
    var cityFilter by remember { mutableStateOf("All") }
    var categoryFilter by remember { mutableStateOf("All") }
    var organizerFilter by remember { mutableStateOf("All") }
    var sortMode by remember { mutableStateOf("Oldest") }
    val selectedBulk = remember { mutableStateListOf<String>() }
    var showFilterSheet by remember { mutableStateOf(false) }
    var showSortSheet by remember { mutableStateOf(false) }
    val cities = remember(applications) { listOf("All") + applications.map { it.city }.distinct() }
    val categories = remember(applications) { listOf("All") + applications.map { it.category }.distinct() }
    val organizerFilters = remember(applications) { listOf("All") + applications.map { it.organizer }.distinct() }
    val riskWeight = mapOf("High" to 3, "Medium" to 2, "Low" to 1)
    val filtered = applications
        .filter { statusFilter == "All" || it.status == statusFilter }
        .filter { riskFilter == "All" || it.risk == riskFilter }
        .filter { cityFilter == "All" || it.city == cityFilter }
        .filter { categoryFilter == "All" || it.category == categoryFilter }
        .filter { organizerFilter == "All" || it.organizer == organizerFilter }
        .let { list ->
            when (sortMode) {
                "Newest" -> list.sortedByDescending { it.ageHours }
                "Risk" -> list.sortedWith(
                    compareByDescending<AdminApplication> { riskWeight[it.risk] ?: 0 }
                        .thenBy { it.ageHours }
                )
                else -> list.sortedBy { it.ageHours }
            }
        }
    val scrollState = rememberScrollState()
    val activityPrimaryAccent = MaterialTheme.colorScheme.primary
    val activitySecondaryAccent = MaterialTheme.colorScheme.secondary
    var bulkDialogAction by remember { mutableStateOf<String?>(null) }
    var bulkRationale by remember { mutableStateOf("") }
    var bulkReviewer by remember { mutableStateOf(reviewers.firstOrNull() ?: "") }
    var bulkError by remember { mutableStateOf<String?>(null) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            SectionHeader(
                title = "Applications",
                action = null
            )

            val hasFilterActive = statusFilter != "All" ||
                riskFilter != "All" ||
                cityFilter != "All" ||
                categoryFilter != "All" ||
                organizerFilter != "All"

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlowCard(
                    modifier = Modifier
                        .weight(1f)
                        .pressAnimated()
                        .clickable { showFilterSheet = !showFilterSheet }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(Icons.Outlined.FilterList, contentDescription = "Filters", tint = MaterialTheme.colorScheme.primary)
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1f)) {
                            Text("Filters", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface)
                            val summary = buildString {
                                if (hasFilterActive) append("Active • ")
                                append(statusFilter.ifBlank { "All" })
                                if (riskFilter != "All") append(" • Risk $riskFilter")
                                if (cityFilter != "All") append(" • $cityFilter")
                            }
                            Text(summary, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Pill(text = if (hasFilterActive) "On" else "All", modifier = Modifier)
                    }
                }
                GlowCard(
                    modifier = Modifier
                        .weight(1f)
                        .pressAnimated()
                        .clickable { showSortSheet = !showSortSheet }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(Icons.Outlined.Sort, contentDescription = "Sort", tint = MaterialTheme.colorScheme.secondary)
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1f)) {
                            Text("Sort", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface)
                            Text("Mode: $sortMode", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Pill(text = "Change", modifier = Modifier)
                    }
                }
            }

            AnimatedVisibility(visible = showFilterSheet) {
                GlowCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(goTickyShapes.large)
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Status", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(listOf("All", "New", "In Review", "Needs Info", "Approved", "Rejected")) { option ->
                                NeonSelectablePill(text = option, selected = statusFilter == option, onClick = { statusFilter = option })
                            }
                        }

                        Text("Risk", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(listOf("All", "Low", "Medium", "High")) { risk ->
                                NeonSelectablePill(text = if (risk == "All") "All risk" else "Risk: $risk", selected = riskFilter == risk, onClick = { riskFilter = risk })
                            }
                        }

                        Text("City", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(cities) { city ->
                                NeonSelectablePill(text = if (city == "All") "All cities" else city, selected = cityFilter == city, onClick = { cityFilter = city })
                            }
                        }

                        Text("Category", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(categories) { cat ->
                                NeonSelectablePill(text = if (cat == "All") "All categories" else cat, selected = categoryFilter == cat, onClick = { categoryFilter = cat })
                            }
                        }

                        Text("Organizer", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(organizerFilters) { org ->
                                NeonSelectablePill(text = if (org == "All") "All organizers" else org, selected = organizerFilter == org, onClick = { organizerFilter = org })
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                            GhostButton(text = "Reset", modifier = Modifier.weight(1f)) {
                                statusFilter = "All"
                                riskFilter = "All"
                                cityFilter = "All"
                                categoryFilter = "All"
                                organizerFilter = "All"
                            }
                            PrimaryButton(text = "Close", modifier = Modifier.weight(1f)) {
                                showFilterSheet = false
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(visible = showSortSheet) {
                GlowCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(goTickyShapes.large)
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Sort by", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(listOf("Oldest", "Newest", "Risk")) { mode ->
                                NeonSelectablePill(text = mode, selected = sortMode == mode, onClick = {
                                    sortMode = mode
                                    showSortSheet = false
                                })
                            }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                            GhostButton(text = "Close", modifier = Modifier.weight(1f)) { showSortSheet = false }
                        }
                    }
                }
            }
            if (filtered.isEmpty()) {
                GlowCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text("No applications match these filters.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                        Text("Adjust filters to see the queue.", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            } else {
                if (selectedBulk.isNotEmpty()) {
                    GlowCard {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text("${selectedBulk.size} selected", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                            PrimaryButton(text = "Bulk approve", modifier = Modifier.weight(1f)) {
                                bulkDialogAction = "Approve"
                                bulkError = null
                                bulkRationale = ""
                            }
                            GhostButton(text = "Bulk reject", modifier = Modifier.weight(1f)) {
                                bulkDialogAction = "Reject"
                                bulkError = null
                                bulkRationale = ""
                            }
                            GhostButton(text = "Bulk assign", modifier = Modifier.weight(1f)) {
                                bulkDialogAction = "Assign"
                                bulkError = null
                            }
                        }
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    filtered.forEach { app ->
                        val selectedForBulk = selectedBulk.contains(app.id)
                        ApplicationCard(
                            app = app,
                            selected = app.id == selectedAppId,
                            onSelect = {
                                selectedAppId = app.id
                                detailAppId = app.id
                            },
                            onToggleSelect = {
                                if (selectedForBulk) selectedBulk.remove(app.id) else selectedBulk.add(app.id)
                            },
                            selectedForBulk = selectedForBulk,
                            onUpdateStatus = onUpdateStatus
                        )
                    }
                }
            }

            GlowCard {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Notification templates", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                    listOf(
                        "Info needed" to "Ask organizer for missing docs",
                        "Approved" to "Congrats + next steps",
                        "Rejected" to "Provide rationale"
                    ).forEach { (title, desc) ->
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(title, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                                Text(desc, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            NeonTextButton(text = "Preview", onClick = { addActivity("Preview template: $title", activitySecondaryAccent) })
                        }
                    }
                }
            }

            GlowCard {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Audit log", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                    val recent = activityLog.takeLast(10).reversed()
                    if (recent.isEmpty()) {
                        Text("No activity yet.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    } else {
                        recent.forEach { item ->
                            Text(item.text, style = MaterialTheme.typography.bodySmall, color = item.accent)
                        }
                    }
                }
            }

            bulkDialogAction?.let { action ->
                AlertDialog(
                    onDismissRequest = {
                        bulkDialogAction = null
                        bulkRationale = ""
                        bulkError = null
                    },
                    confirmButton = {
                        PrimaryButton(
                            text = "Confirm",
                            modifier = Modifier.pressAnimated()
                        ) {
                            val ids = selectedBulk.toList()
                            when (action) {
                                "Approve" -> {
                                    ids.forEach { id -> onUpdateStatus(id, "Approved") }
                                    addActivity("Bulk approved ${ids.size} apps", activityPrimaryAccent)
                                    selectedBulk.clear()
                                    bulkDialogAction = null
                                }
                                "Reject" -> {
                                    if (bulkRationale.isBlank()) {
                                        bulkError = "Rationale required for bulk reject."
                                    } else {
                                        ids.forEach { id -> onUpdateStatus(id, "Rejected") }
                                        addActivity("Bulk rejected ${ids.size} apps — ${bulkRationale.trim()}", Color(0xFFFF6B6B))
                                        selectedBulk.clear()
                                        bulkDialogAction = null
                                        bulkRationale = ""
                                        bulkError = null
                                    }
                                }
                                "Assign" -> {
                                    if (bulkReviewer.isBlank()) {
                                        bulkError = "Select an owner."
                                    } else {
                                        ids.forEach { id -> reviewerByApp[id] = bulkReviewer }
                                        addActivity("Bulk assigned ${ids.size} apps to $bulkReviewer", activitySecondaryAccent)
                                        selectedBulk.clear()
                                        bulkDialogAction = null
                                        bulkError = null
                                    }
                                }
                            }
                        }
                    },
                    dismissButton = {
                        GhostButton(text = "Cancel") {
                            bulkDialogAction = null
                            bulkRationale = ""
                            bulkError = null
                        }
                    },
                    title = { Text("Bulk ${action.lowercase()}", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text("${selectedBulk.size} applications selected.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                            if (action == "Assign") {
                                Text("Assign owner", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    reviewers.forEach { reviewer ->
                                        NeonSelectablePill(
                                            text = reviewer,
                                            selected = bulkReviewer == reviewer,
                                            onClick = { bulkReviewer = reviewer }
                                        )
                                    }
                                }
                            } else {
                                OutlinedTextField(
                                    value = bulkRationale,
                                    onValueChange = { bulkRationale = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    placeholder = { Text("Add rationale (required for reject)") },
                                    singleLine = false,
                                    minLines = 2,
                                    shape = goTickyShapes.medium,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                                    ),
                                    isError = action == "Reject" && bulkRationale.isBlank()
                                )
                            }
                            bulkError?.let { err ->
                                Text(err, style = MaterialTheme.typography.labelSmall, color = Color(0xFFFF6B6B))
                            }
                        }
                    }
                )
            }
        }

        AnimatedVisibility(
            visible = detailAppId != null,
            enter = fadeIn() + slideInVertically { it / 6 },
            exit = fadeOut() + slideOutVertically { it / 6 },
            modifier = Modifier
                .fillMaxSize()
                .background(GoTickyGradients.CardGlow)
        ) {
            val app = applications.firstOrNull { it.id == detailAppId }
            val organizer = organizers.firstOrNull { it.name == app?.organizer }
            if (app != null) {
                ApplicationDetailScreen(
                    app = app,
                    organizer = organizer,
                    reviewers = reviewers,
                    reviewerByApp = reviewerByApp,
                    commentsByApp = commentsByApp,
                    addActivity = addActivity,
                    onClose = { detailAppId = null },
                    onUpdateStatus = { status: String, rationale: String ->
                        onUpdateStatus(app.id, status)
                        val accent = when (status) {
                            "Approved" -> activityPrimaryAccent
                            "Rejected" -> Color(0xFFFF6B6B)
                            "Needs Info" -> Color(0xFFFFC94A)
                            else -> activitySecondaryAccent
                        }
                        val suffix = if (rationale.isBlank()) "" else " — ${rationale.trim()}"
                        addActivity("$status: ${app.title}$suffix", accent)
                    },
                    onUpdateEarlyBird = { enabled, discount, duration, startNow, paused ->
                        onUpdateEarlyBird(app.id, enabled, discount, duration, startNow, paused)
                    }
                )
            }
        }
    }
}

@Composable
private fun ApplicationCard(
    app: AdminApplication,
    selected: Boolean,
    onSelect: () -> Unit,
    onToggleSelect: () -> Unit = {},
    selectedForBulk: Boolean = false,
    onUpdateStatus: (String, String) -> Unit,
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(targetValue = if (pressed) 0.97f else 1f, animationSpec = tween(180), label = "appCardScale")
    val severityColor = when (app.risk) {
        "High" -> Color(0xFFFF6B6B)
        "Medium" -> Color(0xFFFFC94A)
        else -> MaterialTheme.colorScheme.tertiary
    }
    val poster = remember(app.id) {
        resolveProfilePhotoRes() ?: Res.allDrawableResources["hero_vic_falls_midnight_lights"]
    }
    GlowCard(
        modifier = Modifier
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .pressAnimated()
            .then(
                if (selected) {
                    Modifier.border(1.dp, GoTickyGradients.EdgeHalo, goTickyShapes.large)
                } else {
                    Modifier
                }
            )
            .clickable(interactionSource = interaction, indication = null) { onSelect() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Checkbox(checked = selectedForBulk, onCheckedChange = { onToggleSelect() })
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(severityColor)
                )
                Text(app.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface)
                Spacer(Modifier.weight(1f))
                Pill(text = app.status, modifier = Modifier.background(severityColor.copy(alpha = 0.18f), shape = goTickyShapes.small).padding(horizontal = 10.dp, vertical = 6.dp))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                poster?.let { drawable ->
                    Box(
                        modifier = Modifier
                            .size(78.dp)
                            .clip(goTickyShapes.medium)
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.25f))
                    ) {
                        Image(
                            painter = painterResource(drawable),
                            contentDescription = "Event poster",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.12f))
                        )
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.weight(1f)) {
                    Text("${app.organizer} • ${app.city} • ${app.category}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Risk ${app.risk} • ${app.ageHours}h old", style = MaterialTheme.typography.labelSmall, color = severityColor)
                    Text("Attachments: ${if (app.attachmentsReady) "Ready" else "Missing"} • Completeness ${app.completeness}%", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.weight(1f)) {
                    Text("Completeness", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    AnimatedProgressBar(progress = app.completeness / 100f, modifier = Modifier.fillMaxWidth())
                }
                NeonSelectablePill(text = if (app.attachmentsReady) "Docs ready" else "Docs missing", selected = app.attachmentsReady, onClick = {})
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                NeonSelectablePill(text = "Risk ${app.risk}", selected = true, onClick = {})
                NeonSelectablePill(text = "${app.ageHours}h old", selected = false, onClick = {})
            }
            if (app.notes.isNotBlank()) {
                Text(app.notes, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                GhostButton(
                    text = "Needs info",
                    modifier = Modifier.weight(1f),
                    onClick = { onUpdateStatus(app.id, "Needs Info") }
                )
                PrimaryButton(
                    text = "Approve",
                    modifier = Modifier.weight(1f),
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    onClick = { onUpdateStatus(app.id, "Approved") }
                )
            }
            NeonTextButton(text = "Reject", onClick = { onUpdateStatus(app.id, "Rejected") })
        }
    }
}

@Composable
private fun ApplicationDetailScreen(
    app: AdminApplication,
    organizer: AdminOrganizer?,
    reviewers: List<String>,
    reviewerByApp: MutableMap<String, String>,
    commentsByApp: MutableMap<String, SnapshotStateList<String>>,
    addActivity: (String, Color) -> Unit,
    onClose: () -> Unit,
    onUpdateStatus: (String, String) -> Unit,
    onUpdateEarlyBird: (Boolean, Int, Int, Boolean, Boolean) -> Unit,
) {
    val comments = remember(app.id) { commentsByApp.getOrPut(app.id) { mutableStateListOf<String>() } }
    var commentDraft by remember { mutableStateOf("") }
    var rationale by remember { mutableStateOf("") }
    var rationaleError by remember { mutableStateOf(false) }
    var showDocs by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val statusColor = when (app.risk) {
        "High" -> Color(0xFFFF6B6B)
        "Medium" -> Color(0xFFFFC94A)
        else -> MaterialTheme.colorScheme.tertiary
    }
    val commentAccent = MaterialTheme.colorScheme.primary
    val heroPoster = remember(app.id) {
        resolveProfilePhotoRes() ?: Res.allDrawableResources["hero_vic_falls_midnight_lights"]
    }
    val organizerAvatar = remember(organizer?.id) {
        Res.allDrawableResources["gotickypic"] ?: heroPoster
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GoTickyGradients.CardGlow)
            .padding(16.dp)
    ) {
        GlowCard(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(28.dp)
                            .pressAnimated()
                            .clickable { onClose() }
                    )
                    Text(
                        "Application Detail",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.weight(1f))
                }

                GlowCard {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Basic", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                        InfoRow("Title", app.title)
                        InfoRow("Organizer", app.organizer)
                        InfoRow("Category", app.category)
                        InfoRow("City", app.city)
                        val earlyBirdStatusText = remember(app.earlyBirdEnabled, app.earlyBirdPaused, app.earlyBirdDiscountPercent, app.earlyBirdDurationHours) {
                            val core = if (!app.earlyBirdEnabled) {
                                "Disabled"
                            } else if (app.earlyBirdPaused) {
                                "Paused"
                            } else {
                                "Live"
                            }
                            "$core — ${app.earlyBirdDiscountPercent}% for ${app.earlyBirdDurationHours}h"
                        }
                        InfoRow("Early Bird", earlyBirdStatusText)
                        if (app.eventDateTime.isNotBlank()) {
                            InfoRow("Date / Time", app.eventDateTime)
                        }
                        AnimatedProgressBar(progress = app.completeness / 100f, modifier = Modifier.fillMaxWidth())
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            NeonSelectablePill(text = app.status, selected = true, onClick = {})
                            NeonSelectablePill(text = "Risk ${app.risk}", selected = true, onClick = {})
                            NeonSelectablePill(text = "${app.ageHours}h old", selected = false, onClick = {})
                            NeonSelectablePill(text = if (app.attachmentsReady) "Docs ready" else "Docs missing", selected = app.attachmentsReady, onClick = {})
                        }
                    }
                }

                val pricingDisplay = remember(app.pricingTiers) { app.pricingTiers.take(4) }
                if (pricingDisplay.isNotEmpty()) {
                    GlowCard {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Pricing tiers", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                            pricingDisplay.forEach { tier ->
                                val priceStart = tier.indexOf('$')
                                val price = when {
                                    priceStart >= 0 -> tier.substring(priceStart).trim()
                                    tier.split(" ").lastOrNull()?.any { it.isDigit() } == true -> tier.split(" ").last()
                                    else -> "—"
                                }
                                val name = when {
                                    priceStart >= 0 -> tier.substring(0, priceStart).trim().trimEnd('-', ':', '•').ifBlank { "Ticket" }
                                    tier.contains(" ") -> tier.substringBeforeLast(" ").ifBlank { "Ticket" }
                                    else -> tier.ifBlank { "Ticket" }
                                }
                                InfoRow(name, price)
                            }
                        }
                    }
                }

                heroPoster?.let { drawable ->
                    GlowCard {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(goTickyShapes.large)
                            ) {
                                Image(
                                    painter = painterResource(drawable),
                                    contentDescription = "Event flyer",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                Box(
                                    modifier = Modifier
                                        .matchParentSize()
                                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.12f))
                                )
                            }
                            Text("Flyer preview", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }

                GlowCard {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Organizer", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                        organizer?.let {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                organizerAvatar?.let { avatar ->
                                    Image(
                                        painter = painterResource(avatar),
                                        contentDescription = "Organizer avatar",
                                        modifier = Modifier
                                            .size(56.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.surfaceVariant),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.weight(1f)) {
                                    Text(it.name, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                                    Text("KYC: ${it.kycStatus}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("Trust ${it.trustScore} • Strikes ${it.strikes} • Frozen: ${it.frozen}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                NeonSelectablePill(text = "View profile", selected = false, onClick = {})
                            }
                            if (it.notes.isNotBlank()) {
                                Text(it.notes, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        } ?: Text("No organizer record linked", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                GlowCard {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Tickets", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                        InfoRow("Completeness", "${app.completeness}%")
                        InfoRow("Docs", if (app.attachmentsReady) "Ready" else "Missing")
                        InfoRow("Submitted", "${app.ageHours}h ago")
                    }
                }

                GlowCard {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Venue details", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                        InfoRow("City", app.city)
                        InfoRow("Category", app.category)
                        if (app.notes.isNotBlank()) {
                            InfoRow("Notes", app.notes)
                        }
                    }
                }

                // Early Bird configuration
                GlowCard {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            "Early Bird settings",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        var enabled by remember(app.id) { mutableStateOf(app.earlyBirdEnabled) }
                        var paused by remember(app.id) { mutableStateOf(app.earlyBirdPaused) }
                        var discount by remember(app.id) { mutableStateOf(app.earlyBirdDiscountPercent.coerceIn(10, 50)) }
                        var duration by remember(app.id) { mutableStateOf(app.earlyBirdDurationHours.coerceIn(24, 168)) }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text("Enabled", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                                Text(
                                    if (enabled) "Customers see Early Bird once approved" else "Early Bird hidden for this event",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Switch(
                                checked = enabled,
                                onCheckedChange = {
                                    enabled = it
                                    onUpdateEarlyBird(enabled, discount, duration, false, paused)
                                }
                            )
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Discount", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                            Text("$discount% off base GA", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Slider(
                                value = discount.toFloat(),
                                onValueChange = {
                                    val stepped = (it / 5f).toInt() * 5
                                    discount = stepped.coerceIn(10, 50)
                                    onUpdateEarlyBird(enabled, discount, duration, false, paused)
                                },
                                valueRange = 10f..50f
                            )
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Duration", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                            val days = duration / 24
                            Text("${duration}h (${days}d)", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Slider(
                                value = duration.toFloat(),
                                onValueChange = {
                                    val stepped = (it / 24f).toInt() * 24
                                    duration = stepped.coerceIn(24, 168)
                                    onUpdateEarlyBird(enabled, discount, duration, false, paused)
                                },
                                valueRange = 24f..168f
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                            GhostButton(text = if (paused) "Resume" else "Pause", modifier = Modifier.weight(1f)) {
                                paused = !paused
                                onUpdateEarlyBird(enabled, discount, duration, false, paused)
                            }
                            PrimaryButton(text = "Start now", modifier = Modifier.weight(1f)) {
                                onUpdateEarlyBird(enabled, discount, duration, true, paused)
                            }
                        }
                    }
                }

                NeonTextButton(
                    text = if (showDocs) "Hide docs & checklist" else "Show docs & checklist",
                    onClick = { showDocs = !showDocs }
                )
                AnimatedVisibility(visible = showDocs) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        val docChecklist = listOf(
                            "Venue permit" to app.attachmentsReady,
                            "Insurance" to app.attachmentsReady,
                            "ID upload" to true,
                            "Floorplan" to true,
                        )
                        docChecklist.forEach { (label, done) ->
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(
                                    imageVector = if (done) Icons.Outlined.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                                    contentDescription = null,
                                    tint = if (done) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    GhostButton(text = "Needs info", modifier = Modifier.weight(1f)) {
                        if (rationale.isBlank()) {
                            rationaleError = true
                        } else {
                            rationaleError = false
                            onUpdateStatus("Needs Info", rationale)
                            addActivity("Needs info: ${app.title}", Color(0xFFFFC94A))
                        }
                    }
                    PrimaryButton(text = "Approve", modifier = Modifier.weight(1f)) {
                        onUpdateStatus("Approved", rationale)
                        rationaleError = false
                    }
                }
                NeonTextButton(
                    text = "Reject",
                    onClick = {
                        if (rationale.isBlank()) {
                            rationaleError = true
                        } else {
                            rationaleError = false
                            onUpdateStatus("Rejected", rationale)
                            addActivity("Rejected: ${app.title}", Color(0xFFFF6B6B))
                        }
                    }
                )
                OutlinedTextField(
                    value = rationale,
                    onValueChange = { rationale = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Add rationale (required for reject/needs info)") },
                    singleLine = false,
                    minLines = 2,
                    shape = goTickyShapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    ),
                    isError = rationaleError
                )
                if (rationaleError) {
                    Text("Rationale is required for Needs info / Reject.", style = MaterialTheme.typography.labelSmall, color = Color(0xFFFF6B6B))
                }

                Text("Comments", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (comments.isEmpty()) {
                        Text("No comments yet.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    } else {
                        comments.forEach { msg ->
                            GlowCard {
                                Text(msg, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(10.dp))
                            }
                        }
                    }
                    OutlinedTextField(
                        value = commentDraft,
                        onValueChange = { commentDraft = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Add a comment") },
                        singleLine = false,
                        minLines = 2,
                        shape = goTickyShapes.medium
                    )
                    PrimaryButton(text = "Post comment") {
                        if (commentDraft.isNotBlank()) {
                            comments.add(commentDraft.trim())
                            addActivity("Commented on ${app.title}", commentAccent)
                            commentDraft = ""
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.widthIn(min = 88.dp)
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ApplicationDetailPanel(
    app: AdminApplication,
    onUpdateStatus: (String, String) -> Unit,
    reviewers: List<String>,
    reviewerByApp: MutableMap<String, String>,
    commentsByApp: MutableMap<String, SnapshotStateList<String>>,
    addActivity: (String, Color) -> Unit,
) {
    var showNotes by remember { mutableStateOf(false) }
    var selectedReviewer by remember(app.id) { mutableStateOf(reviewerByApp[app.id] ?: reviewers.firstOrNull()) }
    val comments = remember(app.id) { commentsByApp.getOrPut(app.id) { mutableStateListOf<String>() } }
    var commentDraft by remember { mutableStateOf("") }
    val activityPrimaryAccent = MaterialTheme.colorScheme.primary
    val activitySecondaryAccent = MaterialTheme.colorScheme.secondary
    val slaHoursRemaining = (36 - app.ageHours).coerceAtLeast(0)
    val attachments = listOf("Venue permit", "Insurance", "ID upload", "Floorplan")
    val checklist = listOf(
        "Organizer verified" to app.attachmentsReady,
        "Pricing rationale provided" to (app.completeness > 60),
        "Media quality ok" to true,
        "Venue capacity ok" to true,
    )
    var rationale by remember { mutableStateOf("") }
    var rationaleError by remember { mutableStateOf(false) }
    val timeline = remember(app.id) {
        listOf(
            "Submitted ${app.ageHours}h ago by ${app.organizer}",
            "Auto validation flagged ${if (app.attachmentsReady) "0" else "2"} items",
            "Assigned admin: ${selectedReviewer ?: reviewers.firstOrNull().orEmpty()}",
            "Awaiting permit upload" + if (app.attachmentsReady) " (received)" else "",
        )
    }
    GlowCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Application detail", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface)
            Text("${app.title} • ${app.city}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            AnimatedProgressBar(progress = app.completeness / 100f, modifier = Modifier.fillMaxWidth())
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                NeonSelectablePill(text = app.status, selected = true, onClick = {})
                NeonSelectablePill(text = "Risk ${app.risk}", selected = true, onClick = {})
                NeonSelectablePill(text = "${app.ageHours}h old", selected = false, onClick = {})
                NeonSelectablePill(text = "SLA ${slaHoursRemaining}h left", selected = slaHoursRemaining > 0, onClick = {})
            }
            Text("Assigned admin", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                reviewers.forEach { reviewer ->
                    val selected = reviewer == selectedReviewer
                    NeonSelectablePill(
                        text = reviewer,
                        selected = selected,
                        onClick = {
                            selectedReviewer = reviewer
                            reviewerByApp[app.id] = reviewer
                            addActivity("Assigned $reviewer to ${app.title}", activitySecondaryAccent)
                        }
                    )
                }
            }
            Text("Checklist", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                checklist.forEach { (label, done) ->
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(
                            imageVector = if (done) Icons.Outlined.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                            contentDescription = null,
                            tint = if (done) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
            Text("Attachments", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                attachments.forEach { att ->
                    NeonSelectablePill(text = att, selected = app.attachmentsReady, onClick = {})
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                GhostButton(text = "Needs info", modifier = Modifier.weight(1f)) {
                    if (rationale.isBlank()) {
                        rationaleError = true
                    } else {
                        rationaleError = false
                        onUpdateStatus("Needs Info", rationale)
                        addActivity("Needs info: ${app.title}", Color(0xFFFFC94A))
                    }
                }
                PrimaryButton(text = "Approve", modifier = Modifier.weight(1f)) {
                    onUpdateStatus("Approved", rationale)
                    rationaleError = false
                }
            }
            NeonTextButton(text = "Reject", onClick = {
                if (rationale.isBlank()) {
                    rationaleError = true
                } else {
                    rationaleError = false
                    onUpdateStatus("Rejected", rationale)
                    addActivity("Rejected: ${app.title}", Color(0xFFFF6B6B))
                }
            })
            OutlinedTextField(
                value = rationale,
                onValueChange = { rationale = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Add rationale (required for reject/needs info)") },
                singleLine = false,
                minLines = 2,
                shape = goTickyShapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                ),
                isError = rationaleError
            )
            if (rationaleError) {
                Text("Rationale is required for Needs info / Reject.", style = MaterialTheme.typography.labelSmall, color = Color(0xFFFF6B6B))
            }
            NeonTextButton(
                text = if (showNotes) "Hide internal notes" else "Show internal notes",
                onClick = { showNotes = !showNotes }
            )
            AnimatedVisibility(visible = showNotes) {
                Text("Internal notes: ${app.notes.ifBlank { "No notes yet" }}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text("Comments", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                if (comments.isEmpty()) {
                    Text("No comments yet.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                } else {
                    comments.forEach { msg ->
                        GlowCard {
                            Text(
                                msg,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = commentDraft,
                        onValueChange = { updated -> commentDraft = updated },
                        modifier = Modifier
                            .weight(1f),
                        placeholder = { Text("Add internal comment") },
                        singleLine = true
                    )
                    PrimaryButton(
                        text = "Add",
                        modifier = Modifier,
                    ) {
                        if (commentDraft.isNotBlank()) {
                            comments.add(0, commentDraft.trim())
                            addActivity("Comment on ${app.title}", activityPrimaryAccent)
                            commentDraft = ""
                        }
                    }
                    GhostButton(text = "Clear") { commentDraft = "" }
                }
            }
            Text("Timeline", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                timeline.forEach { entry ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.7f))
                        )
                        Text(entry, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
    }
}

@Composable
private fun ModerationSection(
    reports: List<AdminReport>,
    addActivity: (String, Color) -> Unit,
    onResolve: (String) -> Unit,
    onWarn: (String, String, String) -> Unit,
) {
    val resolveAccent by rememberUpdatedState(MaterialTheme.colorScheme.primary)
    var warnTargetId by remember { mutableStateOf<String?>(null) }
    var warnTemplate by remember { mutableStateOf("Copy/brand violation") }
    var warnNote by remember { mutableStateOf("") }
    val resolving = remember { mutableStateMapOf<String, Boolean>() }
    val warning = remember { mutableStateMapOf<String, Boolean>() }
    val templates = listOf(
        "Copy/brand violation",
        "Pricing transparency issue",
        "Image inappropriate",
        "Spam / misleading"
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(title = "Moderation", action = null)
        if (reports.isEmpty()) {
            GlowCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "No reports right now.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(14.dp)
                )
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                reports.forEach { report ->
                    val isResolving = resolving[report.id] == true
                    val isWarning = warning[report.id] == true
                    ModerationCard(
                        report = report,
                        isResolving = isResolving,
                        isWarning = isWarning,
                        onResolve = {
                            resolving[report.id] = true
                            onResolve(report.id)
                            addActivity("Resolved report for ${report.target}", resolveAccent)
                            resolving[report.id] = false
                        },
                        onWarn = {
                            warnTargetId = report.id
                        }
                    )
                }
            }
        }
    }

    warnTargetId?.let { targetId ->
        AlertDialog(
            onDismissRequest = {
                warnTargetId = null
                warnNote = ""
            },
            confirmButton = {
                PrimaryButton(
                    text = "Send warning",
                    onClick = {
                        warning[targetId] = true
                        onWarn(targetId, warnTemplate, warnNote)
                        addActivity("Warned ${reports.firstOrNull { it.id == targetId }?.target ?: targetId}", Color(0xFFFFC94A))
                        warnTargetId = null
                        warnNote = ""
                        warning[targetId] = false
                    }
                )
            },
            dismissButton = {
                GhostButton(text = "Cancel") {
                    warnTargetId = null
                    warnNote = ""
                }
            },
            title = {
                Text("Send warning", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Template", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    templates.forEach { template ->
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            RadioButton(selected = warnTemplate == template, onClick = { warnTemplate = template })
                            Text(template, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                    OutlinedTextField(
                        value = warnNote,
                        onValueChange = { warnNote = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Optional note to organizer") },
                        singleLine = false,
                        minLines = 2,
                        shape = goTickyShapes.medium,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                }
            }
        )
    }
}

@Composable
private fun ModerationCard(
    report: AdminReport,
    isResolving: Boolean,
    isWarning: Boolean,
    onResolve: () -> Unit,
    onWarn: () -> Unit,
) {
    val severityColor = when (report.severity) {
        "High" -> Color(0xFFFF6B6B)
        "Medium" -> Color(0xFFFFC94A)
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    GlowCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(severityColor)
                )
                Text(report.target, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                Spacer(Modifier.weight(1f))
                Pill(text = report.state, modifier = Modifier.background(severityColor.copy(alpha = 0.18f), shape = goTickyShapes.small).padding(horizontal = 10.dp, vertical = 6.dp))
            }
            Text(report.reason, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                NeonSelectablePill(text = report.severity, selected = true, onClick = {})
                NeonSelectablePill(text = "${report.ageHours}h old", selected = false, onClick = {})
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                GhostButton(text = if (isWarning) "Sending..." else "Warn", modifier = Modifier.weight(1f)) { onWarn() }
                PrimaryButton(
                    text = if (isResolving) "Resolving..." else "Resolve",
                    modifier = Modifier.weight(1f),
                    onClick = onResolve
                )
            }
        }
    }
}

@Composable
private fun OrganizerSection(
    organizers: List<AdminOrganizer>,
    onVerify: (String, Boolean) -> Unit,
    onFreeze: (String, Boolean) -> Unit,
    onRequestReKyc: (String) -> Unit,
    addActivity: (String, Color) -> Unit,
) {
    val amOptions = remember { listOf("Tari", "Rudo", "Kuda", "Amina") }
    val amAssignments = remember { mutableStateMapOf<String, String>() }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(title = "Organizers", action = null)
        if (organizers.isEmpty()) {
            GlowCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "No organizers found.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(14.dp)
                )
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                organizers.forEach { org ->
                    OrganizerCard(
                        organizer = org,
                        onVerify = onVerify,
                        onFreeze = onFreeze,
                        onRequestReKyc = onRequestReKyc,
                        addActivity = addActivity,
                        amOptions = amOptions,
                        amAssignments = amAssignments
                    )
                }
            }
        }
    }
}

@Composable
private fun OrganizerCard(
    organizer: AdminOrganizer,
    onVerify: (String, Boolean) -> Unit,
    onFreeze: (String, Boolean) -> Unit,
    onRequestReKyc: (String) -> Unit,
    addActivity: (String, Color) -> Unit,
    amOptions: List<String>,
    amAssignments: MutableMap<String, String>,
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(targetValue = if (pressed) 0.98f else 1f, animationSpec = tween(180), label = "orgCardScale")
    val trustColor = when {
        organizer.trustScore >= 80 -> MaterialTheme.colorScheme.primary
        organizer.trustScore >= 60 -> Color(0xFFFFC94A)
        else -> Color(0xFFFF6B6B)
    }
    val docChecklist = listOf(
        "KYC document" to true,
        "Business cert" to (organizer.trustScore >= 70),
        "Banking verified" to !organizer.frozen,
        "Pricing rationale" to (organizer.trustScore >= 60),
    )
    var showDocs by remember { mutableStateOf(false) }
    var amExpanded by remember { mutableStateOf(false) }
    val assigned = amAssignments[organizer.id]
    GlowCard(
        modifier = Modifier
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .pressAnimated()
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(organizer.name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                Pill(text = organizer.kycStatus, modifier = Modifier)
                Pill(text = "Strikes: ${organizer.strikes}", modifier = Modifier)
                Spacer(Modifier.weight(1f))
                if (organizer.frozen) {
                    NeonSelectablePill(text = "Frozen", selected = true, onClick = {})
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Trust score", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                AnimatedProgressBar(progress = organizer.trustScore / 100f, modifier = Modifier.fillMaxWidth())
            }
            Text(organizer.notes, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Account manager", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface)
                Spacer(Modifier.weight(1f))
                Box {
                    NeonTextButton(text = assigned ?: "Assign", onClick = { amExpanded = true })
                    DropdownMenu(expanded = amExpanded, onDismissRequest = { amExpanded = false }) {
                        amOptions.forEach { am ->
                            DropdownMenuItem(
                                text = { Text(am) },
                                onClick = {
                                    amAssignments[organizer.id] = am
                                    amExpanded = false
                                    addActivity("Assigned $am to ${organizer.name}", trustColor)
                                }
                            )
                        }
                    }
                }
            }
            NeonTextButton(
                text = if (showDocs) "Hide docs & checklist" else "Show docs & checklist",
                onClick = { showDocs = !showDocs }
            )
            AnimatedVisibility(visible = showDocs) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Docs & checklist", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                    docChecklist.forEach { (label, done) ->
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(
                                imageVector = if (done) Icons.Outlined.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                                contentDescription = null,
                                tint = if (done) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                val isVerified = organizer.kycStatus == "Verified"
                PrimaryButton(
                    text = if (isVerified) "Mark pending" else "Verify",
                    modifier = Modifier.weight(1f)
                ) {
                    onVerify(organizer.id, !isVerified)
                }
                val isFrozen = organizer.frozen
                GhostButton(
                    text = if (isFrozen) "Unfreeze" else "Freeze",
                    modifier = Modifier.weight(1f)
                ) {
                    onFreeze(organizer.id, !isFrozen)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                NeonTextButton(text = "Request re-KYC", onClick = { onRequestReKyc(organizer.id) })
                NeonTextButton(text = "Log note", onClick = { addActivity("Note on ${organizer.name}", trustColor) })
            }
        }
    }
}

@Composable
private fun CatalogSection(
    flags: List<AdminFeatureFlag>,
    onToggleFlag: (String, Boolean) -> Unit,
    onOpenModeration: () -> Unit,
    onOpenApplications: () -> Unit,
    addActivity: (String, Color) -> Unit,
) {
    val flagAccent = MaterialTheme.colorScheme.secondary
    var scheduleNote by remember { mutableStateOf("Weekend drop - 9am PST") }
    var scheduleTime by remember { mutableStateOf("Tomorrow 09:00") }
    var isLive by remember { mutableStateOf(false) }
    var promoProgress by remember { mutableStateOf(0.35f) }
    val promoAnim = animateFloatAsState(targetValue = if (isLive) 1f else promoProgress, animationSpec = tween(400), label = "promoProgress")
    val publishAccent = MaterialTheme.colorScheme.primary
    val scheduleAccent = MaterialTheme.colorScheme.secondary
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            title = "Catalog controls",
            action = {
                NeonTextButton(text = "Applications", onClick = onOpenApplications)
            }
        )
        GlowCard(modifier = Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(14.dp)) {
                Text("Publish & scheduling", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                Text("Stub controls for publish/unpublish and schedule. Hook to backend later.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    PrimaryButton(text = if (isLive) "Unpublish now" else "Publish now", modifier = Modifier.weight(1f)) {
                        isLive = !isLive
                        addActivity(if (isLive) "Catalog published" else "Catalog unpublished", publishAccent)
                    }
                    GhostButton(text = "Schedule", modifier = Modifier.weight(1f)) {
                        addActivity("Scheduled catalog: $scheduleTime — $scheduleNote", scheduleAccent)
                        promoProgress = 0.6f
                    }
                }
                AnimatedProgressBar(progress = promoAnim.value, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(
                    value = scheduleTime,
                    onValueChange = { scheduleTime = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("e.g. Tomorrow 09:00") },
                    singleLine = true,
                    shape = goTickyShapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                OutlinedTextField(
                    value = scheduleNote,
                    onValueChange = { scheduleNote = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Rationale / note") },
                    singleLine = false,
                    minLines = 2,
                    shape = goTickyShapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
            }
        }
        SectionHeader(title = "Feature flags", action = null)
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            flags.forEach { flag ->
                FlagRow(flag = flag, onToggle = { enabled ->
                    onToggleFlag(flag.key, enabled)
                    addActivity("Flag ${flag.label} ${if (enabled) "enabled" else "disabled"}", flagAccent)
                })
            }
        }
    }
}

@Composable
private fun BannersSection(
    addActivity: (String, Color) -> Unit,
    heroSlides: List<HeroSlide>,
    featuredSlots: List<String>,
    onToggleFeaturedSlot: (String, Boolean) -> Unit,
    onMoveFeaturedSlot: (String, Int) -> Unit,
    bannerDraft: BannerDraft,
    onBannerDraftChange: (BannerDraft) -> Unit,
    onPickBannerImage: () -> Unit,
    onSaveBanner: () -> Unit,
    onDeleteBanner: (String) -> Unit,
    onResetBanner: () -> Unit,
    bannerSaving: Boolean,
    bannerSavingProgress: Float,
    loadingHeroBanners: Boolean,
    simulatedHeroBannerId: String?,
    onSimulatedHeroBannerChange: (String?) -> Unit,
) {
    val flagAccent = MaterialTheme.colorScheme.secondary
    val badgeOptions = listOf("Hot", "Limited", "Early Bird")
    val badgesBySlide = remember { mutableStateMapOf<String, String>() }
    val featuredSlides = featuredSlots.mapNotNull { id -> heroSlides.firstOrNull { it.id == id } }
    val imagePulse by rememberInfiniteTransition(label = "bannerImagePulse").animateFloat(
        initialValue = 0.9f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(tween(1200, easing = LinearEasing)),
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            title = "Banners",
            action = {
                NeonTextButton(
                    text = "Log banner session",
                    onClick = { addActivity("Reviewing home hero banners", flagAccent) }
                )
            }
        )
        GlowCard(
            modifier = Modifier
                .fillMaxWidth()
                .pressAnimated(scaleDown = 0.98f)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(14.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Banner builder", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                        Text(
                            if (loadingHeroBanners) "Syncing banners from Firestore..." else "Craft and launch hero banners for the public home screen.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (loadingHeroBanners || bannerSaving) {
                        LoadingSpinner(size = 26)
                    }
                }
                if (bannerSaving) {
                    AnimatedProgressBar(
                        progress = bannerSavingProgress,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Pill(text = "ID: ${bannerDraft.id}", modifier = Modifier)
                    Pill(text = "Order ${bannerDraft.order}", modifier = Modifier)
                    if (!bannerDraft.imageUrl.isNullOrBlank()) {
                        Pill(text = "Has image", modifier = Modifier, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f), textColor = MaterialTheme.colorScheme.primary)
                    }
                }
                OutlinedTextField(
                    value = bannerDraft.title,
                    onValueChange = { onBannerDraftChange(bannerDraft.copy(title = it)) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Title") },
                    singleLine = true,
                    shape = goTickyShapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                OutlinedTextField(
                    value = bannerDraft.subtitle,
                    onValueChange = { onBannerDraftChange(bannerDraft.copy(subtitle = it)) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Subtitle") },
                    singleLine = false,
                    minLines = 2,
                    shape = goTickyShapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = bannerDraft.cta,
                        onValueChange = { onBannerDraftChange(bannerDraft.copy(cta = it)) },
                        modifier = Modifier.weight(1f),
                        label = { Text("CTA label") },
                        singleLine = true,
                        shape = goTickyShapes.medium,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    OutlinedTextField(
                        value = bannerDraft.tag,
                        onValueChange = { onBannerDraftChange(bannerDraft.copy(tag = it)) },
                        modifier = Modifier.weight(1f),
                        label = { Text("Tag / chip") },
                        singleLine = true,
                        shape = goTickyShapes.medium,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                }
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = bannerDraft.location,
                        onValueChange = { onBannerDraftChange(bannerDraft.copy(location = it)) },
                        modifier = Modifier.weight(1f),
                        label = { Text("Location") },
                        singleLine = true,
                        shape = goTickyShapes.medium,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    OutlinedTextField(
                        value = bannerDraft.accentHex,
                        onValueChange = {
                            val color = colorFromHex(it)
                            onBannerDraftChange(bannerDraft.copy(accentHex = it, accent = color))
                        },
                        modifier = Modifier.weight(1f),
                        label = { Text("Accent hex") },
                        singleLine = true,
                        shape = goTickyShapes.medium,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                }
                OutlinedTextField(
                    value = bannerDraft.imageHint,
                    onValueChange = { onBannerDraftChange(bannerDraft.copy(imageHint = it)) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Image hint (for prompt/reference)") },
                    singleLine = false,
                    minLines = 2,
                    shape = goTickyShapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                // Lightweight hero-style preview of the current draft
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "Preview",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                val previewSlide = remember(bannerDraft) {
                    HeroSlide(
                        id = bannerDraft.id.ifBlank { "preview" },
                        title = bannerDraft.title.ifBlank { "Untitled banner" },
                        subtitle = bannerDraft.subtitle.ifBlank { "Subtitle and supporting copy will appear here." },
                        cta = bannerDraft.cta.ifBlank { "View details" },
                        tag = bannerDraft.tag.ifBlank { "Hero" },
                        accent = bannerDraft.accent,
                        imageHint = bannerDraft.imageHint.ifBlank { "Hero background art hint" },
                        location = bannerDraft.location.ifBlank { "Location" },
                        heroImageKey = null,
                        imageUrl = bannerDraft.imageUrl,
                        accentHex = bannerDraft.accentHex,
                        order = bannerDraft.order,
                        active = bannerDraft.active,
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {
                    HeroCard(slide = previewSlide, onCta = {})
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Display order", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Slider(
                            value = bannerDraft.order.toFloat(),
                            onValueChange = { onBannerDraftChange(bannerDraft.copy(order = it.roundToInt())) },
                            valueRange = 0f..20f,
                            steps = 19,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Active", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Switch(checked = bannerDraft.active, onCheckedChange = { onBannerDraftChange(bannerDraft.copy(active = it)) })
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    NeonTextButton(
                        text = if (bannerDraft.localImageUri == null) "Pick image" else "Change image",
                        modifier = Modifier
                            .weight(1f)
                            .pressAnimated(scaleDown = 0.97f),
                        onClick = onPickBannerImage
                    )
                    if (bannerDraft.localImageUri != null || bannerDraft.imageUrl != null) {
                        Pill(
                            text = "Image ready",
                            modifier = Modifier.graphicsLayer(scaleX = imagePulse, scaleY = imagePulse),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.16f),
                            textColor = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    PrimaryButton(
                        text = if (bannerSaving) "Saving..." else "Save & simulate",
                        modifier = Modifier
                            .weight(1f)
                            .pressAnimated(scaleDown = 0.97f),
                    ) {
                        if (!bannerSaving) {
                            onSaveBanner()
                            val simulateId = bannerDraft.id.ifBlank { null }
                            if (simulateId != null) {
                                onSimulatedHeroBannerChange(simulateId)
                                val label = bannerDraft.title.ifBlank { "new banner" }
                                addActivity("started hero simulation for $label", bannerDraft.accent)
                            }
                        }
                    }
                    GhostButton(
                        text = if (bannerSaving) "Saving..." else "Save only",
                        modifier = Modifier
                            .weight(1f)
                            .pressAnimated(scaleDown = 0.97f),
                        onClick = { if (!bannerSaving) onSaveBanner() }
                    )
                    NeonTextButton(
                        text = "Reset",
                        modifier = Modifier
                            .weight(1f)
                            .pressAnimated(scaleDown = 0.97f),
                        onClick = { onResetBanner() }
                    )
                }
            }
        }
        GlowCard(
            modifier = Modifier
                .fillMaxWidth()
                .pressAnimated(scaleDown = 0.98f)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(14.dp)) {
                Text("Featured slots", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                if (featuredSlides.isEmpty()) {
                    Text("No featured slots selected.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        featuredSlots.mapNotNull { id -> heroSlides.firstOrNull { it.id == id } }.forEachIndexed { idx, slide ->
                            GlowCard(
                                modifier = Modifier.pressAnimated(scaleDown = 0.97f)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Text("Slot ${idx + 1}", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(slide.title, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                                        Text(slide.subtitle, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    }
                                    Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        IconButton(onClick = { onMoveFeaturedSlot(slide.id, -1) }, enabled = idx > 0) {
                                            Icon(Icons.Outlined.ArrowUpward, contentDescription = "Move up", tint = MaterialTheme.colorScheme.onSurface)
                                        }
                                        IconButton(onClick = { onMoveFeaturedSlot(slide.id, +1) }, enabled = idx < featuredSlots.lastIndex) {
                                            Icon(Icons.Outlined.ArrowDownward, contentDescription = "Move down", tint = MaterialTheme.colorScheme.onSurface)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                Text("All hero slides", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    heroSlides.forEach { slide ->
                        val isFeatured = featuredSlots.contains(slide.id)
                        val isSimulated = simulatedHeroBannerId == slide.id
                        val currentBadge = badgesBySlide[slide.id]
                        val simulationAccent = MaterialTheme.colorScheme.primary
                        GlowCard(
                            modifier = Modifier.pressAnimated(scaleDown = 0.97f)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Pill(text = if (isFeatured) "Featured" else "Available", modifier = Modifier)
                                    if (isSimulated) {
                                        Pill(
                                            text = "Simulated in hero",
                                            modifier = Modifier,
                                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.22f),
                                            textColor = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    Text(slide.title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                                }
                                Text(slide.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2, overflow = TextOverflow.Ellipsis)
                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                                    PrimaryButton(
                                        text = if (isFeatured) "Remove from featured" else "Add to featured",
                                        modifier = Modifier
                                            .weight(1f)
                                            .pressAnimated(scaleDown = 0.96f),
                                        onClick = { onToggleFeaturedSlot(slide.id, !isFeatured) }
                                    )
                                    GhostButton(
                                        text = if (isSimulated) "Stop simulate" else "Simulate in hero",
                                        modifier = Modifier
                                            .weight(1f)
                                            .pressAnimated(scaleDown = 0.96f),
                                    ) {
                                        onSimulatedHeroBannerChange(if (isSimulated) null else slide.id)
                                        val verb = if (isSimulated) "ended" else "started"
                                        addActivity("$verb hero simulation for ${slide.title}", simulationAccent)
                                    }
                                    if (isFeatured) {
                                        GhostButton(
                                            text = "Move up",
                                            modifier = Modifier
                                                .weight(1f)
                                                .pressAnimated(scaleDown = 0.96f)
                                        ) { onMoveFeaturedSlot(slide.id, -1) }
                                        GhostButton(
                                            text = "Move down",
                                            modifier = Modifier
                                                .weight(1f)
                                                .pressAnimated(scaleDown = 0.96f)
                                        ) { onMoveFeaturedSlot(slide.id, +1) }
                                    }
                                }
                                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    badgeOptions.forEach { badge ->
                                        val selected = currentBadge == badge
                                        val badgeColor = when (badge) {
                                            "Hot" -> Color(0xFFFF6B6B)
                                            "Limited" -> Color(0xFFFFC85C)
                                            "Early Bird" -> Color(0xFF4BE8FF)
                                            else -> flagAccent
                                        }
                                        NeonSelectablePill(
                                            text = badge,
                                            selected = selected,
                                            onClick = {
                                                if (selected) {
                                                    badgesBySlide.remove(slide.id)
                                                    addActivity("Removed badge $badge from ${slide.title}", badgeColor)
                                                } else {
                                                    badgesBySlide[slide.id] = badge
                                                    addActivity("Badge $badge set for ${slide.title}", badgeColor)
                                                }
                                            }
                                        )
                                    }
                                    if (currentBadge != null) {
                                        NeonSelectablePill(text = "Clear badge", selected = false, onClick = {
                                            badgesBySlide.remove(slide.id)
                                            addActivity("Cleared badge for ${slide.title}", flagAccent)
                                        })
                                    }
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
private fun FlagRow(
    flag: AdminFeatureFlag,
    onToggle: (Boolean) -> Unit,
) {
    GlowCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1f)) {
                Text(flag.label, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                Text("Audience: ${flag.audience}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Switch(checked = flag.enabled, onCheckedChange = onToggle)
        }
    }
}

@Composable
private fun SettingsSection(
    flags: List<AdminFeatureFlag>,
    onToggleFlag: (String, Boolean) -> Unit,
    roles: List<AdminRoleEntry>,
    onChangeRole: (String, String) -> Unit,
    auditLog: List<AdminActivity> = emptyList(),
) {
    var expandedRoleId by remember { mutableStateOf<String?>(null) }
    val roleOptions = listOf("Admin", "Organizer", "Support", "Suspended")
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(title = "Role matrix", action = null)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            roles.forEach { entry ->
                GlowCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(entry.name, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                            Pill(text = entry.role, modifier = Modifier)
                            Spacer(Modifier.weight(1f))
                            NeonTextButton(text = "Change role", onClick = { expandedRoleId = entry.id })
                        }
                        Text(entry.email, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                DropdownMenu(expanded = expandedRoleId == entry.id, onDismissRequest = { expandedRoleId = null }) {
                    roleOptions.forEach { role ->
                        DropdownMenuItem(
                            text = { Text(role) },
                            onClick = {
                                onChangeRole(entry.id, role)
                                expandedRoleId = null
                            }
                        )
                    }
                }
            }
        }

        SectionHeader(title = "Feature flags", action = null)
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            flags.forEach { flag ->
                FlagRow(flag = flag, onToggle = { enabled ->
                    onToggleFlag(flag.key, enabled)
                })
            }
        }

        GlowCard {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Audit log (sample)", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                if (auditLog.isEmpty()) {
                    Text("View detailed audit in future backend hookup. Current feed is visible in Dashboard.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                } else {
                    auditLog.takeLast(6).reversed().forEach { item ->
                        Text(item.text, style = MaterialTheme.typography.bodySmall, color = item.accent)
                    }
                }
            }
        }
    }
}

@Composable
private fun AdminKpiCard(kpi: AdminKpi) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(targetValue = if (pressed) 0.97f else 1f, animationSpec = tween(180), label = "adminKpiScale")
    GlowCard(
        modifier = Modifier
            .width(180.dp)
            .pressAnimated()
    ) {
        Column(
            modifier = Modifier
                .graphicsLayer(scaleX = scale, scaleY = scale)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = kpi.title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = kpi.value,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                color = kpi.accent
            )
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(kpi.accent)
                )
                Text(
                    text = kpi.delta,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun AdminAttentionCard(item: AdminAttention) {
    val severityColor = when (item.severity) {
        "High" -> Color(0xFFFF6B6B)
        "Medium" -> Color(0xFFFFC94A)
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    GlowCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(severityColor)
            )
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(Modifier.weight(1f))
            NeonTextButton(text = "Review", onClick = { /* TODO */ })
        }
    }
}

@Composable
private fun AdminActivityRow(item: AdminActivity) {
    GlowCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(item.accent)
            )
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = item.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = item.time,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun HomeScreen(
    userProfile: UserProfile,
    isGuest: Boolean,
    onOpenAlerts: () -> Unit,
    onEventSelected: (org.example.project.data.EventItem) -> Unit,
    heroSlides: List<HeroSlide>,
    simulatedHeroBannerId: String?,
    recommendations: List<Recommendation>,
    adminApplications: List<AdminApplication>,
    onOpenMap: () -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    forceOpenSearchDialog: Boolean,
    onConsumeForceOpenSearchDialog: () -> Unit,
    onSearchExecuted: (String) -> Unit,
    favoriteEvents: List<String>,
    onToggleFavorite: (String) -> Unit,
) {
    val filters = remember { mutableStateListOf<String>() }
    var heroDetail by remember { mutableStateOf<HeroSlide?>(null) }
    var showNewsList by remember { mutableStateOf(false) }
    var newsDetail by remember { mutableStateOf<EntertainmentNewsItem?>(null) }
    var showLocationDialog by remember { mutableStateOf(false) }
    var showDateDialog by remember { mutableStateOf(false) }
    var showQueryDialog by remember { mutableStateOf(false) }
    var activeFilterDialog by remember { mutableStateOf<String?>(null) }
    var showHeatPriceDialog by remember { mutableStateOf(false) }
    var showHeatListDialog by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }
    var showComingSoonDialog by remember { mutableStateOf(false) }
    var showForYouPersonalize by remember { mutableStateOf(false) }
    var selectedMonth by remember { mutableStateOf<String?>(null) }
    var showDiscoverDialog by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(IconCategory.Discover) }
    val scrollState = rememberScrollState()
    val publicEvents = sampleEvents.filter { isEventPublic(it.id, adminApplications) }

    LaunchedEffect(forceOpenSearchDialog) {
        if (forceOpenSearchDialog) {
            showQueryDialog = true
            onConsumeForceOpenSearchDialog()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GoTickyGradients.CardGlow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
        GlowCard(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TopBar(
                title = "GoTicky Live",
                onBack = null,
                actions = {
                    val greetingText = remember { currentGreeting() }
                    var greetVisible by remember { mutableStateOf(false) }
                    val greetAlpha by animateFloatAsState(
                        targetValue = if (greetVisible) 1f else 0f,
                        animationSpec = tween(durationMillis = 480, easing = EaseOutBack),
                        label = "homeGreetingAlpha"
                    )
                    LaunchedEffect(Unit) { greetVisible = true }
                    val profilePhotoPainter = profilePainter(userProfile)
                    val firstName = remember(userProfile.fullName, isGuest) {
                        if (isGuest) "Guest" else userProfile.fullName.trim().substringBefore(" ").ifBlank { "Guest" }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.graphicsLayer(alpha = greetAlpha),
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = greetingText,
                                style = MaterialTheme.typography.bodyMedium,
                                color = currentGreetingColor()
                            )
                            Text(
                                text = firstName,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.95f)
                            )
                        }
                        ProfileAvatar(
                            initials = "TG",
                            onClick = onOpenAlerts,
                            photoPainter = profilePhotoPainter
                        )
                    }
                },
                backgroundBrush = null,
                backgroundColor = Color.Transparent,
                contentPadding = PaddingValues(start = 12.dp, top = 1.dp, end = 1.dp, bottom = 1.dp),
                titleContent = {
                    val liveSwingTransition = rememberInfiniteTransition(label = "liveSwing")
                    val swingAngle by liveSwingTransition.animateFloat(
                        initialValue = -18f,
                        targetValue = 18f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 2200, easing = LinearEasing),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "liveSwingAngle"
                    )

                    Box(
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Text(
                            text = "GoTicky",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                shadow = Shadow(
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.55f),
                                    offset = Offset(0f, 1.2f),
                                    blurRadius = 4f
                                )
                            ),
                            color = MaterialTheme.colorScheme.primary
                        )

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .offset(x = 15.dp, y = 22.dp)
                                .graphicsLayer(
                                    rotationX = 12f,
                                    rotationY = -10f,
                                    rotationZ = swingAngle,
                                    transformOrigin = TransformOrigin(
                                        pivotFractionX = 0.5f,
                                        pivotFractionY = 0f
                                    )
                                ),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(1.5.dp)
                                    .height(6.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f)
                                    )
                            )

                            Box(
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFff4b5c))
                                    .padding(horizontal = 8.dp, vertical = 2.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Live",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        shadow = Shadow(
                                            color = Color(0xFF8A0015).copy(alpha = 0.9f),
                                            offset = Offset(0f, 3.5f),
                                            blurRadius = 11f
                                        )
                                    ),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            )
        }
        HeroCarousel(
            slides = heroSlides,
            onCta = { slide ->
                heroDetail = slide
                Analytics.log(
                    AnalyticsEvent(
                        name = "hero_cta",
                        params = mapOf("slide_id" to slide.id)
                    )
                )
            },
            simulatedHeroBannerId = simulatedHeroBannerId,
        )
        QuickSearchBar(
            query = searchQuery,
            onQueryChange = { onSearchQueryChange(it) },
            filters = filters,
            onLocationClick = { showLocationDialog = true },
            onDateClick = { showDateDialog = true },
            onQueryFieldClick = {
                showQueryDialog = true
            },
            onFilterClick = { label -> activeFilterDialog = label },
            isDialogOpen = showQueryDialog
        )
        CategoryRow(
            initialCategory = selectedCategory,
            onCategorySelected = { category ->
                selectedCategory = category
                when (category) {
                    IconCategory.Discover -> showDiscoverDialog = true
                    IconCategory.Map -> onOpenMap()
                    IconCategory.Ticket -> publicEvents.firstOrNull()?.let { onEventSelected(it) }
                    IconCategory.Calendar -> showDateDialog = true
                    IconCategory.Alerts -> onOpenAlerts()
                    else -> {}
                }
            }
        )
        EntertainmentNewsSection(
            items = sampleEntertainmentNews,
            onViewAll = { showNewsList = true },
            onReadMore = { newsDetail = it }
        )
        SectionHeader(
            title = "Tonight's heat",
            action = { NeonTextButton(text = "See all", onClick = { showHeatListDialog = true }) }
        )
        HighlightCard(
            onOpenDetails = {
                val event = publicEvents.firstOrNull()
                event?.let { onEventSelected(it) }
            },
            onSelectSeats = {
                showComingSoonDialog = true
            },
            onPriceAlerts = { showHeatPriceDialog = true }
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    snackbarMessage = "Coming soon: VIP drops will unlock extra perks."
                }
        ) {
            NeonBanner(
                title = "VIP Drop",
                subtitle = "Exclusive pre-sale seats with transparent fees.",
                modifier = Modifier
            )
        }
        SectionHeader(
            title = "For you",
            action = { NeonTextButton(text = "Personalize", onClick = { showForYouPersonalize = true }) }
        )
        ForYouRecommendationsRow(
            recommendations = recommendations,
            favoriteEvents = favoriteEvents,
            onToggleFavorite = onToggleFavorite,
            onOpen = { rec ->
                Analytics.log(
                    AnalyticsEvent(
                        name = "recommendation_tap",
                        params = mapOf(
                            "rec_id" to rec.id,
                            "event_id" to rec.eventId,
                            "source" to "home_for_you"
                        )
                    )
                )

                val event = publicEvents.firstOrNull { it.id == rec.eventId }
                event?.let { onEventSelected(it) }
            }
        )
        MapPreview(onOpenMap = onOpenMap)
        SectionHeader(
            title = "Popular near you",
            action = { NeonTextButton(text = "See all", onClick = { /* TODO */ }) }
        )
        val nearbyByEventId = sampleNearbyEvents.associateBy { it.event.id }
        val popularNearby = publicEvents.mapNotNull { event ->
            val nearby = nearbyByEventId[event.id] ?: return@mapNotNull null

            val matchesQuery = searchQuery.isBlank() ||
                event.title.contains(searchQuery, ignoreCase = true) ||
                event.city.contains(searchQuery, ignoreCase = true)

            val matchesFilter = if (filters.isEmpty()) {
                true
            } else {
                filters.any { pill ->
                    when (pill) {
                        "Concerts" -> event.category == IconCategory.Discover ||
                            (event.tag?.contains("EDM", ignoreCase = true) == true)
                        "Sports" -> event.category == IconCategory.Calendar ||
                            (event.tag?.contains("Basketball", ignoreCase = true) == true)
                        "Family" -> event.badge?.contains("Family", ignoreCase = true) == true ||
                            event.category == IconCategory.Profile
                        else -> true
                    }
                }
            }

            if (matchesQuery && matchesFilter) nearby else null
        }

        PopularNearbySwingDeck(
            nearby = popularNearby,
            favoriteEvents = favoriteEvents,
            onToggleFavorite = onToggleFavorite,
            onOpen = { event -> onEventSelected(event) }
        )

        SectionHeader("Progress preview", action = null)
        LoadingRow(Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(96.dp))
    }

        snackbarMessage?.let { message ->
            GoTickySnackbar(
                message = message,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp, vertical = 90.dp),
                onDismiss = { snackbarMessage = null }
            )
        }
    }

    if (showDiscoverDialog) {
        val shimmer by rememberInfiniteTransition(label = "discoverShimmer").animateFloat(
            initialValue = -220f,
            targetValue = 420f,
            animationSpec = infiniteRepeatable(tween(1600, easing = LinearEasing)),
            label = "discoverShimmerAnim"
        )
        Dialog(onDismissRequest = { showDiscoverDialog = false }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                GlowCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .pressAnimated(scaleDown = 0.98f)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.surface.copy(alpha = 0.94f),
                                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f)
                                    )
                                )
                            )
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text("Discover spotlight", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                                Text("Immersive picks happening now", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            NeonTextButton(
                                text = "Done",
                                onClick = { showDiscoverDialog = false }
                            )
                        }
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(publicEvents) { event ->
                                val tint = IconCategoryColors[event.category] ?: MaterialTheme.colorScheme.primary
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .drawBehind {
                                            val glowRadius = 26.dp.toPx()
                                            drawRoundRect(
                                                brush = Brush.radialGradient(
                                                    colors = listOf(tint.copy(alpha = 0.14f), Color.Transparent),
                                                    center = center,
                                                    radius = size.maxDimension * 0.85f
                                                ),
                                                cornerRadius = CornerRadius(glowRadius, glowRadius)
                                            )
                                            drawRoundRect(
                                                brush = Brush.linearGradient(
                                                    colors = listOf(
                                                        tint.copy(alpha = 0.28f),
                                                        tint.copy(alpha = 0.08f)
                                                    ),
                                                    start = Offset(shimmer, 0f),
                                                    end = Offset(shimmer + 260f, size.height)
                                                ),
                                                cornerRadius = CornerRadius(glowRadius, glowRadius)
                                            )
                                        }
                                        .clip(goTickyShapes.large)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(goTickyShapes.large)
                                            .pressAnimated()
                                            .clickable {
                                                onEventSelected(event)
                                                showDiscoverDialog = false
                                            }
                                    ) {
                                        val photoRes = event.imagePath?.let { key -> Res.allDrawableResources[key] }
                                        if (photoRes != null) {
                                            Image(
                                                painter = painterResource(photoRes),
                                                contentDescription = null,
                                                modifier = Modifier.matchParentSize(),
                                                contentScale = ContentScale.Crop
                                            )
                                        } else {
                                            Box(
                                                modifier = Modifier
                                                    .matchParentSize()
                                                    .background(
                                                        Brush.linearGradient(
                                                            colors = listOf(
                                                                tint.copy(alpha = 0.24f),
                                                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f)
                                                            )
                                                        )
                                                    )
                                            )
                                        }

                                        Box(
                                            modifier = Modifier
                                                .matchParentSize()
                                                .background(
                                                    Brush.verticalGradient(
                                                        colors = listOf(
                                                            Color.Black.copy(alpha = 0.25f),
                                                            Color.Black.copy(alpha = 0.88f)
                                                        )
                                                    )
                                                )
                                                .drawBehind { drawRect(GoTickyTextures.GrainTint) }
                                        )

                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .border(
                                                    width = 1.dp,
                                                    brush = Brush.linearGradient(
                                                        colors = listOf(
                                                            tint.copy(alpha = 0.8f),
                                                            tint.copy(alpha = 0.35f)
                                                        )
                                                    ),
                                                    shape = goTickyShapes.large
                                                )
                                                .padding(14.dp),
                                            verticalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(46.dp)
                                                        .clip(CircleShape)
                                                        .background(Color.Black.copy(alpha = 0.35f)),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Outlined.Explore,
                                                        contentDescription = null,
                                                        tint = tint
                                                    )
                                                }
                                                Column(
                                                    modifier = Modifier.weight(1f),
                                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                                ) {
                                                    Text(
                                                        event.title,
                                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                                        color = Color(0xFFFDFDFE),
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                    Text(
                                                        event.city,
                                                        style = MaterialTheme.typography.labelMedium,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                                    )
                                                }
                                            }
                                            // Center-right "From $X" price pill to reduce squashing next to the title.
                                            Box(modifier = Modifier.fillMaxWidth()) {
                                                Pill(
                                                    text = event.priceFrom,
                                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f),
                                                    textColor = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.align(Alignment.CenterEnd)
                                                )
                                            }
                                            // Bottom row: time/date on the left, Open action on the right.
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Pill(
                                                    text = event.dateLabel,
                                                    color = tint.copy(alpha = 0.22f),
                                                    textColor = tint
                                                )
                                                NeonTextButton(
                                                    text = "Open",
                                                    onClick = {
                                                        onEventSelected(event)
                                                        showDiscoverDialog = false
                                                    }
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
        }
    }

    if (showNewsList) {
        AlertDialog(
            onDismissRequest = { showNewsList = false },
            title = { Text("Entertainment News") },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(sampleEntertainmentNews) { item ->
                        GlowCard {
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(item.title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                                Text(item.summary, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                                    Text("${item.minutesAgo} min ago • ${item.source}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    NeonTextButton(text = "Read more", onClick = { newsDetail = item })
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                NeonTextButton(text = "Close", onClick = { showNewsList = false })
            }
        )
    }

    newsDetail?.let { detail ->
        AlertDialog(
            onDismissRequest = { newsDetail = null },
            title = { Text(detail.title) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(detail.summary, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                    Text("${detail.minutesAgo} min ago • ${detail.source}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Tag: ${detail.tag}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    GhostButton(text = "Close") { newsDetail = null }
                    PrimaryButton(text = "Continue") { newsDetail = null }
                }
            }
        )
    }

    heroDetail?.let { slide ->
        AlertDialog(
            onDismissRequest = { heroDetail = null },
            title = { Text(slide.title) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(slide.subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                    Text(slide.location, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Imagery hint: ${slide.imageHint}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    GhostButton(text = "Close") { heroDetail = null }
                    PrimaryButton(text = slide.cta) { heroDetail = null }
                }
            }
        )
    }

    if (showForYouPersonalize) {
        var genres by remember { mutableStateOf(setOf("Concerts", "Sports", "Comedy", "Family")) }
        var city by remember { mutableStateOf("Harare") }
        AlertDialog(
            onDismissRequest = { showForYouPersonalize = false },
            title = { Text("Tune For You") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "Tell GoTicky what you’re into to refine this row.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Concerts", "Sports", "Comedy", "Family").forEach { tag ->
                            val selected = genres.contains(tag)
                            NeonSelectablePill(
                                text = tag,
                                selected = selected,
                                onClick = {
                                    genres = if (selected) genres - tag else genres + tag
                                }
                            )
                        }
                    }
                    Text("Home city: $city", style = MaterialTheme.typography.bodySmall)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Harare", "Bulawayo", "Gaborone").forEach { option ->
                            val selected = city == option
                            NeonSelectablePill(
                                text = option,
                                selected = selected,
                                onClick = { city = option },
                                selectedContainerColor = MaterialTheme.colorScheme.secondary,
                                selectedContentColor = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    GhostButton(text = "Cancel") { showForYouPersonalize = false }
                    PrimaryButton(text = "Save") {
                        Analytics.log(
                            AnalyticsEvent(
                                name = "for_you_personalize", 
                                params = mapOf(
                                    "genres" to genres.joinToString(","),
                                    "city" to city
                                )
                            )
                        )
                        showForYouPersonalize = false
                    }
                }
            }
        )
    }

    if (showLocationDialog) {
        AlertDialog(
            onDismissRequest = { showLocationDialog = false },
            title = { Text("Pick location or venue") },
            text = {
                var visible by remember { mutableStateOf(false) }
                val scale by animateFloatAsState(
                    targetValue = if (visible) 1f else 0.9f,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
                    label = "mapDialogScale"
                )
                LaunchedEffect(Unit) { visible = true }

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    GlowCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(190.dp)
                            .graphicsLayer(scaleX = scale, scaleY = scale)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(GoTickyGradients.CardGlow)
                                .drawBehind { drawRect(GoTickyTextures.GrainTint) },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(
                                    text = "Map preview • Zimbabwe",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                AnimatedProgressBar(progress = 0.4f, modifier = Modifier.fillMaxWidth(0.86f))
                                Text(
                                    text = "Tap pins in future versions to jump straight into events.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                    Text(
                        text = "Events in your country",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.heightIn(max = 300.dp)
                    ) {
                        items(publicEvents) { event ->
                            GlowCard {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(event.title, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                                    Text("${event.city} • ${event.dateLabel}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                NeonTextButton(text = "Close", onClick = { showLocationDialog = false })
            }
        )
    }

    if (showDateDialog) {
        AlertDialog(
            onDismissRequest = { showDateDialog = false },
            title = { Text("Pick a date") },
            text = {
                var visible by remember { mutableStateOf(false) }
                val scale by animateFloatAsState(
                    targetValue = if (visible) 1f else 0.94f,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
                    label = "calendarDialogScale"
                )
                LaunchedEffect(Unit) { visible = true }

                val months = listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
                val activeMonthIndex = remember(selectedMonth) {
                    val initial = selectedMonth ?: months.first()
                    months.indexOf(initial).coerceAtLeast(0)
                }
                var monthIndex by remember { mutableStateOf(activeMonthIndex) }
                val activeMonth = months[monthIndex]
                val daysInMonth = 30

                val monthEvents = publicEvents.filter { it.month.equals(activeMonth, ignoreCase = true) }

                // Map sample events to pinned days in the month (purely for demo since SampleEvents has no concrete dates)
                val pinnedByDay = remember(activeMonth) {
                    if (monthEvents.isEmpty()) emptyMap() else {
                        val baseDays = listOf(3, 7, 12, 18, 22, 26)
                        monthEvents.mapIndexed { index, event ->
                            val day = baseDays[index % baseDays.size].coerceIn(1, daysInMonth)
                            day to event
                        }.groupBy({ it.first }, { it.second })
                    }
                }

                var selectedDay by remember(activeMonth) {
                    mutableStateOf(pinnedByDay.keys.minOrNull())
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale)
                ) {
                    // Month header with previous/next controls
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        NeonTextButton(
                            text = "<",
                            onClick = {
                                monthIndex = if (monthIndex == 0) months.lastIndex else monthIndex - 1
                                selectedMonth = months[monthIndex]
                            }
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = activeMonth,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Tap a date to see pinned events",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        NeonTextButton(
                            text = ">",
                            onClick = {
                                monthIndex = if (monthIndex == months.lastIndex) 0 else monthIndex + 1
                                selectedMonth = months[monthIndex]
                            }
                        )
                    }

                    // Weekday labels
                    val weekDays = listOf("M", "T", "W", "T", "F", "S", "S")
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        weekDays.forEach { label ->
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // Calendar grid (simple 30-day month, aligned from Monday)
                    val cellModifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        var day = 1
                        repeat(5) { // up to 5 weeks for 30 days
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                repeat(7) {
                                    if (day <= daysInMonth) {
                                        val thisDay = day
                                        val hasEvents = pinnedByDay.containsKey(thisDay)
                                        val isSelected = selectedDay == thisDay
                                        val bgColor = when {
                                            isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.22f)
                                            hasEvents -> MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                                            else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
                                        }
                                        val border = if (isSelected) GoTickyGradients.EdgeHalo else GoTickyGradients.CardGlow

                                        Column(
                                            modifier = cellModifier
                                                .clip(goTickyShapes.small)
                                                .background(bgColor)
                                                .border(1.dp, border, goTickyShapes.small)
                                                .pressAnimated(scaleDown = 0.9f)
                                                .clickable {
                                                    selectedDay = thisDay
                                                },
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = thisDay.toString(),
                                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            if (hasEvents) {
                                                Box(
                                                    modifier = Modifier
                                                        .padding(top = 4.dp)
                                                        .size(5.dp)
                                                        .clip(CircleShape)
                                                        .background(MaterialTheme.colorScheme.primary)
                                                )
                                            }
                                        }
                                        day++
                                    } else {
                                        Spacer(modifier = cellModifier)
                                    }
                                }
                            }
                        }
                    }

                    // Events for selected day
                    val eventsForDay = selectedDay?.let { pinnedByDay[it] }.orEmpty()
                    if (eventsForDay.isEmpty()) {
                        Text(
                            text = "No pinned events for this date in the sample data.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Pinned events on day ${selectedDay}",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            eventsForDay.forEach { event ->
                                GlowCard {
                                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text(
                                            event.title,
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            "${event.city} – ${event.dateLabel}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                NeonTextButton(text = "Close", onClick = { showDateDialog = false })
            }
        )
    }

    if (showQueryDialog) {
        AlertDialog(
            onDismissRequest = { showQueryDialog = false },
            title = { Text("Search events") },
            text = {
                var visible by remember { mutableStateOf(false) }
                val scale by animateFloatAsState(
                    targetValue = if (visible) 1f else 0.9f,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
                    label = "queryDialogScale"
                )
                val dialogFocusRequester = remember { FocusRequester() }
                LaunchedEffect(showQueryDialog) {
                    if (showQueryDialog) {
                        visible = true
                        dialogFocusRequester.requestFocus()
                    }
                }

                val filtered = publicEvents.filter {
                    searchQuery.isNotBlank() && (it.title.contains(searchQuery, ignoreCase = true) || it.city.contains(searchQuery, true))
                }

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = if (searchQuery.isBlank()) "Type to search across events" else "Searching for \"$searchQuery\"",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { onSearchQueryChange(it) },
                        leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = "Search") },
                        placeholder = { Text("Search events, artists, venues") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer(scaleX = scale, scaleY = scale)
                            .focusRequester(dialogFocusRequester),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = { if (searchQuery.isNotBlank()) showQueryDialog = true }),
                        singleLine = true,
                        shape = goTickyShapes.medium,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    when {
                        searchQuery.isBlank() -> {
                            Text(
                                "Start typing to see live results in this dialog.",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        filtered.isEmpty() -> {
                            Text(
                                "No events match \"$searchQuery\" yet. Try another artist, venue, or city.",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        else -> {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.heightIn(max = 280.dp)
                            ) {
                                items(filtered) { event ->
                                    val (earlyLabel, earlyHighlight) = buildEarlyBirdBadgeForEvent(event, adminApplications)
                                    EventCard(
                                        item = event,
                                        modifier = Modifier.pressAnimated(scaleDown = 0.96f),
                                        isFavorite = favoriteEvents.contains(event.id),
                                        onToggleFavorite = { onToggleFavorite(event.id) },
                                        onClick = {
                                            onEventSelected(event)
                                            showQueryDialog = false
                                        },
                                        earlyBirdLabel = earlyLabel,
                                        earlyBirdHighlight = earlyHighlight
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                NeonTextButton(
                    text = "Done",
                    onClick = {
                        if (searchQuery.isNotBlank()) {
                            Analytics.log(
                                AnalyticsEvent(
                                    name = "search",
                                    params = mapOf(
                                        "query" to searchQuery,
                                        "filters" to filters.joinToString(",")
                                    )
                                )
                            )
                            onSearchExecuted(searchQuery)
                        }
                        showQueryDialog = false
                    }
                )
            }
        )
    }

    activeFilterDialog?.let { filterLabel ->
        AlertDialog(
            onDismissRequest = { activeFilterDialog = null },
            title = { Text("$filterLabel picks") },
            text = {
                var visible by remember { mutableStateOf(false) }
                val scale by animateFloatAsState(
                    targetValue = if (visible) 1f else 0.9f,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
                    label = "filterDialogScale"
                )
                LaunchedEffect(Unit) { visible = true }

                val filteredByCategory = when (filterLabel) {
                    "Concerts" -> publicEvents.filter {
                        it.category == IconCategory.Discover ||
                            (it.tag?.contains("EDM", ignoreCase = true) == true)
                    }
                    "Sports" -> publicEvents.filter {
                        it.category == IconCategory.Calendar ||
                            (it.tag?.contains("Basketball", ignoreCase = true) == true)
                    }
                    "Family" -> publicEvents.filter {
                        it.badge?.contains("Family", ignoreCase = true) == true ||
                            it.category == IconCategory.Profile
                    }
                    else -> publicEvents
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .graphicsLayer(scaleX = scale, scaleY = scale)
                ) {
                    Text(
                        text = "Curated $filterLabel vibes from our sample events.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (filteredByCategory.isNotEmpty()) {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.heightIn(max = 220.dp)
                        ) {
                            items(filteredByCategory) { event ->
                                GlowCard(
                                    modifier = Modifier.pressAnimated(scaleDown = 0.95f)
                                ) {
                                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Text(
                                            event.title,
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                                        )
                                        Text(
                                            "${event.city} – ${event.dateLabel}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        Text(
                            text = "No sample events yet for this filter.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            confirmButton = {
                NeonTextButton(text = "Close", onClick = { activeFilterDialog = null })
            }
        )
    }

    if (showHeatPriceDialog) {
        AlertDialog(
            onDismissRequest = { showHeatPriceDialog = false },
            title = { Text("Tonight's heat prices") },
            text = {
                var visible by remember { mutableStateOf(false) }
                val scale by animateFloatAsState(
                    targetValue = if (visible) 1f else 0.9f,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
                    label = "heatPricesScale"
                )
                LaunchedEffect(Unit) { visible = true }

                val tiers = listOf(
                    "Early bird" to "\$10",
                    "General admission" to "\$15",
                    "Floor / close to stage" to "\$25",
                    "VIP lounge" to "\$40",
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .graphicsLayer(scaleX = scale, scaleY = scale)
                ) {
                    Text(
                        text = "Sample tiers for Marquee Night.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.heightIn(max = 280.dp)
                    ) {
                        items(tiers) { (label, price) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .pressAnimated(scaleDown = 0.96f)
                                    .clip(goTickyShapes.medium)
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Text(label, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                                    Text("Sample tier", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                Text(price, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                NeonTextButton(text = "Close", onClick = { showHeatPriceDialog = false })
            }
        )
    }

    if (showHeatListDialog) {
        AlertDialog(
            onDismissRequest = { showHeatListDialog = false },
            title = { Text("Tonight's heat") },
            text = {
                var visible by remember { mutableStateOf(false) }
                val scale by animateFloatAsState(
                    targetValue = if (visible) 1f else 0.9f,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
                    label = "heatListScale"
                )
                LaunchedEffect(Unit) { visible = true }

                val tonightEvents = publicEvents.filter {
                    it.dateLabel.contains("Tonight", ignoreCase = true) || it.badge?.contains("Hot", ignoreCase = true) == true
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale)
                ) {
                    Text(
                        text = "Curated picks for tonight from the demo data.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.heightIn(max = 240.dp)
                    ) {
                        items(tonightEvents) { event ->
                            GlowCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .pressAnimated(scaleDown = 0.96f)
                                    .clickable {
                                        onEventSelected(event)
                                        showHeatListDialog = false
                                    }
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(event.title, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                                    Text("${event.city} – ${event.dateLabel}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(event.priceFrom, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                NeonTextButton(text = "Close", onClick = { showHeatListDialog = false })
            }
        )
    }
    if (showComingSoonDialog) {
        AlertDialog(
            onDismissRequest = { showComingSoonDialog = false },
            title = { Text("Coming soon") },
            text = {
                var visible by remember { mutableStateOf(false) }
                val scale by animateFloatAsState(
                    targetValue = if (visible) 1f else 0.9f,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
                    label = "comingSoonScale"
                )
                LaunchedEffect(Unit) { visible = true }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale)
                ) {
                    Text(
                        text = "Coming soon: deeper seat maps, VIP flows and alerts in this GoTicky demo.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "For now, explore tonight's heat and alerts as interactive previews.",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                NeonTextButton(text = "Nice", onClick = { showComingSoonDialog = false })
            }
        )
    }
}

@Composable
private fun GoTickySnackbar(
    message: String,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
) {
    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 260, easing = LinearEasing),
        label = "snackbarAlpha"
    )
    val offsetY by animateDpAsState(
        targetValue = if (visible) 0.dp else 16.dp,
        animationSpec = tween(durationMillis = 260, easing = EaseOutBack),
        label = "snackbarOffset"
    )

    LaunchedEffect(message) {
        visible = true
        delay(2600)
        visible = false
        delay(260)
        onDismiss()
    }

    Row(
        modifier = modifier
            .offset(y = offsetY)
            .graphicsLayer(alpha = alpha)
            .clip(goTickyShapes.large)
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, GoTickyGradients.EdgeHalo, goTickyShapes.large)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun HeroCarousel(
    slides: List<HeroSlide>,
    onCta: (HeroSlide) -> Unit,
    simulatedHeroBannerId: String? = null,
    modifier: Modifier = Modifier,
) {
    if (slides.isEmpty()) return

    val listState = rememberLazyListState()
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(slides.size) {
        if (slides.size <= 1) return@LaunchedEffect
        var direction = 1
        val lastIndex = slides.lastIndex
        while (true) {
            delay(5200)
            val current = listState.firstVisibleItemIndex
            val candidate = current + direction
            val next = candidate.coerceIn(0, lastIndex)
            listState.animateScrollToItem(next)
            if (next == 0 || next == lastIndex) {
                direction *= -1
            }
        }
    }

    LaunchedEffect(simulatedHeroBannerId, slides.size) {
        val targetId = simulatedHeroBannerId ?: return@LaunchedEffect
        val index = slides.indexOfFirst { it.id == targetId }
        if (index >= 0) {
            listState.animateScrollToItem(index)
        }
    }

    Column(
        modifier = modifier.padding(top = 20.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val cardWidth = maxWidth * 0.9f
            val sidePadding = (maxWidth - cardWidth) / 2f
            LazyRow(
                state = listState,
                contentPadding = PaddingValues(horizontal = sidePadding),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(slides) { index, slide ->
                    val layoutInfo = listState.layoutInfo
                    val visibleInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
                    val viewportWidth = layoutInfo.viewportSize.width
                    val viewportCenter = layoutInfo.viewportStartOffset + viewportWidth / 2

                    var scaleTarget = 1f
                    var rotationYTarget = 0f
                    var translationXTarget = 0f
                    var alphaTarget = 0.85f
                    var z = 0f

                    if (visibleInfo != null && viewportWidth > 0) {
                        val itemCenter = visibleInfo.offset + visibleInfo.size / 2
                        val distance = (itemCenter - viewportCenter).toFloat()
                        val normalized = (distance / (viewportWidth / 2f)).coerceIn(-1f, 1f)
                        val absNorm = abs(normalized)

                        // Keep subtle 3D rotation/translation but avoid zooming the cards
                        scaleTarget = 1f
                        rotationYTarget = -normalized * 18f
                        translationXTarget = -normalized * with(density) { 14.dp.toPx() }
                        alphaTarget = 0.55f + (1f - absNorm) * 0.45f
                        z = 1f - absNorm
                    }

                    val scale by animateFloatAsState(scaleTarget, animationSpec = tween(220), label = "heroScale-$index")
                    val rotationYAnim by animateFloatAsState(rotationYTarget, animationSpec = tween(320), label = "heroRot-$index")
                    val translationXPx by animateFloatAsState(translationXTarget, animationSpec = tween(320), label = "heroTx-$index")
                    val alpha by animateFloatAsState(alphaTarget, animationSpec = tween(240), label = "heroAlpha-$index")

                    val isSimulated = simulatedHeroBannerId == slide.id
                    Box(
                        modifier = Modifier
                            .width(cardWidth)
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                rotationY = rotationYAnim
                                translationX = translationXPx
                                cameraDistance = 18f * density.density
                                this.alpha = alpha
                            }
                            .zIndex(z)
                    ) {
                        HeroCard(slide = slide, onCta = { onCta(slide) }, simulated = isSimulated)
                    }
                }
            }
        }
        HeroPagerIndicator(
            count = slides.size,
            listState = listState,
            modifier = Modifier
                .padding(top = 8.dp)
        )
        if (simulatedHeroBannerId != null && slides.any { it.id == simulatedHeroBannerId }) {
            NeonTextButton(
                text = "Jump to simulated slide",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .pressAnimated(scaleDown = 0.97f),
                onClick = {
                    val targetId = simulatedHeroBannerId
                    if (targetId != null) {
                        val index = slides.indexOfFirst { it.id == targetId }
                        if (index >= 0) {
                            coroutineScope.launch {
                                listState.animateScrollToItem(index)
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun HeroCard(slide: HeroSlide, onCta: () -> Unit, simulated: Boolean = false) {
    val accent = slide.accent
    Box(
        modifier = Modifier
            .height(240.dp)
            .clip(goTickyShapes.extraLarge)
            .background(GoTickyGradients.CardGlow)
            .background(GoTickyGradients.GlassWash)
            .drawBehind { drawRect(GoTickyTextures.GrainTint) }
            .pressAnimated()
    ) {
        // Hero photo layer
        val photoRes = slide.heroImageKey?.let { key -> Res.allDrawableResources[key] }
        val remotePainter = slide.imageUrl?.let { rememberUriPainter(it) }
        when {
            remotePainter != null -> {
                Image(
                    painter = remotePainter,
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )
            }
            photoRes != null -> {
                Image(
                    painter = painterResource(photoRes),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )
            }
            else -> {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    accent.copy(alpha = 0.22f),
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                                )
                            )
                        )
                )
            }
        }

        // Dark overlay + grain to keep text readable
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.25f),
                            Color.Black.copy(alpha = 0.85f)
                        )
                    )
                )
                .graphicsLayer(alpha = 0.9f)
        )
        if (simulated) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color(0xFF4BE8FF).copy(alpha = 0.18f))
                    .border(1.dp, Color(0xFF4BE8FF).copy(alpha = 0.9f), goTickyShapes.extraLarge)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .clip(goTickyShapes.small)
                    .background(Color.Black.copy(alpha = 0.72f))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Simulated",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF4BE8FF)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Pill(
                    text = slide.tag,
                    color = accent.copy(alpha = 0.18f),
                    textColor = accent
                )
                Text(
                    slide.location,
                    style = MaterialTheme.typography.labelMedium.copy(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.65f),
                            offset = Offset(0f, 1.5f),
                            blurRadius = 4f
                        )
                    ),
                    color = Color.White.copy(alpha = 0.88f)
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = slide.title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.8f),
                            offset = Offset(0f, 2.5f),
                            blurRadius = 8f
                        )
                    ),
                    color = Color(0xFFFDFDFE)
                )
                Text(
                    text = slide.subtitle,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.7f),
                            offset = Offset(0f, 1.5f),
                            blurRadius = 6f
                        )
                    ),
                    color = Color.White.copy(alpha = 0.96f)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = slide.imageHint,
                    style = MaterialTheme.typography.labelSmall.copy(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.7f),
                            offset = Offset(0f, 1.5f),
                            blurRadius = 4f
                        )
                    ),
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.weight(1f)
                )
                PrimaryButton(
                    text = slide.cta,
                    onClick = onCta
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
private fun CategoryRow(
    modifier: Modifier = Modifier,
    initialCategory: IconCategory = IconCategory.Discover,
    onCategorySelected: (IconCategory) -> Unit = {},
) {
    val categories = listOf(
        "Discover" to IconCategory.Discover,
        "Calendar" to IconCategory.Calendar,
        "Map" to IconCategory.Map,
        "Tickets" to IconCategory.Ticket,
        "Alerts" to IconCategory.Alerts,
    )
    var selected by rememberSaveable { mutableStateOf(initialCategory) }
    val pulse = rememberInfiniteTransition(label = "categoryPulse").animateFloat(
        initialValue = 0.75f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = EaseOutBack),
            repeatMode = RepeatMode.Reverse
        ),
        label = "categoryPulseAnim"
    )

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { (label, category) ->
            val tint = IconCategoryColors[category] ?: MaterialTheme.colorScheme.primary
            val isSelected = selected == category
            val bgColor by animateColorAsState(
                targetValue = if (isSelected) tint.copy(alpha = 0.18f) else tint.copy(alpha = 0.08f),
                animationSpec = tween(220, easing = LinearEasing),
                label = "categoryBg"
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) tint else tint.copy(alpha = 0.7f),
                animationSpec = tween(180, easing = LinearEasing),
                label = "categoryText"
            )
            val haloAlpha by animateFloatAsState(
                targetValue = if (isSelected) 1f else 0f,
                animationSpec = tween(180, easing = LinearEasing),
                label = "categoryHalo"
            )

            Row(
                modifier = Modifier
                    .pressAnimated(scaleDown = 0.92f)
                    .drawBehind {
                        if (isSelected) {
                            val halo = tint.copy(alpha = 0.20f * haloAlpha)
                            drawRoundRect(
                                brush = Brush.radialGradient(
                                    colors = listOf(halo, Color.Transparent),
                                    center = center,
                                    radius = size.maxDimension * pulse.value
                                ),
                                cornerRadius = CornerRadius(size.height / 1.5f, size.height / 1.5f)
                            )
                        }
                    }
                    .clip(goTickyShapes.medium)
                    .background(bgColor)
                    .then(
                        if (isSelected) {
                            Modifier.border(
                                width = 1.4.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        tint.copy(alpha = 0.9f),
                                        tint.copy(alpha = 0.55f)
                                    )
                                ),
                                shape = goTickyShapes.medium
                            )
                        } else Modifier
                    )
                    .clickable {
                        selected = category
                        onCategorySelected(category)
                    }
                    .padding(horizontal = 14.dp, vertical = 11.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(if (isSelected) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(tint)
                )
                Text(
                    label,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = textColor
                )
            }
        }
    }
}

@Composable
private fun QuickSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    filters: MutableList<String>,
    onLocationClick: () -> Unit,
    onDateClick: () -> Unit,
    onQueryFieldClick: () -> Unit,
    onFilterClick: (String) -> Unit,
    isDialogOpen: Boolean,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val textScale by animateFloatAsState(
        targetValue = if (pressed) 0.985f else 1f,
        animationSpec = tween(durationMillis = 140, easing = EaseOutBack),
        label = "quickSearchFieldScale"
    )
    val focusRequester = remember { FocusRequester() }
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
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchChip(
                label = "Location or venue",
                leadingIcon = { Icon(Icons.Outlined.Place, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                onClick = onLocationClick
            )
            SearchChip(
                label = "Date",
                leadingIcon = { Icon(Icons.Outlined.Event, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                onClick = onDateClick
            )
        }
        val haloAlpha by animateFloatAsState(
            targetValue = if (isDialogOpen) 1f else 0f,
            animationSpec = tween(durationMillis = 220, easing = LinearEasing),
            label = "searchHalo"
        )
        val primaryColor = MaterialTheme.colorScheme.primary
        val borderBrush = Brush.linearGradient(
            colors = listOf(
                primaryColor.copy(alpha = 0.9f),
                primaryColor.copy(alpha = 0.65f)
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer(scaleX = textScale, scaleY = textScale)
                .clip(goTickyShapes.medium)
                .background(MaterialTheme.colorScheme.surface)
                .drawBehind {
                    if (haloAlpha > 0f) {
                        drawRoundRect(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    primaryColor.copy(alpha = 0.14f * haloAlpha),
                                    Color.Transparent
                                ),
                                center = center,
                                radius = size.maxDimension * 0.85f
                            ),
                            cornerRadius = CornerRadius(size.height / 2, size.height / 2)
                        )
                    }
                }
                .border(
                    width = 1.6.dp,
                    brush = if (isDialogOpen) borderBrush else Brush.linearGradient(
                        listOf(MaterialTheme.colorScheme.outline, MaterialTheme.colorScheme.outline)
                    ),
                    shape = goTickyShapes.medium
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    onQueryFieldClick()
                }
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Open search",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = if (query.isNotBlank()) query else "Search events, artists, venues",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (query.isNotBlank()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { onQueryFieldClick() },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = if (isDialogOpen) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Open search dialog"
                    )
                }
            }
        }
        AnimatedProgressBar(progress = 0.32f, modifier = Modifier.fillMaxWidth())
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            FilterPill("Concerts", filters, onTap = onFilterClick)
            FilterPill("Sports", filters, onTap = onFilterClick)
            FilterPill("Family", filters, onTap = onFilterClick)
        }
    }
}

@Composable
private fun HighlightCard(
    onOpenDetails: () -> Unit,
    onSelectSeats: () -> Unit,
    onPriceAlerts: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(10.dp, goTickyShapes.extraLarge)
            .clip(goTickyShapes.extraLarge)
            .background(GoTickyGradients.CardGlow)
            .background(GoTickyGradients.GlassWash)
            .drawBehind { drawRect(GoTickyTextures.GrainTint) }
            .pressAnimated(scaleDown = 0.96f)
            .clickable { onOpenDetails() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(1.5.dp)
                .clip(goTickyShapes.large)
        ) {
            // Fixed key for Tonight's heat hero image
            val photoRes = Res.allDrawableResources["tonights_heat_marquee_night"]

            if (photoRes != null) {
                Image(
                    painter = painterResource(photoRes),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(GoTickyGradients.Cta)
                )
            }

            // Dark overlay for legibility
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.25f),
                                Color.Black.copy(alpha = 0.85f)
                            )
                        )
                    )
                    .drawBehind { drawRect(GoTickyTextures.GrainTint) }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(GoTickyGradients.Cta)
                    )
                    Column {
                        Text(
                            "Marquee Night",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.8f),
                                    offset = Offset(0f, 2f),
                                    blurRadius = 6f
                                )
                            ),
                            color = Color(0xFFFDFDFE)
                        )
                        Text(
                            "Harare – Reps Theatre – Tonight – Limited VIP",
                            style = MaterialTheme.typography.bodySmall.copy(
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.7f),
                                    offset = Offset(0f, 1.5f),
                                    blurRadius = 4f
                                )
                            ),
                            color = Color.White.copy(alpha = 0.96f)
                        )
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Best seats highlighted with neon glow. See transparent fees before checkout.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.7f),
                                offset = Offset(0f, 1.5f),
                                blurRadius = 5f
                            )
                        ),
                        color = Color.White.copy(alpha = 0.97f)
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        PrimaryButton(text = "Select seats", onClick = onSelectSeats)
                        GhostButton(text = "Price alerts", onClick = onPriceAlerts)
                    }
                }
            }
        }
    }
}

@Composable
private fun EntertainmentNewsSection(
    items: List<EntertainmentNewsItem>,
    onViewAll: () -> Unit,
    onReadMore: (EntertainmentNewsItem) -> Unit
) {
    if (items.isEmpty()) return

    val listState = rememberLazyListState()
    val density = LocalDensity.current

    // Slow auto-scroll to mimic a gentle news ticker while keeping it interactive.
    LaunchedEffect(items.size) {
        if (items.size <= 1) return@LaunchedEffect
        while (true) {
            delay(5200)
            val target = (listState.firstVisibleItemIndex + 1).mod(items.size)
            listState.animateScrollToItem(target)
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        SectionHeader(
            title = "Entertainment News",
            action = { NeonTextButton(text = "View all", onClick = { onViewAll() }) }
        )
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(bottom = 16.dp)
        ) {
            val cardWidth = maxWidth * 0.78f
            val sidePadding = (maxWidth - cardWidth) / 2f
            val accentSwatches = items.take(5).map { IconCategoryColors[it.category] ?: MaterialTheme.colorScheme.secondary }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                NewsCollageBackdrop(colors = accentSwatches)
            }
            LazyRow(
                state = listState,
                contentPadding = PaddingValues(horizontal = sidePadding),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(items) { index, item ->
                    val layoutInfo = listState.layoutInfo
                    val visibleInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
                    val viewportWidth = layoutInfo.viewportSize.width
                    val viewportCenter = layoutInfo.viewportStartOffset + viewportWidth / 2

                    var scale = 0.9f
                    var rotationY = 0f
                    var translationXPx = 0f
                    var alpha = 0.8f
                    var z = 0f

                    if (visibleInfo != null && viewportWidth > 0) {
                        val itemCenter = visibleInfo.offset + visibleInfo.size / 2
                        val distance = (itemCenter - viewportCenter).toFloat()
                        val normalized = (distance / (viewportWidth / 2f)).coerceIn(-1f, 1f)
                        val absNorm = abs(normalized)

                        scale = 0.9f + (1f - absNorm) * 0.22f
                        rotationY = -normalized * 22f
                        translationXPx = -normalized * with(density) { 18.dp.toPx() }
                        alpha = 0.5f + (1f - absNorm) * 0.5f
                        z = 1f - absNorm
                    }

                    Box(
                        modifier = Modifier
                            .width(cardWidth)
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                rotationY = rotationY
                                translationX = translationXPx
                                cameraDistance = 18f * density.density
                                this.alpha = alpha
                            }
                            .zIndex(z),
                        contentAlignment = Alignment.Center
                    ) {
                        EntertainmentNewsCard(
                            item = item,
                            isPrimary = z > 0.9f,
                            onReadMore = { onReadMore(item) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EntertainmentNewsCard(
    item: EntertainmentNewsItem,
    isPrimary: Boolean,
    onReadMore: () -> Unit,
) {
    val accent = IconCategoryColors[item.category] ?: MaterialTheme.colorScheme.secondary

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(10.dp, goTickyShapes.extraLarge)
            .clip(goTickyShapes.extraLarge)
            .background(GoTickyGradients.CardGlow)
            .background(GoTickyGradients.GlassWash)
            .drawBehind { drawRect(GoTickyTextures.GrainTint) }
            .height(184.dp)
            .pressAnimated(scaleDown = 0.94f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.5.dp)
                .clip(goTickyShapes.large)
        ) {
            val photoRes = item.imageKey?.let { key -> Res.allDrawableResources[key] }

            if (photoRes != null) {
                Image(
                    painter = painterResource(photoRes),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(GoTickyGradients.CardGlow)
                )
            }

            // Dark overlay + grain to keep text readable over the photo
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.15f),
                                Color.Black.copy(alpha = 0.85f)
                            )
                        )
                    )
                    .drawBehind { drawRect(GoTickyTextures.GrainTint) }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top: event type (tag) left, time right
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(9.dp)
                            .clip(CircleShape)
                            .background(accent)
                    )
                    Text(
                        text = item.tag.uppercase(),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = accent
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "${item.minutesAgo} min ago",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Middle: details from center downward (title + summary)
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = if (isPrimary) 2 else 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = item.summary,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = if (isPrimary) 3 else 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Bottom: location/source left, Read more right
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = item.source,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    NeonTextButton(text = "Read more", onClick = { onReadMore() })
                }
            }
        }
    }
}

@Composable
private fun NewsCollageBackdrop(colors: List<Color>) {
    val strips = if (colors.isNotEmpty()) colors else listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(goTickyShapes.extraLarge)
            .background(GoTickyGradients.CardGlow)
            .graphicsLayer(alpha = 0.32f)
            .drawBehind { drawRect(GoTickyTextures.GrainTint) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            strips.forEachIndexed { idx, color ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(goTickyShapes.large)
                        .background(color.copy(alpha = 0.25f))
                        .graphicsLayer(
                            rotationZ = if (idx % 2 == 0) -2.5f else 2.5f,
                            alpha = 0.85f
                        )
                )
            }
        }
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.22f),
                            Color.Transparent
                        )
                    )
                )
        )
    }
}

@Composable
private fun PopularNearbySwingDeck(
    nearby: List<org.example.project.data.NearbyEvent>,
    favoriteEvents: List<String>,
    onToggleFavorite: (String) -> Unit,
    onOpen: (EventItem) -> Unit,
    modifier: Modifier = Modifier,
    swingIntensity: Float = 1f,
) {
    if (nearby.isEmpty()) {
        GlowCard(
            modifier = modifier.fillMaxWidth()
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "No nearby events match your filters yet.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Try adjusting the search filters to discover more around you.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        return
    }

    var currentIndex by remember { mutableStateOf(0) }
    val lastIndex = nearby.lastIndex
    if (currentIndex > lastIndex) {
        currentIndex = lastIndex.coerceAtLeast(0)
    }

    var dragOffset by remember { mutableStateOf(0f) }
    val animatedDragOffset by animateFloatAsState(
        targetValue = dragOffset,
        animationSpec = tween(durationMillis = GoTickyMotion.Comfort, easing = EaseOutBack),
        label = "popularNearbyDrag"
    )

    val density = LocalDensity.current
    val spreadPx = with(density) { 42.dp.toPx() }
    val maxVisible = 5

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(vertical = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(nearby.size) {
                    detectDragGestures(
                        onDrag = { _, dragAmount ->
                            dragOffset += dragAmount.x
                        },
                        onDragEnd = {
                            val threshold = size.width * 0.18f
                            when {
                                dragOffset <= -threshold && currentIndex < lastIndex -> {
                                    currentIndex += 1
                                }
                                dragOffset >= threshold && currentIndex > 0 -> {
                                    currentIndex -= 1
                                }
                            }
                            dragOffset = 0f
                        },
                        onDragCancel = {
                            dragOffset = 0f
                        }
                    )
                }
        ) {
            nearby.forEachIndexed { index, item ->
                val layerIndex = index - currentIndex
                if (abs(layerIndex) > maxVisible / 2) return@forEachIndexed

                val depth = abs(layerIndex).coerceAtMost(2)
                val baseScale = 1f - 0.06f * depth
                val depthOffsetYPx = with(density) { (depth * 10).dp.toPx() }
                val isCenter = layerIndex == 0

                val slideFactor = 0.9f - depth * 0.18f
                val translationXTarget = layerIndex * spreadPx + animatedDragOffset * slideFactor
                val scaleTarget = if (isCenter) 1.02f else baseScale
                val alphaTarget = if (depth == 0) 1f else 0.55f

                val translationXAnim by animateFloatAsState(
                    targetValue = translationXTarget,
                    animationSpec = tween(durationMillis = GoTickyMotion.Emphasized, easing = EaseOutBack),
                    label = "nearbyTx-$index"
                )
                val scaleAnim by animateFloatAsState(
                    targetValue = scaleTarget,
                    animationSpec = tween(durationMillis = GoTickyMotion.Emphasized, easing = EaseOutBack),
                    label = "nearbyScale-$index"
                )
                val alphaAnim by animateFloatAsState(
                    targetValue = alphaTarget,
                    animationSpec = tween(durationMillis = GoTickyMotion.Quick),
                    label = "nearbyAlpha-$index"
                )

                val swingSeed = remember(index) { Random.nextFloat() }
                val clampedIntensity = swingIntensity.coerceIn(0.3f, 1.7f)
                val swingAmplitude = (4f + swingSeed * 5f) * clampedIntensity
                val swingDuration = 2600 + (swingSeed * 1400f).toInt()
                val swingTransition = rememberInfiniteTransition(label = "nearbySwing-$index")
                val swing by swingTransition.animateFloat(
                    initialValue = -swingAmplitude,
                    targetValue = swingAmplitude,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = swingDuration, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "nearbySwingAngle-$index"
                )

                val sideTilt = layerIndex * 3f
                val rotationZAnim = swing + sideTilt
                val flickTiltY = ((animatedDragOffset / spreadPx).coerceIn(-2f, 2f)) * 14f
                val rotationY = flickTiltY * (1f - depth * 0.2f)

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            translationX = translationXAnim
                            translationY = depthOffsetYPx
                            scaleX = scaleAnim
                            scaleY = scaleAnim
                            rotationZ = rotationZAnim
                            this.rotationY = rotationY
                            transformOrigin = TransformOrigin(0.5f, 0f)
                            cameraDistance = 18f * density.density
                            this.alpha = alphaAnim
                        }
                        .zIndex(if (isCenter) 3f else 1f - depth * 0.3f),
                    contentAlignment = Alignment.Center
                ) {
                    NearbySwingCard(
                        event = item.event,
                        distanceLabel = item.distance.formatted,
                        fromLabel = item.distance.fromLabel,
                        isPrimary = isCenter,
                        isFavorite = favoriteEvents.contains(item.event.id),
                        onToggleFavorite = { onToggleFavorite(item.event.id) },
                        modifier = Modifier.fillMaxWidth(0.9f),
                        onOpen = { onOpen(item.event) }
                    )
                }
            }
        }

        val canSwipePrev = currentIndex > 0
        val canSwipeNext = currentIndex < lastIndex
        val leftHintAlpha by animateFloatAsState(
            targetValue = if (canSwipePrev) 0.55f else 0f,
            animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = LinearEasing),
            label = "nearbyHintLeft"
        )
        val rightHintAlpha by animateFloatAsState(
            targetValue = if (canSwipeNext) 0.55f else 0f,
            animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = LinearEasing),
            label = "nearbyHintRight"
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SwipeEdgeHint(
                icon = Icons.Outlined.ArrowBack,
                text = "Swipe",
                alpha = leftHintAlpha
            )
            SwipeEdgeHint(
                icon = Icons.Outlined.ArrowForward,
                text = "Swipe",
                alpha = rightHintAlpha
            )
        }
    }
}

@Composable
private fun NeumorphicDistanceTag(
    text: String,
    accent: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(goTickyShapes.medium)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.16f),
                        accent.copy(alpha = 0.6f)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.55f),
                shape = goTickyShapes.medium
            )
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.65f),
                    offset = Offset(0f, 1.5f),
                    blurRadius = 3.5f
                )
            ),
            color = Color.White
        )
    }
}

@Composable
private fun NearbySwingCard(
    event: EventItem,
    distanceLabel: String,
    fromLabel: String,
    isPrimary: Boolean,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier,
    onOpen: () -> Unit,
) {
    val accent = IconCategoryColors[event.category] ?: MaterialTheme.colorScheme.primary
    val favoriteScale by animateFloatAsState(
        targetValue = if (isFavorite) 1.15f else 1f,
        animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
        label = "nearbyFavoriteScale"
    )
    val favoriteTint by animateColorAsState(
        targetValue = if (isFavorite) Color(0xFFFF4B5C) else Color.White.copy(alpha = 0.9f),
        animationSpec = tween(durationMillis = GoTickyMotion.Standard),
        label = "nearbyFavoriteTint"
    )
    Box(
        modifier = modifier
            .height(260.dp)
            .shadow(10.dp, goTickyShapes.extraLarge)
            .background(GoTickyGradients.CardGlow, goTickyShapes.extraLarge)
            .background(GoTickyGradients.GlassWash, goTickyShapes.extraLarge)
            .drawBehind { drawRect(GoTickyTextures.GrainTint) }
            .then(
                if (isPrimary) {
                    Modifier.drawBehind {
                        val strokeWidth = 3.dp.toPx()
                        val radius = 26.dp.toPx()
                        drawRoundRect(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    accent.copy(alpha = 0.95f),
                                    accent.copy(alpha = 0.5f),
                                    accent.copy(alpha = 0.95f)
                                ),
                                start = Offset(0f, 0f),
                                end = Offset(size.width, size.height)
                            ),
                            cornerRadius = CornerRadius(radius, radius),
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth)
                        )
                    }
                } else {
                    Modifier
                }
            )
            .padding(1.5.dp)
            .pressAnimated(scaleDown = if (isPrimary) 0.96f else 0.98f)
            .clickable { onOpen() }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(goTickyShapes.extraLarge)
        ) {
            // Photo layer (resolved from Compose resources)
            val photoRes = event.imagePath?.let { key -> Res.allDrawableResources[key] }

            if (photoRes != null) {
                Image(
                    painter = painterResource(photoRes),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    accent.copy(alpha = 0.8f),
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                                    MaterialTheme.colorScheme.background.copy(alpha = 0.95f)
                                )
                            )
                        )
                )
            }

            // Texture + darkening overlay for readability
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.15f),
                                Color.Black.copy(alpha = 0.85f)
                            )
                        )
                    )
                    .drawBehind { drawRect(GoTickyTextures.GrainTint) }
            )

            IconButton(
                onClick = { onToggleFavorite() },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(14.dp)
                    .size(34.dp)
                    .graphicsLayer(scaleX = favoriteScale, scaleY = favoriteScale)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.55f))
                    .border(1.dp, Color.White.copy(alpha = 0.45f), CircleShape)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from favourites" else "Add to favourites",
                    tint = favoriteTint
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.05f),
                                Color.Black.copy(alpha = 0.85f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    NeumorphicDistanceTag(
                        text = distanceLabel,
                        accent = accent
                    )
                    Text(
                        text = fromLabel,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.8f),
                                offset = Offset(0f, 2f),
                                blurRadius = 6f
                            )
                        ),
                        color = Color(0xFFFDFDFE)
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.8f),
                                offset = Offset(0f, 2.5f),
                                blurRadius = 8f
                            )
                        ),
                        color = Color(0xFFFDFDFE),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${event.city} – ${event.dateLabel}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.7f),
                                offset = Offset(0f, 1.5f),
                                blurRadius = 5f
                            )
                        ),
                        color = Color.White.copy(alpha = 0.95f)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = event.priceFrom,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.75f),
                                    offset = Offset(0f, 2f),
                                    blurRadius = 6f
                                )
                            ),
                            color = accent
                        )
                        event.tag?.let { tag ->
                            Text(
                                text = tag,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    shadow = Shadow(
                                        color = Color.Black.copy(alpha = 0.65f),
                                        offset = Offset(0f, 1.3f),
                                        blurRadius = 4f
                                    )
                                ),
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                    NeonTextButton(
                        text = "View details",
                        onClick = onOpen
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-18).dp)
                .zIndex(4f)
                .graphicsLayer { clip = false }
        ) {
            Box(
                modifier = Modifier
                    .offset(y = 6.dp)
                    .width(1.5.dp)
                    .height(18.dp)
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f))
            )
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(accent)
            )
        }
    }
}

@Composable
private fun FilterPill(
    text: String,
    filters: MutableList<String>,
    onTap: (String) -> Unit,
) {
    val selected = filters.contains(text)
    NeonSelectablePill(
        text = text,
        selected = selected,
        onClick = {
            if (selected) filters.remove(text) else filters.add(text)
            onTap(text)
        }
    )
}

@Composable
private fun NeonSelectablePill(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    centerText: Boolean = false,
    selectedContainerColor: Color = MaterialTheme.colorScheme.primary,
    selectedContentColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    val bg = if (selected) {
        selectedContainerColor.copy(alpha = 0.95f)
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    val fg = if (selected) selectedContentColor else MaterialTheme.colorScheme.onSurface

    Row(
        modifier = modifier
            .pressAnimated(scaleDown = 0.95f)
            .then(
                if (selected) {
                    Modifier.border(1.dp, GoTickyGradients.EdgeHalo, goTickyShapes.medium)
                } else {
                    Modifier
                }
            )
            .background(bg, goTickyShapes.medium)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (centerText) Arrangement.Center else Arrangement.Start
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
            color = fg
        )
    }
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
            .padding(start = 16.dp, end = 16.dp, top = 34.dp),
        contentPadding = PaddingValues(bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            GlowCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                TopBar(
                    title = "My Tickets",
                    onBack = null,
                    actions = null,
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
    userProfile: UserProfile,
    onUpdateProfile: (UserProfile) -> Unit,
    checklistState: MutableMap<String, Boolean>,
    onToggleCheck: (String) -> Unit,
    favorites: List<String>,
    onToggleFavorite: (String) -> Unit,
    adminApplications: List<AdminApplication>,
    onOpenEvent: (EventItem) -> Unit,
    onOpenOrganizer: () -> Unit,
    onGoHome: () -> Unit,
    onOpenPrivacyTerms: () -> Unit,
    onOpenFaq: () -> Unit,
    searchHistory: List<String>,
    onClearSearchHistory: () -> Unit,
    onSelectHistoryQuery: (String) -> Unit,
    onOpenSettings: () -> Unit,
    onLogout: () -> Unit,
    isGuest: Boolean,
) {
    val scrollState = rememberScrollState()
    val headerPulse = infinitePulseAmplitude(
        minScale = 0.99f,
        maxScale = 1.01f,
        durationMillis = 3600,
    )
    val userName = userProfile.fullName
    val userEmail = userProfile.email
    val profileBackgroundPainter = profilePainter(userProfile)
    val profilePhotoRes = remember(userProfile.photoResKey) {
        // prefer explicit profile pic; fall back to hero art if missing
        userProfile.photoResKey?.let { Res.allDrawableResources[it] }
            ?: Res.allDrawableResources["gotickypic"]
            ?: Res.allDrawableResources["hero_vic_falls_midnight_lights"]
    }
    val uriHandler = LocalUriHandler.current
    val clipboard = LocalClipboardManager.current
    var showProfileDetails by remember { mutableStateOf(false) }
    var showSearchHistory by remember { mutableStateOf(false) }
    var showFavorites by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GoTickyGradients.CardGlow)
    ) {
        when {
            profileBackgroundPainter != null -> {
                Image(
                    painter = profileBackgroundPainter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            profilePhotoRes != null -> {
                Image(
                    painter = painterResource(profilePhotoRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.35f),
                            Color.Black.copy(alpha = 0.9f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer(scaleX = headerPulse, scaleY = headerPulse)
                    .clip(goTickyShapes.extraLarge)
                    .background(Color.Black.copy(alpha = 0.35f))
                    .background(GoTickyGradients.GlassWash)
                    .drawBehind { drawRect(GoTickyTextures.GrainTint) }
                    .padding(horizontal = 18.dp, vertical = 16.dp)
            ) {
                // Home button in the corner
                val homeInteraction = remember { MutableInteractionSource() }
                val homePressed by homeInteraction.collectIsPressedAsState()
                val homeScale by animateFloatAsState(
                    targetValue = if (homePressed) 0.9f else 1f,
                    animationSpec = tween(GoTickyMotion.Standard, easing = EaseOutBack),
                    label = "profileHomePress"
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .size(42.dp)
                        .graphicsLayer(scaleX = homeScale, scaleY = homeScale)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.55f))
                        .border(1.dp, GoTickyGradients.EdgeHalo, CircleShape)
                        .clickable(
                            interactionSource = homeInteraction,
                            indication = null,
                        ) { onGoHome() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = "Home",
                        tint = IconCategoryColors[IconCategory.Discover]
                            ?: MaterialTheme.colorScheme.primary
                    )
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.9f),
                                offset = Offset(0f, 3f),
                                blurRadius = 10f
                            )
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = userEmail,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.9f),
                                offset = Offset(0f, 2f),
                                blurRadius = 8f
                            )
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            val profileMenuItems = listOf(
                MenuItemSpec(
                    label = "My Profile",
                    icon = Icons.Outlined.AccountCircle,
                    tint = IconCategoryColors[IconCategory.Profile] ?: MaterialTheme.colorScheme.primary,
                    trailing = null,
                    onClick = { showProfileDetails = true }
                ),
                MenuItemSpec(
                    label = "My Organizer Dashboard",
                    icon = Icons.Outlined.Event,
                    tint = IconCategoryColors[IconCategory.Calendar] ?: MaterialTheme.colorScheme.secondary,
                    trailing = null,
                    onClick = onOpenOrganizer
                ),
                MenuItemSpec(
                    label = "Search History",
                    icon = Icons.Outlined.Search,
                    tint = IconCategoryColors[IconCategory.Discover] ?: MaterialTheme.colorScheme.primary,
                    trailing = null,
                    onClick = { showSearchHistory = true }
                ),
                MenuItemSpec(
                    label = "Favorites",
                    icon = Icons.Outlined.FavoriteBorder,
                    tint = Color(0xFFFF4B5C),
                    trailing = if (favorites.isNotEmpty()) {
                        {
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFFF4B5C).copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = favorites.size.toString(),
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color(0xFFFF4B5C)
                                )
                            }
                        }
                    } else null,
                    onClick = { showFavorites = true }
                ),
                MenuItemSpec(
                    label = "Reviews",
                    icon = Icons.Outlined.Star,
                    tint = Color(0xFFFFD54F),
                    trailing = {
                        Box(
                            modifier = Modifier
                                .size(26.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "3",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    onClick = { }
                ),
                MenuItemSpec(
                    label = "Settings",
                    icon = Icons.Outlined.Settings,
                    tint = Color(0xFFB0BEC5),
                    trailing = null,
                    onClick = onOpenSettings
                ),
                MenuItemSpec(
                    label = "Help & FAQ",
                    icon = Icons.Outlined.HelpOutline,
                    tint = MaterialTheme.colorScheme.primary,
                    trailing = null,
                    onClick = onOpenFaq
                ),
                MenuItemSpec(
                    label = "Privacy Policy and Terms of Service",
                    icon = Icons.Outlined.Shield,
                    tint = Color(0xFF4CAF50),
                    trailing = null,
                    onClick = onOpenPrivacyTerms
                ),
                MenuItemSpec(
                    label = "About",
                    icon = Icons.Outlined.Info,
                    tint = MaterialTheme.colorScheme.secondary,
                    trailing = null,
                    onClick = { }
                )
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                val lastIndex = (profileMenuItems.size - 1).coerceAtLeast(1)
                profileMenuItems.forEachIndexed { index, item ->
                    val outlineStart = lerp(0.20f, 0.03f, index / lastIndex.toFloat())
                    ProfileMenuItem(
                        label = item.label,
                        icon = item.icon,
                        iconTint = item.tint,
                        trailing = item.trailing,
                        outlineStartFraction = outlineStart,
                        onClick = item.onClick
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            PrimaryButton(
                text = "Logout",
                modifier = Modifier.fillMaxWidth(),
                onClick = { onLogout() }
            )
        }
    }

    if (showProfileDetails) {
        ProfileDetailsDialog(
            profile = userProfile,
            onUpdateProfile = onUpdateProfile,
            onDismiss = { showProfileDetails = false },
            isGuest = isGuest
        )
    }

    if (showSearchHistory) {
        AlertDialog(
            onDismissRequest = { showSearchHistory = false },
            title = { Text("Search history") },
            text = {
                var visible by remember { mutableStateOf(false) }
                val scale by animateFloatAsState(
                    targetValue = if (visible) 1f else 0.9f,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
                    label = "searchHistoryScale"
                )
                LaunchedEffect(Unit) { visible = true }

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale)
                ) {
                    if (searchHistory.isEmpty()) {
                        Text(
                            text = "No recent searches yet. Start from GoTicky Live to build your history.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        Text(
                            text = "Tap a query to copy it and reuse in search.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.heightIn(max = 260.dp)
                        ) {
                            items(searchHistory) { query ->
                                GlowCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .pressAnimated(scaleDown = 0.96f)
                                        .clickable {
                                            onSelectHistoryQuery(query)
                                            showSearchHistory = false
                                        }
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 12.dp, vertical = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Search,
                                            contentDescription = null,
                                            tint = IconCategoryColors[IconCategory.Discover]
                                                ?: MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = query,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (searchHistory.isNotEmpty()) {
                        GhostButton(text = "Clear", onClick = {
                            onClearSearchHistory()
                            showSearchHistory = false
                        })
                    }
                    NeonTextButton(text = "Close", onClick = { showSearchHistory = false })
                }
            }
        )
    }

    if (showFavorites) {
        AlertDialog(
            onDismissRequest = { showFavorites = false },
            title = { Text("Favorites") },
            text = {
                var visible by remember { mutableStateOf(false) }
                val scale by animateFloatAsState(
                    targetValue = if (visible) 1f else 0.9f,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
                    label = "favoritesScale"
                )
                LaunchedEffect(Unit) { visible = true }

                val favoriteItems = sampleEvents.filter { favorites.contains(it.id) && isEventPublic(it.id, adminApplications) }

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale)
                ) {
                    if (favoriteItems.isEmpty()) {
                        Text(
                            text = "You haven't saved any events yet. Tap the heart on an event to add it here.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        Text(
                            text = "Tap a card to open details, or tap the heart again to remove it from favourites.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.heightIn(max = 320.dp)
                        ) {
                            items(favoriteItems) { event ->
                                val (earlyLabel, earlyHighlight) = buildEarlyBirdBadgeForEvent(event, adminApplications)
                                EventCard(
                                    item = event,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .pressAnimated(scaleDown = 0.96f),
                                    isFavorite = favorites.contains(event.id),
                                    onToggleFavorite = { onToggleFavorite(event.id) },
                                    onClick = {
                                        onOpenEvent(event)
                                        showFavorites = false
                                    },
                                    earlyBirdLabel = earlyLabel,
                                    earlyBirdHighlight = earlyHighlight
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                NeonTextButton(text = "Close", onClick = { showFavorites = false })
            }
        )
    }
}

@Composable
private fun ProfileMenuItem(
    label: String,
    icon: ImageVector,
    iconTint: Color,
    trailing: (@Composable (() -> Unit))?,
    outlineStartFraction: Float,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val pressScale by animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1f,
        animationSpec = tween(GoTickyMotion.Standard),
        label = "profileItemPress"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(scaleX = pressScale, scaleY = pressScale)
            .clip(goTickyShapes.extraLarge)
            .background(Color.Black.copy(alpha = 0.35f))
            .background(GoTickyGradients.GlassWash)
            .drawBehind { drawRect(GoTickyTextures.GrainTint) }
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    colorStops = arrayOf(
                        0f to iconTint.copy(alpha = 0.55f),
                        outlineStartFraction.coerceIn(0f, 1f) to iconTint.copy(alpha = 0.35f),
                        (outlineStartFraction + 0.02f).coerceAtMost(1f) to Color.Transparent,
                        1f to Color.Transparent
                    ),
                ),
                shape = goTickyShapes.extraLarge
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 18.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(iconTint.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint
                )
            }
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.85f),
                        offset = Offset(0f, 2f),
                        blurRadius = 6f
                    )
                ),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            if (trailing != null) {
                trailing()
            }
        }
    }
}

@Composable
private fun ProfileDetailsDialog(
    profile: UserProfile,
    onUpdateProfile: (UserProfile) -> Unit,
    onDismiss: () -> Unit,
    isGuest: Boolean,
) {
    val clipboard = LocalClipboardManager.current
    val uriHandler = LocalUriHandler.current
    var showCameraDialog by remember { mutableStateOf(false) }
    var editedProfile by remember { mutableStateOf(profile) }
    val photo = profilePainter(editedProfile)
    val pulse = infinitePulseAmplitude(minScale = 0.98f, maxScale = 1.02f, durationMillis = 2200)
    val appearVisible = remember { mutableStateOf(false) }
    val appearScale by animateFloatAsState(
        targetValue = if (appearVisible.value) 1f else 0.95f,
        animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
        label = "profileDialogScale"
    )
    val appearAlpha by animateFloatAsState(
        targetValue = if (appearVisible.value) 1f else 0f,
        animationSpec = tween(durationMillis = GoTickyMotion.Standard),
        label = "profileDialogAlpha"
    )
    LaunchedEffect(Unit) { appearVisible.value = true }

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.Black.copy(alpha = 0.32f),
        unfocusedContainerColor = Color.Black.copy(alpha = 0.22f),
        disabledContainerColor = Color.Black.copy(alpha = 0.18f),
        focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        cursorColor = MaterialTheme.colorScheme.primary,
        focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
    )

    val imagePicker = rememberImagePicker { uri ->
        uri?.let {
            editedProfile = editedProfile.copy(photoUri = it, photoResKey = null)
            onUpdateProfile(editedProfile)
        }
        showCameraDialog = false
    }

    Dialog(onDismissRequest = { showCameraDialog = false; onDismiss() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 360.dp, max = 760.dp)
                .padding(18.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer(
                        scaleX = appearScale,
                        scaleY = appearScale,
                        alpha = appearAlpha
                    )
                    .clip(goTickyShapes.extraLarge)
                    .background(GoTickyGradients.CardGlow)
                    .background(GoTickyGradients.GlassWash)
                    .drawBehind { drawRect(GoTickyTextures.GrainTint) }
                    .border(1.dp, GoTickyGradients.EdgeHalo, goTickyShapes.extraLarge)
                    .padding(18.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Close
                val closeInteraction = remember { MutableInteractionSource() }
                val closePressed by closeInteraction.collectIsPressedAsState()
                val closeScale by animateFloatAsState(
                    targetValue = if (closePressed) 0.9f else 1f,
                    animationSpec = tween(GoTickyMotion.Standard),
                    label = "profileCloseScale"
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { showCameraDialog = false; onDismiss() },
                        modifier = Modifier
                            .graphicsLayer(scaleX = closeScale, scaleY = closeScale)
                            .size(38.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Color(0xFFFF5252)
                        ),
                        interactionSource = closeInteraction
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close profile dialog"
                        )
                    }
                }

                // Header + avatar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer(scaleX = pulse, scaleY = pulse)
                        .pressAnimated(scaleDown = 0.98f)
                        .clip(goTickyShapes.large)
                        .background(Color.Black.copy(alpha = 0.35f))
                        .background(GoTickyGradients.GlassWash)
                        .drawBehind { drawRect(GoTickyTextures.GrainTint) }
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(
                        modifier = Modifier.widthIn(min = 92.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(78.dp)
                                .clip(CircleShape)
                                .background(Color.Black.copy(alpha = 0.45f))
                                .border(2.dp, GoTickyGradients.EdgeHalo, CircleShape)
                                .drawBehind { drawRect(GoTickyTextures.GrainTint) },
                            contentAlignment = Alignment.Center
                        ) {
                            if (photo != null) {
                                Image(
                                    painter = photo,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(74.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Outlined.AccountCircle,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                    modifier = Modifier.size(52.dp)
                                )
                            }
                        }
                        val cameraInteraction = remember { MutableInteractionSource() }
                        val cameraPressed by cameraInteraction.collectIsPressedAsState()
                        val cameraScale by animateFloatAsState(
                            targetValue = if (cameraPressed) 0.9f else 1f,
                            animationSpec = tween(GoTickyMotion.Standard),
                            label = "profileCameraScale"
                        )
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .graphicsLayer(scaleX = cameraScale, scaleY = cameraScale)
                                .clip(CircleShape)
                                .background(Color.Black.copy(alpha = 0.8f))
                                .border(1.dp, GoTickyGradients.EdgeHalo, CircleShape)
                                .clickable(
                                    interactionSource = cameraInteraction,
                                    indication = null
                                ) { showCameraDialog = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.PhotoCamera,
                                contentDescription = "Change photo",
                                tint = Color(0xFF5CF0FF)
                            )
                        }
                    }

                    val displayName = if (isGuest) "GoTicky Guest" else editedProfile.fullName.ifBlank { "Add your name" }
                    val displayEmail = if (isGuest) "guest@goticky.app" else editedProfile.email.ifBlank { "you@email.com" }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = displayName,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.9f),
                                    offset = Offset(0f, 3f),
                                    blurRadius = 10f
                                )
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = displayEmail,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            if (isGuest) {
                                Pill(
                                    text = "Guest mode",
                                    color = Color.Black.copy(alpha = 0.35f),
                                    textColor = MaterialTheme.colorScheme.onSurface
                                )
                                Pill(
                                    text = "Private by default",
                                    color = Color(0xFF2C4BFF).copy(alpha = 0.28f),
                                    textColor = Color(0xFF9ED1FF)
                                )
                            } else {
                                Pill(
                                    text = "Personalized",
                                    color = Color(0xFF183BFF).copy(alpha = 0.35f),
                                    textColor = Color(0xFF9ED1FF)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                @Composable
                fun ProfileField(
                    value: String,
                    onValueChange: (String) -> Unit,
                    label: String,
                    leadingIcon: @Composable (() -> Unit)? = null,
                    trailing: @Composable (() -> Unit)? = null,
                    modifier: Modifier = Modifier,
                    singleLine: Boolean = true,
                    keyboardType: KeyboardType = KeyboardType.Text
                ) {
                    OutlinedTextField(
                        value = value,
                        onValueChange = onValueChange,
                        label = { Text(label) },
                        leadingIcon = leadingIcon,
                        trailingIcon = trailing,
                        modifier = modifier
                            .fillMaxWidth()
                            .clip(goTickyShapes.medium)
                            .background(Color.Transparent),
                        shape = goTickyShapes.medium,
                        singleLine = singleLine,
                        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                        colors = fieldColors
                    )
                }

                if (isGuest) {
                    GlowCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Guest session",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "We hide personal fields for guests. Sign in to store your details, keep alerts synced, and unlock social sharing.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Pill(text = "No signup required", color = Color.Black.copy(alpha = 0.35f), textColor = MaterialTheme.colorScheme.onSurface)
                                Pill(text = "Limited features", color = Color(0xFF2C4BFF).copy(alpha = 0.28f), textColor = Color(0xFF9ED1FF))
                                Pill(text = "Upgrade anytime", color = Color(0xFF20E0B4).copy(alpha = 0.28f), textColor = Color(0xFFCFFCF0))
                            }
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Profile",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        ProfileField(
                            value = editedProfile.fullName,
                            onValueChange = { editedProfile = editedProfile.copy(fullName = it) },
                            label = "Full name",
                            leadingIcon = { Icon(Icons.Outlined.Person, null) }
                        )
                        ProfileField(
                            value = editedProfile.email,
                            onValueChange = { editedProfile = editedProfile.copy(email = it) },
                            label = "Email",
                            leadingIcon = { Icon(Icons.Outlined.Email, null) },
                            trailing = {
                                IconButton(
                                    onClick = { clipboard.setText(AnnotatedString(editedProfile.email)) },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Upload,
                                        contentDescription = "Copy email",
                                        tint = IconCategoryColors[IconCategory.Profile] ?: MaterialTheme.colorScheme.primary
                                    )
                                }
                            },
                            keyboardType = KeyboardType.Email
                        )

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            ProfileField(
                                value = editedProfile.phoneCode,
                                onValueChange = { editedProfile = editedProfile.copy(phoneCode = it) },
                                label = "Code",
                                leadingIcon = { Icon(Icons.Outlined.Flag, null) },
                                modifier = Modifier.widthIn(min = 110.dp).weight(0.45f, fill = false),
                                keyboardType = KeyboardType.Phone
                            )
                            ProfileField(
                                value = editedProfile.phoneNumber,
                                onValueChange = { editedProfile = editedProfile.copy(phoneNumber = it) },
                                label = "Phone",
                                leadingIcon = { Icon(Icons.Outlined.PhoneAndroid, null) },
                                trailing = {
                                    IconButton(
                                        onClick = {
                                            clipboard.setText(AnnotatedString("${editedProfile.phoneCode}${editedProfile.phoneNumber}"))
                                        },
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.CheckCircle,
                                            contentDescription = "Copy phone",
                                            tint = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                keyboardType = KeyboardType.Phone
                            )
                        }

                        ProfileField(
                            value = editedProfile.birthday,
                            onValueChange = { editedProfile = editedProfile.copy(birthday = it) },
                            label = "Birthday",
                            leadingIcon = { Icon(Icons.Outlined.Cake, null) }
                        )
                        ProfileField(
                            value = editedProfile.gender,
                            onValueChange = { editedProfile = editedProfile.copy(gender = it) },
                            label = "Gender",
                            leadingIcon = { Icon(Icons.Outlined.Person, null) }
                        )

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            ProfileField(
                                value = editedProfile.countryFlag,
                                onValueChange = { editedProfile = editedProfile.copy(countryFlag = it) },
                                label = "Flag emoji",
                                leadingIcon = { Icon(Icons.Outlined.Flag, null) },
                                modifier = Modifier.widthIn(min = 120.dp).weight(0.45f, fill = false)
                            )
                            ProfileField(
                                value = editedProfile.countryName,
                                onValueChange = { editedProfile = editedProfile.copy(countryName = it) },
                                label = "Country",
                                leadingIcon = { Icon(Icons.Outlined.Place, null) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val completeness = (
                        listOf(
                            editedProfile.fullName,
                            editedProfile.email,
                            editedProfile.phoneNumber,
                            editedProfile.countryName
                        ).count { it.isNotBlank() } * 20 + 20
                        ).coerceIn(20, 100)
                    val completenessFraction by animateFloatAsState(
                        targetValue = completeness / 100f,
                        animationSpec = tween(GoTickyMotion.Standard, easing = EaseOutBack),
                        label = "profileCompleteness"
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(goTickyShapes.large)
                            .background(Color.Black.copy(alpha = 0.32f))
                            .background(GoTickyGradients.GlassWash)
                            .drawBehind { drawRect(GoTickyTextures.GrainTint) }
                            .border(1.dp, GoTickyGradients.EdgeHalo, goTickyShapes.large)
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Profile completeness",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "$completeness%",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            LinearProgressIndicator(
                                progress = { completenessFraction },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(goTickyShapes.small),
                                trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = null,
                            tint = IconCategoryColors[IconCategory.Alerts]
                                ?: MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PrimaryButton(
                            text = "Save profile",
                            onClick = {
                                onUpdateProfile(editedProfile)
                                onDismiss()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .pressAnimated(scaleDown = 0.96f),
                            icon = { Icon(Icons.Outlined.Save, contentDescription = null) }
                        )
                        NeonTextButton(
                            text = "Share",
                            onClick = {
                                val share = "${editedProfile.fullName} • ${editedProfile.email} • ${editedProfile.countryFlag}"
                                clipboard.setText(AnnotatedString(share))
                            },
                            modifier = Modifier.pressAnimated(scaleDown = 0.94f),
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.Share,
                                    contentDescription = null,
                                    tint = IconCategoryColors[IconCategory.Profile]
                                        ?: Color(0xFF5CF0FF)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
    if (showCameraDialog) {
        Dialog(onDismissRequest = { showCameraDialog = false }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .clip(goTickyShapes.extraLarge)
                    .background(GoTickyGradients.CardGlow)
                    .background(GoTickyGradients.GlassWash)
                    .drawBehind { drawRect(GoTickyTextures.GrainTint) }
                    .border(1.dp, GoTickyGradients.EdgeHalo, goTickyShapes.extraLarge)
                    .padding(18.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Update profile photo",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    listOf(
                        "Take a photo" to Icons.Outlined.PhotoCamera to { imagePicker.capturePhoto() },
                        "Choose from gallery" to Icons.Outlined.PhotoLibrary to { imagePicker.pickFromGallery() },
                    ).forEachIndexed { idx, entry ->
                        val (spec, action) = entry
                        val (label, icon) = spec
                        val interaction = remember { MutableInteractionSource() }
                        val pressed by interaction.collectIsPressedAsState()
                        val scale by animateFloatAsState(
                            targetValue = if (pressed) 0.95f else 1f,
                            animationSpec = tween(GoTickyMotion.Standard),
                            label = "cameraOption-$idx"
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .graphicsLayer(scaleX = scale, scaleY = scale)
                                .clip(goTickyShapes.large)
                                .background(Color.Black.copy(alpha = 0.32f))
                                .background(GoTickyGradients.GlassWash)
                                .drawBehind { drawRect(GoTickyTextures.GrainTint) }
                                .border(1.dp, GoTickyGradients.EdgeHalo, goTickyShapes.large)
                                .clickable(
                                    interactionSource = interaction,
                                    indication = null
                                ) { action() }
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = label,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    NeonTextButton(
                        text = "Cancel",
                        onClick = { showCameraDialog = false }
                    )
                }
            }
        }
    }
}

@Composable
private fun OrganizerDashboardScreen(
    events: List<OrganizerEvent>,
    onBack: () -> Unit,
    onCreateEvent: () -> Unit,
    onOpenEvent: (OrganizerEvent) -> Unit,
    chromeAlpha: Float,
) {
    val totalViews = events.sumOf { it.views }
    val totalSaves = events.sumOf { it.saves }
    val totalSales = events.sumOf { it.sales }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GoTickyGradients.CardGlow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 120.dp),
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
                action = null
            )
            events.forEach { event ->
                OrganizerEventCard(
                    event = event,
                    onClick = { onOpenEvent(event) }
                )
            }
            if (events.isEmpty()) {
                GlowCard(modifier = Modifier.fillMaxWidth()) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "No events yet",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Tap \"Create NEW Event\" to publish your first GoTicky listing.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        OrganizerCreateFab(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 64.dp),
            visibilityFraction = chromeAlpha,
            onClick = onCreateEvent
        )
    }
}

@Composable
private fun OrganizerCreateFab(
    modifier: Modifier = Modifier,
    visibilityFraction: Float = 1f,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val baseScale by animateFloatAsState(
        targetValue = if (pressed) 0.94f else 1f,
        animationSpec = tween(GoTickyMotion.Standard, easing = EaseOutBack),
        label = "organizerFabScale"
    )
    val fabAlphaLocal = visibilityFraction.coerceIn(0f, 1f)
    val haloColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.55f)
    val wordColors = listOf(
        Color(0xFFFFF8E1), // soft warm highlight
        Color(0xFFE3F2FD), // cool highlight
        Color(0xFFFCE4EC), // soft pink highlight
    )
    val danceTransition = rememberInfiniteTransition(label = "newDance")
    val danceOffsetY by danceTransition.animateFloat(
        initialValue = -3.5f,
        targetValue = 3.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = EaseOutBack),
            repeatMode = RepeatMode.Reverse
        ),
        label = "newOffsetY"
    )
    val danceScale by danceTransition.animateFloat(
        initialValue = 0.94f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "newScale"
    )
    val colorIndex by danceTransition.animateFloat(
        initialValue = 0f,
        targetValue = wordColors.size.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "newColorIndex"
    )
    val newColor = remember(colorIndex) {
        val idx = colorIndex.toInt().coerceIn(0, wordColors.lastIndex)
        wordColors[idx]
    }

    Box(
        modifier = modifier.graphicsLayer(
            scaleX = baseScale,
            scaleY = baseScale,
            alpha = fabAlphaLocal
        )
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer(alpha = fabAlphaLocal)
                .drawBehind {
                    val radius = size.maxDimension
                    val haloBrush = Brush.radialGradient(
                        colors = listOf(
                            haloColor,
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
                .clip(goTickyShapes.extraLarge)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                )
                .border(1.dp, GoTickyGradients.EdgeHalo, goTickyShapes.extraLarge)
                .shadow(
                    elevation = 12.dp,
                    shape = goTickyShapes.extraLarge,
                    ambientColor = MaterialTheme.colorScheme.primary
                )
        ) {
            Row(
                modifier = Modifier
                    .clickable(interactionSource = interactionSource, indication = null) { onClick() }
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.18f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Upload,
                        contentDescription = "Create NEW Event",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Create",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "NEW",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = newColor,
                        modifier = Modifier.graphicsLayer(
                            translationY = danceOffsetY,
                            scaleX = danceScale,
                            scaleY = danceScale
                        )
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "Event",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
private fun OrganizerEventDetailScreen(
    event: OrganizerEvent,
    onBack: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val baseEvent = remember(event.eventId) {
        sampleEvents.firstOrNull { it.id == event.eventId }
    }
    val accent = IconCategoryColors[baseEvent?.category ?: IconCategory.Discover]
        ?: MaterialTheme.colorScheme.primary
    val statusColor = when (event.status) {
        "Live" -> MaterialTheme.colorScheme.primary
        "Draft" -> MaterialTheme.colorScheme.outline
        else -> MaterialTheme.colorScheme.tertiary
    }
    val views = event.views.coerceAtLeast(0)
    val saves = event.saves.coerceAtLeast(0)
    val sales = event.sales.coerceAtLeast(0)
    val engagementRate = if (views == 0) 0f else (saves.toFloat() / views.toFloat()).coerceIn(0f, 1f)
    val conversionRate = if (views == 0) 0f else (sales.toFloat() / views.toFloat()).coerceIn(0f, 1f)
    val interestScore = ((engagementRate * 0.4f) + (conversionRate * 0.6f)).coerceIn(0f, 1f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GoTickyGradients.CardGlow)
    ) {
        baseEvent?.imagePath?.let { key ->
            val photoRes = Res.allDrawableResources[key]
            if (photoRes != null) {
                Image(
                    painter = painterResource(photoRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.35f),
                            Color.Black.copy(alpha = 0.92f)
                        )
                    )
                )
                .drawBehind { drawRect(GoTickyTextures.GrainTint) }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            GlowCard(modifier = Modifier.fillMaxWidth()) {
                TopBar(
                    title = "Event performance",
                    onBack = onBack,
                    actions = null,
                    backgroundColor = Color.Transparent
                )
            }

            GlowCard(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Pill(
                            text = "Organizer view",
                            color = Color.Black.copy(alpha = 0.4f),
                            textColor = MaterialTheme.colorScheme.onSurface
                        )
                        Pill(
                            text = event.status,
                            color = statusColor.copy(alpha = 0.18f),
                            textColor = statusColor
                        )
                        if (event.isVerified) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.9f),
                                offset = Offset(0f, 3f),
                                blurRadius = 10f
                            )
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${event.city} – ${event.venue}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = event.dateLabel,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = event.priceFrom,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = accent
                    )
                }
            }

            GlowCard(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Stats overview",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OrganizerDetailStat(
                            label = "Views",
                            value = views,
                            accent = MaterialTheme.colorScheme.primary
                        )
                        OrganizerDetailStat(
                            label = "Saves",
                            value = saves,
                            accent = MaterialTheme.colorScheme.secondary
                        )
                        OrganizerDetailStat(
                            label = "Sales",
                            value = sales,
                            accent = MaterialTheme.colorScheme.tertiary
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Engagement",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        AnimatedProgressBar(
                            progress = engagementRate,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "${(engagementRate * 100).toInt()}% of viewers saved this event",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Conversion",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        AnimatedProgressBar(
                            progress = conversionRate,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "${(conversionRate * 100).toInt()}% of viewers purchased tickets",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Overall health",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        AnimatedProgressBar(
                            progress = interestScore,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = when {
                                interestScore >= 0.66f -> "Strong momentum – keep pushing alerts and social posts."
                                interestScore >= 0.33f -> "Decent traction – consider early-bird or bundle offers."
                                else -> "Quiet so far – a flyer refresh and more sharing could help."
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            GlowCard(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Audience signals",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        NeonSelectablePill(
                            text = if (views > 0) "Discovery on track" else "Needs discovery",
                            selected = views > 0,
                            onClick = {}
                        )
                        NeonSelectablePill(
                            text = if (saves > 0) "People are saving this" else "Few saves yet",
                            selected = saves > 0,
                            onClick = {}
                        )
                        NeonSelectablePill(
                            text = if (sales > 0) "Tickets moving" else "No sales in sample data",
                            selected = sales > 0,
                            onClick = {}
                        )
                        NeonSelectablePill(
                            text = event.city,
                            selected = true,
                            onClick = {},
                            selectedContainerColor = accent,
                            selectedContentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Text(
                        text = "These signals are based on the demo data for your uploaded events.",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun OrganizerDetailStat(
    label: String,
    value: Int,
    accent: Color,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
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

@Composable
private fun OrganizerMetricCard(
    title: String,
    value: Int,
    accent: Color,
    modifier: Modifier = Modifier,
) {
    GlowCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
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
private fun OrganizerEventCard(
    event: OrganizerEvent,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    GlowCard(
        modifier = modifier
            .fillMaxWidth()
            .pressAnimated(scaleDown = 0.97f)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.weight(1f))
                Pill(text = event.status)
            }
            Text(
                text = "${event.city} • ${event.venue}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = event.dateLabel,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = event.priceFrom,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = event.views.toString(),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "views",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = event.saves.toString(),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "saves",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = event.sales.toString(),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "sales",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CheckoutSuccessScreen(
    amount: Int?,
    method: String?,
    ticketType: String,
    onViewTickets: () -> Unit,
    onBackHome: () -> Unit,
) {
    var confettiVisible by remember { mutableStateOf(false) }
    val paidAtInstant: Instant = remember { currentInstant() }
    val paidAt = remember(paidAtInstant) { paidAtInstant.toLocalDateTime(TimeZone.currentSystemDefault()) }
    val paidAtLabel = remember(paidAt) {
        val date = paidAt.date
        val time = paidAt.time
        val minute = time.minute.toString().padStart(2, '0')
        val month = date.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
        "${date.dayOfMonth} $month ${date.year}, ${time.hour}:$minute"
    }
    val transactionId = remember { "TXN-${sampleOrder.id.takeLast(4)}-${(1000..9999).random()}" }
    val pulse by rememberInfiniteTransition(label = "successPulse").animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "successPulseValue"
    )

    LaunchedEffect(Unit) { confettiVisible = true }

    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GoTickyGradients.CardGlow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            GlowCard(modifier = Modifier.fillMaxWidth()) {
                TopBar(
                    title = "Payment successful",
                    onBack = null, // No back button on success screen
                    actions = null,
                    backgroundColor = Color.Transparent,
                    titleContent = {
                        Text(
                            text = "Payment successful",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                            color = Color(0xFF7CFF7A)
                        )
                    }
                )
            }

            GlowCard(modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer(scaleX = pulse, scaleY = pulse)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.16f))
                            .drawBehind { drawRect(GoTickyTextures.GrainTint) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(54.dp)
                        )
                    }
                    Text(
                        text = "You’re locked in!",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    val methodLabel = when (method) {
                        null -> "Pesepay or card"
                        else -> method.replaceFirstChar { it.uppercase() }
                    }
                    val amountLabel = amount?.let { "$$it" } ?: "Your total"
                    Text(
                        text = "$amountLabel • $ticketType",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Paid via $methodLabel. Your tickets are ready in your wallet.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    AnimatedProgressBar(
                        progress = 1f,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Animated invoice-style payment summary
            var showInvoice by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                delay(400)
                showInvoice = true
            }

            AnimatedVisibility(
                visible = showInvoice,
                enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(animationSpec = tween(400))
            ) {
                GlowCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Payment summary",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Invoice #" + sampleOrder.id,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Paid at",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = paidAtLabel,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Transaction",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = transactionId,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        sampleOrder.items.forEach { item ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = item.label,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.95f)
                                )
                                Text(
                                    text = item.price,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                        sampleOrder.fees.forEach { fee ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = fee.label,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = fee.price,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                        )
                        val discountLabel = if (ticketType.contains("Early", ignoreCase = true)) {
                            "Early Bird discount applied"
                        } else {
                            "No discounts applied"
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Discounts",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                            Text(
                                text = discountLabel,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total paid",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            val invoiceTotal = amount?.let { "$$it" } ?: sampleOrder.total
                            Text(
                                text = invoiceTotal,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            GlowCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "What’s next",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "View tickets",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        NeonTextButton(text = "Open wallet", onClick = onViewTickets)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Share with friends",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        NeonTextButton(text = "Invite", onClick = onBackHome)
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                PrimaryButton(
                    text = "View tickets",
                    modifier = Modifier.weight(1f)
                ) {
                    onViewTickets()
                }
                GhostButton(
                    text = "Back home",
                    modifier = Modifier.weight(1f)
                ) {
                    onBackHome()
                }
            }
        }

        if (confettiVisible) {
            // Simple, lightweight confetti-style overlay using gradients
            val confettiColors = listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.secondary,
                MaterialTheme.colorScheme.tertiary
            )
            Canvas(
                modifier = Modifier
                    .matchParentSize()
                    .pointerInput(Unit) {}
            ) {
                val random = Random(42)
                repeat(40) {
                    val x = random.nextFloat() * size.width
                    val y = random.nextFloat() * size.height
                    val w = random.nextFloat() * 10f + 4f
                    val h = random.nextFloat() * 18f + 6f
                    drawRect(
                        color = confettiColors[random.nextInt(confettiColors.size)].copy(alpha = 0.25f),
                        topLeft = Offset(x, y),
                        size = androidx.compose.ui.geometry.Size(w, h)
                    )
                }
            }
        }
    }
}

@Composable
private fun ForYouRecommendationsRow(
    recommendations: List<Recommendation>,
    favoriteEvents: List<String>,
    onToggleFavorite: (String) -> Unit,
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
                val isFavorite = favoriteEvents.contains(rec.eventId)
                val favoriteScale by animateFloatAsState(
                    targetValue = if (isFavorite) 1.15f else 1f,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
                    label = "forYouFavoriteScale"
                )
                val favoriteTint by animateColorAsState(
                    targetValue = if (isFavorite) Color(0xFFFF4B5C) else Color.White.copy(alpha = 0.9f),
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard),
                    label = "forYouFavoriteTint"
                )

                Box(
                    modifier = Modifier
                        .width(260.dp)
                        .shadow(10.dp, goTickyShapes.extraLarge)
                        .clip(goTickyShapes.extraLarge)
                        .background(GoTickyGradients.CardGlow)
                        .background(GoTickyGradients.GlassWash)
                        .drawBehind { drawRect(GoTickyTextures.GrainTint) }
                        .pressAnimated(scaleDown = 0.96f)
                        .clickable { onOpen(rec) }
                ) {
                    Box(
                        modifier = Modifier
                            .height(180.dp)
                            .fillMaxWidth()
                            .padding(1.5.dp)
                            .clip(goTickyShapes.large)
                    ) {
                        // Resolve image key: explicit on rec, otherwise fall back to event photo
                        val imageKey = rec.imageKey
                            ?: sampleEvents.firstOrNull { it.id == rec.eventId }?.imagePath
                        val photoRes = imageKey?.let { key -> Res.allDrawableResources[key] }

                        if (photoRes != null) {
                            Image(
                                painter = painterResource(photoRes),
                                contentDescription = null,
                                modifier = Modifier.matchParentSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(
                                                MaterialTheme.colorScheme.primary.copy(alpha = 0.45f),
                                                MaterialTheme.colorScheme.background.copy(alpha = 0.95f)
                                            )
                                        )
                                    )
                            )
                        }

                        // Dark overlay + grain for readability
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black.copy(alpha = 0.15f),
                                            Color.Black.copy(alpha = 0.85f)
                                        )
                                    )
                                )
                                .drawBehind { drawRect(GoTickyTextures.GrainTint) }
                        )

                        IconButton(
                            onClick = { onToggleFavorite(rec.eventId) },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                                .size(24.dp)
                                .graphicsLayer(scaleX = favoriteScale, scaleY = favoriteScale)
                                .clip(CircleShape)
                                .background(Color.Black.copy(alpha = 0.25f))
                                .border(1.dp, Color.White.copy(alpha = 0.10f), CircleShape)
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = if (isFavorite) "Remove from favourites" else "Add to favourites",
                                tint = favoriteTint
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Pill(
                                text = rec.tag,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                                textColor = MaterialTheme.colorScheme.primary
                            )
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    rec.title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        shadow = Shadow(
                                            color = Color.Black.copy(alpha = 0.8f),
                                            offset = Offset(0f, 2f),
                                            blurRadius = 6f
                                        )
                                    ),
                                    color = Color(0xFFFDFDFE),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    "${rec.city} • ${rec.reason}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        shadow = Shadow(
                                            color = Color.Black.copy(alpha = 0.7f),
                                            offset = Offset(0f, 1.5f),
                                            blurRadius = 4f
                                        )
                                    ),
                                    color = Color.White.copy(alpha = 0.94f),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    rec.priceFrom,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                NeonTextButton(text = "Open", onClick = { onOpen(rec) })
                            }
                        }
                    }
                }
            }
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
                Box(
                    modifier = Modifier
                        .width(260.dp)
                        .shadow(10.dp, goTickyShapes.extraLarge)
                        .clip(goTickyShapes.extraLarge)
                        .background(GoTickyGradients.CardGlow)
                        .background(GoTickyGradients.GlassWash)
                        .drawBehind { drawRect(GoTickyTextures.GrainTint) }
                        .pressAnimated(scaleDown = 0.96f)
                        .clickable { onOpen(rec) }
                ) {
                    Box(
                        modifier = Modifier
                            .height(180.dp)
                            .fillMaxWidth()
                            .padding(1.5.dp)
                            .clip(goTickyShapes.large)
                    ) {
                        // Resolve image key: explicit on rec, otherwise fall back to event photo
                        val imageKey = rec.imageKey
                            ?: sampleEvents.firstOrNull { it.id == rec.eventId }?.imagePath
                        val photoRes = imageKey?.let { key -> Res.allDrawableResources[key] }

                        if (photoRes != null) {
                            Image(
                                painter = painterResource(photoRes),
                                contentDescription = null,
                                modifier = Modifier.matchParentSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(
                                                MaterialTheme.colorScheme.primary.copy(alpha = 0.45f),
                                                MaterialTheme.colorScheme.background.copy(alpha = 0.95f)
                                            )
                                        )
                                    )
                            )
                        }

                        // Dark overlay + grain for readability
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black.copy(alpha = 0.15f),
                                            Color.Black.copy(alpha = 0.85f)
                                        )
                                    )
                                )
                                .drawBehind { drawRect(GoTickyTextures.GrainTint) }
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(14.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Pill(
                                text = rec.tag,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                                textColor = MaterialTheme.colorScheme.primary
                            )
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    rec.title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        shadow = Shadow(
                                            color = Color.Black.copy(alpha = 0.8f),
                                            offset = Offset(0f, 2f),
                                            blurRadius = 6f
                                        )
                                    ),
                                    color = Color(0xFFFDFDFE),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    "${rec.city} • ${rec.reason}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        shadow = Shadow(
                                            color = Color.Black.copy(alpha = 0.7f),
                                            offset = Offset(0f, 1.5f),
                                            blurRadius = 4f
                                        )
                                    ),
                                    color = Color.White.copy(alpha = 0.94f),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    rec.priceFrom,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                NeonTextButton(text = "Open", onClick = { onOpen(rec) })
                            }
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
            .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 120.dp),
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
                            NeonSelectablePill(
                                text = tag,
                                selected = selected,
                                onClick = {
                                    genres = if (selected) genres - tag else genres + tag
                                }
                            )
                        }
                    }
                    Text("Home city: $city", style = MaterialTheme.typography.bodySmall)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Harare", "Bulawayo", "Gaborone").forEach { option ->
                            val selected = city == option
                            NeonSelectablePill(
                                text = option,
                                selected = selected,
                                onClick = { city = option },
                                selectedContainerColor = MaterialTheme.colorScheme.secondary,
                                selectedContentColor = MaterialTheme.colorScheme.onSecondary
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GoTickyGradients.CardGlow)
    ) {
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
}

@Composable
private fun SettingsScreen(
    prefs: SettingsPrefs,
    onPrefsChange: (SettingsPrefs) -> Unit,
    onBack: () -> Unit,
    onOpenPrivacyTerms: () -> Unit,
    onOpenFaq: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GoTickyGradients.CardGlow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            GlowCard(modifier = Modifier.fillMaxWidth()) {
                TopBar(
                    title = "Settings",
                    onBack = onBack,
                    actions = null,
                    backgroundColor = Color.Transparent
                )
            }

            GlowCard {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "Notifications",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    SettingsToggleRow(
                        title = "Event alerts & drops",
                        description = "Show push alerts for price drops, new dates, and saved events.",
                        checked = prefs.pushEnabled,
                        onCheckedChange = { onPrefsChange(prefs.copy(pushEnabled = it)) }
                    )
                    SettingsToggleRow(
                        title = "Email updates",
                        description = "Occasional roundups for watched events and new city picks.",
                        checked = prefs.emailUpdates,
                        onCheckedChange = { onPrefsChange(prefs.copy(emailUpdates = it)) }
                    )
                }
            }

            GlowCard {
                val tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                val iconPulse by rememberInfiniteTransition(label = "rememberMePulse").animateFloat(
                    initialValue = 0.96f,
                    targetValue = 1.06f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 1200, easing = EaseOutBack),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "rememberMeScale"
                )
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .graphicsLayer(scaleX = iconPulse, scaleY = iconPulse)
                                .clip(CircleShape)
                                .background(tint.copy(alpha = 0.18f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Shield,
                                contentDescription = null,
                                tint = tint
                            )
                        }
                        Text(
                            "Security & sign-in",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    SettingsToggleRow(
                        title = "Remember me on this device",
                        description = "Skip the sign-in screen next time while you stay signed in.",
                        checked = prefs.rememberMe,
                        onCheckedChange = { onPrefsChange(prefs.copy(rememberMe = it)) }
                    )
                }
            }

            GlowCard {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "Data & performance",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    SettingsToggleRow(
                        title = "Data saver for artwork",
                        description = "Prefer lighter hero art and fewer background refreshes when on mobile data.",
                        checked = prefs.dataSaver,
                        onCheckedChange = { onPrefsChange(prefs.copy(dataSaver = it)) }
                    )
                }
            }

            GlowCard {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "Feedback",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    SettingsToggleRow(
                        title = "Haptic feedback",
                        description = "Use subtle vibration on key actions like saves and checkout steps.",
                        checked = prefs.hapticsEnabled,
                        onCheckedChange = { onPrefsChange(prefs.copy(hapticsEnabled = it)) }
                    )
                }
            }

            GlowCard {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        "Support",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    SettingsLinkRow(
                        label = "Privacy policy & terms",
                        onClick = onOpenPrivacyTerms
                    )
                    SettingsLinkRow(
                        label = "Help & FAQ",
                        onClick = onOpenFaq
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsToggleRow(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
        label = "settingsToggleScale-$title"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .clip(goTickyShapes.medium)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.65f))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onCheckedChange(!checked) }
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
private fun SettingsLinkRow(
    label: String,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
        label = "settingsLinkScale-$label"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .clip(goTickyShapes.medium)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.65f))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSurface
        )
        Icon(
            imageVector = Icons.Outlined.ArrowForward,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun LegalScreen(onBack: () -> Unit) {
    val scrollState = rememberScrollState()
    val uriHandler = LocalUriHandler.current
    val pdfUrl = "https://goticky.app/legal/goticky-privacy-terms.pdf"
    val sections = listOf(
        "Overview" to "GoTicky is a ticket discovery and purchase experience. We use your data to process orders, prevent fraud, personalize recommendations, and keep you informed about events you engage with.",
        "Data we collect" to "Account data (name, email, phone), preferences (genres, cities), device signals for fraud prevention, and transaction metadata. We do not store card details—payments are handled by PCI-compliant providers.",
        "How we use data" to "To issue tickets, notify you about changes, recommend relevant events, fight fraud/abuse, and comply with legal requests. We never sell your personal data.",
        "Sharing" to "We share minimal data with payment processors, venue partners (for entry lists), and security/fraud services. Any marketing is opt-in and revocable.",
        "Your controls" to "You can update profile info, clear search history, revoke marketing, and request data deletion/export. Alerts and personalization are opt-in.",
        "Security" to "TLS in transit, encrypted storage for sensitive tokens, rate-limiting for abusive behavior, and anomaly detection on checkout/alerts.",
        "Contact" to "privacy@goticky.app for data rights; security@goticky.app for vulnerability reports."
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GoTickyGradients.CardGlow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            GlowCard(modifier = Modifier.fillMaxWidth()) {
                TopBar(
                    title = "Privacy & Terms",
                    onBack = onBack,
                    actions = {
                        NeonTextButton(text = "Download", onClick = { uriHandler.openUri(pdfUrl) })
                    },
                    backgroundColor = Color.Transparent
                )
            }
            GlowCard {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        "Our promise",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        "We operate with a privacy-first mindset for ticketing. This summary highlights the essentials; detailed clauses follow.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            sections.forEachIndexed { index, (title, body) ->
                val delay = 80 * index
                val visible = remember { mutableStateOf(false) }
                LaunchedEffect(Unit) { delay(delay.toLong()); visible.value = true }
                AnimatedVisibility(
                    visible = visible.value,
                    enter = fadeIn(animationSpec = tween(280, delayMillis = delay)) + slideInVertically { it / 5 },
                ) {
                    GlowCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .pressAnimated(scaleDown = 0.98f)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                title,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                body,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
            GlowCard {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Terms (high level)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    val terms = listOf(
                        "Use of service" to "You agree to truthful info, no scalping abuse, and compliance with venue rules.",
                        "Tickets & refunds" to "All sales are subject to organizer policies; we surface refund/transfer options where supported.",
                        "Liability" to "We are not responsible for event changes or cancellations beyond our control; we facilitate organizer remedies.",
                        "User conduct" to "No fraud, harassment, or platform abuse. Violations may lead to suspension.",
                        "Changes" to "We may update these terms; continued use means acceptance. Material changes will be highlighted."
                    )
                    terms.forEach { (t, desc) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(goTickyShapes.medium)
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                            )
                            Column {
                                Text(t, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                                Text(desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FaqScreen(onBack: () -> Unit) {
    val faqs = listOf(
        "How do I transfer or resell a ticket?" to "Open your ticket, tap Transfer/Share. If the organizer allows transfers, you'll get a shareable QR or link.",
        "Can I get a refund?" to "Refunds follow the organizer's policy. If enabled, you'll see a Request Refund button under the ticket or receive credits.",
        "Why are prices changing?" to "Dynamic pricing is set by organizers. We show the latest price and the lowest available option in \"price from\".",
        "Is my payment secure?" to "Payments are processed by PCI-compliant providers; we never store your card. Suspicious activity triggers verification.",
        "How do alerts work?" to "Set a price or drop alert. We'll notify you when prices move or new drops land. You can pause alerts anytime.",
        "Which cities does GoTicky support?" to "We focus on Zimbabwe now, with curated events in Harare and Bulawayo. More cities are coming."
    )
    val openIndices = remember { mutableStateMapOf<Int, Boolean>() }
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GoTickyGradients.CardGlow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            GlowCard(modifier = Modifier.fillMaxWidth()) {
                TopBar(
                    title = "Help & FAQ",
                    onBack = onBack,
                    actions = {
                        NeonTextButton(text = "Contact", onClick = { /* stub: open support */ })
                    },
                    backgroundColor = Color.Transparent
                )
            }
            GlowCard {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "Quick answers",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        "Based on what users ask most in GoTicky: transfers, refunds, dynamic pricing, security, alerts, and city coverage.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            faqs.forEachIndexed { index, (q, a) ->
                val expanded = openIndices[index] ?: false
                val transition = updateTransition(expanded, label = "faq$index")
                val arrowRotation by transition.animateFloat(
                    transitionSpec = { tween(durationMillis = 240, easing = EaseOutBack) },
                    label = "faqArrow$index"
                ) { if (it) 180f else 0f }
                GlowCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .pressAnimated(scaleDown = 0.97f)
                        .clickable { openIndices[index] = !expanded }
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                q,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Icon(
                                imageVector = Icons.Outlined.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.rotate(arrowRotation),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        AnimatedVisibility(
                            visible = expanded,
                            enter = fadeIn(animationSpec = tween(200)) + slideInVertically { it / 4 },
                            exit = fadeOut(animationSpec = tween(160))
                        ) {
                            Text(
                                a,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            GlowCard {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Need more help?",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    PrimaryButton(text = "Chat with support") { /* stub: open chat */ }
                    GhostButton(text = "Email support") { /* stub: email */ }
                }
            }
        }
    }
}

private fun generateTimeSlots(): List<String> {
    val result = mutableListOf<String>()
    val minutes = listOf(0, 30)
    for (hour in 0 until 24) {
        for (minute in minutes) {
            val hour12 = when (val h = hour % 12) {
                0 -> 12
                else -> h
            }
            val suffix = if (hour < 12) "AM" else "PM"
            val minuteLabel = if (minute == 0) "00" else minute.toString()
            result.add("$hour12:$minuteLabel $suffix")
        }
    }
    return result
}

@Composable
@kotlin.OptIn(kotlin.time.ExperimentalTime::class)
private fun EventDetailScreen(
    event: org.example.project.data.EventItem,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onBack: () -> Unit,
    onProceedToCheckout: (String) -> Unit,
    onAlert: () -> Unit,
    totalTickets: Int? = null,
    ticketsSold: Int? = null,
    adminApplication: AdminApplication? = null,
    earlyBirdWindow: EarlyBirdWindow? = null,
) {
    var showReport by remember { mutableStateOf(false) }
    var priceAlertSelected by remember { mutableStateOf(false) }
    var selectedReason by remember { mutableStateOf("Wrong information") }
    var bannerLogged by remember { mutableStateOf(false) }
    val ticketOptions = listOf("Early Bird", "General / Standard", "VIP", "Golden Circle")
    var selectedTicketType by remember { mutableStateOf<String?>(null) }
    val nowState = remember { mutableStateOf<Instant>(currentInstant()) }
    LaunchedEffect(Unit) {
        while (true) {
            nowState.value = currentInstant()
            delay(30_000)
        }
    }
    val now = nowState.value
    val earlyBirdActive = earlyBirdWindow?.let { now >= it.start && now <= it.end } ?: false
    val earlyBirdExpired = earlyBirdWindow?.let { now > it.end } ?: false
    val earlyBirdPendingApproval = adminApplication != null && adminApplication.status != "Approved"
    val earlyBirdRemaining: Duration? = earlyBirdWindow?.let { window ->
        (window.end - now).coerceAtLeast(ZERO)
    }
    val earlyBirdProgressTarget = earlyBirdWindow?.let { window ->
        val totalMs = (window.end - window.start).toLong(DurationUnit.MILLISECONDS).coerceAtLeast(1L)
        val elapsedMs = (now - window.start).toLong(DurationUnit.MILLISECONDS).coerceAtLeast(0L)
        (elapsedMs.toFloat() / totalMs.toFloat()).coerceIn(0f, 1f)
    } ?: 0f
    val earlyBirdProgress by animateFloatAsState(
        targetValue = earlyBirdProgressTarget,
        animationSpec = tween(500, easing = LinearEasing),
        label = "earlyBirdProgress"
    )
    var checkoutNotice by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(earlyBirdActive) {
        if (earlyBirdActive) {
            Analytics.log(
                AnalyticsEvent(
                    name = "early_bird_view",
                    params = mapOf("event_id" to event.id, "title" to event.title)
                )
            )
        }
    }
    LaunchedEffect(earlyBirdWindow?.start, earlyBirdWindow?.end) {
        if (earlyBirdWindow != null && !bannerLogged) {
            bannerLogged = true
            Analytics.log(
                AnalyticsEvent(
                    name = "early_bird_banner_impression",
                    params = mapOf("event_id" to event.id, "title" to event.title)
                )
            )
        }
    }
    LaunchedEffect(earlyBirdActive, earlyBirdExpired, earlyBirdPendingApproval) {
        if (!earlyBirdActive && selectedTicketType == "Early Bird") {
            selectedTicketType = null
        }
    }
    val accent = IconCategoryColors[event.category] ?: MaterialTheme.colorScheme.primary
    val heroPulse = rememberInfiniteTransition(label = "detailHero").animateFloat(
        initialValue = -120f,
        targetValue = 220f,
        animationSpec = infiniteRepeatable(tween(2800, easing = LinearEasing)),
        label = "detailHeroSheen"
    )
    val favoriteScale by animateFloatAsState(
        targetValue = if (isFavorite) 1.1f else 1f,
        animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
        label = "detailFavoriteScale"
    )
    val favoriteTint by animateColorAsState(
        targetValue = if (isFavorite) Color(0xFFFF4B5C) else Color.White.copy(alpha = 0.9f),
        animationSpec = tween(durationMillis = GoTickyMotion.Standard),
        label = "detailFavoriteTint"
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(GoTickyGradients.CardGlow)
            .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GlowCard(modifier = Modifier.fillMaxWidth()) {
            TopBar(
                title = event.title,
                onBack = onBack,
                actions = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        NeonSelectablePill(
                            text = "Report",
                            selected = showReport,
                            onClick = { showReport = true },
                            modifier = Modifier.border(
                                1.dp,
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
                                goTickyShapes.medium
                            )
                        )
                    }
                },
                backgroundColor = Color.Transparent
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(10.dp, goTickyShapes.extraLarge)
                .clip(goTickyShapes.extraLarge)
                .background(GoTickyGradients.CardGlow)
                .background(GoTickyGradients.GlassWash)
                .drawBehind { drawRect(GoTickyTextures.GrainTint) }
        ) {
            // Large banner that bleeds to card edges with a slim inset
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .padding(1.5.dp) // match popular nearby inset
                    .clip(goTickyShapes.extraLarge)
            ) {
                // Photo layer
                event.imagePath?.let { key ->
                    val res = Res.allDrawableResources[key]
                    if (res != null) {
                        Image(
                            painter = painterResource(res),
                            contentDescription = null,
                            modifier = Modifier.matchParentSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                } ?: Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    accent.copy(alpha = 0.85f),
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.95f)
                                )
                            )
                        )
                )

                // Grain + sheen overlay and content padding
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .drawBehind {
                            drawRect(GoTickyTextures.GrainTint)
                            drawRoundRect(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.22f),
                                        Color.Transparent
                                    ),
                                    start = Offset(heroPulse.value, 0f),
                                    end = Offset(heroPulse.value + 240f, size.height)
                                ),
                                cornerRadius = CornerRadius(24f, 24f)
                            )
                        }
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Text(
                            event.title,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.55f),
                                    offset = Offset(0f, 2f),
                                    blurRadius = 6f
                                )
                            ),
                            color = Color.White
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GlowCard(
                modifier = Modifier.wrapContentWidth()
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Event info",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    val infoRows = listOf(
                        Pair(Icons.Outlined.Event, event.dateLabel),
                        Pair(Icons.Outlined.Place, event.city),
                        Pair(Icons.Outlined.ReceiptLong, event.priceFrom)
                    )
                    infoRows.forEach { (icon, copy) ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(accent.copy(alpha = 0.14f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(icon, contentDescription = null, tint = accent)
                            }
                            Text(
                                text = copy,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier
                            .size(32.dp)
                            .graphicsLayer(scaleX = favoriteScale, scaleY = favoriteScale)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.55f))
                            .border(1.dp, Color.White.copy(alpha = 0.45f), CircleShape)
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (isFavorite) "Remove from favourites" else "Add to favourites",
                            tint = favoriteTint
                        )
                    }
                    AnimatedContent(
                        targetState = isFavorite,
                        label = "FavoriteCaption"
                    ) { fav ->
                        Text(
                            text = if (fav) "added to your favorites!" else "select this event as a favorite!",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        GlowCard {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ticket options",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (totalTickets != null && totalTickets > 0 && ticketsSold != null) {
                        val remaining = (totalTickets - ticketsSold).coerceAtLeast(0)
                        val soldColor = Color(0xFFFF4B5C)
                        val totalColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.95f)
                        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = "Ticket Count",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "$remaining/${totalTickets} left",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = soldColor
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 2.dp)
                                    .clip(goTickyShapes.small)
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
                            ) {
                                val progress = (remaining.toFloat() / totalTickets.toFloat()).coerceIn(0f, 1f)
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(progress)
                                        .height(4.dp)
                                        .clip(goTickyShapes.small)
                                        .background(Color(0xFFFF4B5C))
                                )
                            }
                        }
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    AnimatedVisibility(
                        visible = earlyBirdWindow != null,
                        enter = fadeIn() + slideInVertically { it / 6 },
                        exit = fadeOut() + slideOutVertically { it / 6 }
                    ) {
                        GlowCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    1.dp,
                                    GoTickyGradients.EdgeHalo,
                                    goTickyShapes.large
                                )
                        ) {
                            val shimmer = rememberInfiniteTransition(label = "earlyBirdShimmer").animateFloat(
                                initialValue = -140f,
                                targetValue = 260f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(durationMillis = 2200, easing = LinearEasing),
                                    repeatMode = RepeatMode.Restart
                                ),
                                label = "earlyBirdShimmerX"
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .drawBehind {
                                        if (earlyBirdActive) {
                                            drawRoundRect(
                                                brush = Brush.linearGradient(
                                                    colors = listOf(
                                                        Color.Transparent,
                                                        Color.White.copy(alpha = 0.24f),
                                                        Color.Transparent
                                                    ),
                                                    start = Offset(shimmer.value, 0f),
                                                    end = Offset(shimmer.value + 180f, size.height)
                                                ),
                                                cornerRadius = CornerRadius(40f, 40f)
                                            )
                                        }
                                    }
                                    .padding(12.dp)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    val headline = when {
                                        earlyBirdActive -> "Early Bird live • ${earlyBirdWindow?.discountPercent ?: 20}% off"
                                        earlyBirdPendingApproval -> "Early Bird coming soon"
                                        earlyBirdExpired -> "Early Bird ended"
                                        else -> "Early Bird inactive"
                                    }
                                    val caption = when {
                                        earlyBirdActive && earlyBirdRemaining != null -> "Ends in ${formatDurationShort(earlyBirdRemaining)} • ${earlyBirdWindow?.basePriceLabel} → ${earlyBirdWindow?.earlyPriceLabel}"
                                        earlyBirdPendingApproval -> "Early Bird will be available once this event is fully set up."
                                        earlyBirdExpired -> "Window closed. Switching to General."
                                        else -> "Early Bird is not available for this event."
                                    }
                                    Text(
                                        text = headline,
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = caption,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    if (earlyBirdWindow != null) {
                                        LinearProgressIndicator(
                                            progress = { earlyBirdProgress },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(goTickyShapes.small),
                                            color = MaterialTheme.colorScheme.primary,
                                            trackColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    ticketOptions.forEach { option ->
                        val selected = selectedTicketType == option
                        val earlyBirdOption = option == "Early Bird"
                        val disabled = earlyBirdOption && !earlyBirdActive
                        val disabledReason = when {
                            earlyBirdExpired -> "Ended"
                            earlyBirdPendingApproval && earlyBirdOption -> "Not available yet"
                            earlyBirdOption && earlyBirdWindow == null -> "Not available"
                            else -> ""
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .then(
                                    if (selected) {
                                        Modifier.border(
                                            width = 1.5.dp,
                                            brush = GoTickyGradients.EdgeHalo,
                                            shape = goTickyShapes.extraLarge
                                        )
                                    } else {
                                        Modifier
                                    }
                                )
                        ) {
                            val onSelectOption: () -> Unit = {
                                if (!disabled) {
                                    selectedTicketType = option
                                    if (earlyBirdOption) {
                                        Analytics.log(
                                            AnalyticsEvent(
                                                name = "early_bird_select",
                                                params = mapOf("event_id" to event.id, "title" to event.title)
                                            )
                                        )
                                    }
                                } else if (earlyBirdPendingApproval) {
                                    Analytics.log(
                                        AnalyticsEvent(
                                            name = "early_bird_denied_preapproval",
                                            params = mapOf("event_id" to event.id, "title" to event.title)
                                        )
                                    )
                                } else if (earlyBirdExpired) {
                                    Analytics.log(
                                        AnalyticsEvent(
                                            name = "early_bird_expired_click",
                                            params = mapOf("event_id" to event.id, "title" to event.title)
                                        )
                                    )
                                }
                            }
                            GlowCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .pressAnimated(scaleDown = 0.97f)
                                    .clickable(enabled = !disabled) { onSelectOption() }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text(
                                            text = option,
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        val subtitle = when {
                                            earlyBirdOption && earlyBirdWindow != null -> "${earlyBirdWindow.earlyPriceLabel} (base ${earlyBirdWindow.basePriceLabel})"
                                            else -> "Perks: instant QR, transfers, price locks"
                                        }
                                        Text(
                                            text = subtitle,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (disabled) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.55f) else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        if (disabledReason.isNotBlank()) {
                                            Text(
                                                text = disabledReason,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Color(0xFFFFC94A)
                                            )
                                        }
                                    }
                                    RadioButton(
                                        selected = selected,
                                        onClick = { onSelectOption() },
                                        enabled = !disabled
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        GlowCard(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    "Highlights",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    textAlign = TextAlign.Center
                )
                val points = listOf(
                    "Interactive seat map with neon highlights.",
                    "Transparent fees before checkout.",
                    "Transfers and resale supported.",
                    "Add-ons: parking, merch, VIP lounge."
                )
                points.forEach {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                        )
                        Text(
                            it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
        SeatPreview()
        GlowCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            val canCheckout = selectedTicketType != null
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PrimaryButton(
                    text = "Checkout",
                    modifier = Modifier
                        .weight(1f)
                        .graphicsLayer(alpha = if (canCheckout) 1f else 0.45f)
                ) {
                    var type = selectedTicketType
                    if (type == "Early Bird") {
                        // Guardrail: if Early Bird is no longer active, gracefully fall back.
                        if (!earlyBirdActive) {
                            type = "General / Standard"
                            selectedTicketType = type
                            checkoutNotice = "Early Bird window ended, continuing with General tickets."
                            Analytics.log(
                                AnalyticsEvent(
                                    name = "early_bird_auto_switch",
                                    params = mapOf(
                                        "event_id" to event.id,
                                        "title" to event.title,
                                        "to_type" to type
                                    )
                                )
                            )
                        }
                    }
                    if (type != null) {
                        onProceedToCheckout(type)
                    }
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NeonSelectablePill(
                        text = if (priceAlertSelected) "Alert on" else "Price alert",
                        selected = priceAlertSelected,
                        onClick = {
                            val nowSelected = !priceAlertSelected
                            priceAlertSelected = nowSelected
                            if (nowSelected) {
                                onAlert()
                            }
                        },
                        centerText = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                Color.White.copy(alpha = 0.35f),
                                goTickyShapes.medium
                            )
                    )
                    Text(
                        text = "Get notified if this price changes.",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
            checkoutNotice?.let { msg ->
                Text(
                    text = msg,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFFFFC94A),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }
        }
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
                            NeonSelectablePill(
                                text = reason,
                                selected = selected,
                                onClick = { selectedReason = reason },
                                selectedContainerColor = MaterialTheme.colorScheme.error,
                                selectedContentColor = MaterialTheme.colorScheme.onError
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
    userProfile: UserProfile,
    onBack: () -> Unit,
    onSaveDraft: (String, String, String, String, String, String, String) -> Unit,
) {
    var companyName by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var dateLabel by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<String?>(null) }
    var selectedTime by remember { mutableStateOf<String?>(null) }
    var priceFrom by remember { mutableStateOf("") }
    var ticketCount by remember { mutableStateOf("") }
    var ticketGeneral by remember { mutableStateOf("") }
    var ticketVip by remember { mutableStateOf("") }
    var ticketStudent by remember { mutableStateOf("") }
    var ticketEarlyBird by remember { mutableStateOf("") }
    var venue by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Draft") }
    var flyerUrl by remember { mutableStateOf("") }
    var flyerUploading by remember { mutableStateOf(false) }
    var flyerUploaded by remember { mutableStateOf(false) }
    var showDateTimePicker by remember { mutableStateOf(false) }
    val timeSlots: List<String> = remember { generateTimeSlots() }
    val months = listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
    var datePickerMonthIndex by remember { mutableStateOf(0) }
    var datePickerDay by remember { mutableStateOf(7) }
    var datePickerTime by remember { mutableStateOf("6:00 PM") }
    var showErrors by remember { mutableStateOf(false) }
    val flyerImagePicker = rememberImagePicker { uri ->
        flyerUploading = false
        uri?.let {
            flyerUploaded = true
            flyerUrl = it.substringAfterLast('/')
        }
    }

    LaunchedEffect(showDateTimePicker) {
        if (showDateTimePicker) {
            datePickerMonthIndex = months.indexOf(selectedDate?.substringBefore(" ") ?: userProfile.birthday.substringBefore(" ").ifBlank { months.first() }).coerceAtLeast(0)
            datePickerDay = selectedDate?.substringAfter(" ")?.toIntOrNull()
                ?: userProfile.birthday.substringAfter(" ").toIntOrNull()
                ?: 7
            val resolvedTime = selectedTime ?: datePickerTime
            datePickerTime = if (resolvedTime in timeSlots) resolvedTime else timeSlots.first()
        }
    }

    fun parsePrice(input: String): Double? {
        val cleaned = input.filter { it.isDigit() || it == '.' }
        return cleaned.toDoubleOrNull()
    }
    fun resolvedPriceFrom(): String {
        val candidates = listOf(priceFrom, ticketEarlyBird, ticketGeneral, ticketVip, ticketStudent)
            .mapNotNull { parsePrice(it) }
        return (candidates.minOrNull() ?: 0.0).let { if (it == 0.0) "" else it.toString() }
    }
    val primaryPrice = parsePrice(priceFrom)
    val hasAnyPrice = (primaryPrice != null) || resolvedPriceFrom().isNotBlank()
    val completedFields = listOf(
        companyName,
        title,
        dateLabel,
        venue,
        city,
        country,
    ).count { it.isNotBlank() } +
        listOf(ticketGeneral, ticketVip, ticketStudent, ticketEarlyBird).count { it.isNotBlank() } +
        (if (hasAnyPrice) 1 else 0) +
        if (flyerUploaded || flyerUrl.isNotBlank()) 1 else 0
    val progress = (completedFields / 12f).coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(GoTickyGradients.CardGlow)
            .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 120.dp),
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
            val profilePhotoPainter = profilePainter(userProfile)
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    "Organizer details",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(goTickyShapes.large)
                        .background(Color.Black.copy(alpha = 0.25f))
                        .background(GoTickyGradients.GlassWash)
                        .drawBehind { drawRect(GoTickyTextures.GrainTint) }
                        .border(1.dp, GoTickyGradients.EdgeHalo, goTickyShapes.large)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(58.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.35f))
                            .border(1.dp, GoTickyGradients.EdgeHalo, CircleShape)
                    ) {
                        if (profilePhotoPainter != null) {
                            Image(
                                painter = profilePhotoPainter,
                                contentDescription = "Organizer photo",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.AccountCircle,
                                contentDescription = null,
                                tint = IconCategoryColors[IconCategory.Profile] ?: MaterialTheme.colorScheme.primary,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = userProfile.fullName,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = userProfile.email,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${userProfile.phoneCode} ${userProfile.phoneNumber}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf(
                                Icons.Outlined.Cake to userProfile.birthday,
                                Icons.Outlined.Person to userProfile.gender,
                                Icons.Outlined.Flag to userProfile.countryName
                            ).forEach { (icon, value) ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    modifier = Modifier
                                        .clip(goTickyShapes.medium)
                                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f))
                                        .border(1.dp, GoTickyGradients.EdgeHalo, goTickyShapes.medium)
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                    Text(
                                        text = value,
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
                Text(
                    "Organizer details are synced from My Profile. Update there to keep events consistent.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        GlowCard {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    "Basics",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                val currencyTint = MaterialTheme.colorScheme.primary.copy(alpha = 0.82f)
                val priceError = showErrors && !hasAnyPrice
                val dateError = showErrors && dateLabel.isBlank()
                val titleError = showErrors && title.isBlank()
                val venueError = showErrors && venue.isBlank()
                val cityError = showErrors && city.isBlank()
                val countryError = showErrors && country.isBlank()

                OutlinedTextField(
                    value = companyName,
                    onValueChange = { companyName = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Company / organiser name") },
                    placeholder = { Text("e.g. GoTicky Live Studio") },
                    shape = goTickyShapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Event title") },
                    isError = titleError,
                    supportingText = {
                        if (titleError) Text("Title is required", color = MaterialTheme.colorScheme.error)
                    },
                    shape = goTickyShapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = dateLabel,
                        onValueChange = { dateLabel = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Date & time") },
                        placeholder = { Text("Tap to pick date and time") },
                        isError = dateError,
                        supportingText = {
                            if (dateError) Text("Pick a date and time", color = MaterialTheme.colorScheme.error)
                        },
                        trailingIcon = {
                            IconButton(onClick = { showDateTimePicker = true }) {
                                Icon(
                                    imageVector = Icons.Outlined.Event,
                                    contentDescription = "Pick date",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        readOnly = true,
                        shape = goTickyShapes.medium,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clip(goTickyShapes.medium)
                            .clickable { showDateTimePicker = true }
                    )
                }
                OutlinedTextField(
                    value = ticketCount,
                    onValueChange = { input ->
                        val cleaned = input.filter { it.isDigit() }.take(6)
                        ticketCount = cleaned
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Ticket count") },
                    placeholder = { Text("e.g. 500") },
                    suffix = {
                        Text(
                            text = "tickets",
                            modifier = Modifier.padding(start = 6.dp, end = 2.dp),
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                    placeholder = { Text("50") },
                    prefix = {
                        Text(
                            text = "$",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = currencyTint
                        )
                    },
                    isError = priceError,
                    supportingText = {
                        if (priceError) Text("Enter at least one numeric price", color = MaterialTheme.colorScheme.error)
                    },
                    shape = goTickyShapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                Text(
                    "Ticket options",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                OutlinedTextField(
                    value = ticketEarlyBird,
                    onValueChange = { ticketEarlyBird = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Early bird / Promo") },
                    placeholder = { Text("18") },
                    prefix = {
                        Text(
                            text = "$",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = currencyTint
                        )
                    },
                    shape = goTickyShapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                OutlinedTextField(
                    value = ticketGeneral,
                    onValueChange = { ticketGeneral = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("General / Standard") },
                    placeholder = { Text("25") },
                    prefix = {
                        Text(
                            text = "$",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = currencyTint
                        )
                    },
                    shape = goTickyShapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                OutlinedTextField(
                    value = ticketVip,
                    onValueChange = { ticketVip = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("VIP / Lounge") },
                    placeholder = { Text("60") },
                    prefix = {
                        Text(
                            text = "$",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = currencyTint
                        )
                    },
                    shape = goTickyShapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                OutlinedTextField(
                    value = ticketStudent,
                    onValueChange = { ticketStudent = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Golden Circle") },
                    placeholder = { Text("120") },
                    prefix = {
                        Text(
                            text = "$",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = currencyTint
                        )
                    },
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
                    isError = venueError,
                    supportingText = {
                        if (venueError) Text("Venue is required", color = MaterialTheme.colorScheme.error)
                    },
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
                    isError = cityError,
                    supportingText = {
                        if (cityError) Text("City is required", color = MaterialTheme.colorScheme.error)
                    },
                    shape = goTickyShapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                OutlinedTextField(
                    value = country,
                    onValueChange = { country = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Country") },
                    placeholder = { Text("Where will this event be hosted?") },
                    isError = countryError,
                    supportingText = {
                        if (countryError) Text("Country is required", color = MaterialTheme.colorScheme.error)
                    },
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
                    ) {
                        flyerUploading = true
                        flyerImagePicker.pickFromGallery()
                    }
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
                    onReplace = {
                        flyerUploading = true
                        flyerImagePicker.pickFromGallery()
                    },
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
                        NeonSelectablePill(
                            text = option,
                            selected = selected,
                            onClick = { status = option }
                        )
                    }
                }
                val primaryLabel = if (status == "Live") "Submit application" else "Save draft"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
                ) {
                    GhostButton(
                        text = "Cancel",
                        modifier = Modifier.weight(1f)
                    ) { onBack() }
                    PrimaryButton(
                        text = primaryLabel,
                        modifier = Modifier.weight(1f)
                    ) {
                        val valid = title.isNotBlank() &&
                            dateLabel.isNotBlank() &&
                            venue.isNotBlank() &&
                            city.isNotBlank() &&
                            country.isNotBlank() &&
                            parsePrice(priceFrom) != null &&
                            ticketCount.isNotBlank()
                        if (!valid) {
                            showErrors = true
                        } else {
                            val normalizedPrice = if (priceFrom.isBlank()) resolvedPriceFrom() else priceFrom
                            val normalizedTickets = ticketCount.ifBlank { "0" }
                            onSaveDraft(
                                title.trim(),
                                city.trim(),
                                venue.trim(),
                                dateLabel.ifBlank { "${selectedDate ?: ""} ${selectedTime ?: ""}".trim() },
                                normalizedPrice.ifBlank { "0" },
                                status,
                                normalizedTickets
                            )
                            showErrors = false
                        }
                    }
                }
            }
        }
        AnimatedProgressBar(progress = progress, modifier = Modifier.fillMaxWidth())
    }

    if (showDateTimePicker) {
        AlertDialog(
            onDismissRequest = { showDateTimePicker = false },
            title = { Text("Pick date & time") },
            text = {
                var visible by remember { mutableStateOf(false) }
                val scale by animateFloatAsState(
                    targetValue = if (visible) 1f else 0.94f,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
                    label = "dateTimeDialogScale"
                )
                LaunchedEffect(Unit) { visible = true }

                Column(
                    modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        NeonTextButton(
                            text = "<",
                            onClick = {
                                datePickerMonthIndex = if (datePickerMonthIndex == 0) months.lastIndex else datePickerMonthIndex - 1
                            }
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = months[datePickerMonthIndex],
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Tap a day, then choose a time",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        NeonTextButton(
                            text = ">",
                            onClick = {
                                datePickerMonthIndex = if (datePickerMonthIndex == months.lastIndex) 0 else datePickerMonthIndex + 1
                            }
                        )
                    }

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        (1..30).forEach { d ->
                            val selected = d == datePickerDay
                            NeonSelectablePill(
                                text = d.toString(),
                                selected = selected,
                                onClick = { datePickerDay = d }
                            )
                        }
                    }

                    Text(
                        "Time",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    val timeSlotHeight = 46.dp
                    val timeWheelState = rememberLazyListState(
                        initialFirstVisibleItemIndex = timeSlots.indexOf(datePickerTime).coerceAtLeast(0)
                    )
                    LaunchedEffect(showDateTimePicker) {
                        if (showDateTimePicker) {
                            val target = timeSlots.indexOf(datePickerTime).coerceAtLeast(0)
                            timeWheelState.scrollToItem(target)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(timeSlotHeight * 5)
                            .clip(goTickyShapes.large)
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                            .border(1.dp, GoTickyGradients.EdgeHalo, goTickyShapes.large)
                            .drawBehind { drawRect(GoTickyTextures.GrainTint) }
                    ) {
                        LazyColumn(
                            state = timeWheelState,
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = timeSlotHeight * 2),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            itemsIndexed(timeSlots) { _: Int, slot: String ->
                                val selected = datePickerTime == slot
                                val tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(timeSlotHeight)
                                        .clip(goTickyShapes.medium)
                                        .clickable { datePickerTime = slot }
                                        .padding(horizontal = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = slot,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                        color = tint
                                    )
                                    if (selected) {
                                        Icon(
                                            imageVector = Icons.Outlined.CheckCircle,
                                            contentDescription = null,
                                            tint = tint
                                        )
                                    }
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(
                                    brush = Brush.verticalGradient(
                                        0f to MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.75f),
                                        0.2f to Color.Transparent,
                                        0.8f to Color.Transparent,
                                        1f to MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.75f)
                                    )
                                )
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxWidth()
                                .height(timeSlotHeight + 6.dp)
                                .clip(goTickyShapes.medium)
                                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.45f), goTickyShapes.medium)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f))
                        )
                    }
                }
            },
            confirmButton = {
                PrimaryButton(text = "Apply") {
                    val month = months[datePickerMonthIndex]
                    val date = "$month $datePickerDay"
                    selectedDate = date
                    val timeLabel = datePickerTime
                    selectedTime = timeLabel
                    dateLabel = "$date at $timeLabel"
                    showDateTimePicker = false
                }
            },
            dismissButton = {
                NeonTextButton(
                    text = "Cancel",
                    onClick = { showDateTimePicker = false }
                )
            }
        )
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
            .height(144.dp)
            .graphicsLayer(clip = false)
    ) {
        val slotWidth = maxWidth / navItems.size

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Dark misty panel behind icons to obstruct content slightly.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(132.dp)
                    .graphicsLayer(alpha = chromeAlpha, clip = false)
                    .padding(horizontal = 10.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.Black.copy(alpha = 0.75f))
                    .blur(34.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer(alpha = chromeAlpha, clip = false)
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                navItems.forEach { item ->
                    val selected = item.screen == current
                    val highlight = if (item.category == IconCategory.Ticket) {
                        Color.White
                    } else {
                        IconCategoryColors[item.category] ?: MaterialTheme.colorScheme.primary
                    }
                    val interactionSource = remember { MutableInteractionSource() }
                    val pressed by interactionSource.collectIsPressedAsState()
                    val selectedScale by animateFloatAsState(
                        targetValue = if (selected) 1.3f else 0.85f,
                        animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
                        label = "navSelectedScale-${item.label}"
                    )
                    val pressScale by animateFloatAsState(
                        targetValue = if (pressed) 0.94f else 1f,
                        animationSpec = tween(durationMillis = GoTickyMotion.Quick, easing = EaseOutBack),
                        label = "navPressScale-${item.label}"
                    )
                    val selectedOffsetY by animateDpAsState(
                        targetValue = 0.dp,
                        animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
                        label = "navSelectedOffset-${item.label}"
                    )
                    val labelAlpha by animateFloatAsState(
                        targetValue = if (selected) 1f else 0.88f,
                        animationSpec = tween(durationMillis = GoTickyMotion.Standard),
                        label = "navLabel-${item.label}"
                    )
                    val borderAlpha by animateFloatAsState(
                        targetValue = if (selected) 0.9f else 0.45f,
                        animationSpec = tween(durationMillis = GoTickyMotion.Standard),
                        label = "navChipBorder-${item.label}"
                    )
                    val borderWidth by animateDpAsState(
                        targetValue = if (selected) 1.dp else 0.5.dp,
                        animationSpec = tween(durationMillis = GoTickyMotion.Standard),
                        label = "navChipBorderWidth-${item.label}"
                    )
                    val chipTopColor by animateColorAsState(
                        targetValue = if (selected) highlight else Color.Black.copy(alpha = 1f),
                        animationSpec = tween(durationMillis = GoTickyMotion.Standard),
                        label = "navChipTop-${item.label}"
                    )
                    val chipBottomColor by animateColorAsState(
                        targetValue = if (selected) highlight else Color.Black.copy(alpha = 0.9f),
                        animationSpec = tween(durationMillis = GoTickyMotion.Standard),
                        label = "navChipBottom-${item.label}"
                    )
                    val iconTint by animateColorAsState(
                        targetValue = if (selected) {
                            Color.Black
                        } else {
                            when {
                                item.category == IconCategory.Ticket -> Color.White
                                else -> highlight
                            }
                        },
                        animationSpec = tween(durationMillis = GoTickyMotion.Standard),
                        label = "navIconTint-${item.label}"
                    )

                    Box(
                        modifier = Modifier
                            .width(slotWidth)
                            .heightIn(min = 86.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val itemSpacing = if (selected) 14.dp else 8.dp
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = selectedOffsetY)
                                .padding(top = 12.dp, bottom = 14.dp)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) { onSelected(item.screen) },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(itemSpacing)
                        ) {
                            val labelNeonColor = highlight.copy(alpha = if (selected) 1f else 0.8f)
                            val labelGlowColor = highlight.copy(alpha = if (selected) 0.95f else 0.7f)
                            val labelStyle = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                                shadow = Shadow(
                                    color = labelGlowColor,
                                    offset = Offset(0f, 2f),
                                    blurRadius = 10f
                                )
                            )
                            Box(
                                modifier = Modifier
                                    .size(if (selected) 56.dp else 48.dp)
                                    .graphicsLayer(
                                        scaleX = selectedScale * pressScale,
                                        scaleY = selectedScale * pressScale
                                    )
                                    .drawBehind {
                                        val mistStrength = if (selected) 1f else 0.75f
                                        val radius = size.minDimension * 0.95f
                                        val haloBrush = if (selected) {
                                            Brush.radialGradient(
                                                colors = listOf(
                                                    Color.White.copy(alpha = mistStrength * 0.55f),
                                                    highlight.copy(alpha = mistStrength * 0.28f),
                                                    Color.Transparent
                                                ),
                                                center = center,
                                                radius = radius
                                            )
                                        } else {
                                            Brush.radialGradient(
                                                colors = listOf(
                                                    Color.Black.copy(alpha = mistStrength * 0.85f),
                                                    Color.Black.copy(alpha = mistStrength * 0.5f),
                                                    Color.Transparent
                                                ),
                                                center = center,
                                                radius = radius
                                            )
                                        }
                                        drawCircle(brush = haloBrush, radius = radius)
                                    }
                                    .shadow(
                                        elevation = if (selected) 14.dp else 8.dp,
                                        shape = CircleShape,
                                        ambientColor = highlight.copy(alpha = 0.35f),
                                        spotColor = highlight.copy(alpha = 0.6f)
                                    )
                                    .clip(CircleShape)
                                    .background(
                                        Brush.radialGradient(
                                            colors = listOf(
                                                chipTopColor,
                                                chipBottomColor,
                                                Color.Black.copy(alpha = 0.2f)
                                            )
                                        )
                                    )
                                    .border(
                                        width = borderWidth,
                                        color = highlight.copy(alpha = borderAlpha),
                                        shape = CircleShape
                                    )
                                    .padding(10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CompositionLocalProvider(LocalContentColor provides iconTint) {
                                    item.icon()
                                }
                            }
                            Text(
                                text = item.label,
                                color = labelNeonColor.copy(alpha = labelAlpha),
                                style = labelStyle
                            )
                        }
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
