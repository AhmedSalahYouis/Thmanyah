package com.thamaneya.androidchallenge.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.thamaneya.androidchallenge.core.ui.components.EmptyView
import com.thamaneya.androidchallenge.core.ui.components.ErrorView
import com.thamaneya.androidchallenge.core.ui.components.SectionItem
import com.thamaneya.androidchallenge.core.ui.extensions.toUiText
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRoute(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel()
) {
    val ui by viewModel.ui.collectAsState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.search_back_button)
                        )
                    }
                },
                title = { Text(text = stringResource(R.string.search_title), style = MaterialTheme.typography.titleLarge) }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search TextField
            OutlinedTextField(
                value = ui.query,
                onValueChange = { viewModel.onEvent(SearchEvent.OnQueryChanged(it)) },
                placeholder = { Text(text = stringResource(R.string.search_placeholder), style = MaterialTheme.typography.bodyLarge) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = stringResource(R.string.search_icon_description)
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = { focusManager.clearFocus() }
                )
            )

            // Content based on state
            when {
                ui.isError -> {
                    ErrorView(
                        text = ui.error?.toUiText()?.asString() ?: stringResource(R.string.search_error_unknown),
                        retryMessage = stringResource(R.string.search_action_retry),
                        onRetry = { viewModel.onEvent(SearchEvent.OnRetry) }
                    )
                }

                ui.isLoading && ui.query.isNotEmpty() -> {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .height(20.dp)
                            .fillMaxWidth()
                    )
                }

                ui.isQueryEmpty -> {
                    EmptyView(
                        text = stringResource(R.string.search_empty_query_guide),
                    )
                }

                ui.isEmptyResults -> {
                    EmptyView(text = stringResource(R.string.search_empty_results))
                }

                else -> {
                    // Render sections exactly like Home
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(
                            items = ui.sections,
                            key = { it.name + it.order }
                        ) { section ->
                            SectionItem(
                                section = section,
                                onItemClick = { item ->
                                    viewModel.onEvent(SearchEvent.OnItemClick(item))
                                },
                                onSectionVisible = {}
                            )
                        }
                    }
                }
            }
        }
    }
}

