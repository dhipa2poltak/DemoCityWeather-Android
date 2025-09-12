package com.dpfht.democityweather.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.democityweather.domain.model.Weather
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class WeatherDto(
  @SerializedName("id")
  @Expose
  val id: Long? = 0L,
  @SerializedName("main")
  @Expose
  val main: String? = "",
  @SerializedName("description")
  @Expose
  val description: String? = "",
  @SerializedName("icon")
  @Expose
  val icon: String? = ""
)

fun WeatherDto.toDomain(): Weather {
  return Weather(
    id = this.id ?: 0L,
    main = this.main ?: "",
    description = this.description ?: "",
    icon = this.icon ?: ""
  )
}
