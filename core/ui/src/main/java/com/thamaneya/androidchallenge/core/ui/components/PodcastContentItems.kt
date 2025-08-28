package com.thamaneya.androidchallenge.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thamaneya.androidchallenge.core.model.PodcastItem

/**
 * Podcast content item with Queue layout (wrapper for CoreCard)
 */
@Composable
fun QueuePodcastItem(
    podcast: PodcastItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    CoreCard(
        item = podcast,
        modifier = modifier,
        onClick = onClick
    )
}

/**
 * Podcast content item with Square layout (wrapper for CoreCard)
 */
@Composable
fun SquarePodcastItem(
    podcast: PodcastItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    CoreCard(
        item = podcast,
        modifier = modifier,
        onClick = onClick
    )
}

/**
 * Podcast content item with Big Square layout (wrapper for CoreCard)
 */
@Composable
fun BigSquarePodcastItem(
    podcast: PodcastItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    CoreCard(
        item = podcast,
        modifier = modifier,
        onClick = onClick
    )
}

/**
 * Podcast content item with Two Lines Grid layout (wrapper for CoreCardTwoLines)
 */
@Composable
fun TwoLinesGridPodcastItem(
    podcast: PodcastItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    CoreCardTwoLines(
        item = podcast,
        modifier = modifier,
        onClick = onClick
    )
}
