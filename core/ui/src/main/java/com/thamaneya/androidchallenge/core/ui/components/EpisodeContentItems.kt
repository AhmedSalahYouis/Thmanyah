package com.thamaneya.androidchallenge.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thamaneya.androidchallenge.core.model.EpisodeItem

/**
 * Episode content item with Queue layout (wrapper for CoreCard)
 */
@Composable
fun QueueEpisodeItem(
    episode: EpisodeItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    CoreCard(
        item = episode,
        modifier = modifier,
        onClick = onClick
    )
}

/**
 * Episode content item with Square layout (wrapper for CoreCard)
 */
@Composable
fun SquareEpisodeItem(
    episode: EpisodeItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    CoreCard(
        item = episode,
        modifier = modifier,
        onClick = onClick
    )
}

/**
 * Episode content item with Big Square layout (wrapper for CoreCard)
 */
@Composable
fun BigSquareEpisodeItem(
    episode: EpisodeItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    CoreCard(
        item = episode,
        modifier = modifier,
        onClick = onClick
    )
}

/**
 * Episode content item with Two Lines Grid layout (wrapper for CoreCardTwoLines)
 */
@Composable
fun TwoLinesGridEpisodeItem(
    episode: EpisodeItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    CoreCardTwoLines(
        item = episode,
        modifier = modifier,
        onClick = onClick
    )
}
