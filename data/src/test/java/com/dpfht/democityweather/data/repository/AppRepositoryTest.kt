package com.dpfht.democityweather.data.repository

import com.dpfht.democityweather.data.datasource.LocalDataSource
import com.dpfht.democityweather.data.datasource.RemoteDataSource
import com.dpfht.democityweather.domain.entity.AppException
import com.dpfht.democityweather.domain.entity.CityEntity
import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.CountryEntity
import com.dpfht.democityweather.domain.entity.CurrentWeatherDomain
import com.dpfht.democityweather.domain.entity.ForecastDomain
import com.dpfht.democityweather.domain.entity.LocalMessage
import com.dpfht.democityweather.domain.entity.MainEntity
import com.dpfht.democityweather.domain.entity.WindEntity
import com.dpfht.democityweather.domain.repository.AppRepository
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.spy
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class AppRepositoryTest {

  private lateinit var appRepository: AppRepository

  @Mock
  private lateinit var localDataSource: LocalDataSource

  @Mock
  private lateinit var remoteDataSource: RemoteDataSource

  private val countryCode = "ID"
  private val countryName = "Indonesia"
  private val countryEntity = CountryEntity(countryCode, countryName)

  private val cityWeatherEntity1 = CityWeatherEntity(1, 101, "ID", "Jakarta", 1.0, 1.0)
  private val cityWeatherEntity2 = CityWeatherEntity(2, 102, "ID", "Bogor", 2.0, 2.0)
  private val list = listOf(cityWeatherEntity1, cityWeatherEntity2)

  private val cityEntity = CityEntity(101, "ID", "Jakarta", 1.0, 1.0)

  private val msg = "this is an error message"

  @Before
  fun setup() {
    appRepository = spy(AppRepositoryImpl(localDataSource, remoteDataSource))
  }

  @Test
  fun `get all city successfully`() = runTest {
    val cityEntity1 = CityEntity(101, "ID", "Jakarta", 1.0, 1.0)
    val cityEntity2 = CityEntity(102, "ID", "Bogor", 2.0, 2.0)
    val list = listOf(cityEntity1, cityEntity2)

    whenever(localDataSource.getAllCity()).thenReturn(list)

    val actual = appRepository.getAllCity()

    assertTrue(list == actual)
  }

  @Test
  fun `fail to get all city`() = runTest {
    whenever(localDataSource.getAllCity()).then {
      throw AppException(msg)
    }

    var actual: String? = null
    try {
      appRepository.getAllCity()
    } catch (e: AppException) {
      actual = e.message
    }

    assertTrue(actual != null)
    assertTrue(msg == actual)
  }

  @Test
  fun `get country from localDataSource successfully`() = runTest {
    whenever(localDataSource.getCountry(countryCode)).thenReturn(listOf(countryEntity))

    val expected = countryEntity
    val actual = appRepository.getCountry(countryCode)

    assertTrue(expected == actual)
  }

  @Test
  fun `get country from remoteDataSource successfully`() = runTest {
    whenever(localDataSource.getCountry(countryCode)).thenReturn(listOf())
    whenever(remoteDataSource.getCountryInfo(countryCode)).thenReturn(listOf(countryEntity))

    val expected = countryEntity
    val actual = appRepository.getCountry(countryCode)

    assertTrue(expected == actual)
  }

  @Test
  fun `fail to get country`() = runTest {
    whenever(localDataSource.getCountry(countryCode)).thenReturn(listOf())
    whenever(remoteDataSource.getCountryInfo(countryCode)).thenReturn(listOf())
    whenever(appRepository.getLocalMessage(LocalMessage.NoCountryFound)).thenReturn(msg)

    var actual: String? = null
    try {
      appRepository.getCountry(countryCode)
    } catch (e: AppException) {
      actual = e.message
    }

    assertTrue(actual != null)
    assertTrue(msg == actual)
  }

  @Test
  fun `save country successfully`() = runTest {
    whenever(localDataSource.saveCountry(countryEntity)).then {}

    var isError = true
    try {
      appRepository.saveCountry(countryEntity)
      isError = false
    } catch (_: AppException) {}

    assertFalse(isError)
  }

  @Test
  fun `fail to save country`() = runTest {
    whenever(localDataSource.saveCountry(countryEntity)).then {
      throw AppException(msg)
    }

    var actual: String? = null
    try {
      appRepository.saveCountry(countryEntity)
    } catch (e: AppException) {
      actual = e.message
    }

    assertTrue(actual != null)
    assertTrue(msg == actual)
  }

  @Test
  fun `calling getStreamIsDBInitialized method in appRepository is succeeded`() {
    val obs = Observable.just(true)
    whenever(localDataSource.getStreamIsDBInitialized()).thenReturn(obs)

    val actual = appRepository.getStreamIsDBInitialized()

    assertTrue(obs == actual)
  }

  @Test
  fun `get all city weather successfully`() = runTest {
    whenever(localDataSource.getAllCityWeather()).thenReturn(list)

    val expected = list
    val actual = appRepository.getAllCityWeather()

    assertTrue(expected == actual)
  }

  @Test
  fun `fail to get all city weather`() = runTest {
    whenever(localDataSource.getAllCityWeather()).then {
      throw AppException(msg)
    }

    var actual: String? = null
    try {
      appRepository.getAllCityWeather()
    } catch (e: AppException) {
      actual = e.message
    }

    assertTrue(actual != null)
    assertTrue(msg == actual)
  }

  @Test
  fun `add city weather successfully`() = runTest {
    whenever(localDataSource.addCityWeather(cityEntity)).then {}

    var isError = true
    try {
      appRepository.addCityWeather(cityEntity)
      isError = false
    } catch (_: AppException) {}

    assertFalse(isError)
  }

  @Test
  fun `fail to add city weather`() = runTest {
    whenever(localDataSource.addCityWeather(cityEntity)).then {
      throw AppException(msg)
    }

    var actual: String? = null
    try {
      appRepository.addCityWeather(cityEntity)
    } catch (e: AppException) {
      actual = e.message
    }

    assertTrue(actual != null)
    assertTrue(msg == actual)
  }

  @Test
  fun `delete city weather successfully`() = runTest {
    whenever(localDataSource.deleteCityWeather(cityWeatherEntity1)).then {}

    var isError = true
    try {
      appRepository.deleteCityWeather(cityWeatherEntity1)
      isError = false
    } catch (_: AppException) {}

    assertFalse(isError)
  }

  @Test
  fun `fail to delete city weather`() = runTest {
    whenever(localDataSource.deleteCityWeather(cityWeatherEntity1)).then {
      throw AppException(msg)
    }

    var actual: String? = null
    try {
      appRepository.deleteCityWeather(cityWeatherEntity1)
    } catch (e: AppException) {
      actual = e.message
    }

    assertTrue(actual != null)
    assertTrue(msg == actual)
  }

  @Test
  fun `get current weather successfully`() = runTest {
    val weather = CurrentWeatherDomain(listOf(), MainEntity(), WindEntity(), "name")
    whenever(remoteDataSource.getCurrentWeather(cityWeatherEntity1)).thenReturn(weather)

    val actual = appRepository.getCurrentWeather(cityWeatherEntity1)

    assertTrue(weather == actual)
  }

  @Test
  fun `fail to get current weather`() = runTest {
    whenever(remoteDataSource.getCurrentWeather(cityWeatherEntity1)).then {
      throw AppException(msg)
    }

    var actual: String? = null
    try {
      appRepository.getCurrentWeather(cityWeatherEntity1)
    } catch (e: AppException) {
      actual = e.message
    }

    assertTrue(actual != null)
    assertTrue(msg == actual)
  }

  @Test
  fun `get forecast successfully`() = runTest {
    val forecast = ForecastDomain(listOf())
    whenever(remoteDataSource.getForecast(cityWeatherEntity1)).thenReturn(forecast)

    val actual = appRepository.getForecast(cityWeatherEntity1)

    assertTrue(forecast == actual)
  }

  @Test
  fun `fail to get forecast`() = runTest {
    whenever(remoteDataSource.getForecast(cityWeatherEntity1)).then {
      throw AppException(msg)
    }

    var actual: String? = null
    try {
      appRepository.getForecast(cityWeatherEntity1)
    } catch (e: AppException) {
      actual = e.message
    }

    assertTrue(actual != null)
    assertTrue(msg == actual)
  }

  @Test
  fun `get local message successfully`() = runTest {
    val retMessage = "this is a message"
    whenever(localDataSource.getLocalMessage(LocalMessage.NoCountryFound)).thenReturn(retMessage)

    val actual = appRepository.getLocalMessage(LocalMessage.NoCountryFound)

    assertTrue(retMessage == actual)
  }
}
