package de.fjuretzko.comicviewer.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.fjuretzko.comicviewer.domain.model.Comic

@Database(entities = [(Comic::class)], version = 2)
abstract class ComicDatabase: RoomDatabase() {

    companion object {

        private var INSTANCE: ComicDatabase? = null

        fun getInstance(context: Context): ComicDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ComicDatabase::class.java,
                        "comic_database"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    abstract fun comicDao(): ComicDao
}