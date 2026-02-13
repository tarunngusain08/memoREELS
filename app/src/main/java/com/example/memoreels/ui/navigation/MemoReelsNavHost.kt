package com.example.memoreels.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.memoreels.data.preferences.FeedMode
import com.example.memoreels.data.preferences.UserPreferences
import kotlinx.coroutines.flow.flowOf
import com.example.memoreels.ui.screen.CollectionDetailScreen
import com.example.memoreels.ui.screen.CollectionFeedScreen
import com.example.memoreels.ui.screen.DuplicateCleanerScreen
import com.example.memoreels.ui.screen.ExploreScreen
import com.example.memoreels.ui.screen.FavoritesScreen
import com.example.memoreels.ui.screen.HighlightReelsScreen
import com.example.memoreels.ui.screen.JournalScreen
import com.example.memoreels.ui.screen.MemoryMapScreen
import com.example.memoreels.ui.screen.MoodFeedScreen
import com.example.memoreels.ui.screen.NearbySharingScreen
import com.example.memoreels.ui.screen.OnboardingScreen
import com.example.memoreels.ui.screen.PeopleAlbumsScreen
import com.example.memoreels.ui.screen.PhotoFeedScreen
import com.example.memoreels.ui.screen.TimeCapsuleScreen
import com.example.memoreels.ui.screen.PhotoViewerScreen
import com.example.memoreels.ui.screen.VideoFeedScreen
import com.example.memoreels.ui.screen.VideoPlayerScreen

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object Feed : Screen("feed", "Feed", Icons.Default.Home)
    data object Photos : Screen("photos", "Photos", Icons.Default.Face)
    data object Favorites : Screen("favorites", "Favorites", Icons.Default.Favorite)
    data object Explore : Screen("explore", "Explore", Icons.Default.Search)
    data object Onboarding : Screen("onboarding", "Onboarding", Icons.Default.Home)
    data object Player : Screen("player", "Player", Icons.Default.Home)
}

// Shared transition specs
private const val TAB_ANIM_DURATION = 250
private const val DETAIL_ANIM_DURATION = 300

// Tab transitions: fade + subtle scale for polished feel
private val tabEnter: EnterTransition =
    fadeIn(tween(TAB_ANIM_DURATION)) + scaleIn(
        animationSpec = tween(TAB_ANIM_DURATION),
        initialScale = 0.96f,
        transformOrigin = TransformOrigin.Center
    )
private val tabExit: ExitTransition =
    fadeOut(tween(TAB_ANIM_DURATION)) + scaleOut(
        animationSpec = tween(TAB_ANIM_DURATION),
        targetScale = 0.96f,
        transformOrigin = TransformOrigin.Center
    )

// Detail screen transitions: smoother half-width slide + fade
private val slideInFromRight: EnterTransition =
    slideInHorizontally(
        tween(DETAIL_ANIM_DURATION, easing = FastOutSlowInEasing)
    ) { it / 2 } + fadeIn(tween(DETAIL_ANIM_DURATION))
private val slideOutToRight: ExitTransition =
    slideOutHorizontally(
        tween(DETAIL_ANIM_DURATION, easing = FastOutSlowInEasing)
    ) { it / 2 } + fadeOut(tween(DETAIL_ANIM_DURATION))
private val slideInFromLeft: EnterTransition =
    slideInHorizontally(
        tween(DETAIL_ANIM_DURATION, easing = FastOutSlowInEasing)
    ) { -it / 4 } + fadeIn(tween(DETAIL_ANIM_DURATION))
private val slideOutToLeft: ExitTransition =
    slideOutHorizontally(
        tween(DETAIL_ANIM_DURATION, easing = FastOutSlowInEasing)
    ) { -it / 4 } + fadeOut(tween(DETAIL_ANIM_DURATION))

/** Helper to navigate to the video player with a URI string. */
private fun NavHostController.navigateToPlayer(uri: String) {
    val encoded = android.util.Base64.encodeToString(
        uri.toByteArray(Charsets.UTF_8),
        android.util.Base64.URL_SAFE or android.util.Base64.NO_WRAP
    )
    navigate("player/$encoded")
}

/** Helper to navigate to the photo viewer with a URI string. */
private fun NavHostController.navigateToPhotoViewer(uri: String) {
    val encoded = android.util.Base64.encodeToString(
        uri.toByteArray(Charsets.UTF_8),
        android.util.Base64.URL_SAFE or android.util.Base64.NO_WRAP
    )
    navigate("photoViewer/$encoded")
}

/**
 * Smart media navigator: detects whether the URI is a photo or video and
 * routes to the appropriate viewer screen.
 */
private fun NavHostController.navigateToMedia(uri: String) {
    if (uri.contains("/images/") || uri.contains("/photo/")) {
        navigateToPhotoViewer(uri)
    } else {
        navigateToPlayer(uri)
    }
}

@Composable
fun MemoReelsNavHost(
    navController: NavHostController = rememberNavController(),
    userPreferences: UserPreferences? = null
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Observe feed mode for conditional Photos tab
    val feedMode by (userPreferences?.feedMode
        ?: kotlinx.coroutines.flow.flowOf(FeedMode.UNIFIED))
        .collectAsState(initial = FeedMode.UNIFIED)

    // Build tab list dynamically based on feed mode
    val tabs = remember(feedMode) {
        if (feedMode == FeedMode.SEPARATE) {
            listOf(Screen.Feed, Screen.Photos, Screen.Favorites, Screen.Explore)
        } else {
            listOf(Screen.Feed, Screen.Favorites, Screen.Explore)
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }

    val currentRoute = currentDestination?.route ?: ""
    val detailRoutes = setOf(
        "timeCapsules", "journal", "moodFeed", "duplicateCleaner",
        "memoryMap", "highlightReels", "peopleAlbums", "nearbySharing"
    )
    val showBottomBar = currentRoute != Screen.Onboarding.route &&
        !currentRoute.startsWith("player/") &&
        !currentRoute.startsWith("photoViewer/") &&
        !currentRoute.startsWith("collection/") &&
        !currentRoute.startsWith("collectionFeed/") &&
        currentRoute !in detailRoutes

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    tabs.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any {
                                it.route == screen.route
                            } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Onboarding.route,
            modifier = Modifier.padding(padding)
        ) {
            // --- Onboarding ---
            composable(Screen.Onboarding.route) {
                OnboardingScreen(
                    onComplete = {
                        navController.navigate(Screen.Feed.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                )
            }

            // --- Main tabs (fade + scale transitions) ---
            composable(
                Screen.Feed.route,
                enterTransition = { tabEnter },
                exitTransition = { tabExit },
                popEnterTransition = { tabEnter },
                popExitTransition = { tabExit }
            ) {
                VideoFeedScreen(
                    onNavigate = { route -> navController.navigate(route) },
                    onPhotoClick = { uri -> navController.navigateToPhotoViewer(uri) }
                )
            }

            // Photos tab (only navigable in SEPARATE mode, but route always exists)
            composable(
                Screen.Photos.route,
                enterTransition = { tabEnter },
                exitTransition = { tabExit },
                popEnterTransition = { tabEnter },
                popExitTransition = { tabExit }
            ) {
                PhotoFeedScreen(
                    onPhotoClick = { uri -> navController.navigateToPhotoViewer(uri) }
                )
            }

            composable(
                Screen.Favorites.route,
                enterTransition = { tabEnter },
                exitTransition = { tabExit },
                popEnterTransition = { tabEnter },
                popExitTransition = { tabExit }
            ) {
                FavoritesScreen(
                    onVideoClick = { video ->
                        navController.navigateToMedia(video.uri)
                    }
                )
            }

            composable(
                Screen.Explore.route,
                enterTransition = { tabEnter },
                exitTransition = { tabExit },
                popEnterTransition = { tabEnter },
                popExitTransition = { tabExit }
            ) {
                ExploreScreen(
                    onVideoClick = { video ->
                        navController.navigateToMedia(video.uri)
                    },
                    onVideoUriClick = { uri ->
                        navController.navigateToMedia(uri)
                    },
                    onCollectionClick = { tag ->
                        val encodedTag = android.util.Base64.encodeToString(
                            tag.toByteArray(Charsets.UTF_8),
                            android.util.Base64.URL_SAFE or android.util.Base64.NO_WRAP
                        )
                        navController.navigate("collection/$encodedTag")
                    }
                )
            }

            // --- Detail screens (slide transitions) ---
            composable(
                "collection/{encodedTag}",
                enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight }
            ) { backStackEntry ->
                val encodedTag = backStackEntry.arguments?.getString("encodedTag")
                    ?: return@composable
                val tag = try {
                    String(
                        android.util.Base64.decode(
                            encodedTag,
                            android.util.Base64.URL_SAFE or android.util.Base64.NO_WRAP
                        )
                    )
                } catch (e: Exception) {
                    return@composable
                }
                CollectionDetailScreen(
                    tag = tag,
                    onVideoClick = { index ->
                        navController.navigate("collectionFeed/$encodedTag/$index")
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                "collectionFeed/{encodedTag}/{startIndex}",
                enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight }
            ) { backStackEntry ->
                val encodedTag = backStackEntry.arguments?.getString("encodedTag")
                    ?: return@composable
                val startIndex = backStackEntry.arguments?.getString("startIndex")
                    ?.toIntOrNull() ?: 0
                val tag = try {
                    String(
                        android.util.Base64.decode(
                            encodedTag,
                            android.util.Base64.URL_SAFE or android.util.Base64.NO_WRAP
                        )
                    )
                } catch (e: Exception) {
                    return@composable
                }
                CollectionFeedScreen(
                    tag = tag,
                    startIndex = startIndex,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                "player/{encodedUri}",
                enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight }
            ) { backStackEntry ->
                val encoded = backStackEntry.arguments?.getString("encodedUri")
                    ?: return@composable
                val videoUri = try {
                    String(
                        android.util.Base64.decode(
                            encoded,
                            android.util.Base64.URL_SAFE or android.util.Base64.NO_WRAP
                        )
                    )
                } catch (e: Exception) {
                    return@composable
                }
                VideoPlayerScreen(
                    videoUri = videoUri,
                    onBack = { navController.popBackStack() }
                )
            }

            // --- Photo Viewer ---
            composable(
                "photoViewer/{encodedUri}",
                enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight }
            ) { backStackEntry ->
                val encoded = backStackEntry.arguments?.getString("encodedUri")
                    ?: return@composable
                val photoUri = try {
                    String(
                        android.util.Base64.decode(
                            encoded,
                            android.util.Base64.URL_SAFE or android.util.Base64.NO_WRAP
                        )
                    )
                } catch (e: Exception) {
                    return@composable
                }
                PhotoViewerScreen(
                    photoUri = photoUri,
                    onBack = { navController.popBackStack() }
                )
            }

            // --- New Feature Screens ---
            composable(
                "timeCapsules",
                enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight }
            ) {
                TimeCapsuleScreen(
                    onBack = { navController.popBackStack() },
                    onMediaClick = { uri -> navController.navigateToMedia(uri) }
                )
            }

            composable(
                "journal",
                enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight }
            ) {
                JournalScreen(
                    onBack = { navController.popBackStack() },
                    onMediaClick = { uri -> navController.navigateToMedia(uri) }
                )
            }

            composable(
                "moodFeed",
                enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight }
            ) {
                MoodFeedScreen(
                    onBack = { navController.popBackStack() },
                    onMediaClick = { uri -> navController.navigateToMedia(uri) }
                )
            }

            composable(
                "duplicateCleaner",
                enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight }
            ) {
                DuplicateCleanerScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                "memoryMap",
                enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight }
            ) {
                MemoryMapScreen(
                    onBack = { navController.popBackStack() },
                    onMediaClick = { uri -> navController.navigateToMedia(uri) }
                )
            }

            composable(
                "highlightReels",
                enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight }
            ) {
                HighlightReelsScreen(
                    onBack = { navController.popBackStack() },
                    onMediaClick = { uri -> navController.navigateToMedia(uri) }
                )
            }

            composable(
                "peopleAlbums",
                enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight }
            ) {
                PeopleAlbumsScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                "nearbySharing",
                enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight }
            ) {
                NearbySharingScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
