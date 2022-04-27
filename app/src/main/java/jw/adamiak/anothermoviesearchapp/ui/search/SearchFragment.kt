package jw.adamiak.anothermoviesearchapp.ui.search

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
import jw.adamiak.anothermoviesearchapp.data.adapter.SearchPagerAdapter
import jw.adamiak.anothermoviesearchapp.data.model.Movie
import jw.adamiak.anothermoviesearchapp.databinding.FragmentSearchBinding
import jw.adamiak.anothermoviesearchapp.utils.ApiUtils.isUserOnline
import jw.adamiak.anothermoviesearchapp.utils.UiUtils
import jw.adamiak.anothermoviesearchapp.utils.UiUtils.hideKeyboard
import jw.adamiak.anothermoviesearchapp.utils.UiUtils.toggleProgressBar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment: Fragment(R.layout.fragment_search), SearchPagerAdapter.OnMovieClickListener {

	private lateinit var binding: FragmentSearchBinding
	private lateinit var linearLayoutManager: LinearLayoutManager
	private lateinit var moviesSearchAdapter: SearchPagerAdapter

	private val viewModel by viewModels<SearchViewModel>()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding = FragmentSearchBinding.bind(view)

		initUI()
		checkIfUserIsOnline()
	}

	private fun initUI(){
		binding.btnUserListSearch.setOnClickListener {
			searchMovie()
		}
		moviesSearchAdapter = SearchPagerAdapter(requireContext(), this)
		linearLayoutManager = LinearLayoutManager(activity)
		binding.rvUsers.layoutManager = linearLayoutManager
		binding.rvUsers.adapter = moviesSearchAdapter
	}

	private fun checkIfUserIsOnline(){
		if(isUserOnline(requireContext())){
			setupObservers()
		} else {
			UiUtils.showAlert(requireContext(), requireActivity(), "No Internet connection",
				"Please turn on internet connection")
		}
	}

	private fun setupObservers() {
		viewModel.moviesList?.observe(viewLifecycleOwner) {
			lifecycleScope.launch {
				moviesSearchAdapter.submitData(it)
			}
		}
		viewModel.isLoading.observe(viewLifecycleOwner) {
			toggleProgressBar(binding.pbList, it)
		}
	}

	private fun searchMovie() {
		val searchText = binding.etSearch.text.toString()
		hideKeyboard(requireActivity())
		lifecycleScope.launch {
			viewModel.getMoviesFromApi(searchText).collectLatest { data ->
				moviesSearchAdapter.submitData(data)
			}
		}
	}

	override fun onMovieClicked(movie: Movie) {
		val bundle = bundleOf("movie" to movie)
		findNavController().navigate(R.id.action_searchFragment_to_detailsBarFragment, bundle)
	}

}