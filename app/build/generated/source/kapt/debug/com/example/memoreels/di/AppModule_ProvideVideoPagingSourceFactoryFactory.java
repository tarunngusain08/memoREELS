package com.example.memoreels.di;

import android.content.Context;
import com.example.memoreels.data.datasource.VideoPagingSourceFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideVideoPagingSourceFactoryFactory implements Factory<VideoPagingSourceFactory> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideVideoPagingSourceFactoryFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public VideoPagingSourceFactory get() {
    return provideVideoPagingSourceFactory(contextProvider.get());
  }

  public static AppModule_ProvideVideoPagingSourceFactoryFactory create(
      Provider<Context> contextProvider) {
    return new AppModule_ProvideVideoPagingSourceFactoryFactory(contextProvider);
  }

  public static VideoPagingSourceFactory provideVideoPagingSourceFactory(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideVideoPagingSourceFactory(context));
  }
}
