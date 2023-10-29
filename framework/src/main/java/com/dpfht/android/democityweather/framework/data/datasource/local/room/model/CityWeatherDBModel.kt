package com.dpfht.android.democityweather.framework.data.datasource.local.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dpfht.democityweather.domain.entity.CityWeatherEntity

@Entity(tableName = "tbl_city_weather")
data class CityWeatherDBModel(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo("id")
  val id: Long? = null,
  @ColumnInfo(name = "id_city")
  val idCity: Long? = 0L,
  @ColumnInfo(name = "country_code")
  val countryCode: String? = "",
  @ColumnInfo(name = "city_name")
  val cityName: String? = "",
  @ColumnInfo(name = "lat")
  val lat: Double? = 0.0,
  @ColumnInfo(name = "lon")
  val lon: Double? = 0.0
)

fun CityWeatherDBModel.toDomain(): CityWeatherEntity {
  return CityWeatherEntity(
    id = this.id ?: 0L,
    idCity = this.idCity ?: 0L,
    countryCode = this.countryCode ?: "",
    cityName = this.cityName ?: "",
    lat = this.lat ?: 0.0,
    lon = this.lon ?: 0.0
  )
}
