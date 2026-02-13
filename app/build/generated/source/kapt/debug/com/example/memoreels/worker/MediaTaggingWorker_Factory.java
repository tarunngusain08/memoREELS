package com.example.memoreels.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.example.memoreels.data.ml.PhotoTagger;
import com.example.memoreels.data.ml.VideoTagger;
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
public final class MediaTaggingWorker_Factory {
  private final Provider<VideoTagger> videoTaggerProvider;

  private final Provider<PhotoTagger> photoTaggerProvider;

  public MediaTaggingWorker_Factory(Provider<VideoTagger> videoTaggerProvider,
      Provider<PhotoTagger> photoTaggerProvider) {
    this.videoTaggerProvider = videoTaggerProvider;
    this.photoTaggerProvider = photoTaggerProvider;
  }

  public MediaTaggingWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, videoTaggerProvider.get(), photoTaggerProvider.get());
  }

  public static MediaTaggingWorker_Factory create(Provider<VideoTagger> videoTaggerProvider,
      Provider<PhotoTagger> photoTaggerProvider) {
    return new MediaTaggingWorker_Factory(videoTaggerProvider, photoTaggerProvider);
  }

  public static MediaTaggingWorker newInstance(Context appContext, WorkerParameters workerParams,
      VideoTagger videoTagger, PhotoTagger photoTagger) {
    return new MediaTaggingWorker(appContext, workerParams, videoTagger, photoTagger);
  }
}
