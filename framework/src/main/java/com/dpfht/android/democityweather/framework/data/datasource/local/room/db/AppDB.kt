package com.dpfht.android.democityweather.framework.data.datasource.local.room.db

import android.content.Context
import android.content.res.AssetManager
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dpfht.android.democityweather.framework.data.datasource.local.assets.model.CityAssetModel
import com.dpfht.android.democityweather.framework.data.datasource.local.assets.model.toDBModel
import com.dpfht.android.democityweather.framework.data.datasource.local.room.dao.CityDao
import com.dpfht.android.democityweather.framework.data.datasource.local.room.dao.CityWeatherDao
import com.dpfht.android.democityweather.framework.data.datasource.local.room.dao.CountryDao
import com.dpfht.android.democityweather.framework.data.datasource.local.room.model.CityDBModel
import com.dpfht.android.democityweather.framework.data.datasource.local.room.model.CityWeatherDBModel
import com.dpfht.android.democityweather.framework.data.datasource.local.room.model.CountryDBModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

@Database(version = 1, entities = [CityDBModel::class, CityWeatherDBModel::class, CountryDBModel::class], exportSchema = false)
abstract class AppDB: RoomDatabase() {

  abstract fun cityDao(): CityDao
  abstract fun cityWeatherDao(): CityWeatherDao
  abstract fun countryDao(): CountryDao

  private class AppDBCallback(
    private val scope: CoroutineScope,
    private val assetManager: AssetManager
  ) : Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
      super.onCreate(db)
      INSTANCE?.let { database ->
        scope.launch {
          val cityDao = database.cityDao()
          prePopulateDatabase(cityDao)
        }
      }
    }

    override fun onOpen(db: SupportSQLiteDatabase) {
      super.onOpen(db)
      rawIsDBInitialized.onNext(true)
    }

    private suspend fun prePopulateDatabase(cityDao: CityDao) {
      var text = ""

      var reader: BufferedReader? = null
      try {
        reader = BufferedReader(InputStreamReader(assetManager.open("id.city.list.json")))

        var mLine = reader.readLine()
        while (mLine != null) {
          text += mLine
          mLine = reader.readLine()
        }
      } catch (e: Exception) {
        e.printStackTrace()
      } finally {
        if (reader != null) {
          try {
            reader.close()
          } catch (e: IOException) {
            e.printStackTrace()
          }
        }
      }

      val typeTokenCity = object : TypeToken<List<CityAssetModel>>() {}.type
      val citiesAsset = Gson().fromJson<List<CityAssetModel>>(text, typeTokenCity)
      val cities = citiesAsset.map { it.toDBModel() }
      cityDao.insertCities(cities)
    }
  }

  companion object {
    private var INSTANCE: AppDB? = null

    private val rawIsDBInitialized = BehaviorSubject.createDefault(false)
    val obsIsDBInitialized: Observable<Boolean> = rawIsDBInitialized

    fun getDatabase(context: Context, coroutineScope: CoroutineScope): AppDB {
      val tempInstance = INSTANCE
      if (tempInstance != null) {
        return tempInstance
      }

      synchronized(this) {
        val instance = Room.databaseBuilder(context.applicationContext,
          AppDB::class.java,
          "demo_city_weather_database")
          .addCallback(AppDBCallback(coroutineScope, context.assets))
          .build()
        INSTANCE = instance
        return instance
      }
    }
  }
}
