package com.example.memoreels.widget;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.example.memoreels.data.local.VideoTagDao;
import dagger.internal.DaggerGenerated;
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
public final class DailyMemoryWorker_Factory {
  private final Provider<VideoTagDao> videoTagDaoProvider;

  public DailyMemoryWorker_Factory(Provider<VideoTagDao> videoTagDaoProvider) {
    this.videoTagDaoProvider = videoTagDaoProvider;
  }

  public DailyMemoryWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, videoTagDaoProvider.get());
  }

  public static DailyMemoryWorker_Factory create(Provider<VideoTagDao> videoTagDaoProvider) {
    return new DailyMemoryWorker_Factory(videoTagDaoProvider);
  }

  public static DailyMemoryWorker newInstance(Context context, WorkerParameters params,
      VideoTagDao videoTagDao) {
    return new DailyMemoryWorker(context, params, videoTagDao);
  }
}
