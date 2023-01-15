package de.fjuretzko.comicviewer.data.remote.dto.character

data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)