package com.thamaneya.androidchallenge.feature.search

import com.thamaneya.androidchallenge.core.model.HomeSection
import com.thamaneya.error.DataError

/**
 * UI state for the search feature
 */
data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: DataError? = null,
    val sections: List<HomeSection> = emptyList(),
    val isEmptyResults: Boolean = false,
    val isQueryEmpty: Boolean = true
)

