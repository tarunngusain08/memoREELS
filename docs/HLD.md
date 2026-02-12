# memoREELS - High Level Design (HLD)

## 1. System Overview
memoREELS is a single-module Android app built with Kotlin, Jetpack Compose, and MVVM architecture. It fetches local videos via MediaStore, displays them in an infinite vertical pager with ExoPlayer, and manages favorites locally with Room DB. All operations are offline.

### Key Principles
- **Modular Design**: Layered architecture (Presentation, Domain, Data).
- **Reactive UI**: Compose + Flow/Coroutines for state management.
- **Scalability**: Pagination for large video libraries.
- **Privacy**: No network calls.

## 2. Architecture Layers
```
[Presentation Layer] (Compose UI + ViewModels)
    ↓ (Flows/Events)
[Domain Layer] (UseCases, Repositories Interface)
    ↓ (Repository Pattern)
[Data Layer] (Room DB, MediaStore, ExoPlayer)
```

- **Presentation**: Screens (FeedScreen, FavoritesScreen, SettingsScreen), ViewModels.
- **Domain**: Business logic in UseCases (e.g., GetVideosUseCase, ToggleFavoriteUseCase).
- **Data**: Repositories (VideoRepository impl), DataSources (LocalVideoDataSource, FavoritesDataSource).

## 3. Key Components
### 3.1 UI Components (Jetpack Compose)
- **MainScreen**: BottomNav with Feed, Favorites, Search.
- **VideoFeedScreen**: VerticalPager with VideoPlayer composable.
- **VideoPlayer**: ExoPlayer integration with overlays (metadata, heart button).
- **FavoritesScreen**: LazyColumn with video grid/list.

### 3.2 Data Flow
1. **Onboarding/Permissions**: Request perms → Success → Fetch videos.
2. **Video Discovery**:
   - MediaStore query → VideoEntity list → PagerState.
3. **Playback**:
   - Pager onPageChange → Prepare ExoPlayer for current/next video.
4. **Favorites**:
   - Heart tap → ToggleFavoriteUseCase → Room insert/update.

```
User Swipe → Pager → Play Video → Overlay Tap → UseCase → Room/MediaStore
```

### 3.3 Modules & Dependencies
- **:app** (single module)
  - Core: Compose, Media3, Room, Coil, Paging3.
  - DI: Hilt for injecting Repos into ViewModels.

### 3.4 Data Models
- **VideoEntity**: id, uri, path, date, duration, location, thumbnail.
- **FavoriteEntity**: videoId, note, timestamp.

## 4. External Integrations
- **MediaStore**: Query videos.
- **ExoPlayer**: Playback & preloading.
- **Room**: Local persistence.
- **Share Intent**: Native sharing.

## 5. Non-Functional
- **Performance**: Paging3 for feeds, Coroutines for IO.
- **Error Handling**: Permission denied → Manual picker; No videos → Empty state.
- **Testing**: Unit (ViewModels/UseCases), Integration (Repo), UI (Compose).

## 6. Deployment
- APK via Gradle.
- Play Store: Privacy policy emphasizing local-only.

## Diagrams (Text-based)
**Component Diagram**:
```
[MainActivity] --> [VideoFeedViewModel] --> [VideoRepository]
[VideoRepository] --> [MediaStoreDataSource]
[VideoRepository] --> [FavoritesDao]
[VideoPlayerComposable] <-- [ExoPlayerInstance]
```

**Sequence Diagram for Feed Load**:
```
AppStart → RequestPerms → QueryMediaStore → LoadToPager → AutoPlay
```

This HLD guides Phase 1-2 implementation.
