package com.example.memoreels.ui.viewmodel;

import android.content.Context;
import com.example.memoreels.data.datasource.PhotoDataSource;
import com.example.memoreels.data.local.MediaLocationDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class MemoryMapViewModel_Factory implements Factory<MemoryMapViewModel> {
  private final Provider<MediaLocationDao> mediaLocationDaoProvider;

  private final Provider<PhotoDataSource> photoDataSourceProvider;

  private final Provider<Context> contextProvider;

  public MemoryMapViewModel_Factory(Provider<MediaLocationDao> mediaLocationDaoProvider,
      Provider<PhotoDataSource> photoDataSourceProvider, Provider<Context> contextProvider) {
    this.mediaLocationDaoProvider = mediaLocationDaoProvider;
    this.photoDataSourceProvider = photoDataSourceProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public MemoryMapViewModel get() {
    return newInstance(mediaLocationDaoProvider.get(), photoDataSourceProvider.get(), contextProvider.get());
  }

  public static MemoryMapViewModel_Factory create(
      Provider<MediaLocationDao> mediaLocationDaoProvider,
      Provider<PhotoDataSource> photoDataSourceProvider, Provider<Context> contextProvider) {
    return new MemoryMapViewModel_Factory(mediaLocationDaoProvider, photoDataSourceProvider, contextProvider);
  }

  public static MemoryMapViewModel newInstance(MediaLocationDao mediaLocationDao,
      PhotoDataSource photoDataSource, Context context) {
    return new MemoryMapViewModel(mediaLocationDao, photoDataSource, context);
  }
}
