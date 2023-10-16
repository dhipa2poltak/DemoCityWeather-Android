package com.dpfht.android.democityweather.feature_city_weather.view.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.android.democityweather.feature_city_weather.R
import com.dpfht.android.democityweather.feature_city_weather.databinding.FragmentListCityWeatherBinding
import com.dpfht.android.democityweather.framework.Constants
import com.dpfht.android.democityweather.framework.commons.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListCityWeatherFragment : BaseFragment<FragmentListCityWeatherBinding>(R.layout.fragment_list_city_weather) {

  private val viewModel by viewModels<ListCityWeatherViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.rvCityWeather.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    binding.rvCityWeather.adapter = viewModel.cityWeatherAdapter

    observeViewModel()
    setListener()

    viewModel.start()
  }

  private fun observeViewModel() {
    viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
      if (message.isNotEmpty()) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
      }
    }

    viewModel.isNoData.observe(viewLifecycleOwner) { isNoData ->
      if (isNoData) {
        binding.rvCityWeather.visibility = View.INVISIBLE
        binding.tvNoData.visibility = View.VISIBLE
      } else {
        binding.rvCityWeather.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.GONE
      }
    }

    viewModel.isRefreshing.observe(viewLifecycleOwner) { isRefreshing ->
      binding.swRefreshCityWeather.isRefreshing = isRefreshing
    }

    viewModel.navigateToWeatherDetails.observe(viewLifecycleOwner) { cityWeather ->
      if (cityWeather != null) {
        navigationService.navigateToDetailsCityWeather(cityWeather)
      }
    }
  }

  private fun setListener() {
    binding.ivAdd.setOnClickListener {
      if (viewModel.cityWeathers.size >= Constants.MAX_ADDED_CITY_COUNT) {
        Toast.makeText(requireContext(), requireContext().getString(R.string.city_weather_text_quota_exceeded), Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }

      navigationService.navigateToAddCityWeather(this.viewModel::addCityWeather)
    }

    binding.swRefreshCityWeather.setOnRefreshListener {
      viewModel.onRefreshingData()
    }
  }
}
