package com.dpfht.democityweather.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.democityweather.domain.model.Wind
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class WindDto(
  @SerializedName("speed")
  @Expose
  val speed: Double? = 0.0,
  @SerializedName("deg")
  @Expose
  val deg: Double? = 0.0,
  @SerializedName("gust")
  @Expose
  val gust: Double? = 0.0
)

fun WindDto.toDomain(): Wind {
  return Wind(
    speed = this.speed ?: 0.0,
    deg = this.deg ?: 0.0,
    gust = this.gust ?: 0.0
  )
}
