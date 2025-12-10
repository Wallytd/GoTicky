package org.example.project.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import kotlin.random.Random
import org.example.project.data.OrderSummary
import org.example.project.data.TicketPass
import org.example.project.data.TicketType
import org.example.project.platform.rememberTicketShareAction
import org.example.project.data.sampleEvents
import org.example.project.platform.platformBarcodeBitmap
import org.example.project.platform.platformQrBitmap
import org.example.project.ui.components.ProfileAvatar
import org.example.project.ui.theme.GoTickyGradients
import org.example.project.ui.theme.GoTickyTextures
import org.example.project.ui.theme.goTickyShapes
import goticky.composeapp.generated.resources.Res
import goticky.composeapp.generated.resources.allDrawableResources
import goticky.composeapp.generated.resources.ic_pesepay
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

@Composable
fun TicketCard(
    ticket: TicketPass,
    showOpenButton: Boolean = true,
    onClick: () -> Unit
) {
    val metallic = metallicPalette(ticket.type)
    val qrPainter = rememberQrPainter(ticket.qrSeed, 220.dp, metallic.darkStroke)
    val barcodePainter = rememberBarcodePainter(ticket.qrSeed, metallic.darkStroke)
    val barcodeId = remember(ticket.id, ticket.qrSeed, ticket.holderInitials) {
        buildBarcodeNumericId(ticket)
    }
    val clipboard = LocalClipboardManager.current
    var barcodeCopied by remember { mutableStateOf(false) }

    LaunchedEffect(barcodeCopied) {
        if (barcodeCopied) {
            delay(1500)
            barcodeCopied = false
        }
    }

    GlowCard(
        modifier = Modifier
            .fillMaxWidth()
            .pressAnimated()
            .clickable { onClick() }
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Header with status + type chip + avatar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        ticket.eventTitle,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            shadow = Shadow(
                                color = metallic.darkStroke.copy(alpha = 0.65f),
                                offset = Offset(0f, 1.5f),
                                blurRadius = 9f
                            )
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        ticket.venue,
                        style = MaterialTheme.typography.bodySmall.copy(
                            shadow = Shadow(
                                color = metallic.darkStroke.copy(alpha = 0.4f),
                                offset = Offset(0f, 1f),
                                blurRadius = 6f
                            )
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.9f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Pill(
                        text = ticket.type.name.replaceFirstChar { it.uppercase() },
                        color = metallic.glow,
                        textColor = metallic.ink
                    )
                    Pill(
                        text = ticket.status,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.22f),
                        textColor = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Main body with metallic panel + user + details
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(goTickyShapes.extraLarge)
                    .background(
                        Brush.linearGradient(
                            colors = metallic.gradient
                        )
                    )
                    .drawBehind { drawRect(GoTickyTextures.GrainTint) }
                    .border(
                        1.dp,
                        Brush.linearGradient(listOf(metallic.glow, metallic.darkStroke.copy(alpha = 0.6f))),
                        goTickyShapes.extraLarge
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                            ProfileAvatar(
                                modifier = Modifier.size(42.dp),
                                initials = ticket.holderInitials,
                                onClick = {}
                            )
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text(
                                    ticket.holderName,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        shadow = Shadow(
                                            color = metallic.darkStroke.copy(alpha = 0.55f),
                                            offset = Offset(0f, 1.2f),
                                            blurRadius = 7f
                                        )
                                    ),
                                    color = metallic.ink
                                )
                                Text(
                                    "ID ${ticket.id}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        shadow = Shadow(
                                            color = metallic.darkStroke.copy(alpha = 0.4f),
                                            offset = Offset(0f, 1f),
                                            blurRadius = 5f
                                        )
                                    ),
                                    color = metallic.ink.copy(alpha = 0.85f)
                                )
                            }
                        }
                        Text(
                            ticket.dateLabel,
                            style = MaterialTheme.typography.labelMedium.copy(
                                shadow = Shadow(
                                    color = metallic.darkStroke.copy(alpha = 0.45f),
                                    offset = Offset(0f, 1.2f),
                                    blurRadius = 6f
                                )
                            ),
                            color = metallic.ink.copy(alpha = 0.95f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        DetailChip(title = "Seat", value = ticket.seat, accent = metallic.ink)
                        DetailChip(title = "Type", value = ticket.type.name, accent = metallic.ink)
                    }

                    // Perforation line
                    val backgroundColor = MaterialTheme.colorScheme.background
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                    ) {
                        val dashWidth = size.width / 20f
                        val dashSpace = dashWidth * 0.6f
                        var x = 0f
                        while (x < size.width) {
                            drawRect(
                                color = metallic.darkStroke.copy(alpha = 0.35f),
                                topLeft = androidx.compose.ui.geometry.Offset(x, size.height / 2 - 1f),
                                size = androidx.compose.ui.geometry.Size(dashWidth, 2f)
                            )
                            x += dashWidth + dashSpace
                        }
                        // Side nicks
                        drawCircle(
                            color = backgroundColor,
                            radius = size.height / 2.2f,
                            center = androidx.compose.ui.geometry.Offset(0f, size.height / 2),
                            style = Fill
                        )
                        drawCircle(
                            color = backgroundColor,
                            radius = size.height / 2.2f,
                            center = androidx.compose.ui.geometry.Offset(size.width, size.height / 2),
                            style = Fill
                        )
                    }

                    // QR + barcode
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clip(goTickyShapes.large)
                                .background(Color.White.copy(alpha = 0.92f))
                                .border(1.dp, metallic.darkStroke.copy(alpha = 0.25f), goTickyShapes.large)
                                .drawBehind { drawRect(GoTickyTextures.GrainTint) },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = qrPainter,
                                contentDescription = "QR",
                                modifier = Modifier.fillMaxSize(0.9f)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(86.dp)
                                .clip(goTickyShapes.large)
                                .background(Color.White.copy(alpha = 0.92f))
                                .border(1.dp, metallic.darkStroke.copy(alpha = 0.25f), goTickyShapes.large)
                                .drawBehind { drawRect(GoTickyTextures.GrainTint) },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.fillMaxWidth(0.9f)
                            ) {
                                Image(
                                    painter = barcodePainter,
                                    contentDescription = "Barcode",
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Row(
                                    modifier = Modifier
                                        .clip(goTickyShapes.medium)
                                        .background(
                                            MaterialTheme.colorScheme.surface.copy(alpha = 0.96f)
                                        )
                                        .pressAnimated()
                                        .clickable {
                                            clipboard.setText(AnnotatedString(barcodeId))
                                            barcodeCopied = true
                                        }
                                        .padding(horizontal = 12.dp, vertical = 4.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val prettyId = remember(barcodeId) {
                                        // Group digits for readability, e.g. 0808 1982 2797
                                        barcodeId.chunked(4).joinToString(" ")
                                    }
                                    Text(
                                        text = prettyId,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Medium,
                                            letterSpacing = 0.6.sp
                                        ),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                if (barcodeCopied) {
                                    Text(
                                        text = "Copied",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            if (showOpenButton) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PrimaryButton(text = "Open", modifier = Modifier.weight(1f)) { onClick() }
                }
            }
        }
    }
}

@Composable
private fun TicketEventHeaderImage(ticket: TicketPass) {
    val event = remember(ticket.eventTitle) {
        sampleEvents.firstOrNull { it.title == ticket.eventTitle }
    }
    val photoRes = event?.imagePath?.let { key -> Res.allDrawableResources[key] }

    if (photoRes != null) {
        GlowCard(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(goTickyShapes.large)
            ) {
                Image(
                    painter = painterResource(photoRes),
                    contentDescription = ticket.eventTitle,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.25f),
                                    Color.Black.copy(alpha = 0.75f)
                                )
                            )
                        )
                        .drawBehind { drawRect(GoTickyTextures.GrainTint) }
                )
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
    val onShare = rememberTicketShareAction(ticket)

    var vignetteActivated by remember { mutableStateOf(false) }
    val vignetteAlpha by animateFloatAsState(
        targetValue = if (vignetteActivated) 1f else 0f,
        animationSpec = tween(durationMillis = GoTickyMotion.Comfort),
        label = "ticketDetailVignetteAlpha"
    )

    LaunchedEffect(Unit) {
        vignetteActivated = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GoTickyGradients.CardGlow)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    if (vignetteAlpha > 0f) {
                        val radius = size.maxDimension * 0.85f
                        drawRect(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f * vignetteAlpha)
                                ),
                                center = center,
                                radius = radius
                            )
                        )
                    }
                }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GlowCard(modifier = Modifier.fillMaxWidth()) {
                TopBar(
                    title = ticket.eventTitle,
                    onBack = onBack,
                    actions = null,
                    backgroundColor = Color.Transparent,
                    titleContent = {
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = ticket.eventTitle,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    shadow = Shadow(
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                        offset = Offset(0f, 2f),
                                        blurRadius = 10f
                                    )
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = ticket.venue,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    shadow = Shadow(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f),
                                        offset = Offset(0f, 1.2f),
                                        blurRadius = 7f
                                    )
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.96f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                )
            }
            TicketEventHeaderImage(ticket = ticket)
            TicketCard(ticket = ticket, showOpenButton = false) { /* already in detail */ }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                PrimaryButton(
                    text = "Add to Wallet",
                    modifier = Modifier.weight(1f)
                ) { onAddToWallet() }
                GhostButton(
                    text = "Share",
                    modifier = Modifier.weight(1f),
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "Share ticket",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                ) {
                    onShare()
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LoadingSpinner(size = 32)
            Text(
                "Dynamic QR will appear here",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun parsePrice(raw: String): Int = raw.filter { it.isDigit() }.toIntOrNull() ?: 0

private enum class CheckoutPaymentMethod {
    Pesepay,
    Card,
    Wallet,
}

private enum class CheckoutStep {
    Review,
    Confirm,
}

@Composable
fun CheckoutScreen(
    order: OrderSummary,
    selectedTicketType: String,
    onBack: () -> Unit,
    onPlaceOrder: (paymentMethod: String, totalAmount: Int) -> Unit
) {
    var ticketQty by remember { mutableStateOf(1) }
    var selectedMethod by remember { mutableStateOf(CheckoutPaymentMethod.Pesepay) }
    var currentStep by remember { mutableStateOf(CheckoutStep.Review) }
    val baseTicketPrice = order.items.firstOrNull()?.let { parsePrice(it.price) } ?: 0
    val addOnsTotal = order.items.drop(1).sumOf { parsePrice(it.price) }
    val feesTotal = order.fees.sumOf { parsePrice(it.price) }
    val totalAmount = baseTicketPrice * ticketQty + addOnsTotal + feesTotal

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GoTickyGradients.CardGlow)
    ) {
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
                    Text(
                        text = when (currentStep) {
                            CheckoutStep.Review -> "Review"
                            CheckoutStep.Confirm -> "Confirm order"
                        },
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Selected: ",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = selectedTicketType,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                shadow = Shadow(
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                                    offset = Offset(0f, 1.5f),
                                    blurRadius = 8f
                                )
                            ),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Tickets",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                            Text(
                                text = "Adjust ticket count",
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
                                text = ticketQty.toString(),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    shadow = Shadow(
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                        offset = Offset(0f, 1.5f),
                                        blurRadius = 8f
                                    )
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .pressAnimated()
                                    .clickable { ticketQty++ },
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
                            Text(
                                text = "${item.label} x$displayQty",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.95f)
                            )
                            Text(
                                text = item.price,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.98f)
                            )
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    order.fees.forEach { fee ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val isServiceFee = fee.label.contains("service", ignoreCase = true)
                            val labelColor = if (isServiceFee) {
                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.9f)
                            } else {
                                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.9f)
                            }

                            Text(
                                text = fee.label,
                                style = MaterialTheme.typography.bodySmall,
                                color = labelColor
                            )
                            Text(
                                text = fee.price,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.95f)
                            )
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                shadow = Shadow(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                                    offset = Offset(0f, 1.5f),
                                    blurRadius = 8f
                                )
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "$${totalAmount}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                shadow = Shadow(
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                                    offset = Offset(0f, 2f),
                                    blurRadius = 10f
                                )
                            ),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    AnimatedProgressBar(
                        progress = when (currentStep) {
                            CheckoutStep.Review -> 0.66f
                            CheckoutStep.Confirm -> 1f
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = "Payment",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PaymentMethodChip(
                            label = "Pesepay",
                            isSelected = selectedMethod == CheckoutPaymentMethod.Pesepay,
                            accentColor = MaterialTheme.colorScheme.primary
                        ) {
                            selectedMethod = CheckoutPaymentMethod.Pesepay
                            currentStep = CheckoutStep.Review
                        }
                        PaymentMethodChip(
                            label = "Card",
                            isSelected = selectedMethod == CheckoutPaymentMethod.Card,
                            accentColor = MaterialTheme.colorScheme.secondary
                        ) {
                            selectedMethod = CheckoutPaymentMethod.Card
                            currentStep = CheckoutStep.Review
                        }
                        PaymentMethodChip(
                            label = "Wallet",
                            isSelected = selectedMethod == CheckoutPaymentMethod.Wallet,
                            accentColor = MaterialTheme.colorScheme.tertiary
                        ) {
                            selectedMethod = CheckoutPaymentMethod.Wallet
                            currentStep = CheckoutStep.Review
                        }
                    }
                    when (selectedMethod) {
                        CheckoutPaymentMethod.Pesepay -> {
                            Text(
                                text = "Secure checkout via Pesepay for local cards, mobile money, and bank transfers.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                            )
                            Column {
                                Text(
                                    text = when (currentStep) {
                                        CheckoutStep.Review -> "Step 1 of 2"
                                        CheckoutStep.Confirm -> "Step 2 of 2"
                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                                )
                                Text(
                                    text = when (currentStep) {
                                        CheckoutStep.Review -> "Review & payment"
                                        CheckoutStep.Confirm -> "Confirmation"
                                    },
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CheckoutStepDot(
                                    stepNumber = 1,
                                    isActive = currentStep == CheckoutStep.Review
                                )
                                CheckoutStepDot(
                                    stepNumber = 2,
                                    isActive = currentStep == CheckoutStep.Confirm
                                )
                            }
                            AnimatedProgressBar(
                                progress = when (currentStep) {
                                    CheckoutStep.Review -> 0.5f
                                    CheckoutStep.Confirm -> 1f
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                            if (currentStep == CheckoutStep.Confirm) {
                                Spacer(Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(goTickyShapes.medium)
                                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                                        .padding(horizontal = 10.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.24f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(Res.drawable.ic_pesepay),
                                            contentDescription = "Pesepay",
                                            tint = Color.Unspecified,
                                            modifier = Modifier.size(48.dp)
                                        )
                                    }
                                    Column {
                                        Text(
                                            text = "Confirm Pesepay payment",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                shadow = Shadow(
                                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.45f),
                                                    offset = Offset(0f, 1.5f),
                                                    blurRadius = 8f
                                                )
                                            )
                                        )
                                        Text(
                                            text = "We’ll place your order and show your tickets next.",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                        CheckoutPaymentMethod.Card -> {
                            Text(
                                text = "Use a saved or new debit/credit card for this booking.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        CheckoutPaymentMethod.Wallet -> {
                            Text(
                                text = "Pay from your in-app wallet balance when available.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    if (selectedMethod == CheckoutPaymentMethod.Pesepay) {
                        // Branded Pesepay button + instruction
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(goTickyShapes.large)
                                    .background(GoTickyGradients.Cta)
                                    .pressAnimated()
                                    .clickable {
                                        if (currentStep == CheckoutStep.Review) {
                                            currentStep = CheckoutStep.Confirm
                                        } else {
                                            onPlaceOrder("pesepay", totalAmount)
                                        }
                                    }
                                    .padding(horizontal = 18.dp, vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(Res.drawable.ic_pesepay),
                                        contentDescription = "Pesepay",
                                        tint = Color.Unspecified,
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Column {
                                        Text(
                                            text = "Pay with Pesepay",
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                shadow = Shadow(
                                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                                    offset = Offset(0f, 2f),
                                                    blurRadius = 10f
                                                )
                                            ),
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                        Text(
                                            text = if (currentStep == CheckoutStep.Review) "Tap to continue then confirm in the next step" else "Tap to complete your Pesepay payment",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.82f)
                                        )
                                    }
                                }
                            }
                            Text(
                                text = "You’ll be charged securely via Pesepay using your selected method.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            GhostButton(
                                text = "Edit order",
                                modifier = Modifier.weight(1f)
                            ) {
                                onBack()
                            }
                        }
                    } else {
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            PrimaryButton(
                                text = when (selectedMethod) {
                                    CheckoutPaymentMethod.Card -> "Pay with card"
                                    CheckoutPaymentMethod.Wallet -> "Confirm from wallet"
                                    CheckoutPaymentMethod.Pesepay -> "Pay"
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                when (selectedMethod) {
                                    CheckoutPaymentMethod.Card -> onPlaceOrder("card", totalAmount)
                                    CheckoutPaymentMethod.Wallet -> onPlaceOrder("wallet", totalAmount)
                                    CheckoutPaymentMethod.Pesepay -> onPlaceOrder("pesepay", totalAmount)
                                }
                            }
                            GhostButton(
                                text = "Edit",
                                modifier = Modifier.weight(1f)
                            ) {
                                onBack()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PaymentMethodChip(
    label: String,
    isSelected: Boolean,
    accentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(goTickyShapes.medium)
            .background(
                if (isSelected) accentColor.copy(alpha = 0.22f)
                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
            .pressAnimated()
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(accentColor)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun CheckoutStepDot(
    stepNumber: Int,
    isActive: Boolean,
) {
    val size = if (isActive) 18.dp else 12.dp
    val background = if (isActive) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor = if (isActive) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stepNumber.toString(),
            style = MaterialTheme.typography.labelSmall,
            color = contentColor,
            fontSize = 9.sp
        )
    }
}

private data class MetallicPalette(
    val gradient: List<Color>,
    val glow: Color,
    val ink: Color,
    val darkStroke: Color,
)

private fun metallicPalette(type: TicketType): MetallicPalette = when (type) {
    TicketType.EarlyBird -> MetallicPalette(
        gradient = listOf(Color(0xFFF5F3E8), Color(0xFFE9E4D4)),
        glow = Color(0xFFFFF3CC),
        ink = Color(0xFF4A3E2B),
        darkStroke = Color(0xFFB8AA84)
    )
    TicketType.General -> MetallicPalette(
        gradient = listOf(Color(0xFFE8ECF1), Color(0xFFD7DBE2)),
        glow = Color(0xFFE1E6EF),
        ink = Color(0xFF2E3848),
        darkStroke = Color(0xFF8A93A0)
    )
    TicketType.VIP -> MetallicPalette(
        gradient = listOf(Color(0xFFF5F7FA), Color(0xFFDDE2E8)),
        glow = Color(0xFFC0C7D2),
        ink = Color(0xFF1F2733),
        darkStroke = Color(0xFF9DA6B2)
    )
    TicketType.GoldenCircle -> MetallicPalette(
        gradient = listOf(Color(0xFFFFF4CC), Color(0xFFF6E3A6)),
        glow = Color(0xFFFFE08A),
        ink = Color(0xFF3E2B10),
        darkStroke = Color(0xFFB9922C)
    )
}

@Composable
private fun DetailChip(title: String, value: String, accent: Color) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(
                shadow = Shadow(
                    color = accent.copy(alpha = 0.35f),
                    offset = Offset(0f, 0.8f),
                    blurRadius = 4f
                )
            ),
            color = accent.copy(alpha = 0.85f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                shadow = Shadow(
                    color = accent.copy(alpha = 0.5f),
                    offset = Offset(0f, 1.2f),
                    blurRadius = 7f
                )
            ),
            color = accent
        )
    }
}

@Composable
private fun rememberQrPainter(seed: String, size: Dp, stroke: Color): Painter {
    val density = LocalDensity.current
    val sizePx = with(density) { size.toPx().toInt().coerceAtLeast(120) }
    val bitmap = remember(seed, sizePx) {
        // On Android/JVM this uses ZXing-backed actuals; on other targets falls back to blank bitmaps.
        platformQrBitmap(seed.ifBlank { "ticket-qr" }, sizePx, stroke, Color.White)
    }
    return BitmapPainter(bitmap)
}

private fun buildBarcodeNumericId(ticket: TicketPass): String {
    // Structure: CC DDDD OOOOO C
    // CC   = 2-digit country/region code
    // DDDD = 4-digit date block (MMYY) derived from event month
    // OOOOO= 5-digit order/sequence per ticket
    // C    = checksum (sum of first 11 digits mod 10)

    val event = sampleEvents.firstOrNull { it.title == ticket.eventTitle }

    // Country code by city (01 = Zimbabwe, 02 = Botswana, 00 = unknown)
    val country = when (event?.city) {
        "Harare", "Bulawayo", "Victoria Falls" -> "01"
        "Gaborone", "Maun", "Francistown" -> "02"
        else -> "00"
    }

    // Month to MM, year fixed to demo year 24/25 based on month bucket
    val month = when (event?.month) {
        "January" -> "01"
        "February" -> "02"
        "March" -> "03"
        "April" -> "04"
        "May" -> "05"
        "June" -> "06"
        "July" -> "07"
        "August" -> "08"
        "September" -> "09"
        "October" -> "10"
        "November" -> "11"
        "December" -> "12"
        else -> "00"
    }
    val year = if (month in listOf("01", "02", "03", "04")) "24" else "25"
    val dateBlock = month + year // MMYY

    // Order block: 5-digit sequence from ticket id or hash
    val numericId = ticket.id.filter { it.isDigit() }.toIntOrNull()
    val baseOrder = numericId ?: kotlin.math.abs((ticket.qrSeed + ticket.holderInitials).hashCode())
    val order = (baseOrder % 100_000)
    val orderBlock = order.toString().padStart(5, '0')

    var first11 = country + dateBlock + orderBlock
    if (first11.length != 11) {
        first11 = first11.take(11).padEnd(11, '0')
    }

    val sum = first11.sumOf { it.digitToInt() }
    val checksum = (sum % 10)

    return first11 + checksum.digitToChar()
}

@Composable
private fun rememberBarcodePainter(seed: String, stroke: Color): Painter {
    val density = LocalDensity.current
    val widthPx = with(density) { 360.dp.toPx().toInt().coerceAtLeast(220) }
    val heightPx = with(density) { 120.dp.toPx().toInt().coerceAtLeast(80) }
    val bitmap = remember(seed, widthPx, heightPx) {
        platformBarcodeBitmap(seed.ifBlank { "ticket-barcode" }, widthPx, heightPx, stroke, Color.White)
    }
    return BitmapPainter(bitmap)
}
