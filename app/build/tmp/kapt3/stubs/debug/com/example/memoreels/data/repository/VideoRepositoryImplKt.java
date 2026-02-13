package com.example.memoreels.data.repository;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import com.example.memoreels.data.datasource.VideoPagingSourceFactory;
import com.example.memoreels.data.local.FavoritesDao;
import com.example.memoreels.data.model.FavoriteEntity;
import com.example.memoreels.data.model.VideoEntity;
import com.example.memoreels.domain.model.Favorite;
import com.example.memoreels.domain.model.Video;
import com.example.memoreels.domain.repository.VideoRepository;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002\u001a\f\u0010\u0000\u001a\u00020\u0003*\u00020\u0004H\u0002\u00a8\u0006\u0005"}, d2 = {"toDomain", "Lcom/example/memoreels/domain/model/Favorite;", "Lcom/example/memoreels/data/model/FavoriteEntity;", "Lcom/example/memoreels/domain/model/Video;", "Lcom/example/memoreels/data/model/VideoEntity;", "app_debug"})
public final class VideoRepositoryImplKt {
    
    private static final com.example.memoreels.domain.model.Video toDomain(com.example.memoreels.data.model.VideoEntity $this$toDomain) {
        return null;
    }
    
    private static final com.example.memoreels.domain.model.Favorite toDomain(com.example.memoreels.data.model.FavoriteEntity $this$toDomain) {
        return null;
    }
}