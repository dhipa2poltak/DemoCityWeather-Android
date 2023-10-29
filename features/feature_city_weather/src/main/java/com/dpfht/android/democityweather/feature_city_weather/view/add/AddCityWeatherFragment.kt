package com.dpfht.android.democityweather.feature_city_weather.view.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.democityweather.domain.entity.CityEntity
import com.dpfht.android.democityweather.feature_city_weather.R
import com.dpfht.android.democityweather.feature_city_weather.databinding.FragmentAddCityWeatherBinding
import com.dpfht.android.democityweather.framework.commons.base.BaseBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCityWeatherFragment : BaseBottomSheetDialogFragment<FragmentAddCityWeatherBinding>(R.layout.fragment_add_city_weather) {

  private val viewModel by viewModels<AddCityWeatherViewModel>()

  private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
  var onSelectCityCallback: ((cityEntity: CityEntity) -> Unit)? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.rvCity.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    binding.rvCity.adapter = viewModel.addCityAdapter
    viewModel.addCityAdapter.onSelectCity = this::onSelectCity

    bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
    bottomSheetBehavior.peekHeight = (resources.displayMetrics.heightPixels * 0.9).toInt()
    binding.bottomsheetLayout.minimumHeight = (resources.displayMetrics.heightPixels * 0.9).toInt()

    observeViewModel()
    setListener()

    viewModel.start()
  }

  private fun observeViewModel() {
    viewModel.isShowDialogLoading.observe(viewLifecycleOwner) {}

    viewModel.toastMessage.observe(viewLifecycleOwner) { msg ->
      if (msg.isNotEmpty()) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
      }
    }

    viewModel.modalMessage.observe(viewLifecycleOwner) { msg ->
      if (msg.isNotEmpty()) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
      }
    }
  }

  private fun setListener() {
    binding.etSearchCity.addTextChangedListener {
      val text = it.toString().trim()
      viewModel.addCityAdapter.onFilteringCityName(text)
    }
  }

  private fun onSelectCity(cityEntity: CityEntity) {
    dismiss()
    onSelectCityCallback?.let { it(cityEntity) }
  }

  companion object {
    fun newInstance(): AddCityWeatherFragment {
      return AddCityWeatherFragment()
    }
  }
}
