# GoTicky Admin - Ticket Scanning Guide

## Overview
The GoTicky ticket scanning system allows administrators to scan and validate tickets at event entrances using QR codes or barcodes. The system works both online and offline, ensuring uninterrupted operations.

## Getting Started

### Accessing the Scanner
1. Log in to GoTicky with your admin credentials
2. Navigate to the **Admin** section from the bottom navigation
3. In the **Tickets** tab, you'll see all tickets grouped by event
4. Click the **Open Scanner** button at the top right of the screen

### First-Time Setup
- **Camera Permission**: Grant camera access when prompted
- **Internet Connection**: For first use, internet is recommended to download MLKit models
- **Offline Mode**: After first use, scanner works completely offline

## Scanning Tickets

### Basic Scanning
1. Point your device camera at the ticket's QR code or barcode
2. Hold steady until the code is detected (usually instant)
3. Wait for scan result overlay to appear
4. Review ticket information

### Scan Results

#### ✅ Success (Green)
- Ticket is valid and has been checked in
- Attendee can enter the event
- Scan is recorded in the system

#### ⚠️ Already Scanned (Orange)
- This ticket was previously scanned
- Shows original scan time and scanner
- Attendee may have already entered
- **Action**: Verify attendee identity before allowing entry

#### ❌ Error (Red)
- Ticket cannot be scanned
- Common reasons:
  - Invalid ticket status
  - Ticket not found
  - Expired ticket
  - Network error (if online)
- **Action**: Check error message and contact support if needed

#### 📴 Offline (Gray)
- Scan recorded locally
- Will sync when internet connection is restored
- Attendee can enter the event
- **Note**: Duplicate prevention works offline too

### Flashlight Control
- Tap the flashlight icon (top-right) for low-light scanning
- Yellow icon = flashlight ON
- White icon = flashlight OFF

### Scanner Modes

#### MLKit (Online Mode)
- Faster and more accurate
- Uses Google's machine learning
- Requires internet for first use
- Indicated by "MLKit (Online)" pill

#### ZXing (Offline Mode)
- Works completely offline
- Lightweight and reliable
- Automatically activates when offline
- Indicated by "ZXing (Offline)" pill

## Managing Tickets

### Viewing Tickets
1. Navigate to **Admin** → **Tickets & Scanning**
2. View all tickets grouped by event
3. See real-time scan statistics

### Searching Tickets
1. Click the **Search** icon (magnifying glass)
2. Enter:
   - Ticket holder name
   - Ticket ID
   - Event name
3. Results update instantly

### Filtering Tickets

#### By Status
- **All**: Show all tickets
- **Valid**: Ready to scan
- **Pending entry**: Awaiting check-in
- **Hold**: Temporarily blocked
- **Scanned**: Already checked in

#### By City
- Filter tickets by event location
- Useful for multi-city events

### Viewing Ticket Details
1. Tap any ticket in the list
2. View complete information:
   - Holder name and seat
   - Event details
   - Purchase information
   - Scan history (if scanned)
3. Manual check-in option (if not scanned)

### Exporting Data
1. Click the **Export** icon (download)
2. Choose format (CSV or PDF)
3. Data includes:
   - All visible tickets
   - Current filters applied
   - Scan timestamps

## Statistics Dashboard

### Live Entry Progress
- **Total Tickets**: All tickets for selected events
- **Scanned**: Successfully checked in
- **Pending**: Awaiting check-in
- **Progress Bar**: Visual scan rate

### Real-Time Updates
- Statistics update automatically as tickets are scanned
- No refresh needed
- Works across multiple devices

## Offline Operations

### How It Works
1. Scan tickets as normal
2. Scans are queued locally
3. When internet returns, scans sync automatically
4. No data is lost

### Offline Capabilities
- ✅ Scan tickets
- ✅ View cached tickets
- ✅ Prevent duplicate scans
- ✅ Record scan timestamps
- ❌ Load new tickets (requires internet)
- ❌ Export data (requires internet)

### Sync Status
- Check "Offline" indicator in scanner
- Queued scans show count
- Automatic sync when online

## Best Practices

### Before the Event
1. **Test Scanner**: Scan a test ticket to ensure camera works
2. **Download Data**: Load ticket list while online
3. **Charge Device**: Ensure full battery
4. **Backup Scanner**: Have a second device ready

### During the Event
1. **Stay in Scanner**: Keep scanner open for faster scanning
2. **Verify Duplicates**: Check already-scanned warnings carefully
3. **Monitor Stats**: Watch for unusual patterns
4. **Handle Errors**: Have support contact ready

### After the Event
1. **Sync Data**: Ensure all offline scans are synced
2. **Export Report**: Download scan report for records
3. **Review Issues**: Check for any failed scans

## Troubleshooting

### Camera Not Working
- **Check Permission**: Settings → Apps → GoTicky → Permissions → Camera
- **Restart App**: Close and reopen GoTicky
- **Clean Lens**: Wipe camera lens

### QR Code Not Scanning
- **Lighting**: Use flashlight in dark venues
- **Distance**: Hold 6-12 inches from code
- **Angle**: Keep camera perpendicular to code
- **Quality**: Ensure QR code is not damaged

### Offline Sync Issues
- **Check Connection**: Verify internet is available
- **Manual Sync**: Pull down to refresh ticket list
- **Wait**: Sync happens automatically every 30 seconds

### Duplicate Scan Warnings
- **Verify Identity**: Check attendee ID matches ticket
- **Check Time**: Review original scan timestamp
- **Contact Support**: If legitimate re-entry needed

## Security

### Admin Access Only
- Only users with admin role can scan tickets
- Regular users cannot access scanner
- Audit trail records all scans

### Data Privacy
- Ticket data encrypted in transit
- Scan events are immutable (cannot be deleted)
- Complete audit trail maintained

### Preventing Fraud
- Idempotent scanning prevents duplicates
- Offline mode prevents network manipulation
- QR codes are unique and validated

## Support

### Common Issues
- See Troubleshooting section above
- Check FAQ in app settings

### Contact Support
- Email: support@goticky.com
- In-app: Settings → Help & Support
- Emergency: Event coordinator contact

## Tips for Success

1. **Practice**: Scan test tickets before live events
2. **Speed**: Keep scanner open, don't close between scans
3. **Accuracy**: Always verify duplicate warnings
4. **Backup**: Have manual check-in list as fallback
5. **Communication**: Coordinate with other scanners

---

**Version**: 1.0  
**Last Updated**: January 2026  
**For**: GoTicky Administrators
