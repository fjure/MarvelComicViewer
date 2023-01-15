package de.fjuretzko.comicviewer.presentation.my_comics

import de.fjuretzko.comicviewer.domain.model.Comic

data class MyComicsState(
    val comics: MutableList<Comic> = mutableListOf()
)