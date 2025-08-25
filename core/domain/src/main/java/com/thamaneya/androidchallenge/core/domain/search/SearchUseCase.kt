package com.thamaneya.androidchallenge.core.domain.search

import com.thamaneya.androidchallenge.core.model.HomeSection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.util.Locale
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * UseCase contract for search functionality
 */
interface SearchUseCase {
    /**
     * Applies debounce(200), distinctUntilChanged, and flatMapLatest.
     * No caching: each trigger hits repository (network).
     * Provides a retry path even for the same query.
     */
    fun stream(query: Flow<String>, retry: Flow<Unit>): Flow<Result<List<HomeSection>>>
}

/**
 * UseCase implementation for search functionality and business logic
 */
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchUseCaseImpl(
    private val repository: SearchRepository,
    private val ioDispatcher: CoroutineDispatcher
) : SearchUseCase {
    companion object {
        const val DEBOUNCE_TIME = 200L
    }

    override fun stream(
        query: Flow<String>,
        retry: Flow<Unit>
    ): Flow<Result<List<HomeSection>>> {
        val normalized = query
            .map { it.trim().replace(Regex("\\s+"), " ") }
            .debounce(DEBOUNCE_TIME)
            .map { it.lowercase(Locale.ROOT) }
            .distinctUntilChanged()

        // Combine normalized query with a retry ticker so we can re-hit network
        // for the same query without changing text.
        return combine(normalized, retry.onStart { emit(Unit) }) { q, _ -> q }
            .flatMapLatest { q ->
                flow {
                    if (q.isEmpty()) {
                        emit(Result.success(emptyList()))
                    } else {
                        emit(repository.searchSections(q))
                    }
                }
            }
            .flowOn(ioDispatcher)
    }
}
