package jw.adamiak.anothermoviesearchapp.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import jw.adamiak.anothermoviesearchapp.data.model.Movie
import jw.adamiak.anothermoviesearchapp.databinding.MovieListPopularItemBinding
import jw.adamiak.anothermoviesearchapp.utils.ApiUtils
import jw.adamiak.anothermoviesearchapp.utils.UiUtils
import jw.adamiak.anothermoviesearchapp.utils.UiUtils.setImage
import jw.adamiak.anothermoviesearchapp.utils.UiUtils.setRatingColor

class PopularPagerAdapter(val context: Context, val clickListener: OnMovieClickListener):
	PagingDataAdapter<Movie, PopularPagerAdapter.PopularMovieViewHolder>(MoviesDiffCallback()) {

	inner class PopularMovieViewHolder(private val binding: MovieListPopularItemBinding):
		RecyclerView.ViewHolder(binding.root) {
			fun bind(movie: Movie) {
				binding.apply {
					tvPopTitle.text = movie.original_title
					tvPopDesc.text = movie.overview
					tvPopYear.text = movie.release_date
					tvPopRating.text = "${movie.vote_average.toString()}/10"
					tvPopRating.setTextColor(ContextCompat.getColor(context, setRatingColor(movie.vote_average)))
					root.setOnClickListener { clickListener.onMovieClicked(movie) }
				}
				setImage(binding.ivPopPoster, (ApiUtils.IMG_URL_500_PREFIX + movie.poster_path).toUri())
			}
		}

	override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
		val movie = getItem(position)
		if(movie != null) {
			holder.bind(movie)
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
		return PopularMovieViewHolder(
			MovieListPopularItemBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			)
		)
	}

	interface OnMovieClickListener {
		fun onMovieClicked(movie: Movie)
	}

}

private class MoviesDiffCallback: DiffUtil.ItemCallback<Movie>(){
	override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
		return oldItem.id == newItem.id
	}

	override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
		return oldItem == newItem
	}
}