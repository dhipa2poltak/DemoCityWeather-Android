package com.dpfht.android.democityweather.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.android.democityweather.domain.entity.CurrentWeatherDomain
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class CurrentWeatherResponse(
  @SerializedName("weather")
  @Expose
  val weathers: List<Weather>? = listOf(),
  @SerializedName("main")
  @Expose
  val main: Main? = null,
  @SerializedName("wind")
  @Expose
  val wind: Wind? = null,
  @SerializedName("name")
  @Expose
  val name: String? = ""
)

fun CurrentWeatherResponse.toDomain(): CurrentWeatherDomain {
  return CurrentWeatherDomain(
    weathers = this.weathers?.map { it.toDomain() } ?: listOf(),
    main = this.main?.toDomain(),
    wind = this.wind?.toDomain(),
    name = this.name ?: ""
  )
}
