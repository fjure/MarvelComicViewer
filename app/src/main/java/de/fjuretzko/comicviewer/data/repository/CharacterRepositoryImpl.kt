package de.fjuretzko.comicviewer.data.repository

import de.fjuretzko.comicviewer.data.remote.MarvelApi
import de.fjuretzko.comicviewer.data.remote.dto.character.APIResponseCharacter
import de.fjuretzko.comicviewer.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: MarvelApi
) : CharacterRepository {

    override suspend fun getCharacter(characterName: String): APIResponseCharacter {
        return api.getCharacter(characterName)
    }
}