package com.thamaneya.androidchallenge.core.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thamaneya.androidchallenge.core.data.local.HomeSectionDao
import com.thamaneya.androidchallenge.core.data.local.HomeSectionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * PagingSource for HomeSection that reads from database
 */
class HomeSectionPagingSource(
    private val homeSectionDao: HomeSectionDao,
) : PagingSource<Int, HomeSectionEntity>() {
    
    override fun getRefreshKey(state: PagingState<Int, HomeSectionEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeSectionEntity> {
        return try {
            val page = params.key ?: 1
            val sectionEntities = withContext(Dispatchers.IO) {
                homeSectionDao.getSectionsByPage(page)
            }
            
            val nextKey = if (sectionEntities.isNotEmpty()) page + 1 else null
            val prevKey = if (page == 1) null else page - 1
            
            LoadResult.Page(
                data = sectionEntities,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
