package com.thamaneya.androidchallenge.core.domain.search

import com.thamaneya.androidchallenge.core.model.HomeSection

/**
 * Repository contract for search functionality
 */
interface SearchRepository {
    /**
     * Always hits network. No caching.
     */
    suspend fun searchSections(keyword: String): Result<List<HomeSection>>
}
