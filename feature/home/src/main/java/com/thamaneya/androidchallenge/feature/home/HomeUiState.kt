package com.thamaneya.androidchallenge.feature.home

import androidx.paging.compose.LazyPagingItems
import com.thamaneya.androidchallenge.core.model.HomeSection

/**
 * UI state for the Home feature
 */
data class HomeUiState(
    val paging: LazyPagingItems<HomeSection>? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val lastInteractionTs: Long = System.currentTimeMillis()
)
