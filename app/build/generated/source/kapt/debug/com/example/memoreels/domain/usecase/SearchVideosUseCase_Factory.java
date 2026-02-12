package com.example.memoreels.domain.usecase;

import com.example.memoreels.domain.repository.VideoRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class SearchVideosUseCase_Factory implements Factory<SearchVideosUseCase> {
  private final Provider<VideoRepository> repositoryProvider;

  public SearchVideosUseCase_Factory(Provider<VideoRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SearchVideosUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SearchVideosUseCase_Factory create(Provider<VideoRepository> repositoryProvider) {
    return new SearchVideosUseCase_Factory(repositoryProvider);
  }

  public static SearchVideosUseCase newInstance(VideoRepository repository) {
    return new SearchVideosUseCase(repository);
  }
}
