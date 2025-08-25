package com.thamaneya.androidchallenge.core.data.mapper

import com.google.gson.Gson
import com.thamaneya.androidchallenge.core.model.AudioArticleItem
import com.thamaneya.androidchallenge.core.model.AudioBookItem
import com.thamaneya.androidchallenge.core.model.ContentType
import com.thamaneya.androidchallenge.core.model.EpisodeItem
import com.thamaneya.androidchallenge.core.model.HomeItem
import com.thamaneya.androidchallenge.core.model.HomeSection
import com.thamaneya.androidchallenge.core.model.PodcastItem
import com.thamaneya.androidchallenge.core.network.AudioArticleDto
import com.thamaneya.androidchallenge.core.network.AudioBookDto
import com.thamaneya.androidchallenge.core.network.EpisodeDto
import com.thamaneya.androidchallenge.core.network.PodcastDto
import com.thamaneya.androidchallenge.core.network.SectionDto
import com.thamaneya.androidchallenge.core.network.normalizedContentType
import com.thamaneya.androidchallenge.core.network.normalizedLayout
import com.thamaneya.logger.logging.ITimberLogger

/**
 * Mapper to convert network DTOs to domain models
 */
class HomeSectionMapper(
    private val logger: ITimberLogger
) {

    private val gson = Gson()

    /**
     * Map SectionDto to HomeSection domain model
     */
    fun mapToHomeSection(sectionDto: SectionDto): HomeSection {
        try {
            logger.logDebug("Mapping section: ${sectionDto.name}")

            val layout = sectionDto.normalizedLayout()
            var contentType = sectionDto.normalizedContentType()

            val items = if (contentType == ContentType.UNKNOWN){
                // Attempt to infer content type if not explicitly set and items are empty
                contentType = inferContentTypeFromContent(sectionDto.content)
                mapContentItems(contentType, sectionDto.content)
            }else {
                mapContentItems(contentType, sectionDto.content)
            }
            logger.logDebug("Successfully mapped section: ${sectionDto.name} with ${items.size} items")

            return HomeSection(
                name = sectionDto.name,
                order = sectionDto.order as? Int ?: 0,
                layout = layout,
                contentType = contentType,
                items = items.distinctBy { it.id } // Deduplicate by ID
            )
        } catch (e: Exception) {
            logger.logError("Failed to map section: ${sectionDto.name}", e)
            throw e
        }
    }

    private fun mapContentItems(contentType: ContentType, content: List<Map<String, Any>>): List<HomeItem> {
        return when (contentType) {
                ContentType.PODCAST -> mapPodcastItems(content)
                ContentType.EPISODE -> mapEpisodeItems(content)
                ContentType.AUDIO_BOOK -> mapAudioBookItems(content)
                ContentType.AUDIO_ARTICLE -> mapAudioArticleItems(content)
                ContentType.UNKNOWN -> emptyList()
            }
    }

    private fun inferContentTypeFromContent(content: List<Map<String, Any>>): ContentType {
        return when {
            content.all { it.containsKey("podcast_id") } -> ContentType.PODCAST
            content.all { it.containsKey("episode_id") } -> ContentType.EPISODE
            content.all { it.containsKey("audiobook_id") } -> ContentType.AUDIO_BOOK
            content.all { it.containsKey("article_id") } -> ContentType.AUDIO_ARTICLE
            else -> ContentType.UNKNOWN
        }
    }

    private fun mapPodcastItems(content: List<Map<String, Any>>): List<PodcastItem> {
        return content.mapNotNull { itemMap ->
            try {
                val jsonString = gson.toJson(itemMap)
                val dto = gson.fromJson(jsonString, PodcastDto::class.java)

                PodcastItem(
                    id = dto.podcastId,
                    name = dto.name,
                    description = dto.description.toPlainText(),
                    avatarUrl = dto.avatarUrl,
                    durationSeconds = dto.duration,
                    episodeCount = dto.episodeCount as? Int ?: 0,
                    language = dto.language,
                    priority = dto.priority as? Int,
                    popularityScore = dto.popularityScore as? Int ?: 0,
                    score = dto.score as? Double
                )
            } catch (e: Exception) {
                logger.logError("Failed to map podcast item: ${itemMap["name"]}", e)
                null
            }
        }
    }

    private fun mapEpisodeItems(content: List<Map<String, Any>>): List<EpisodeItem> {
        return content.mapNotNull { itemMap ->
            try {
                val jsonString = gson.toJson(itemMap)
                val dto = gson.fromJson(jsonString, EpisodeDto::class.java)

                EpisodeItem(
                    id = dto.episodeId,
                    name = dto.name,
                    description = dto.description.toPlainText(),
                    avatarUrl = dto.avatarUrl,
                    durationSeconds = dto.duration,
                    podcastId = dto.podcastId,
                    podcastName = dto.podcastName,
                    audioUrl = dto.audioUrl,
                    releaseDateIso = dto.releaseDate,
                    episodeType = dto.episodeType,
                    score = dto.score
                )
            } catch (e: Exception) {
                logger.logError("Failed to map episode item: ${itemMap["name"]}", e)
                null
            }
        }
    }

    private fun mapAudioBookItems(content: List<Map<String, Any>>): List<AudioBookItem> {
        return content.mapNotNull { itemMap ->
            try {
                val jsonString = gson.toJson(itemMap)
                val dto = gson.fromJson(jsonString, AudioBookDto::class.java)

                AudioBookItem(
                    id = dto.audiobookId,
                    name = dto.name,
                    description = dto.description.toPlainText(),
                    avatarUrl = dto.avatarUrl,
                    durationSeconds = dto.duration,
                    authorName = dto.authorName,
                    language = dto.language,
                    releaseDateIso = dto.releaseDate,
                    score = dto.score
                )
            } catch (e: Exception) {
                logger.logError("Failed to map audio book item: ${itemMap["name"]}", e)
                null
            }
        }
    }

    private fun mapAudioArticleItems(content: List<Map<String, Any>>): List<AudioArticleItem> {
        return content.mapNotNull { itemMap ->
            try {
                val jsonString = gson.toJson(itemMap)
                val dto = gson.fromJson(jsonString, AudioArticleDto::class.java)

                AudioArticleItem(
                    id = dto.articleId,
                    name = dto.name,
                    description = dto.description.toPlainText(),
                    avatarUrl = dto.avatarUrl,
                    durationSeconds = dto.duration,
                    authorName = dto.authorName,
                    releaseDateIso = dto.releaseDate,
                    score = dto.score
                )
            } catch (e: Exception) {
                logger.logError("Failed to map audio article item: ${itemMap["name"]}", e)
                null
            }
        }
    }

    /**
     * Extension function to strip HTML tags from description
     */
    private fun String.toPlainText(): String {
        return this.replace(Regex("<[^>]*>"), "")
            .replace("&nbsp;", " ")
            .replace("&amp;", "&")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&quot;", "\"")
            .trim()
    }
}
