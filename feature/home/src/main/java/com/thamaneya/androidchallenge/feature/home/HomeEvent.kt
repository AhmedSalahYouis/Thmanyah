package com.thamaneya.androidchallenge.feature.home

import com.thamaneya.androidchallenge.core.model.HomeItem
import com.thamaneya.androidchallenge.core.model.EpisodeItem
import com.thamaneya.androidchallenge.core.model.PodcastItem

/**
 * Events for the Home feature
 */
sealed interface HomeEvent {
    data object OnRetry : HomeEvent
    data object OnRefresh : HomeEvent
    data class OnSectionVisible(val sectionIndex: Int) : HomeEvent
    data class OnItemClick(val item: HomeItem) : HomeEvent
    data class OnPlayClicked(val item: EpisodeItem) : HomeEvent
    data class OnPodcastClicked(val item: PodcastItem) : HomeEvent
}

