package com.example.memoreels.ui.viewmodel;

import com.example.memoreels.data.datasource.FeedItemFactory;
import com.example.memoreels.data.datasource.PhotoDataSource;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class PhotoFeedViewModel_Factory implements Factory<PhotoFeedViewModel> {
  private final Provider<PhotoDataSource> photoDataSourceProvider;

  private final Provider<FeedItemFactory> feedItemFactoryProvider;

  public PhotoFeedViewModel_Factory(Provider<PhotoDataSource> photoDataSourceProvider,
      Provider<FeedItemFactory> feedItemFactoryProvider) {
    this.photoDataSourceProvider = photoDataSourceProvider;
    this.feedItemFactoryProvider = feedItemFactoryProvider;
  }

  @Override
  public PhotoFeedViewModel get() {
    return newInstance(photoDataSourceProvider.get(), feedItemFactoryProvider.get());
  }

  public static PhotoFeedViewModel_Factory create(Provider<PhotoDataSource> photoDataSourceProvider,
      Provider<FeedItemFactory> feedItemFactoryProvider) {
    return new PhotoFeedViewModel_Factory(photoDataSourceProvider, feedItemFactoryProvider);
  }

  public static PhotoFeedViewModel newInstance(PhotoDataSource photoDataSource,
      FeedItemFactory feedItemFactory) {
    return new PhotoFeedViewModel(photoDataSource, feedItemFactory);
  }
}
