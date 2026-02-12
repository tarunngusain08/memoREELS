package com.example.memoreels.domain.usecase;

import android.content.Context;
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
public final class GetThisDayVideosUseCase_Factory implements Factory<GetThisDayVideosUseCase> {
  private final Provider<Context> contextProvider;

  public GetThisDayVideosUseCase_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public GetThisDayVideosUseCase get() {
    return newInstance(contextProvider.get());
  }

  public static GetThisDayVideosUseCase_Factory create(Provider<Context> contextProvider) {
    return new GetThisDayVideosUseCase_Factory(contextProvider);
  }

  public static GetThisDayVideosUseCase newInstance(Context context) {
    return new GetThisDayVideosUseCase(context);
  }
}
