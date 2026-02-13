package com.example.memoreels.di;

import com.example.memoreels.data.local.AppDatabase;
import com.example.memoreels.data.local.MediaHashDao;
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
public final class AppModule_ProvideMediaHashDaoFactory implements Factory<MediaHashDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideMediaHashDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public MediaHashDao get() {
    return provideMediaHashDao(dbProvider.get());
  }

  public static AppModule_ProvideMediaHashDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideMediaHashDaoFactory(dbProvider);
  }

  public static MediaHashDao provideMediaHashDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideMediaHashDao(db));
  }
}
