package com.example.memoreels.di;

import com.example.memoreels.data.repository.VideoRepositoryImpl;
import com.example.memoreels.domain.repository.VideoRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideVideoRepositoryFactory implements Factory<VideoRepository> {
  private final Provider<VideoRepositoryImpl> implProvider;

  public AppModule_ProvideVideoRepositoryFactory(Provider<VideoRepositoryImpl> implProvider) {
    this.implProvider = implProvider;
  }

  @Override
  public VideoRepository get() {
    return provideVideoRepository(implProvider.get());
  }

  public static AppModule_ProvideVideoRepositoryFactory create(
      Provider<VideoRepositoryImpl> implProvider) {
    return new AppModule_ProvideVideoRepositoryFactory(implProvider);
  }

  public static VideoRepository provideVideoRepository(VideoRepositoryImpl impl) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideVideoRepository(impl));
  }
}
