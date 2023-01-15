package de.fjuretzko.comicviewer.domain.use_case.save_comic_to_favorite

import de.fjuretzko.comicviewer.domain.model.Comic
import de.fjuretzko.comicviewer.domain.repository.ComicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class SaveComicToFavoriteUseCase @Inject constructor(
    private val repository: ComicRepository
) {
    operator fun invoke(comic: Comic): Flow<Boolean> = flow {
        try {
            repository.saveComicToStorage(comic)
            emit(true)
        } catch (error: Error) {
            emit(false)
        }
    }

}