package jw.adamiak.anothermoviesearchapp.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import jw.adamiak.anothermoviesearchapp.BuildConfig

const val STARTING_PAGE_INDEX = 1

object ApiUtils {
	const val BASE_URL = "https://api.themoviedb.org"
	const val API_KEY = BuildConfig.TMDB_API_KEY
	const val IMG_URL_500_PREFIX = "https://image.tmdb.org/t/p/w500/"

	fun isUserOnline(context: Context): Boolean {
		val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
			val activeNetwork = connectivityManager.activeNetwork ?: return false
			val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
			return when {
				capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
				capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
				capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
				else -> false
			}
		} else {
			connectivityManager.activeNetworkInfo?.run {
				return when(type) {
					ConnectivityManager.TYPE_WIFI -> true
					ConnectivityManager.TYPE_MOBILE -> true
					ConnectivityManager.TYPE_ETHERNET -> true
					else -> false
				}
			}
		}
		return false
	}

	val REQUEST_PERMISSIONS_CODE = 1001
	val REQUIRED_PERMISSIONS = arrayOf(
		Manifest.permission.INTERNET,
		Manifest.permission.ACCESS_WIFI_STATE,
		Manifest.permission.ACCESS_NETWORK_STATE,
	)

	fun allPermissionsGranted(context: Context) = REQUIRED_PERMISSIONS.all {
		ContextCompat.checkSelfPermission(
			context.applicationContext, it
		) == PackageManager.PERMISSION_GRANTED
	}

	fun requestPermissions(activity: Activity){
		ActivityCompat.requestPermissions(activity,
			REQUIRED_PERMISSIONS,
			REQUEST_PERMISSIONS_CODE)
	}

}