package de.fjuretzko.comicviewer.domain.use_case.get_comic

import de.fjuretzko.comicviewer.common.Resource
import de.fjuretzko.comicviewer.domain.model.Comic
import de.fjuretzko.comicviewer.domain.repository.ComicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class GetComicUseCase @Inject constructor(
    private val repository: ComicRepository
) {

    operator fun invoke(comicId: Int): Flow<Resource<Comic>> = flow {
        try {
            emit(Resource.Loading())
            val apiResponse = repository.getComicById(comicId)
            emit(Resource.Success(apiResponse))
        } catch(error: HttpException) {
            emit(Resource.Error(error.localizedMessage ?: "Irgendwas ist falsch gelaufen.."))
        } catch(error: IOException) {
            emit(Resource.Error(error.localizedMessage ?: "Irgendwas ist falsch gelaufen.."))
        }
    }

}