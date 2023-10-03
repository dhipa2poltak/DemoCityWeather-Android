package com.dpfht.android.democityweather.framework.data.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dpfht.android.democityweather.framework.data.datasource.local.room.model.CountryDBModel

@Dao
interface CountryDao {

  @Query("SELECT * FROM tbl_country WHERE country_code = :countryCode")
  suspend fun getCountry(countryCode: String): List<CountryDBModel>

  @Query("SELECT * FROM tbl_country")
  fun getAllCountry(): List<CountryDBModel>

  @Insert
  suspend fun insertCountry(country: CountryDBModel)
}
