package com.thamaneya.androidchallenge.core.network

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API interface for home sections
 */
interface HomeApi {
    @GET("/home_sections")
    suspend fun getHomeSections(@Query("page") page: Int): HomeResponseDto
}

