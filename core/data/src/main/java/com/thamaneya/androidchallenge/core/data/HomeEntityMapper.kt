package com.thamaneya.androidchallenge.core.data

import com.thamaneya.androidchallenge.core.data.local.HomeItemEntity
import com.thamaneya.androidchallenge.core.data.local.HomeSectionEntity
import com.thamaneya.androidchallenge.core.model.HomeItem
import com.thamaneya.androidchallenge.core.model.HomeSection

/**
 * Mapper for converting between Room entities and domain models
 */
class HomeEntityMapper {
    
    /**
     * Convert HomeSectionEntity to HomeSection domain model
     */
    fun mapToHomeSection(
        sectionEntity: HomeSectionEntity,
        items: List<HomeItemEntity>
    ): HomeSection {
        return HomeSection(
            name = sectionEntity.name,
            order = sectionEntity.order,
            layout = sectionEntity.layout,
            contentType = sectionEntity.contentType,
            items = items.map { mapToHomeItem(it) }
        )
    }
    
    /**
     * Convert HomeItemEntity to HomeItem domain model
     */
    fun mapToHomeItem(itemEntity: HomeItemEntity): HomeItem {
        // Since HomeItem is a sealed interface, we need to determine the concrete type
        // For now, we'll use PodcastItem as default - this should be improved based on contentType
        return when (itemEntity.contentType) {
            com.thamaneya.androidchallenge.core.model.ContentType.PODCAST -> 
                com.thamaneya.androidchallenge.core.model.PodcastItem(
                    id = itemEntity.id,
                    name = itemEntity.name,
                    description = itemEntity.description,
                    avatarUrl = itemEntity.avatarUrl,
                    durationSeconds = itemEntity.duration,
                    episodeCount = 0, // Default value, should be stored in entity
                    language = null,
                    priority = null,
                    popularityScore = null,
                    score = null
                )
            com.thamaneya.androidchallenge.core.model.ContentType.EPISODE ->
                com.thamaneya.androidchallenge.core.model.EpisodeItem(
                    id = itemEntity.id,
                    name = itemEntity.name,
                    description = itemEntity.description,
                    avatarUrl = itemEntity.avatarUrl,
                    durationSeconds = itemEntity.duration,
                    podcastId = "", // Default value, should be stored in entity
                    podcastName = "",
                    audioUrl = "",
                    releaseDateIso = "",
                    episodeType = "",
                    score = null
                )
            com.thamaneya.androidchallenge.core.model.ContentType.AUDIO_BOOK ->
                com.thamaneya.androidchallenge.core.model.AudioBookItem(
                    id = itemEntity.id,
                    name = itemEntity.name,
                    description = itemEntity.description,
                    avatarUrl = itemEntity.avatarUrl,
                    durationSeconds = itemEntity.duration,
                    authorName = "", // Default value, should be stored in entity
                    language = null,
                    releaseDateIso = "",
                    score = null
                )
            com.thamaneya.androidchallenge.core.model.ContentType.AUDIO_ARTICLE ->
                com.thamaneya.androidchallenge.core.model.AudioArticleItem(
                    id = itemEntity.id,
                    name = itemEntity.name,
                    description = itemEntity.description,
                    avatarUrl = itemEntity.avatarUrl,
                    durationSeconds = itemEntity.duration,
                    authorName = "", // Default value, should be stored in entity
                    releaseDateIso = "",
                    score = null
                )
            else -> com.thamaneya.androidchallenge.core.model.PodcastItem(
                id = itemEntity.id,
                name = itemEntity.name,
                description = itemEntity.description,
                avatarUrl = itemEntity.avatarUrl,
                durationSeconds = itemEntity.duration,
                episodeCount = 0,
                language = null,
                priority = null,
                popularityScore = null,
                score = null
            )
        }
    }
}
