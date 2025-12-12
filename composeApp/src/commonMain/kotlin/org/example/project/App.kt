package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.HelpOutline
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
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.util.lerp
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.compose.ui.window.Dialog
import org.example.project.data.TicketPass
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
import org.example.project.platform.rememberImagePicker
import org.example.project.platform.rememberUriPainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.Job
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
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

private enum class MainScreen {
    Home, Browse, Tickets, Alerts, Profile, Organizer, Map, PrivacyTerms, FAQ, Settings
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
)
private data class MenuItemSpec(
    val label: String,
    val icon: ImageVector,
    val tint: Color,
    val trailing: (@Composable (() -> Unit))? = null,
    val onClick: () -> Unit,
)

private data class UserProfile(
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
            it.photoUri ?: ""
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
            photoResKey = (it[8] as String).ifBlank { null },
            photoUri = (it[9] as String).ifBlank { null },
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

private val heroSlides = listOf(
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

private fun resolveProfilePhotoRes() =
    Res.allDrawableResources["gotickypic"]
        ?: Res.allDrawableResources["hero_vic_falls_midnight_lights"]

private fun defaultUserProfile() = UserProfile(
    fullName = "Walter Banda",
    email = "guest@goticky.app",
    countryName = "Zimbabwe",
    countryFlag = "🇿🇼",
    phoneCode = "+263",
    phoneNumber = "771234567",
    birthday = "1992-06-14",
    gender = "Prefer not to say",
    photoResKey = "gotickypic",
    photoUri = null
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
        val genres = it[0] as List<String>
        val city = it[1] as String
        PersonalizationPrefs(genres, city)
    }
)

private data class SettingsPrefs(
    val pushEnabled: Boolean = true,
    val emailUpdates: Boolean = true,
    val dataSaver: Boolean = false,
    val hapticsEnabled: Boolean = true,
    val theme: String = "Auto", // Auto | Light | Dark
)

private val SettingsPrefsSaver = listSaver<SettingsPrefs, Any>(
    save = { listOf(it.pushEnabled, it.emailUpdates, it.dataSaver, it.hapticsEnabled, it.theme) },
    restore = {
        SettingsPrefs(
            pushEnabled = it[0] as Boolean,
            emailUpdates = it[1] as Boolean,
            dataSaver = it[2] as Boolean,
            hapticsEnabled = it[3] as Boolean,
            theme = it[4] as String
        )
    }
)

private val SearchHistorySaver = listSaver<SnapshotStateList<String>, String>(
    save = { stateList -> stateList.toList() },
    restore = { saved -> mutableStateListOf(*saved.toTypedArray()) }
)

@Composable
fun App() {
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
    var selectedTicket by remember { mutableStateOf<TicketPass?>(null) }
    var showCheckout by remember { mutableStateOf(false) }
    var selectedTicketType by remember { mutableStateOf("General / Standard") }
    var userProfile by rememberSaveable(stateSaver = UserProfileSaver) { mutableStateOf(defaultUserProfile()) }
    val favoriteEvents = rememberSaveable { mutableStateListOf<String>() }
    fun toggleFavorite(eventId: String) {
        if (favoriteEvents.contains(eventId)) favoriteEvents.remove(eventId) else favoriteEvents.add(eventId)
    }
    val alerts = remember { mutableStateListOf<PriceAlert>(*sampleAlerts.toTypedArray()) }
    val recommendations = remember { sampleRecommendations }
    val organizerEvents = remember { mutableStateListOf<OrganizerEvent>(*sampleOrganizerEvents.toTypedArray()) }
    var showCreateEvent by remember { mutableStateOf(false) }
    var selectedOrganizerEvent by remember { mutableStateOf<OrganizerEvent?>(null) }
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
        mutableStateOf(PersonalizationPrefs(genres = listOf("Concerts", "Sports", "Comedy", "Family"), city = "Harare"))
    }
    val searchHistory = rememberSaveable(saver = SearchHistorySaver) { mutableStateListOf<String>() }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var forceOpenSearchDialog by remember { mutableStateOf(false) }
    var settingsPrefs by rememberSaveable(stateSaver = SettingsPrefsSaver) { mutableStateOf(SettingsPrefs()) }

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
            NavItem(MainScreen.Home, "Home", { Icon(Icons.Outlined.Home, null) }, IconCategory.Discover),
            NavItem(MainScreen.Browse, "Browse", { Icon(Icons.Outlined.Event, null) }, IconCategory.Calendar),
            NavItem(MainScreen.Tickets, "My Tickets", { Icon(Icons.Outlined.ReceiptLong, null) }, IconCategory.Ticket),
            NavItem(MainScreen.Alerts, "Alerts", { Icon(Icons.Outlined.Notifications, null) }, IconCategory.Alerts),
            NavItem(MainScreen.Profile, "Profile", { Icon(Icons.Outlined.AccountCircle, null) }, IconCategory.Profile),
        )
    }

    val showChromeOnScreen = currentScreen in setOf(
        MainScreen.Home,
        MainScreen.Browse,
        MainScreen.Tickets,
        MainScreen.Alerts,
        MainScreen.Profile,
    )
    val showRootChrome = showChromeOnScreen &&
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
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
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
        },
    ) { inner ->

        val layoutDirection = LocalLayoutDirection.current
        val density = LocalDensity.current
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
                    onPlaceOrder = { paymentMethod, totalAmount ->
                        if (GoTickyFeatures.EnableRealPayments) {
                            // TODO: integrate real payment processor; for now keep mock success flow for demos.
                        }
                        Analytics.log(
                            AnalyticsEvent(
                                name = "checkout_success",
                                params = mapOf(
                                    "order_id" to sampleOrder.id,
                                    "payment_method" to paymentMethod,
                                    "total_amount" to totalAmount.toString()
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
                    isFavorite = favoriteEvents.contains(detailEvent!!.id),
                    onToggleFavorite = { detailEvent?.let { toggleFavorite(it.id) } },
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
                        userProfile = userProfile,
                        onOpenAlerts = { currentScreen = MainScreen.Profile },
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
                        recommendations = personalize(recommendations),
                        onOpenMap = { currentScreen = MainScreen.Map },
                        searchQuery = searchQuery,
                        onSearchQueryChange = { searchQuery = it },
                        forceOpenSearchDialog = forceOpenSearchDialog,
                        onConsumeForceOpenSearchDialog = { forceOpenSearchDialog = false },
                        onSearchExecuted = { query -> recordSearch(query) },
                        favoriteEvents = favoriteEvents,
                        onToggleFavorite = { id -> toggleFavorite(id) }
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
                        userProfile = userProfile,
                        onUpdateProfile = { updated -> userProfile = updated },
                        checklistState = checklistState,
                        onToggleCheck = { id -> checklistState[id] = !(checklistState[id] ?: false) },
                        favorites = favoriteEvents,
                        onToggleFavorite = { id -> toggleFavorite(id) },
                        onOpenEvent = { event ->
                            detailEvent = event
                        },
                        onOpenOrganizer = {
                            Analytics.log(
                                AnalyticsEvent(
                                    name = "organizer_open",
                                    params = emptyMap()
                                )
                            )
                            currentScreen = MainScreen.Organizer
                        },
                        onGoHome = { currentScreen = MainScreen.Home },
                        onOpenPrivacyTerms = { currentScreen = MainScreen.PrivacyTerms },
                        onOpenFaq = { currentScreen = MainScreen.FAQ },
                        searchHistory = searchHistory,
                        onClearSearchHistory = { searchHistory.clear() },
                        onSelectHistoryQuery = { query ->
                            searchQuery = query
                            forceOpenSearchDialog = true
                            currentScreen = MainScreen.Home
                        },
                        onOpenSettings = { currentScreen = MainScreen.Settings }
                    )
                    MainScreen.Organizer -> when {
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
                    MainScreen.PrivacyTerms -> LegalScreen(
                        onBack = { currentScreen = MainScreen.Profile }
                    )
                    MainScreen.FAQ -> FaqScreen(
                        onBack = { currentScreen = MainScreen.Profile }
                    )
                    MainScreen.Settings -> SettingsScreen(
                        prefs = settingsPrefs,
                        onPrefsChange = { settingsPrefs = it },
                        onBack = { currentScreen = MainScreen.Profile },
                        onOpenPrivacyTerms = { currentScreen = MainScreen.PrivacyTerms },
                        onOpenFaq = { currentScreen = MainScreen.FAQ }
                    )
                    MainScreen.Map -> EventMapScreen(
                        onBack = { currentScreen = MainScreen.Home },
                        onOpenEvent = { eventId -> openEventById(eventId) }
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeScreen(
    userProfile: UserProfile,
    onOpenAlerts: () -> Unit,
    onEventSelected: (org.example.project.data.EventItem) -> Unit,
    recommendations: List<Recommendation>,
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
                                text = "Walter",
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
                    IconCategory.Ticket -> sampleEvents.firstOrNull()?.let { onEventSelected(it) }
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
                val event = sampleEvents.firstOrNull()
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

                val event = sampleEvents.firstOrNull { it.id == rec.eventId }
                event?.let { onEventSelected(it) }
            }
        )
        MapPreview(onOpenMap = onOpenMap)
        SectionHeader(
            title = "Popular near you",
            action = { NeonTextButton(text = "See all", onClick = { /* TODO */ }) }
        )
        val nearbyByEventId = sampleNearbyEvents.associateBy { it.event.id }
        val popularNearby = sampleEvents.mapNotNull { event ->
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
                            items(sampleEvents) { event ->
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

                                        Row(
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
                                                Row(
                                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Pill(
                                                        text = event.dateLabel,
                                                        color = tint.copy(alpha = 0.22f),
                                                        textColor = tint
                                                    )
                                                    Pill(
                                                        text = event.priceFrom,
                                                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f),
                                                        textColor = MaterialTheme.colorScheme.primary
                                                    )
                                                }
                                            }
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
                        items(sampleEvents) { event ->
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

                val monthEvents = sampleEvents.filter { it.month.equals(activeMonth, ignoreCase = true) }

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

                val filtered = sampleEvents.filter {
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
                                    EventCard(
                                        item = event,
                                        modifier = Modifier.pressAnimated(scaleDown = 0.96f),
                                        isFavorite = favoriteEvents.contains(event.id),
                                        onToggleFavorite = { onToggleFavorite(event.id) },
                                        onClick = {
                                            onEventSelected(event)
                                            showQueryDialog = false
                                        }
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
                    "Concerts" -> sampleEvents.filter {
                        it.category == IconCategory.Discover ||
                            (it.tag?.contains("EDM", ignoreCase = true) == true)
                    }
                    "Sports" -> sampleEvents.filter {
                        it.category == IconCategory.Calendar ||
                            (it.tag?.contains("Basketball", ignoreCase = true) == true)
                    }
                    "Family" -> sampleEvents.filter {
                        it.badge?.contains("Family", ignoreCase = true) == true ||
                            it.category == IconCategory.Profile
                    }
                    else -> sampleEvents
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

                val tonightEvents = sampleEvents.filter {
                    it.dateLabel.contains("Tonight", ignoreCase = true) || it.badge?.contains("Hot", ignoreCase = true) == true
                }.ifEmpty { sampleEvents }

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
    modifier: Modifier = Modifier,
) {
    if (slides.isEmpty()) return

    val listState = rememberLazyListState()
    val density = LocalDensity.current

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
                        HeroCard(slide = slide, onCta = { onCta(slide) })
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
    }
}

@Composable
private fun HeroCard(slide: HeroSlide, onCta: () -> Unit) {
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
                                accent.copy(alpha = 0.22f),
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                            )
                        )
                    )
            )
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
    onOpenEvent: (EventItem) -> Unit,
    onOpenOrganizer: () -> Unit,
    onGoHome: () -> Unit,
    onOpenPrivacyTerms: () -> Unit,
    onOpenFaq: () -> Unit,
    searchHistory: List<String>,
    onClearSearchHistory: () -> Unit,
    onSelectHistoryQuery: (String) -> Unit,
    onOpenSettings: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val headerPulse = infinitePulseAmplitude(
        minScale = 0.99f,
        maxScale = 1.01f,
        durationMillis = 3600,
    )
    val userName = userProfile.fullName
    val userEmail = userProfile.email
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
        profilePhotoRes?.let { resId ->
            Image(
                painter = painterResource(resId),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
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
                    Pill(
                        text = "GoTicky demo profile",
                        color = Color.Black.copy(alpha = 0.4f),
                        textColor = MaterialTheme.colorScheme.onSurface
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
                onClick = { }
            )
        }
    }

    if (showProfileDetails) {
        ProfileDetailsDialog(
            profile = userProfile,
            onUpdateProfile = onUpdateProfile,
            onDismiss = { showProfileDetails = false }
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

                val favoriteItems = sampleEvents.filter { favorites.contains(it.id) }

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
                                    }
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
) {
    val clipboard = LocalClipboardManager.current
    val uriHandler = LocalUriHandler.current
    var showCameraDialog by remember { mutableStateOf(false) }
    val pulse = infinitePulseAmplitude(minScale = 0.98f, maxScale = 1.02f, durationMillis = 2200)
    val photo = profilePainter(profile)
    data class InfoItem(
        val label: String,
        val value: String,
        val icon: ImageVector,
        val tint: Color,
        val editable: Boolean = false,
    )
    val infoItems = listOf(
        InfoItem("Email", profile.email, Icons.Outlined.Email, Color(0xFF5CF0FF), editable = true),
        InfoItem("Phone", "${profile.phoneCode} ${profile.phoneNumber}", Icons.Outlined.PhoneAndroid, Color(0xFF9C7BFF), editable = true),
        InfoItem("Birthday", profile.birthday, Icons.Outlined.Cake, Color(0xFF4CAF50)),
        InfoItem("Gender", profile.gender, Icons.Outlined.Person, Color(0xFFFF8A65)),
        InfoItem("Country", "${profile.countryFlag} ${profile.countryName}", Icons.Outlined.Flag, Color(0xFFFFC94A)),
    )
    val imagePicker = rememberImagePicker { uri ->
        uri?.let {
            onUpdateProfile(profile.copy(photoUri = it, photoResKey = null))
        }
        showCameraDialog = false
    }
    Dialog(onDismissRequest = { showCameraDialog = false; onDismiss() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(min = 360.dp, max = 640.dp)
                .padding(24.dp)
                .clip(goTickyShapes.extraLarge)
                .background(GoTickyGradients.CardGlow)
                .background(GoTickyGradients.GlassWash)
                .drawBehind { drawRect(GoTickyTextures.GrainTint) }
                .border(1.dp, GoTickyGradients.EdgeHalo, goTickyShapes.extraLarge)
                .padding(18.dp)
        ) {
            // Close icon top-right
            val closeInteraction = remember { MutableInteractionSource() }
            val closePressed by closeInteraction.collectIsPressedAsState()
            val closeScale by animateFloatAsState(
                targetValue = if (closePressed) 0.9f else 1f,
                animationSpec = tween(GoTickyMotion.Standard),
                label = "profileCloseScale"
            )
            IconButton(
                onClick = { showCameraDialog = false; onDismiss() },
                modifier = Modifier
                    .align(Alignment.TopEnd)
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

            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer(scaleX = pulse, scaleY = pulse),
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
                                .size(74.dp)
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
                                        .size(70.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Outlined.AccountCircle,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                    modifier = Modifier.size(46.dp)
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
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = profile.fullName,
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
                            text = profile.email,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Pill(
                            text = "GoTicky demo profile • personalized",
                            color = Color.Black.copy(alpha = 0.3f),
                            textColor = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    infoItems.forEachIndexed { index, entry ->
                        val (label, value, icon, tint, editable) = entry
                        val interaction = remember { MutableInteractionSource() }
                        val pressed by interaction.collectIsPressedAsState()
                        val pressScale by animateFloatAsState(
                            targetValue = if (pressed) 0.97f else 1f,
                            animationSpec = tween(GoTickyMotion.Standard),
                            label = "profileField-$index"
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .graphicsLayer(scaleX = pressScale, scaleY = pressScale)
                                .clip(goTickyShapes.large)
                                .background(Color.Black.copy(alpha = 0.35f))
                                .background(GoTickyGradients.GlassWash)
                                .drawBehind { drawRect(GoTickyTextures.GrainTint) }
                                .border(1.dp, Brush.horizontalGradient(listOf(tint, Color.Transparent)), goTickyShapes.large)
                                .clickable(
                                    interactionSource = interaction,
                                    indication = null
                                ) {
                                    if (label == "Email") clipboard.setText(AnnotatedString(value))
                                    if (label == "Phone") clipboard.setText(AnnotatedString(value))
                                }
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(tint.copy(alpha = 0.22f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = tint
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = tint
                                )
                                Text(
                                    text = value,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            if (editable) {
                                val editInteraction = remember { MutableInteractionSource() }
                                val editPressed by editInteraction.collectIsPressedAsState()
                                val editScale by animateFloatAsState(
                                    targetValue = if (editPressed) 0.92f else 1f,
                                    animationSpec = tween(GoTickyMotion.Quick),
                                    label = "profileEditScale-$label"
                                )
                                Icon(
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = "Edit $label",
                                    modifier = Modifier
                                        .size(22.dp)
                                        .graphicsLayer(scaleX = editScale, scaleY = editScale)
                                        .clickable(
                                            interactionSource = editInteraction,
                                            indication = null
                                        ) {
                                            clipboard.setText(AnnotatedString(value))
                                        },
                                    tint = tint
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    PrimaryButton(
                        text = "Save",
                        modifier = Modifier.weight(1f),
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Save,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    ) {
                        // Stub: plug into profile persistence when backend is ready
                    }
                    NeonTextButton(
                        text = "Share profile",
                        modifier = Modifier.weight(1f),
                        onClick = {
                            clipboard.setText(
                                AnnotatedString("${profile.fullName} • ${profile.email} • ${profile.countryFlag}")
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Share,
                                contentDescription = null,
                                tint = Color(0xFF5CF0FF)
                            )
                        }
                    )
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
private fun EventDetailScreen(
    event: org.example.project.data.EventItem,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onBack: () -> Unit,
    onProceedToCheckout: (String) -> Unit,
    onAlert: () -> Unit
) {
    var showReport by remember { mutableStateOf(false) }
    var priceAlertSelected by remember { mutableStateOf(false) }
    var selectedReason by remember { mutableStateOf("Wrong information") }
    val ticketOptions = listOf("Early Bird", "General / Standard", "VIP", "Golden Circle")
    var selectedTicketType by remember { mutableStateOf<String?>(null) }
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
                Text(
                    text = "Ticket options",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    ticketOptions.forEach { option ->
                        val selected = selectedTicketType == option

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
                            GlowCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .pressAnimated(scaleDown = 0.97f)
                                    .clickable { selectedTicketType = option }
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
                                        Text(
                                            text = "Perks: instant QR, transfers, price locks",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    RadioButton(
                                        selected = selected,
                                        onClick = { selectedTicketType = option }
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
                    val type = selectedTicketType
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
    onSaveDraft: (String, String, String, String, String, String) -> Unit,
) {
    var companyName by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var dateLabel by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<String?>(null) }
    var selectedTime by remember { mutableStateOf<String?>(null) }
    var priceFrom by remember { mutableStateOf("") }
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
                            parsePrice(priceFrom) != null
                        if (!valid) {
                            showErrors = true
                        } else {
                            val normalizedPrice = if (priceFrom.isBlank()) resolvedPriceFrom() else priceFrom
                            onSaveDraft(
                                title.trim(),
                                city.trim(),
                                venue.trim(),
                                dateLabel.ifBlank { "${selectedDate ?: ""} ${selectedTime ?: ""}".trim() },
                                normalizedPrice.ifBlank { "0" },
                                status
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
                    targetValue = if (selected) (-8).dp else 0.dp,
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
                    targetValue = if (selected) highlight else MaterialTheme.colorScheme.surfaceVariant,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard),
                    label = "navChipTop-${item.label}"
                )
                val chipBottomColor by animateColorAsState(
                    targetValue = if (selected) highlight else MaterialTheme.colorScheme.surface,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard),
                    label = "navChipBottom-${item.label}"
                )
                val iconTint by animateColorAsState(
                    targetValue = if (selected) MaterialTheme.colorScheme.onPrimary else highlight,
                    animationSpec = tween(durationMillis = GoTickyMotion.Standard),
                    label = "navIconTint-${item.label}"
                )

                Box(
                    modifier = Modifier.width(slotWidth),
                    contentAlignment = Alignment.Center
                ) {
                    val mistStrength = if (selected) 0.9f else 0.55f
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .drawBehind {
                                val radius = size.minDimension
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
                            .offset(y = selectedOffsetY)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) { onSelected(item.screen) },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val chipBrush = Brush.linearGradient(
                            colors = listOf(
                                chipTopColor,
                                chipBottomColor
                            )
                        )
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
                                .size(46.dp)
                                .graphicsLayer(
                                    scaleX = selectedScale * pressScale,
                                    scaleY = selectedScale * pressScale
                                )
                                .shadow(
                                    elevation = if (selected) 18.dp else 8.dp,
                                    shape = CircleShape,
                                    ambientColor = highlight.copy(alpha = 0.4f),
                                    spotColor = highlight.copy(alpha = 0.9f)
                                )
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