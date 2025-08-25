package com.thamaneya.androidchallenge.core.network

import com.thamaneya.androidchallenge.core.model.ContentType
import com.thamaneya.androidchallenge.core.model.SectionLayout
import com.google.gson.annotations.SerializedName

/**
 * Top level response DTO
 */
data class HomeResponseDto(
    val sections: List<SectionDto?>,
    val pagination: PaginationDto
)

/**
 * Pagination DTO
 */
data class PaginationDto(
    @SerializedName("next_page")
    val nextPage: String?,
    @SerializedName("total_pages")
    val totalPages: Int
)

/**
 * Section DTO
 */
data class SectionDto(
    val name: String,
    val type: String,
    @SerializedName("content_type")
    val contentType: String,
    val order: Any,
    val content: List<Map<String, Any>>
)

/**
 * Extension function to normalize section layout
 */
fun SectionDto.normalizedLayout(): SectionLayout {
    return when (type.lowercase().trim()) {
        "square" -> SectionLayout.SQUARE
        "2_lines_grid" -> SectionLayout.TWO_LINES_GRID
        "big_square", "big square" -> SectionLayout.BIG_SQUARE
        "queue" -> SectionLayout.QUEUE
        else -> SectionLayout.UNKNOWN
    }
}

/**
 * Extension function to normalize content type
 */
fun SectionDto.normalizedContentType(): ContentType {
    return when (contentType.lowercase().trim()) {
        "podcast" -> ContentType.PODCAST
        "episode" -> ContentType.EPISODE
        "audio_book" -> ContentType.AUDIO_BOOK
        "audio_article" -> ContentType.AUDIO_ARTICLE
        else -> ContentType.UNKNOWN
    }
}

/**
 * Podcast item DTO
 */
data class PodcastDto(
    @SerializedName("podcast_id")
    val podcastId: String,
    val name: String,
    val description: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("episode_count")
    val episodeCount: Any,
    val duration: Int?,
    val language: String?,
    val priority: Any?,
    @SerializedName("popularityScore")
    val popularityScore: Any?,
    val score: Any?
)

/**
 * Episode item DTO
 */
data class EpisodeDto(
    @SerializedName("episode_id")
    val episodeId: String,
    val name: String,
    @SerializedName("episode_type")
    val episodeType: String,
    @SerializedName("season_number")
    val seasonNumber: Int?,
    val number: Int?,
    @SerializedName("podcast_id")
    val podcastId: String,
    @SerializedName("podcast_name")
    val podcastName: String,
    @SerializedName("author_name")
    val authorName: String,
    val description: String,
    val duration: Int?,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("audio_url")
    val audioUrl: String,
    @SerializedName("separated_audio_url")
    val separatedAudioUrl: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    val chapters: List<String>?,
    @SerializedName("podcastPopularityScore")
    val podcastPopularityScore: Int?,
    @SerializedName("podcastPriority")
    val podcastPriority: Int?,
    val score: Double?
)

/**
 * Audio book item DTO
 */
data class AudioBookDto(
    @SerializedName("audiobook_id")
    val audiobookId: String,
    val name: String,
    @SerializedName("author_name")
    val authorName: String,
    val description: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val duration: Int?,
    val language: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    val score: Double?
)

/**
 * Audio article item DTO
 */
data class AudioArticleDto(
    @SerializedName("article_id")
    val articleId: String,
    val name: String,
    @SerializedName("author_name")
    val authorName: String,
    val description: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val duration: Int?,
    @SerializedName("release_date")
    val releaseDate: String,
    val score: Double?
)




