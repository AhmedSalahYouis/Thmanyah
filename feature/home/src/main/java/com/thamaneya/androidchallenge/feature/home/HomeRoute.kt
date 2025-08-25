package com.thamaneya.androidchallenge.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.thamaneya.androidchallenge.core.model.ContentType
import com.thamaneya.androidchallenge.core.model.HomeItem
import com.thamaneya.androidchallenge.core.model.HomeSection
import com.thamaneya.androidchallenge.core.model.PodcastItem
import com.thamaneya.androidchallenge.core.model.SectionLayout
import com.thamaneya.androidchallenge.core.ui.components.CoreCard
import com.thamaneya.androidchallenge.core.ui.components.CoreCardTwoLines
import com.thamaneya.androidchallenge.core.ui.components.EmptyView
import com.thamaneya.androidchallenge.core.ui.components.ProgressView
import com.thamaneya.androidchallenge.core.ui.components.SectionHeader
import com.thamaneya.androidchallenge.core.ui.extensions.toUiText
import com.thamaneya.androidchallenge.feature.home.components.CategoryChipsRow
import com.thamaneya.androidchallenge.core.ui.components.ErrorView
import com.thamaneya.error.DataErrorException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel

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

    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = pullRefreshState,
        isRefreshing = pagingItems.loadState.refresh is LoadState.Loading,
        onRefresh = { pagingItems.refresh() },
        modifier = modifier
            .fillMaxSize()
    ) {
        HomeContent(
            pagingItems = pagingItems,
            onItemClick = onItemClick,
            onSectionVisible = { homeSection ->
                viewModel.onEvent(HomeEvent.OnSectionVisible(homeSection.order))
            },
            onRetry = {
                viewModel.onEvent(HomeEvent.OnRetry)
            }
        )
    }
}

/**
 * Composable that displays the home content.
 */
@Composable
private fun HomeContent(
    pagingItems: LazyPagingItems<HomeSection>,
    onItemClick: (HomeItem) -> Unit,
    onSectionVisible: (HomeSection) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { scaffoldPaddings ->

        when {
            pagingItems.loadState.refresh is LoadState.Loading -> {
                ProgressView()
                //@TODO replace it with shimmer views
            }

            pagingItems.loadState.refresh is LoadState.Error && pagingItems.itemCount == 0 -> {
                val error = pagingItems.loadState.refresh as LoadState.Error
                val errorMessage =
                    (error.error as? DataErrorException)?.error?.toUiText()?.asString()
                        ?: stringResource(R.string.home_error_unknown)

                ErrorView(
                    modifier = Modifier.padding(scaffoldPaddings),
                    text = errorMessage,
                    retryMessage = stringResource(id = R.string.home_action_retry),
                    onRetry = { pagingItems.retry() }
                )
            }

            pagingItems.itemCount == 0 -> {
                EmptyView(
                    modifier = Modifier.padding(scaffoldPaddings),
                    text = stringResource(id = R.string.empty_sections_message),
                )
            }

            else -> {

                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    // Category chips row (placeholder for future filtering)
                    item {
                        CategoryChipsRow()
                    }

                    // Sections
                    items(
                        count = pagingItems.itemCount,
                    ) { index ->
                        pagingItems[index]?.let { homeSection ->
                            SectionItem(
                                section = homeSection,
                                onItemClick = onItemClick,
                                onSectionVisible = {
                                    onSectionVisible(homeSection)
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
                                ErrorView(
                                    text = stringResource(R.string.home_error_loading_content),
                                    retryMessage = stringResource(id = R.string.home_action_retry),
                                    onRetry = onRetry
                                )
                            }

                            else -> {}
                        }
                    }
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
            items = List(1) { PodcastItem.Default }
        ),
        onItemClick = {},
        onSectionVisible = {}
    )
}

private fun sampleHomeSection(i: Int): HomeSection =
    HomeSection(
        name = "Section $i",
        // If your section carries its items, include them here:
        items = List(4) { PodcastItem.Default },
        order = 1,
        contentType = ContentType.PODCAST,
        // Add any other required fields:
        layout = SectionLayout.TWO_LINES_GRID // e.g., "square" or "2_lines_grid" if you use display types
    )

private fun previewPagingItemsLoading(): Flow<PagingData<HomeSection>> {
    val sections = List(5) { i -> sampleHomeSection(i) }
    return flowOf(
        PagingData.from(
            sections, LoadStates(
                refresh = LoadState.Loading,
                prepend = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false)
            )
        )
    )
}
// ================================================================

@Preview(showBackground = true)
@Composable
private fun HomeContentPreview_Default() {
    MaterialTheme {
        Surface {
            val pagingItems = remember { previewPagingItemsLoading() }
            HomeContent(
                pagingItems = pagingItems.collectAsLazyPagingItems(),
                onItemClick = { /* no-op for preview */ },
                onSectionVisible = { /* no-op for preview */ },
                onRetry = { /* no-op for preview */ },
                modifier = Modifier
            )
        }
    }
}

/**
 * Optional: an “empty state” preview—handy to verify your empty UI.
 */
@Preview(name = "HomeContent • Empty", showBackground = true)
@Composable
private fun HomeContentPreview_Empty() {
    MaterialTheme {
        Surface {
            val emptyPaging = remember {
                flowOf(PagingData.from(emptyList<HomeSection>(),
                    LoadStates(
                        refresh = LoadState.NotLoading(false),
                        prepend = LoadState.NotLoading(false),
                        append = LoadState.NotLoading(false)
                    )))
            }
            HomeContent(
                pagingItems = emptyPaging.collectAsLazyPagingItems(),
                onItemClick = { },
                onSectionVisible = { },
                onRetry = { },
                modifier = Modifier
            )
        }
    }
}


