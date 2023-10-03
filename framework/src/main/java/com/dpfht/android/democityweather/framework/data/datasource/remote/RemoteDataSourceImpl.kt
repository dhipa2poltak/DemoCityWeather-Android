package com.dpfht.android.democityweather.framework.data.datasource.remote

import android.content.Context
import com.dpfht.android.democityweather.data.datasource.RemoteDataSource
import com.dpfht.android.democityweather.data.model.remote.response.toDomain
import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.CountryEntity
import com.dpfht.android.democityweather.domain.entity.CurrentWeatherDomain
import com.dpfht.android.democityweather.domain.entity.ForecastDomain
import com.dpfht.android.democityweather.domain.entity.Result
import com.dpfht.android.democityweather.domain.entity.Result.ErrorResult
import com.dpfht.android.democityweather.domain.entity.Result.Success
import com.dpfht.android.democityweather.framework.R
import com.dpfht.android.democityweather.framework.data.datasource.remote.rest.RestService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class RemoteDataSourceImpl(
  private val context: Context,
  private val restService: RestService
): RemoteDataSource {

  override suspend fun getCountryInfo(countryCode: String): Result<List<CountryEntity>> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getCountry(countryCode) }) {
      is Success -> {
        val list = result.value.map { CountryEntity(countryCode = countryCode, countryName = it.name?.common ?: "") }

        Success(list)
      }
      is ErrorResult -> {
        result
      }
    }
  }

  override suspend fun getCurrentWeather(cityWeather: CityWeatherEntity): Result<CurrentWeatherDomain> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getCurrentWeather(cityWeather.lat, cityWeather.lon) }) {
      is Success -> {
        Success(result.value.toDomain())
      }
      is ErrorResult -> {
        result
      }
    }
  }

  override suspend fun getForecast(cityWeather: CityWeatherEntity): Result<ForecastDomain> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getForecast(cityWeather.lat, cityWeather.lon) }) {
      is Success -> {
        Success(result.value.toDomain())
      }
      is ErrorResult -> {
        result
      }
    }
  }

  //--

  private suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): Result<T> {
    return withContext(dispatcher) {
      try {
        Success(apiCall.invoke())
      } catch (t: Throwable) {
        when (t) {
          is IOException -> ErrorResult(context.getString(R.string.framework_text_error_connection))
          is HttpException -> {
            //val code = t.code()
            /*
            val errorResponse = convertErrorBody(t)

            ErrorResult(errorResponse?.results?.get(0)?.error ?: "http error")
            */
            ErrorResult(context.getString(R.string.framework_text_http_error))
          }
          else -> {
            ErrorResult(context.getString(R.string.framework_text_error_conversion))
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
