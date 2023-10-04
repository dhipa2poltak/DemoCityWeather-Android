package com.dpfht.android.democityweather.framework

object Constants {

  const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
  const val API_KEY = BuildConfig.API_KEY

  const val DELAY_SPLASH = 3000L
  const val MAX_ADDED_CITY_COUNT = 10

  object FragmentArgsName {
    const val ARG_CITY_WEATHER = "arg_city_weather"
  }
}
