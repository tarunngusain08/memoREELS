package com.example.memoreels.data.datasource;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class FeedItemFactory_Factory implements Factory<FeedItemFactory> {
  @Override
  public FeedItemFactory get() {
    return newInstance();
  }

  public static FeedItemFactory_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FeedItemFactory newInstance() {
    return new FeedItemFactory();
  }

  private static final class InstanceHolder {
    private static final FeedItemFactory_Factory INSTANCE = new FeedItemFactory_Factory();
  }
}
