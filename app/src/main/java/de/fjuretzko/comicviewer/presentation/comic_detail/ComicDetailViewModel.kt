package de.fjuretzko.comicviewer.presentation.comic_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fjuretzko.comicviewer.common.Constants
import de.fjuretzko.comicviewer.common.Resource
import de.fjuretzko.comicviewer.domain.model.Comic
import de.fjuretzko.comicviewer.domain.use_case.get_comic.GetComicUseCase
import de.fjuretzko.comicviewer.domain.use_case.save_comic_to_favorite.SaveComicToFavoriteUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class ComicDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getComicUseCase: GetComicUseCase,
    private val saveComicToFavoriteUseCase: SaveComicToFavoriteUseCase
) : ViewModel() {
    var state by mutableStateOf(ComicDetailState())
        private set

    init {
        savedStateHandle.get<String>(Constants.PARAM_COMIC_ID)?.let {
            getComic(it)
        }
    }

    private fun getComic(comicId: String) {
        getComicUseCase(comicId.toInt()).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        comic = result.data
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        error = result.message ?: "Fehler"
                    )
                }
                is Resource.Loading -> {
                    state = state.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun saveComic(comic: Comic) {
        saveComicToFavoriteUseCase.invoke(comic).onEach { result ->
            state = when(result) {
                true -> {
                    state
                }
                false -> {
                    state.copy(error = "Error occurred saving comic..")
                }
            }

        }.launchIn(viewModelScope)
    }

    fun getImageURL(): String {
        if(state.comic != null) {
            return state.comic!!.thumbnail!!.path + "/portrait_xlarge.jpg"
        }
        return ""
    }

}