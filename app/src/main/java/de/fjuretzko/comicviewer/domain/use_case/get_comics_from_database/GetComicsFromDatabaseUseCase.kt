package de.fjuretzko.comicviewer.domain.use_case.get_comics_from_database

import de.fjuretzko.comicviewer.domain.model.Comic
import de.fjuretzko.comicviewer.domain.repository.ComicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class GetComicsFromDatabaseUseCase @Inject constructor(
    private val repository: ComicRepository
) {
    operator fun invoke(): Flow<List<Comic>> = flow {
        Timber.i("test")
       try {
           val comics = repository.getComicsFromDatabase()
           emit(comics)
       } catch(error: IOException) {
           emit(emptyList())
       }
    }
}