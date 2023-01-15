package de.fjuretzko.comicviewer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import de.fjuretzko.comicviewer.presentation.comic_detail.ComicDetailScreen
import de.fjuretzko.comicviewer.presentation.comic_list.ComicListScreen
import de.fjuretzko.comicviewer.presentation.comic_list.ComicListViewModel
import de.fjuretzko.comicviewer.presentation.common.MyBottomNavigation
import de.fjuretzko.comicviewer.presentation.my_comics.MyComicsScreen
import de.fjuretzko.comicviewer.presentation.ui.theme.ComicViewerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComicViewerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    val navController = rememberNavController()
                    val comicListViewModel: ComicListViewModel by viewModels()
                    Scaffold(
                        bottomBar = { MyBottomNavigation(navController = navController)}
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.ComicListScreen.route,
                            modifier = Modifier.padding(it)
                        ) {
                            composable(
                                route = Screen.ComicListScreen.route
                            ) {
                                ComicListScreen(navController = navController, viewModel = comicListViewModel)
                            }
                            composable(
                                route = Screen.ComicDetailScreen.route + "/{comicId}"
                            ) {
                                ComicDetailScreen(navController = navController)
                            }
                            composable(
                                route = Screen.MyComicsScreen.route
                            ) {
                                MyComicsScreen(navController = navController)
                            }

                        }
                    }


                }
            }
        }
    }
}