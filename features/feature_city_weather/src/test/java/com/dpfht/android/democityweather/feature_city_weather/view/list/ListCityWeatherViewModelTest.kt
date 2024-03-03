package com.dpfht.android.democityweather.feature_city_weather.view.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.android.democityweather.feature_city_weather.view.list.adapter.CityWeatherAdapter
import com.dpfht.democityweather.domain.entity.CityEntity
import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.Result
import com.dpfht.democityweather.domain.entity.VoidResult
import com.dpfht.democityweather.domain.usecase.AddCityWeatherUseCase
import com.dpfht.democityweather.domain.usecase.DeleteCityWeatherUseCase
import com.dpfht.democityweather.domain.usecase.GetAllCityWeatherUseCase
import com.dpfht.democityweather.domain.usecase.GetCurrentWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.eq
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ListCityWeatherViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @get:Rule
  val instantTaskExecutionRule = InstantTaskExecutorRule()

  private lateinit var viewModel: ListCityWeatherViewModel

  private lateinit var getAllCityWeatherUseCase: GetAllCityWeatherUseCase
  private lateinit var addCityWeatherUseCase: AddCityWeatherUseCase
  private lateinit var deleteCityWeatherUseCase: DeleteCityWeatherUseCase
  private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

  private lateinit var errorMessageObserver: Observer<String>
  private lateinit var isNoDataObserver: Observer<Boolean>
  private lateinit var isLoadingObserver: Observer<Boolean>
  private lateinit var navigateObserver: Observer<CityWeatherEntity?>

  private lateinit var cityWeatherAdapter: CityWeatherAdapter

  private lateinit var cityWeathers: ArrayList<CityWeatherEntity>

  private val cityWeatherEntity1 = CityWeatherEntity(1, 101, "ID", "Jakarta", 1.0, 1.0)
  private val cityWeatherEntity2 = CityWeatherEntity(2, 102, "ID", "Bogor", 2.0, 2.0)
  private val list = listOf(cityWeatherEntity1, cityWeatherEntity2)

  private val cityEntity = CityEntity(101, "ID", "Jakarta", 1.0, 1.0)

  private val msg = "this is an error message"

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)

    getAllCityWeatherUseCase = mock()
    addCityWeatherUseCase = mock()
    deleteCityWeatherUseCase = mock()
    getCurrentWeatherUseCase = mock()

    errorMessageObserver = mock()
    isNoDataObserver = mock()
    isLoadingObserver = mock()
    navigateObserver = mock()

    cityWeathers = arrayListOf()
    cityWeatherAdapter = spy(CityWeatherAdapter(getCurrentWeatherUseCase))
    viewModel = ListCityWeatherViewModel(
      getAllCityWeatherUseCase,
      addCityWeatherUseCase,
      deleteCityWeatherUseCase,
      cityWeathers,
      cityWeatherAdapter
    )
  }

  @Test
  fun `get all city weather successfully`() = runTest {
    val result = Result.Success(list)
    whenever(getAllCityWeatherUseCase()).thenReturn(result)

    viewModel.isNoData.observeForever(isNoDataObserver)
    viewModel.isRefreshing.observeForever(isLoadingObserver)
    viewModel.start()

    verify(isLoadingObserver).onChanged(eq(false))
    verify(isNoDataObserver).onChanged(eq(cityWeathers.isEmpty()))
    assertTrue(list.size == cityWeathers.size)
  }

  @Test
  fun `fail to get all city weather`() = runTest {
    val result = Result.Error(msg)
    whenever(getAllCityWeatherUseCase()).thenReturn(result)

    viewModel.isNoData.observeForever(isNoDataObserver)
    viewModel.isRefreshing.observeForever(isLoadingObserver)
    viewModel.toastMessage.observeForever(errorMessageObserver)
    viewModel.start()

    inOrder(errorMessageObserver) {
      verify(errorMessageObserver).onChanged(eq(msg))
      verify(errorMessageObserver).onChanged(eq(""))
    }
    verify(isLoadingObserver).onChanged(eq(false))
    verify(isNoDataObserver).onChanged(eq(cityWeathers.isEmpty()))
    assertTrue(cityWeathers.isEmpty())
  }

  @Test
  fun `add city weather successfully`() = runTest {
    val result = Result.Success(cityWeatherEntity1)
    whenever(addCityWeatherUseCase(cityEntity)).thenReturn(result)

    viewModel.isRefreshing.observeForever(isLoadingObserver)
    viewModel.isNoData.observeForever(isNoDataObserver)
    viewModel.addCityWeather(cityEntity)

    verify(isLoadingObserver).onChanged(eq(false))
    verify(isNoDataObserver).onChanged(eq(cityWeathers.isEmpty()))
    verify(cityWeatherAdapter).notifyItemInserted(cityWeathers.size - 1)
  }

  @Test
  fun `fail to add city weather`() = runTest {
    val result = Result.Error(msg)
    whenever(addCityWeatherUseCase(cityEntity)).thenReturn(result)

    viewModel.toastMessage.observeForever(errorMessageObserver)
    viewModel.isRefreshing.observeForever(isLoadingObserver)
    viewModel.isNoData.observeForever(isNoDataObserver)
    viewModel.addCityWeather(cityEntity)

    inOrder(errorMessageObserver) {
      verify(errorMessageObserver).onChanged(eq(msg))
      verify(errorMessageObserver).onChanged(eq(""))
    }
    verify(isLoadingObserver).onChanged(eq(false))
    verify(isNoDataObserver).onChanged(eq(cityWeathers.isEmpty()))
  }

  @Test
  fun `delete city weather successfully`() = runTest {
    val result = VoidResult.Success
    whenever(deleteCityWeatherUseCase(cityWeatherEntity1)).thenReturn(result)

    cityWeathers.add(cityWeatherEntity1)

    viewModel.isRefreshing.observeForever(isLoadingObserver)
    viewModel.isNoData.observeForever(isNoDataObserver)
    cityWeatherAdapter.onDeleteCityWeather?.let { it(cityWeatherEntity1) }

    verify(isLoadingObserver).onChanged(eq(false))
    verify(isNoDataObserver).onChanged(eq(cityWeathers.isEmpty()))
    verify(cityWeatherAdapter).notifyItemRemoved(0)
  }

  @Test
  fun `fail to delete city weather`() = runTest {
    val result = VoidResult.Error(msg)
    whenever(deleteCityWeatherUseCase(cityWeatherEntity1)).thenReturn(result)

    cityWeathers.add(cityWeatherEntity1)

    viewModel.toastMessage.observeForever(errorMessageObserver)
    viewModel.isRefreshing.observeForever(isLoadingObserver)
    viewModel.isNoData.observeForever(isNoDataObserver)
    cityWeatherAdapter.onDeleteCityWeather?.let { it(cityWeatherEntity1) }

    inOrder(errorMessageObserver) {
      verify(errorMessageObserver).onChanged(eq(msg))
      verify(errorMessageObserver).onChanged(eq(""))
    }
    verify(isLoadingObserver).onChanged(eq(false))
    verify(isNoDataObserver).onChanged(eq(cityWeathers.isEmpty()))
  }

  @Test
  fun `on refresh successfully`() = runTest {
    val result = Result.Success(list)
    whenever(getAllCityWeatherUseCase()).thenReturn(result)

    viewModel.isNoData.observeForever(isNoDataObserver)
    viewModel.isRefreshing.observeForever(isLoadingObserver)
    viewModel.onRefreshingData()

    verify(isLoadingObserver).onChanged(eq(false))
    verify(isNoDataObserver).onChanged(eq(cityWeathers.isEmpty()))
    assertTrue(list.size == cityWeathers.size)
  }

  @Test
  fun `on navigate successfully`() = runTest {
    viewModel.navigateToWeatherDetails.observeForever(navigateObserver)
    cityWeatherAdapter.onClickRowCityWeather?.let { it(cityWeatherEntity1) }

    inOrder(navigateObserver) {
      verify(navigateObserver).onChanged(eq(cityWeatherEntity1))
      verify(navigateObserver).onChanged(eq(null))
    }
  }
}
