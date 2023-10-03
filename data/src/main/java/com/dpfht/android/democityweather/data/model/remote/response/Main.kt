package com.dpfht.android.democityweather.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.android.democityweather.domain.entity.MainEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class Main(
  @SerializedName("temp")
  @Expose
  val temp: Double? = 0.0,
  @SerializedName("feels_like")
  @Expose
  val feelsLike: Double? = 0.0,
  @SerializedName("temp_min")
  @Expose
  val tempMin: Double? = 0.0,
  @SerializedName("temp_max")
  @Expose
  val tempMax: Double? = 0.0,
  @SerializedName("pressure")
  @Expose
  val pressure: Double? = 0.0,
  @SerializedName("humidity")
  @Expose
  val humidity: Double? = 0.0,
  @SerializedName("sea_level")
  @Expose
  val seaLevel: Double? = 0.0,
  @SerializedName("grnd_level")
  @Expose
  val groundLevel: Double? = 0.0
)

fun Main.toDomain(): MainEntity {
  return MainEntity(
    temp = this.temp ?: 0.0,
    feelsLike = this.feelsLike ?: 0.0,
    tempMin = this.tempMin ?: 0.0,
    tempMax = this.tempMax ?: 0.0,
    pressure = this.pressure ?: 0.0,
    humidity = this.humidity ?: 0.0,
    seaLevel = this.seaLevel ?: 0.0,
    groundLevel = this.groundLevel ?: 0.0
  )
}
