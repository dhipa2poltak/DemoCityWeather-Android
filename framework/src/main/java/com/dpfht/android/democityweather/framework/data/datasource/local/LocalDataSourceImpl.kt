package com.dpfht.android.democityweather.framework.data.datasource.local

import android.content.Context
import com.dpfht.android.democityweather.framework.R
import com.dpfht.android.democityweather.framework.data.datasource.local.room.db.AppDB
import com.dpfht.android.democityweather.framework.data.datasource.local.room.model.CityWeatherDBModel
import com.dpfht.android.democityweather.framework.data.datasource.local.room.model.CountryDBModel
import com.dpfht.android.democityweather.framework.data.datasource.local.room.model.toDomain
import com.dpfht.democityweather.data.datasource.LocalDataSource
import com.dpfht.democityweather.domain.model.AppException
import com.dpfht.democityweather.domain.model.City
import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.Country
import com.dpfht.democityweather.domain.model.LocalMessage
import com.dpfht.democityweather.domain.model.LocalMessage.ErrorWhenGettingForecastData
import com.dpfht.democityweather.domain.model.LocalMessage.GeneralError
import com.dpfht.democityweather.domain.model.LocalMessage.NoCountryFound
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocalDataSourceImpl(
  private val context: Context,
  private val appDB: AppDB
): LocalDataSource {

  init {
    GlobalScope.launch(Dispatchers.IO) {
      appDB.cityDao().getAllCityByCountryCode("ID")
    }
  }

  override fun getStreamIsDBInitialized(): Observable<Boolean> {
    return AppDB.obsIsDBInitialized
  }

  override suspend fun getAllCity(): List<City> {
    return try {
      val list = withContext(Dispatchers.IO) {
        appDB.cityDao().getAllCity().map { it.toDomain() }
      }

      list
    } catch (e: Exception) {
      e.printStackTrace()
      throw AppException(context.getString(R.string.framework_failed_get_all_city))
    }
  }

  override suspend fun getCountry(countryCode: String): List<Country> {
    return try {
      val list = withContext(Dispatchers.IO) {
        appDB.countryDao().getCountry(countryCode).map { it.toDomain() }
      }

      list
    } catch (e: Exception) {
      e.printStackTrace()

      throw AppException(context.getString(R.string.framework_failed_to_get_country) + " $countryCode")
    }
  }

  override suspend fun saveCountry(countryEntity: Country) {
    return try {
      withContext(Dispatchers.IO) {
        val countryDBModel = CountryDBModel(countryCode = countryEntity.countryCode, countryName = countryEntity.countryName)
        appDB.countryDao().insertCountry(countryDBModel)
      }
    } catch (e: Exception) {
      e.printStackTrace()

      throw AppException(context.getString(R.string.framework_cannot_save_country) + " ${countryEntity.countryCode}")
    }
  }

  override suspend fun getAllCityWeather(): List<CityWeather> {
    return try {
      val list = withContext(Dispatchers.IO) {
        appDB.cityWeatherDao().getAllCityWeather().map { it.toDomain() }
      }

      list
    } catch (e: Exception) {
      e.printStackTrace()

      throw AppException(context.getString(R.string.framework_failed_get_all_city_weather))
    }
  }

  override suspend fun addCityWeather(cityEntity: City): CityWeather {
    return try {
      withContext(Dispatchers.IO) {
        val dbModel = CityWeatherDBModel(
          idCity = cityEntity.idCity,
          countryCode = cityEntity.countryCode,
          cityName = cityEntity.cityName,
          lat = cityEntity.lat,
          lon = cityEntity.lon
        )

        val newId = appDB.cityWeatherDao().insertCityWeather(dbModel)
        dbModel.copy(id = newId).toDomain()
      }
    } catch (e: Exception) {
      e.printStackTrace()

      throw AppException(context.getString(R.string.framework_failed_add_city_weather))
    }
  }

  override suspend fun deleteCityWeather(cityWeatherEntity: CityWeather) {
    return try {
      withContext(Dispatchers.IO) {
        appDB.cityWeatherDao().deleteCityWeather(cityWeatherEntity.id)
      }
    } catch (e: Exception) {
      e.printStackTrace()

      throw AppException(context.getString(R.string.framework_failed_delete_city_weather))
    }
  }

  override fun getLocalMessage(localMessage: LocalMessage): String {
    return when (localMessage) {
      ErrorWhenGettingForecastData -> context.getString(R.string.error_when_getting_forecast_data)
      GeneralError -> context.getString(R.string.error_general)
      NoCountryFound -> context.getString(R.string.no_country_found)
    }
  }
}
