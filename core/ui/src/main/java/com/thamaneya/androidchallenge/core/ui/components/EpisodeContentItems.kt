package com.thamaneya.androidchallenge.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
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
import com.thamaneya.androidchallenge.core.model.EpisodeItem
import com.thamaneya.androidchallenge.core.ui.formatter.DurationFormatter

/**
 * Episode content item with Queue layout
 */
@Composable
fun QueueEpisodeItem(
    episode: EpisodeItem,
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
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Box {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                DynamicAsyncImage(
                    imageUrl = episode.avatarUrl,
                    contentDescription = episode.name,
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
                        text = episode.name,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row {
                        // Duration
                        episode.durationSeconds?.let {
                            Text(
                                modifier = Modifier,
                                text = DurationFormatter.formatDuration(episode.durationSeconds),
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

@Preview(name = "QueueEpisodeItem Light AR", locale = "ar")
@Composable
private fun QueueEpisodeItemLightArPreview() {
    ThmanyahTheme {
        QueueEpisodeItem(
            episode = EpisodeItem.Default
        )
    }
}
@Preview(name = "QueueEpisodeItem Light", locale = "en")
@Composable
private fun QueueEpisodeItemLightPreview() {
    ThmanyahTheme {
        QueueEpisodeItem(
            episode = EpisodeItem.Default
        )
    }
}

@Preview(name = "QueueEpisodeItem Dark AR", locale = "ar")
@Composable
private fun QueueEpisodeItemDarkArPreview() {
    ThmanyahTheme(darkTheme = true) {
        QueueEpisodeItem(
            episode = EpisodeItem.Default
        )
    }
}

@Preview(name = "QueueEpisodeItem Dark", locale = "en")
@Composable
private fun QueueEpisodeItemDarkPreview() {
    ThmanyahTheme(darkTheme = true) {
        QueueEpisodeItem(
            episode = EpisodeItem.Default
        )
    }
}

/**
 * Episode content item with Square layout
 */
@Composable
fun SquareEpisodeItem(
    episode: EpisodeItem,
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
                imageUrl = episode.avatarUrl,
                contentDescription = "episode image",
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
                    text = episode.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Play button with duration - at the bottom of content
                PlayAndDurationChip(episode)
            }
        }
    }
}

@Composable
private fun ColumnScope.PlayAndDurationChip(episode: EpisodeItem) {
    episode.durationSeconds?.let { duration ->
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

@Preview(name = "SquareEpisodeItem Light AR", locale = "ar")
@Composable
private fun SquareEpisodeItemLightArPreview() {
    ThmanyahTheme {
        SquareEpisodeItem(episode = EpisodeItem.Default)
    }
}

@Preview(name = "SquareEpisodeItem Light")
@Composable
private fun SquareEpisodeItemLightPreview() {
    ThmanyahTheme {
        SquareEpisodeItem(episode = EpisodeItem.Default)
    }
}

@Preview(name = "SquareEpisodeItem Dark AR", locale = "ar")
@Composable
private fun SquareEpisodeItemDarkArPreview() {
    ThmanyahTheme(darkTheme = true) {
        SquareEpisodeItem(
            episode = EpisodeItem.Default
        )
    }
}
@Preview(name = "SquareEpisodeItem Dark")
@Composable
private fun SquareEpisodeItemDarkPreview() {
    ThmanyahTheme(darkTheme = true) {
        SquareEpisodeItem(
            episode = EpisodeItem.Default
        )
    }
}

/**
 * Episode content item with Big Square layout
 */
@Composable
fun BigSquareEpisodeItem(
    episode: EpisodeItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick ?: {},
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0f)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Image section - top of card, fully visible
            DynamicAsyncImage(
                imageUrl = episode.avatarUrl,
                contentDescription = "episode image",
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp)),
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Gray.copy(alpha = 0.7f)),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = episode.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                PlayAndDurationChip(episode)
            }
        }
    }
}

@Preview(name = "BigSquareEpisodeItem Light AR", locale = "ar")
@Composable
private fun BigSquareEpisodeItemLightArPreview() {
    ThmanyahTheme {
        BigSquareEpisodeItem(episode = EpisodeItem.Default)
    }
}

@Preview(name = "BigSquareEpisodeItem Light", locale = "en")
@Composable
private fun BigSquareEpisodeItemLightPreview() {
    ThmanyahTheme {
        BigSquareEpisodeItem(episode = EpisodeItem.Default)
    }
}

@Preview(name = "BigSquareEpisodeItem Dark AR", locale = "ar")
@Composable
private fun BigSquareEpisodeItemDarkArPreview() {
    ThmanyahTheme(darkTheme = true) {
        BigSquareEpisodeItem(
            episode = EpisodeItem.Default
        )
    }
}
@Preview(name = "BigSquareEpisodeItem Dark", locale = "en")
@Composable
private fun BigSquareEpisodeItemDarkPreview() {
    ThmanyahTheme(darkTheme = true) {
        BigSquareEpisodeItem(
            episode = EpisodeItem.Default
        )
    }
}

/**
 * Episode content item with Two Lines Grid layout
 */
@Composable
fun TwoLinesGridEpisodeItem(
    episode: EpisodeItem,
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
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            DynamicAsyncImage(
                imageUrl = episode.avatarUrl,
                contentDescription = episode.name,
                modifier = Modifier
                    .align(CenterVertically)
                    .aspectRatio(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .weight(1f)
                    .padding(horizontal = 8.dp),
            ) {
                Text(
                    text = episode.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Duration chip overlay
                    DurationChipOverlay(episode.durationSeconds)
                    OptionsRow()
                }
            }
        }
    }
}
@Composable
private fun RowScope.OptionsRow() {
    Row(
        modifier = Modifier
            .align(Alignment.Bottom)
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "options",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .size(24.dp)
                .rotate(90f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.List,
            contentDescription = "playlist",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
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

@Preview(name = "TwoLinesGridEpisodeItem Light AR", locale = "ar")
@Composable
private fun TwoLinesGridEpisodeItemLightArPreview() {
    ThmanyahTheme {
        TwoLinesGridEpisodeItem(
            episode = EpisodeItem.Default
        )
    }
}
@Preview(name = "TwoLinesGridEpisodeItem Light", locale = "en")
@Composable
private fun TwoLinesGridEpisodeItemLightPreview() {
    ThmanyahTheme {
        TwoLinesGridEpisodeItem(
            episode = EpisodeItem.Default
        )
    }
}

@Preview(name = "TwoLinesGridEpisodeItem Dark AR", locale = "ar")
@Composable
private fun TwoLinesGridEpisodeItemDarkArPreview() {
    ThmanyahTheme(darkTheme = true) {
        TwoLinesGridEpisodeItem(
            episode = EpisodeItem.Default
        )
    }
}
@Preview(name = "TwoLinesGridEpisodeItem Dark", locale = "en")
@Composable
private fun TwoLinesGridEpisodeItemDarkPreview() {
    ThmanyahTheme(darkTheme = true) {
        TwoLinesGridEpisodeItem(
            episode = EpisodeItem.Default
        )
    }
}
