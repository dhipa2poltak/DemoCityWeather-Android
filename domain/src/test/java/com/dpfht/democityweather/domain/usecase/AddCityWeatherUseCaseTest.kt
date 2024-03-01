package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.entity.AppException
import com.dpfht.democityweather.domain.entity.CityEntity
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
class AddCityWeatherUseCaseTest: BaseUseCaseTest() {

  private lateinit var addCityWeatherUseCase: AddCityWeatherUseCase

  private val cityEntity = CityEntity(101, "ID", "Jakarta", 1.0, 1.0)
  private val cityWeatherEntity = CityWeatherEntity(1, 101, "ID", "Jakarta", 1.0, 1.0)

  @Before
  fun setup() {
    addCityWeatherUseCase = AddCityWeatherUseCaseImpl(appRepository)
  }

  @Test
  fun `add city successfully`() = runTest {
    whenever(appRepository.addCityWeather(cityEntity)).thenReturn(cityWeatherEntity)

    val expected = Result.Success(cityWeatherEntity)
    val actual = addCityWeatherUseCase(cityEntity)

    assertTrue(expected == actual)
  }

  @Test
  fun `fail to add city`() = runTest {
    val msg = "this is an error message"
    whenever(appRepository.addCityWeather(cityEntity)).then {
      throw AppException(msg)
    }

    val expected = Result.Error(msg)
    val actual = addCityWeatherUseCase(cityEntity)

    assertTrue(expected == actual)
  }
}
