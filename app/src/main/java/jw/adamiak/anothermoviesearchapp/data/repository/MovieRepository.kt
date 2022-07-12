package jw.adamiak.anothermoviesearchapp.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import jw.adamiak.anothermoviesearchapp.data.api.MoviesPagingSource
import jw.adamiak.anothermoviesearchapp.data.api.PopularMoviesPagingSource
import jw.adamiak.anothermoviesearchapp.data.api.TheMovieDbApi
import jw.adamiak.anothermoviesearchapp.data.db.MoviesDao
import jw.adamiak.anothermoviesearchapp.data.model.Movie
import jw.adamiak.anothermoviesearchapp.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val NETWORK_PAGE_SIZE = 25

class MovieRepository @Inject constructor (val api: TheMovieDbApi, private val dao: MoviesDao) {
	private val TAG = "MovieRepository"

	val favoriteMovies = dao.getAllFavorites()
	val moviesToWatch = dao.getAllMoviesToWatch()

	fun getSearchResultStream(query: String): Flow<PagingData<Movie>> {
		return Pager(
			config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
			pagingSourceFactory = { MoviesPagingSource(api, query) }
		).flow
	}

	fun getPopularMoviesStream(): Flow<PagingData<Movie>> {
		return Pager(
			config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
			pagingSourceFactory = { PopularMoviesPagingSource(api) }
		).flow
	}

	suspend fun insertMovieToFavorites(movie: Movie): DataState {
		return try {
			movie.isFavorite = true
			dao.insert(movie)
			DataState.Success
		} catch (e: Exception) {
			DataState.Error("Error occurred ${e.message}")
		}
	}



	suspend fun deleteMovieFromFavorites(movie: Movie): DataState {
		return try {
			movie.isFavorite = false
			dao.insert(movie)
			DataState.Success
		} catch (e: Exception) {
			DataState.Error("Error occurred ${e.message}")
		}
	}

	suspend fun insertMovieToWatch(movie: Movie): DataState {
		return try {
			movie.toWatch = true
			dao.insert(movie)
			DataState.Success
		} catch (e: Exception) {
			DataState.Error("Error occurred ${e.message}")
		}
	}

	suspend fun deleteMovieFromWatchList(movie: Movie): DataState {
		return try {
			movie.toWatch = false
			dao.insert(movie)
			DataState.Success
		} catch (e: Exception) {
			DataState.Error("Error occurred ${e.message}")
		}
	}

	suspend fun isMovieOnWatchlist(movie: Movie): Boolean {
		return try {
			dao.isMovieOnWatchlist(movie.id)
		} catch (e: Exception) {
			Log.e(TAG, e.message.toString())
			false
		}
	}

	suspend fun isMovieFavorite(movie: Movie): Boolean {
		return try {
			dao.isMovieFavorite(movie.id)
		} catch (e: Exception) {
			Log.e(TAG, e.message.toString())
			false
		}
	}

}