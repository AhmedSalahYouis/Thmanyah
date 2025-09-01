package com.thamaneya.androidchallenge.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thamaneya.androidchallenge.core.design.components.DynamicAsyncImage
import com.thamaneya.androidchallenge.core.design.theme.ThmanyahTheme
import com.thamaneya.androidchallenge.core.model.PodcastItem
import com.thamaneya.androidchallenge.core.ui.R
import com.thamaneya.androidchallenge.core.ui.formatter.DurationFormatter

/**
 * Podcast content item with Queue layout
 */
@Composable
fun QueuePodcastItem(
    podcast: PodcastItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .width(400.dp)
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
                    imageUrl = podcast.avatarUrl,
                    contentDescription = podcast.name,
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
                        text = podcast.name,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row {
                        // Duration
                        podcast.durationSeconds?.let {
                            Text(
                                modifier = Modifier,
                                text = DurationFormatter.formatDuration(podcast.durationSeconds),
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

@Preview(name = "QueuePodcastItem Light", group = "Light", locale= "ar")
@Preview(name = "QueuePodcastItem Dark", group = "Dark", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun QueuePodcastItemPreview() {
    ThmanyahTheme {
        QueuePodcastItem(
            podcast = PodcastItem.Default
        )
    }
}

/**
 * Podcast content item with Square layout
 */
@Composable
fun SquarePodcastItem(
    podcast: PodcastItem,
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
                imageUrl = podcast.avatarUrl,
                contentDescription = "podcast image",
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
                    text = podcast.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.episodes_count, podcast.episodeCount),
                    style = MaterialTheme.typography.labelSmall,
                    color = White)
            }
        }
    }
}

@Preview(name = "SquarePodcastItem Light", group = "Light", locale= "ar")
@Preview(name = "SquarePodcastItem Dark", group = "Dark", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SquarePodcastItemPreview() {
    ThmanyahTheme {
        SquarePodcastItem(podcast = PodcastItem.Default)
    }
}

/**
 * Podcast content item with Big Square layout
 */
@Composable
fun BigSquarePodcastItem(
    podcast: PodcastItem,
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
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Image section - top of card, fully visible
            DynamicAsyncImage(
                imageUrl = podcast.avatarUrl,
                contentDescription = "podcast image",
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
            )
            // Overlay column for podcast name and episode count
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
                    text = podcast.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(R.string.episodes_count, podcast.episodeCount),
                    style = MaterialTheme.typography.labelSmall,
                    color = White)
            }
        }
    }
}

@Preview(name = "BigSquarePodcastItem Light", group = "Light")
@Preview(name = "BigSquarePodcastItem Dark", group = "Dark", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BigSquarePodcastItemPreview() {
    ThmanyahTheme {
        BigSquarePodcastItem(podcast = PodcastItem.Default)
    }
}

/**
 * Podcast content item with Two Lines Grid layout
 */
@Composable
fun TwoLinesGridPodcastItem(
    podcast: PodcastItem,
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
                imageUrl = podcast.avatarUrl,
                contentDescription = podcast.name,
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
                    text = podcast.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(4.dp))

                podcast.episodeCount.let { episodeCount ->
                    Text(
                        text = "$episodeCount ${stringResource(R.string.episodes)}",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Duration chip overlay
                    DurationChipOverlay(podcast.durationSeconds)
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

@Preview(name = "TwoLinesGridPodcastItem Light", group = "Light", locale= "ar")
@Preview(name = "TwoLinesGridPodcastItem Dark", group = "Dark", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TwoLinesGridPodcastItemPreview() {
    ThmanyahTheme {
        TwoLinesGridPodcastItem(
            podcast = PodcastItem.Default
        )
    }
}

