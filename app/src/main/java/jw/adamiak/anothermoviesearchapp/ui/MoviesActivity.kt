package jw.adamiak.anothermoviesearchapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import jw.adamiak.anothermoviesearchapp.R
import jw.adamiak.anothermoviesearchapp.utils.ApiUtils
import jw.adamiak.anothermoviesearchapp.utils.ApiUtils.allPermissionsGranted
import jw.adamiak.anothermoviesearchapp.utils.ApiUtils.isUserOnline
import jw.adamiak.anothermoviesearchapp.utils.UiUtils.showAlert
import jw.adamiak.anothermoviesearchapp.utils.UiUtils.showSnackbar

@AndroidEntryPoint
class MoviesActivity : AppCompatActivity() {

	private lateinit var bottomNav: BottomNavigationView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_movies)

		if(!allPermissionsGranted(this)){
			ApiUtils.requestPermissions(this)
		}
		if(!isUserOnline(this)){
			showSnackbar(window.decorView.rootView, "No Internet connection")
		}

		val navigation = Navigation.findNavController(this, R.id.nav_host_fragment)
		bottomNav = findViewById(R.id.bottom_navigation)
		NavigationUI.setupWithNavController(bottomNav, navigation)
	}
}