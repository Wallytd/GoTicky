package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.platform.rememberImagePicker
import org.example.project.platform.rememberProfileImageStorage
import org.example.project.platform.rememberUriPainter
import org.example.project.ui.components.AnimatedProgressBar
import org.example.project.ui.components.GoTickyMotion
import org.example.project.ui.components.LoadingRow
import org.example.project.ui.components.LoadingSpinner
import org.example.project.ui.components.NeonTextButton
import org.example.project.ui.components.PrimaryButton
import org.example.project.ui.components.pressAnimated
import org.example.project.ui.theme.GoTickyGradients
import org.example.project.ui.theme.IconCategory
import org.example.project.ui.theme.IconCategoryColors
import org.example.project.ui.theme.goTickyShapes
import goticky.composeapp.generated.resources.Res
import goticky.composeapp.generated.resources.allDrawableResources
import org.example.project.ui.components.GlowCard
import org.example.project.ui.components.Pill
import org.jetbrains.compose.resources.painterResource
import kotlin.random.Random

data class CountryOption(
    val name: String,
    val flag: String,
    val phoneCode: String
)

@Composable
fun IntroScreen(
    modifier: Modifier = Modifier,
    onContinue: () -> Unit,
) {
    val introLogoRes = remember { Res.allDrawableResources["intro_logo"] }
    val introBackgroundRes = remember { Res.allDrawableResources["intro_background"] }
    val introBackgroundPainter: Painter? = introBackgroundRes?.let { painterResource(it) }

    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val brandLetters = remember { listOf("G", "o", "T", "i", "c", "k", "y") }
        val density = LocalDensity.current
        val widthPx = with(density) { maxWidth.toPx().coerceAtLeast(1f) }
        val heightPx = with(density) { maxHeight.toPx().coerceAtLeast(1f) }
        val offscreenX = widthPx * 0.35f + 80f
        val offscreenY = heightPx * 0.35f + 80f

        fun randomOffscreenOffset(): Pair<Float, Float> {
            val side = Random.nextInt(0, 4)
            return when (side) {
                // Left of screen
                0 -> Pair(
                    Random.nextDouble(-offscreenX * 1.4, -offscreenX * 0.6).toFloat(),
                    Random.nextDouble(-heightPx * 0.25, heightPx * 0.25).toFloat()
                )
                // Right of screen
                1 -> Pair(
                    Random.nextDouble(widthPx + offscreenX * 0.6, widthPx + offscreenX * 1.4).toFloat(),
                    Random.nextDouble(-heightPx * 0.25, heightPx * 0.25).toFloat()
                )
                // Above screen
                2 -> Pair(
                    Random.nextDouble(-widthPx * 0.25, widthPx * 0.25).toFloat(),
                    Random.nextDouble(-offscreenY * 1.2, -offscreenY * 0.6).toFloat()
                )
                // Below screen
                else -> Pair(
                    Random.nextDouble(-widthPx * 0.25, widthPx * 0.25).toFloat(),
                    Random.nextDouble(heightPx + offscreenY * 0.6, heightPx + offscreenY * 1.2).toFloat()
                )
            }
        }

        val letterOffsetsPx = remember(maxWidth, maxHeight, density) {
            List(brandLetters.size) { randomOffscreenOffset() }
        }
        val letterAnimX = remember(letterOffsetsPx) { letterOffsetsPx.map { Animatable(it.first) } }
        val letterAnimY = remember(letterOffsetsPx) { letterOffsetsPx.map { Animatable(it.second) } }
        val taglineLeftOffset = remember { Animatable(-180f) }
        val taglineRightOffset = remember { Animatable(180f) }
        val descriptionAlpha = remember { Animatable(0f) }
        val glowIntensity = remember { Animatable(0f) }
        val dissolveAlpha = remember { Animatable(1f) }

        // Timings (ms)
        val titleDuration = 5_000
        val taglineStart = 4_000L // when letters are mostly gathered
        val taglineDuration = 1_800
        val descriptionStart = 6_000L
        val descriptionDuration = 2_000
        val glowStart = 8_100L
        val glowDuration = 900
        // Dissolve is aligned with glow so the sequence ends when the glow finishes.
        val dissolveStart = glowStart
        val dissolveDuration = glowDuration
        // Cut total duration so the whole intro finishes when the glow ends.
        val totalDuration = glowStart + glowDuration

        LaunchedEffect(Unit) {
            // Title letters gather for ~5s from scattered offsets with random delays so they're not in sync.
            letterAnimX.forEachIndexed { index, anim ->
                val startDelay = Random.nextInt(0, 900)
                val duration = Random.nextInt((titleDuration * 0.65).toInt(), titleDuration + 1200)
                launch {
                    delay(startDelay.toLong())
                    anim.animateTo(0f, animationSpec = tween(durationMillis = duration, easing = EaseOutBack))
                }
                launch {
                    delay(startDelay.toLong())
                    letterAnimY[index].animateTo(0f, animationSpec = tween(durationMillis = duration, easing = EaseOutBack))
                }
            }

            // Tagline slides from left/right starting at taglineStart.
            launch {
                delay(taglineStart)
                taglineLeftOffset.animateTo(0f, animationSpec = tween(durationMillis = taglineDuration, easing = EaseOutBack))
            }
            launch {
                delay(taglineStart)
                taglineRightOffset.animateTo(0f, animationSpec = tween(durationMillis = taglineDuration, easing = EaseOutBack))
            }

            // Description fades in starting at descriptionStart.
            launch {
                delay(descriptionStart)
                descriptionAlpha.animateTo(1f, animationSpec = tween(durationMillis = descriptionDuration))
            }

            // Title glow (and scale pulse) from glowStart to glowStart + glowDuration.
            launch {
                delay(glowStart)
                glowIntensity.animateTo(1f, animationSpec = tween(durationMillis = glowDuration, easing = EaseOutBack))
            }

            // Dissolve aligned with glow so everything completes together.
            launch {
                delay(dissolveStart)
                dissolveAlpha.animateTo(0f, animationSpec = tween(durationMillis = dissolveDuration))
            }

            // Start the transition to the next screen when the dissolve begins so AnimatedContent can crossfade.
            delay(dissolveStart)
            onContinue()
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (introBackgroundPainter != null) {
                Image(
                    painter = introBackgroundPainter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF05070D))
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xE6000000),
                                Color(0xCC000000),
                                Color(0xF2000000)
                            )
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(26.dp),
                contentAlignment = Alignment.Center
            ) {
                SplashHalo()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                    modifier = Modifier.graphicsLayer(alpha = dissolveAlpha.value)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        brandLetters.forEachIndexed { index, letter ->
                            val glowAlpha = 0.25f + 0.55f * glowIntensity.value
                            val glowBlur = 10f + 14f * glowIntensity.value
                            val titleScale = 1f + 0.05f * glowIntensity.value
                            Text(
                                text = letter,
                                color = Color(0xFFD6FF1F),
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    shadow = androidx.compose.ui.graphics.Shadow(
                                        color = Color(0xFF40FFAA).copy(alpha = glowAlpha),
                                        offset = androidx.compose.ui.geometry.Offset(0f, 2f),
                                        blurRadius = glowBlur
                                    )
                                ),
                                modifier = Modifier.graphicsLayer(
                                    rotationZ = if (index % 2 == 0) -2f else 2f,
                                    translationX = letterAnimX[index].value,
                                    translationY = letterAnimY[index].value,
                                    scaleX = titleScale,
                                    scaleY = titleScale
                                )
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Find it. Feel it.",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.graphicsLayer(translationX = taglineLeftOffset.value)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "GoTicky.",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.graphicsLayer(translationX = taglineRightOffset.value)
                        )
                    }

                    Text(
                        text = "Discover the hottest events around you and grab tickets in a few taps with GoTicky.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.graphicsLayer(alpha = descriptionAlpha.value)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    onSignIn: suspend (email: String, password: String, rememberMe: Boolean) -> AuthResult,
    onSignUp: suspend (profile: UserProfile, password: String) -> AuthResult,
    onSkip: () -> Unit,
    onBiometricSignIn: suspend () -> AuthResult,
    onAdminSignIn: () -> Unit,
    findProfileByEmail: suspend (email: String) -> UserProfile?,
    externalUploadInProgress: Boolean = false,
    externalUploadProgress: Float = 0f,
    externalUploadMessage: String? = null,
) {
    var mode by rememberSaveable { mutableStateOf(AuthMode.SignIn) }
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var emailFocused by remember { mutableStateOf(false) }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var phoneCode by rememberSaveable { mutableStateOf("+263") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var countryName by rememberSaveable { mutableStateOf("Zimbabwe") }
    var countryFlag by rememberSaveable { mutableStateOf("🇿🇼") }
    var phoneCodeFlag by rememberSaveable { mutableStateOf("🇿🇼") }
    var birthday by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("Prefer not to say") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var agreedTerms by rememberSaveable { mutableStateOf(false) }
    var rememberMe by rememberSaveable { mutableStateOf(false) }
    var profilePhotoUri by rememberSaveable { mutableStateOf<String?>(null) }
    var profilePhotoOwnerEmail by rememberSaveable { mutableStateOf<String?>(null) }
    var isUploadingPhoto by rememberSaveable { mutableStateOf(false) }
    var uploadProgress by rememberSaveable { mutableStateOf(0f) }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var errorText by rememberSaveable { mutableStateOf<String?>(null) }
    var showBirthdayDialog by rememberSaveable { mutableStateOf(false) }
    var showGenderDialog by rememberSaveable { mutableStateOf(false) }
    var showCountryDialog by rememberSaveable { mutableStateOf(false) }
    var showPhoneCodeDialog by rememberSaveable { mutableStateOf(false) }
    var phoneCodeOverridden by rememberSaveable { mutableStateOf(false) }
    var fetchedProfileForEmail by remember { mutableStateOf<UserProfile?>(null) }
    var fetchingProfile by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    // Focus requesters to gently guide the user to missing fields when validating Sign Up.
    val nameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val phoneFocusRequester = remember { FocusRequester() }
    val birthdayFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val months = remember {
        listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
    }
    // Use a stable, non-clock-based default to avoid ExperimentalTime / Clock issues on multiplatform builds.
    var birthdayMonthIndex by rememberSaveable { mutableStateOf(0) } // January
    var birthdayDay by rememberSaveable { mutableStateOf(1) }
    var birthdayYear by rememberSaveable { mutableStateOf(2000) }
    val minYear = remember { 1900 }
    val maxYear = remember { 2100 }
    val sadcCountries: List<CountryOption> = remember {
        listOf(
            CountryOption("Zimbabwe", "🇿🇼", "+263"),
            CountryOption("Botswana", "🇧🇼", "+267"),
            CountryOption("South Africa", "🇿🇦", "+27"),
            CountryOption("Namibia", "🇳🇦", "+264"),
            CountryOption("Zambia", "🇿🇲", "+260"),
            CountryOption("Mozambique", "🇲🇿", "+258"),
            CountryOption("Angola", "🇦🇴", "+244"),
            CountryOption("Malawi", "🇲🇼", "+265"),
            CountryOption("Lesotho", "🇱🇸", "+266"),
            CountryOption("Eswatini", "🇸🇿", "+268"),
            CountryOption("Tanzania", "🇹🇿", "+255"),
            CountryOption("DR Congo", "🇨🇩", "+243"),
            CountryOption("Madagascar", "🇲🇬", "+261"),
            CountryOption("Mauritius", "🇲🇺", "+230"),
            CountryOption("Seychelles", "🇸🇨", "+248"),
            CountryOption("Comoros", "🇰🇲", "+269"),
        )
    }
    val genderOptions = remember {
        listOf(
            "Male",
            "Female",
            "Transgender",
            "Prefer not to say"
        )
    }
    val phoneCodeOptions = remember { sadcCountries }
    val cardScale by animateFloatAsState(
        targetValue = if (mode == AuthMode.SignUp) 1.02f else 1f,
        animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
        label = "authCardScale"
    )
    val neonInputColor = Color(0xFF7EF9FF)
    val linkColor = MaterialTheme.colorScheme.primary
    val termsUrl = "https://github.com/Wallytd/GoTicky/blob/master/TERMS_OF_SERVICE.md"
    val privacyUrl = "https://github.com/Wallytd/GoTicky/blob/master/PRIVACY_POLICY.md"
    val uriHandler = LocalUriHandler.current
    val termsText = remember(linkColor) {
        buildAnnotatedString {
            append("By creating an account or logging in, you agree with GoTicky's ")
            pushStringAnnotation(tag = "terms", annotation = "terms")
            withStyle(SpanStyle(color = linkColor, textDecoration = TextDecoration.Underline, fontWeight = FontWeight.SemiBold)) {
                append("Terms and Conditions")
            }
            pop()
            append(" and ")
            pushStringAnnotation(tag = "privacy", annotation = "privacy")
            withStyle(SpanStyle(color = linkColor, textDecoration = TextDecoration.Underline, fontWeight = FontWeight.SemiBold)) {
                append("Privacy Policy")
            }
            pop()
        }
    }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val imagePicker = rememberImagePicker { uri ->
        scope.launch {
            if (uri == null) return@launch
            // For sign-up, keep things simple and reliable: store the picked local URI
            // so validation and the avatar preview work immediately.
            isUploadingPhoto = true
            uploadProgress = 0f
            profilePhotoUri = uri
            profilePhotoOwnerEmail = email.trim()
            // Play a short progress animation to keep the UI feeling alive.
            uploadProgress = 1f
            delay(300)
            isUploadingPhoto = false
        }
    }

    LaunchedEffect(showBirthdayDialog) {
        if (showBirthdayDialog && birthday.isNotBlank()) {
            val monthToken = birthday.substringBefore(" ").trim()
            val dayToken = birthday.substringAfter(" ").substringBefore(",").trim()
            val yearToken = birthday.substringAfter(",").trim()
            months.indexOf(monthToken).takeIf { it >= 0 }?.let { birthdayMonthIndex = it }
            dayToken.toIntOrNull()?.let { birthdayDay = it.coerceIn(1, daysInMonth(birthdayMonthIndex, birthdayYear)) }
            yearToken.toIntOrNull()?.let { birthdayYear = it.coerceIn(minYear, maxYear) }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                val isError = data.visuals.actionLabel == "error"
                Snackbar(
                    snackbarData = data,
                    containerColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (isError) MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState)
                .padding(horizontal = 18.dp, vertical = 24.dp)
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            AuthHeader(mode = mode, onModeChange = { mode = it })

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer(scaleX = cardScale, scaleY = cardScale)
                    .clip(goTickyShapes.large)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f),
                        shape = goTickyShapes.large
                    )
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                if (mode == AuthMode.SignUp) {
                    AuthField(
                        value = name,
                        onValueChange = { name = it.capitalizeWordsPreserveSpaces() },
                        label = "Full name",
                        leading = { Icon(Icons.Outlined.Person, contentDescription = null, tint = Color(0xFF795548)) },
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                        modifier = Modifier.focusRequester(nameFocusRequester)
                    )
                }
                if (mode == AuthMode.SignIn) {
                    val emailTrimmed = email.trim()
                    val fetchedPainter = fetchedProfileForEmail?.photoUri?.let { rememberUriPainter(it) }
                    val emailValid = emailTrimmed.contains("@") && emailTrimmed.contains(".")
                    LaunchedEffect(emailTrimmed) {
                        if (!emailValid) {
                            fetchedProfileForEmail = null
                            fetchingProfile = false
                        } else {
                            fetchingProfile = true
                            // Ensure the loading avatar spinner is visible for at least 400 ms
                            val minDelayJob = launch { delay(400L) }
                            val profile = findProfileByEmail(emailTrimmed)
                            val fallbackPhoto = fallbackAvatarUrl(emailTrimmed)
                            fun fallbackNameFromEmail(raw: String): String {
                                val local = raw.substringBefore("@").replace(".", " ").replace("_", " ").trim()
                                if (local.isBlank()) return "Found profile"
                                return local.split(" ").filter { it.isNotBlank() }.joinToString(" ") { part ->
                                    part.lowercase().replaceFirstChar { ch -> ch.titlecase() }
                                }
                            }
                            fun cleanedName(original: String?, emailValue: String): String {
                                val base = original?.ifBlank { fallbackNameFromEmail(emailValue) }
                                    ?: fallbackNameFromEmail(emailValue)
                                return if (base.contains("admin", ignoreCase = true)) {
                                    fallbackNameFromEmail(emailValue)
                                } else base
                            }
                            fetchedProfileForEmail = when {
                                profile != null && !profile.photoUri.isNullOrBlank() -> profile.copy(
                                    fullName = cleanedName(profile.fullName, emailTrimmed)
                                )
                                profile != null -> profile.copy(
                                    fullName = cleanedName(profile.fullName, emailTrimmed),
                                    photoUri = fallbackPhoto
                                )
                                else -> UserProfile(
                                    fullName = cleanedName(null, emailTrimmed),
                                    email = emailTrimmed,
                                    countryName = "Zimbabwe",
                                    countryFlag = "",
                                    phoneCode = "+263",
                                    phoneNumber = "",
                                    birthday = "",
                                    gender = "",
                                    photoResKey = null,
                                    photoUri = fallbackPhoto
                                )
                            }
                            minDelayJob.join()
                            fetchingProfile = false
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(92.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
                                .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            when {
                                fetchingProfile -> {
                                    LoadingSpinner(modifier = Modifier.size(32.dp))
                                }
                                fetchedPainter != null -> {
                                    Image(
                                        painter = fetchedPainter,
                                        contentDescription = "Profile photo",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                else -> {
                                    Icon(
                                        imageVector = Icons.Outlined.Person,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(42.dp)
                                    )
                                }
                            }
                        }
                        AnimatedVisibility(visible = fetchedProfileForEmail != null) {
                            Text(
                                text = fetchedProfileForEmail?.fullName?.ifBlank { "Found profile" } ?: "",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        if (mode == AuthMode.SignUp && externalUploadMessage != null && externalUploadInProgress) {
                            Text(
                                text = externalUploadMessage,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Outlined.AlternateEmail, contentDescription = null, tint = Color(0xFF1E88E5)) },
                    trailingIcon = {
                        if (email.isNotBlank() && emailFocused) {
                            IconButton(onClick = { email = "" }) {
                                Icon(
                                    imageVector = Icons.Outlined.Clear,
                                    contentDescription = "Clear email",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .pressAnimated()
                        .focusRequester(emailFocusRequester)
                        .onFocusChanged { emailFocused = it.isFocused },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        focusedTextColor = neonInputColor,
                        unfocusedTextColor = neonInputColor,
                        cursorColor = neonInputColor
                    ),
                    singleLine = true
                )
                val emailTrimmed = email.trim()
                val photoMatchesEmail = profilePhotoUri != null &&
                        profilePhotoOwnerEmail?.equals(emailTrimmed, ignoreCase = true) == true
                LaunchedEffect(emailTrimmed, profilePhotoUri) {
                    if (profilePhotoUri != null && emailTrimmed.isNotBlank() &&
                        !profilePhotoOwnerEmail.equals(emailTrimmed, ignoreCase = true)
                    ) {
                        profilePhotoOwnerEmail = emailTrimmed
                    }
                }
                val avatarPainter = if (photoMatchesEmail) profilePhotoUri?.let { rememberUriPainter(it) } else null
                val handWave by rememberInfiniteTransition(label = "handWave")
                    .animateFloat(
                        initialValue = -6f,
                        targetValue = 6f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(900, easing = EaseOutBack),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "handWaveAngle"
                    )
                val cameraPulse by rememberInfiniteTransition(label = "cameraPulse")
                    .animateFloat(
                        initialValue = 0.94f,
                        targetValue = 1.06f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(800, easing = EaseOutBack),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "cameraPulseScale"
                    )
                AnimatedVisibility(visible = mode == AuthMode.SignUp) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(104.dp)
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.12f),
                                    shape = CircleShape
                                )
                                .pressAnimated(),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(88.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                if (avatarPainter != null) {
                                    Image(
                                        painter = avatarPainter,
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Outlined.Person,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(46.dp)
                                    )
                                }
                            }
                            // Camera badge
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .offset(x = 4.dp, y = 4.dp)
                                    .size(30.dp)
                                    .graphicsLayer(scaleX = cameraPulse, scaleY = cameraPulse)
                                    .clip(CircleShape)
                                    .background(Brush.radialGradient(listOf(Color(0xFF00E5FF), Color(0xFF7C4DFF))))
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() }
                                    ) { if (!isLoading && !isUploadingPhoto) imagePicker.pickFromGallery() },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.PhotoCamera,
                                    contentDescription = "Upload photo",
                                    tint = Color(0xFF0A0A0A),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            // Floating hand hint outside to the left (only before a photo is set or while not uploading)
                            if (profilePhotoUri == null && !isUploadingPhoto) {
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                        .offset(x = (-66).dp)
                                        .graphicsLayer(rotationZ = handWave, translationY = handWave * 0.2f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        "\uD83D\uDC49",
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                    Text(
                                        "Tap camera to\nadd profile photo",
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
                                            .padding(horizontal = 10.dp, vertical = 6.dp),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        if (mode == AuthMode.SignUp && email.isNotBlank()) {
                            NeonTextButton(
                                text = if (profilePhotoUri == null) "Add profile photo" else "Change photo",
                                onClick = { if (!isLoading) imagePicker.pickFromGallery() }
                            )
                        }
                        if (isUploadingPhoto) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "Uploading photo… ${(uploadProgress * 100).toInt()}%",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                AnimatedProgressBar(
                                    progress = uploadProgress,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }

                if (mode == AuthMode.SignUp) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = countryName,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Country") },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Fingerprint,
                                    contentDescription = "Pick country",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .pressAnimated(),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                focusedTextColor = neonInputColor,
                                unfocusedTextColor = neonInputColor,
                                cursorColor = neonInputColor
                            )
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clip(goTickyShapes.medium)
                                .clickable { showCountryDialog = true }
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(0.45f)) {
                            OutlinedTextField(
                                value = phoneCode,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Code") },
                                leadingIcon = { Text(phoneCodeFlag) },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Fingerprint,
                                        contentDescription = "Pick phone code",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .pressAnimated(),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                                ),
                                singleLine = true
                            )
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .clip(goTickyShapes.medium)
                                    .clickable { showPhoneCodeDialog = true }
                            )
                        }
                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            label = { Text("Phone number") },
                            placeholder = {
                                Text(
                                    "7xx xxx xxx",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.55f)
                                )
                            },
                            leadingIcon = { Icon(Icons.Outlined.PhoneAndroid, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .weight(1f)
                                .focusRequester(phoneFocusRequester),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                focusedTextColor = neonInputColor,
                                unfocusedTextColor = neonInputColor,
                                cursorColor = neonInputColor
                            ),
                            singleLine = true
                        )
                    }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = birthday.ifBlank { "Tap to pick" },
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Birthday") },
                            trailingIcon = {
                                Icon(
                                    Icons.Outlined.Fingerprint,
                                    contentDescription = "Pick birthday",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .pressAnimated()
                                .focusRequester(birthdayFocusRequester),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                focusedTextColor = neonInputColor,
                                unfocusedTextColor = neonInputColor,
                                cursorColor = neonInputColor
                            ),
                            singleLine = true
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clip(goTickyShapes.medium)
                                .clickable { showBirthdayDialog = true }
                        )
                    }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = gender,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Gender") },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Fingerprint,
                                    contentDescription = "Select gender",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .pressAnimated(),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                focusedTextColor = neonInputColor,
                                unfocusedTextColor = neonInputColor,
                                cursorColor = neonInputColor
                            ),
                            singleLine = true
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clip(goTickyShapes.medium)
                                .clickable { showGenderDialog = true }
                        )
                    }
                }

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    leadingIcon = { Icon(Icons.Outlined.Key, contentDescription = null, tint = Color(0xFF2E7D32)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(passwordFocusRequester),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        focusedTextColor = neonInputColor,
                        unfocusedTextColor = neonInputColor,
                        cursorColor = neonInputColor
                    ),
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                                contentDescription = "Toggle password visibility",
                                tint = if (passwordVisible) MaterialTheme.colorScheme.error else Color(0xFF2E7D32)
                            )
                        }
                    }
                )

                if (mode == AuthMode.SignUp) {
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm password") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            focusedTextColor = neonInputColor,
                            unfocusedTextColor = neonInputColor,
                            cursorColor = neonInputColor
                        ),
                        singleLine = true,
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                Icon(
                                    imageVector = if (confirmPasswordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                                    contentDescription = "Toggle confirm password visibility",
                                    tint = if (confirmPasswordVisible) MaterialTheme.colorScheme.error else Color(0xFF2E7D32)
                                )
                            }
                        }
                    )
                }

                if (mode == AuthMode.SignUp) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Checkbox(
                            checked = agreedTerms,
                            onCheckedChange = { agreedTerms = it }
                        )
                        ClickableText(
                            text = termsText,
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                            onClick = { offset ->
                                termsText.getStringAnnotations(start = offset, end = offset).firstOrNull()?.let { annotation ->
                                    when (annotation.tag) {
                                        "terms" -> uriHandler.openUri(termsUrl)
                                        "privacy" -> uriHandler.openUri(privacyUrl)
                                    }
                                }
                            }
                        )
                    }
                }

                if (mode == AuthMode.SignIn) {
                    RememberMeRow(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it }
                    )
                }

                PrimaryButton(
                    text = if (mode == AuthMode.SignIn) "Sign in" else "Create account",
                    onClick = {
                        if (isLoading || externalUploadInProgress) return@PrimaryButton
                        if (isUploadingPhoto) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Please wait, uploading your profile photo…")
                            }
                            return@PrimaryButton
                        }
                        isLoading = true
                        errorText = null
                        scope.launch {
                            if (mode == AuthMode.SignUp) {
                                val missingMessage: String? = when {
                                    name.trim().isEmpty() -> "Please add your full name."
                                    email.trim().isEmpty() -> "Please add your email address."
                                    !email.contains("@") || !email.contains(".") -> "Please enter a valid email address."
                                    phoneNumber.trim().isEmpty() -> "Please add your phone number."
                                    birthday.trim().isEmpty() -> "Please pick your birthday."
                                    !photoMatchesEmail -> "Please add a profile photo for this email."
                                    password.isBlank() -> "Please create a password."
                                    confirmPassword.isBlank() -> "Please confirm your password."
                                    !agreedTerms -> "Please agree to the Terms & Privacy Policy to continue."
                                    else -> null
                                }
                                if (missingMessage != null) {
                                    when {
                                        name.trim().isEmpty() -> nameFocusRequester.requestFocus()
                                        email.trim().isEmpty() || !email.contains("@") || !email.contains(".") -> emailFocusRequester.requestFocus()
                                        phoneNumber.trim().isEmpty() -> phoneFocusRequester.requestFocus()
                                        birthday.trim().isEmpty() -> {
                                            birthdayFocusRequester.requestFocus()
                                            showBirthdayDialog = true
                                        }
                                        profilePhotoUri == null && !photoMatchesEmail -> {
                                            // Gently scroll back to the top where the avatar lives.
                                            scrollState.animateScrollTo(0)
                                        }
                                        password.isBlank() || confirmPassword.isBlank() -> passwordFocusRequester.requestFocus()
                                    }

                                    snackbarHostState.showSnackbar(
                                        message = missingMessage,
                                        actionLabel = "error"
                                    )
                                    isLoading = false
                                    return@launch
                                }
                            }

                            if (mode == AuthMode.SignUp && password != confirmPassword) {
                                errorText = "Passwords do not match."
                                isLoading = false
                                return@launch
                            }

                            val result: AuthResult
                            if (mode == AuthMode.SignIn) {
                                // Keep the sign-in loading state visible for at least 700 ms
                                val minDelayJob = launch { delay(700L) }
                                result = onSignIn(email, password, rememberMe)
                                minDelayJob.join()
                            } else {
                                val safePhotoUri = if (photoMatchesEmail) profilePhotoUri else null
                                val profile = UserProfile(
                                    fullName = name.trim(),
                                    email = email.trim(),
                                    countryName = countryName,
                                    countryFlag = countryFlag,
                                    phoneCode = phoneCode,
                                    phoneNumber = phoneNumber.trim(),
                                    birthday = birthday.trim(),
                                    gender = gender,
                                    photoResKey = null,
                                    photoUri = safePhotoUri
                                )
                                result = onSignUp(profile, password)
                            }
                            when (result) {
                                is AuthResult.Success -> {
                                    if (mode == AuthMode.SignUp) {
                                        snackbarHostState.showSnackbar("Account created. Please sign in.")
                                        mode = AuthMode.SignIn
                                    } else {
                                        snackbarHostState.showSnackbar("Signed in")
                                    }
                                }
                                is AuthResult.Error -> errorText = result.message
                            }
                            isLoading = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                )

                if (mode == AuthMode.SignUp && (externalUploadInProgress || externalUploadMessage != null)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(goTickyShapes.medium)
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = externalUploadMessage ?: "Uploading profile photo…",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        LinearProgressIndicator(
                            progress = externalUploadProgress.coerceIn(0f, 1f),
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (externalUploadProgress in 0.01f..0.99f) {
                            Text(
                                text = "${(externalUploadProgress * 100).toInt()}%",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

            if (mode == AuthMode.SignIn) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NeonTextButton(
                        text = "Forgot password?",
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Password reset is coming soon in this demo.")
                            }
                        }
                    )
                    NeonTextButton(
                        text = "Create account",
                        onClick = { mode = AuthMode.SignUp }
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "I already have an account",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    PrimaryButton(
                        text = "Sign in",
                        modifier = Modifier
                            .height(44.dp)
                            .weight(0.4f),
                        onClick = { mode = AuthMode.SignIn }
                    )
                }
            }
            AnimatedProgressBar(progress = if (mode == AuthMode.SignIn) 0.35f else 0.65f)
            AnimatedVisibility(visible = isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(goTickyShapes.medium)
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LoadingRow(modifier = Modifier.fillMaxWidth())
                    Text(
                        "Signing you in…",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            errorText?.let { msg ->
                Text(
                    msg,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

            if (mode == AuthMode.SignIn) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Or continue",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )
                    SocialRow(
                        onSkip = onSkip,
                        onBiometric = {
                            scope.launch {
                                val result = onBiometricSignIn()
                                when (result) {
                                    is AuthResult.Success -> snackbarHostState.showSnackbar("Signed in with biometrics")
                                    is AuthResult.Error -> snackbarHostState.showSnackbar(result.message, actionLabel = "error")
                                }
                            }
                        },
                        onAdminSignIn = onAdminSignIn
                    )
                }
            }
        }
        if (showBirthdayDialog) {
            AlertDialog(
                onDismissRequest = { showBirthdayDialog = false },
                icon = { Icon(Icons.Outlined.CalendarMonth, contentDescription = null) },
                title = { Text("Select birthday") },
                text = {
                    var visible by remember { mutableStateOf(false) }
                    val scale by animateFloatAsState(
                        targetValue = if (visible) 1f else 0.94f,
                        animationSpec = tween(durationMillis = GoTickyMotion.Standard, easing = EaseOutBack),
                        label = "birthdayDialogScale"
                    )
                    LaunchedEffect(Unit) { visible = true }

                    val weekDays = listOf("M", "T", "W", "T", "F", "S", "S")
                    val totalDays = daysInMonth(birthdayMonthIndex, birthdayYear)
                    val rows = ((totalDays + 6) / 7).coerceAtLeast(1)

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
                                    birthdayMonthIndex = if (birthdayMonthIndex == 0) months.lastIndex else birthdayMonthIndex - 1
                                    birthdayDay = birthdayDay.coerceAtMost(daysInMonth(birthdayMonthIndex, birthdayYear))
                                }
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "${months[birthdayMonthIndex]} $birthdayYear",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Tap a date, swipe months, adjust year",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            NeonTextButton(
                                text = ">",
                                onClick = {
                                    birthdayMonthIndex = if (birthdayMonthIndex == months.lastIndex) 0 else birthdayMonthIndex + 1
                                    birthdayDay = birthdayDay.coerceAtMost(daysInMonth(birthdayMonthIndex, birthdayYear))
                                }
                            )
                        }

                        // Weekday labels
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

                        // Calendar grid
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            var day = 1
                            repeat(rows) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    repeat(7) {
                                        if (day <= totalDays) {
                                            val thisDay = day
                                            val isSelected = birthdayDay == thisDay
                                            val bgColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
                                            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.72f)

                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .aspectRatio(1f)
                                                    .clip(goTickyShapes.small)
                                                    .background(bgColor)
                                                    .border(
                                                        1.dp,
                                                        if (isSelected) GoTickyGradients.EdgeHalo else GoTickyGradients.CardGlow,
                                                        goTickyShapes.small
                                                    )
                                                    .pressAnimated(scaleDown = 0.9f)
                                                    .clickable { birthdayDay = thisDay },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = thisDay.toString(),
                                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                            day++
                                        } else {
                                            Spacer(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .aspectRatio(1f)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Year adjustment
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Year", style = MaterialTheme.typography.labelLarge)
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                NeonTextButton(
                                    text = "<",
                                    onClick = {
                                        birthdayYear = (birthdayYear - 1).coerceAtLeast(minYear)
                                        birthdayDay = birthdayDay.coerceAtMost(daysInMonth(birthdayMonthIndex, birthdayYear))
                                    }
                                )
                                Text(
                                    birthdayYear.toString(),
                                    style = MaterialTheme.typography.titleMedium
                                )
                                NeonTextButton(
                                    text = ">",
                                    onClick = {
                                        birthdayYear = (birthdayYear + 1).coerceAtMost(maxYear)
                                        birthdayDay = birthdayDay.coerceAtMost(daysInMonth(birthdayMonthIndex, birthdayYear))
                                    }
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    PrimaryButton(text = "Apply") {
                        val safeDay = birthdayDay.coerceAtMost(daysInMonth(birthdayMonthIndex, birthdayYear))
                        birthdayDay = safeDay
                        birthday = "${months[birthdayMonthIndex]} $safeDay, $birthdayYear"
                        showBirthdayDialog = false
                    }
                },
                dismissButton = {
                    NeonTextButton(text = "Cancel", onClick = { showBirthdayDialog = false })
                }
            )
        }
        if (showCountryDialog) {
            AlertDialog(
                onDismissRequest = { showCountryDialog = false },
                icon = { Icon(Icons.Outlined.Person, contentDescription = null) },
                title = { Text("Choose your country") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            "We’ll localize experiences and dialing codes for SADC travelers.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            sadcCountries.forEach { country ->
                                val interaction = remember { MutableInteractionSource() }
                                val pressed by interaction.collectIsPressedAsState()
                                val optionScale by animateFloatAsState(
                                    targetValue = if (pressed) 0.95f else 1f,
                                    animationSpec = tween(GoTickyMotion.Quick, easing = EaseOutBack),
                                    label = "countryOption-${country.name}"
                                )
                                val isSelected = country.name == countryName
                                Row(
                                    modifier = Modifier
                                        .graphicsLayer(scaleX = optionScale, scaleY = optionScale)
                                        .clip(goTickyShapes.medium)
                                        .background(
                                            if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.16f)
                                            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.72f)
                                        )
                                        .border(
                                            1.dp,
                                            if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                                            goTickyShapes.medium
                                        )
                                        .clickable(
                                            interactionSource = interaction,
                                            indication = null
                                        ) {
                                            countryName = country.name
                                            countryFlag = country.flag
                                            if (!phoneCodeOverridden) {
                                                phoneCode = country.phoneCode
                                                phoneCodeFlag = country.flag
                                            }
                                        }
                                        .padding(horizontal = 12.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Text(
                                        country.flag,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Column {
                                        Text(
                                            country.name,
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            country.phoneCode,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    PrimaryButton(text = "Done") { showCountryDialog = false }
                },
                dismissButton = {
                    NeonTextButton(text = "Cancel", onClick = { showCountryDialog = false })
                }
            )
        }
        if (showPhoneCodeDialog) {
            AlertDialog(
                onDismissRequest = { showPhoneCodeDialog = false },
                icon = { Icon(Icons.Outlined.PhoneAndroid, contentDescription = null) },
                title = { Text("Choose dialing code") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            "Pick the code that matches the number you’ll verify — handy if you’re abroad.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            phoneCodeOptions.forEach { country ->
                                val interaction = remember { MutableInteractionSource() }
                                val pressed by interaction.collectIsPressedAsState()
                                val optionScale by animateFloatAsState(
                                    targetValue = if (pressed) 0.95f else 1f,
                                    animationSpec = tween(GoTickyMotion.Quick, easing = EaseOutBack),
                                    label = "codeOption-${country.name}"
                                )
                                val isSelected = country.phoneCode == phoneCode
                                Row(
                                    modifier = Modifier
                                        .graphicsLayer(scaleX = optionScale, scaleY = optionScale)
                                        .clip(goTickyShapes.medium)
                                        .background(
                                            if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.16f)
                                            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.72f)
                                        )
                                        .border(
                                            1.dp,
                                            if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                                            goTickyShapes.medium
                                        )
                                        .clickable(
                                            interactionSource = interaction,
                                            indication = null
                                        ) {
                                            phoneCode = country.phoneCode
                                            phoneCodeFlag = country.flag
                                            phoneCodeOverridden = true
                                        }
                                        .padding(horizontal = 12.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Text(country.flag, style = MaterialTheme.typography.titleMedium)
                                    Column {
                                        Text(
                                            country.name,
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            country.phoneCode,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    PrimaryButton(text = "Done") { showPhoneCodeDialog = false }
                },
                dismissButton = {
                    NeonTextButton(text = "Cancel", onClick = { showPhoneCodeDialog = false })
                }
            )
        }
        if (showGenderDialog) {
            AlertDialog(
                onDismissRequest = { showGenderDialog = false },
                icon = { Icon(Icons.Outlined.Person, contentDescription = null) },
                title = { Text("Select gender") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            "Pick how you’d like to be shown. You can change this anytime.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            genderOptions.forEach { option ->
                                val interaction = remember { MutableInteractionSource() }
                                val pressed by interaction.collectIsPressedAsState()
                                val optionScale by animateFloatAsState(
                                    targetValue = if (pressed) 0.96f else 1f,
                                    animationSpec = tween(GoTickyMotion.Quick, easing = EaseOutBack),
                                    label = "genderOption-$option"
                                )
                                Row(
                                    modifier = Modifier
                                        .graphicsLayer(scaleX = optionScale, scaleY = optionScale)
                                        .clip(goTickyShapes.medium)
                                        .background(
                                            if (gender == option) MaterialTheme.colorScheme.primary.copy(alpha = 0.16f)
                                            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.75f)
                                        )
                                        .border(
                                            1.dp,
                                            if (gender == option) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(
                                                alpha = 0.5f
                                            ),
                                            goTickyShapes.medium
                                        )
                                        .clickable(
                                            interactionSource = interaction,
                                            indication = null
                                        ) { gender = option }
                                        .padding(horizontal = 12.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    RadioButton(
                                        selected = option == gender,
                                        onClick = { gender = option }
                                    )
                                    Text(option, style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    PrimaryButton(text = "Done") { showGenderDialog = false }
                },
                dismissButton = {
                    NeonTextButton(text = "Cancel", onClick = { showGenderDialog = false })
                }
            )
        }
    }
}

private fun daysInMonth(monthIndex: Int, year: Int): Int {
    return when (monthIndex) {
        0, 2, 4, 6, 7, 9, 11 -> 31
        3, 5, 8, 10 -> 30
        1 -> if (isLeapYear(year)) 29 else 28
        else -> 30
    }
}

private fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

private fun String.capitalizeWordsPreserveSpaces(): String {
    if (isEmpty()) return this
    val sb = StringBuilder(length)
    var capitalizeNext = true
    for (c in this) {
        if (c.isLetter()) {
            if (capitalizeNext) {
                sb.append(c.titlecaseChar())
                capitalizeNext = false
            } else {
                sb.append(c.lowercaseChar())
            }
        } else {
            sb.append(c)
            capitalizeNext = c.isWhitespace()
        }
    }
    return sb.toString()
}

@Composable
private fun AuthHeader(
    mode: AuthMode,
    onModeChange: (AuthMode) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = tween(GoTickyMotion.Standard, easing = EaseOutBack),
        label = "authHeaderScale"
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .clip(RoundedCornerShape(20.dp))
            .background(GoTickyGradients.Hero)
            .clickable(interactionSource = interactionSource, indication = null) {
                onModeChange(if (mode == AuthMode.SignIn) AuthMode.SignUp else AuthMode.SignIn)
            }
            .padding(18.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = if (mode == AuthMode.SignIn) "Welcome back" else "Create your GoTicky pass",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                textAlign = TextAlign.Center
            )
            Text(
                text = if (mode == AuthMode.SignIn)
                    "Sign in to sync tickets, alerts, and favorites."
                else
                    "Join to unlock alerts, curated recs, and organizer perks.",
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun AuthField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leading: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = leading,
        modifier = modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        shape = goTickyShapes.medium,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
    )
}

@Composable
private fun SocialRow(
    onSkip: () -> Unit,
    onBiometric: () -> Unit,
    onAdminSignIn: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        // Center the pill buttons horizontally while keeping even spacing between them.
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SocialChip(
            label = "Guest",
            icon = Icons.Outlined.PlayArrow,
            tint = IconCategoryColors[IconCategory.Discover] ?: MaterialTheme.colorScheme.primary,
            onClick = onSkip
        )
        SocialChip(
            label = "Biometric",
            icon = Icons.Outlined.Fingerprint,
            tint = IconCategoryColors[IconCategory.Profile] ?: MaterialTheme.colorScheme.secondary,
            onClick = onBiometric
        )
        SocialChip(
            label = "Secure",
            icon = Icons.Outlined.Shield,
            tint = IconCategoryColors[IconCategory.Admin] ?: MaterialTheme.colorScheme.tertiary,
            onClick = onAdminSignIn
        )
    }
}

@Composable
fun AdminSignInScreen(
    modifier: Modifier = Modifier,
    flagEnabled: Boolean,
    onBack: () -> Unit,
    prefillEmail: String = "",
    prefillPasscode: String = "",
    prefillRememberMe: Boolean = false,
    onSubmit: suspend (email: String, passcode: String, rememberMe: Boolean) -> AuthResult,
) {
    val adminTint = IconCategoryColors[IconCategory.Admin] ?: MaterialTheme.colorScheme.tertiary
    val neonTextColor = Color(0xFF7EF9FF)

    var email by rememberSaveable { mutableStateOf(prefillEmail) }
    var passcode by rememberSaveable { mutableStateOf(prefillPasscode) }
    var rememberMe by rememberSaveable { mutableStateOf(prefillRememberMe) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    val backInteraction = remember { MutableInteractionSource() }
    val backPressed by backInteraction.collectIsPressedAsState()
    val backScale by animateFloatAsState(
        targetValue = if (backPressed) 0.8f else 1f,
        animationSpec = tween(GoTickyMotion.Standard, easing = EaseOutBack),
        label = "adminBackScale"
    )
    val backRotation by animateFloatAsState(
        targetValue = if (backPressed) -45f else 0f,
        animationSpec = tween(GoTickyMotion.Standard, easing = EaseOutBack),
        label = "adminBackRotate"
    )
    val haloPulse by rememberInfiniteTransition(label = "adminHalo").animateFloat(
        initialValue = 0.92f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseOutBack),
            repeatMode = RepeatMode.Reverse
        ),
        label = "adminHaloScale"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.85f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .padding(horizontal = 18.dp, vertical = 24.dp)
    ) {
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .blur(140.dp)
        ) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        adminTint.copy(alpha = 0.18f),
                        Color.Transparent
                    )
                ),
                radius = size.minDimension * 0.35f,
                center = center
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 6.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .graphicsLayer(
                            scaleX = backScale,
                            scaleY = backScale,
                            rotationZ = backRotation
                        )
                        .pressAnimated(),
                    interactionSource = backInteraction
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Shield,
                        contentDescription = "Back",
                        tint = adminTint
                    )
                }
                Pill(
                    text = if (flagEnabled) "Admin secure" else "Flag off",
                    color = Color(0xFF2196F3).copy(alpha = 0.9f),
                    textColor = Color.White
                )
            }

            GlowCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer(scaleX = haloPulse, scaleY = haloPulse)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(
                                    adminTint.copy(
                                        alpha = 0.16f
                                    )
                                )
                                .border(
                                    1.dp,
                                    adminTint.copy(alpha = 0.4f),
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Shield,
                                contentDescription = "Admin shield",
                                tint = adminTint,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                "Admin control room",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                if (flagEnabled)
                                    "Use a secure admin identity to enter dashboards, flags, and moderation."
                                else
                                    "Feature flag is off. Sign-in is allowed, but access will be gated.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Admin email") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.AlternateEmail,
                                contentDescription = null,
                                tint = Color(0xFF1E88E5) // blue
                            )
                        },
                        trailingIcon = {
                            if (email.isNotBlank()) {
                                IconButton(onClick = { email = "" }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Clear,
                                        contentDescription = "Clear email",
                                        tint = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .pressAnimated(),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            focusedTextColor = neonTextColor,
                            unfocusedTextColor = neonTextColor,
                            cursorColor = neonTextColor
                        ),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = passcode,
                        onValueChange = { passcode = it },
                        label = { Text("Admin passcode") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Key,
                                contentDescription = null,
                                tint = Color(0xFF43A047) // green
                            )
                        },
                        trailingIcon = {
                            val icon = if (passwordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = if (passwordVisible) "Hide passcode" else "Show passcode",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .pressAnimated(),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            focusedTextColor = neonTextColor,
                            unfocusedTextColor = neonTextColor,
                            cursorColor = neonTextColor
                        ),
                        singleLine = true
                    )

                    AnimatedProgressBar(
                        progress = if (isLoading) 0.35f else 0.1f,
                        modifier = Modifier.fillMaxWidth()
                    )

                    RememberMeRow(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it }
                    )

                    PrimaryButton(
                        text = "Enter admin",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            if (isLoading) return@PrimaryButton
                            error = null
                            isLoading = true
                            scope.launch {
                                val trimmedEmail = email.trim()
                                val trimmedPass = passcode.trim()
                                if (trimmedEmail.isBlank() || trimmedPass.isBlank()) {
                                    error = "Email and passcode are required."
                                    isLoading = false
                                    return@launch
                                }
                                val result = onSubmit(trimmedEmail, trimmedPass, rememberMe)
                                if (result is AuthResult.Error) {
                                    error = result.message
                                }
                                isLoading = false
                            }
                        }
                    )
                    NeonTextButton(
                        text = "Back to public sign-in",
                        onClick = onBack
                    )
                    if (isLoading) {
                        LoadingSpinner(modifier = Modifier.size(32.dp))
                    }
                    error?.let { msg ->
                        Text(
                            msg,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RememberMeRow(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = tween(GoTickyMotion.Standard, easing = EaseOutBack),
        label = "rememberMeScale"
    )
    val boxColor by animateColorAsState(
        targetValue = if (checked) Color(0xFF1B5E20) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        animationSpec = tween(GoTickyMotion.Standard, easing = EaseOutBack),
        label = "rememberMeBoxColor"
    )
    val borderColor by animateColorAsState(
        targetValue = if (checked) Color(0xFF00E676) else MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
        animationSpec = tween(GoTickyMotion.Standard, easing = EaseOutBack),
        label = "rememberMeBorderColor"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onCheckedChange(!checked) }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(18.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(boxColor)
                    .border(1.5.dp, borderColor, RoundedCornerShape(5.dp))
            )
            if (checked) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = "Remember me checked",
                    tint = Color(0xFF00E676),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 2.dp, y = (-3).dp)
                        .size(18.dp)
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = "Remember me",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Keep me signed in on this device",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SocialChip(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.94f else 1f,
        animationSpec = tween(GoTickyMotion.Standard, easing = EaseOutBack),
        label = "socialScale-$label"
    )
    Row(
        modifier = Modifier
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .clip(RoundedCornerShape(16.dp))
            .background(tint.copy(alpha = 0.12f))
            .border(1.dp, tint.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .clickable(interactionSource = interactionSource, indication = null) { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = label, tint = tint)
        Text(label, color = tint, style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold))
    }
}

@Composable
private fun SplashHalo() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .blur(140.dp)
    ) {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.08f),
                    Color.Transparent
                ),
                center = center,
            ),
            radius = size.minDimension / 2f
        )
    }
}

private fun fallbackAvatarUrl(email: String): String {
    val seed = email.trim().lowercase().ifBlank { "goticky-user" }
    val sanitizedSeed = buildString {
        seed.forEach { ch ->
            append(if (ch.isLetterOrDigit()) ch else '-')
        }
    }
    return "https://api.dicebear.com/7.x/shapes/svg?seed=$sanitizedSeed&backgroundType=gradientLinear&scale=95"
}

private enum class AuthMode { SignIn, SignUp }
