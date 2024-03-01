package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.entity.AppException
import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.VoidResult
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
class DeleteCityWeatherUseCaseTest: BaseUseCaseTest() {

  private lateinit var deleteCityWeatherUseCase: DeleteCityWeatherUseCase

  private val cityWeatherEntity = CityWeatherEntity(1, 101, "ID", "Jakarta", 1.0, 1.0)

  @Before
  fun setup() {
    deleteCityWeatherUseCase = DeleteCityWeatherUseCaseImpl(appRepository)
  }

  @Test
  fun `delete city weather successfully`() = runTest {
    whenever(appRepository.deleteCityWeather(cityWeatherEntity)).then {}

    val expected = VoidResult.Success
    val actual = deleteCityWeatherUseCase(cityWeatherEntity)

    assertTrue(expected == actual)
  }

  @Test
  fun `fail to delete city weather`() = runTest {
    val msg = "this is an error message"

    whenever(appRepository.deleteCityWeather(cityWeatherEntity)).then {
      throw AppException(msg)
    }

    val expected = VoidResult.Error(msg)
    val actual = deleteCityWeatherUseCase(cityWeatherEntity)

    assertTrue(expected == actual)
  }
}
