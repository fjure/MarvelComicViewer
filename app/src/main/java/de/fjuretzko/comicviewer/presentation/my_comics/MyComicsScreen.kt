package de.fjuretzko.comicviewer.presentation.my_comics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.fjuretzko.comicviewer.presentation.Screen
import de.fjuretzko.comicviewer.presentation.common.ComicListItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MyComicsScreen(
    navController: NavController,
    viewModel: MyComicsViewModel = hiltViewModel()
) {
    
    val state = viewModel.state
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Scaffold(scaffoldState = scaffoldState) {
        Column(modifier = Modifier.padding(4.dp).padding(it)) {
            Text("Your saved comics", style = MaterialTheme.typography.h4)
            if(state.comics.isNotEmpty()) {
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)) {
                    items(state.comics) { comic ->
                        Row(modifier = Modifier.padding(4.dp)) {
                            ComicListItem(
                                comicTitle = comic.title,
                                onItemClick = {
                                    navController.navigate(Screen.ComicDetailScreen.route + "/${comic.id}")
                                },
                                actions = {
                                    Button(
                                        onClick = {
                                            viewModel.deleteFromDatabase(comic.id)
                                            coroutineScope.launch {
                                                scaffoldState.snackbarHostState.showSnackbar(
                                                    message = "Successfully deleted comic!",
                                                    duration = SnackbarDuration.Short
                                                )
                                            }
                                        },
                                    ) {
                                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                                    }
                                }
                            )
                        }

                    }
                }
            }
        }
    }

}