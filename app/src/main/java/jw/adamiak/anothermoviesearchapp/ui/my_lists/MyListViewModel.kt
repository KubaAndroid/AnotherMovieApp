package jw.adamiak.anothermoviesearchapp.ui.my_lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jw.adamiak.anothermoviesearchapp.data.model.Movie
import jw.adamiak.anothermoviesearchapp.data.repository.MovieRepository
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(
	repository: MovieRepository
): ViewModel() {

	private var _favMovies = repository.favoriteMovies
	val favMovies: LiveData<MutableList<Movie>>
		get() = _favMovies

	private var _toWatchMovies = repository.moviesToWatch
	val toWatchMovies: LiveData<MutableList<Movie>>
		get() = _toWatchMovies
}