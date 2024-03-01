package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.entity.AppException
import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.ForecastDomain
import com.dpfht.democityweather.domain.entity.ForecastEntity
import com.dpfht.democityweather.domain.entity.MainEntity
import com.dpfht.democityweather.domain.entity.Result
import com.dpfht.democityweather.domain.entity.Result.Success
import com.dpfht.democityweather.domain.entity.WeatherEntity
import com.dpfht.democityweather.domain.entity.WindEntity
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

  private val cityWeatherEntity = CityWeatherEntity(1, 101, "ID", "Jakarta", 1.0, 1.0)
  private val weatherEntity = WeatherEntity(101, "main", "desc", "icon")
  private val forecastEntity = ForecastEntity(100000, MainEntity(), listOf(weatherEntity), WindEntity(), "2024-01-20 05:30:45")
  private val forecast = ForecastDomain(listOf(forecastEntity))

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
