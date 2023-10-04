package com.dpfht.android.democityweather.feature_city_weather.view.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.feature_city_weather.databinding.FragmentDetailsCityWeatherBinding
import com.dpfht.android.democityweather.feature_city_weather.di.DaggerDetailsCityWeatherComponent
import com.dpfht.android.democityweather.framework.Constants
import com.dpfht.android.democityweather.framework.di.dependency.NavigationServiceDependency
import com.dpfht.android.democityweather.framework.navigation.NavigationService
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

@AndroidEntryPoint
class DetailsCityWeatherFragment : Fragment() {

  private lateinit var binding: FragmentDetailsCityWeatherBinding
  private val viewModel by viewModels<DetailsCityWeatherViewModel>()

  @Inject
  lateinit var navigationService: NavigationService

  override fun onAttach(context: Context) {
    super.onAttach(context)

    DaggerDetailsCityWeatherComponent.builder()
      .context(requireContext())
      .navDependency(EntryPointAccessors.fromActivity(requireActivity(), NavigationServiceDependency::class.java))
      .build()
      .inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentDetailsCityWeatherBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.rvHourly.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    binding.rvHourly.adapter = viewModel.hourlyAdapter

    binding.rvWeekly.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    binding.rvWeekly.adapter = viewModel.weeklyAdapter

    arguments?.let {
      val cw = it.getSerializable(Constants.FragmentArgsName.ARG_CITY_WEATHER) as? CityWeatherEntity
      if (cw != null) {
        viewModel.cityWeather = cw
        binding.tvCityName.text = cw.cityName
      }
    }

    observeViewModel()
    setListener()

    viewModel.start()
  }

  private fun observeViewModel() {
    viewModel.isShowDialogLoading.observe(viewLifecycleOwner) { isShow ->
      binding.swRefreshForecast.isRefreshing = isShow
    }

    viewModel.toastMessage.observe(viewLifecycleOwner) { msg ->
      if (msg.isNotEmpty()) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
      }
    }

    viewModel.modalMessage.observe(viewLifecycleOwner) { msg ->
      if (msg.isNotEmpty()) {
        navigationService.navigatoErrorMessage(msg)
      }
    }

    viewModel.tempData.observe(viewLifecycleOwner) { pair ->
      if (pair.first.isNotEmpty()) {
        binding.tvTemp.text = pair.first
      }

      if (pair.second != -1) {
        binding.iconTemp.setAnimation(pair.second)
      }
    }
  }

  private fun setListener() {
    binding.swRefreshForecast.setOnRefreshListener {
      viewModel.onRefreshing()
    }
  }
}
