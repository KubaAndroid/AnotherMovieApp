package jw.adamiak.anothermoviesearchapp.data.api

import okhttp3.Interceptor
import okhttp3.Response

class HttpInterceptor: Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response {
		val request = chain.request()
			.newBuilder()
			.addHeader("Content-Type", "application/json;charset=utf-8")
			.build()

		return chain.proceed(request)
	}
}