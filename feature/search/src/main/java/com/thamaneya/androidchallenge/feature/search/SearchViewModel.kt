package com.thamaneya.androidchallenge.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thamaneya.androidchallenge.core.domain.search.SearchUseCase
import com.thamaneya.error.DataError
import com.thamaneya.error.DataErrorException
import com.thamaneya.logger.logging.ITimberLogger
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * ViewModel for the search feature
 */
class SearchViewModel(
    private val useCase: SearchUseCase
) : ViewModel(), KoinComponent {

    private val logger: ITimberLogger by inject()

    private val queryFlow = MutableStateFlow("")
    private val retryFlow = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    private val _ui = MutableStateFlow(SearchUiState())
    val ui: StateFlow<SearchUiState> = _ui.asStateFlow()

    init {
        viewModelScope.launch {
            useCase.stream(queryFlow, retryFlow)
                .onStart { 
                    _ui.update { it.copy(isLoading = false) } // idle until first keystroke
                }
                .onEach { 
                    _ui.update { it.copy(isLoading = true, isError = false, error = null) } 
                }
                .catch { ex ->
                    logger.logError("Search error", (ex as? DataErrorException))
                    val err = (ex as? DataErrorException)?.error ?: DataError.Network.UNEXPECTED_ERROR
                    _ui.update { it.copy(isLoading = false, isError = true, error = err) }
                }
                .collect { result ->
                    result.onSuccess { sections ->
                        _ui.update {
                            it.copy(
                                sections = sections,
                                isLoading = false,
                                isError = false,
                                error = null,
                                isEmptyResults = it.query.isNotBlank() && sections.isEmpty(),
                                isQueryEmpty = it.query.isBlank() && sections.isEmpty()
                            )
                        }
                    }.onFailure { ex ->
                        logger.logError("Search failed", (ex as? DataErrorException))
                        val err = (ex as? DataErrorException)?.error ?: DataError.Network.UNEXPECTED_ERROR
                        _ui.update { it.copy(isLoading = false, isError = true, error = err) }
                    }
                }
        }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnQueryChanged -> {
                _ui.update { it.copy(query = event.text) }
                queryFlow.value = event.text
            }
            SearchEvent.OnRetry -> {
                logger.logDebug("Retry requested for query: ${_ui.value.query}")
                retryFlow.tryEmit(Unit) // re-hit network even if query unchanged
            }
            is SearchEvent.OnItemClick -> {
                logger.logDebug("Search: Item clicked: ${event.item.name}")
                // TODO: Navigate to appropriate detail screen
            }
            SearchEvent.OnBackClick -> {
                logger.logDebug("Search: Back button clicked")
                // Handled in Composable via navController.popBackStack()
            }
        }
    }
}

