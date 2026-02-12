package com.example.memoreels.data.repository;

import com.example.memoreels.data.datasource.VideoPagingSourceFactory;
import com.example.memoreels.data.local.FavoritesDao;
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
public final class VideoRepositoryImpl_Factory implements Factory<VideoRepositoryImpl> {
  private final Provider<VideoPagingSourceFactory> videoPagingSourceFactoryProvider;

  private final Provider<FavoritesDao> favoritesDaoProvider;

  public VideoRepositoryImpl_Factory(
      Provider<VideoPagingSourceFactory> videoPagingSourceFactoryProvider,
      Provider<FavoritesDao> favoritesDaoProvider) {
    this.videoPagingSourceFactoryProvider = videoPagingSourceFactoryProvider;
    this.favoritesDaoProvider = favoritesDaoProvider;
  }

  @Override
  public VideoRepositoryImpl get() {
    return newInstance(videoPagingSourceFactoryProvider.get(), favoritesDaoProvider.get());
  }

  public static VideoRepositoryImpl_Factory create(
      Provider<VideoPagingSourceFactory> videoPagingSourceFactoryProvider,
      Provider<FavoritesDao> favoritesDaoProvider) {
    return new VideoRepositoryImpl_Factory(videoPagingSourceFactoryProvider, favoritesDaoProvider);
  }

  public static VideoRepositoryImpl newInstance(VideoPagingSourceFactory videoPagingSourceFactory,
      FavoritesDao favoritesDao) {
    return new VideoRepositoryImpl(videoPagingSourceFactory, favoritesDao);
  }
}
