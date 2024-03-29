package com.dpfht.android.democityweather.feature_city_weather.view.add.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.android.democityweather.feature_city_weather.R
import com.dpfht.democityweather.domain.entity.Result
import com.dpfht.democityweather.domain.entity.CityEntity
import com.dpfht.democityweather.domain.usecase.GetCountryUseCase
import com.dpfht.android.democityweather.feature_city_weather.databinding.LayoutRowCityBinding
import com.dpfht.democityweather.domain.entity.Result.Error
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

class AddCityAdapter @Inject constructor(
  private val getCountryUseCase: GetCountryUseCase
): RecyclerView.Adapter<AddCityAdapter.ViewHolder>() {

  lateinit var cities: ArrayList<CityEntity>

  private val filteredCities = arrayListOf<CityEntity>()
  private var textFilter = ""

  lateinit var scope: CoroutineScope

  var onSelectCity: ((cityEntity: CityEntity) -> Unit)? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = LayoutRowCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val list = if (textFilter.isEmpty()) cities else filteredCities
    val city = list[position]

    holder.binding.tvCityName.text = city.cityName

    scope.launch {
      when (val result = getCountryUseCase(city.countryCode)) {
        is Result.Success -> {
          holder.binding.tvCountryName.text = result.value.countryName
          holder.binding.tvCountryName.setTextColor(Color.BLACK)
        }
        is Error -> {
          val str = holder.binding.root.context.getString(R.string.city_weather_text_failed_to_get_data)
          holder.binding.tvCountryName.text = str
          holder.binding.tvCountryName.setTextColor(Color.RED)
        }
      }
    }

    holder.binding.root.setOnClickListener {
      onSelectCity?.let { it(city) }
    }
  }

  override fun getItemCount(): Int {
    return if (textFilter.isEmpty()) {
      cities.size
    } else {
      filteredCities.size
    }
  }

  fun onFilteringCityName(text: String) {
    textFilter = text
    if (textFilter.isNotEmpty()) {
      filteredCities.clear()
      filteredCities.addAll(cities.filter { it.cityName.lowercase(Locale.ROOT).startsWith(
        textFilter.lowercase(Locale.ROOT)
      ) })
    } else {
      filteredCities.clear()
    }
    notifyDataSetChanged()
  }

  class ViewHolder(val binding: LayoutRowCityBinding): RecyclerView.ViewHolder(binding.root)
}
