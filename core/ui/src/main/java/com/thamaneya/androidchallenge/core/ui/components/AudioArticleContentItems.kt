package com.thamaneya.androidchallenge.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thamaneya.androidchallenge.core.model.AudioArticleItem

/**
 * Audio Article content item with Queue layout (wrapper for CoreCard)
 */
@Composable
fun QueueAudioArticleItem(
    audioArticle: AudioArticleItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    CoreCard(
        item = audioArticle,
        modifier = modifier,
        onClick = onClick
    )
}

/**
 * Audio Article content item with Square layout (wrapper for CoreCard)
 */
@Composable
fun SquareAudioArticleItem(
    audioArticle: AudioArticleItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    CoreCard(
        item = audioArticle,
        modifier = modifier,
        onClick = onClick
    )
}

/**
 * Audio Article content item with Big Square layout (wrapper for CoreCard)
 */
@Composable
fun BigSquareAudioArticleItem(
    audioArticle: AudioArticleItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    CoreCard(
        item = audioArticle,
        modifier = modifier,
        onClick = onClick
    )
}

/**
 * Audio Article content item with Two Lines Grid layout (wrapper for CoreCardTwoLines)
 */
@Composable
fun TwoLinesGridAudioArticleItem(
    audioArticle: AudioArticleItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    CoreCardTwoLines(
        item = audioArticle,
        modifier = modifier,
        onClick = onClick
    )
}
