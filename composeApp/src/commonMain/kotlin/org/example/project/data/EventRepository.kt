package org.example.project.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import org.example.project.currentInstant
import kotlin.random.Random
import org.example.project.platform.EventImageStorage

data class SavedEvent(
    val event: OrganizerEvent,
    val publicId: String,
    val flyerUrl: String,
)

class EventRepository(
    private val imageStorage: EventImageStorage,
) {

    private fun normalizePriceFrom(raw: String): String {
        val trimmed = raw.trim()
        if (trimmed.isBlank()) return ""
        if (trimmed.contains("from", ignoreCase = true)) return trimmed
        if (trimmed.contains("free", ignoreCase = true)) return trimmed
        if (trimmed.contains("$")) return trimmed

        val numeric = trimmed.filter { it.isDigit() || it == '.' }
        val value = numeric.toDoubleOrNull() ?: return trimmed
        val rendered = if (value % 1.0 == 0.0) value.toInt().toString() else value.toString()
        return "From $$rendered"
    }

    @kotlin.OptIn(kotlin.time.ExperimentalTime::class)
    private fun isoNow(): String = currentInstant().toString()

    @kotlin.OptIn(kotlin.time.ExperimentalTime::class)
    private fun generateEventId(): String {
        val now = currentInstant().toEpochMilliseconds()
        val salt = Random.nextInt(1000, 9999)
        return "evt-${now}-${salt}"
    }

    private suspend fun loadOrganizerProfile(uid: String): Map<String, Any?> =
        runCatching {
            val doc = Firebase.firestore.collection("users").document(uid).get()
            if (!doc.exists) return@runCatching emptyMap()
            val fullName = doc.get<String?>("displayName")?.trim().orEmpty()
            val email = doc.get<String?>("email")?.trim().orEmpty()
            val phoneCode = doc.get<String?>("phoneCode")?.trim().orEmpty()
            val phoneNumber = doc.get<String?>("phoneNumber")?.trim().orEmpty()
            val birthday = doc.get<String?>("birthday")?.trim().orEmpty()
            val gender = doc.get<String?>("gender")?.trim().orEmpty()
            val countryName = doc.get<String?>("countryName")?.trim().orEmpty()
            val photoUri = doc.get<String?>("photoUri")
            mapOf(
                "uid" to uid,
                "fullName" to fullName,
                "email" to email,
                "phoneCode" to phoneCode,
                "phoneNumber" to phoneNumber,
                "birthday" to birthday,
                "gender" to gender,
                "countryName" to countryName,
                "photoUri" to photoUri,
                "syncedAt" to isoNow(),
            )
        }.getOrDefault(emptyMap())

    private suspend fun ensureAuthUid(): String? {
        val auth = Firebase.auth
        if (auth.currentUser == null) {
            runCatching { auth.signInAnonymously() }
        }
        return Firebase.auth.currentUser?.uid
    }

    private fun cityToLatLng(city: String): Pair<Double, Double> =
        when (city.trim()) {
            "Harare" -> -17.8292 to 31.0522
            "Bulawayo" -> -20.1325 to 28.6265
            "Gaborone" -> -24.6282 to 25.9231
            "Victoria Falls" -> -17.9243 to 25.8562
            "Maun" -> -19.9833 to 23.4167
            "Francistown" -> -21.1700 to 27.5072
            else -> -17.8292 to 31.0522
        }

    suspend fun saveEvent(
        input: EventDraftInput,
        localFlyerUri: String?,
        onUploadProgress: (Float) -> Unit = {},
    ): Result<SavedEvent> {
        val uid = ensureAuthUid() ?: return Result.failure(IllegalStateException("No auth session"))
        val firestore = Firebase.firestore
        val authUser = Firebase.auth.currentUser
        val organizerCollection = firestore.collection("organizers").document(uid).collection("events")
        val eventId = generateEventId()
        val newDoc = organizerCollection.document(eventId)

        // Upload flyer if a local URI was provided; otherwise use existing URL (if any).
        val finalFlyerUrl = when {
            !localFlyerUri.isNullOrBlank() -> {
                onUploadProgress(0.05f)
                val uploaded = imageStorage.uploadEventFlyer(uid, eventId, localFlyerUri) { p ->
                    onUploadProgress(0.1f + 0.8f * p)
                }
                onUploadProgress(if (uploaded != null) 1f else 0f)
                uploaded ?: input.flyerUrl
            }
            input.flyerUrl.startsWith("http", ignoreCase = true) -> input.flyerUrl
            else -> ""
        }

        val (fallbackLat, fallbackLng) = cityToLatLng(input.city)
        val lat = input.lat ?: fallbackLat
        val lng = input.lng ?: fallbackLng
        val nowIso = isoNow()
        val organizerProfileData = loadOrganizerProfile(uid)
        val normalizedPriceFrom = normalizePriceFrom(input.priceFrom)
        val payload = mapOf(
            "organizerId" to uid,
            "title" to input.title,
            "city" to input.city,
            "venue" to input.venue,
            "country" to input.country,
            "dateLabel" to input.dateLabel,
            "status" to input.status,
            "priceFrom" to normalizedPriceFrom,
            "isApproved" to input.isApproved,
            "ticketCount" to input.ticketCount,
            "tickets" to mapOf(
                "earlyBird" to input.ticketEarlyBird,
                "general" to input.ticketGeneral,
                "vip" to input.ticketVip,
                "goldenCircle" to input.ticketGoldenCircle,
            ),
            "flyerUrl" to finalFlyerUrl,
            "companyName" to input.companyName,
            "lat" to lat,
            "lng" to lng,
            "views" to 0,
            "saves" to 0,
            "sales" to 0,
            "isVerified" to false,
            "createdAt" to nowIso,
            "updatedAt" to nowIso,
        )
        val organizerPayload = payload.toMutableMap().apply {
            if (organizerProfileData.isNotEmpty()) {
                this["organizerProfile"] = organizerProfileData
            }
        }

        return runCatching {
            newDoc.set(organizerPayload)
            val publicPayload = payload.toMutableMap().apply {
                this["status"] = if (input.isApproved) "Approved" else "In Review"
            }
            firestore.collection("events").document(eventId).set(publicPayload)

            // Ensure organizer profile exists for admin dashboard linkage.
            val userDoc = firestore.collection("users").document(uid)
            val existingRole = runCatching { userDoc.get().get<String?>("role") }.getOrNull()
            val roleToSet = existingRole ?: "organizer"
            val displayName = authUser?.displayName ?: input.companyName.ifBlank { "Organizer" }
            val email = authUser?.email
            val organizerProfile = mutableMapOf<String, Any?>().apply {
                this["uid"] = uid
                this["displayName"] = displayName
                this["companyName"] = input.companyName
                this["role"] = roleToSet
                this["createdAt"] = nowIso
                this["updatedAt"] = nowIso
                if (!email.isNullOrBlank()) {
                    this["email"] = email
                }
            }
            userDoc.set(organizerProfile, merge = true)

            // Mirror organizer metadata into /organizers/{uid} for admin and event detail views.
            val organizerDocPayload = mutableMapOf<String, Any?>().apply {
                this["uid"] = uid
                this["name"] = organizerProfile["displayName"] ?: displayName
                this["companyName"] = input.companyName
                this["role"] = roleToSet
                this["createdAt"] = nowIso
                this["updatedAt"] = nowIso
                organizerProfile["countryName"]?.let { this["countryName"] = it }
                organizerProfile["phoneCode"]?.let { this["phoneCode"] = it }
                organizerProfile["phoneNumber"]?.let { this["phoneNumber"] = it }
                organizerProfile["birthday"]?.let { this["birthday"] = it }
                organizerProfile["gender"]?.let { this["gender"] = it }
                organizerProfile["email"]?.let { this["email"] = it }
                organizerProfile["photoUri"]?.let { this["photoUri"] = it }
            }
            firestore.collection("organizers").document(uid).set(organizerDocPayload, merge = true)

            val organizerEvent = OrganizerEvent(
                id = "org-$eventId",
                eventId = eventId,
                title = input.title.ifBlank { "Untitled event" },
                city = input.city.ifBlank { "City TBD" },
                venue = input.venue.ifBlank { "Venue TBD" },
                dateLabel = input.dateLabel,
                priceFrom = if (normalizedPriceFrom.isBlank()) "From $0" else normalizedPriceFrom,
                isApproved = input.isApproved,
                status = input.status,
                views = 0,
                saves = 0,
                sales = 0,
                ticketCount = input.ticketCount,
                isVerified = false,
            )
            SavedEvent(
                event = organizerEvent,
                publicId = eventId,
                flyerUrl = finalFlyerUrl,
            )
        }
    }

    suspend fun fetchOrganizerEvents(): Result<List<OrganizerEvent>> {
        val uid = ensureAuthUid() ?: return Result.failure(IllegalStateException("No auth session"))
        val firestore = Firebase.firestore
        return runCatching {
            val snap = firestore.collection("organizers").document(uid).collection("events").get()
            snap.documents.mapNotNull { doc ->
                val title = doc.get<String?>("title") ?: return@mapNotNull null
                val isApproved = doc.get<Boolean?>("isApproved") ?: false
                val normalizedPriceFrom = normalizePriceFrom(doc.get<String?>("priceFrom") ?: "")
                OrganizerEvent(
                    id = "org-${doc.id}",
                    eventId = doc.id,
                    title = title,
                    city = doc.get<String?>("city") ?: "",
                    venue = doc.get<String?>("venue") ?: "",
                    dateLabel = doc.get<String?>("dateLabel") ?: "",
                    priceFrom = normalizedPriceFrom,
                    isApproved = isApproved,
                    status = doc.get<String?>("status") ?: "Draft",
                    views = (doc.get<Long?>("views") ?: 0L).toInt(),
                    saves = (doc.get<Long?>("saves") ?: 0L).toInt(),
                    sales = (doc.get<Long?>("sales") ?: 0L).toInt(),
                    ticketCount = (doc.get<Long?>("ticketCount") ?: 0L).toInt(),
                    isVerified = doc.get<Boolean?>("isVerified") ?: false,
                )
            }
        }
    }
}
