package de.fjuretzko.comicviewer.data.remote.dto.character

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Result>,
    val total: Int
)