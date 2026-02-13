package com.example.memoreels.data.datasource;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class PhotoDataSource_Factory implements Factory<PhotoDataSource> {
  private final Provider<Context> contextProvider;

  public PhotoDataSource_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public PhotoDataSource get() {
    return newInstance(contextProvider.get());
  }

  public static PhotoDataSource_Factory create(Provider<Context> contextProvider) {
    return new PhotoDataSource_Factory(contextProvider);
  }

  public static PhotoDataSource newInstance(Context context) {
    return new PhotoDataSource(context);
  }
}
