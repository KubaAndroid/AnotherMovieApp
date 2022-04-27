package jw.adamiak.anothermoviesearchapp.data.api

import androidx.paging.PagingSource
import jw.adamiak.anothermoviesearchapp.data.model.Movie
import jw.adamiak.anothermoviesearchapp.utils.STARTING_PAGE_INDEX

class PopularMoviesPagingSource (private val service: TheMovieDbApi)
	: PagingSource<Int, Movie>() {

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
		val page = params.key ?: STARTING_PAGE_INDEX
		return try {
			val response = service.getPopularMoviesPager(page)
			val movies = response.body()?.results ?: emptyList()
			LoadResult.Page(
				data = movies,
				prevKey = if(page == STARTING_PAGE_INDEX) null else page - 1,
				nextKey = if (page == response.body()?.total_pages) null else page + 1
			)

		} catch (e: Exception) {
			LoadResult.Error(e)
		}
	}

}

