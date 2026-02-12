package com.example.memoreels.data.datasource;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class VideoPagingSourceFactory_Factory implements Factory<VideoPagingSourceFactory> {
  private final Provider<Context> contextProvider;

  public VideoPagingSourceFactory_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public VideoPagingSourceFactory get() {
    return newInstance(contextProvider.get());
  }

  public static VideoPagingSourceFactory_Factory create(Provider<Context> contextProvider) {
    return new VideoPagingSourceFactory_Factory(contextProvider);
  }

  public static VideoPagingSourceFactory newInstance(Context context) {
    return new VideoPagingSourceFactory(context);
  }
}
