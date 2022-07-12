package jw.adamiak.anothermoviesearchapp.ui.details

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jw.adamiak.anothermoviesearchapp.R
import jw.adamiak.anothermoviesearchapp.data.model.Movie
import jw.adamiak.anothermoviesearchapp.data.db.MoviesDatabase
import jw.adamiak.anothermoviesearchapp.data.repository.MovieRepository
import jw.adamiak.anothermoviesearchapp.utils.ApiUtils.IMG_URL_500_PREFIX
import jw.adamiak.anothermoviesearchapp.utils.DataState
import kotlinx.coroutines.launch
import java.io.*
import java.lang.Thread.sleep
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
	private val repository: MovieRepository
): ViewModel() {

	private var _snackbarMessage = MutableLiveData<String>()
	val snackbarMessage: LiveData<String>
		get() = _snackbarMessage

	var currentMovie: Movie? = null

	private var _isMovieFavorite = MutableLiveData<Boolean>()
	val isMovieFavorite: LiveData<Boolean>
		get() = _isMovieFavorite

	private var _isMovieOnWatchlist = MutableLiveData<Boolean>()
	val isMovieOnWatchlist: LiveData<Boolean>
		get() = _isMovieOnWatchlist


	fun insertMovieToFavorites(movie: Movie){
		viewModelScope.launch {
			when(val result = repository.insertMovieToFavorites(movie)){
				is DataState.Success -> {
					_snackbarMessage.postValue("The movie was added to favorites")
				}
				is DataState.Error -> {
					_snackbarMessage.postValue(result.error)
				}
			}
			isFav()
		}
	}

	fun deleteMovieFromFavorites(movie: Movie){
		viewModelScope.launch {
			when(val result = repository.deleteMovieFromFavorites(movie)){
				is DataState.Success -> {
					_snackbarMessage.postValue("The movie was removed from favorites")
				}
				is DataState.Error -> {
					_snackbarMessage.postValue(result.error)
				}
			}
			isFav()
		}
	}

	fun insertMovieToWatch(movie: Movie){
		viewModelScope.launch {
			when(val result = repository.insertMovieToWatch(movie)){
				is DataState.Success -> {
					_snackbarMessage.postValue( "The movie was added to watchlist")
				}
				is DataState.Error -> {
					_snackbarMessage.postValue(result.error)
				}
			}
			isWatchlist()
		}
	}

	fun deleteMovieFromWatchList(movie: Movie){
		viewModelScope.launch {
			when(val result = repository.deleteMovieFromWatchList(movie)){
				is DataState.Success -> {
					_snackbarMessage.postValue( "The movie was removed from watchlist")
				}
				is DataState.Error -> {
					_snackbarMessage.postValue(result.error)
				}
			}
			isWatchlist()
		}
	}

	fun addCurrentMovie(movie: Movie){
		currentMovie = movie
	}

	fun isFav(){
		viewModelScope.launch {
			if(currentMovie != null){
				_isMovieFavorite.postValue(repository.isMovieFavorite(currentMovie!!))
			}
		}
	}

	fun isWatchlist(){
		viewModelScope.launch {
			if(currentMovie != null) {
				_isMovieOnWatchlist.postValue(repository.isMovieOnWatchlist(currentMovie!!))
			}
		}
	}

}