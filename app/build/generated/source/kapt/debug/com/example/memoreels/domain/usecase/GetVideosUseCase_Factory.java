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
public final class GetVideosUseCase_Factory implements Factory<GetVideosUseCase> {
  private final Provider<VideoRepository> repositoryProvider;

  public GetVideosUseCase_Factory(Provider<VideoRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetVideosUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetVideosUseCase_Factory create(Provider<VideoRepository> repositoryProvider) {
    return new GetVideosUseCase_Factory(repositoryProvider);
  }

  public static GetVideosUseCase newInstance(VideoRepository repository) {
    return new GetVideosUseCase(repository);
  }
}
