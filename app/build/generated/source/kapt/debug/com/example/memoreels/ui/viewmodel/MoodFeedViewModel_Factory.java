package com.example.memoreels.ui.viewmodel;

import com.example.memoreels.data.local.VideoTagDao;
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
public final class MoodFeedViewModel_Factory implements Factory<MoodFeedViewModel> {
  private final Provider<VideoTagDao> videoTagDaoProvider;

  public MoodFeedViewModel_Factory(Provider<VideoTagDao> videoTagDaoProvider) {
    this.videoTagDaoProvider = videoTagDaoProvider;
  }

  @Override
  public MoodFeedViewModel get() {
    return newInstance(videoTagDaoProvider.get());
  }

  public static MoodFeedViewModel_Factory create(Provider<VideoTagDao> videoTagDaoProvider) {
    return new MoodFeedViewModel_Factory(videoTagDaoProvider);
  }

  public static MoodFeedViewModel newInstance(VideoTagDao videoTagDao) {
    return new MoodFeedViewModel(videoTagDao);
  }
}
