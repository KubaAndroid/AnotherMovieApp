package jw.adamiak.anothermoviesearchapp.data.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jw.adamiak.anothermoviesearchapp.R
import jw.adamiak.anothermoviesearchapp.data.model.Movie
import jw.adamiak.anothermoviesearchapp.databinding.MovieListItemBinding
import jw.adamiak.anothermoviesearchapp.utils.ApiUtils
import jw.adamiak.anothermoviesearchapp.utils.UiUtils.setRatingColor

class SearchPagerAdapter(val context: Context, val clickListener: OnMovieClickListener):
	PagingDataAdapter<Movie, SearchPagerAdapter.MovieViewHolder>(MoviesDiffCallback()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
		return MovieViewHolder(
			MovieListItemBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			)
		)
	}

	override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
		val movie = getItem(position)
		if(movie != null) {
			holder.bind(movie)
		}
	}

	inner class MovieViewHolder(private val binding: MovieListItemBinding
	): RecyclerView.ViewHolder(binding.root) {

		fun bind(movie: Movie) {
			binding.apply {
				tvListTitle.text = movie.original_title
				tvListYear.text = movie.release_date
				tvListRating.text = "Rating: ${movie.vote_average.toString()}/10"
				tvListRating.setTextColor(ContextCompat.getColor(context, setRatingColor(movie.vote_average)))
				tvListOverview.text = movie.overview
				root.setOnClickListener { clickListener.onMovieClicked(movie) }
				setImage((ApiUtils.IMG_URL_500_PREFIX + movie.poster_path).toUri(), binding.ivListPoster)
			}
		}

		private fun setImage(uri: Uri?, iv: ImageView) {
			Glide.with(context)
				.load(uri)
				.placeholder(R.drawable.ic_movies)
				.error(R.drawable.ic_movies)
				.into(iv)
		}
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