package com.dpfht.democityweather.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.democityweather.domain.model.CurrentWeatherModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class CurrentWeatherResponse(
  @SerializedName("weather")
  @Expose
  val weathers: List<WeatherDto>? = listOf(),
  @SerializedName("main")
  @Expose
  val main: MainDto? = null,
  @SerializedName("wind")
  @Expose
  val wind: WindDto? = null,
  @SerializedName("name")
  @Expose
  val name: String? = ""
)

fun CurrentWeatherResponse.toDomain(): CurrentWeatherModel {
  return CurrentWeatherModel(
    weathers = this.weathers?.map { it.toDomain() } ?: listOf(),
    main = this.main?.toDomain(),
    wind = this.wind?.toDomain(),
    name = this.name ?: ""
  )
}
