package jw.adamiak.anothermoviesearchapp.ui.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import jw.adamiak.anothermoviesearchapp.data.api.TheMovieDbApi
import jw.adamiak.anothermoviesearchapp.data.model.Movie
import jw.adamiak.anothermoviesearchapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
	private val repository: MovieRepository
): ViewModel() {

	private var _moviesList: Flow<PagingData<Movie>>? = null
	val moviesList: LiveData<PagingData<Movie>>?
		get() = _moviesList?.asLiveData()

	private var currentQueryValue: String? = null

	private var _isLoading = MutableLiveData<Boolean>()
	val isLoading: LiveData<Boolean>
		get() = _isLoading

	fun getMoviesFromApi(queryString: String): Flow<PagingData<Movie>> {
		_isLoading.postValue(true)
		currentQueryValue = queryString
		val result: Flow<PagingData<Movie>> =
			repository.getSearchResultStream(queryString).cachedIn(viewModelScope)
		_moviesList = result
		_isLoading.postValue(false)
		return result
	}

}