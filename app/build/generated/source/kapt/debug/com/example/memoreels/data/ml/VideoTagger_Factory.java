package com.example.memoreels.data.ml;

import android.content.Context;
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
public final class VideoTagger_Factory implements Factory<VideoTagger> {
  private final Provider<Context> contextProvider;

  private final Provider<VideoTagDao> videoTagDaoProvider;

  public VideoTagger_Factory(Provider<Context> contextProvider,
      Provider<VideoTagDao> videoTagDaoProvider) {
    this.contextProvider = contextProvider;
    this.videoTagDaoProvider = videoTagDaoProvider;
  }

  @Override
  public VideoTagger get() {
    return newInstance(contextProvider.get(), videoTagDaoProvider.get());
  }

  public static VideoTagger_Factory create(Provider<Context> contextProvider,
      Provider<VideoTagDao> videoTagDaoProvider) {
    return new VideoTagger_Factory(contextProvider, videoTagDaoProvider);
  }

  public static VideoTagger newInstance(Context context, VideoTagDao videoTagDao) {
    return new VideoTagger(context, videoTagDao);
  }
}
