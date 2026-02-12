package com.example.memoreels.di;

import com.example.memoreels.data.local.AppDatabase;
import com.example.memoreels.data.local.FavoritesDao;
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
public final class AppModule_ProvideFavoritesDaoFactory implements Factory<FavoritesDao> {
  private final Provider<AppDatabase> databaseProvider;

  public AppModule_ProvideFavoritesDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public FavoritesDao get() {
    return provideFavoritesDao(databaseProvider.get());
  }

  public static AppModule_ProvideFavoritesDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new AppModule_ProvideFavoritesDaoFactory(databaseProvider);
  }

  public static FavoritesDao provideFavoritesDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideFavoritesDao(database));
  }
}
