package de.fjuretzko.comicviewer.data.remote.dto.character

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)