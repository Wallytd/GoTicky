<!-- Stage-by-stage build plan for GoTicky “best-in-class” ticketing & event app -->
<!-- Approach: Kotlin Multiplatform Compose (Android/iOS/Web/Desktop), Firebase backend (already in deps), inspired by leading US ticketing apps (Ticketmaster, SeatGeek, Eventbrite, AXS, StubHub, Vivid Seats). Colors: pull from existing GoTicky web app; if palette updates, re-swatch before implementation. -->

# GoTicky – Best-in-Class Ticketing & Event Experience

## Stage 0: Research & Foundations
1) Brand & palette finalization  
   - Extract primary/secondary/neutral colors from https://goticky.lovable.app/ (sample assets/screens, capture gradients if present).  
   - Define semantic roles: Primary (CTA), Accent (floating actions), Success (ticket secured), Warning (timeouts), Background (layered surfaces), Text (high/medium/low).  
   - Typography: pick expressive modern family (e.g., “Space Grotesk”/“Sora”/“DM Sans”) with weights for headings, body, labels.  
2) Competitive feature benchmarking (Ticketmaster, SeatGeek, Eventbrite, AXS, StubHub, Vivid Seats, Universe):  
   - Must-have: mobile tickets with rotating QR/NFC, interactive seat maps, transparent fees, waitlist, price alerts, filters (date/genre/city/price), map-based discovery, social sharing, saved favorites, push for drops/price moves, secure resale, transfer, wallet.  
   - Delight: “best seat score” (value rating), vibe-based browse (color-coded chips), collaborative planning (share pack), loyalty/points, dynamic bundles (ticket + add-ons), AI concierge for recommendations.  
3) Architecture decisions  
   - Keep UI in Compose Multiplatform commonMain; platform shells minimal.  
   - State: MVVM with ViewModels + immutable state models; coroutines/Flow for async.  
   - Data: Repository interfaces in commonMain; platform-specific impls for Firebase (Auth/Firestore/Storage/Messaging), payments (placeholder abstraction for Stripe/Google Pay/Apple Pay), location, deep links.  
   - Navigation: Typed navigator (stack) with screen enum; support deep links to events/tickets.  
   - Theming system: color roles, shape system, motion specs (buttons, lists, FABs, progress, spinners) per user rules (visible animations).  
4) Data model sketch  
   - User, Organizer, Venue (with geo), Event (schedule, tags, media, seat map/ref map), TicketType (pricing, fees), Ticket (status, QR), Order, Wishlist, Notification, Review, AddOn (parking/merch), PriceAlert.  
5) Backend alignment  
   - Firestore collections plan & security rules draft (role-based, ownership, anti-scalping constraints).  
   - Realtime updates for inventory/price; FCM for drops/alerts.  

## Stage 1: Design System & Scaffolding
1) Compose theme: implement GoTickyColorScheme, typography, shapes, elevations, blur/Glass surfaces, gradient backgrounds if brand uses them.  
2) Motion: reusable animations for buttons/clickable text, FAB, progress bars, loading spinners, list reveal; back-button animation per rule (scale/rotate + slide transition).  
3) Iconography: assign subtle distinct colors per icon category (discover, calendar, map, ticket, wallet, alerts, profile).  
4) Layout primitives: page scaffolds (top bar, bottom nav, FAB), card styles (event, organizer, ticket, add-on), chips, pill filters, banners.  
5) Navigation shell: bottom bar (Home/Discover, Browse, My Tickets, Alerts, Profile) + modal/flow for checkout; deep link handling skeleton.  

## Stage 2: Core Discovery & Event Surfacing
1) Home/Discover screen  
   - Hero carousel (upcoming marquee events), animated category chips, smart sections (Near You, Trending, For You, Friends are going).  
   - Map toggle (mini map preview).  
2) Search + filters  
   - Full-text search; filters for date range, price, genre, distance, venue type; sort by “Best Value” (seat score), popularity, soonest.  
   - Recent searches & saved filters.  
3) Event list & details  
   - Event card with gradient overlays, price-from, fee transparency badge, animated CTA.  
   - Event detail: gallery, schedule, lineup, venue map preview, seat map entry, reviews, FAQs, add-ons, share/alert/favorite actions.  
4) Seat selection  
   - Interactive seat map placeholder (SVG/canvas stub in commonMain; platform hooks for native map later).  
   - Seat score visualization (color-coded), quantity selector, dynamic pricing indicator.  

## Stage 3: Ticketing, Checkout, and Wallet
1) Checkout flow  
   - Steps: tickets -> add-ons -> payment -> review -> confirmation.  
   - Fees breakdown, promo codes, payment abstraction (Stripe/GP/Apple Pay ready).  
   - Accessibility: large tap targets, progress bar with visible animation.  
2) Orders & tickets  
   - My Tickets list; ticket detail with animated QR, countdown to event, transfer/share actions, add-to-wallet (pass stub).  
   - Offline-ready ticket caching.  
3) Resale & transfer  
   - Transfer flow (select recipient, confirmation).  
   - List for resale (price guidance, constraints).  

## Stage 4: Alerts, Notifications, and Personalization
1) Price alerts & drops  
   - Create/edit alerts per event/section/price; notify via FCM; feed cards with animated badges.  
2) Recommendations  
   - Rule-based first pass (location + genres + past purchases + popularity).  
   - “For you” strip with animated skeleton loaders.  
3) Social & sharing  
   - Deep-link share; collaborative “group plan” placeholder (invite friends).  

## Stage 5: Organizer & Content Tools (MVP scope)
1) Organizer dashboard (consumer-facing shell)  
   - Create event stub (title, schedule, venue, media upload, ticket types).  
   - Basic analytics cards (views, saves, sales).  
2) Moderation  
   - Verified organizer badge; report flow for events.  

## Stage 6: Hardening, Quality, and Launch Readiness
1) Security & rules  
   - Firestore rules, auth flows (Email/Google/Apple), rate limits.  
2) Performance & offline  
   - Caching layers, pagination, image optimization, lazy lists with placeholders.  
3) Testing  
   - Unit: ViewModels/reducers. UI: snapshot/robot tests where feasible.  
4) Observability  
   - Analytics events map (view, search, select seat, add-to-cart, checkout start/success, alert create).  
5) Store-readiness  
   - App icons/splash (animated), privacy policy, TOS links.  

## Stage 7: Polish & Delight
1) Microinteractions  
   - Lottie/animated vectors for success, loading spinners, progress bars; FAB lift/rotate; ripple tuned.  
2) Empty & error states  
   - Illustrated cards with CTA to discover or retry.  
3) Accessibility & internationalization  
   - Dynamic type scaling, contrast checks against palette, RTL readiness, copy hooks.  

## Stage 8: Delivery Plan & Sequencing
1) Iteration order (vertical slices using current MVP)  
   - 0: Theme + system + nav – GoTickyTheme, layout primitives, bottom bar + FAB.  
   - 1: Discover/search/event detail – Home, QuickSearchBar, Hero, Event detail.  
   - 2: Seat selection stub + pricing/filters – SeatPreview stub, price-from context, alert entry from detail.  
   - 3: Checkout + wallet + tickets – CheckoutScreen (mock), Tickets list + ticket detail, wallet/add-to-wallet stub.  
   - 4: Alerts/recs/social share – Alerts list, AlertCard, recommendations row + personalize dialog, deep-link share.  
   - 5: Organizer mini-dashboard – Profile organizer entry, OrganizerDashboardScreen, CreateEventScreen (draft events).  
   - 6: Hardening/polish – motion, performance, analytics, accessibility, empty/error states.  
2) Milestones & demos  
   - End of each slice: runnable Android build (iOS/Web as available) + 20–60s screen recording of that flow.  
   - Demo recipes: discover event; preview seats; buy ticket; live with alerts/recs; organizer overview; polished E2E.  
3) Risk/mitigation (explicitly surfaced)  
   - Seat maps: keep SeatPreview as stub; later plug native map behind a stable interface.  
   - Payments: CheckoutScreen stays mocked until keys available; guard real processors behind feature flag.  
   - Palette/source of truth: re-sample GoTicky web colors before major theme passes; keep tokens in theme only.  

## Stage 9: Visual Direction & Implementation Plan
1) Palette + tokens (finalize after re-swatch)
   - Extract brand primaries/accents from goticky.lovable.app; define semantic tokens (primary, accent, success, warning, surface, surfaceVariant, on* roles).
   - Lock gradient duotones for hero/CTA (primary → accent) and supporting gradients for banners/cards.
2) Texture & depth
   - Layered glass surfaces with subtle grain/noise; soft shadow stacks for elevation.
   - Neon edge/halo on hover/press for cards and primary CTAs.
3) Motion system application
   - Apply visible animations to: buttons/clickable text, progress bars, loading spinners, FAB; keep back-button animation per rule (scale/rotate + slide).
   - Staggered list/card reveals and shimmer placeholders for loading states.
4) Iconography direction
   - Assign subtle distinct colors per icon category (discover, calendar, map, ticket, wallet, alerts, profile) to reduce visual monotony.
5) Seat/map styling (stub-ready)
   - Cool neutral base with accent highlights for available seats/sections; maintain contrast for disabled/unavailable.
6) Typographic voice
   - Expressive modern family (Space Grotesk/Sora/DM Sans or equivalent) with bold weights for hero/CTA and comfortable body copy.
7) Deliverables for this stage
   - Updated theme tokens file(s) in commonMain.
   - Documented gradient/texture specs and motion application notes.
   - Visual QA checklist to ensure consistent “VIP night out” feel across screens.

## Stage 10: Delivery & QA Checklist
1) Tokens & theming
   - Freeze final palette (brand + semantic) in `ui/theme/Color.kt`; verify icon category tints are distinct.
   - Typography: confirm chosen headline/body families (Space Grotesk/Sora/DM Sans) are wired when assets are available.
2) Components & motion
   - Ensure GoTickyTheme exports colors/shapes/typography; GlowCard/NeonBanner/EventCard use glass + grain + edge halo.
   - Buttons/FAB/text/CTA/alerts use visible press/hover animations; progress bars/spinners/FAB follow motion specs.
3) Screens completeness (MVP slices)
   - Home/Discover, Search+Filters, Event Detail, Seat Select stub, Checkout, My Tickets, Ticket Detail, Alerts, Profile basics, Organizer stub all compile in commonMain.
4) Interaction polish
   - Back buttons use required animation config; shimmer/skeletons present for loading lists; FAB fade-on-scroll behavior validated.
5) Analytics & feature flags
   - Event map: view/search/select seat/add-to-cart/checkout start/success/alert create, etc., logged per flow.
   - Payments guarded by feature flag (mock flow by default).
6) Backend & data scaffolding
   - Draft Firestore schemas/rules for events/tickets/orders/alerts/recommendations; document in repo.
7) Release readiness
   - Build checks for Android/iOS/Web/Desktop targets as available.
   - Assets: app icon/splash placeholders; links for Privacy/TOS ready; demo recording recipes per slice.  

> Notes:  
> - Ensure icon colors differ subtly across categories.  
> - All interactive elements use visible animations (buttons, clickable text, progress bars, spinners, FAB).  
> - Back buttons use provided scale/rotate + slide animation.  
> - Re-swatch GoTicky web colors before final theme; if palette unavailable, request assets/screenshot.  
