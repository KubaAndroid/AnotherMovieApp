package jw.adamiak.anothermoviesearchapp.ui.popular

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jw.adamiak.anothermoviesearchapp.R
import jw.adamiak.anothermoviesearchapp.data.model.Movie
import jw.adamiak.anothermoviesearchapp.databinding.FragmentPopularBinding
import jw.adamiak.anothermoviesearchapp.ui.adapter.PopularAdapterScaler
import jw.adamiak.anothermoviesearchapp.ui.adapter.PopularPagerAdapter
import jw.adamiak.anothermoviesearchapp.utils.ApiUtils
import jw.adamiak.anothermoviesearchapp.utils.UiUtils.showAlert
import jw.adamiak.anothermoviesearchapp.utils.UiUtils.toggleProgressBar
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularFragment: Fragment(R.layout.fragment_popular), PopularPagerAdapter.OnMovieClickListener {
	private lateinit var adapterScaler: PopularAdapterScaler
	private lateinit var popularMoviesAdapter: PopularPagerAdapter
	private lateinit var binding: FragmentPopularBinding

	private val viewModel by viewModels<PopularViewModel>()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding = FragmentPopularBinding.bind(view)

		initRecycler()
		if(ApiUtils.isUserOnline(requireContext())){
			setupObservers()
		} else {
			toggleProgressBar(binding.pbPopular, false)
			showAlert(requireContext(), requireActivity(), "No internet connection",
				"Please turn on internet connection")
		}
	}

	private fun setupObservers() {
		viewModel.isLoading.observe(viewLifecycleOwner) {
			toggleProgressBar(binding.pbPopular, it)
		}
		viewModel.popularMoviesList?.observe(viewLifecycleOwner) {
			lifecycleScope.launch {
				popularMoviesAdapter.submitData(it)
			}
		}
	}

	private fun initRecycler() {
		adapterScaler = PopularAdapterScaler(requireContext(), LinearLayoutManager.HORIZONTAL, false)
		binding.rvPopular.layoutManager = adapterScaler
		popularMoviesAdapter = PopularPagerAdapter(requireContext(), this)
		binding.rvPopular.adapter = popularMoviesAdapter
	}

	override fun onMovieClicked(movie: Movie) {
		val bundle = bundleOf("movie" to movie)
		findNavController().navigate(R.id.action_popularFragment_to_detailsBarFragment, bundle)
	}
}