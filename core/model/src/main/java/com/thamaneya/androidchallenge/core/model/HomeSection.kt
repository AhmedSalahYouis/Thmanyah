package com.thamaneya.androidchallenge.core.model

/**
 * Enum for section layout types
 */
enum class SectionLayout {
    SQUARE,
    TWO_LINES_GRID,
    BIG_SQUARE,
    QUEUE,
    UNKNOWN
}

/**
 * Enum for content types
 */
enum class ContentType {
    PODCAST,
    EPISODE,
    AUDIO_BOOK,
    AUDIO_ARTICLE,
    UNKNOWN
}

/**
 * Home section domain model
 */
data class HomeSection(
    val name: String,
    val order: Int,
    val layout: SectionLayout,
    val contentType: ContentType,
    val items: List<HomeItem>
)

/**
 * Home page domain model containing sections and pagination info
 */
data class HomePage(
    val sections: List<HomeSection>,
    val nextPagePath: String?, // e.g., "/home_sections?page=2"
    val totalPages: Int
)

