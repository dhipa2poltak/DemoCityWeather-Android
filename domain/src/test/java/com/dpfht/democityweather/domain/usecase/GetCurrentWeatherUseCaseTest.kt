package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.model.AppException
import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.CurrentWeatherModel
import com.dpfht.democityweather.domain.model.Main
import com.dpfht.democityweather.domain.model.Result
import com.dpfht.democityweather.domain.model.Wind
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class GetCurrentWeatherUseCaseTest: BaseUseCaseTest() {

  private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

  private val cityWeatherEntity = CityWeather(1, 101, "ID", "Jakarta", 1.0, 1.0)
  private val currentWeather = CurrentWeatherModel(listOf(), Main(), Wind(), "name")

  @Before
  fun setup() {
    getCurrentWeatherUseCase = GetCurrentWeatherUseCaseImpl(appRepository)
  }

  @Test
  fun `get current weather successfully`() = runTest {
    whenever(appRepository.getCurrentWeather(cityWeatherEntity)).thenReturn(currentWeather)

    val expected = Result.Success(currentWeather)
    val actual = getCurrentWeatherUseCase(cityWeatherEntity)

    assertTrue(expected == actual)
  }

  @Test
  fun `fail to get current weather`() = runTest {
    val msg = "this is an error message"

    whenever(appRepository.getCurrentWeather(cityWeatherEntity)).then {
      throw AppException(msg)
    }

    val expected = Result.Error(msg)
    val actual = getCurrentWeatherUseCase(cityWeatherEntity)

    assertTrue(expected == actual)
  }
}
