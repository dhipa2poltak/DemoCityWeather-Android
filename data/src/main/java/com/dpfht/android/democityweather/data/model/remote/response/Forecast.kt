package com.dpfht.android.democityweather.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.android.democityweather.domain.entity.ForecastEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class Forecast(
  @SerializedName("dt")
  @Expose
  val dt: Long? = 0L,
  @SerializedName("main")
  @Expose
  val main: Main? = null,
  @SerializedName("weather")
  @Expose
  val weathers: List<Weather>? = listOf(),
  @SerializedName("wind")
  @Expose
  val wind: Wind? = null,
  @SerializedName("dt_txt")
  @Expose
  val dtTxt: String? = ""
)

fun Forecast.toDomain(): ForecastEntity {
  return ForecastEntity(
    dt = this.dt ?: 0L,
    main = this.main?.toDomain(),
    weathers = this.weathers?.map { it.toDomain() } ?: listOf(),
    wind = this.wind?.toDomain(),
    dtTxt = this.dtTxt ?: ""
  )
}
