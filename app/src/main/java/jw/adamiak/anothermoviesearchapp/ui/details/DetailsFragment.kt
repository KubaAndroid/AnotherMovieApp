package jw.adamiak.anothermoviesearchapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import jw.adamiak.anothermoviesearchapp.R
import jw.adamiak.anothermoviesearchapp.data.model.Movie
import jw.adamiak.anothermoviesearchapp.databinding.FragmentDetailsBinding
import jw.adamiak.anothermoviesearchapp.utils.*
import jw.adamiak.anothermoviesearchapp.utils.UiUtils.setImage
import jw.adamiak.anothermoviesearchapp.utils.UiUtils.setRatingColor

@AndroidEntryPoint
class DetailsFragment: Fragment(R.layout.fragment_details) {

	private val viewModel by viewModels<DetailsViewModel>()

	private lateinit var movie: Movie
	private lateinit var binding: FragmentDetailsBinding

	private var isFav: Boolean = false
	private var isWatch: Boolean = false

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {

		if(arguments != null) {
			movie = requireArguments().get("movie") as Movie
			viewModel.addCurrentMovie(movie)
			viewModel.isFav()
			viewModel.isWatchlist()
		} else {
			findNavController().popBackStack()
		}

		setupObservers()

		binding = FragmentDetailsBinding.inflate(inflater).apply {
			detailsViewModel = viewModel
			lifecycleOwner = viewLifecycleOwner
			setImage(ivDetailsBar, (ApiUtils.IMG_URL_500_PREFIX + movie.poster_path).toUri())
			tvDetailsBarRating.setTextColor(ContextCompat.getColor(requireContext(), setRatingColor(movie.vote_average)))
			tvDetailsBarRating.text = "Avg rating: ${movie.vote_average}/10"
			var isToolbarShown = false
			movieDetailScrollview.setOnScrollChangeListener(
				NestedScrollView.OnScrollChangeListener {_, _, scrollY, _, _ ->
					val shouldShowToolbar = scrollY > toolbarDetails.height
					if(isToolbarShown != shouldShowToolbar) {
						isToolbarShown = shouldShowToolbar
						appbarDetails.isActivated = shouldShowToolbar
						toolbarLayout.isTitleEnabled = shouldShowToolbar
					}
				}
			)

			toolbarDetails.setNavigationOnClickListener { view ->
				view.findNavController().navigateUp()
			}
			toolbarDetails.setOnMenuItemClickListener { item ->
				when(item.itemId) {
					R.id.action_watchlist -> {
						checkWatchlist()
						true
					}
					R.id.action_favorite -> {
						checkFavorite()
						true
					}
					else -> false
				}
			}
		}

		setHasOptionsMenu(true)

		return binding.root
	}

	private fun setupObservers() {
		viewModel.isMovieFavorite.observe(viewLifecycleOwner) {
			isFav = it
		}
		viewModel.isMovieOnWatchlist.observe(viewLifecycleOwner) {
			isWatch = it
		}
		viewModel.snackbarMessage.observe(viewLifecycleOwner) {
			showSnackBar(it)
		}
	}

	private fun checkFavorite() {
			if(isFav) {
				viewModel.deleteMovieFromFavorites(movie)
			} else {
				viewModel.insertMovieToFavorites(movie)
		}
	}

	private fun checkWatchlist() {
		if(isWatch) {
			viewModel.deleteMovieFromWatchList(movie)
		} else {
			viewModel.insertMovieToWatch(movie)
		}
	}

	private fun showSnackBar(message: String){
		Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
	}

}