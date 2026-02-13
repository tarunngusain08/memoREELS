package com.example.memoreels.data.ml;

import android.content.Context;
import com.example.memoreels.data.datasource.PhotoDataSource;
import com.example.memoreels.data.local.VideoTagDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class PhotoTagger_Factory implements Factory<PhotoTagger> {
  private final Provider<Context> contextProvider;

  private final Provider<VideoTagDao> videoTagDaoProvider;

  private final Provider<PhotoDataSource> photoDataSourceProvider;

  public PhotoTagger_Factory(Provider<Context> contextProvider,
      Provider<VideoTagDao> videoTagDaoProvider,
      Provider<PhotoDataSource> photoDataSourceProvider) {
    this.contextProvider = contextProvider;
    this.videoTagDaoProvider = videoTagDaoProvider;
    this.photoDataSourceProvider = photoDataSourceProvider;
  }

  @Override
  public PhotoTagger get() {
    return newInstance(contextProvider.get(), videoTagDaoProvider.get(), photoDataSourceProvider.get());
  }

  public static PhotoTagger_Factory create(Provider<Context> contextProvider,
      Provider<VideoTagDao> videoTagDaoProvider,
      Provider<PhotoDataSource> photoDataSourceProvider) {
    return new PhotoTagger_Factory(contextProvider, videoTagDaoProvider, photoDataSourceProvider);
  }

  public static PhotoTagger newInstance(Context context, VideoTagDao videoTagDao,
      PhotoDataSource photoDataSource) {
    return new PhotoTagger(context, videoTagDao, photoDataSource);
  }
}
