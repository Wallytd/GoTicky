// One-off admin script to backfill and synchronize profile data
// between `users`, `userDirectory`, and `publicProfiles`.
//
// Usage (from project root):
//   1) Install dependencies:  npm install firebase-admin
//   2) Point GOOGLE_APPLICATION_CREDENTIALS to a service account JSON
//   3) Run:  node backfillProfilePhotos.js
//
// This script is safe to re-run; it only performs merge writes.

const admin = require('firebase-admin');

if (admin.apps.length === 0) {
  // Uses Application Default Credentials. Configure via
  // GOOGLE_APPLICATION_CREDENTIALS or your environment.
  admin.initializeApp();
}

const db = admin.firestore();

async function backfillFromUsers() {
  console.log('Backfilling from /users ...');
  const snap = await db.collection('users').get();

  for (const doc of snap.docs) {
    const data = doc.data() || {};
    const email = (data.email || '').trim();
    const emailLower = email.toLowerCase();
    const displayName = data.displayName || '';
    const photoUri = data.photoUri || null;

    if (!emailLower) {
      console.log(`Skipping users/${doc.id} – no email`);
      continue;
    }

    await db.collection('publicProfiles').doc(emailLower).set(
      {
        emailLower,
        email,
        displayName,
        // null is allowed by Firestore rules via validPublicProfile
        photoUri: photoUri ?? null,
      },
      { merge: true }
    );
  }
}

async function backfillFromUserDirectory() {
  console.log('Backfilling from /userDirectory/customer ...');

  const customerRoot = db.collection('userDirectory').doc('customer');
  const nameCollections = await customerRoot.listCollections();

  for (const nameCol of nameCollections) {
    const snap = await nameCol.get();

    for (const doc of snap.docs) {
      const data = doc.data() || {};
      const email = (data.email || '').trim();
      const emailLower = email.toLowerCase();
      const displayName = data.displayName || '';
      const uid = (data.uid || '').trim() || emailLower;
      const photoUri = data.photoUri || null;

      if (!emailLower) {
        console.log(`Skipping userDirectory/${nameCol.id}/${doc.id} – no email`);
        continue;
      }

      // Upsert minimal profile into users; do not overwrite unrelated fields.
      await db.collection('users').doc(uid).set(
        {
          uid,
          email,
          displayName,
          photoUri,
        },
        { merge: true }
      );

      // Ensure publicProfiles has a minimal, avatar-ready projection.
      await db.collection('publicProfiles').doc(emailLower).set(
        {
          emailLower,
          email,
          displayName,
          photoUri,
        },
        { merge: true }
      );
    }
  }
}

async function main() {
  try {
    await backfillFromUsers();
    await backfillFromUserDirectory();
    console.log('Backfill complete.');
  } catch (err) {
    console.error('Backfill failed:', err);
    process.exitCode = 1;
  }
}

main();
