package de.fjuretzko.comicviewer.domain.model


data class APIResponse(
    val code: Int,
    val status: String,
    val data: APIData
)
