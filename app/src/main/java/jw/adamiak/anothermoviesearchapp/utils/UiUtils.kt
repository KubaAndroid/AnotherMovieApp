package jw.adamiak.anothermoviesearchapp.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import jw.adamiak.anothermoviesearchapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object UiUtils {
	fun toggleProgressBar(pb: ProgressBar, show: Boolean){
		CoroutineScope(Dispatchers.Main).launch {
			if(show){
				pb.visibility = View.VISIBLE
			} else {
				pb.visibility = View.INVISIBLE
			}
		}
	}

	fun setImage(iv: ImageView, uri: Uri){
		Glide.with(iv)
			.load(uri)
			.into(iv)
	}

	fun showSnackbar(v: View, message: String){
		val snack = Snackbar.make(v, message, Snackbar.LENGTH_LONG).setAction("OK", null)
		snack.show()
	}

	fun showAlert(context: Context, activity: Activity, title: String, msg: String) {
		CoroutineScope(Dispatchers.Main).launch {
			val alertBuilder = AlertDialog.Builder(context)
			val customLayout = activity.layoutInflater.inflate(R.layout.custom_alert, null)
			val okButton = customLayout.findViewById<TextView>(R.id.btn_alert_ok)
			val tvTitle = customLayout.findViewById<TextView>(R.id.tv_alert_title)
			val tvMessage = customLayout.findViewById<TextView>(R.id.tv_alert_message)
			alertBuilder.setCancelable(false)
			alertBuilder.setView(customLayout)
			val alert = alertBuilder.create()
			okButton.setOnClickListener {
				alert.dismiss()
			}
			tvTitle.text = title
			tvMessage.text = msg
			alert.show()
		}
	}

	fun hideKeyboard(currentActivity: Activity){
		val imm = currentActivity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
		var view = currentActivity.currentFocus
		if(view == null){
			view = View(currentActivity)
		}
		imm.hideSoftInputFromWindow(view.windowToken, 0)
	}

	fun setRatingColor(rating: Float): Int{
		return when(rating.toInt()){
			in 0..4 -> R.color.rating_bad
			in 5..6 -> R.color.rating_average
			in 7..10 -> R.color.rating_good
			else -> R.color.dark_font_dark
		}
	}
}