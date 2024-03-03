package com.dpfht.android.democityweather.feature_city_weather.view.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.android.democityweather.feature_city_weather.view.details.adapter.HourlyAdapter
import com.dpfht.android.democityweather.feature_city_weather.view.details.adapter.WeeklyAdapter
import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.CurrentWeatherDomain
import com.dpfht.democityweather.domain.entity.MainEntity
import com.dpfht.democityweather.domain.entity.WindEntity
import com.dpfht.democityweather.domain.entity.Result
import com.dpfht.democityweather.domain.entity.vw_entity.ForecastVWEntity
import com.dpfht.democityweather.domain.usecase.GetCurrentWeatherUseCase
import com.dpfht.democityweather.domain.usecase.GetForecastUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class DetailsCityWeatherViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @get:Rule
  val instantTaskExecutionRule = InstantTaskExecutorRule()

  private lateinit var viewModel: DetailsCityWeatherViewModel

  @Mock
  private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

  @Mock
  private lateinit var getForecastUseCase: GetForecastUseCase

  @Mock
  private lateinit var hourlyAdapter: HourlyAdapter

  @Mock
  private lateinit var weeklyAdapter: WeeklyAdapter

  @Mock
  private lateinit var errorMessageObserver: Observer<String>

  @Mock
  private lateinit var isLoadingObserver: Observer<Boolean>

  @Mock
  private lateinit var tempDataObserver: Observer<Pair<String, Int>>

  private val currentWeather = CurrentWeatherDomain(listOf(), MainEntity(), WindEntity(), "Jakarta")
  private val forecast = ForecastVWEntity(listOf(), listOf())

  private val cityWeatherEntity = CityWeatherEntity(1, 101, "ID", "Jakarta", 1.0, 1.0)

  private val msg = "this is an error message"

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)

    viewModel = DetailsCityWeatherViewModel(
      getCurrentWeatherUseCase,
      getForecastUseCase,
      hourlyAdapter,
      weeklyAdapter
    )
  }

  @Test
  fun `get current weather and forecast successfully`() = runTest {
    val resultWeather = Result.Success(currentWeather)
    val resultForecast = Result.Success(forecast)

    whenever(getCurrentWeatherUseCase(cityWeatherEntity)).thenReturn(resultWeather)
    whenever(getForecastUseCase(cityWeatherEntity)).thenReturn(resultForecast)

    viewModel.tempData.observeForever(tempDataObserver)
    viewModel.isShowDialogLoading.observeForever(isLoadingObserver)
    viewModel.cityWeather = cityWeatherEntity
    viewModel.start()

    verify(tempDataObserver).onChanged(any())
    verify(isLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `fail to get current weather but get forecast successfully`() = runTest {
    val resultWeather = Result.Error(msg)
    val resultForecast = Result.Success(forecast)

    whenever(getCurrentWeatherUseCase(cityWeatherEntity)).thenReturn(resultWeather)
    whenever(getForecastUseCase(cityWeatherEntity)).thenReturn(resultForecast)

    viewModel.modalMessage.observeForever(errorMessageObserver)
    viewModel.isShowDialogLoading.observeForever(isLoadingObserver)
    viewModel.cityWeather = cityWeatherEntity
    viewModel.start()

    inOrder(errorMessageObserver) {
      verify(errorMessageObserver).onChanged(eq(msg))
      verify(errorMessageObserver).onChanged("")
    }
    verify(isLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `get current weather successfully but fail to get forecast`() = runTest {
    val resultWeather = Result.Success(currentWeather)
    val resultForecast = Result.Error(msg)

    whenever(getCurrentWeatherUseCase(cityWeatherEntity)).thenReturn(resultWeather)
    whenever(getForecastUseCase(cityWeatherEntity)).thenReturn(resultForecast)

    viewModel.tempData.observeForever(tempDataObserver)
    viewModel.modalMessage.observeForever(errorMessageObserver)
    viewModel.isShowDialogLoading.observeForever(isLoadingObserver)
    viewModel.cityWeather = cityWeatherEntity
    viewModel.start()

    verify(tempDataObserver).onChanged(any())
    inOrder(errorMessageObserver) {
      verify(errorMessageObserver).onChanged(eq(msg))
      verify(errorMessageObserver).onChanged("")
    }
    verify(isLoadingObserver).onChanged(eq(false))
  }

  @Test
  fun `on refresh successfully`() = runTest {
    val resultWeather = Result.Success(currentWeather)
    val resultForecast = Result.Success(forecast)

    whenever(getCurrentWeatherUseCase(cityWeatherEntity)).thenReturn(resultWeather)
    whenever(getForecastUseCase(cityWeatherEntity)).thenReturn(resultForecast)

    viewModel.tempData.observeForever(tempDataObserver)
    viewModel.isShowDialogLoading.observeForever(isLoadingObserver)
    viewModel.cityWeather = cityWeatherEntity
    viewModel.onRefreshing()

    verify(tempDataObserver).onChanged(any())
    verify(isLoadingObserver).onChanged(eq(false))
  }
}
