package com.dpfht.android.democityweather.data.model.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class CountryInfo(
  @SerializedName("name")
  @Expose
  val name: CountryName? = null
)
