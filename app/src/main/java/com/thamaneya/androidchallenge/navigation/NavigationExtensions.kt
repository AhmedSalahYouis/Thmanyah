package com.thamaneya.androidchallenge.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

/**
 * Extension function to create a navigation graph
 */
fun NavGraphBuilder.navigationGraph(
    route: String,
    startDestination: String,
    content: NavGraphBuilder.() -> Unit
) {
    navigation(
        startDestination = startDestination,
        route = route
    ) {
        content()
    }
}

/**
 * Extension function to create a composable route
 */
fun NavGraphBuilder.composableRoute(
    route: AppRoutes,
    content: @androidx.compose.runtime.Composable () -> Unit
) {
    composable(route = route.route) {
        content()
    }
}

/**
 * Extension function to navigate to a route
 */
fun NavHostController.navigateToRoute(route: AppRoutes) {
    navigate(route.route)
}

/**
 * Extension function to navigate to a route and clear the back stack
 */
fun NavHostController.navigateToRouteAndClearStack(route: AppRoutes) {
    navigate(route.route) {
        popUpTo(0) { inclusive = true }
    }
}

