package com.thamaneya.androidchallenge.feature.search

import com.thamaneya.androidchallenge.core.model.HomeItem

/**
 * Events for the search feature
 */
sealed interface SearchEvent {
    data class OnQueryChanged(val text: String) : SearchEvent
    data object OnRetry : SearchEvent
    data class OnItemClick(val item: HomeItem) : SearchEvent
    data object OnBackClick : SearchEvent
}

