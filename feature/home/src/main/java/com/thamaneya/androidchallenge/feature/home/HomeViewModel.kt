package com.thamaneya.androidchallenge.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thamaneya.androidchallenge.core.data.HomeEntityMapper
import com.thamaneya.androidchallenge.core.data.HomeRepository
import com.thamaneya.androidchallenge.core.data.local.AppDatabase
import com.thamaneya.androidchallenge.core.model.HomeSection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the Home feature following MVVM + UDF pattern
 */
class HomeViewModel(
    private val repository: HomeRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())

    val pagingFlow: StateFlow<PagingData<HomeSection>> = repository
        .pagedSections()
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Lazily, PagingData.empty())


    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnRetry -> {
                // Refresh the paging data
                viewModelScope.launch {
                    _uiState.update { it.copy(isError = false, errorMessage = null) }
                }
            }
            is HomeEvent.OnRefresh -> {
                // Refresh the paging data
                viewModelScope.launch {
                    _uiState.update { it.copy(isError = false, errorMessage = null) }
                    // The RemoteMediator will handle the refresh automatically
                    // when the paging data is invalidated
                }
            }
            is HomeEvent.OnSectionVisible -> {
                // Handle section visibility for telemetry
                _uiState.update { 
                    it.copy(lastInteractionTs = System.currentTimeMillis()) 
                }
            }
            is HomeEvent.OnItemClick -> {
                // Handle item click
                _uiState.update { 
                    it.copy(lastInteractionTs = System.currentTimeMillis()) 
                }
                // TODO: Navigate to appropriate detail screen
            }
            is HomeEvent.OnPlayClicked -> {
                // Handle play button click for episodes
                _uiState.update { 
                    it.copy(lastInteractionTs = System.currentTimeMillis()) 
                }
                // TODO: Navigate to player
            }
            is HomeEvent.OnPodcastClicked -> {
                // Handle podcast click
                _uiState.update { 
                    it.copy(lastInteractionTs = System.currentTimeMillis()) 
                }
                // TODO: Navigate to podcast detail
            }
        }
    }
}
