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
fun TabletScaffold(
    navController: NavHostController,
    content: @Composable (modifier: Modifier) -> Unit
) {
    Scaffold {
        Row(Modifier.padding(it)) {
            CustomNavigationRail(navController)
            content(Modifier.padding(it))
        }
    }
}

@Composable
private fun CustomNavigationRail(navController: NavHostController, modifier: Modifier = Modifier) {
    val menuItems =
        listOf(
            Screen.Home,
            Screen.Movies,
            Screen.Music,
            Screen.Books,
            Screen.Profile,
            Screen.Settings
        )


    NavigationRail(
        modifier = modifier.width(180.dp),
        containerColor = MaterialTheme.colorScheme.inversePrimary
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            menuItems.forEach { screen ->
                CustomNavigationRailItem(screen, navController)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun CustomNavigationRailItem(
    item: Screen,
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = if (currentDestination?.hierarchy?.any { it.route == item.route } == true) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.inversePrimary,
            contentColor = if (currentDestination?.hierarchy?.any { it.route == item.route } == true) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onBackground
        ),
        onClick = {
            navController.navigate(item.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }) {
        Icon(item.icon, contentDescription = null)
        Spacer(Modifier.width(16.dp))
        Text(item.title)
    }
}