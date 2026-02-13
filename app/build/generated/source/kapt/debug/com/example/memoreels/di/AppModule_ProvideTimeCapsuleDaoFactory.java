package com.example.memoreels.di;

import com.example.memoreels.data.local.AppDatabase;
import com.example.memoreels.data.local.TimeCapsuleDao;
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
public final class AppModule_ProvideTimeCapsuleDaoFactory implements Factory<TimeCapsuleDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideTimeCapsuleDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public TimeCapsuleDao get() {
    return provideTimeCapsuleDao(dbProvider.get());
  }

  public static AppModule_ProvideTimeCapsuleDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideTimeCapsuleDaoFactory(dbProvider);
  }

  public static TimeCapsuleDao provideTimeCapsuleDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideTimeCapsuleDao(db));
  }
}
