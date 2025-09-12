package com.dpfht.democityweather.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.democityweather.domain.model.Forecast
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class ForecastDto(
  @SerializedName("dt")
  @Expose
  val dt: Long? = 0L,
  @SerializedName("main")
  @Expose
  val main: MainDto? = null,
  @SerializedName("weather")
  @Expose
  val weathers: List<WeatherDto>? = listOf(),
  @SerializedName("wind")
  @Expose
  val wind: WindDto? = null,
  @SerializedName("dt_txt")
  @Expose
  val dtTxt: String? = ""
)

fun ForecastDto.toDomain(): Forecast {
  return Forecast(
    dt = this.dt ?: 0L,
    main = this.main?.toDomain(),
    weathers = this.weathers?.map { it.toDomain() } ?: listOf(),
    wind = this.wind?.toDomain(),
    dtTxt = this.dtTxt ?: ""
  )
}
