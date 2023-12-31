package com.dpfht.android.democityweather.framework.di.module

import com.dpfht.democityweather.data.datasource.LocalDataSource
import com.dpfht.democityweather.data.datasource.RemoteDataSource
import com.dpfht.democityweather.data.repository.AppRepositoryImpl
import com.dpfht.democityweather.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

  @Provides
  @Singleton
  fun provideAppRepository(localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource): AppRepository {
    return AppRepositoryImpl(localDataSource, remoteDataSource)
  }
}
