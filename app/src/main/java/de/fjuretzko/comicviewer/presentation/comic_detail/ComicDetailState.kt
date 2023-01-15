package de.fjuretzko.comicviewer.presentation.comic_detail

import de.fjuretzko.comicviewer.domain.model.Comic

data class ComicDetailState(
    val isLoading: Boolean = false,
    val comic: Comic? = null,
    val error: String = "",
    val snackbar: Boolean = false
)