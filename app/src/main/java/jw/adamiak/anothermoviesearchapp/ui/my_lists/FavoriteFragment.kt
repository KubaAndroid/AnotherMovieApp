package jw.adamiak.anothermoviesearchapp.ui.my_lists

import android.os.Bundle
import android.view.View
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
import jw.adamiak.anothermoviesearchapp.databinding.FragmentFavoriteBinding
import jw.adamiak.anothermoviesearchapp.ui.adapter.MoviesListAdapter

@AndroidEntryPoint
class FavoriteFragment: Fragment(R.layout.fragment_favorite), MoviesListAdapter.OnMovieClickListener {
	private lateinit var linearLayoutManager: LinearLayoutManager
	private lateinit var moviesListAdapter: MoviesListAdapter
	private lateinit var binding: FragmentFavoriteBinding

	private val viewModel by viewModels<MyListViewModel>()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding = FragmentFavoriteBinding.bind(view)
		initRecyclerView()
		setupObservers()
	}

	private fun setupObservers() {
		viewModel.favMovies.observe(viewLifecycleOwner) {
			moviesListAdapter.setData(it)
		}
	}

	private fun initRecyclerView(){
		linearLayoutManager = LinearLayoutManager(activity)
		binding.rvFavorites.layoutManager = linearLayoutManager
		moviesListAdapter = MoviesListAdapter(requireContext(), this)
		binding.rvFavorites.adapter = moviesListAdapter
	}

	override fun onMovieClicked(movie: Movie) {
		val bundle = bundleOf("movie" to movie)
		findNavController().navigate(R.id.action_myListsFragment_to_detailsBarFragment, bundle)
	}

}