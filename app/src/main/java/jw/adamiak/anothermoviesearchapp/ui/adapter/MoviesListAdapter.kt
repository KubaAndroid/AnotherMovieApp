package jw.adamiak.anothermoviesearchapp.ui.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jw.adamiak.anothermoviesearchapp.R
import jw.adamiak.anothermoviesearchapp.data.model.Movie
import jw.adamiak.anothermoviesearchapp.databinding.MovieListItemBinding
import jw.adamiak.anothermoviesearchapp.utils.ApiUtils.IMG_URL_500_PREFIX
import jw.adamiak.anothermoviesearchapp.utils.UiUtils.setImage
import jw.adamiak.anothermoviesearchapp.utils.UiUtils.setRatingColor

class MoviesListAdapter(private val context: Context, private val clickListener: OnMovieClickListener) :
	PagingDataAdapter<Movie, MoviesListAdapter.MovieListHolder>(MoviesDiffCallback()) {

	private var movies: List<Movie> = ArrayList()
	private var moviesFilteredList: List<Movie> = ArrayList()

	fun setData(moviesList: List<Movie>) {
		movies = moviesList
		moviesFilteredList = moviesList
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder {
		return MovieListHolder(
			MovieListItemBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			)
		)
	}

	override fun onBindViewHolder(holder: MovieListHolder, position: Int) {
		if (moviesFilteredList.isNotEmpty() && moviesFilteredList.size > position) {
			holder.bind(moviesFilteredList[position])
		}
	}

	inner class MovieListHolder(private val binding: MovieListItemBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(movie: Movie) {
			binding.apply {
				tvListTitle.text = movie.original_title
				tvListYear.text = movie.release_date
				tvListRating.text = "Rating: ${movie.vote_average.toString()}/10"
				tvListRating.setTextColor(
					ContextCompat.getColor(
						context,
						setRatingColor(movie.vote_average)
					)
				)
				tvListOverview.text = movie.overview
				root.setOnClickListener { clickListener.onMovieClicked(movie) }
				setImage(ivListPoster, (IMG_URL_500_PREFIX + movie.poster_path).toUri())
			}
		}
	}

	override fun getItemCount(): Int {
		return moviesFilteredList.size
	}

	fun getFilter(): Filter {
		return object : Filter() {
			override fun performFiltering(constraint: CharSequence): FilterResults {
				val charSequenceString = constraint.toString()
				if (charSequenceString.isEmpty()) {
					moviesFilteredList = movies
				} else {
					val filteredList: MutableList<Movie> = java.util.ArrayList<Movie>()
					for (movie in movies) {
						if ((movie.original_title != null && movie.original_title.lowercase()
								.contains(charSequenceString.lowercase())) ||
							(movie.title != null && movie.title.lowercase()
								.contains(charSequenceString.lowercase()))
						) {
							filteredList.add(movie)
						}
						moviesFilteredList = filteredList
					}
				}
				val results = FilterResults()
				results.values = moviesFilteredList
				return results
			}

			override fun publishResults(constraint: CharSequence, results: FilterResults) {
				moviesFilteredList = results.values as List<Movie>
				notifyDataSetChanged()
			}
		}
	}

	interface OnMovieClickListener {
		fun onMovieClicked(movie: Movie)
	}
}

private class MoviesDiffCallback: DiffUtil.ItemCallback<Movie>() {
	override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
		oldItem.id == newItem.id
	override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
		oldItem == newItem
}