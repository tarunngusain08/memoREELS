# memoREELS

A local video discovery Android app that transforms your static video storage into an immersive, vertically scrolling feed — like TikTok/Reels for your personal memories. **100% offline, zero data sharing.**

## Features

- **Vertical Feed**: Swipe through your local videos in a Reels-style interface
- **Auto-Play & Loop**: Videos play automatically and loop seamlessly
- **Favorites**: Heart videos to save them to a local Room database
- **Share**: Share videos via Android's native share sheet
- **Metadata Overlay**: Date and location (when available) with auto-fade
- **Privacy-First**: No internet permission — all data stays on your device

## Tech Stack

- **UI**: Jetpack Compose, Material 3
- **Video**: Media3 ExoPlayer
- **Data**: Room, Paging3, MediaStore API
- **Architecture**: Clean Architecture (Data/Domain/UI), MVVM, Hilt DI

## Project Structure

```
app/src/main/java/com/example/memoreels/
├── data/           # Data layer (Room, MediaStore, Repository impl)
├── domain/         # Domain layer (Models, Repository interface, UseCases)
├── ui/              # UI layer (Screens, ViewModels, Components)
└── di/              # Hilt dependency injection modules
```

## Building

1. Open the project in Android Studio (Ladybug or later)
2. Let Gradle sync (this will download dependencies and set up the wrapper if needed)
3. Run on device or emulator (API 26+)

From terminal (after Gradle sync):
```bash
./gradlew assembleDebug
```

## Permissions

- `READ_MEDIA_VIDEO` (Android 13+)
- `READ_EXTERNAL_STORAGE` (Android 12 and below)

## Documentation

See the `docs/` folder for:
- PRD.md - Product Requirements
- HLD.md - High-Level Design
- LLD.md - Low-Level Design
- Implementation Plan.md - Phased implementation roadmap
