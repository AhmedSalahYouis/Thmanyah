package com.thamaneya.androidchallenge.core.data.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thamaneya.androidchallenge.core.data.HomeEntityMapper
import com.thamaneya.androidchallenge.core.model.HomeSection

/**
 * Local PagingSource for reading HomeSection data from Room database
 */
class HomeSectionLocalPagingSource(
    private val homeSectionDao: HomeSectionDao,
    private val entityMapper: HomeEntityMapper,
    private val homeItemDao: HomeItemDao
) : PagingSource<Int, HomeSection>() {
    
    override fun getRefreshKey(state: PagingState<Int, HomeSection>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
    
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeSection> {
        return try {
            val page = params.key ?: 1
            val sectionEntities = homeSectionDao.getSectionsByPage(page)
            
            // Convert entities to domain models
            val sections = sectionEntities.map { sectionEntity ->
                val items = homeItemDao.getItemsBySectionId(sectionEntity.id)
                entityMapper.mapToHomeSection(sectionEntity, items)
            }
            
            val nextKey = if (sections.isNotEmpty()) page + 1 else null
            val prevKey = if (page == 1) null else page - 1
            
            LoadResult.Page(
                data = sections,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
