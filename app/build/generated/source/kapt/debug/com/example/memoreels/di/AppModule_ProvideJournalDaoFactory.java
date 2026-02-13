package com.example.memoreels.di;

import com.example.memoreels.data.local.AppDatabase;
import com.example.memoreels.data.local.JournalDao;
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
public final class AppModule_ProvideJournalDaoFactory implements Factory<JournalDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideJournalDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public JournalDao get() {
    return provideJournalDao(dbProvider.get());
  }

  public static AppModule_ProvideJournalDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideJournalDaoFactory(dbProvider);
  }

  public static JournalDao provideJournalDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideJournalDao(db));
  }
}
