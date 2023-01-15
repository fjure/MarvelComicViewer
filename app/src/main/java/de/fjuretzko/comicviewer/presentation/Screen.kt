package de.fjuretzko.comicviewer.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector) {
    object ComicListScreen: Screen("comic_list_screen", Icons.Filled.Home)
    object ComicDetailScreen: Screen("comic_detail_screen",Icons.Default.Home)
    object MyComicsScreen: Screen("my_comics", Icons.Filled.AccountBox)
}
