package com.dpfht.android.democityweather.framework.data.datasource.remote

import android.content.Context
import com.dpfht.android.democityweather.framework.data.datasource.remote.rest.RestService
import com.dpfht.democityweather.data.datasource.RemoteDataSource
import com.dpfht.democityweather.data.model.remote.response.CountryInfoResponse
import com.dpfht.democityweather.data.model.remote.response.CountryNameDto
import com.dpfht.democityweather.data.model.remote.response.CurrentWeatherResponse
import com.dpfht.democityweather.data.model.remote.response.ForecastResponse
import com.dpfht.democityweather.data.model.remote.response.MainDto
import com.dpfht.democityweather.data.model.remote.response.WindDto
import com.dpfht.democityweather.domain.model.AppException
import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.Country
import com.dpfht.democityweather.domain.model.CurrentWeatherModel
import com.dpfht.democityweather.domain.model.ForecastModel
import com.dpfht.democityweather.domain.model.Main
import com.dpfht.democityweather.domain.model.Wind
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RemoteDataSourceTest {

  private lateinit var remoteDataSource: RemoteDataSource

  @Mock
  private lateinit var context: Context

  @Mock
  private lateinit var restService: RestService

  private val countryCode = "ID"
  private val countryName = "Indonesia"

  private val lat = 1.0
  private val lng = 1.0
  private val cityWeatherEntity = CityWeather(1, 101, "ID", "Jakarta", lat, lng)

  private val msg = "this is an error message"

  @Before
  fun setup() {
    remoteDataSource = RemoteDataSourceImpl(context, restService)
  }

  @Test
  fun `get country info successfully`() = runTest {
    val expected = Country(countryCode, countryName)

    val country = CountryInfoResponse(CountryNameDto(countryName, countryName))
    whenever(restService.getCountry(countryCode)).thenReturn(listOf(country))

    val actual = remoteDataSource.getCountryInfo(countryCode).firstOrNull()

    assertTrue(expected == actual)
  }

  @Test
  fun `fail to get country info`() = runTest {
    whenever(restService.getCountry(countryCode)).then {
      throw Exception()
    }
    whenever(context.getString(anyInt())).thenReturn(msg)

    val expected = msg
    var actual: String? = null
    try {
      remoteDataSource.getCountryInfo(countryCode)
    } catch (e: AppException) {
      actual = e.message
    }

    assertTrue(actual != null)
    assertTrue(expected == actual)
  }

  @Test
  fun `get current weather successfully`() = runTest {
    val name = "Jakarta"
    val response = CurrentWeatherResponse(listOf(), MainDto(), WindDto(), name)
    whenever(restService.getCurrentWeather(lat, lng)).thenReturn(response)

    val expected = CurrentWeatherModel(listOf(), Main(), Wind(), name)
    val actual = remoteDataSource.getCurrentWeather(cityWeatherEntity)

    assertTrue(expected == actual)
  }

  @Test
  fun `fail to get current weather`() = runTest {
    whenever(restService.getCurrentWeather(lat, lng)).then {
      throw Exception()
    }
    whenever(context.getString(anyInt())).thenReturn(msg)

    val expected = msg
    var actual: String? = null
    try {
      remoteDataSource.getCurrentWeather(cityWeatherEntity)
    } catch (e: AppException) {
      actual = e.message
    }

    assertTrue(actual != null)
    assertTrue(expected == actual)
  }

  @Test
  fun `get forecast successfully`() = runTest {
    val response = ForecastResponse("cod", 1, 1, listOf())
    whenever(restService.getForecast(lat, lng)).thenReturn(response)

    val expected = ForecastModel(listOf())
    val actual = remoteDataSource.getForecast(cityWeatherEntity)

    assertTrue(expected == actual)
  }

  @Test
  fun `fail to get forecast`() = runTest {
    whenever(restService.getForecast(lat, lng)).then {
      throw Exception()
    }
    whenever(context.getString(anyInt())).thenReturn(msg)

    val expected = msg
    var actual: String? = null
    try {
      remoteDataSource.getForecast(cityWeatherEntity)
    } catch (e: AppException) {
      actual = e.message
    }

    assertTrue(actual != null)
    assertTrue(expected == actual)
  }
}
