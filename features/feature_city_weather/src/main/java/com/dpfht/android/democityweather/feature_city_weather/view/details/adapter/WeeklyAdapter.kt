package com.dpfht.android.democityweather.feature_city_weather.view.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.democityweather.domain.entity.vw_entity.ForecastWeeklyVWEntity
import com.dpfht.democityweather.domain.util.WeatherUtil
import com.dpfht.android.democityweather.feature_city_weather.databinding.LayoutRowWeeklyBinding
import com.dpfht.android.democityweather.feature_city_weather.view.details.adapter.WeeklyAdapter.ViewHolder
import javax.inject.Inject

class WeeklyAdapter @Inject constructor(

): RecyclerView.Adapter<ViewHolder>() {

  lateinit var weeklyVWEntities: List<ForecastWeeklyVWEntity>

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = LayoutRowWeeklyBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val weeklyModel = weeklyVWEntities[position]

    holder.binding.tvDay.text = weeklyModel.day
    if (weeklyModel.minTemperature != -999.0) {
      holder.binding.tvMinTemp.text = WeatherUtil.getFormattedTemperatureString(weeklyModel.minTemperature)
    }
    if (weeklyModel.maxTemperature != -999.0) {
      holder.binding.tvMaxTemp.text = WeatherUtil.getFormattedTemperatureString(weeklyModel.maxTemperature)
    }

    if (weeklyModel.animationId != -1) {
      holder.binding.iconTemp.setAnimation(weeklyModel.animationId)
    }
  }

  override fun getItemCount(): Int {
    return weeklyVWEntities.size
  }

  class ViewHolder(val binding: LayoutRowWeeklyBinding): RecyclerView.ViewHolder(binding.root)
}
