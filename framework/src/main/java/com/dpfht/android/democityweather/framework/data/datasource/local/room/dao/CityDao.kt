package com.dpfht.android.democityweather.framework.data.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dpfht.android.democityweather.framework.data.datasource.local.room.model.CityDBModel

@Dao
interface CityDao {

  @Query("SELECT * FROM tbl_master_city ORDER BY city_name ASC")
  fun getAllCity(): List<CityDBModel>

  @Query("SELECT * FROM tbl_master_city where country_code = :countryCode")
  fun getAllCityByCountryCode(countryCode: String): List<CityDBModel>

  @Insert
  suspend fun insertCity(city: CityDBModel)

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insertCities(cities: List<CityDBModel>)
}
