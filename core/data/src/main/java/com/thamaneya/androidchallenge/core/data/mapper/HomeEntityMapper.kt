package com.thamaneya.androidchallenge.core.data.mapper

import com.thamaneya.androidchallenge.core.data.home.local.HomeItemEntity
import com.thamaneya.androidchallenge.core.data.home.local.HomeSectionEntity
import com.thamaneya.androidchallenge.core.model.AudioArticleItem
import com.thamaneya.androidchallenge.core.model.AudioBookItem
import com.thamaneya.androidchallenge.core.model.ContentType
import com.thamaneya.androidchallenge.core.model.EpisodeItem
import com.thamaneya.androidchallenge.core.model.HomeItem
import com.thamaneya.androidchallenge.core.model.HomeSection
import com.thamaneya.androidchallenge.core.model.PodcastItem

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
            ContentType.PODCAST ->
                PodcastItem(
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
            ContentType.EPISODE ->
                EpisodeItem(
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
            ContentType.AUDIO_BOOK ->
                AudioBookItem(
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
            ContentType.AUDIO_ARTICLE ->
                AudioArticleItem(
                    id = itemEntity.id,
                    name = itemEntity.name,
                    description = itemEntity.description,
                    avatarUrl = itemEntity.avatarUrl,
                    durationSeconds = itemEntity.duration,
                    authorName = "", // Default value, should be stored in entity
                    releaseDateIso = "",
                    score = null
                )
            else -> PodcastItem(
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
