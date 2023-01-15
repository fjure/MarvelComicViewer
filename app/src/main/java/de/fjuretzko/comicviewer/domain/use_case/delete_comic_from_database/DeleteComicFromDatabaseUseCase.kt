package de.fjuretzko.comicviewer.domain.use_case.delete_comic_from_database

import de.fjuretzko.comicviewer.domain.repository.ComicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class DeleteComicFromDatabaseUseCase @Inject constructor(
    private val repository: ComicRepository
) {
    operator fun invoke(comicId: Int): Flow<Boolean> = flow {
        try {
            repository.deleteComicFromDatabase(comicId)
            emit(true)
        } catch(error: IOException) {
            emit(false)
        }
    }
}