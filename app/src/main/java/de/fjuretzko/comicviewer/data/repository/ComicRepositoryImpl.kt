package de.fjuretzko.comicviewer.data.repository

import de.fjuretzko.comicviewer.data.remote.MarvelApi
import de.fjuretzko.comicviewer.data.remote.dto.comic.APIResponseComic
import de.fjuretzko.comicviewer.data.remote.dto.comic.toModel
import de.fjuretzko.comicviewer.data.room.ComicDao
import de.fjuretzko.comicviewer.domain.model.Comic
import de.fjuretzko.comicviewer.domain.repository.ComicRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ComicRepositoryImpl @Inject constructor(
    private val api: MarvelApi,
    private val comicDao: ComicDao
) : ComicRepository {

    override suspend fun getComicsByCharacterId(characterId: Int, index: Int): APIResponseComic {
        return api.getComicsByCharacterId(characterId, index)
    }

    override suspend fun getComicById(comicId: Int): Comic {
        return api.getComicById(comicId).data.results[0].toModel()
    }

    override suspend fun saveComicToStorage(comic: Comic) {
        return comicDao.insertComic(comic)
    }

    override suspend fun getComicsFromDatabase(): List<Comic> {
        return comicDao.getAllComics()
    }

    override suspend fun deleteComicFromDatabase(comicId: Int) {
        return comicDao.deleteComic(comicId)
    }
}