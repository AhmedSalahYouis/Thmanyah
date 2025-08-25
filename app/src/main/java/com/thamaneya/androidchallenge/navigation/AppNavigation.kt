package com.thamaneya.androidchallenge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import com.thamaneya.androidchallenge.core.ui.app.ThmanyahAppState
import com.thamaneya.androidchallenge.feature.home.HomeRoute
import com.thamaneya.androidchallenge.feature.search.SearchRoute

/**
 * Main navigation host for the app
 */
@Composable
fun AppNavHost(
    appState: ThmanyahAppState,
    modifier: Modifier = Modifier,
) {
    val navigationState = rememberNavigationState(appState.navController)

    NavHost(
        navController = navigationState.navController,
        startDestination = AppRoutes.HomeBaseRoute.route,
        modifier = modifier,
    ) {
        homeScreenGraph(
            navigateToSearch = {
                navigationState.navigateToSearch()
            }
        )
        searchScreenGraph(
            navigateBack = {
                navigationState.navigateBack()
            }
        )
    }
}

/**
 * Home screen navigation graph
 */
fun NavGraphBuilder.homeScreenGraph(
    navigateToSearch: () -> Unit
) {
    navigationGraph(
        route = AppRoutes.HomeBaseRoute.route,
        startDestination = AppRoutes.HomeRoute.route
    ) {
        composableRoute(AppRoutes.HomeRoute) {
            HomeRoute(
                onSearchClick = navigateToSearch
            )
        }
    }
}

/**
 * Search screen navigation graph
 */
fun NavGraphBuilder.searchScreenGraph(
    navigateBack: () -> Unit
) {
    navigationGraph(
        route = AppRoutes.SearchBaseRoute.route,
        startDestination = AppRoutes.SearchRoute.route
    ) {
        composableRoute(AppRoutes.SearchRoute) {
            SearchRoute(
                onBackPressed = navigateBack
            )
        }
    }
}

