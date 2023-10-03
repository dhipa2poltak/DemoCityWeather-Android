package com.dpfht.android.democityweather.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.android.democityweather.domain.entity.WeatherEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class Weather(
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

fun Weather.toDomain(): WeatherEntity {
  return WeatherEntity(
    id = this.id ?: 0L,
    main = this.main ?: "",
    description = this.description ?: "",
    icon = this.icon ?: ""
  )
}
