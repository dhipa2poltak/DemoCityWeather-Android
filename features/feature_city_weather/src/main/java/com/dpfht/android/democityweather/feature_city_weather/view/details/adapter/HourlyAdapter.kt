package com.dpfht.android.democityweather.feature_city_weather.view.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.android.democityweather.domain.entity.vw_entity.ForecastHourlyVWEntity
import com.dpfht.android.democityweather.feature_city_weather.databinding.LayoutCellHourlyBinding
import com.dpfht.android.democityweather.feature_city_weather.view.details.adapter.HourlyAdapter.ViewHolder
import javax.inject.Inject

class HourlyAdapter @Inject constructor(

): RecyclerView.Adapter<ViewHolder>() {

  lateinit var hourlyVWEntities: List<ForecastHourlyVWEntity>

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = LayoutCellHourlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val hourlyModel = hourlyVWEntities[position]

    holder.binding.tvTime.text = hourlyModel.strTime
    if (hourlyModel.animationId != -1) {
      holder.binding.iconTemp.setAnimation(hourlyModel.animationId)
    }
    holder.binding.tvTemp.text = hourlyModel.strTemperature
  }

  override fun getItemCount(): Int {
    return hourlyVWEntities.size
  }

  class ViewHolder(val binding: LayoutCellHourlyBinding): RecyclerView.ViewHolder(binding.root)
}
