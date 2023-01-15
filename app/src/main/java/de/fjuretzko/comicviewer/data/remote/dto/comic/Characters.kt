package de.fjuretzko.comicviewer.data.remote.dto.comic

data class Characters(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)