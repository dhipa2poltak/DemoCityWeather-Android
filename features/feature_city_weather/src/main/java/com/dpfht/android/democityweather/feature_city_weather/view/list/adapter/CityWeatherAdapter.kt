package com.dpfht.android.democityweather.feature_city_weather.view.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.CurrentWeatherDomain
import com.dpfht.android.democityweather.domain.entity.Result
import com.dpfht.android.democityweather.domain.usecase.GetCurrentWeatherUseCase
import com.dpfht.android.democityweather.domain.util.WeatherUtil
import com.dpfht.android.democityweather.feature_city_weather.databinding.LayoutRowCityWeatherBinding
import com.dpfht.android.democityweather.feature_city_weather.util.ResourceUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CityWeatherAdapter @Inject constructor(
  private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
): RecyclerView.Adapter<CityWeatherAdapter.ViewHolder>() {

  lateinit var cityWeathers: ArrayList<CityWeatherEntity>
  lateinit var scope: CoroutineScope

  var onClickRowCityWeather: ((cityWeather: CityWeatherEntity) -> Unit)? = null
  var onDeleteCityWeather: ((position: Int, cityWeather: CityWeatherEntity) -> Unit)? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = LayoutRowCityWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val cityWeather = cityWeathers[position]

    holder.binding.tvCityName.text = cityWeather.cityName

    getCurrentWeather(holder.binding, cityWeather)

    holder.binding.ivDelete.setOnClickListener {
      onDeleteCityWeather?.let { it(position, cityWeather) }
    }

    holder.binding.root.setOnClickListener {
      onClickRowCityWeather?.let { it(cityWeather) }
    }
  }

  private fun getCurrentWeather(binding: LayoutRowCityWeatherBinding, cityWeather: CityWeatherEntity) {
    scope.launch {
      when (val result = getCurrentWeatherUseCase(cityWeather)) {
        is Result.Success -> {
          onSuccessGetCurrentWeather(binding, result.value)
        }
        is Result.ErrorResult -> {
          onErrorGetCurrentWeather(result.message)
        }
      }
    }
  }

  private fun onSuccessGetCurrentWeather(
    binding: LayoutRowCityWeatherBinding,
    currentWeather: CurrentWeatherDomain
  ) {
    val temp = currentWeather.main?.temp ?: 0.0
    binding.tvTemp.text = WeatherUtil.getFormattedTemperatureString(temp)

    if (currentWeather.weathers.isNotEmpty()) {
      val description = currentWeather.weathers[0].description
      binding.iconTemp.setAnimation(ResourceUtil.getAnimationResourceForWeatherDescription(description))
    }
  }

  private fun onErrorGetCurrentWeather(message: String) {}

  override fun getItemCount(): Int {
    return cityWeathers.size
  }

  class ViewHolder(val binding: LayoutRowCityWeatherBinding): RecyclerView.ViewHolder(binding.root)
}
