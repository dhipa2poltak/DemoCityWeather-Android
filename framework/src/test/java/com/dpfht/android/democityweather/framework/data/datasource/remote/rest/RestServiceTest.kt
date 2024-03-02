package com.dpfht.android.democityweather.framework.data.datasource.remote.rest

import android.net.Uri
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RestServiceTest {

  private lateinit var mockWebServer: MockWebServer
  private lateinit var restService: RestService
  private lateinit var gson: Gson

  @Before
  fun setup() {
    gson = Gson()
    mockWebServer = MockWebServer()

    restService = Retrofit.Builder()
      .baseUrl(mockWebServer.url("/"))
      .addConverterFactory(GsonConverterFactory.create(gson))
      .client(UnsafeOkHttpClient.getUnsafeOkHttpClient())
      .build().create(RestService::class.java)

    val mockResponse = MockResponse()
    mockWebServer.enqueue(mockResponse.setBody("{}"))
  }

  @Test
  fun `ensure path and parameter(s) in the generated URL are correct when calling getCurrentWeather method in RestService`() = runTest {
    val lat = 1.0
    val lng = 1.0

    restService.getCurrentWeather(lat, lng)
    val request = mockWebServer.takeRequest()

    val uri = Uri.parse(request.requestUrl.toString())

    assertEquals("/weather", uri.path)
    assertTrue(uri.queryParameterNames.contains("lat"))
    assertTrue(uri.getQueryParameter("lat") == "$lat")
    assertTrue(uri.queryParameterNames.contains("lon"))
    assertTrue(uri.getQueryParameter("lon") == "$lng")
  }

  @Test
  fun `ensure path and parameter(s) in the generated URL are correct when calling getForecast method in RestService`() = runTest {
    val lat = 1.0
    val lng = 1.0

    restService.getForecast(lat, lng)
    val request = mockWebServer.takeRequest()

    val uri = Uri.parse(request.requestUrl.toString())

    assertEquals("/forecast", uri.path)
    assertTrue(uri.queryParameterNames.contains("lat"))
    assertTrue(uri.getQueryParameter("lat") == "$lat")
    assertTrue(uri.queryParameterNames.contains("lon"))
    assertTrue(uri.getQueryParameter("lon") == "$lng")
  }
}
