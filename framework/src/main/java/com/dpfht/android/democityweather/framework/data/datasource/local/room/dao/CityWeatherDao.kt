package com.dpfht.android.democityweather.framework.data.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dpfht.android.democityweather.framework.data.datasource.local.room.model.CityWeatherDBModel

@Dao
interface CityWeatherDao {

  @Query("SELECT * FROM tbl_city_weather")
  fun getAllCityWeather(): List<CityWeatherDBModel>

  @Insert
  suspend fun insertCityWeather(cityWeather: CityWeatherDBModel): Long

  @Query("DELETE FROM tbl_city_weather WHERE id = :cityWeatherId")
  suspend fun deleteCityWeather(cityWeatherId: Long)
}
