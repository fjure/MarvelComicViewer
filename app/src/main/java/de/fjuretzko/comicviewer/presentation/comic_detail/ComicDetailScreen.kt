package de.fjuretzko.comicviewer.presentation.comic_detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ComicDetailScreen(
    viewModel: ComicDetailViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state = viewModel.state
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    if(state.comic != null) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = {Text(state.comic.title)},
                    backgroundColor = MaterialTheme.colors.primary,
                    navigationIcon = {
                        Button(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "back")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        viewModel.saveComic(state.comic)
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Successfully saved comic!",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                ) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Save")
                }
            }
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(4.dp)
                .border(BorderStroke(2.dp, Color.Gray), shape = RoundedCornerShape(4.dp)),

            ) {
                Column(modifier = Modifier.padding(4.dp)) {
                    Text(text = "Description", style = MaterialTheme.typography.h6)
                    Row(modifier = Modifier.fillMaxWidth()) {
                        state.comic.description?.let { it1 -> Text(it1, modifier = Modifier.weight(1f)) }
                        AsyncImage(
                            model = viewModel.getImageURL(),
                            contentDescription = state.comic.title,
                            modifier = Modifier
                                .size(150.dp, 225.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                    Text(text = "Number of pages:", style = MaterialTheme.typography.h6)
                    Text(text = state.comic.comicSize.toString())
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Included characters:", style = MaterialTheme.typography.h6)
                    LazyColumn {
                        items(state.comic.characters!!.items) { item ->
                            Text(text = item.name)
                        }
                    }
                }

            }

        }
    }
    else {
        CircularProgressIndicator()
    }



}