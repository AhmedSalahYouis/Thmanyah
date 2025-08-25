package com.thamaneya.androidchallenge.core.data

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.thamaneya.androidchallenge.core.data.local.AppDatabase
import com.thamaneya.androidchallenge.core.data.local.HomeItemEntity
import com.thamaneya.androidchallenge.core.data.local.HomeSectionEntity
import com.thamaneya.androidchallenge.core.data.local.RemoteKeysEntity
import com.thamaneya.androidchallenge.core.network.HomeApi
import com.thamaneya.error.DataErrorException
import com.thamaneya.error.IDataErrorProvider
import com.thamaneya.logger.logging.ITimberLogger
import retrofit2.HttpException
import java.io.IOException

/**
 * RemoteMediator for handling offline-first data with remote synchronization
 */
@OptIn(ExperimentalPagingApi::class)
class HomeRemoteMediator(
    private val api: HomeApi,
    private val database: AppDatabase,
    private val mapper: HomeSectionMapper,
    private val logger: ITimberLogger,
    private val dataErrorProvider: IDataErrorProvider,

) : RemoteMediator<Int, HomeSectionEntity>() {

    private val homeSectionDao = database.homeSectionDao()
    private val homeItemDao = database.homeItemDao()
    private val remoteKeysDao = database.remoteKeysDao()


    companion object {
        const val SECTIONS_STARTING_PAGE_INDEX = 1
    }
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HomeSectionEntity>
    ): MediatorResult {
        try {
            logger.logDebug("HomeRemoteMediator: Starting load operation - loadType: $loadType")


            val page = when (loadType) {
                LoadType.REFRESH -> SECTIONS_STARTING_PAGE_INDEX
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> remoteKeysDao.getLastRemoteKey()?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
            }

            logger.logDebug("HomeRemoteMediator: Loading page $page with loadType: $loadType")

            // Fetch data from remote API
            val response = api.getHomeSections(page)
            logger.logInfo("HomeRemoteMediator: API response: ${response.sections.size} sections, total pages: ${response.pagination.totalPages}")

            // Map DTOs to domain models
            val sections = response.sections.filterNotNull().mapNotNull { sectionDto ->
                try {
                    mapper.mapToHomeSection(sectionDto)
                } catch (e: Exception) {
                    logger.logError("HomeRemoteMediator: Failed to map section: ${sectionDto.name}", e)
                    null
                }
            }

            logger.logDebug("HomeRemoteMediator: Successfully mapped ${sections.size} sections")

            // Convert domain models to entities
            val sectionEntities = sections.map { section ->
                HomeSectionEntity(
                    id = "${section.name}_${section.order}_$page",
                    name = section.name,
                    order = section.order,
                    layout = section.layout,
                    contentType = section.contentType,
                    page = page
                )
            }

            val itemEntities = sections.flatMap { section ->
                section.items.mapIndexed { index, item ->
                    HomeItemEntity(
                        id = "${item.id}_${section.name}_$page",
                        sectionId = "${section.name}_${section.order}_$page",
                        name = item.name,
                        description = item.description,
                        avatarUrl = item.avatarUrl,
                        duration = item.durationSeconds,
                        contentType = section.contentType, // Use section's contentType
                        order = index
                    )
                }
            }

            // Create remote keys for pagination
            val remoteKeys = sections.map { section ->
                RemoteKeysEntity(
                    sectionId = "${section.name}_${section.order}_$page",
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (page < response.pagination.totalPages) page + 1 else null
                )
            }

            // Save to database
                if (loadType == LoadType.REFRESH) {
                    logger.logDebug("HomeRemoteMediator: Clearing existing data for refresh")
                    // Clear existing data on refresh
                    homeSectionDao.deleteAllSections()
                    homeItemDao.deleteAllItems()
                    remoteKeysDao.deleteAllRemoteKeys()
                }

                // Insert new data
                homeSectionDao.insertSections(sectionEntities)
                homeItemDao.insertItems(itemEntities)
                remoteKeysDao.insertRemoteKeys(remoteKeys)


            logger.logInfo("HomeRemoteMediator: Successfully saved ${sectionEntities.size} sections and ${itemEntities.size} items to database")

            return MediatorResult.Success(
                endOfPaginationReached = page >= response.pagination.totalPages
            )

        } catch (e: Exception) {
            logger.logError("HomeRemoteMediator: Unexpected error during load operation", e)
            return MediatorResult.Error(
                DataErrorException(
                    dataErrorProvider.fromThrowable(e),
                    e
                )
            )
        }
    }
}
