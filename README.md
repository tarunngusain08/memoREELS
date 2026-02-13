# memoREELS

**Your personal memories, reimagined as Reels.**

memoREELS transforms your phone's photo and video library into an immersive, vertically scrolling feed -- like TikTok/Instagram Reels, but entirely for your own memories. Rediscover forgotten moments, relive your best days, and never let a memory fade again.

**100% offline. Zero data sharing. Your memories stay yours.**

---

## Download

> **[Download the latest APK from Google Drive](https://drive.google.com/drive/folders/1z9CNhRp9jtKBt7kA4oEKB5K4Pbky5MeU)**

Multiple versions are available in the drive folder. Always grab the most recent one for the best experience.

---

## Features

### Core Experience

- **Reels-Style Feed** -- Swipe through your local videos and photos in a full-screen vertical feed with buttery-smooth transitions
- **Unified or Separate Views** -- Toggle between a mixed media feed (videos + photos shuffled together) or dedicated Video and Photo tabs
- **Auto-Play & Loop** -- Videos play automatically and loop seamlessly; configurable via settings
- **Smart Muting** -- Start videos muted or unmuted based on your preference
- **Auto-Advance** -- When looping is off, the feed automatically scrolls to the next item when a video ends
- **Elegant Seek Bar** -- Tap a video to pause and reveal a smooth, draggable seek bar with fade-in/out animations

### Photo Display Modes

- **Ken Burns Effect** -- Single photos get a cinematic slow zoom and pan
- **Stacked Photos** -- Related photos are layered in a card stack with spring-physics animations; tap left/right to navigate, double-tap to open full-screen
- **Polaroid Style** -- Photos displayed in a stylized Polaroid frame with date
- **Motion Effect** -- Slow parallax zoom/pan with a vignette overlay
- **Collage Layout** -- Multi-photo grid layouts for groups

### AI-Powered Organization

- **On-Device ML Tagging** -- Photos and videos are automatically categorized using ML Kit Image Labeling (runs entirely on-device)
- **Explore Tab with Categories** -- Instagram-style story bubbles showing your auto-generated categories (mountains, food, pets, etc.)
- **Tag-Based Search** -- Search across all your memories by AI-generated tags or filenames
- **Smart Photo Stacking** -- Photos are grouped by day, location proximity, and shared visual tags for meaningful stacks

### Memory Features

- **Memory of the Moment** -- A random memory is highlighted each time you open the app
- **"This Day" Memories** -- Resurface photos and videos from the same date in past years
- **Time Capsules** -- Seal memories for the future and get notified when they unlock
- **Memory Journal** -- A timeline diary where you can browse photos by day and add personal notes
- **Memory Map** -- See where your memories were captured on a map with reverse-geocoded place names
- **Voice Notes** -- Attach audio recordings to any memory

### Organization & Utilities

- **Favorites** -- Heart any video or photo to save it; browse your favorites in a dedicated tab
- **People Albums** -- Face detection and clustering to group photos by person
- **Duplicate Cleaner** -- Find and clean visually similar photos
- **Nearby Sharing** -- Share memories peer-to-peer via Wi-Fi Direct
- **Home Screen Widget** -- A daily memory widget that refreshes automatically

### Settings & Preferences

- **Dedicated Settings Tab** -- All preferences accessible from the bottom navigation bar
- **Feed Mode Toggle** -- Switch between Unified (mixed) and Separate (videos + photos tabs) views
- **Playback Controls** -- Start Muted, Loop Videos toggles
- **Feature Navigation** -- Quick access to all feature screens from Settings

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| **UI** | Jetpack Compose, Material 3, Glance (widgets) |
| **Video** | Media3 ExoPlayer |
| **Images** | Coil (with VideoFrameDecoder) |
| **Data** | Room, Paging 3, MediaStore API, DataStore Preferences |
| **ML/AI** | ML Kit Image Labeling, ML Kit Face Detection |
| **Background** | WorkManager |
| **Architecture** | Clean Architecture (Data / Domain / UI), MVVM, Hilt DI |
| **Language** | Kotlin with Coroutines + Flow |

---

## Project Structure

```
app/src/main/java/com/example/memoreels/
├── data/
│   ├── datasource/       # MediaStore data sources, FeedItemFactory
│   ├── local/            # Room DAOs (Favorites, VideoTags, Journal, TimeCapsule, etc.)
│   ├── model/            # Room entities
│   ├── ml/               # On-device ML taggers (VideoTagger, PhotoTagger)
│   ├── preferences/      # DataStore user preferences
│   └── repository/       # Repository implementations
├── domain/
│   ├── model/            # Domain models (Video, Photo, FeedItem)
│   └── repository/       # Repository interfaces
├── ui/
│   ├── components/       # Reusable composables (VideoPlayer, PhotoDisplay, SeekBar, etc.)
│   ├── navigation/       # NavHost and routing
│   ├── screen/           # Screen composables (Feed, Explore, Favorites, Settings, etc.)
│   ├── viewmodel/        # ViewModels for each screen
│   └── theme/            # Material 3 theming
├── di/                   # Hilt dependency injection modules
├── widget/               # Glance home screen widget + worker
├── worker/               # WorkManager workers (media tagging)
└── MemoReelsApplication.kt
```

---

## Building Locally

### Prerequisites

- **Android Studio** Ladybug (2024.2) or later
- **JDK 17** (bundled with Android Studio)
- **Android SDK 34** (install via SDK Manager)
- A physical Android device or emulator running **API 26+** (Android 8.0+)

### Steps

1. **Clone the repository**

   ```bash
   git clone https://github.com/your-username/memoREELS.git
   cd memoREELS
   ```

2. **Open in Android Studio**

   Open the project root folder in Android Studio. Gradle will sync automatically and download all dependencies.

3. **Build the debug APK**

   From the terminal:

   ```bash
   ./gradlew assembleDebug
   ```

   The APK will be generated at:

   ```
   app/build/outputs/apk/debug/memoREELS.apk
   ```

4. **Install on a connected device**

   ```bash
   adb install app/build/outputs/apk/debug/memoREELS.apk
   ```

   Or simply click **Run** in Android Studio with your device selected.

### Build Variants

| Variant | Command | Description |
|---------|---------|-------------|
| Debug | `./gradlew assembleDebug` | Unoptimized, debuggable build |
| Release | `./gradlew assembleRelease` | Minified with R8, shrunk resources |

---

## Permissions

The app requests only the minimum permissions needed to access your local media:

| Permission | Android Version | Purpose |
|-----------|----------------|---------|
| `READ_MEDIA_VIDEO` | 13+ (API 33+) | Access local videos |
| `READ_MEDIA_IMAGES` | 13+ (API 33+) | Access local photos |
| `READ_EXTERNAL_STORAGE` | 12 and below | Access local media (legacy) |
| `ACCESS_MEDIA_LOCATION` | 10+ (API 29+) | Read GPS metadata from photos |
| `RECORD_AUDIO` | All | Voice notes feature |
| `ACCESS_FINE_LOCATION` | All | Nearby sharing feature |

**No INTERNET permission** -- the app cannot send any data off your device.

---

## Documentation

See the [`docs/`](docs/) folder for detailed design documents:

- **[PRD.md](docs/PRD.md)** -- Product Requirements Document
- **[LLD.md](docs/LLD.md)** -- Low-Level Design
- **[HLD.md](docs/HLD.md)** -- High-Level Design
- **[Implementation Plan.md](docs/Implementation%20Plan.md)** -- Phased roadmap

---

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.
