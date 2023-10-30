package com.dpfht.android.democityweather.framework.data.datasource.remote

import android.content.Context
import com.dpfht.android.democityweather.framework.R
import com.dpfht.android.democityweather.framework.data.datasource.remote.rest.RestService
import com.dpfht.democityweather.data.datasource.RemoteDataSource
import com.dpfht.democityweather.data.model.remote.response.toDomain
import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.CountryEntity
import com.dpfht.democityweather.domain.entity.CurrentWeatherDomain
import com.dpfht.democityweather.domain.entity.ForecastDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class RemoteDataSourceImpl(
  private val context: Context,
  private val restService: RestService
): RemoteDataSource {

  override suspend fun getCountryInfo(countryCode: String): List<CountryEntity> {
    val list = safeApiCall(Dispatchers.IO) { restService.getCountry(countryCode) }

    return list.map { CountryEntity(countryCode = countryCode, countryName = it.name?.common ?: "") }
  }

  override suspend fun getCurrentWeather(cityWeather: CityWeatherEntity): CurrentWeatherDomain {
    return safeApiCall(Dispatchers.IO) { restService.getCurrentWeather(cityWeather.lat, cityWeather.lon) }.toDomain()
  }

  override suspend fun getForecast(cityWeather: CityWeatherEntity): ForecastDomain {
    return safeApiCall(Dispatchers.IO) { restService.getForecast(cityWeather.lat, cityWeather.lon) }.toDomain()
  }

  //--

  private suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): T {
    return withContext(dispatcher) {
      try {
        apiCall.invoke()
      } catch (t: Throwable) {
        throw when (t) {
          is IOException -> Exception(context.getString(R.string.framework_text_error_connection))
          is HttpException -> {
            //val code = t.code()
            /*
            val errorResponse = convertErrorBody(t)

            ErrorResult(errorResponse?.results?.get(0)?.error ?: "http error")
            */
            Exception(context.getString(R.string.framework_text_http_error))
          }
          else -> {
            Exception(context.getString(R.string.framework_text_error_conversion))
          }
        }
      }
    }
  }

  /*
  private fun convertErrorBody(t: HttpException): PostFCMMessageResponse? {
    return try {
      t.response()?.errorBody()?.source().let {
        val json = it?.readString(Charset.defaultCharset())
        val typeToken = object : TypeToken<PostFCMMessageResponse>() {}.type
        val errorResponse = Gson().fromJson<PostFCMMessageResponse>(json, typeToken)
        errorResponse
      }
    } catch (e: Exception) {
      null
    }
  }
  */
}
