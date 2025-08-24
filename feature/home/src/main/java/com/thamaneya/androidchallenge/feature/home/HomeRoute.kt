package com.thamaneya.androidchallenge.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import com.thamaneya.androidchallenge.core.model.ContentType
import com.thamaneya.androidchallenge.core.model.HomeItem
import com.thamaneya.androidchallenge.core.model.HomeSection
import com.thamaneya.androidchallenge.core.model.PodcastItem
import com.thamaneya.androidchallenge.core.model.SectionLayout
import com.thamaneya.androidchallenge.core.ui.CoreCard
import com.thamaneya.androidchallenge.core.ui.CoreCardTwoLines
import com.thamaneya.androidchallenge.core.ui.SectionHeader
import com.thamaneya.androidchallenge.feature.home.components.*

/**
 * Main route for the Home feature
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onItemClick: (HomeItem) -> Unit = {},
) {

    val pagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()

    LaunchedEffect(pagingItems.loadState.refresh) {
        if (pagingItems.loadState.refresh is LoadState.Error) {
            // Handle initial load error
        }
    }

    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = pullRefreshState,
        isRefreshing = pagingItems.loadState.refresh is LoadState.Loading,
        onRefresh = { pagingItems.refresh() },
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            // Category chips row (placeholder for future filtering)
            item {
                CategoryChipsRow()
            }

            // Sections
            items(
                count = pagingItems.itemCount,
                key = pagingItems.itemKey { it.name + it.order },
            ) { index ->
                pagingItems[index]?.let { homeSection ->
                    SectionItem(
                        section = homeSection,
                        onItemClick = onItemClick,
                        onSectionVisible = {
                            viewModel.onEvent(HomeEvent.OnSectionVisible(homeSection.order))
                        }
                    )
                }
            }

            // Loading state
            item {
                when (pagingItems.loadState.append) {
                    is LoadState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is LoadState.Error -> {
                        ErrorItem(
                            message = "حدث خطأ في تحميل المحتوى",
                            onRetry = {
                                viewModel.onEvent(HomeEvent.OnRetry)
                            }
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}

/**
 * Section item composable
 */
@Composable
private fun SectionItem(
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
                LazyHorizontalGrid (
                    rows = GridCells.Fixed(1),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.heightIn(max = 400.dp)
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
                    rows = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.heightIn(max = 300.dp)
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
                    modifier = Modifier.heightIn(max = 400.dp)
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
                    modifier = Modifier.heightIn(max = 400.dp)
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
                    rows = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.heightIn(max = 400.dp)
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

@Preview(showBackground = true)
@Composable
fun HomeRoutePreview() {
    HomeRoute()
}

@Preview(showBackground = true)
@Composable
fun SectionItemSquarePreview() {
    SectionItem(
        section = HomeSection(
            name = "Section Title",
            order = 1,
            layout = SectionLayout.SQUARE,
            contentType = ContentType.PODCAST,
            items = List(4) { PodcastItem.Default }
        ),
        onItemClick = {},
        onSectionVisible = {}
    )
}

@Preview(showBackground = true)
@Composable
fun SectionItemTwoLinesGridPreview() {
    SectionItem(
        section = HomeSection(
            name = "Section Title",
            order = 1,
            contentType = ContentType.PODCAST,
            layout = SectionLayout.TWO_LINES_GRID,
            items = List(4) { PodcastItem.Default }
        ),
        onItemClick = {},
        onSectionVisible = {}
    )
}

