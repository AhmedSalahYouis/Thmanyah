package com.thamaneya.androidchallenge.feature.home

import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.thamaneya.androidchallenge.core.model.HomeSection
import kotlinx.coroutines.flow.StateFlow

/**
 * UI state for the Home feature
 */
data class HomeUiState(
    val paging: StateFlow<PagingData<HomeSection>>? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val lastInteractionTs: Long = System.currentTimeMillis()
)
