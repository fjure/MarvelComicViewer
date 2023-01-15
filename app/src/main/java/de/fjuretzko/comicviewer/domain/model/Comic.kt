package de.fjuretzko.comicviewer.domain.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import de.fjuretzko.comicviewer.data.remote.dto.comic.Characters
import de.fjuretzko.comicviewer.data.remote.dto.comic.Price
import de.fjuretzko.comicviewer.data.remote.dto.comic.Thumbnail

@Entity(tableName = "comics")
data class Comic(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    @Ignore
    val description: String?,
    @Ignore
    val thumbnail: Thumbnail?,
    @Ignore
    val comicSize: Int,
    @Ignore
    val characters: Characters?,
    @Ignore
    val prices: List<Price>?
) {
    constructor(id: Int, title: String): this(id, title, "", null, 0, null, null)
}
