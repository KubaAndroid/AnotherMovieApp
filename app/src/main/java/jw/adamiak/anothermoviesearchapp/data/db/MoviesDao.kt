package jw.adamiak.anothermoviesearchapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import jw.adamiak.anothermoviesearchapp.data.model.Movie

@Dao
interface MoviesDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(movie: Movie)

	@Update(onConflict = OnConflictStrategy.REPLACE)
	suspend fun update(movie: Movie)

	@Delete
	suspend fun delete(movie: Movie)

	@Query("SELECT * FROM movies_table")
	fun getAllMovies(): LiveData<List<Movie>>

	@Query("SELECT * FROM movies_table WHERE id = :movieId")
	suspend fun getWithId(movieId: Long): Movie?

	@Query("SELECT * FROM movies_table WHERE isFavorite = 1")
	fun getAllFavorites(): LiveData<MutableList<Movie>>

	@Query("SELECT * FROM movies_table WHERE toWatch = 1")
	fun getAllMoviesToWatch(): LiveData<MutableList<Movie>>

	@Query("SELECT EXISTS (SELECT * FROM movies_table AS m WHERE m.id = :movieId AND m.isFavorite = 1) ")
	suspend fun isMovieFavorite(movieId: Long): Boolean

	@Query("SELECT EXISTS (SELECT * FROM movies_table AS m WHERE m.id = :movieId AND m.toWatch = 1) ")
	suspend fun isMovieOnWatchlist(movieId: Long): Boolean

}