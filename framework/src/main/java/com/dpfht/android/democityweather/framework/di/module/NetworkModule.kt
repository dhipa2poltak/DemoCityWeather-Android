package com.dpfht.android.democityweather.framework.di.module

import android.content.Context
import com.dpfht.democityweather.data.datasource.RemoteDataSource
import com.dpfht.android.democityweather.framework.BuildConfig
import com.dpfht.android.democityweather.framework.Constants
import com.dpfht.android.democityweather.framework.data.datasource.remote.RemoteDataSourceImpl
import com.dpfht.android.democityweather.framework.data.datasource.remote.rest.AuthInterceptor
import com.dpfht.android.democityweather.framework.data.datasource.remote.rest.RestService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @Provides
  @Singleton
  fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return httpLoggingInterceptor
  }

  @Provides
  @Singleton
  fun provideAuthInterceptor(): AuthInterceptor {
    return AuthInterceptor()
  }

  @Provides
  @Singleton
  fun provideClient(httpLoggingInterceptor: HttpLoggingInterceptor, authInterceptor: AuthInterceptor): OkHttpClient {
    val httpClientBuilder = OkHttpClient()
      .newBuilder()

    if (BuildConfig.DEBUG) {
      httpClientBuilder.addInterceptor(httpLoggingInterceptor)
    }

    httpClientBuilder.addInterceptor(authInterceptor)

    return httpClientBuilder.build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .addConverterFactory(GsonConverterFactory.create())
      .baseUrl(Constants.BASE_URL)
      .client(okHttpClient)
      .build()
  }

  @Provides
  @Singleton
  fun provideApiService(retrofit: Retrofit): RestService {
    return retrofit.create(RestService::class.java)
  }

  @Provides
  @Singleton
  fun provideRemoteDataSource(@ApplicationContext context: Context, restService: RestService): RemoteDataSource {
    return RemoteDataSourceImpl(context, restService)
  }
}
