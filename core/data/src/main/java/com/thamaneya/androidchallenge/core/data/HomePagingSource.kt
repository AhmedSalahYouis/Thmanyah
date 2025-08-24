package com.thamaneya.androidchallenge.core.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thamaneya.androidchallenge.core.model.HomeSection
import com.thamaneya.androidchallenge.core.network.HomeApi

/**
 * PagingSource for home sections with infinite pagination
 */
class HomePagingSource(
    private val api: HomeApi,
    private val mapper: HomeSectionMapper
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
            val response = api.getHomeSections(page)
            
            val sections = response.sections.map { sectionDto ->
                mapper.mapToHomeSection(sectionDto)
            }
            
            val nextKey = if (response.pagination.nextPage != null && page < response.pagination.totalPages) {
                page + 1
            } else {
                null
            }
            
            LoadResult.Page(
                data = sections,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
