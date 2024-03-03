package com.dpfht.android.democityweather.framework.data.datasource.local.room.dao

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.dpfht.android.democityweather.framework.data.datasource.local.room.db.AppDB
import com.dpfht.android.democityweather.framework.data.datasource.local.room.model.CityDBModel
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
class CityDaoTest {

  private lateinit var appDb: AppDB
  private lateinit var cityDao: CityDao

  private val countryCode = "ID"
  private val cityModel1 = CityDBModel(1, 101, countryCode, "Jakarta", 1.0, 1.0)
  private val cityModel2 = CityDBModel(2, 102, countryCode, "Bogor", 2.0, 2.0)
  private val cityModel3 = CityDBModel(3, 103, countryCode, "Depok", 3.0, 3.0)
  private val listModels = listOf(cityModel1, cityModel2, cityModel3)

  @Before
  fun setup() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    appDb = AppDB.getDatabase(context, GlobalScope, true)
    cityDao = appDb.cityDao()
  }

  @After
  fun cleanup() {
    appDb.close()
  }

  @Test
  fun `ensure insertCity and getAllCity methods are called successfully`() = runTest {
    cityDao.insertCity(cityModel1)
    cityDao.insertCity(cityModel2)
    cityDao.insertCity(cityModel3)

    val expected = listModels.size
    val actual = withContext(Dispatchers.IO) { cityDao.getAllCity() }.size

    assertTrue(expected == actual)
  }

  @Test
  fun `ensure insertCities and getAllCityByCountryCode methods are called successfully`() = runTest {
    cityDao.insertCities(listModels)

    val expected = listModels
    val actual = withContext(Dispatchers.IO) { cityDao.getAllCityByCountryCode(countryCode) }

    assertTrue(expected == actual)
  }
}
