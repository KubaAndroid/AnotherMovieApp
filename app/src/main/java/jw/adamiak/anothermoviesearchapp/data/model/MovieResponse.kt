package jw.adamiak.anothermoviesearchapp.data.model

data class MovieResponse(
	val page: Int?,
	val results: List<Movie>,
	val total_pages: Int
)
