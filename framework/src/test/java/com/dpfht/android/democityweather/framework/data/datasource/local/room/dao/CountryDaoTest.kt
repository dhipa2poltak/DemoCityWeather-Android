package com.dpfht.android.democityweather.framework.data.datasource.local.room.dao

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.dpfht.android.democityweather.framework.data.datasource.local.room.db.AppDB
import com.dpfht.android.democityweather.framework.data.datasource.local.room.model.CountryDBModel
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
class CountryDaoTest {

  private lateinit var appDb: AppDB
  private lateinit var countryDao: CountryDao

  private val countryCode = "ID"
  private val countryName = "Indonesia"
  private val countryModel = CountryDBModel(1, countryCode, countryName)
  private val listModels = listOf(countryModel)

  @Before
  fun setup() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    appDb = AppDB.getDatabase(context, GlobalScope, true)
    countryDao = appDb.countryDao()
  }

  @After
  fun cleanup() {
    appDb.close()
  }

  @Test
  fun `ensure insertCountry and getCountry methods are called successfully`() = runTest {
    countryDao.insertCountry(countryModel)

    val expected = listModels
    val actual = withContext(Dispatchers.IO) { countryDao.getCountry(countryCode) }

    assertTrue(expected == actual)
  }

  @Test
  fun `ensure insertCountry and getAllCountry methods are called successfully`() = runTest {
    countryDao.insertCountry(countryModel)

    val expected = listModels
    val actual = withContext(Dispatchers.IO) { countryDao.getAllCountry() }

    assertTrue(expected == actual)
  }
}
