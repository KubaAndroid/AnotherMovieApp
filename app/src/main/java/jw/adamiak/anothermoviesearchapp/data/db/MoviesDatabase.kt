package jw.adamiak.anothermoviesearchapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jw.adamiak.anothermoviesearchapp.data.model.Movie

@Database(
	entities = [Movie::class],
	version = 3,
	exportSchema = false
)
abstract class MoviesDatabase: RoomDatabase() {
	abstract val dao: MoviesDao
}