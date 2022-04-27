package jw.adamiak.anothermoviesearchapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import jw.adamiak.anothermoviesearchapp.data.db.MovieTypeConverters

@TypeConverters(MovieTypeConverters::class)
@Entity(tableName = "movies_table")
@kotlinx.parcelize.Parcelize
data class Movie (
	@PrimaryKey(autoGenerate = false)
	val id: Long,
	val original_title: String?,
	val title: String?,
	val overview: String?,
	val poster_path: String?,
	val release_date: String?,
	val vote_average: Float,
	var isFavorite: Boolean? = null,
	var toWatch: Boolean? = null
): Parcelable