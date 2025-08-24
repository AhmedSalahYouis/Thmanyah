package com.thamaneya.androidchallenge.core.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.thamaneya.androidchallenge.core.data.local.AppDatabase
import com.thamaneya.androidchallenge.core.data.local.HomeItemEntity
import com.thamaneya.androidchallenge.core.data.local.HomeSectionEntity
import com.thamaneya.androidchallenge.core.data.local.RemoteKeysEntity
import com.thamaneya.androidchallenge.core.network.HomeApi
import retrofit2.HttpException
import java.io.IOException

/**
 * RemoteMediator for handling offline-first data with remote synchronization
 */
@OptIn(ExperimentalPagingApi::class)
class HomeRemoteMediator(
    private val api: HomeApi,
    private val database: AppDatabase,
    private val mapper: HomeSectionMapper
) : RemoteMediator<Int, HomeSectionEntity>() {

    private val homeSectionDao = database.homeSectionDao()
    private val homeItemDao = database.homeItemDao()
    private val remoteKeysDao = database.remoteKeysDao()


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, HomeSectionEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { section ->
            remoteKeysDao.getRemoteKeyBySectionId(section.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, HomeSectionEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { section ->
                remoteKeysDao.getRemoteKeyBySectionId(section.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, HomeSectionEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let { section ->
                remoteKeysDao.getRemoteKeyBySectionId(section.id)
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HomeSectionEntity>
    ): MediatorResult {
        try {
            // Determine the page to load
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                    if (prevKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                    prevKey
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                    if (nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                    nextKey
                }
            }

            Log.d("HomeRemoteMediator", "Loading page $page with loadType: $loadType")

            // Fetch data from remote API
            val response = api.getHomeSections(page)
            Log.d(
                "HomeRemoteMediator",
                "API response: ${response.sections.size} sections, total pages: ${response.pagination.totalPages}"
            )

            // Map DTOs to domain models
            val sections = response.sections.map { sectionDto ->
                mapper.mapToHomeSection(sectionDto)
            }

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

            // Save to database in a transaction

            if (loadType == LoadType.REFRESH) {
                // Clear existing data on refresh
                homeSectionDao.deleteAllSections()
                homeItemDao.deleteAllItems()
                remoteKeysDao.deleteAllRemoteKeys()
            }

            // Insert new data
            homeSectionDao.insertSections(sectionEntities)
            homeItemDao.insertItems(itemEntities)
            remoteKeysDao.insertRemoteKeys(remoteKeys)


            return MediatorResult.Success(
                endOfPaginationReached = page >= response.pagination.totalPages
            )

        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}
