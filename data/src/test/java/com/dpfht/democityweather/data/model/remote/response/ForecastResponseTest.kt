package com.dpfht.democityweather.data.model.remote.response

import com.dpfht.democityweather.data.helpers.FileReaderHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ForecastResponseTest {

  @Test
  fun `ensure converting ForecastResponse to domain is success`() {
    val str = FileReaderHelper.readFileAsString("ForecastResponse.json")
    assertTrue(str.isNotEmpty())

    val typeToken = object : TypeToken<ForecastResponse>() {}.type
    val response = Gson().fromJson<ForecastResponse>(str, typeToken)
    val entity = response.toDomain()

    assertTrue(entity.forecasts.isNotEmpty())
  }
}
