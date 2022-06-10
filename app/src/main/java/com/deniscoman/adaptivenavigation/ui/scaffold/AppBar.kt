package com.deniscoman.adaptivenavigation.ui.scaffold

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.deniscoman.adaptivenavigation.ui.screen.Screen

@Composable
fun AppBar(
    items: List<Screen>,
    navController: NavHostController
) {
    SmallTopAppBar(
        title = { Text("Multiple Navigaitions") },
        modifier = Modifier.background(Color.Blue),
        actions = {
            items.forEach {
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