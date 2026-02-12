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
  private final Provider<AppDatabase> databaseProvider;

  public AppModule_ProvideVideoTagDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public VideoTagDao get() {
    return provideVideoTagDao(databaseProvider.get());
  }

  public static AppModule_ProvideVideoTagDaoFactory create(Provider<AppDatabase> databaseProvider) {
    return new AppModule_ProvideVideoTagDaoFactory(databaseProvider);
  }

  public static VideoTagDao provideVideoTagDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideVideoTagDao(database));
  }
}
