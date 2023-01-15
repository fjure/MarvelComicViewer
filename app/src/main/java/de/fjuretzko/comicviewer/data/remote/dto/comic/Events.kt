package de.fjuretzko.comicviewer.data.remote.dto.comic

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Int
)