package jw.adamiak.anothermoviesearchapp.ui.my_lists

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import jw.adamiak.anothermoviesearchapp.R
import jw.adamiak.anothermoviesearchapp.databinding.FragmentMyListsBinding
import jw.adamiak.anothermoviesearchapp.ui.adapter.MyListAdapter

class MyListsFragment: Fragment(R.layout.fragment_my_lists) {
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val binding = FragmentMyListsBinding.bind(view)

		val fragmentsList = arrayListOf<Fragment>(
			FavoriteFragment(),
			WatchListFragment()
		)

		val adapter = MyListAdapter(requireActivity().supportFragmentManager, lifecycle, fragmentsList)
		val viewPager = binding.vpMyList
		val tabs = binding.tabsMyList

		viewPager.adapter = adapter

		TabLayoutMediator(tabs, viewPager) {tab, position ->
			if(position == 0){
				tab.text = "Favorites"
			} else {
				tab.text = "To watch"
			}
		}.attach()

	}
}