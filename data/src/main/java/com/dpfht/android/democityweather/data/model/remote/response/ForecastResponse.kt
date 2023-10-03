package com.dpfht.android.democityweather.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.android.democityweather.domain.entity.ForecastDomain
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class ForecastResponse(
  @SerializedName("cod")
  @Expose
  val cod: String? = "",
  @SerializedName("message")
  @Expose
  val message: Int? = -1,
  @SerializedName("cnt")
  @Expose
  val cnt: Int? = -1,
  @SerializedName("list")
  @Expose
  val forecasts: List<Forecast>? = listOf()
)

fun ForecastResponse.toDomain(): ForecastDomain {
  return ForecastDomain(this.forecasts?.map { it.toDomain() } ?: listOf())
}
