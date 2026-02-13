package com.example.memoreels.ui.viewmodel;

import android.content.Context;
import com.example.memoreels.data.datasource.PhotoDataSource;
import com.example.memoreels.data.local.JournalDao;
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
public final class JournalViewModel_Factory implements Factory<JournalViewModel> {
  private final Provider<JournalDao> journalDaoProvider;

  private final Provider<PhotoDataSource> photoDataSourceProvider;

  private final Provider<Context> contextProvider;

  public JournalViewModel_Factory(Provider<JournalDao> journalDaoProvider,
      Provider<PhotoDataSource> photoDataSourceProvider, Provider<Context> contextProvider) {
    this.journalDaoProvider = journalDaoProvider;
    this.photoDataSourceProvider = photoDataSourceProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public JournalViewModel get() {
    return newInstance(journalDaoProvider.get(), photoDataSourceProvider.get(), contextProvider.get());
  }

  public static JournalViewModel_Factory create(Provider<JournalDao> journalDaoProvider,
      Provider<PhotoDataSource> photoDataSourceProvider, Provider<Context> contextProvider) {
    return new JournalViewModel_Factory(journalDaoProvider, photoDataSourceProvider, contextProvider);
  }

  public static JournalViewModel newInstance(JournalDao journalDao, PhotoDataSource photoDataSource,
      Context context) {
    return new JournalViewModel(journalDao, photoDataSource, context);
  }
}
