package com.thamaneya.androidchallenge.navigation

/**
 * Sealed class representing all possible navigation destinations
 */
sealed class NavigationDestination(val route: String) {
    data object Home : NavigationDestination(AppRoutes.HomeRoute.route)
    data object Search : NavigationDestination(AppRoutes.SearchRoute.route)
    
    companion object {
        fun fromRoute(route: String): NavigationDestination {
            return when (route) {
                AppRoutes.HomeRoute.route -> Home
                AppRoutes.SearchRoute.route -> Search
                else -> Home // Default fallback
            }
        }
    }
}

/**
 * Navigation actions that can be performed
 */
sealed class NavigationAction {
    data object NavigateToSearch : NavigationAction()
    data object NavigateBack : NavigationAction()
    data object NavigateToHome : NavigationAction()
}

