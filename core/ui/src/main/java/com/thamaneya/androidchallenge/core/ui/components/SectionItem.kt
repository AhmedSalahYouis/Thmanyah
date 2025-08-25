package com.thamaneya.androidchallenge.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thamaneya.androidchallenge.core.model.HomeItem
import com.thamaneya.androidchallenge.core.model.HomeSection
import com.thamaneya.androidchallenge.core.model.SectionLayout


/**
 * Section item composable
 */
@Composable
fun SectionItem(
    section: HomeSection,
    onItemClick: (HomeItem) -> Unit,
    onSectionVisible: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SectionHeader(
            title = section.name,
            showAction = true,
            onActionClick = { /* TODO: Navigate to section detail */ }
        )

        when (section.layout) {
            SectionLayout.SQUARE -> {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(1),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.heightIn(max = 200.dp)
                ) {
                    items(section.items) { item ->
                        CoreCard(
                            item = item,
                            onClick = { onItemClick(item) }
                        )
                    }
                }
            }

            SectionLayout.TWO_LINES_GRID -> {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(if (section.items.size == 1) 1 else 2),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.heightIn(min= 100.dp,max = 300.dp)
                ) {
                    items(section.items) { item ->
                        CoreCardTwoLines(
                            item = item,
                            onClick = { onItemClick(item) }
                        )
                    }
                }
            }

            SectionLayout.BIG_SQUARE -> {
                // Fallback to square layout
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(1),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.heightIn(max = 200.dp)
                ) {
                    items(section.items) { item ->
                        CoreCard(
                            item = item,
                            onClick = { onItemClick(item) }
                        )
                    }
                }
            }

            SectionLayout.QUEUE -> {
                // Fallback to square layout
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(1),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.heightIn(max = 200.dp)
                ) {
                    items(section.items) { item ->
                        CoreCard(
                            item = item,
                            onClick = { onItemClick(item) }
                        )
                    }
                }
            }

            SectionLayout.UNKNOWN -> {
                // Fallback to square layout
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(1),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.heightIn(max = 200.dp)
                ) {
                    items(section.items) { item ->
                        CoreCard(
                            item = item,
                            onClick = { onItemClick(item) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }

    LaunchedEffect(Unit) {
        onSectionVisible()
    }
}
