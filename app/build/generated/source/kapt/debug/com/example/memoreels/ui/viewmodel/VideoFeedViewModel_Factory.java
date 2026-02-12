package com.example.memoreels.ui.viewmodel;

import com.example.memoreels.data.ml.VideoTagger;
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
@QualifierMetadata
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

  private final Provider<VideoTagger> videoTaggerProvider;

  public VideoFeedViewModel_Factory(Provider<GetVideosUseCase> getVideosProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteProvider,
      Provider<VideoRepository> repositoryProvider,
      Provider<GetThisDayVideosUseCase> getThisDayVideosProvider,
      Provider<VideoTagger> videoTaggerProvider) {
    this.getVideosProvider = getVideosProvider;
    this.toggleFavoriteProvider = toggleFavoriteProvider;
    this.repositoryProvider = repositoryProvider;
    this.getThisDayVideosProvider = getThisDayVideosProvider;
    this.videoTaggerProvider = videoTaggerProvider;
  }

  @Override
  public VideoFeedViewModel get() {
    return newInstance(getVideosProvider.get(), toggleFavoriteProvider.get(), repositoryProvider.get(), getThisDayVideosProvider.get(), videoTaggerProvider.get());
  }

  public static VideoFeedViewModel_Factory create(Provider<GetVideosUseCase> getVideosProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteProvider,
      Provider<VideoRepository> repositoryProvider,
      Provider<GetThisDayVideosUseCase> getThisDayVideosProvider,
      Provider<VideoTagger> videoTaggerProvider) {
    return new VideoFeedViewModel_Factory(getVideosProvider, toggleFavoriteProvider, repositoryProvider, getThisDayVideosProvider, videoTaggerProvider);
  }

  public static VideoFeedViewModel newInstance(GetVideosUseCase getVideos,
      ToggleFavoriteUseCase toggleFavorite, VideoRepository repository,
      GetThisDayVideosUseCase getThisDayVideos, VideoTagger videoTagger) {
    return new VideoFeedViewModel(getVideos, toggleFavorite, repository, getThisDayVideos, videoTagger);
  }
}
