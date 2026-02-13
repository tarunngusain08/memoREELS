package com.example.memoreels.di;

import com.example.memoreels.data.local.AppDatabase;
import com.example.memoreels.data.local.FaceClusterDao;
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
public final class AppModule_ProvideFaceClusterDaoFactory implements Factory<FaceClusterDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideFaceClusterDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public FaceClusterDao get() {
    return provideFaceClusterDao(dbProvider.get());
  }

  public static AppModule_ProvideFaceClusterDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideFaceClusterDaoFactory(dbProvider);
  }

  public static FaceClusterDao provideFaceClusterDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideFaceClusterDao(db));
  }
}
