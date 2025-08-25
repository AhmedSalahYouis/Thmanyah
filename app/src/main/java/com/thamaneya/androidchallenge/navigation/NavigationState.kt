package com.thamaneya.androidchallenge.navigation

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * Navigation state class that manages navigation logic
 */
class NavigationState(
    val navController: NavHostController
) {
    /**
     * Navigate to search screen
     */
    fun navigateToSearch() {
        navController.navigateToRoute(AppRoutes.SearchBaseRoute)
    }

    /**
     * Navigate back
     */
    fun navigateBack() {
        navController.popBackStack()
    }

}

/**
 * Composable function to remember navigation state
 */
@Composable
fun rememberNavigationState(
    navController: NavHostController = rememberNavController()
): NavigationState {
    return remember(navController) {
        NavigationState(navController)
    }
}

