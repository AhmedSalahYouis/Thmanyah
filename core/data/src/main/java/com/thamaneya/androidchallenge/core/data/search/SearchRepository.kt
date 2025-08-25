package com.thamaneya.androidchallenge.core.data.search

import com.thamaneya.androidchallenge.core.data.mapper.HomeSectionMapper
import com.thamaneya.androidchallenge.core.domain.search.SearchRepository
import com.thamaneya.androidchallenge.core.model.HomeSection
import com.thamaneya.androidchallenge.core.network.SearchApi
import com.thamaneya.error.DataErrorException
import com.thamaneya.error.IDataErrorProvider
import com.thamaneya.logger.logging.ITimberLogger

/**
 * Repository implementation for search functionality (online only, no cache)
 */
class SearchRepositoryImpl(
    private val api: SearchApi,
    private val mapper: HomeSectionMapper,
    private val dataErrorProvider: IDataErrorProvider,
    private val logger: ITimberLogger
) : SearchRepository {

    override suspend fun searchSections(keyword: String): Result<List<HomeSection>> {
        val q = keyword.trim()
        if (q.isEmpty()) return Result.success(emptyList())

        return runCatching {
            logger.logDebug("Searching for keyword: $q")
            val dto = api.search(q)

            // Map DTOs to domain models
            val sections = dto.sections.filterNotNull().mapNotNull { sectionDto ->
                try {
                    mapper.mapToHomeSection(sectionDto)
                } catch (e: Exception) {
                    logger.logError(
                        "SearchRepositoryImpl: Failed to map section: ${sectionDto.name}",
                        e
                    )
                    null
                }
            }
            logger.logDebug("Search completed. Found ${sections.size} sections")
            sections
        }.recoverCatching { exception ->
            logger.logError("Search failed for keyword: $q", (exception as? DataErrorException))
            throw DataErrorException(
                dataErrorProvider.fromThrowable(exception),
                exception
            )
        }
    }
}
