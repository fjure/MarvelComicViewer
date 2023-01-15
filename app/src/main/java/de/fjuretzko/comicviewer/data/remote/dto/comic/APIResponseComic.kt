package de.fjuretzko.comicviewer.data.remote.dto.comic

data class APIResponseComic(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val `data`: Data,
    val etag: String,
    val status: String
)