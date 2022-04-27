package jw.adamiak.anothermoviesearchapp.ui.popular

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import jw.adamiak.anothermoviesearchapp.data.api.TheMovieDbApi
import jw.adamiak.anothermoviesearchapp.data.model.Movie
import jw.adamiak.anothermoviesearchapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
	private val repository: MovieRepository
): ViewModel() {

	private var _isLoading = MutableLiveData(true)
	val isLoading: LiveData<Boolean>
		get() = _isLoading

	private var _popularMoviesList: Flow<PagingData<Movie>>? = null
	val popularMoviesList: LiveData<PagingData<Movie>>?
		get() = _popularMoviesList?.asLiveData()

	private fun getPopularMoviesFlow(){
		viewModelScope.launch {
			_isLoading.postValue(true)
			val result: Flow<PagingData<Movie>> =
				repository.getPopularMoviesStream().cachedIn(viewModelScope)
			_popularMoviesList = repository.getPopularMoviesStream()
			_popularMoviesList = result
			_isLoading.postValue(false)
		}
	}

	init {
		getPopularMoviesFlow()
	}

}