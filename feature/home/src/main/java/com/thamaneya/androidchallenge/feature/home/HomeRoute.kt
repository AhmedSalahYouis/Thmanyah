package com.thamaneya.androidchallenge.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.thamaneya.androidchallenge.core.design.components.EmptyView
import com.thamaneya.androidchallenge.core.design.components.ErrorView
import com.thamaneya.androidchallenge.core.design.components.PagingProgressView
import com.thamaneya.androidchallenge.core.design.components.ProgressView
import com.thamaneya.androidchallenge.core.ui.components.SectionItem
import com.thamaneya.androidchallenge.core.ui.extensions.toUiText
import com.thamaneya.androidchallenge.feature.home.components.CategoryChipsRow
import com.thamaneya.error.DataErrorException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

/**
 * Main route for the Home feature
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onItemClick: (HomeItem) -> Unit = {},
    onSearchClick: () -> Unit = {},
) {

    val pagingItems = viewModel.displayHomeSections.collectAsLazyPagingItems()
    val selectedContentType by viewModel.selectedContentType.collectAsStateWithLifecycle()
    val allContentTypes = remember { viewModel.getAvailableContentTypes() }

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
            selectedContentType = selectedContentType,
            contentTypes = allContentTypes,
            onItemClick = onItemClick,
            onSearchClick = onSearchClick,
            onCategorySelected = {
                viewModel.selectContentType(it)
            },
        )
    }
}

/**
 * Composable that displays the home content.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    pagingItems: LazyPagingItems<HomeSection>,
    selectedContentType: ContentType?,
    contentTypes: List<ContentType>,
    onItemClick: (HomeItem) -> Unit,
    onSearchClick: () -> Unit,
    onCategorySelected: (ContentType?) -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val greetingMessage = remember {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        if (hour in 0..11) {
            context.getString(R.string.home_morning_greeting)
        } else {
            context.getString(R.string.home_evening_greeting)
        }.plus("${context.getString(R.string.comma)} Salah")
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.salah_profile_pic),
                        contentDescription = "Profile-Picture",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = greetingMessage,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = stringResource(R.string.home_action_search)
                        )
                    }

                    IconButton(onClick = onSearchClick) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { scaffoldPaddings ->
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(modifier.padding(scaffoldPaddings)) {
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
                                CategoryChipsRow(
                                    categories = contentTypes,
                                    selectedCategory = selectedContentType,
                                    onCategorySelected = onCategorySelected,
                                )
                            }

                            // Sections
                            items(
                                count = pagingItems.itemCount,
                            ) { index ->
                                pagingItems[index]?.let { homeSection ->
                                    SectionItem(
                                        section = homeSection,
                                        onItemClick = onItemClick,
                                    )
                                }
                            }

                            // Loading state
                            item {
                                when (pagingItems.loadState.append) {
                                    is LoadState.Loading -> {
                                        PagingProgressView()
                                    }

                                    is LoadState.Error -> {
                                        ErrorView(
                                            text = stringResource(R.string.home_error_loading_content),
                                            retryMessage = stringResource(id = R.string.home_action_retry),
                                            onRetry = { pagingItems.retry() }
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
                modifier = Modifier,
                onSearchClick = {},
                selectedContentType = ContentType.PODCAST,
                contentTypes = listOf(),
                onCategorySelected = {},
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
                flowOf(
                    PagingData.from(
                        emptyList<HomeSection>(),
                        LoadStates(
                            refresh = LoadState.NotLoading(false),
                            prepend = LoadState.NotLoading(false),
                            append = LoadState.NotLoading(false)
                        )
                    )
                )
            }
            HomeContent(
                pagingItems = emptyPaging.collectAsLazyPagingItems(),
                onItemClick = { },
                modifier = Modifier,
                onSearchClick = {},
                selectedContentType = null,
                contentTypes = emptyList(),
                onCategorySelected = {}
            )
        }
    }
}


