package com.dpfht.democityweather.data.model.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class CountryInfoResponse(
  @SerializedName("name")
  @Expose
  val name: CountryNameDto? = null
)
