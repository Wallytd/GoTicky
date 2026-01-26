const { initializeTestEnvironment, assertFails, assertSucceeds } = require('@firebase/rules-unit-testing');
const { readFileSync } = require('fs');

/**
 * Firestore Security Rules Tests
 * Tests ticket scanning permissions and validation rules.
 */

let testEnv;

beforeAll(async () => {
    testEnv = await initializeTestEnvironment({
        projectId: 'goticky-test',
        firestore: {
            rules: readFileSync('../firestore.rules', 'utf8'),
        },
    });
});

afterAll(async () => {
    await testEnv.cleanup();
});

beforeEach(async () => {
    await testEnv.clearFirestore();
});

describe('Ticket Scanning Security Rules', () => {

    test('Admin can read all tickets', async () => {
        const admin = testEnv.authenticatedContext('admin-user', {
            role: 'admin'
        });

        await testEnv.withSecurityRulesDisabled(async (context) => {
            await context.firestore().collection('tickets').doc('ticket-1').set({
                eventId: 'event-1',
                ownerId: 'user-1',
                eventTitle: 'Test Event',
                venue: 'Test Venue',
                seat: 'A1',
                status: 'Valid',
                type: 'General',
                qrSeed: 'test-seed',
                scanned: false,
                scanCount: 0
            });
        });

        await assertSucceeds(
            admin.firestore().collection('tickets').doc('ticket-1').get()
        );
    });

    test('Ticket owner can read their own ticket', async () => {
        const owner = testEnv.authenticatedContext('user-1');

        await testEnv.withSecurityRulesDisabled(async (context) => {
            await context.firestore().collection('tickets').doc('ticket-1').set({
                eventId: 'event-1',
                ownerId: 'user-1',
                eventTitle: 'Test Event',
                venue: 'Test Venue',
                seat: 'A1',
                status: 'Valid',
                type: 'General',
                qrSeed: 'test-seed',
                scanned: false,
                scanCount: 0
            });
        });

        await assertSucceeds(
            owner.firestore().collection('tickets').doc('ticket-1').get()
        );
    });

    test('Non-owner cannot read other tickets', async () => {
        const otherUser = testEnv.authenticatedContext('user-2');

        await testEnv.withSecurityRulesDisabled(async (context) => {
            await context.firestore().collection('tickets').doc('ticket-1').set({
                eventId: 'event-1',
                ownerId: 'user-1',
                eventTitle: 'Test Event',
                venue: 'Test Venue',
                seat: 'A1',
                status: 'Valid',
                type: 'General',
                qrSeed: 'test-seed',
                scanned: false,
                scanCount: 0
            });
        });

        await assertFails(
            otherUser.firestore().collection('tickets').doc('ticket-1').get()
        );
    });

    test('Admin can update scan status', async () => {
        const admin = testEnv.authenticatedContext('admin-user', {
            role: 'admin'
        });

        await testEnv.withSecurityRulesDisabled(async (context) => {
            await context.firestore().collection('tickets').doc('ticket-1').set({
                eventId: 'event-1',
                ownerId: 'user-1',
                eventTitle: 'Test Event',
                venue: 'Test Venue',
                seat: 'A1',
                status: 'Valid',
                type: 'General',
                qrSeed: 'test-seed',
                scanned: false,
                scanCount: 0
            });
        });

        await assertSucceeds(
            admin.firestore().collection('tickets').doc('ticket-1').update({
                scanned: true,
                scannedAt: '2026-01-26T10:00:00Z',
                scannedBy: 'admin-user',
                scanCount: 1,
                updatedAt: '2026-01-26T10:00:00Z'
            })
        );
    });

    test('Non-admin cannot update scan status', async () => {
        const user = testEnv.authenticatedContext('user-1');

        await testEnv.withSecurityRulesDisabled(async (context) => {
            await context.firestore().collection('tickets').doc('ticket-1').set({
                eventId: 'event-1',
                ownerId: 'user-1',
                eventTitle: 'Test Event',
                venue: 'Test Venue',
                seat: 'A1',
                status: 'Valid',
                type: 'General',
                qrSeed: 'test-seed',
                scanned: false,
                scanCount: 0
            });
        });

        await assertFails(
            user.firestore().collection('tickets').doc('ticket-1').update({
                scanned: true,
                scannedAt: '2026-01-26T10:00:00Z',
                scannedBy: 'user-1',
                scanCount: 1
            })
        );
    });

    test('Scan count cannot decrease', async () => {
        const admin = testEnv.authenticatedContext('admin-user', {
            role: 'admin'
        });

        await testEnv.withSecurityRulesDisabled(async (context) => {
            await context.firestore().collection('tickets').doc('ticket-1').set({
                eventId: 'event-1',
                ownerId: 'user-1',
                eventTitle: 'Test Event',
                venue: 'Test Venue',
                seat: 'A1',
                status: 'Valid',
                type: 'General',
                qrSeed: 'test-seed',
                scanned: true,
                scanCount: 5,
                scannedAt: '2026-01-26T09:00:00Z',
                scannedBy: 'admin-user'
            });
        });

        await assertFails(
            admin.firestore().collection('tickets').doc('ticket-1').update({
                scanCount: 3, // Trying to decrease
                updatedAt: '2026-01-26T10:00:00Z'
            })
        );
    });

    test('Scanned status cannot revert to false', async () => {
        const admin = testEnv.authenticatedContext('admin-user', {
            role: 'admin'
        });

        await testEnv.withSecurityRulesDisabled(async (context) => {
            await context.firestore().collection('tickets').doc('ticket-1').set({
                eventId: 'event-1',
                ownerId: 'user-1',
                eventTitle: 'Test Event',
                venue: 'Test Venue',
                seat: 'A1',
                status: 'Valid',
                type: 'General',
                qrSeed: 'test-seed',
                scanned: true,
                scanCount: 1,
                scannedAt: '2026-01-26T09:00:00Z',
                scannedBy: 'admin-user'
            });
        });

        await assertFails(
            admin.firestore().collection('tickets').doc('ticket-1').update({
                scanned: false, // Trying to un-scan
                updatedAt: '2026-01-26T10:00:00Z'
            })
        );
    });

    test('Admin can create scan events', async () => {
        const admin = testEnv.authenticatedContext('admin-user', {
            role: 'admin'
        });

        await assertSucceeds(
            admin.firestore()
                .collection('tickets').doc('ticket-1')
                .collection('scanEvents').doc('scan-1')
                .set({
                    ticketId: 'ticket-1',
                    scannerId: 'admin-user',
                    scannerName: 'Admin Scanner',
                    timestamp: '2026-01-26T10:00:00Z',
                    success: true
                })
        );
    });

    test('Scan events cannot be modified', async () => {
        const admin = testEnv.authenticatedContext('admin-user', {
            role: 'admin'
        });

        await testEnv.withSecurityRulesDisabled(async (context) => {
            await context.firestore()
                .collection('tickets').doc('ticket-1')
                .collection('scanEvents').doc('scan-1')
                .set({
                    ticketId: 'ticket-1',
                    scannerId: 'admin-user',
                    timestamp: '2026-01-26T10:00:00Z',
                    success: true
                });
        });

        await assertFails(
            admin.firestore()
                .collection('tickets').doc('ticket-1')
                .collection('scanEvents').doc('scan-1')
                .update({
                    success: false
                })
        );
    });
});
