package de.fjuretzko.comicviewer.domain.model

import arrow.core.Either

data class APIData(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Either<Character, Comic>>
)
