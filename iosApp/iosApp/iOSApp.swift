import SwiftUI
import FirebaseCore

@main
struct iOSApp: App {
    
    init() {
        FirebaseConfigurator.configure()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

private enum FirebaseConfigurator {
    static func configure() {
        if FirebaseApp.app() != nil { return }
        
        // Prefer real GoogleService-Info.plist if present.
        if let path = Bundle.main.path(forResource: "GoogleService-Info", ofType: "plist"),
           let options = FirebaseOptions(contentsOfFile: path) {
            FirebaseApp.configure(options: options)
            return
        }
        
        // Fallback: create a dummy config to prevent Auth/Firestore fatal errors in dev.
        // Replace with a real GoogleService-Info.plist for production.
        let bundleID = Bundle.main.bundleIdentifier ?? "org.example.project.GoTicky"
        let options = FirebaseOptions(googleAppID: "1:000000000000:ios:dummyappid",
                                      gcmSenderID: "000000000000")
        options.apiKey = "dummy-api-key"
        options.projectID = "dummy-project"
        options.bundleID = bundleID
        FirebaseApp.configure(options: options)
    }
}