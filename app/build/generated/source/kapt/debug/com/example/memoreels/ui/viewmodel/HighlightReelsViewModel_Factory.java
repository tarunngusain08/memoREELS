package com.example.memoreels.ui.viewmodel;

import android.content.Context;
import com.example.memoreels.data.datasource.PhotoDataSource;
import com.example.memoreels.data.local.VideoTagDao;
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
public final class HighlightReelsViewModel_Factory implements Factory<HighlightReelsViewModel> {
  private final Provider<PhotoDataSource> photoDataSourceProvider;

  private final Provider<VideoTagDao> videoTagDaoProvider;

  private final Provider<Context> contextProvider;

  public HighlightReelsViewModel_Factory(Provider<PhotoDataSource> photoDataSourceProvider,
      Provider<VideoTagDao> videoTagDaoProvider, Provider<Context> contextProvider) {
    this.photoDataSourceProvider = photoDataSourceProvider;
    this.videoTagDaoProvider = videoTagDaoProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public HighlightReelsViewModel get() {
    return newInstance(photoDataSourceProvider.get(), videoTagDaoProvider.get(), contextProvider.get());
  }

  public static HighlightReelsViewModel_Factory create(
      Provider<PhotoDataSource> photoDataSourceProvider, Provider<VideoTagDao> videoTagDaoProvider,
      Provider<Context> contextProvider) {
    return new HighlightReelsViewModel_Factory(photoDataSourceProvider, videoTagDaoProvider, contextProvider);
  }

  public static HighlightReelsViewModel newInstance(PhotoDataSource photoDataSource,
      VideoTagDao videoTagDao, Context context) {
    return new HighlightReelsViewModel(photoDataSource, videoTagDao, context);
  }
}
