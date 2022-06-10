package com.deniscoman.adaptivenavigation.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object Books : Screen("books", "Books", Icons.Filled.Book)
    object Movies : Screen("movies", "Movies", Icons.Filled.Movie)
    object Music : Screen("music", "Music", Icons.Filled.LibraryMusic)
    object Search : Screen("search", "Search", Icons.Filled.Search)
    object Settings : Screen("settings", "Settings", Icons.Filled.Settings)
    object Profile : Screen("profile", "Profile", Icons.Filled.People)
}