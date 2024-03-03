package com.dpfht.android.democityweather.feature_splash.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.dpfht.android.democityweather.feature_splash.R
import com.dpfht.android.democityweather.feature_splash.databinding.FragmentSplashBinding
import com.dpfht.android.democityweather.framework.commons.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

  private val viewModel by viewModels<SplashViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    observeViewModel()
    viewModel.start()
  }

  private fun observeViewModel() {
    viewModel.isInitializationDone.observe(viewLifecycleOwner) { isDone ->
      if (isDone) {
        navigationService.navigateToListOfCityWeather()
      } else if (viewModel.isDelayDone) {
        binding.tvLoading.text = requireContext().resources.getString(R.string.splash_init_db)
      }
    }
  }

  override fun onStart() {
    super.onStart()
    (requireActivity() as AppCompatActivity).supportActionBar?.hide()
  }

  override fun onStop() {
    super.onStop()
    (requireActivity() as AppCompatActivity).supportActionBar?.show()
  }
}
