package com.example.memoreels.ui.viewmodel;

import com.example.memoreels.data.local.TimeCapsuleDao;
import com.example.memoreels.data.local.VideoTagDao;
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

  private final Provider<VideoTagDao> videoTagDaoProvider;

  public TimeCapsuleViewModel_Factory(Provider<TimeCapsuleDao> timeCapsuleDaoProvider,
      Provider<VideoTagDao> videoTagDaoProvider) {
    this.timeCapsuleDaoProvider = timeCapsuleDaoProvider;
    this.videoTagDaoProvider = videoTagDaoProvider;
  }

  @Override
  public TimeCapsuleViewModel get() {
    return newInstance(timeCapsuleDaoProvider.get(), videoTagDaoProvider.get());
  }

  public static TimeCapsuleViewModel_Factory create(Provider<TimeCapsuleDao> timeCapsuleDaoProvider,
      Provider<VideoTagDao> videoTagDaoProvider) {
    return new TimeCapsuleViewModel_Factory(timeCapsuleDaoProvider, videoTagDaoProvider);
  }

  public static TimeCapsuleViewModel newInstance(TimeCapsuleDao timeCapsuleDao,
      VideoTagDao videoTagDao) {
    return new TimeCapsuleViewModel(timeCapsuleDao, videoTagDao);
  }
}
