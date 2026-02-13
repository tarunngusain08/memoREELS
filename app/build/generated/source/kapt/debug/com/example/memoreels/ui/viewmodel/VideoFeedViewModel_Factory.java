package com.example.memoreels.ui.viewmodel;

import android.content.Context;
import com.example.memoreels.data.datasource.FeedItemFactory;
import com.example.memoreels.data.datasource.PhotoDataSource;
import com.example.memoreels.data.local.MediaLocationDao;
import com.example.memoreels.data.local.VideoTagDao;
import com.example.memoreels.data.preferences.UserPreferences;
import com.example.memoreels.domain.repository.VideoRepository;
import com.example.memoreels.domain.usecase.GetThisDayVideosUseCase;
import com.example.memoreels.domain.usecase.GetVideosUseCase;
import com.example.memoreels.domain.usecase.ToggleFavoriteUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class VideoFeedViewModel_Factory implements Factory<VideoFeedViewModel> {
  private final Provider<GetVideosUseCase> getVideosProvider;

  private final Provider<ToggleFavoriteUseCase> toggleFavoriteProvider;

  private final Provider<VideoRepository> repositoryProvider;

  private final Provider<GetThisDayVideosUseCase> getThisDayVideosProvider;

  private final Provider<PhotoDataSource> photoDataSourceProvider;

  private final Provider<FeedItemFactory> feedItemFactoryProvider;

  private final Provider<VideoTagDao> videoTagDaoProvider;

  private final Provider<MediaLocationDao> mediaLocationDaoProvider;

  private final Provider<UserPreferences> userPreferencesProvider;

  private final Provider<Context> appContextProvider;

  public VideoFeedViewModel_Factory(Provider<GetVideosUseCase> getVideosProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteProvider,
      Provider<VideoRepository> repositoryProvider,
      Provider<GetThisDayVideosUseCase> getThisDayVideosProvider,
      Provider<PhotoDataSource> photoDataSourceProvider,
      Provider<FeedItemFactory> feedItemFactoryProvider, Provider<VideoTagDao> videoTagDaoProvider,
      Provider<MediaLocationDao> mediaLocationDaoProvider,
      Provider<UserPreferences> userPreferencesProvider, Provider<Context> appContextProvider) {
    this.getVideosProvider = getVideosProvider;
    this.toggleFavoriteProvider = toggleFavoriteProvider;
    this.repositoryProvider = repositoryProvider;
    this.getThisDayVideosProvider = getThisDayVideosProvider;
    this.photoDataSourceProvider = photoDataSourceProvider;
    this.feedItemFactoryProvider = feedItemFactoryProvider;
    this.videoTagDaoProvider = videoTagDaoProvider;
    this.mediaLocationDaoProvider = mediaLocationDaoProvider;
    this.userPreferencesProvider = userPreferencesProvider;
    this.appContextProvider = appContextProvider;
  }

  @Override
  public VideoFeedViewModel get() {
    return newInstance(getVideosProvider.get(), toggleFavoriteProvider.get(), repositoryProvider.get(), getThisDayVideosProvider.get(), photoDataSourceProvider.get(), feedItemFactoryProvider.get(), videoTagDaoProvider.get(), mediaLocationDaoProvider.get(), userPreferencesProvider.get(), appContextProvider.get());
  }

  public static VideoFeedViewModel_Factory create(Provider<GetVideosUseCase> getVideosProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteProvider,
      Provider<VideoRepository> repositoryProvider,
      Provider<GetThisDayVideosUseCase> getThisDayVideosProvider,
      Provider<PhotoDataSource> photoDataSourceProvider,
      Provider<FeedItemFactory> feedItemFactoryProvider, Provider<VideoTagDao> videoTagDaoProvider,
      Provider<MediaLocationDao> mediaLocationDaoProvider,
      Provider<UserPreferences> userPreferencesProvider, Provider<Context> appContextProvider) {
    return new VideoFeedViewModel_Factory(getVideosProvider, toggleFavoriteProvider, repositoryProvider, getThisDayVideosProvider, photoDataSourceProvider, feedItemFactoryProvider, videoTagDaoProvider, mediaLocationDaoProvider, userPreferencesProvider, appContextProvider);
  }

  public static VideoFeedViewModel newInstance(GetVideosUseCase getVideos,
      ToggleFavoriteUseCase toggleFavorite, VideoRepository repository,
      GetThisDayVideosUseCase getThisDayVideos, PhotoDataSource photoDataSource,
      FeedItemFactory feedItemFactory, VideoTagDao videoTagDao, MediaLocationDao mediaLocationDao,
      UserPreferences userPreferences, Context appContext) {
    return new VideoFeedViewModel(getVideos, toggleFavorite, repository, getThisDayVideos, photoDataSource, feedItemFactory, videoTagDao, mediaLocationDao, userPreferences, appContext);
  }
}
