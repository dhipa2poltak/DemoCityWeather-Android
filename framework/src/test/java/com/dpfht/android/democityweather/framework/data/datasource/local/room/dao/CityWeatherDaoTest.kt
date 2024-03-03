package com.dpfht.android.democityweather.framework.data.datasource.local.room.dao

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.dpfht.android.democityweather.framework.data.datasource.local.room.db.AppDB
import com.dpfht.android.democityweather.framework.data.datasource.local.room.model.CityWeatherDBModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class CityWeatherDaoTest {

  private lateinit var appDb: AppDB
  private lateinit var cityWeatherDao: CityWeatherDao

  private val weatherModel1 = CityWeatherDBModel(1, 101, "ID", "Jakarta", 1.0, 1.0)
  private val weatherModel2 = CityWeatherDBModel(2, 102, "ID", "Bogor", 2.0, 2.0)
  private val weatherModel3 = CityWeatherDBModel(3, 103, "ID", "Depok", 3.0, 3.0)
  private val listModels = listOf(weatherModel1, weatherModel2, weatherModel3)

  @Before
  fun setup() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    appDb = AppDB.getDatabase(context, GlobalScope, true)
    cityWeatherDao = appDb.cityWeatherDao()
  }

  @After
  fun cleanup() {
    appDb.close()
  }

  @Test
  fun `ensure insertCityWeather and getAllCityWeather methods are called successfully`() = runTest {
    cityWeatherDao.insertCityWeather(weatherModel1)
    cityWeatherDao.insertCityWeather(weatherModel2)
    cityWeatherDao.insertCityWeather(weatherModel3)

    val expected = listModels
    val actual = withContext(Dispatchers.IO) { cityWeatherDao.getAllCityWeather() }

    assertTrue(expected == actual)
  }

  @Test
  fun `ensure insertCityWeather, deleteCityWeather and getAllCityWeather methods are called successfully`() = runTest {
    cityWeatherDao.insertCityWeather(weatherModel1)
    cityWeatherDao.insertCityWeather(weatherModel2)
    cityWeatherDao.insertCityWeather(weatherModel3)

    val list = withContext(Dispatchers.IO) {
      cityWeatherDao.deleteCityWeather(weatherModel2.id ?: -1)
      cityWeatherDao.getAllCityWeather()
    }

    val expected = 2
    val actual = list.size

    assertTrue(expected == actual)
  }
}
