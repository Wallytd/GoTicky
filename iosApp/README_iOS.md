# GoTicky iOS Setup

Follow these steps to run the iOS app with the shared Compose framework.

## Prerequisites
- Xcode 15+
- CocoaPods installed (`gem install cocoapods`)
- JDK 17 (already used for Android/Compose)

## 1) Sync the Compose framework
Run from project root:
```
./gradlew :composeApp:syncFramework
```
This generates `ComposeApp.xcframework` for the `iosApp` Pod.

## 2) Install pods
In `iosApp/`:
```
pod install
```
Open the generated `iosApp.xcworkspace` for development.

## 3) Xcode build/run
- Select `iosApp` scheme.
- Choose a simulator or device (iOS 15+).
- Build & run. The SwiftUI host loads the KMP UI via `MainViewController()`.

## Firebase / analytics
- A real Firebase setup is **not** wired yet. Add your `GoogleService-Info.plist` to `iosApp/iosApp/` and enable pods if you want Firebase; replace the stub in `FirebaseInit.ios.kt` with `FirebaseApp.configure()`.
- Analytics currently logs to `NSLog`; swap `Analytics.logger` in `MainViewController.kt` for a real backend when available.

## Notes
- Map uses MapKit via `UIKitView`.
- Image picker supports gallery/camera; temp files are written under `NSTemporaryDirectory()`.
- The Compose framework is static (`isStatic = true`) to simplify linking.
