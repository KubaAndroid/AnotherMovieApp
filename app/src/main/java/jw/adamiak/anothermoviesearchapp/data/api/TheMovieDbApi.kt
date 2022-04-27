package jw.adamiak.anothermoviesearchapp.data.api

import jw.adamiak.anothermoviesearchapp.data.model.MovieResponse
import jw.adamiak.anothermoviesearchapp.utils.ApiUtils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbApi {

	@GET("3/search/movie?api_key=$API_KEY")
	suspend fun searchMoviePager(
		@Query("query") query: String,
		@Query("page") page: Int
	) : Response<MovieResponse>

	@GET("3/movie/now_playing?api_key=$API_KEY")
	suspend fun getPopularMoviesPager(
		@Query("page") page: Int
	): Response<MovieResponse>

}