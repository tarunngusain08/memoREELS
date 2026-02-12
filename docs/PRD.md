# memoREELS – Local Video Discovery App

## 1. Problem Statement
Users often accumulate vast collections of personal videos on their smartphones, captured from daily life, trips, events, and family moments. However, these videos frequently remain buried in disorganized folders, leading to "forgotten memories" that are rarely revisited. Traditional gallery apps provide linear or grid-based browsing, which lacks engagement and fails to evoke nostalgia effectively. memoREELS addresses this by repurposing local videos into a dynamic, addictive scrolling feed inspired by short-form video platforms like TikTok or Instagram Reels, all while ensuring complete privacy by operating entirely offline without any data upload or internet dependency.

## 2. Executive Summary
memoREELS is a mobile application designed to transform a user's static local video storage into an immersive, vertically scrolling feed. By mimicking the engaging UX of TikTok/Reels, it enables users to rediscover and relive "forgotten" memories stored on their device in a fun, effortless manner. The app focuses on seamless local video playback, intuitive controls, and personalization features, all built with Kotlin for Android. Key differentiators include zero data sharing, offline functionality, and AI-assisted curation (in advanced phases), making it ideal for privacy-focused users who want a social-media-like experience without the risks.

## 3. Target Audience
- **Memory Hoarders**: Individuals with thousands of videos accumulated over years who rarely review them due to overwhelming organization challenges.
- **Privacy-Conscious Users**: People wary of cloud-based apps, preferring a fully local experience to avoid data breaches, tracking, or uploads.
- **Nostalgia Seekers**: Families, travelers, or event enthusiasts looking to relive past moments in an entertaining format without social sharing.
- **Tech-Savvy Minimalists**: Users who appreciate simple, performant apps that integrate deeply with device storage but require minimal setup.
- **Demographics**: Primarily Android users aged 18-45, with high storage usage (e.g., photographers, vloggers, parents). Focus on regions with data privacy concerns like Europe (GDPR compliance) and India.

## 4. Key Objectives and Success Metrics
- **Objectives**:
  - Increase user engagement with local media by 50% (measured via session time in beta testing).
  - Provide a frictionless rediscovery experience, reducing the time to find and view old videos.
  - Maintain 100% offline operation to build trust in privacy.
- **Success Metrics**:
  - User Retention: 70% weekly active users after MVP launch.
  - Engagement: Average session duration >5 minutes; >20 videos viewed per session.
  - Feedback: NPS score >8/10 from beta testers.
  - Technical: App crash rate <1%; Load time for feed <2 seconds.

## 5. Functional Requirements
### 5.1 Video Discovery & Fetching
- **Media Scraper**: Use Android's MediaStore API to query and index all video files (.mp4, .mkv, .mov, .avi, .3gp) from internal and external storage. Support incremental scanning for new videos added post-launch.
- **Permissions Handling**: Gracefully request READ_MEDIA_VIDEO (for Android 13+) or READ_EXTERNAL_STORAGE (legacy). Include educational dialogs explaining why permissions are needed (e.g., "To access your local videos for playback"). Handle denial by showing a fallback UI with instructions to grant permissions.
- **Exclusion and Filtering**: Allow users to exclude specific folders (e.g., WhatsApp Videos, Screen Recordings, Downloads) via a settings menu. Add filters for video duration (e.g., skip videos <5 seconds or >10 minutes by default, user-configurable).
- **Metadata Extraction**: Pull EXIF data for date, location (if available), duration, and resolution. Fallback to file creation date if EXIF is missing.
- **Sorting Options**: Default to random shuffle for "surprise" discovery; alternatives include chronological, reverse-chronological, or by location.

### 5.2 The "Flow" Feed (Core UI)
- **Vertical Pager**: Implement a full-screen vertical swipe interface using Jetpack Compose's Pager or Accompanist Pager for smooth scrolling. Support infinite scrolling by looping back to the start or reshuffling.
- **Auto-Play/Loop**: Videos auto-play when entering the viewport and loop seamlessly. Pause on swipe or app backgrounding; resume on foreground.
- **Mute/Volume Toggle**: Single-tap to toggle mute; double-tap for volume slider. Integrate with system volume controls and respect Do Not Disturb mode.
- **Progress Indicators**: Subtle loading spinner for initial feed population; progress bar for video buffering.

### 5.3 Media Controls & Overlay
- **Metadata Overlay**: Display semi-transparent overlays for date (e.g., "June 15, 2023"), location (e.g., "Goa, India" from GPS metadata), and tags (user-added or AI-generated). Position in bottom-left; fade out after 3 seconds or on tap.
- **Favorites System**: Heart icon to add/remove from favorites. Store in a local Room Database with quick access via a dedicated tab. Support exporting favorites as a playlist.
- **Share and Export**: Invoke Android's native share sheet for sharing videos. Add options to export to gallery or create montages.
- **Additional Interactions**: Swipe left/right for next/previous video (as fallback to vertical swipe). Long-press to enter "edit mode" for deletion or tagging.

### 5.4 Settings and Customization
- **Theme Toggle**: Manual or system-based dark/light mode using Material 3.
- **Playback Preferences**: Auto-mute on start, loop count limit, video quality downscaling for performance.
- **Data Management**: Option to clear cache, rescan storage, or backup favorites database.
- **Onboarding**: Guided tutorial on first launch explaining permissions, feed navigation, and privacy benefits.

### 5.5 Advanced Features (Post-MVP)
- **AI-Enhanced Curation**: Use on-device ML (e.g., ML Kit) to group videos by themes (e.g., "Beach Trips," "Family Gatherings") or detect "This Day in History."
- **Search Functionality**: Keyword search by metadata, filename, or AI tags.

## 6. Non-Functional Requirements
- **Performance**: Feed load time <2 seconds; video preload for next 2-3 items to ensure zero-latency swipes. Target 60 FPS scrolling.
- **Compatibility**: Android 8.0+ (API 26+); Support various screen sizes, orientations (lock to portrait by default), and aspect ratios.
- **Accessibility**: VoiceOver support for metadata reading; high-contrast modes; gesture alternatives for swipes (e.g., buttons).
- **Scalability**: Handle 10,000+ videos without UI lag; use pagination and lazy loading.
- **Reliability**: Graceful error handling for corrupted videos; auto-skip unplayable files.
- **Internationalization**: Support English as default; prepare for multi-language (e.g., date formats).
- **Battery and Storage**: Optimize for low battery drain (e.g., pause background preloading); Minimal app size (<50MB).

## 7. Technical Requirements
| Feature | Implementation Component |
|---------|--------------------------|
| UI Framework | Jetpack Compose (for declarative UI, including Pager and animations) |
| Video Engine | Media3 ExoPlayer (for playback, preloading, and looping) |
| Data Fetching | Kotlin Coroutines + Flow (for asynchronous media scanning without UI blocking) |
| Local Database | Room Persistence Library (for favorites, exclusions, and user preferences) |
| Image Loading | Coil (for efficient thumbnail generation and caching from video frames) |
| Permissions | AndroidX Activity Result API (for modern permission requests) |
| Metadata Handling | ExifInterface (for extracting date/location from video files) |
| Haptics | VibratorManager (for feedback on interactions) |
| AI Features (Advanced) | ML Kit or TensorFlow Lite (on-device for grouping and tagging) |

- **Architecture**: MVVM pattern for separation of concerns; Use Hilt/Dagger for dependency injection.
- **Dependencies**: Minimize external libs; Stick to Jetpack ecosystem where possible.
- **Build Tools**: Gradle with Kotlin DSL; Target SDK 34 (Android 14).

## 8. User Experience (UX) Goals
- **Seamless Immersion**: Zero latency in swipes; Pre-cache videos using ExoPlayer's multi-media source for background preparation.
- **Intuitive Controls**: Aspect ratio handling with center-crop default; Double-tap to toggle fit-to-screen or zoom.
- **Engagement Boosters**: Haptic feedback on swipes, likes, and shares; Subtle animations (e.g., fade-ins for overlays).
- **Personalization**: Customizable feed algorithms (e.g., favor longer videos or location-based).
- **Error States**: User-friendly messages like "No videos found—add some to your gallery!" with illustrations.

## 9. Milestones & Roadmap
### Phase 1: MVP (Minimum Viable Product) – 4-6 Weeks
- Implement MediaStore fetching and permission handling.
- Basic VerticalPager with single ExoPlayer instance for full-screen playback and auto-loop.
- Core controls: Mute toggle, share button.
- Beta testing on emulators and physical devices.

### Phase 2: Polish & Metadata – 3-4 Weeks
- Add metadata overlays (date/location).
- Implement favorites system with Room DB.
- Introduce "Jump to Date" sidebar for quick navigation.
- Performance optimizations: Preloading and caching.

### Phase 3: Advanced Features – 4-6 Weeks
- AI grouping and "This Day" reminders using on-device ML.
- In-app video trimming tool.
- Full dark/light mode with Material 3 themes.
- Accessibility and internationalization prep.

### Phase 4: Release & Iteration – Ongoing
- Play Store submission (if public).
- User feedback integration; Bug fixes and feature requests.

## 10. Risks and Mitigations
- **Risk**: Performance issues with large libraries – Mitigation: Profile with Android Profiler; Optimize queries.
- **Risk**: Permission denials blocking core functionality – Mitigation: Robust fallback UIs and persistent reminders.
- **Risk**: Video format incompatibilities – Mitigation: Use ExoPlayer's adaptive streaming; Test on diverse file types.
- **Risk**: Battery drain from playback – Mitigation: Monitor with Battery Historian; Implement pause-on-background.
- **Risk**: Privacy perceptions – Mitigation: Transparent manifest (no INTERNET); App store descriptions emphasizing local-only.

## 11. Privacy & Security
- **No Internet Permission**: Explicitly exclude INTERNET from manifest to assure users no data leaves the device.
- **No Analytics or Tracking**: Avoid any telemetry; No third-party SDKs that could leak data.
- **Data Isolation**: All data (favorites, exclusions) stored in app-private storage; Support app data export/delete per Android guidelines.
- **Security Best Practices**: Use encrypted Room DB for sensitive metadata; Handle file paths securely to prevent injection.
- **Compliance**: Adhere to Google Play policies for media access; Ensure no background scanning without user consent.

This enhanced PRD provides a hardened foundation with added depth in problem definition, objectives, non-functional specs, risks, and detailed requirements. It ensures clarity for proceeding to High-Level Design (HLD), Low-Level Design (LLD), use cases, and sequence diagrams. Once confirmed, we can move forward.