package com.example.memoreels.di;

import com.example.memoreels.data.local.AppDatabase;
import com.example.memoreels.data.local.VideoTagDao;
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
public final class AppModule_ProvideVideoTagDaoFactory implements Factory<VideoTagDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideVideoTagDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public VideoTagDao get() {
    return provideVideoTagDao(dbProvider.get());
  }

  public static AppModule_ProvideVideoTagDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideVideoTagDaoFactory(dbProvider);
  }

  public static VideoTagDao provideVideoTagDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideVideoTagDao(db));
  }
}
