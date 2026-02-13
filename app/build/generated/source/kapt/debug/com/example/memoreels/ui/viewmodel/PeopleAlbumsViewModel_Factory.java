package com.example.memoreels.ui.viewmodel;

import android.content.Context;
import com.example.memoreels.data.datasource.PhotoDataSource;
import com.example.memoreels.data.local.FaceClusterDao;
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
public final class PeopleAlbumsViewModel_Factory implements Factory<PeopleAlbumsViewModel> {
  private final Provider<FaceClusterDao> faceClusterDaoProvider;

  private final Provider<PhotoDataSource> photoDataSourceProvider;

  private final Provider<Context> contextProvider;

  public PeopleAlbumsViewModel_Factory(Provider<FaceClusterDao> faceClusterDaoProvider,
      Provider<PhotoDataSource> photoDataSourceProvider, Provider<Context> contextProvider) {
    this.faceClusterDaoProvider = faceClusterDaoProvider;
    this.photoDataSourceProvider = photoDataSourceProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public PeopleAlbumsViewModel get() {
    return newInstance(faceClusterDaoProvider.get(), photoDataSourceProvider.get(), contextProvider.get());
  }

  public static PeopleAlbumsViewModel_Factory create(
      Provider<FaceClusterDao> faceClusterDaoProvider,
      Provider<PhotoDataSource> photoDataSourceProvider, Provider<Context> contextProvider) {
    return new PeopleAlbumsViewModel_Factory(faceClusterDaoProvider, photoDataSourceProvider, contextProvider);
  }

  public static PeopleAlbumsViewModel newInstance(FaceClusterDao faceClusterDao,
      PhotoDataSource photoDataSource, Context context) {
    return new PeopleAlbumsViewModel(faceClusterDao, photoDataSource, context);
  }
}
