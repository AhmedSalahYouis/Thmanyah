package com.thamaneya.androidchallenge.core.ui.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thamaneya.androidchallenge.core.model.AudioArticleItem
import com.thamaneya.androidchallenge.core.model.AudioBookItem
import com.thamaneya.androidchallenge.core.model.EpisodeItem
import com.thamaneya.androidchallenge.core.model.HomeItem
import com.thamaneya.androidchallenge.core.model.PodcastItem
import com.thamaneya.androidchallenge.core.model.SectionLayout

/**
 * Factory composable that creates the appropriate content item based on content type and layout
 */
@Composable
fun ContentItemFactory(
    item: HomeItem,
    layout: SectionLayout,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    when (layout) {
        SectionLayout.SQUARE -> {
            when (item) {
                is PodcastItem -> {
                    SquarePodcastItem(
                        podcast = item,
                        modifier = modifier
                            .width(200.dp)
                            .heightIn(max = 270.dp),
                        onClick = onClick
                    )
                }

                is EpisodeItem -> {
                    SquareEpisodeItem(
                        episode = item,
                        modifier = modifier
                            .width(200.dp)
                            .heightIn(max = 270.dp),
                        onClick = onClick
                    )
                }

                is AudioBookItem -> {
                    SquareAudioBookItem(
                        audioBook = item,
                        modifier = modifier
                            .width(200.dp)
                            .heightIn(max = 270.dp),
                        onClick = onClick
                    )
                }

                is AudioArticleItem -> {
                    SquareAudioArticleItem(
                        audioArticle = item,
                        modifier = modifier
                            .width(200.dp)
                            .heightIn(max = 270.dp),
                        onClick = onClick
                    )
                }
            }
        }

        SectionLayout.TWO_LINES_GRID -> {
            when (item) {
                is PodcastItem -> {
                    TwoLinesGridPodcastItem(
                        podcast = item,
                        modifier = modifier,
                        onClick = onClick
                    )
                }

                is EpisodeItem -> {
                    TwoLinesGridEpisodeItem(
                        episode = item,
                        modifier = modifier,
                        onClick = onClick
                    )
                }

                is AudioBookItem -> {
                    TwoLinesGridAudioBookItem(
                        audioBook = item,
                        modifier = modifier,
                        onClick = onClick
                    )
                }

                is AudioArticleItem -> {
                    TwoLinesGridAudioArticleItem(
                        audioArticle = item,
                        modifier = modifier,
                        onClick = onClick
                    )
                }

            }
        }

        SectionLayout.BIG_SQUARE -> {
            when (item) {
                is PodcastItem -> {
                    BigSquarePodcastItem(
                        podcast = item,
                        modifier = modifier,
                        onClick = onClick
                    )
                }

                is EpisodeItem -> {
                    BigSquareEpisodeItem(
                        episode = item,
                        modifier = modifier,
                        onClick = onClick
                    )
                }

                is AudioBookItem -> {
                    BigSquareAudioBookItem(
                        audioBook = item,
                        modifier = modifier,
                        onClick = onClick
                    )
                }

                is AudioArticleItem -> {
                    BigSquareAudioArticleItem(
                        audioArticle = item,
                        modifier = modifier,
                        onClick = onClick
                    )
                }
            }
        }

        SectionLayout.QUEUE -> {
            when (item) {
                is PodcastItem -> {
                    QueuePodcastItem(
                        podcast = item,
                        modifier = modifier,
                        onClick = onClick
                    )
                }

                is EpisodeItem -> {
                    QueueEpisodeItem(
                        episode = item,
                        modifier = modifier,
                        onClick = onClick
                    )
                }

                is AudioBookItem -> {
                    QueueAudioBookItem(
                        audioBook = item,
                        modifier = modifier,
                        onClick = onClick
                    )
                }

                is AudioArticleItem -> {
                    QueueAudioArticleItem(
                        audioArticle = item,
                        modifier = modifier,
                        onClick = onClick
                    )
                }
            }
        }

        else -> {}
    }
}
