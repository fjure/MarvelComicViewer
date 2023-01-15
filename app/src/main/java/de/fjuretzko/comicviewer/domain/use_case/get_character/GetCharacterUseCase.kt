package de.fjuretzko.comicviewer.domain.use_case.get_character

import de.fjuretzko.comicviewer.common.Resource
import de.fjuretzko.comicviewer.data.remote.dto.character.APIResponseCharacter
import de.fjuretzko.comicviewer.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {

    operator fun invoke(characterName: String): Flow<Resource<APIResponseCharacter>> = flow {
        Timber.i("test")
        try {
            emit(Resource.Loading())
            val apiResponse = repository.getCharacter(characterName)
            emit(Resource.Success(apiResponse))
        } catch(error: HttpException) {
            emit(Resource.Error(error.localizedMessage ?: "Irgendwas ist falsch gelaufen.."))
        } catch(error: IOException) {
            emit(Resource.Error(error.localizedMessage ?: "Irgendwas ist falsch gelaufen.."))
        }
    }

}