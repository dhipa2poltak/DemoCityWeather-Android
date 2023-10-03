package com.dpfht.android.democityweather.framework.data.datasource.local.assets.model

import androidx.annotation.Keep
import com.dpfht.android.democityweather.framework.data.datasource.local.room.model.CityDBModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class CityAssetModel(
  @SerializedName("id")
  @Expose
  val id: Long? = 0,
  @SerializedName("coord")
  @Expose
  val coord: Coord? = null,
  @SerializedName("country")
  @Expose
  val countryCode: String? = "",
  @SerializedName("name")
  @Expose
  val name: String? = ""
)

@Keep
data class Coord(
  @SerializedName("lat")
  @Expose
  val lat: Double? = 0.0,
  @SerializedName("lon")
  @Expose
  val lon: Double? = 0.0
)

fun CityAssetModel.toDBModel(): CityDBModel {
  return CityDBModel(idCity = this.id, countryCode = this.countryCode, cityName = this.name, lat = this.coord?.lat ?: 0.0, lon = this.coord?.lon)
}
