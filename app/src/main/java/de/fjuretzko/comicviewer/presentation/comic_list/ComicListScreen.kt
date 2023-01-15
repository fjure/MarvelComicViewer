package de.fjuretzko.comicviewer.presentation.comic_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.fjuretzko.comicviewer.presentation.Screen
import de.fjuretzko.comicviewer.presentation.common.ComicListItem

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ComicListScreen(
    navController: NavController,
    viewModel: ComicListViewModel
) {
    val state = viewModel.state
    val listState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(4.dp)) {
        Text("Find comics for a character..", style = MaterialTheme.typography.h4)
        Column(modifier = Modifier.fillMaxSize()) {
            Row {
                OutlinedTextField(
                    value = state.searchTerm,
                    onValueChange = {
                        viewModel.onSearchTermChange(it)
                    },
                    label = { Text("Search for character..") },
                    modifier = Modifier
                        .weight(0.8f)
                        .height(60.dp)
                )
                IconButton(
                    onClick = {
                        keyboardController?.hide()
                        viewModel.onSearchButtonClicked()
                    },
                    modifier = Modifier.weight(0.2f)
                ) {
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = "Search",
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                }
            }

            if (state.isLoading) {
                Row(modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(top = 256.dp)) {
                    CircularProgressIndicator(modifier = Modifier.size(100.dp))
                }
            }

            if (state.error.isNotEmpty() && !state.isLoading) {
                Text(text = state.error)
            }

            if (state.comics.isNotEmpty()) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp)
                ) {
                    items(state.comics) { comic ->
                        Row(modifier = Modifier.padding(4.dp)) {
                            ComicListItem(
                                comicTitle = comic.title,
                                onItemClick = {
                                    navController.navigate(Screen.ComicDetailScreen.route + "/${comic.id}")
                                }
                            )

                        }

                    }
                }
                listState.OnBottomReached {
                    viewModel.refetch()
                }
            }
        }


    }
}

@Composable
fun LazyListState.OnBottomReached(
    loadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true

            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    // Convert the state into a cold flow and collect
    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect {
                // if should load more, then invoke loadMore
                if (it) loadMore()
            }
    }
}