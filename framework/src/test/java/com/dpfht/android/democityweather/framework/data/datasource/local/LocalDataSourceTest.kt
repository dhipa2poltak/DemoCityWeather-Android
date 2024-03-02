package com.dpfht.android.democityweather.framework.data.datasource.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.dpfht.android.democityweather.framework.data.datasource.local.room.dao.CityDao
import com.dpfht.android.democityweather.framework.data.datasource.local.room.dao.CityWeatherDao
import com.dpfht.android.democityweather.framework.data.datasource.local.room.dao.CountryDao
import com.dpfht.android.democityweather.framework.data.datasource.local.room.db.AppDB
import com.dpfht.android.democityweather.framework.data.datasource.local.room.model.CityDBModel
import com.dpfht.android.democityweather.framework.data.datasource.local.room.model.CityWeatherDBModel
import com.dpfht.android.democityweather.framework.data.datasource.local.room.model.CountryDBModel
import com.dpfht.android.democityweather.framework.data.datasource.local.room.model.toDomain
import com.dpfht.democityweather.data.datasource.LocalDataSource
import com.dpfht.democityweather.domain.entity.AppException
import com.dpfht.democityweather.domain.entity.CityEntity
import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.CountryEntity
import com.dpfht.democityweather.domain.entity.LocalMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class LocalDataSourceTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  private lateinit var localDataSource: LocalDataSource

  private lateinit var context: Context
  private lateinit var appDb: AppDB

  private lateinit var cityDao: CityDao
  private lateinit var countryDao: CountryDao
  private lateinit var cityWeatherDao: CityWeatherDao

  private val countryCode = "ID"
  private val countryName = "Indonesia"
  private val countryEntity = CountryEntity(countryCode, countryName)

  private val cityEntity = CityEntity(101, "ID", "Jakarta", 1.0, 1.0)
  private val cityWeatherEntity = CityWeatherEntity(1, 101, "ID", "Jakarta", 1.0, 1.0)

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)

    context = ApplicationProvider.getApplicationContext()
    appDb = spy(AppDB.getDatabase(context, GlobalScope, true))

    cityDao = mock()
    whenever(appDb.cityDao()).thenReturn(cityDao)

    countryDao = mock()
    whenever(appDb.countryDao()).thenReturn(countryDao)

    cityWeatherDao = mock()
    whenever(appDb.cityWeatherDao()).thenReturn(cityWeatherDao)

    localDataSource = LocalDataSourceImpl(context, appDb)
  }

  @After
  fun cleanup() {
    appDb.close()
  }

  @Test
  fun `call getStreamIsDBInitialized method successfully`() = runTest {
    var isInit: Boolean? = null
    val disposable = localDataSource
      .getStreamIsDBInitialized()
      .subscribe {
        isInit = it
      }

    assertTrue(isInit != null)

    disposable.dispose()
  }

  @Test
  fun `get all city successfully`() = runTest {
    val model1 = CityDBModel(1, 101, countryCode, "Jakarta", 1.0, 1.0)
    val model2 = CityDBModel(2, 102, countryCode, "Bogor", 2.0, 2.0)
    val model3 = CityDBModel(3, 103, countryCode, "Depok", 3.0, 3.0)
    val listModels = listOf(model1, model2, model3)

    whenever(cityDao.getAllCity()).thenReturn(listModels)

    val expected = listModels.map { it.toDomain() }
    val actual = localDataSource.getAllCity()

    assertTrue(expected == actual)
  }

  @Test
  fun `fail to get all city`() = runTest {
    whenever(cityDao.getAllCity()).then {
      throw Exception()
    }

    var actual: String? = null
    try {
      localDataSource.getAllCity()
    } catch (e: AppException) {
      actual = e.message
    }

    assertTrue(actual != null)
    assertTrue(actual?.isNotEmpty() == true)
  }

  @Test
  fun `get country successfully`() = runTest {
    val model = CountryDBModel(101, countryCode, countryName)
    val list = listOf(model)

    whenever(countryDao.getCountry(countryCode)).thenReturn(list)

    val expected = list.map { it.toDomain() }
    val actual = localDataSource.getCountry(countryCode)

    assertTrue(expected == actual)
  }

  @Test
  fun `fail to get country`() = runTest {
    whenever(countryDao.getCountry(countryCode)).then {
      throw Exception()
    }

    var actual: String? = null
    try {
      localDataSource.getCountry(countryCode)
    } catch (e: AppException) {
      actual = e.message
    }

    assertTrue(actual != null)
    assertTrue(actual?.isNotEmpty() == true)
  }

  @Test
  fun `save country successfully`() = runTest {
    whenever(countryDao.insertCountry(any())).then {}

    var isError = true
    try {
      localDataSource.saveCountry(countryEntity)
      isError = false
    } catch (_: AppException) {}

    assertFalse(isError)
  }

  @Test
  fun `fail to save country`() = runTest {
    val countryDBModel = CountryDBModel(countryCode = countryEntity.countryCode, countryName = countryEntity.countryName)
    whenever(countryDao.insertCountry(countryDBModel)).then {
      throw Exception()
    }

    var actual: String? = null
    try {
      localDataSource.saveCountry(countryEntity)
    } catch (e: AppException) {
      actual = e.message
    }

    assertTrue(actual != null)
    assertTrue(actual?.isNotEmpty() == true)
  }

  @Test
  fun `get all city weather successfully`() = runTest {
    val model = CityWeatherDBModel(1, 101, "ID", "Jakarta", 1.0, 1.0)
    val list = listOf(model)

    whenever(cityWeatherDao.getAllCityWeather()).thenReturn(list)

    val expected = list.map { it.toDomain() }
    val actual = localDataSource.getAllCityWeather()

    assertTrue(expected == actual)
  }

  @Test
  fun `fail to get all city weather`() = runTest {
    whenever(cityWeatherDao.getAllCityWeather()).then {
      throw Exception()
    }

    var actual: String? = null
    try {
      localDataSource.getAllCityWeather()
    } catch (e: AppException) {
      actual = e.message
    }

    assertTrue(actual != null)
    assertTrue(actual?.isNotEmpty() == true)
  }

  @Test
  fun `add city weather successfully`() = runTest {
    val newId = 1L
    val dbModel = CityWeatherDBModel(
      idCity = cityEntity.idCity,
      countryCode = cityEntity.countryCode,
      cityName = cityEntity.cityName,
      lat = cityEntity.lat,
      lon = cityEntity.lon
    )

    whenever(cityWeatherDao.insertCityWeather(dbModel)).thenReturn(newId)

    val expected = dbModel.copy(id = newId).toDomain()
    val actual = localDataSource.addCityWeather(cityEntity)

    assertTrue(expected == actual)
  }

  @Test
  fun `fail to add city weather`() = runTest {
    whenever(cityWeatherDao.insertCityWeather(any())).then {
      throw Exception()
    }

    var actual: String? = null
    try {
      localDataSource.addCityWeather(cityEntity)
    } catch (e: AppException) {
      actual = e.message
    }

    assertTrue(actual != null)
    assertTrue(actual?.isNotEmpty() == true)
  }

  @Test
  fun `delete city weather successfully`() = runTest {
    whenever(cityWeatherDao.deleteCityWeather(cityWeatherEntity.id)).then {}

    var isError = true
    try {
      localDataSource.deleteCityWeather(cityWeatherEntity)
      isError = false
    } catch (_: AppException) {}

    assertFalse(isError)
  }

  @Test
  fun `fail to delete city weather`() = runTest {
    whenever(cityWeatherDao.deleteCityWeather(cityWeatherEntity.id)).then {
      throw Exception()
    }

    var actual: String? = null
    try {
      localDataSource.deleteCityWeather(cityWeatherEntity)
    } catch (e: AppException) {
      actual = e.message
    }

    assertTrue(actual != null)
    assertTrue(actual?.isNotEmpty() == true)
  }

  @Test
  fun `get local message successfully`() = runTest {
    val expected = "no country found"
    val actual = localDataSource.getLocalMessage(LocalMessage.NoCountryFound)

    assertTrue(expected == actual)
  }
}
