package jw.adamiak.anothermoviesearchapp.data.api

import jw.adamiak.anothermoviesearchapp.utils.ApiUtils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
	private val httpClient = OkHttpClient.Builder().apply {
		addInterceptor(HttpInterceptor())
	}.build()

	private val retrofit by lazy {
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.client(httpClient)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}

	val api: TheMovieDbApi by lazy {
		retrofit.create(TheMovieDbApi::class.java)
	}
}