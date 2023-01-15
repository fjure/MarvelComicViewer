package de.fjuretzko.comicviewer.data.remote.dto.comic

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXX>,
    val returned: Int
)