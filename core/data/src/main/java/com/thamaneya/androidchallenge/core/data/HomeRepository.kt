package com.thamaneya.androidchallenge.core.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.thamaneya.androidchallenge.core.data.local.AppDatabase
import com.thamaneya.androidchallenge.core.model.HomeSection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Repository interface for home data
 */
interface HomeRepository {
    fun pagedSections(): Flow<PagingData<HomeSection>>
}

/**
 * Repository implementation for home data with RemoteMediator
 */
@OptIn(ExperimentalPagingApi::class)
class HomeRepositoryImpl(
    private val remoteMediator: HomeRemoteMediator,
    private val database: AppDatabase,
    private val entityMapper: HomeEntityMapper
) : HomeRepository {

    override fun pagedSections(): Flow<PagingData<HomeSection>> {
        Log.d("HomeRepository", "Creating Pager with RemoteMediator")
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 3
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = {
                database.homeSectionDao().pagingSource()
            }
        ).flow.map { pagingData ->
            pagingData.map { sectionEntity ->
                // Convert entities to domain models, ensuring database access is on a background thread
                val items = withContext(Dispatchers.IO) {
                    database.homeItemDao().getItemsBySectionId(sectionEntity.id)
                }
                entityMapper.mapToHomeSection(sectionEntity, items)
            }
        }
    }
}
