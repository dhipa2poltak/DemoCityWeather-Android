package com.dpfht.android.democityweather.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.android.democityweather.domain.entity.WindEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class Wind(
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

fun Wind.toDomain(): WindEntity {
  return WindEntity(
    speed = this.speed ?: 0.0,
    deg = this.deg ?: 0.0,
    gust = this.gust ?: 0.0
  )
}
