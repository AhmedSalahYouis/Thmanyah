package com.thamaneya.androidchallenge.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thamaneya.androidchallenge.core.design.components.DynamicAsyncImage
import com.thamaneya.androidchallenge.core.design.theme.ThmanyahTheme
import com.thamaneya.androidchallenge.core.model.AudioArticleItem
import com.thamaneya.androidchallenge.core.ui.formatter.DurationFormatter

/**
 * Audio Article content item with Queue layout
 */
@Composable
fun QueueAudioArticleItem(
    audioArticle: AudioArticleItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(192.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick ?: {},
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                audioArticle.avatarUrl?.let {
                    DynamicAsyncImage(
                        imageUrl = audioArticle.avatarUrl,
                        contentDescription = audioArticle.name,
                        modifier = Modifier
                            .align(CenterVertically)
                            .aspectRatio(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Column(
                    modifier = Modifier
                        .align(CenterVertically)
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                ) {
                    audioArticle.name?.let {
                        Text(
                            text = audioArticle.name,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleMedium,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row {
                        // Duration
                        audioArticle.durationSeconds?.let {
                            Text(
                                modifier = Modifier,
                                text = DurationFormatter.formatDuration(audioArticle.durationSeconds),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                        }
                    }
                }
            }

            Box (
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(32.dp),
            ) {
                Icon(
                    modifier = Modifier.background(color = White, shape = CircleShape).align(Alignment.BottomEnd),
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.Black
                )
            }
        }
    }
}

@Preview(name = "QueueAudioArticleItem")
@Composable
private fun QueueAudioArticleItemPreview() {
    ThmanyahTheme {
        QueueAudioArticleItem(
            audioArticle = AudioArticleItem.Default
        )
    }
}

/**
 * Audio Article content item with Square layout
 */
@Composable
fun SquareAudioArticleItem(
    audioArticle: AudioArticleItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .width(200.dp)
            .heightIn(max = 270.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick ?: {},
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0f)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Image section - top of card, fully visible
            DynamicAsyncImage(
                imageUrl = audioArticle.avatarUrl,
                contentDescription = "audio article image",
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
            )

            // Content section - below the image
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Takes remaining space
                    .padding(12.dp)
            ) {
                Text(
                    text = audioArticle.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Play button with duration - at the bottom of content
                audioArticle.durationSeconds?.let { duration ->
                    if (duration > 0) {
                        Surface(
                            modifier = Modifier
                                .align(Alignment.Start),
                            color = Color.DarkGray.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row(modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play",
                                    tint = White,
                                    modifier = Modifier.align(CenterVertically)
                                )
                                Text(
                                    modifier = Modifier.align(CenterVertically),
                                    text = DurationFormatter.formatDuration(duration),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = White,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "SquareAudioArticleItem")
@Composable
private fun SquareAudioArticleItemPreview() {
    ThmanyahTheme {
        SquareAudioArticleItem(audioArticle = AudioArticleItem.Default)
    }
}

/**
 * Audio Article content item with Big Square layout
 */
@Composable
fun BigSquareAudioArticleItem(
    audioArticle: AudioArticleItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .width(200.dp)
            .heightIn(max = 200.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick ?: {},
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0f)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Image section - top of card, fully visible
            DynamicAsyncImage(
                imageUrl = audioArticle.avatarUrl,
                contentDescription = "audio article image",
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
            )

            // Content section - below the image
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Takes remaining space
                    .padding(12.dp)
            ) {
                Text(
                    text = audioArticle.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Play button with duration - at the bottom of content
                audioArticle.durationSeconds?.let { duration ->
                    if (duration > 0) {
                        Surface(
                            modifier = Modifier
                                .align(Alignment.Start),
                            color = Color.DarkGray.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row(modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play",
                                    tint = White,
                                    modifier = Modifier.align(CenterVertically)
                                )
                                Text(
                                    modifier = Modifier.align(CenterVertically),
                                    text = DurationFormatter.formatDuration(duration),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = White,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "BigSquareAudioArticleItem")
@Composable
private fun BigSquareAudioArticleItemPreview() {
    ThmanyahTheme {
        BigSquareAudioArticleItem(audioArticle = AudioArticleItem.Default)
    }
}

/**
 * Audio Article content item with Two Lines Grid layout
 */
@Composable
fun TwoLinesGridAudioArticleItem(
    audioArticle: AudioArticleItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .width(400.dp)
            .height(96.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick ?: {},
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            DynamicAsyncImage(
                imageUrl = audioArticle.avatarUrl,
                contentDescription = audioArticle.name,
                modifier = Modifier
                    .align(CenterVertically)
                    .aspectRatio(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .align(CenterVertically)
                    .weight(1f)
                    .padding(horizontal = 8.dp),
            ) {
                Text(
                    text = audioArticle.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Duration chip overlay
                DurationChipOverlay(audioArticle.durationSeconds)
            }
        }
    }
}

@Composable
private fun DurationChipOverlay(durationSeconds: Int?) {
    durationSeconds?.let { duration ->
        if (duration > 0) {
            Surface(
                modifier = Modifier
                    .padding(0.dp),
                color = Color.DarkGray.copy(alpha = 0.7f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)) {
                    Text(
                        modifier = Modifier.align(CenterVertically),
                        text = DurationFormatter.formatDuration(duration),
                        style = MaterialTheme.typography.labelSmall,
                        color = White,
                    )
                }
            }
        }
    }
}

@Preview(name = "TwoLinesGridAudioArticleItem")
@Composable
private fun TwoLinesGridAudioArticleItemPreview() {
    ThmanyahTheme {
        TwoLinesGridAudioArticleItem(
            audioArticle = AudioArticleItem.Default
        )
    }
}
