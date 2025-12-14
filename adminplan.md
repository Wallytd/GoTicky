# GoTicky Admin Role Dashboard — Implementation Plan

## 1) Context & what exists today
- Public (customer/organizer) experience is rich and motion-forward: Home screen with hero carousel, quick search, category row, entertainment news, heat highlights, recommendations, map preview, and popular nearby stack, all with animated cards and neon/glass styling @composeApp/src/commonMain/kotlin/org/example/project/App.kt#1296-1609 @composeApp/src/commonMain/kotlin/org/example/project/App.kt#2608-2834 @composeApp/src/commonMain/kotlin/org/example/project/App.kt#5606-5767.
- Organizer-facing stub already exists: Organizer dashboard with metric cards, event list cards, and “Create NEW Event” FAB plus event-detail stub flows @composeApp/src/commonMain/kotlin/org/example/project/App.kt#4979-5069. Sample organizer data provided in `SampleOrganizer.kt`.
- Theming/motion system: glass/grain surfaces, neon edges, visible press/hover animations, animated progress bars/spinners, category icon colors, animated back button, and motion primitives (GlowCard, NeonTextButton, PrimaryButton, etc.) @composeApp/src/commonMain/kotlin/org/example/project/ui/components/Buttons.kt#1-198 and TopBar/back button in AppBars.
- Build direction captured in `buildplan.md` (stages 0–10) including motion and icon-color rules.

## 2) Goals for Admin role
- Provide a modern, interactive dashboard for Admins to manage public-facing content and roles with high confidence and clear auditability.
- Streamline organizer event application intake → review → approval/verification → publish pipeline.
- Offer real-time visibility into platform health (events, organizers, alerts, issues) with actionable controls.
- Reuse existing visual language (glass + neon + animated interactions) and category icon color semantics while keeping admin affordances crisp and legible.

## 3) Personas & roles
- **Admin**: full trust, can approve/reject events, verify organizers, manage roles, moderate content, publish/unpublish, and view audit trails.
- **Reviewer/Moderator** (scoped admin): can process event applications, flag issues, request changes, but cannot change platform roles.
- **Support** (read + escalate): view state, leave internal notes, escalate to Admin.

## 4) Information architecture (admin)
1) **Dashboard Home**: KPIs, queues, alerts, shortcuts.
2) **Event Applications**: inbox/queue with filters; review workspace; bulk actions.
3) **Organizer Management**: verification, trust signals, flags, history.
4) **Public Role Management**: user/organizer roles, suspensions, feature flags.
5) **Content Moderation**: reports, takedowns, edits.
6) **Catalog Control**: publish/unpublish events, featured hero slots, promos.
7) **Analytics & Logs**: event performance, approvals SLAs, audit trails.
8) **Settings**: policies, notifications, integration keys, review templates.

## 5) Core capabilities & interactions
### 5.1 Dashboard Home
- KPI cards: pending applications, approvals today, SLA breach risk, flagged organizers, live/published counts.
- Live tiles: “Needs attention” (aging submissions), “Top risk flags”, “Recently approved”.
- Activity feed with filters (events, organizers, moderation) and quick links.
- Global search (events, organizers, users) with typeahead.

### 5.2 Event application pipeline
- Queue with states: **New → In Review → Needs Info → Approved → Rejected → Scheduled/Published**.
- Filters: state, city, category, organizer, risk score, submission age, attachments completeness.
- Review workspace:
  - Left: application summary (title, venue, schedule, ticket tiers, images, docs).
  - Center: validation checklist (identity, venue proof, pricing fairness, content quality).
  - Right: actions (approve/publish, request changes, reject) with required rationale.
  - Attachments preview (IDs, insurance, permits), comments thread (internal), timeline.
  - Risk flags: duplicate event, suspicious pricing, organizer strikes, missing permits.
  - Auto-validation signals (completeness %, required fields met).
- Bulk actions: approve multiple low-risk applications, assign reviewer, re-queue.
- Outcome logging: every transition writes audit log with actor, timestamp, rationale.

### 5.3 Organizer management
- Organizer profile sheet: verification status, trust score, strike history, KYC docs, past events with performance (views/saves/sales).
- Actions: verify/unverify, freeze/unfreeze, escalate, request re-KYC, assign AM (account manager).
- Badge control: “Verified” toggle propagates to public cards (align with existing organizer badge usage).

### 5.4 Public role & user management
- Role matrix: Customer, Organizer, Admin, Reviewer; assign/revoke roles with reason and audit.
- Suspension/limitations: throttle publishing, hide events, lock payouts (future).
- Feature flags: toggle beta features (e.g., social share, alerts) per role/tenant.

### 5.5 Content moderation
- Report queue for public listings (images, copy, tags). Quick actions: edit, blur image, unpublish, warn organizer.
- Version history + diffs for event copy; revert capability.

### 5.6 Catalog & promo control
- Publish/unpublish, schedule go-live, set featured/hero slot (map to `HeroCarousel` slide data).
- Pricing sanity checks (min/max bounds, fee transparency flag).
- Spotlight campaigns: configure banners, badges (“Hot”, “Limited”) used in event cards.

### 5.7 Analytics & observability
- Metrics: approval rate, time-to-approve, rejection reasons, organizer performance, regional distribution.
- Logs: per-entity audit trail (event, organizer, user, role changes).
- Alerts: SLA breach warnings, surge in reports, anomaly detection (future hook).

### 5.8 Notifications & communications
- Templates for “Approved”, “Needs info”, “Rejected” emails/push; include custom note.
- Internal mentions in comments; watchlist for organizers/events.

## 6) UX/UI direction
- Reuse glass/grain surfaces, neon edge halos, and animated interactions from public UI. Buttons, clickable text, progress bars, spinners, FABs must remain visibly animated per user rules.
- Back buttons must use the scale/rotate + slide transition already present in `AnimatedBackButton`.
- Layout: two-pane adaptive layout on wide screens (queue + detail). On mobile, stack with bottom sheet for actions.
- Icon tints: maintain distinct subtle colors per category; add admin-specific palette (e.g., warning amber for risk, teal for approvals).
- States: skeletons/shimmers for loading queues; animated progress for validation completeness; badges/pills for risk and state.
- Accessibility: high contrast on admin data tables, focus states, keyboard support for desktop/web targets.

## 7) Data & models (to add/extend)
- `AdminUser` (id, role, permissions, displayName).
- `EventApplication` (id, organizerId, eventDraft, status, riskScore, submittedAt, updatedAt, assignedReviewerId, checklist statuses, attachments[], comments[]).
- `OrganizerReview` (organizerId, kycStatus, trustScore, strikes[], notes).
- `AuditLog` (entityType, entityId, action, actorId, timestamp, payload, rationale).
- `Report` (id, targetType, targetId, reason, evidence, state).
- `FeatureFlag` (key, audience, enabled).

## 8) API/backend & integration notes
- Gate admin APIs behind RBAC; enforce server-side checks for status transitions.
- Idempotent status updates; optimistic UI with server reconciliation.
- File handling: upload/preview storage pointers; virus scan hook (future).
- Audit logging required for every state change and role update.
- Analytics events: admin_view, app_open, app_approve, app_reject, needs_info, organizer_verify, role_change, publish_toggle.

## 9) Navigation & flows (initial screens)
1) **AdminDashboardScreen**: KPIs, alerts, queues preview, quick links.
2) **ApplicationsScreen**: list + filters + detail pane; actions; comments.
3) **OrganizerScreen**: list + detail; verify/freeze; history.
4) **ModerationScreen**: reports inbox + resolution.
5) **CatalogScreen**: publish/unpublish, feature slot manager, hero slot assignment.
6) **Settings/AuditScreen**: feature flags, role matrix, audit logs.

## 10) Implementation slices (proposed sequencing)
1) **Shell & Nav**: Admin entry point, tabs/rail, role gate, theme reuse, back-button animation.
2) **Dashboard Home**: KPI cards, alerts widget, activity feed, global search stub.
3) **Applications Queue + Detail**: list with filters, detail workspace, actions, comments, audit log write.
4) **Organizer Management**: list/detail, verify/freeze, trust summary, audit.
5) **Moderation + Catalog Controls**: reports, publish/unpublish, hero slot assignment using hero slide model.
6) **Settings & Audit**: role matrix UI, feature flags, audit log viewer, notifications templates.
7) **Polish**: motion, skeletons, accessibility, analytics events, empty/error states.

## 10.1) Phase-by-phase build instructions (admin role interface)
**Phase 0 — Foundations & gating**
- Add `Admin`/`Reviewer` roles to auth/feature flags; guard admin entry via role gate and feature flag.
- Create navigation entry (tab/rail/drawer) visible only to admins; ensure back-button animation matches global rule.
- Reuse GoTickyTheme; add admin-specific tokens for risk/success/neutral (no new palette drift).
- Stub data models in commonMain: `AdminUser`, `EventApplication`, `AuditLog`, `Report`, `FeatureFlag`, `OrganizerReview`.

**Phase 1 — Admin shell + dashboard home**
- Screen: `AdminDashboardScreen` with hero KPI cards (pending apps, approvals today, SLA risk, flagged organizers, published count).
- Widgets: “Needs attention” list, “Top risk flags”, “Recently approved”, activity feed, global search stub.
- Motion: animated counters, press states on cards, shimmering skeletons while loading; animated progress bars for SLA risk.
- Connect to sample data; wire to analytics events (`admin_view`, `app_open`).

**Phase 2 — Applications queue & review workspace**
- Screen: `ApplicationsScreen` with filter chips (state, city, category, organizer, risk, age) and sortable list.
- Detail workspace: left summary, center validation checklist, right actions (approve/publish, needs info, reject) with rationale required.
- Attachments preview, internal comments, timeline; risk flags pill set; validation completeness bar (animated).
- Bulk actions (approve/assign) with confirmation modals; write audit log entries on every transition.

**Phase 3 — Organizer management**
- Screen: `OrganizerScreen` list with verification badges, trust scores, strikes; detail sheet with KYC docs and past event performance.
- Actions: verify/unverify, freeze/unfreeze, request re-KYC, assign reviewer/AM; each action logs audit.
- Surface impact on public cards (badge, publish rights); add animated status pills and progress where applicable.

**Phase 4 — Moderation & catalog controls**
- Reports inbox for content issues; quick actions: edit/blur/unpublish/warn; version history with diffs and revert.
- Catalog controls: publish/unpublish, schedule go-live, set featured/hero slots using existing `HeroSlide` model; badge/promo controls (“Hot”, “Limited”).
- Ensure actions are RBAC-gated and audit-logged; add animated loaders for long operations.

**Phase 5 — Settings, roles, and feature flags**
- Role matrix UI: Customer/Organizer/Admin/Reviewer with assign/revoke; reasons required; audit entries emitted.
- Feature flags screen: toggle features (e.g., alerts beta, social share) with audience scoping.
- Notification templates: “Approved/Needs info/Rejected” with editable note; preview animation for send button.

**Phase 6 — Polish, accessibility, and analytics**
- Apply global motion rules to all interactive elements (buttons, clickable text, progress bars, spinners, FABs); keep icon colors distinct and subtle.
- Add empty/error states, pagination for queues, debounced filters; keyboard/focus support for web/desktop.
- Finalize analytics map (approve/reject/needs_info/verify/role_change/publish_toggle) and ensure audit trails visible per entity.

## 11) Acceptance criteria (MVP admin)
- Admin-only navigation available after role gate; non-admins cannot access admin screens.
- Event application can be viewed, assigned, approved, rejected, or marked “needs info” with rationale; audit log entries are produced.
- Organizer verify/unverify and freeze/unfreeze actions available and logged.
- Publish/unpublish and feature-slot assignment affect public catalog data source (shared model).
- Dashboard home shows live counts and queues; interactions are animated per motion system.
- All admin interactions adhere to existing visual language (glass/neon, animated buttons/progress/spinners/FAB, icon color distinctions) and back-button animation rule.

## 12) Reuse & component notes
- Reuse `GlowCard`, `NeonTextButton`, `PrimaryButton`, `AnimatedProgressBar`, `LoadingSpinner`, and FAB motion from organizer FAB where suitable.
- For hero/feature slot assignment, reuse `HeroSlide` model and `HeroCarousel` visuals with admin controls for ordering and scheduling.
- For event previews in review workspace, reuse `EventCard`/detail card styles to keep parity with public view.

## 13) Risks & mitigation
- **Data completeness**: organizer submissions may miss permits → enforce checklist + required fields before approval.
- **Role drift**: ensure server-validated RBAC; UI should gray out forbidden actions.
- **Complexity on mobile**: fall back to stacked layout with bottom sheets for actions and filters.
- **Performance**: paginate queues, debounce filters, lazy load attachments.

## 14) Next steps
- Confirm data contract for `EventApplication` and audit logging.
- Define navigation entry and feature flag for admin shell.
- Align palette tokens for admin risk/approval states (warning/success/neutral) within existing theme.
- Draft wireframes for Dashboard, Applications, and Organizer screens with animated microinteractions.
