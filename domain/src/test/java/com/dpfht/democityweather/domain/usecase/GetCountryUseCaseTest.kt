package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.entity.AppException
import com.dpfht.democityweather.domain.entity.CountryEntity
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
class GetCountryUseCaseTest: BaseUseCaseTest() {

  private lateinit var getCountryUseCase: GetCountryUseCase

  private val countryCode = "ID"
  private val countryName = "Indonesia"
  private val countryEntity = CountryEntity(countryCode, countryName)

  @Before
  fun setup() {
    getCountryUseCase = GetCountryUseCaseImpl(appRepository)
  }

  @Test
  fun `get country successfully`() = runTest {
    whenever(appRepository.getCountry(countryCode)).thenReturn(countryEntity)

    val expected = Result.Success(countryEntity)
    val actual = getCountryUseCase(countryCode)

    assertTrue(expected == actual)
  }

  @Test
  fun `fail to get country`() = runTest {
    val msg = "this is an error message"

    whenever(appRepository.getCountry(countryCode)).then {
      throw AppException(msg)
    }

    val expected = Result.Error(msg)
    val actual = getCountryUseCase(countryCode)

    assertTrue(expected == actual)
  }
}
