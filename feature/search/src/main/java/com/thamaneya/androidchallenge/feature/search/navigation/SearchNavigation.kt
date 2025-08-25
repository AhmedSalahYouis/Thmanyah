package com.thamaneya.androidchallenge.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.thamaneya.androidchallenge.feature.search.SearchRoute
import kotlinx.serialization.Serializable

@Serializable
data object SearchRoute // route to Home screen

@Serializable
data object SearchBaseRoute // route to base navigation graph

fun NavGraphBuilder.searchScreenGraph(
    onBackPressed: () -> Unit,
) {
    navigation<SearchBaseRoute>(startDestination = SearchRoute) {
        composable<SearchRoute> {
            SearchRoute(
                onBackPressed = onBackPressed
            )
        }
    }
}
