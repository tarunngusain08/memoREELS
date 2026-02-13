package com.example.memoreels.ui.viewmodel;

import com.example.memoreels.data.local.VideoTagDao;
import com.example.memoreels.data.ml.PhotoTagger;
import com.example.memoreels.data.ml.VideoTagger;
import com.example.memoreels.domain.repository.VideoRepository;
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
public final class ExploreViewModel_Factory implements Factory<ExploreViewModel> {
  private final Provider<VideoRepository> repositoryProvider;

  private final Provider<VideoTagDao> videoTagDaoProvider;

  private final Provider<VideoTagger> videoTaggerProvider;

  private final Provider<PhotoTagger> photoTaggerProvider;

  public ExploreViewModel_Factory(Provider<VideoRepository> repositoryProvider,
      Provider<VideoTagDao> videoTagDaoProvider, Provider<VideoTagger> videoTaggerProvider,
      Provider<PhotoTagger> photoTaggerProvider) {
    this.repositoryProvider = repositoryProvider;
    this.videoTagDaoProvider = videoTagDaoProvider;
    this.videoTaggerProvider = videoTaggerProvider;
    this.photoTaggerProvider = photoTaggerProvider;
  }

  @Override
  public ExploreViewModel get() {
    return newInstance(repositoryProvider.get(), videoTagDaoProvider.get(), videoTaggerProvider.get(), photoTaggerProvider.get());
  }

  public static ExploreViewModel_Factory create(Provider<VideoRepository> repositoryProvider,
      Provider<VideoTagDao> videoTagDaoProvider, Provider<VideoTagger> videoTaggerProvider,
      Provider<PhotoTagger> photoTaggerProvider) {
    return new ExploreViewModel_Factory(repositoryProvider, videoTagDaoProvider, videoTaggerProvider, photoTaggerProvider);
  }

  public static ExploreViewModel newInstance(VideoRepository repository, VideoTagDao videoTagDao,
      VideoTagger videoTagger, PhotoTagger photoTagger) {
    return new ExploreViewModel(repository, videoTagDao, videoTagger, photoTagger);
  }
}
