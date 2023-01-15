package de.fjuretzko.comicviewer.domain.model

import de.fjuretzko.comicviewer.data.remote.dto.character.Comics

data class Character(
    val id: Int,
    val name: String,
    val description: String?,
    val comicsList: Comics
)
