package de.fjuretzko.comicviewer.domain.use_case.get_comics

import de.fjuretzko.comicviewer.common.Resource
import de.fjuretzko.comicviewer.data.remote.dto.comic.APIResponseComic
import de.fjuretzko.comicviewer.domain.repository.ComicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class GetComicsByCharacterUseCase @Inject constructor(
    private val repository: ComicRepository
) {

    operator fun invoke(characterId: Int, index: Int): Flow<Resource<APIResponseComic>> = flow {
        Timber.i("invoking get comics use case")
        try {
            emit(Resource.Loading())
            val apiResponse = repository.getComicsByCharacterId(characterId, index)
            emit(Resource.Success(apiResponse))
        } catch(error: HttpException) {
            emit(Resource.Error(error.localizedMessage ?: "Irgendwas ist falsch gelaufen.."))
        } catch(error: IOException) {
            emit(Resource.Error(error.localizedMessage ?: "Irgendwas ist falsch gelaufen.."))
        }
    }
}