package de.fjuretzko.comicviewer.data.room

import androidx.room.*
import de.fjuretzko.comicviewer.domain.model.Comic

@Dao
interface ComicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComic(comic: Comic)

    @Query("SELECT * FROM comics")
    fun getAllComics(): List<Comic>

    @Query("DELETE FROM comics WHERE id = :comicId")
    fun deleteComic(comicId: Int)
}