package de.fjuretzko.comicviewer.di


import android.content.Context
import arrow.integrations.jackson.module.EitherModuleConfig
import arrow.integrations.jackson.module.registerArrowModule
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.fjuretzko.comicviewer.common.Constants
import de.fjuretzko.comicviewer.data.remote.MarvelApi
import de.fjuretzko.comicviewer.data.repository.CharacterRepositoryImpl
import de.fjuretzko.comicviewer.data.repository.ComicRepositoryImpl
import de.fjuretzko.comicviewer.data.room.ComicDao
import de.fjuretzko.comicviewer.data.room.ComicDatabase
import de.fjuretzko.comicviewer.domain.repository.CharacterRepository
import de.fjuretzko.comicviewer.domain.repository.ComicRepository
import retrofit2.Retrofit

import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMarvelApi(): MarvelApi {

        val mapper = ObjectMapper()
            .registerKotlinModule()
            .registerArrowModule(
                eitherModuleConfig = EitherModuleConfig("left", "right"),
            )
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
            .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .build()
            .create(MarvelApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(api: MarvelApi): CharacterRepository {
        return CharacterRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideComicDatabase(@ApplicationContext appContext : Context): ComicDatabase {
        return ComicDatabase.getInstance(appContext)
    }

    @Provides
    @Singleton
    fun provideComicDao(appDatabase: ComicDatabase): ComicDao {
        return appDatabase.comicDao()
    }

    @Provides
    @Singleton
    fun provideComicRepository(api: MarvelApi, comicDao: ComicDao): ComicRepository {
        return ComicRepositoryImpl(api, comicDao)
    }
}