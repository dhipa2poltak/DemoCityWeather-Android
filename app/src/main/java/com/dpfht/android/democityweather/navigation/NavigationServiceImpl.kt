package com.dpfht.android.democityweather.navigation

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import com.dpfht.android.democityweather.R
import com.dpfht.android.democityweather.domain.entity.CityEntity
import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.feature_city_weather.view.add.AddCityWeatherFragment
import com.dpfht.android.democityweather.framework.Constants
import com.dpfht.android.democityweather.framework.navigation.NavigationService

class NavigationServiceImpl(
  private val navController: NavController,
  private val activity: AppCompatActivity
): NavigationService {

  override fun navigateToListOfCityWeather() {
    val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
    navGraph.setStartDestination(R.id.listCityWeatherFragment)

    navController.graph = navGraph
  }

  override fun navigateToAddCityWeather(onSelectCity: ((cityEntity: CityEntity) -> Unit)?) {
    val fragment = AddCityWeatherFragment.newInstance()
    fragment.onSelectCityCallback = onSelectCity

    fragment.show(activity.supportFragmentManager, "ADD_CITY_WEATHER")
  }

  override fun navigateToDetailsCityWeather(cityWeather: CityWeatherEntity) {
    val args = Bundle()
    args.putSerializable(Constants.FragmentArgsName.ARG_CITY_WEATHER, cityWeather)

    navController.navigate(R.id.action_list_to_details_navigation, args)
  }

  override fun navigateToErrorMessage(message: String) {
    val builder = Uri.Builder()
    builder.scheme("android-app")
      .authority("com.dpfht.android.democityweather")
      .appendPath("error_message_dialog_fragment")
      .appendQueryParameter(Constants.FragmentArgsName.ARG_MESSAGE, message)

    navController.navigate(
      NavDeepLinkRequest.Builder
      .fromUri(builder.build())
      .build())
  }
}
