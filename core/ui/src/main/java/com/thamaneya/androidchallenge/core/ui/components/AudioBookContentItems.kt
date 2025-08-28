package com.thamaneya.androidchallenge.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thamaneya.androidchallenge.core.model.AudioBookItem

/**
 * Audio Book content item with Queue layout (wrapper for CoreCard)
 */
@Composable
fun QueueAudioBookItem(
    audioBook: AudioBookItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    CoreCard(
        item = audioBook,
        modifier = modifier,
        onClick = onClick
    )
}

/**
 * Audio Book content item with Square layout (wrapper for CoreCard)
 */
@Composable
fun SquareAudioBookItem(
    audioBook: AudioBookItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    CoreCard(
        item = audioBook,
        modifier = modifier,
        onClick = onClick
    )
}

/**
 * Audio Book content item with Big Square layout (wrapper for CoreCard)
 */
@Composable
fun BigSquareAudioBookItem(
    audioBook: AudioBookItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    CoreCard(
        item = audioBook,
        modifier = modifier,
        onClick = onClick
    )
}

/**
 * Audio Book content item with Two Lines Grid layout (wrapper for CoreCardTwoLines)
 */
@Composable
fun TwoLinesGridAudioBookItem(
    audioBook: AudioBookItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    CoreCardTwoLines(
        item = audioBook,
        modifier = modifier,
        onClick = onClick
    )
}
