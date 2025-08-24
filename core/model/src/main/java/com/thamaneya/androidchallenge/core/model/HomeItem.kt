package com.thamaneya.androidchallenge.core.model

/**
 * Base interface for all home content items
 */
sealed interface HomeItem {
    val id: String
    val name: String
    val description: String
    val avatarUrl: String
    val durationSeconds: Int?
}

/**
 * Podcast item domain model
 */
data class PodcastItem(
    override val id: String, // podcast_id
    override val name: String,
    override val description: String,
    override val avatarUrl: String,
    override val durationSeconds: Int?,
    val episodeCount: Int,
    val language: String?,
    val priority: Int?,
    val popularityScore: Int?,
    val score: Double?
) : HomeItem {
    companion object {
        val Default = PodcastItem(
            id = "",
            name = "item name",
            description = "item description goes here",
            avatarUrl = "",
            durationSeconds = 4500,
            episodeCount = 8,
            language = "Arabic",
            priority = null,
            popularityScore = null,
            score = null
        )
    }
}

/**
 * Episode item domain model
 */
data class EpisodeItem(
    override val id: String, // episode_id
    override val name: String,
    override val description: String, // may contain HTML
    override val avatarUrl: String,
    override val durationSeconds: Int?,
    val podcastId: String,
    val podcastName: String,
    val audioUrl: String,
    val releaseDateIso: String,
    val episodeType: String,
    val score: Double?
) : HomeItem

/**
 * Audio book item domain model
 */
data class AudioBookItem(
    override val id: String, // audiobook_id
    override val name: String,
    override val description: String,
    override val avatarUrl: String,
    override val durationSeconds: Int?,
    val authorName: String,
    val language: String?,
    val releaseDateIso: String,
    val score: Double?
) : HomeItem

/**
 * Audio article item domain model
 */
data class AudioArticleItem(
    override val id: String, // article_id
    override val name: String,
    override val description: String,
    override val avatarUrl: String,
    override val durationSeconds: Int?,
    val authorName: String,
    val releaseDateIso: String,
    val score: Double?
) : HomeItem

