package com.deniscoman.adaptivenavigation.ui.scaffold

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.deniscoman.adaptivenavigation.ui.screen.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MobileScaffold(
    navController: NavHostController,
    content: @Composable (modifier: Modifier) -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationScreen(
                navController = navController
            )
        }
    ) {
        content(Modifier.padding(it))
    }
}

@Composable
private fun BottomNavigationScreen(navController: NavController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.inversePrimary
    ) {
        val menuItems =
            listOf(Screen.Home, Screen.Movies, Screen.Music, Screen.Books, Screen.Profile)
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        menuItems.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.title) }
            )
        }
    }
}