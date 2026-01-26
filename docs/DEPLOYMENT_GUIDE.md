# GoTicky Ticket Scanning System - Deployment Guide

## Prerequisites

### Required Tools
- Firebase CLI (`npm install -g firebase-tools`)
- Android Studio (for Android builds)
- Git
- Node.js 16+

### Required Access
- Firebase project admin access
- Google Cloud Console access
- App store publishing credentials

## Pre-Deployment Checklist

### 1. Code Review
- [ ] All unit tests passing
- [ ] Firestore rules tested
- [ ] No console errors or warnings
- [ ] Code follows project conventions
- [ ] All TODOs resolved or documented

### 2. Configuration
- [ ] Firebase project ID configured
- [ ] API keys secured (not in source control)
- [ ] Environment variables set
- [ ] Build variants configured (debug/release)

### 3. Testing
- [ ] Test with real QR codes
- [ ] Test offline mode
- [ ] Test duplicate prevention
- [ ] Test on multiple devices
- [ ] Test with poor network conditions

## Deployment Steps

### Step 1: Deploy Firestore Rules

```bash
# Navigate to project root
cd /path/to/GoTicky

# Login to Firebase
firebase login

# Deploy rules only
firebase deploy --only firestore:rules

# Verify deployment
firebase firestore:rules:get
```

**Verification**:
- Check Firebase Console → Firestore → Rules
- Verify rules match `firestore.rules` file
- Test admin access in app

### Step 2: Deploy Firestore Indexes

```bash
# Deploy indexes
firebase deploy --only firestore:indexes

# Monitor index creation
# Go to Firebase Console → Firestore → Indexes
# Wait for all indexes to show "Enabled" status
```

**Note**: Index creation can take 5-30 minutes depending on data size.

### Step 3: Test in Staging

```bash
# Build staging APK
./gradlew assembleStaging

# Install on test device
adb install -r composeApp/build/outputs/apk/staging/composeApp-staging.apk

# Test checklist:
# - Login as admin
# - Navigate to scanner
# - Scan test QR code
# - Verify scan recorded in Firestore
# - Test offline mode
# - Verify sync when back online
```

### Step 4: Production Build

```bash
# Clean build
./gradlew clean

# Build release APK
./gradlew assembleRelease

# Or build App Bundle for Play Store
./gradlew bundleRelease

# Output location:
# APK: composeApp/build/outputs/apk/release/
# Bundle: composeApp/build/outputs/bundle/release/
```

### Step 5: Deploy to Production

#### Option A: Google Play Store

1. **Prepare Release**:
   - Update version code in `build.gradle.kts`
   - Update version name (e.g., 1.0.0 → 1.1.0)
   - Create release notes

2. **Upload to Play Console**:
   - Go to Google Play Console
   - Select GoTicky app
   - Create new release
   - Upload App Bundle
   - Add release notes
   - Submit for review

3. **Rollout Strategy**:
   - Start with 10% rollout
   - Monitor crash reports
   - Increase to 50% after 24 hours
   - Full rollout after 48 hours if stable

#### Option B: Direct Distribution

```bash
# Sign APK manually
jarsigner -verbose -sigalg SHA256withRSA \
  -digestalg SHA-256 \
  -keystore /path/to/keystore.jks \
  composeApp-release-unsigned.apk \
  key-alias

# Verify signature
jarsigner -verify -verbose -certs composeApp-release.apk

# Distribute via your preferred method
```

### Step 6: Post-Deployment Verification

#### Immediate Checks (0-1 hour)
- [ ] App installs successfully
- [ ] Admin login works
- [ ] Scanner opens without crashes
- [ ] Test scan completes successfully
- [ ] Firestore writes visible in console

#### Short-term Monitoring (1-24 hours)
- [ ] No crash reports
- [ ] Scan success rate > 95%
- [ ] Offline sync working
- [ ] No security rule violations
- [ ] Performance metrics normal

#### Long-term Monitoring (1-7 days)
- [ ] User feedback positive
- [ ] No duplicate scan issues
- [ ] Audit trail complete
- [ ] Statistics accurate
- [ ] Battery usage acceptable

## Rollback Procedure

### If Critical Issues Found

1. **Immediate Actions**:
   ```bash
   # Halt Play Store rollout
   # Go to Play Console → Release → Halt rollout
   
   # Revert Firestore rules
   firebase deploy --only firestore:rules --project goticky-prod
   ```

2. **Communicate**:
   - Notify admin users via email
   - Post status update in admin dashboard
   - Provide workaround if available

3. **Fix and Redeploy**:
   - Fix issue in development
   - Test thoroughly
   - Follow deployment steps again

## Monitoring

### Firebase Console
- **Firestore**: Monitor read/write operations
- **Performance**: Check app performance metrics
- **Crashlytics**: Monitor crash reports
- **Analytics**: Track scanner usage

### Key Metrics to Watch
- Scan success rate (target: > 95%)
- Average scan time (target: < 2 seconds)
- Offline sync success rate (target: > 99%)
- Duplicate scan prevention (target: 100%)
- App crash rate (target: < 0.1%)

### Alerts to Configure
- Firestore read/write quota exceeded
- Crash rate spike (> 1%)
- Security rule violations
- Scan failure rate > 10%

## Maintenance

### Weekly
- Review crash reports
- Check Firestore usage
- Monitor scan statistics
- Review user feedback

### Monthly
- Update dependencies
- Review security rules
- Optimize Firestore queries
- Performance audit

### Quarterly
- Major version update
- Feature additions
- Security audit
- User training updates

## Troubleshooting

### Deployment Fails

**Firestore Rules Error**:
```bash
# Check rules syntax
firebase firestore:rules:validate

# Test rules locally
npm --prefix functions test
```

**Index Creation Stuck**:
- Check Firebase Console for errors
- Verify index definition in `firestore.indexes.json`
- May need to delete and recreate

**Build Fails**:
```bash
# Clean and rebuild
./gradlew clean
./gradlew build --stacktrace

# Check for dependency conflicts
./gradlew dependencies
```

### Post-Deployment Issues

**Scanner Not Working**:
1. Check camera permissions in app settings
2. Verify MLKit models downloaded
3. Check Firestore connection
4. Review crash logs

**Offline Sync Failing**:
1. Check network connectivity
2. Verify Firestore rules allow writes
3. Check offline queue size
4. Review sync logs

**Duplicate Scans Occurring**:
1. Verify Firestore transaction logic
2. Check for race conditions
3. Review security rules
4. Test with multiple devices

## Security Considerations

### Before Deployment
- [ ] API keys not in source code
- [ ] Firestore rules tested
- [ ] Admin-only access verified
- [ ] Audit trail immutable
- [ ] Encryption enabled

### After Deployment
- Monitor security rule violations
- Review audit logs regularly
- Update dependencies for security patches
- Rotate API keys quarterly

## Support

### For Deployment Issues
- Email: devops@goticky.com
- Slack: #goticky-deployment
- On-call: Check PagerDuty

### For Production Issues
- Email: support@goticky.com
- Emergency: Event coordinator hotline
- Status page: status.goticky.com

---

**Version**: 1.0  
**Last Updated**: January 2026  
**Maintained by**: GoTicky DevOps Team
