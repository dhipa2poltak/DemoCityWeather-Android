package com.dpfht.democityweather.data.model.remote.response

import com.dpfht.democityweather.data.helpers.FileReaderHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CurrentWeatherResponseTest {

  @Test
  fun `ensure converting CurrentWeatherResponse to domain is success`() {
    val str = FileReaderHelper.readFileAsString("CurrentWeatherResponse.json")
    assertTrue(str.isNotEmpty())

    val typeToken = object : TypeToken<CurrentWeatherResponse>() {}.type
    val response = Gson().fromJson<CurrentWeatherResponse>(str, typeToken)
    val entity = response.toDomain()

    assertTrue(entity.weathers.isNotEmpty())
    assertTrue(entity.main != null)
    assertTrue(entity.wind != null)
    assertTrue(entity.name.isNotEmpty())
  }
}
