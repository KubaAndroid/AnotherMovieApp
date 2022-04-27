package jw.adamiak.anothermoviesearchapp.ui.my_lists

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import jw.adamiak.anothermoviesearchapp.R
import jw.adamiak.anothermoviesearchapp.data.model.Movie
import jw.adamiak.anothermoviesearchapp.databinding.FragmentWatchListBinding
import jw.adamiak.anothermoviesearchapp.ui.adapter.MoviesListAdapter

@AndroidEntryPoint
class WatchListFragment: Fragment(R.layout.fragment_watch_list), MoviesListAdapter.OnMovieClickListener {

	private lateinit var binding: FragmentWatchListBinding
	private lateinit var linearLayoutManager: LinearLayoutManager
	private lateinit var moviesListAdapter: MoviesListAdapter

	private val viewModel by viewModels<MyListViewModel>()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding = FragmentWatchListBinding.bind(view)
		initRecyclerView()
		setupObservers()

	}

	private fun initRecyclerView() {
		linearLayoutManager = LinearLayoutManager(activity)
		binding.rvWatchlist.layoutManager = linearLayoutManager
		moviesListAdapter = MoviesListAdapter(requireContext(), this)
		binding.rvWatchlist.adapter = moviesListAdapter
	}

	private fun setupObservers() {
		viewModel.toWatchMovies.observe(viewLifecycleOwner) {
			if(it.isEmpty()){
				binding.searchviewWatchlist.visibility = View.GONE
			} else {
				binding.searchviewWatchlist.visibility = View.VISIBLE
				setSearchView()
			}
			moviesListAdapter.setData(it)
		}
	}

	private fun setSearchView() {
		binding.searchviewWatchlist.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextChange(newText: String?): Boolean {
				moviesListAdapter.getFilter().filter(newText)
				return false
			}
			override fun onQueryTextSubmit(query: String?): Boolean {
				moviesListAdapter.getFilter().filter(query)
				return false
			}
		})
	}

	override fun onMovieClicked(movie: Movie) {
		val bundle = bundleOf("movie" to movie)
		findNavController().navigate(R.id.action_myListsFragment_to_detailsBarFragment, bundle)
	}

}