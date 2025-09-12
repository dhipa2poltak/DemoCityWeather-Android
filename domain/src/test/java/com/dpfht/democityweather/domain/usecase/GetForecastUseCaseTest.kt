package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.model.AppException
import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.ForecastModel
import com.dpfht.democityweather.domain.model.Forecast
import com.dpfht.democityweather.domain.model.Main
import com.dpfht.democityweather.domain.model.Result
import com.dpfht.democityweather.domain.model.Result.Success
import com.dpfht.democityweather.domain.model.Weather
import com.dpfht.democityweather.domain.model.Wind
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class GetForecastUseCaseTest: BaseUseCaseTest() {

  private lateinit var getForecastUseCase: GetForecastUseCase

  private val cityWeatherEntity = CityWeather(1, 101, "ID", "Jakarta", 1.0, 1.0)
  private val weatherEntity = Weather(101, "main", "desc", "icon")
  private val forecastEntity = Forecast(100000, Main(), listOf(weatherEntity), Wind(), "2024-01-20 05:30:45")
  private val forecast = ForecastModel(listOf(forecastEntity))

  private val msg = "this is an error message"

  @Before
  fun setup() {
    getForecastUseCase = GetForecastUseCaseImpl(appRepository)
  }

  @Test
  fun `get forecast successfully`() = runTest {
    whenever(appRepository.getForecast(cityWeatherEntity)).thenReturn(forecast)

    var isError = true
    when (getForecastUseCase(cityWeatherEntity)) {
      is Success -> {
        isError = false
      }
      else -> {}
    }

    assertFalse(isError)
  }

  @Test
  fun `fail to get forecast because AppException is thrown`() = runTest {
    whenever(appRepository.getForecast(cityWeatherEntity)).then {
      throw AppException(msg)
    }

    val expected = Result.Error(msg)
    val actual = getForecastUseCase(cityWeatherEntity)

    assertTrue(expected == actual)
  }

  @Test
  fun `fail to get forecast because Exception is thrown`() = runTest {
    whenever(appRepository.getForecast(cityWeatherEntity)).then {
      throw Exception()
    }
    whenever(appRepository.getLocalMessage(any())).thenReturn(msg)

    val expected = Result.Error(msg)
    val actual = getForecastUseCase(cityWeatherEntity)

    assertTrue(expected == actual)
  }
}
