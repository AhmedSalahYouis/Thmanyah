package com.thamaneya.androidchallenge.core.network

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API interface for search functionality
 */
interface SearchApi {
    @GET("search")
    suspend fun search(@Query("keyword") keyword: String): HomeResponseDto
}

