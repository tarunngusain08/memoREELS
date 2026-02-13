package com.example.memoreels.di;

import com.example.memoreels.data.local.AppDatabase;
import com.example.memoreels.data.local.VoiceNoteDao;
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
public final class AppModule_ProvideVoiceNoteDaoFactory implements Factory<VoiceNoteDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideVoiceNoteDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public VoiceNoteDao get() {
    return provideVoiceNoteDao(dbProvider.get());
  }

  public static AppModule_ProvideVoiceNoteDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideVoiceNoteDaoFactory(dbProvider);
  }

  public static VoiceNoteDao provideVoiceNoteDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideVoiceNoteDao(db));
  }
}
