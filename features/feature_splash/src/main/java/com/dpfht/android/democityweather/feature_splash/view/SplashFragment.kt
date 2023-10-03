package com.dpfht.android.democityweather.feature_splash.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.dpfht.android.democityweather.feature_splash.R
import com.dpfht.android.democityweather.feature_splash.databinding.FragmentSplashBinding
import com.dpfht.android.democityweather.feature_splash.di.DaggerSplashComponent
import com.dpfht.android.democityweather.framework.Constants
import com.dpfht.android.democityweather.framework.di.dependency.NavigationServiceDependency
import com.dpfht.android.democityweather.framework.navigation.NavigationService
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

  private lateinit var binding: FragmentSplashBinding
  private val viewModel by viewModels<SplashViewModel>()

  @Inject
  lateinit var navigationService: NavigationService

  override fun onAttach(context: Context) {
    super.onAttach(context)

    DaggerSplashComponent.builder()
      .context(requireContext())
      .navDependency(EntryPointAccessors.fromActivity(requireActivity(), NavigationServiceDependency::class.java))
      .build()
      .inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentSplashBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    observeViewModel()
    viewModel.start()

    Handler(Looper.getMainLooper()).postDelayed({
      viewModel.onDelayDone()
      navigateToListOfCityWeather()
    }, Constants.DELAY_SPLASH)
  }

  private fun observeViewModel() {
    viewModel.isInitDBDoneData.observe(viewLifecycleOwner) { isDone ->
      if (isDone) {
        navigateToListOfCityWeather()
      }
    }
  }

  private fun navigateToListOfCityWeather() {
    if (viewModel.isDelayDone && viewModel.isInitDBDone) {
      navigationService.navigateToListOfCityWeather()
    } else if (viewModel.isDelayDone) {
      binding.tvLoading.text = requireContext().resources.getString(R.string.splash_init_db)
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
