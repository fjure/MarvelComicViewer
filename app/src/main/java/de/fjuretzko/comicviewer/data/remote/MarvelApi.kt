package de.fjuretzko.comicviewer.data.remote

import de.fjuretzko.comicviewer.common.Constants
import de.fjuretzko.comicviewer.data.remote.dto.character.APIResponseCharacter
import de.fjuretzko.comicviewer.data.remote.dto.comic.APIResponseComic
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("v1/public/characters")
    suspend fun getCharacter(
        @Query("name") name: String,
        @Query("ts") ts: String? = "1",
        @Query("apikey") apikey: String? = Constants.PUBLIC_KEY,
        @Query("hash") hash: String? = Constants.HASH
    ): APIResponseCharacter

    @GET("v1/public/characters/{id}/comics")
    suspend fun getComicsByCharacterId(
        @Path("id") id: Int,
        @Query("offset") index: Int,
        @Query("ts") ts: String? = "1",
        @Query("apikey") apikey: String? = Constants.PUBLIC_KEY,
        @Query("hash") hash: String? = Constants.HASH
    ): APIResponseComic

    @GET("v1/public/comics/{id}")
    suspend fun getComicById(
        @Path("id") id: Int,
        @Query("ts") ts: String? = "1",
        @Query("apikey") apikey: String? = Constants.PUBLIC_KEY,
        @Query("hash") hash: String? = Constants.HASH
    ): APIResponseComic
}