package com.example.memoreels.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MediaTaggingWorker_AssistedFactory_Impl implements MediaTaggingWorker_AssistedFactory {
  private final MediaTaggingWorker_Factory delegateFactory;

  MediaTaggingWorker_AssistedFactory_Impl(MediaTaggingWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public MediaTaggingWorker create(Context arg0, WorkerParameters arg1) {
    return delegateFactory.get(arg0, arg1);
  }

  public static Provider<MediaTaggingWorker_AssistedFactory> create(
      MediaTaggingWorker_Factory delegateFactory) {
    return InstanceFactory.create(new MediaTaggingWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<MediaTaggingWorker_AssistedFactory> createFactoryProvider(
      MediaTaggingWorker_Factory delegateFactory) {
    return InstanceFactory.create(new MediaTaggingWorker_AssistedFactory_Impl(delegateFactory));
  }
}
