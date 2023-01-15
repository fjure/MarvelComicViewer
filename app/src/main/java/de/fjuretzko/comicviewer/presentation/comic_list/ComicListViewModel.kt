package de.fjuretzko.comicviewer.presentation.comic_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fjuretzko.comicviewer.common.Resource
import de.fjuretzko.comicviewer.data.remote.dto.character.toCharacter
import de.fjuretzko.comicviewer.data.remote.dto.comic.toModel
import de.fjuretzko.comicviewer.domain.model.Comic
import de.fjuretzko.comicviewer.domain.use_case.get_character.GetCharacterUseCase
import de.fjuretzko.comicviewer.domain.use_case.get_comics.GetComicsByCharacterUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ComicListViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val getComicsByCharacterUseCase: GetComicsByCharacterUseCase
) : ViewModel() {

    var state by mutableStateOf(ComicListState())
        private set

    fun onSearchTermChange(value: String) {
        state = state.copy(searchTerm = value)
    }

    fun onSearchButtonClicked() {
        if (state.searchTerm.isNotEmpty()) {
            state = state.copy(
                comics = mutableListOf(),
                pageNumber = 0,
            )
            getCharacter()
        }
        else {
            state = state.copy(
                error = "You have to enter the character's name!"
            )
        }
    }

    fun refetch() {
        getCharacter(state.pageNumber)
    }

    private fun getCharacter(index: Int = 0) {
        getCharacterUseCase(state.searchTerm).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data?.data?.results?.isNotEmpty() == true) {
                        val character = result.data.data.results[0].toCharacter()
                        val characterId = character.id
                        getComicsById(characterId, index)
                        state = state.copy(
                            isLoading = false,
                            error = "",
                            character = character
                        )
                    } else {
                        state = state.copy(
                            isLoading = false,
                            error = "No characters found for your request. Please try another"
                        )
                    }

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

    private fun getComicsById(id: Int, index: Int = 0) {
        getComicsByCharacterUseCase.invoke(id, index * state.pageNumber).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val comics = result.data?.data?.results?.map { it.toModel() }?.toMutableList()
                    val updatedComics: MutableList<Comic> = state.comics.toMutableList()
                    if (comics != null) {
                        updatedComics.addAll(comics)
                        val comicsToCopy = if (state.comics.isEmpty()) comics else updatedComics
                        state = state.copy(
                            isLoading = false,
                            comics = comicsToCopy,
                            pageNumber = state.pageNumber + 1
                        )
                    }
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

}