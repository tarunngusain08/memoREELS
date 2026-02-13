package com.example.memoreels.ui.viewmodel;

import com.example.memoreels.data.local.TimeCapsuleDao;
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
public final class TimeCapsuleViewModel_Factory implements Factory<TimeCapsuleViewModel> {
  private final Provider<TimeCapsuleDao> timeCapsuleDaoProvider;

  public TimeCapsuleViewModel_Factory(Provider<TimeCapsuleDao> timeCapsuleDaoProvider) {
    this.timeCapsuleDaoProvider = timeCapsuleDaoProvider;
  }

  @Override
  public TimeCapsuleViewModel get() {
    return newInstance(timeCapsuleDaoProvider.get());
  }

  public static TimeCapsuleViewModel_Factory create(
      Provider<TimeCapsuleDao> timeCapsuleDaoProvider) {
    return new TimeCapsuleViewModel_Factory(timeCapsuleDaoProvider);
  }

  public static TimeCapsuleViewModel newInstance(TimeCapsuleDao timeCapsuleDao) {
    return new TimeCapsuleViewModel(timeCapsuleDao);
  }
}
