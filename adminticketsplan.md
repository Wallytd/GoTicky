---
description: admin ticket scanning data plan
---

## Goal
Enable Admin Home > Tickets to list and scan tickets stored under `users/{uid}/tickets/{ticketId}` without breaking checkout or user views. Provide a path for optional mirroring to a root collection if needed.

## Current state (before fix)
- Checkout writes tickets only to `users/{uid}/tickets/{ticketId}` (see App.kt `persistTicketForUser`).
- Admin dashboard tried to read a non-existent root `tickets` collection, so no tickets loaded.
- Firestore rules allowed root `/tickets` admin access but did not grant admins access to per-user tickets.

## Decision
Use **collection group** queries over `users/*/tickets` for admin loading/scanning. This preserves the existing checkout storage and user reads. Mirroring to a root collection is optional; not required for the dashboard.

## Data model
- Source of truth: `users/{uid}/tickets/{ticketId}` documents (ownerId, eventId, eventTitle, venue, city, dateLabel, seat, status, type, holderName/Initials, qrSeed, purchaseAt, scanned?, scannedAt?, scanCount?, scanProhibited?).
- Admin read path: `collectionGroup("tickets")` with grouping by `eventId` in app code.
- Optional audit: `users/{uid}/tickets/{ticketId}/scanEvents/{scanEventId}` for immutable scan trail (already modeled in rules for root; can be reused under user path if desired).

## App changes
1) Admin fetch uses `collectionGroup("tickets")` (TicketRepository.fetchTicketGroups) and gracefully groups by eventId.
2) No changes to checkout or user-facing persistence; still writes under `users/{uid}/tickets`.

## Firestore rules changes
- Allow admins to read per-user tickets and to perform scan updates on them via `validTicketScanUpdate`.
- Keep owner read/write intact for checkout and user My Tickets.
- Root `/tickets` rules remain but are not required; can be left unused or used for future mirroring.

## If mirroring is desired later
- Add a Cloud Function/Backend trigger on `users/{uid}/tickets/{ticketId}` create/update to write a sanitized copy to `/tickets/{ticketId}` with `ownerId`, `eventId`, `status`, `type`, `scanned`, `scannedAt`, `scanCount`, `eventTitle`, `venue`, `city`, `dateLabel`, `seat`, `holderName`.
- Ensure rules on `/tickets` stay admin-only for writes/updates; allow owner read if needed.
- Keep scan events either under the mirrored doc (`/tickets/{ticketId}/scanEvents`) or under the user doc; pick one authoritative location.

## Rollout steps
1) Deploy updated Firestore rules (includes admin access to per-user tickets).
2) Ship app update (admin fetch now uses collectionGroup).
3) Test flows:
   - Checkout -> verify ticket appears in `users/{uid}/tickets`.
   - My Tickets loads as before.
   - Admin Home > Tickets loads groups (at least from seeded test tickets).
   - Scan update (once implemented) succeeds as admin and is rejected for non-admin.

## Safety
- No change to user storage path; backward compatible.
- Admin access is gated by `isAdminUser()` in rules.
- Collection group query respects existing security; offline-safe via GitLive client.
