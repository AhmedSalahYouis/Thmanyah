package com.thamaneya.androidchallenge.navigation

import androidx.navigation.NavType

/**
 * Main navigation routes for the app
 */
sealed class AppRoutes(val route: String) {
    data object HomeBaseRoute : AppRoutes("home_base")
    data object HomeRoute : AppRoutes("home")
    data object SearchBaseRoute : AppRoutes("search_base")
    data object SearchRoute : AppRoutes("search")
}

/**
 * Navigation arguments
 */
object NavArgs {
    const val LAUNCH_ID = "launchId"
    const val KEYWORD = "keyword"
}

/**
 * Navigation argument types
 */
object NavTypes {
    val stringType = NavType.StringType
}

