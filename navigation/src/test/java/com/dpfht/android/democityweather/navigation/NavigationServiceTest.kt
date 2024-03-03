package com.dpfht.android.democityweather.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import com.dpfht.android.democityweather.framework.navigation.NavigationService
import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NavigationServiceTest {

  private lateinit var navigationService: NavigationService
  private lateinit var navController: NavController
  private lateinit var activity: AppCompatActivity

  @Before
  fun setup() {
    navController = mock()
    activity = mock()
    navigationService = NavigationServiceImpl(navController, activity)
  }

  @Test
  fun `ensure supportFragmentManager is accessed in Activity when calling navigateToAddCityWeather method in navigationService`() {
    try {
      navigationService.navigateToAddCityWeather(null)
    } catch (_: Exception) {}

    verify(activity).supportFragmentManager
  }

  @Test
  fun `ensure navigate method is called in navController when calling navigateToDetailsCityWeather method in navigationService`() {
    val cityWeatherEntity = CityWeatherEntity(1, 101, "ID", "Jakarta", 1.0, 1.0)
    navigationService.navigateToDetailsCityWeather(cityWeatherEntity)

    verify(navController).navigate(anyInt(), any())
  }

  @Test
  fun `ensure navigate method is called in navController when calling navigateToErrorMessage method in navigationService`() {
    navigationService.navigateToErrorMessage("this is an error message")

    verify(navController).navigate(any<NavDeepLinkRequest>())
  }
}
