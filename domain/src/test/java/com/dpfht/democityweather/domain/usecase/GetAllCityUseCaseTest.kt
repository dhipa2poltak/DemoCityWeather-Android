package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.entity.AppException
import com.dpfht.democityweather.domain.entity.CityEntity
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
class GetAllCityUseCaseTest: BaseUseCaseTest() {

  private lateinit var getAllCityUseCase: GetAllCityUseCase

  private val cityEntity1 = CityEntity(101, "ID", "Jakarta", 1.0, 1.0)
  private val cityEntity2 = CityEntity(102, "ID", "Bogor", 2.0, 2.0)
  private val list = listOf(cityEntity1, cityEntity2)

  @Before
  fun setup() {
    getAllCityUseCase = GetAllCityUseCaseImpl(appRepository)
  }

  @Test
  fun `get all city successfully`() = runTest {
    whenever(appRepository.getAllCity()).thenReturn(list)

    val expected = Result.Success(list)
    val actual = getAllCityUseCase()

    assertTrue(expected == actual)
  }

  @Test
  fun `fail to get all city`() = runTest {
    val msg = "this is an error message"

    whenever(appRepository.getAllCity()).then {
      throw AppException(msg)
    }

    val expected = Result.Error(msg)
    val actual = getAllCityUseCase()

    assertTrue(expected == actual)
  }
}
