package com.thamaneya.androidchallenge.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.cachedIn
import com.thamaneya.androidchallenge.core.data.home.HomeRepository
import com.thamaneya.androidchallenge.core.model.ContentType
import com.thamaneya.androidchallenge.core.model.HomeSection
import com.thamaneya.logger.logging.ITimberLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the Home feature following MVVM + UDF pattern
 */
class HomeViewModel(
    private val repository: HomeRepository,
    private val logger: ITimberLogger
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())

    private val _displayHomeSections = MutableStateFlow<PagingData<HomeSection>>(PagingData.empty())
    val displayHomeSections: StateFlow<PagingData<HomeSection>> = _displayHomeSections.asStateFlow()
    private val _baseHomeSections = MutableStateFlow<PagingData<HomeSection>>(PagingData.empty())
    val baseHomeSections: StateFlow<PagingData<HomeSection>> = _baseHomeSections.asStateFlow()

    init {
        loadHomeSections()
    }

    private fun loadHomeSections() {
        viewModelScope.launch {
            repository.pagedSections()
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _baseHomeSections.value = pagingData
                    applyContentTypeFilter()
                }
        }
    }


    private val _selectedContentType = MutableStateFlow<ContentType?>(null)
    val selectedContentType: StateFlow<ContentType?> = _selectedContentType.asStateFlow()

    /**
     * Handles selecting a content type chip.
     * This filters the home sections based on the selected content type.
     */
    fun selectContentType(contentType: ContentType?) {
        _selectedContentType.value = contentType
        applyContentTypeFilter()
    }

    private fun applyContentTypeFilter() {
        val currentPagingData = baseHomeSections.value
        val selectedType = _selectedContentType?.value

        try {
            if (selectedType == null) {
                // No filter applied, show all sections
                _displayHomeSections.update { currentPagingData }
            } else {
                // Apply filter based on selected content type
                val filteredPagingData = currentPagingData.filter { section ->
                    section.contentType == selectedType
                }
                _displayHomeSections.update { filteredPagingData }
            }
        } catch (e: Exception) {
            logger.logError("Home: Failed to apply content type filter", e)
            // Fallback to showing all sections
            _uiState.update { it.copy(paging = MutableStateFlow(currentPagingData)) }
        }
    }

    /**
     * Get available content types for filtering
     */
    fun getAvailableContentTypes(): List<ContentType> {
        return ContentType.entries.filter { it != ContentType.UNKNOWN }
    }
}
