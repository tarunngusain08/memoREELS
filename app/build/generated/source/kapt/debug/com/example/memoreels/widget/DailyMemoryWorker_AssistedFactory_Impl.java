package com.example.memoreels.widget;

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
public final class DailyMemoryWorker_AssistedFactory_Impl implements DailyMemoryWorker_AssistedFactory {
  private final DailyMemoryWorker_Factory delegateFactory;

  DailyMemoryWorker_AssistedFactory_Impl(DailyMemoryWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public DailyMemoryWorker create(Context arg0, WorkerParameters arg1) {
    return delegateFactory.get(arg0, arg1);
  }

  public static Provider<DailyMemoryWorker_AssistedFactory> create(
      DailyMemoryWorker_Factory delegateFactory) {
    return InstanceFactory.create(new DailyMemoryWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<DailyMemoryWorker_AssistedFactory> createFactoryProvider(
      DailyMemoryWorker_Factory delegateFactory) {
    return InstanceFactory.create(new DailyMemoryWorker_AssistedFactory_Impl(delegateFactory));
  }
}
