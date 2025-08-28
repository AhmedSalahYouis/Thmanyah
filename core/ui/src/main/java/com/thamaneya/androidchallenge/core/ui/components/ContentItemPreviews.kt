package com.thamaneya.androidchallenge.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thamaneya.androidchallenge.core.model.AudioArticleItem
import com.thamaneya.androidchallenge.core.model.AudioBookItem
import com.thamaneya.androidchallenge.core.model.EpisodeItem
import com.thamaneya.androidchallenge.core.model.PodcastItem
import com.thamaneya.androidchallenge.core.model.SectionLayout

/**
 * Preview for Podcast content items in all layouts
 */
@Preview(name = "Podcast Items - All Layouts", showBackground = true)
@Composable
fun PodcastItemsPreview() {
    MaterialTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Podcast Content Items",
                    style = MaterialTheme.typography.headlineSmall
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    QueuePodcastItem(
                        podcast = PodcastItem.Default,
                        onClick = {}
                    )
                    SquarePodcastItem(
                        podcast = PodcastItem.Default,
                        onClick = {}
                    )
                }
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    BigSquarePodcastItem(
                        podcast = PodcastItem.Default,
                        onClick = {}
                    )
                    TwoLinesGridPodcastItem(
                        podcast = PodcastItem.Default,
                        onClick = {}
                    )
                }
            }
        }
    }
}

/**
 * Preview for Episode content items in all layouts
 */
@Preview(name = "Episode Items - All Layouts", showBackground = true)
@Composable
fun EpisodeItemsPreview() {
    MaterialTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Episode Content Items",
                    style = MaterialTheme.typography.headlineSmall
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    QueueEpisodeItem(
                        episode = EpisodeItem(
                            id = "episode1",
                            name = "Sample Episode",
                            description = "A sample episode description",
                            avatarUrl = "",
                            durationSeconds = 1800,
                            podcastId = "podcast1",
                            podcastName = "Sample Podcast",
                            audioUrl = "",
                            releaseDateIso = "2024-01-01",
                            episodeType = "regular",
                            score = 4.5
                        ),
                        onClick = {}
                    )
                    SquareEpisodeItem(
                        episode = EpisodeItem(
                            id = "episode2",
                            name = "Another Episode",
                            description = "Another sample episode description",
                            avatarUrl = "",
                            durationSeconds = 2400,
                            podcastId = "podcast1",
                            podcastName = "Sample Podcast",
                            audioUrl = "",
                            releaseDateIso = "2024-01-02",
                            episodeType = "regular",
                            score = 4.8
                        ),
                        onClick = {}
                    )
                }
            }
        }
    }
}

/**
 * Preview for Audio Book content items in all layouts
 */
@Preview(name = "Audio Book Items - All Layouts", showBackground = true)
@Composable
fun AudioBookItemsPreview() {
    MaterialTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Audio Book Content Items",
                    style = MaterialTheme.typography.headlineSmall
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    QueueAudioBookItem(
                        audioBook = AudioBookItem(
                            id = "audiobook1",
                            name = "Sample Audio Book",
                            description = "A sample audio book description",
                            avatarUrl = "",
                            durationSeconds = 7200,
                            authorName = "Sample Author",
                            language = "English",
                            releaseDateIso = "2024-01-01",
                            score = 4.6
                        ),
                        onClick = {}
                    )
                    SquareAudioBookItem(
                        audioBook = AudioBookItem(
                            id = "audiobook2",
                            name = "Another Audio Book",
                            description = "Another sample audio book description",
                            avatarUrl = "",
                            durationSeconds = 5400,
                            authorName = "Another Author",
                            language = "English",
                            releaseDateIso = "2024-01-02",
                            score = 4.7
                        ),
                        onClick = {}
                    )
                }
            }
        }
    }
}

/**
 * Preview for Audio Article content items in all layouts
 */
@Preview(name = "Audio Article Items - All Layouts", showBackground = true)
@Composable
fun AudioArticleItemsPreview() {
    MaterialTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Audio Article Content Items",
                    style = MaterialTheme.typography.headlineSmall
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    QueueAudioArticleItem(
                        audioArticle = AudioArticleItem(
                            id = "article1",
                            name = "Sample Article",
                            description = "A sample audio article description",
                            avatarUrl = "",
                            durationSeconds = 900,
                            authorName = "Sample Author",
                            releaseDateIso = "2024-01-01",
                            score = 4.4
                        ),
                        onClick = {}
                    )
                    SquareAudioArticleItem(
                        audioArticle = AudioArticleItem(
                            id = "article2",
                            name = "Another Article",
                            description = "Another sample audio article description",
                            avatarUrl = "",
                            durationSeconds = 1200,
                            authorName = "Another Author",
                            releaseDateIso = "2024-01-02",
                            score = 4.5
                        ),
                        onClick = {}
                    )
                }
            }
        }
    }
}

/**
 * Preview for ContentItemFactory with different layouts
 */
@Preview(name = "Content Item Factory - All Types", showBackground = true)
@Composable
fun ContentItemFactoryPreview() {
    MaterialTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Content Item Factory",
                    style = MaterialTheme.typography.headlineSmall
                )
                
                Text(
                    text = "Using ContentItemFactory for automatic layout selection",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ContentItemFactory(
                        item = PodcastItem.Default,
                        layout = SectionLayout.SQUARE,
                        onClick = {}
                    )
                    ContentItemFactory(
                        item = PodcastItem.Default,
                        layout = SectionLayout.TWO_LINES_GRID,
                        onClick = {}
                    )
                }
            }
        }
    }
}
