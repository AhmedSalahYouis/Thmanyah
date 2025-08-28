package com.thamaneya.androidchallenge.feature.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.thamaneya.androidchallenge.core.model.ContentType
import com.thamaneya.androidchallenge.feature.home.R

/**
 * Category chips row for filtering content
 */
@Composable
fun CategoryChipsRow(
    modifier: Modifier = Modifier,
    categories: List<ContentType>,
    selectedCategory: ContentType?,
    onCategorySelected: (ContentType?) -> Unit
) {
    
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Add "For you" option first
        item {
            AssistChip(
                onClick = { onCategorySelected(null) },
                label = { Text(stringResource(R.string.home_section_for_you)) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (selectedCategory == null) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }
                )
            )
        }
        
        // Add content type categories
        items(categories) { category ->
            val contentTypeString = when (category) {
                ContentType.PODCAST -> stringResource(R.string.home_section_podcasts)
                ContentType.EPISODE -> stringResource(R.string.home_section_episodes)
                ContentType.AUDIO_BOOK -> stringResource(R.string.home_section_audio_books)
                ContentType.AUDIO_ARTICLE -> stringResource(R.string.home_section_audio_articles)
                ContentType.UNKNOWN -> stringResource(R.string.home_section_episodes)
            }
            AssistChip(
                onClick = {
                    // Convert display name back to content type string for filtering
                    onCategorySelected(category)
                },
                label = { Text(contentTypeString) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (selectedCategory == category) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }
                )
            )
        }
    }
}

