package com.thamaneya.androidchallenge.feature.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.thamaneya.androidchallenge.feature.home.R

/**
 * Category chips row for filtering content
 */
@Composable
fun CategoryChipsRow(
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        stringResource(R.string.home_section_podcasts),
        stringResource(R.string.home_section_audio_articles), 
        stringResource(R.string.home_section_audio_books),
        stringResource(R.string.home_section_episodes)
    )
    
    var selectedCategory by remember { mutableStateOf(0) }
    
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            AssistChip(
                onClick = { 
                    selectedCategory = categories.indexOf(category)
                },
                label = { Text(category) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (selectedCategory == categories.indexOf(category)) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }
                )
            )
        }
    }
}

