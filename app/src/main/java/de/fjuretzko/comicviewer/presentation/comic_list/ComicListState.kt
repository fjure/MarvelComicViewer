package de.fjuretzko.comicviewer.presentation.comic_list

import de.fjuretzko.comicviewer.domain.model.Character
import de.fjuretzko.comicviewer.domain.model.Comic

data class ComicListState(
    val isLoading: Boolean = false,
    val character: Character? = null,
    val error: String = "",
    val searchTerm: String = "",
    val comics: MutableList<Comic> = mutableListOf(),
    val pageNumber: Int = 0,
)
