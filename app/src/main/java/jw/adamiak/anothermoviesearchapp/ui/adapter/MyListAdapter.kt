package jw.adamiak.anothermoviesearchapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyListAdapter (fm: FragmentManager, lifecycle: Lifecycle, list: ArrayList<Fragment>):
	FragmentStateAdapter(fm, lifecycle) {
	private val fragments = list
	override fun getItemCount(): Int {
		return fragments.size
	}

	override fun createFragment(position: Int): Fragment {
		return fragments[position]
	}
}