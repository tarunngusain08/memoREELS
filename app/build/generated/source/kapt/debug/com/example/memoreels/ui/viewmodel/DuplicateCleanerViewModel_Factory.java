package com.example.memoreels.ui.viewmodel;

import android.content.Context;
import com.example.memoreels.data.datasource.PhotoDataSource;
import com.example.memoreels.data.local.MediaHashDao;
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
public final class DuplicateCleanerViewModel_Factory implements Factory<DuplicateCleanerViewModel> {
  private final Provider<MediaHashDao> mediaHashDaoProvider;

  private final Provider<PhotoDataSource> photoDataSourceProvider;

  private final Provider<Context> contextProvider;

  public DuplicateCleanerViewModel_Factory(Provider<MediaHashDao> mediaHashDaoProvider,
      Provider<PhotoDataSource> photoDataSourceProvider, Provider<Context> contextProvider) {
    this.mediaHashDaoProvider = mediaHashDaoProvider;
    this.photoDataSourceProvider = photoDataSourceProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public DuplicateCleanerViewModel get() {
    return newInstance(mediaHashDaoProvider.get(), photoDataSourceProvider.get(), contextProvider.get());
  }

  public static DuplicateCleanerViewModel_Factory create(
      Provider<MediaHashDao> mediaHashDaoProvider,
      Provider<PhotoDataSource> photoDataSourceProvider, Provider<Context> contextProvider) {
    return new DuplicateCleanerViewModel_Factory(mediaHashDaoProvider, photoDataSourceProvider, contextProvider);
  }

  public static DuplicateCleanerViewModel newInstance(MediaHashDao mediaHashDao,
      PhotoDataSource photoDataSource, Context context) {
    return new DuplicateCleanerViewModel(mediaHashDao, photoDataSource, context);
  }
}
