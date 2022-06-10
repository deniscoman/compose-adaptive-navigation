package com.deniscoman.adaptivenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.deniscoman.adaptivenavigation.ui.scaffold.FoldableScaffold
import com.deniscoman.adaptivenavigation.ui.scaffold.MobileScaffold
import com.deniscoman.adaptivenavigation.ui.scaffold.TabletScaffold
import com.deniscoman.adaptivenavigation.ui.screen.*
import com.deniscoman.adaptivenavigation.ui.theme.MultiplNavigationsTheme
import com.deniscoman.adaptivenavigation.utils.WindowSize
import com.deniscoman.adaptivenavigation.utils.rememberWindowSizeClass

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val windowSizeClass = rememberWindowSizeClass()
            MultiplNavigationsTheme {
                when (windowSizeClass) {
                    WindowSize.Compact -> {
                        MobileScaffold(navController) {
                            NavigationHost(navController, it)
                        }
                    }
                    WindowSize.Medium -> {
                        FoldableScaffold(navController = navController) {
                            NavigationHost(navController = navController, modifier = it)
                        }
                    }
                    WindowSize.Expanded -> {
                        TabletScaffold(navController = navController) {
                            NavigationHost(navController = navController, modifier = it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") { HomeScreen() }
        composable("movies") { MoviesScreen() }
        composable("music") { MusicScreen() }
        composable("books") { BooksScreen() }
        composable("profile") { ProfileScreen() }
        composable("settings") { SettingsScreen() }
        composable("search") { SearchScreen() }
    }
}

@Composable
fun AppBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val appbarItems = listOf(Screen.Search, Screen.Settings)

    SmallTopAppBar(
        title = { Text("Multiple Navigaitions") },
        modifier = Modifier.background(Color.Blue),
        actions = {
            appbarItems.forEach {
                IconButton(onClick = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }) {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = "Localized description"
                    )
                }
            }
        }
    )
}