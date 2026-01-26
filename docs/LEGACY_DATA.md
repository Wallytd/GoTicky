# Legacy Data Reference

- **goticky.json**
  - Purpose: Historical admin profile seed data used during early development.
  - Status: **Legacy only** — no runtime code reads this file; admin profiles now live in Realtime Database under `/adminprofiles`.
  - Guidance: Keep the file for reference/audit. Do not rely on it for sign-in, seeding, or deployment. Update Realtime Database directly for admin profile changes.

- Current source of truth for admins: Firebase Realtime Database `/adminprofiles` (queried via `AdminProfile` helpers in `composeApp/src/commonMain/kotlin/org/example/project/data/AdminSeeder.kt`).
