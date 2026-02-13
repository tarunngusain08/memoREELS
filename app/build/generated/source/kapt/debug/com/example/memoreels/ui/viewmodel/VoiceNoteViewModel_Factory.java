package com.example.memoreels.ui.viewmodel;

import android.content.Context;
import com.example.memoreels.data.local.VoiceNoteDao;
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
public final class VoiceNoteViewModel_Factory implements Factory<VoiceNoteViewModel> {
  private final Provider<VoiceNoteDao> voiceNoteDaoProvider;

  private final Provider<Context> contextProvider;

  public VoiceNoteViewModel_Factory(Provider<VoiceNoteDao> voiceNoteDaoProvider,
      Provider<Context> contextProvider) {
    this.voiceNoteDaoProvider = voiceNoteDaoProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public VoiceNoteViewModel get() {
    return newInstance(voiceNoteDaoProvider.get(), contextProvider.get());
  }

  public static VoiceNoteViewModel_Factory create(Provider<VoiceNoteDao> voiceNoteDaoProvider,
      Provider<Context> contextProvider) {
    return new VoiceNoteViewModel_Factory(voiceNoteDaoProvider, contextProvider);
  }

  public static VoiceNoteViewModel newInstance(VoiceNoteDao voiceNoteDao, Context context) {
    return new VoiceNoteViewModel(voiceNoteDao, context);
  }
}
