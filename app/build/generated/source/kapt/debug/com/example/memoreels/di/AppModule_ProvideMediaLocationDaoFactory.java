package com.example.memoreels.di;

import com.example.memoreels.data.local.AppDatabase;
import com.example.memoreels.data.local.MediaLocationDao;
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
public final class AppModule_ProvideMediaLocationDaoFactory implements Factory<MediaLocationDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideMediaLocationDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public MediaLocationDao get() {
    return provideMediaLocationDao(dbProvider.get());
  }

  public static AppModule_ProvideMediaLocationDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideMediaLocationDaoFactory(dbProvider);
  }

  public static MediaLocationDao provideMediaLocationDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideMediaLocationDao(db));
  }
}
