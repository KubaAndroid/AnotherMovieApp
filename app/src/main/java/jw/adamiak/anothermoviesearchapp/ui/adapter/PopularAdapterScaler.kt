package jw.adamiak.anothermoviesearchapp.ui.adapter

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PopularAdapterScaler: LinearLayoutManager {
	constructor(context: Context, orientation: Int, reverseLayout: Boolean):
		super(context, orientation, reverseLayout)

	override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
		lp?.width = (width / 1.5).toInt()
		return true
	}

	override fun onLayoutCompleted(state: RecyclerView.State?) {
		super.onLayoutCompleted(state)
		scaleMiddleItem()
	}

	private fun scaleMiddleItem(){
		val mid = width / 2
		val d1 = 0.9 * mid
		for(i in 0 until childCount){
			val child = getChildAt(i)
			val childMid = (getDecoratedRight(child!!) + getDecoratedLeft(child)) / 2f
			val d = Math.min(d1, Math.abs(mid - childMid).toDouble())

			val scale = 1 - 0.15f * d/d1
			child.scaleX = scale.toFloat()
			child.scaleY = scale.toFloat()

		}
	}

	override fun scrollHorizontallyBy(
		dx: Int,
		recycler: RecyclerView.Recycler?,
		state: RecyclerView.State?
	): Int {
		return if(orientation == HORIZONTAL){
			val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
			scaleMiddleItem()
			scrolled
		} else {
			0
		}
	}
}