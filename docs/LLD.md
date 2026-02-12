# memoREELS - Low Level Design (LLD)

## 1. Package Structure
```
com.example.memoreels
├── data
│   ├── datasource
│   │   ├── local.VideoDataSource (MediaStore)
│   │   └── local.FavoritesDataSource (Room)
│   ├── model.VideoEntity, FavoriteEntity
│   └── repository.VideoRepositoryImpl
├── domain
│   ├── model.Video (domain model)
│   ├── repository.VideoRepository (interface)
│   └── usecase
│       ├── GetVideosUseCase
│       ├── ToggleFavoriteUseCase
│       └── SearchVideosUseCase
├── ui
│   ├── screen (FeedScreen, FavoritesScreen)
│   ├── components (VideoPlayer, MetadataOverlay)
│   └── viewmodel (VideoFeedViewModel)
└── di (Hilt modules)
```

## 2. Database Schema (Room)
### Tables
```
favorites_table:
- id: Long PRIMARY KEY
- videoUri: String UNIQUE
- note: String?
- createdAt: Long
```

Entity:
```kotlin
@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val videoUri: String,
    val note: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
```

DAO:
```kotlin
@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun toggleFavorite(entity: FavoriteEntity)
    
    @Query("DELETE FROM favorites WHERE videoUri = :uri")
    suspend fun removeFavorite(uri: String)
}
```

## 3. Key Classes & Interfaces

### 3.1 VideoRepository
```kotlin
interface VideoRepository {
    fun getVideos(exclusions: List<String>): Flow<PagingData<Video>>
    suspend fun getVideoByUri(uri: String): Video?
    fun getFavorites(): Flow<List<Favorite>>
}

class VideoRepositoryImpl @Inject constructor(
    private val videoDataSource: VideoDataSource,
    private val favoritesDao: FavoritesDao
) : VideoRepository { ... }
```

### 3.2 UseCases
```kotlin
class GetVideosUseCase @Inject constructor(
    private val repository: VideoRepository
) {
    operator fun invoke(exclusions: List<String>): Flow<PagingData<Video>> = 
        repository.getVideos(exclusions)
}
```

### 3.3 ViewModel
```kotlin
@HiltViewModel
class VideoFeedViewModel @Inject constructor(
    private val getVideos: GetVideosUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase
) : ViewModel() {
    val videos: StateFlow<PagingData<Video>> = ...
    
    fun toggleFavorite(video: Video) { ... }
}
```

## 4. UI Composables (Compose)

### VideoPlayer Composable
```kotlin
@Composable
fun VideoPlayer(
    videoUri: String,
    modifier: Modifier = Modifier
) {
    val exoPlayer = remember { ExoPlayer.Builder(LocalContext.current).build() }
    DisposableEffect(videoUri) { ... }
    
    AndroidView(factory = { ExoPlayerView(it).apply { player = exoPlayer } })
}
```

### FeedScreen
```kotlin
@Composable
fun VideoFeedScreen(
    viewModel: VideoFeedViewModel = hiltViewModel()
) {
    val pagingItems = viewModel.videos.collectAsLazyPagingItems()
    VerticalPager { page ->
        pagingItems[page]?.let { video ->
            VideoPlayer(video.uri)
            MetadataOverlay(video)
            FavoriteButton(onToggle = { viewModel.toggleFavorite(video) })
        }
    }
}
```

## 5. MediaStore DataSource
```kotlin
class VideoDataSource(private val context: Context) {
    fun getVideosCursor(exclusions: List<String>): Cursor? {
        val projection = arrayOf(MediaStore.Video.Media._ID, ...)
        val selection = "${MediaStore.Video.Media.MIME_TYPE} LIKE ? AND ..." // exclude folders
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        return context.contentResolver.query(uri, projection, selection, ..., null)
    }
    
    fun toVideo(cursor: Cursor): Video { ... }
}
```

## 6. Permissions Handling
```kotlin
class PermissionManager {
    suspend fun requestVideoPermissions(): Boolean {
        // Use rememberLauncherForActivityResult or Accompanist
    }
}
```

## 7. Preloading Logic
- Use ExoPlayer's `DefaultMediaSourceFactory` for background prep.
- In Pager, onPreScrollStateChange: prepare next video.

## 8. Error States & Edge Cases
- No perms: Show dialog + manual file picker.
- Empty library: "No videos found. Add some memories!"
- Corrupted video: Skip + log.

## 9. Code Quality
- Lint, Ktlint.
- 80% unit test coverage for Domain/Data.
- Compose previews for all UI.

This LLD provides blueprints for implementation. Use PlantUML or Draw.io for visual diagrams if needed.
