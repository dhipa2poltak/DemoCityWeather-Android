package com.dpfht.android.democityweather.framework.data.datasource.remote

import android.content.Context
import com.dpfht.android.democityweather.framework.R
import com.dpfht.android.democityweather.framework.data.datasource.remote.rest.RestService
import com.dpfht.democityweather.data.datasource.RemoteDataSource
import com.dpfht.democityweather.data.model.remote.response.toDomain
import com.dpfht.democityweather.domain.model.AppException
import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.Country
import com.dpfht.democityweather.domain.model.CurrentWeatherModel
import com.dpfht.democityweather.domain.model.ForecastModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class RemoteDataSourceImpl(
  private val context: Context,
  private val restService: RestService
): RemoteDataSource {

  override suspend fun getCountryInfo(countryCode: String): List<Country> {
    val list = safeApiCall(Dispatchers.IO) { restService.getCountry(countryCode) }

    return list.map { Country(countryCode = countryCode, countryName = it.name?.common ?: "") }
  }

  override suspend fun getCurrentWeather(cityWeather: CityWeather): CurrentWeatherModel {
    return safeApiCall(Dispatchers.IO) { restService.getCurrentWeather(cityWeather.lat, cityWeather.lon) }.toDomain()
  }

  override suspend fun getForecast(cityWeather: CityWeather): ForecastModel {
    return safeApiCall(Dispatchers.IO) { restService.getForecast(cityWeather.lat, cityWeather.lon) }.toDomain()
  }

  //--

  private suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): T {
    return withContext(dispatcher) {
      try {
        apiCall.invoke()
      } catch (t: Throwable) {
        throw when (t) {
          is IOException -> AppException(context.getString(R.string.framework_text_error_connection))
          is HttpException -> {
            //val code = t.code()
            /*
            val errorResponse = convertErrorBody(t)

            ErrorResult(errorResponse?.results?.get(0)?.error ?: "http error")
            */
            AppException(context.getString(R.string.framework_text_http_error))
          }
          else -> {
            AppException(context.getString(R.string.framework_text_error_conversion))
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
