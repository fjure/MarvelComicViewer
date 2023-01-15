package de.fjuretzko.comicviewer.presentation.my_comics

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fjuretzko.comicviewer.domain.model.Comic
import de.fjuretzko.comicviewer.domain.use_case.delete_comic_from_database.DeleteComicFromDatabaseUseCase
import de.fjuretzko.comicviewer.domain.use_case.get_comics_from_database.GetComicsFromDatabaseUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyComicsViewModel @Inject constructor(
    private val getComicsFromDatabaseUseCase: GetComicsFromDatabaseUseCase,
    private val deleteComicFromDatabaseUseCase: DeleteComicFromDatabaseUseCase
) : ViewModel() {

    var state by mutableStateOf(MyComicsState())
        private set

    init {
        getComicsFromDatabaseUseCase.invoke().onEach {
            Timber.i(it.size.toString())
            state = when(it.isNotEmpty()) {
                true -> state.copy(comics = it.toMutableList())
                false -> state.copy(comics = mutableListOf())
            }
        }.launchIn(viewModelScope)
    }

    fun deleteFromDatabase(id: Int) {
        deleteComicFromDatabaseUseCase.invoke(id).onEach { result ->
            var newComics = state.comics
            newComics = newComics.filter { comic -> comic.id != id } as MutableList<Comic>
            state = when(result) {
                true -> state.copy(
                    comics = newComics
                )
                false -> state
            }
        }.launchIn(viewModelScope)
    }

}