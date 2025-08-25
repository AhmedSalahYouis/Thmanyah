package com.thamaneya.androidchallenge.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thamaneya.androidchallenge.core.data.HomeRepository
import com.thamaneya.androidchallenge.core.model.HomeSection
import com.thamaneya.logger.logging.ITimberLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the Home feature following MVVM + UDF pattern
 */
class HomeViewModel(
    repository: HomeRepository,
    private val logger: ITimberLogger
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())

    val pagingFlow: StateFlow<PagingData<HomeSection>> = repository
        .pagedSections()
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Lazily, PagingData.empty())

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnRetry -> {
                logger.logInfo("Home: Retry requested by user")
                // Refresh the paging data
                viewModelScope.launch {
                    try {
                        _uiState.update { it.copy(isError = false, errorMessage = null) }
                        logger.logDebug("Home: UI state updated after retry")
                    } catch (e: Exception) {
                        logger.logError("Home: Failed to update UI state after retry", e)
                    }
                }
            }
            is HomeEvent.OnRefresh -> {
                logger.logInfo("Home: Refresh requested by user")
                // Refresh the paging data
                viewModelScope.launch {
                    try {
                        _uiState.update { it.copy(isError = false, errorMessage = null) }
                        logger.logDebug("Home: UI state updated after refresh")
                        // The RemoteMediator will handle the refresh automatically
                        // when the paging data is invalidated
                    } catch (e: Exception) {
                        logger.logError("Home: Failed to update UI state after refresh", e)
                    }
                }
            }
            is HomeEvent.OnSectionVisible -> {
                logger.logDebug("Home: Section ${event.sectionIndex} became visible")
                // Handle section visibility for telemetry
                _uiState.update { 
                    it.copy(lastInteractionTs = System.currentTimeMillis()) 
                }
            }
            is HomeEvent.OnItemClick -> {
                logger.logDebug("Home: Item clicked: ${event.item.name}")
                // Handle item click
                _uiState.update { 
                    it.copy(lastInteractionTs = System.currentTimeMillis()) 
                }
                // TODO: Navigate to appropriate detail screen
            }
            is HomeEvent.OnPlayClicked -> {
                logger.logDebug("Home: Play button clicked for episode: ${event.item.name}")
                // Handle play button click for episodes
                _uiState.update { 
                    it.copy(lastInteractionTs = System.currentTimeMillis()) 
                }
                // TODO: Navigate to player
            }
            is HomeEvent.OnPodcastClicked -> {
                logger.logDebug("Home: Podcast clicked: ${event.item.name}")
                // Handle podcast click
                _uiState.update { 
                    it.copy(lastInteractionTs = System.currentTimeMillis()) 
                }
                // TODO: Navigate to podcast detail
            }
        }
    }
}
