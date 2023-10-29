package com.dpfht.android.democityweather.framework.di.module

import com.dpfht.democityweather.domain.entity.CityEntity
import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.repository.AppRepository
import com.dpfht.democityweather.domain.usecase.AddCityWeatherUseCase
import com.dpfht.democityweather.domain.usecase.AddCityWeatherUseCaseImpl
import com.dpfht.democityweather.domain.usecase.DeleteCityWeatherUseCase
import com.dpfht.democityweather.domain.usecase.DeleteCityWeatherUseCaseImpl
import com.dpfht.democityweather.domain.usecase.GetAllCityUseCase
import com.dpfht.democityweather.domain.usecase.GetAllCityUseCaseImpl
import com.dpfht.democityweather.domain.usecase.GetAllCityWeatherUseCase
import com.dpfht.democityweather.domain.usecase.GetAllCityWeatherUseCaseImpl
import com.dpfht.democityweather.domain.usecase.GetCountryUseCase
import com.dpfht.democityweather.domain.usecase.GetCountryUseCaseImpl
import com.dpfht.democityweather.domain.usecase.GetCurrentWeatherUseCase
import com.dpfht.democityweather.domain.usecase.GetCurrentWeatherUseCaseImpl
import com.dpfht.democityweather.domain.usecase.GetForecastUseCase
import com.dpfht.democityweather.domain.usecase.GetForecastUseCaseImpl
import com.dpfht.democityweather.domain.usecase.GetStreamIsDBInitializedUseCase
import com.dpfht.democityweather.domain.usecase.GetStreamIsDBInitializedUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

  @Provides
  fun provideGetAllCityUseCase(appRepository: AppRepository): GetAllCityUseCase {
    return GetAllCityUseCaseImpl(appRepository)
  }

  @Provides
  fun provideCities(): ArrayList<CityEntity> {
    return arrayListOf()
  }

  @Provides
  fun provideGetCountryUseCase(appRepository: AppRepository): GetCountryUseCase {
    return GetCountryUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetStreamIsDBInitializedUseCase(appRepository: AppRepository): GetStreamIsDBInitializedUseCase {
    return GetStreamIsDBInitializedUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetAllCityWeatherUseCase(appRepository: AppRepository): GetAllCityWeatherUseCase {
    return GetAllCityWeatherUseCaseImpl(appRepository)
  }

  @Provides
  fun provideAddCityWeatherUseCase(appRepository: AppRepository): AddCityWeatherUseCase {
    return AddCityWeatherUseCaseImpl(appRepository)
  }

  @Provides
  fun provideDeleteCityWeatherUseCase(appRepository: AppRepository): DeleteCityWeatherUseCase {
    return DeleteCityWeatherUseCaseImpl(appRepository)
  }

  @Provides
  fun provideCityWeathers(): ArrayList<CityWeatherEntity> {
    return arrayListOf()
  }

  @Provides
  fun provideGetCurrentWeatherUseCase(appRepository: AppRepository): GetCurrentWeatherUseCase {
    return GetCurrentWeatherUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetForecastUseCase(appRepository: AppRepository): GetForecastUseCase {
    return GetForecastUseCaseImpl(appRepository)
  }
}
