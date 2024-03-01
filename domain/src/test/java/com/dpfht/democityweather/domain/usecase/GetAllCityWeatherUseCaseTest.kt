package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.entity.AppException
import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.Result
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
class GetAllCityWeatherUseCaseTest: BaseUseCaseTest() {

  private lateinit var getAllCityWeatherUseCase: GetAllCityWeatherUseCase

  private val cityWeatherEntity1 = CityWeatherEntity(1, 101, "ID", "Jakarta", 1.0, 1.0)
  private val cityWeatherEntity2 = CityWeatherEntity(2, 102, "ID", "Bogor", 2.0, 2.0)
  private val list = listOf(cityWeatherEntity1, cityWeatherEntity2)

  @Before
  fun setup() {
    getAllCityWeatherUseCase = GetAllCityWeatherUseCaseImpl(appRepository)
  }

  @Test
  fun `get all city weather successfully`() = runTest {
    whenever(appRepository.getAllCityWeather()).thenReturn(list)

    val expected = Result.Success(list)
    val actual = getAllCityWeatherUseCase()

    assertTrue(expected == actual)
  }

  @Test
  fun `fail to get all city weather`() = runTest {
    val msg = "this is an error message"

    whenever(appRepository.getAllCityWeather()).then {
      throw AppException(msg)
    }

    val expected = Result.Error(msg)
    val actual = getAllCityWeatherUseCase()

    assertTrue(expected == actual)
  }
}
