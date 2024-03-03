package com.dpfht.android.democityweather.feature_city_weather.view.add

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.android.democityweather.feature_city_weather.view.add.adapter.AddCityAdapter
import com.dpfht.democityweather.domain.entity.CityEntity
import com.dpfht.democityweather.domain.entity.Result
import com.dpfht.democityweather.domain.usecase.GetAllCityUseCase
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
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.eq
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class AddCityWeatherViewModelTest {

  private val testDispatcher = UnconfinedTestDispatcher()

  @get:Rule
  val instantTaskExecutionRule = InstantTaskExecutorRule()

  private lateinit var viewModel: AddCityWeatherViewModel

  @Mock
  private lateinit var getAllCityUseCase: GetAllCityUseCase

  @Mock
  private lateinit var addCityAdapter: AddCityAdapter

  @Mock
  private lateinit var errorMessageObserver: Observer<String>

  @Mock
  private lateinit var isLoadingObserver: Observer<Boolean>

  private lateinit var cities: ArrayList<CityEntity>

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)

    cities = arrayListOf()
    viewModel = AddCityWeatherViewModel(getAllCityUseCase, cities, addCityAdapter)
  }

  @Test
  fun `get all city successfully`() = runTest {
    val cityEntity1 = CityEntity(101, "ID", "Jakarta", 1.0, 1.0)
    val cityEntity2 = CityEntity(102, "ID", "Bogor", 2.0, 2.0)
    val list = listOf(cityEntity1, cityEntity2)

    val result = Result.Success(list)
    whenever(getAllCityUseCase()).thenReturn(result)

    viewModel.isShowDialogLoading.observeForever(isLoadingObserver)
    viewModel.start()

    verify(isLoadingObserver).onChanged(eq(false))
    assertTrue(list.size == cities.size)
  }

  @Test
  fun `fail to get all city`() = runTest {
    val msg = "this is an error message"
    val result = Result.Error(msg)
    whenever(getAllCityUseCase()).thenReturn(result)

    viewModel.isShowDialogLoading.observeForever(isLoadingObserver)
    viewModel.toastMessage.observeForever(errorMessageObserver)
    viewModel.start()

    verify(isLoadingObserver).onChanged(eq(false))
    inOrder(errorMessageObserver) {
      verify(errorMessageObserver).onChanged(eq(msg))
      verify(errorMessageObserver).onChanged(eq(""))
    }
  }
}
