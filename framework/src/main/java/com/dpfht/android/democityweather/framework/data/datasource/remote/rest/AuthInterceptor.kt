package com.dpfht.android.democityweather.framework.data.datasource.remote.rest

import com.dpfht.android.democityweather.framework.Constants
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class AuthInterceptor: Interceptor {
  override fun intercept(chain: Chain): Response {

    val original = chain.request()
    val originalHttpUrl = original.url

    if (originalHttpUrl.toString().startsWith(Constants.BASE_URL)) {
      val url = originalHttpUrl.newBuilder()
        .addQueryParameter("appid", Constants.API_KEY)
        .addQueryParameter("units", "metric")
        .build()

      val requestBuilder = original.newBuilder()
        .url(url)
        .method(original.method, original.body)

      val request = requestBuilder.build()

      return chain.proceed(request)
    }

    return chain.proceed(original)
  }
}
