package com.thamaneya.androidchallenge.core.data

import com.google.gson.Gson
import com.thamaneya.androidchallenge.core.model.AudioArticleItem
import com.thamaneya.androidchallenge.core.model.AudioBookItem
import com.thamaneya.androidchallenge.core.model.ContentType
import com.thamaneya.androidchallenge.core.model.EpisodeItem
import com.thamaneya.androidchallenge.core.model.HomeSection
import com.thamaneya.androidchallenge.core.model.PodcastItem
import com.thamaneya.androidchallenge.core.network.AudioArticleDto
import com.thamaneya.androidchallenge.core.network.AudioBookDto
import com.thamaneya.androidchallenge.core.network.EpisodeDto
import com.thamaneya.androidchallenge.core.network.PodcastDto
import com.thamaneya.androidchallenge.core.network.SectionDto
import com.thamaneya.androidchallenge.core.network.normalizedContentType
import com.thamaneya.androidchallenge.core.network.normalizedLayout

/**
 * Mapper to convert network DTOs to domain models
 */
class HomeSectionMapper {

    private val gson = Gson()

    /**
     * Map SectionDto to HomeSection domain model
     */
    fun mapToHomeSection(sectionDto: SectionDto): HomeSection {
        val layout = sectionDto.normalizedLayout()
        val contentType = sectionDto.normalizedContentType()
        
        val items = when (contentType) {
            ContentType.PODCAST -> mapPodcastItems(sectionDto.content)
            ContentType.EPISODE -> mapEpisodeItems(sectionDto.content)
            ContentType.AUDIO_BOOK -> mapAudioBookItems(sectionDto.content)
            ContentType.AUDIO_ARTICLE -> mapAudioArticleItems(sectionDto.content)
            ContentType.UNKNOWN -> emptyList()
        }

        return HomeSection(
            name = sectionDto.name,
            order = sectionDto.order,
            layout = layout,
            contentType = contentType,
            items = items.distinctBy { it.id } // Deduplicate by ID
        )
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
                    episodeCount = dto.episodeCount,
                    language = dto.language,
                    priority = dto.priority,
                    popularityScore = dto.popularityScore,
                    score = dto.score
                )
            } catch (e: Exception) {
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
