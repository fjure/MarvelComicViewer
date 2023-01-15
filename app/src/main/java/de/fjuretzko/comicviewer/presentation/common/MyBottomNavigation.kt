package de.fjuretzko.comicviewer.presentation.common

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.fjuretzko.comicviewer.presentation.Screen

@Composable
fun MyBottomNavigation(
    navController: NavController,
) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val items = listOf(Screen.ComicListScreen, Screen.MyComicsScreen)

        items.forEach { item ->
            BottomNavigationItem(
                icon = {Icon((item.icon), contentDescription = item.route)},
                selected = currentRoute == item.route,
                onClick = { navController.navigate(item.route) })
        }

    }

}