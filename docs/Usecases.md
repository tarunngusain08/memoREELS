# memoREELS - Sequence Diagrams (Mermaid Syntax)

Render these in Mermaid Live or VSCode extension.

## 1. App Launch & Feed Load (Phase 1 MVP)
```mermaid
sequenceDiagram
    participant U as User
    participant MA as MainActivity
    participant VM as VideoFeedViewModel
    participant UC as GetVideosUseCase
    participant Repo as VideoRepository
    participant DS as VideoDataSource
    participant MS as MediaStore

    U->>MA: Launch App
    MA->>VM: Collect videos Flow
    VM->>UC: invoke(exclusions)
    UC->>Repo: getVideos()
    Repo->>DS: getVideosCursor()
    DS->>MS: query(videos)
    MS-->>DS: Cursor
    DS-->>Repo: PagingData<Video>
    Repo-->>UC: Flow
    UC-->>VM: PagingData
    VM-->>MA: StateFlow
    MA->>U: Render VerticalPager
```

## 2. Video Playback & Preload
```mermaid
sequenceDiagram
    participant Pager as VerticalPager
    participant VP as VideoPlayer
    participant EP as ExoPlayer

    Pager->>VP: Current page uri
    VP->>EP: setMediaItem(uri)
    EP->>EP: prepare() & play()
    Note over Pager,EP: Preload next uri in background
    Pager->>Pager: Swipe to next
    Pager->>VP: Next uri (recycle)
    VP->>EP: Release & set new
```

## 3. Toggle Favorite
```mermaid
sequenceDiagram
    participant U as User
    participant FB as FavoriteButton
    participant VM as ViewModel
    participant UC as ToggleFavoriteUseCase
    participant Repo as Repository
    participant Dao as FavoritesDao

    U->>FB: Tap Heart
    FB->>VM: toggleFavorite(video)
    VM->>UC: execute(video)
    UC->>Repo: isFavorite ? remove : insert
    Repo->>Dao: insert/remove
    Dao-->>Repo: Updated
    Repo-->>UC: Success
    UC-->>VM: Update state
    VM-->>FB: Heart filled/empty
```

## 4. Permission Request
```mermaid
sequenceDiagram
    participant U as User
    participant PM as PermissionManager
    participant Sys as System

    U->>PM: Check perms
    PM->>Sys: requestPermissions()
    Sys-->>PM: Granted/Denied
    alt Granted
        PM-->>U: Load feed
    else Denied
        PM-->>U: Show dialog
    end
```

## 5. Search Videos
```mermaid
sequenceDiagram
    participant U as User
    participant SM as SearchScreen
    participant VM as SearchViewModel
    participant UC as SearchVideosUseCase
    participant Repo as Repository

    U->>SM: Enter query
    SM->>VM: search(query)
    VM->>UC: invoke(query)
    UC->>Repo: getVideos(filter=query)
    Repo-->>UC: Filtered PagingData
    UC-->>VM: Flow
    VM-->>SM: Update results
```

These diagrams map to HLD data flows and LLD classes. Cover PRD core reqs.
