package de.fjuretzko.comicviewer.domain.repository

import de.fjuretzko.comicviewer.data.remote.dto.comic.APIResponseComic
import de.fjuretzko.comicviewer.domain.model.Comic

interface ComicRepository {

    suspend fun getComicsByCharacterId(characterId: Int, index: Int): APIResponseComic
    suspend fun getComicById(comicId: Int): Comic
    suspend fun saveComicToStorage(comic: Comic)
    suspend fun getComicsFromDatabase(): List<Comic>
    suspend fun deleteComicFromDatabase(comicId: Int)
}