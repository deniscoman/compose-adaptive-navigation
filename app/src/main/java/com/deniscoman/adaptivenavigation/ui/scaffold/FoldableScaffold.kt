package com.deniscoman.adaptivenavigation.ui.scaffold

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.deniscoman.adaptivenavigation.ui.screen.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoldableScaffold(
    navController: NavHostController,
    content: @Composable (modifier: Modifier) -> Unit
) {
    Scaffold {
        Row(Modifier.padding(it)) {
            NavigationRail(navController)
            content(Modifier.padding(it))
        }
    }
}

@Composable
private fun NavigationRail(navController: NavHostController, modifier: Modifier = Modifier) {
    val menuItems =
        listOf(
            Screen.Home,
            Screen.Movies,
            Screen.Music,
            Screen.Books,
            Screen.Profile,
            Screen.Settings
        )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationRail(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.inversePrimary
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            menuItems.forEach { screen ->
                NavigationRailItem(
                    alwaysShowLabel = false,
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
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}