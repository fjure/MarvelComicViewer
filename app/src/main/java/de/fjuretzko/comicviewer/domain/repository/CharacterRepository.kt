package de.fjuretzko.comicviewer.domain.repository

import de.fjuretzko.comicviewer.data.remote.dto.character.APIResponseCharacter

interface CharacterRepository {

    suspend fun getCharacter(characterName: String): APIResponseCharacter

}