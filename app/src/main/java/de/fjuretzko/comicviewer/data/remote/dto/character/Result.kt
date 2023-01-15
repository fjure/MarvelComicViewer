package de.fjuretzko.comicviewer.data.remote.dto.character

import de.fjuretzko.comicviewer.domain.model.Character

data class Result(
    val comics: Comics,
    val description: String?,
    val events: Events,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val urls: List<Url>
)

fun Result.toCharacter(): Character {
    return Character(
        id = id,
        name = name,
        description = description,
        comicsList = comics
    )
}