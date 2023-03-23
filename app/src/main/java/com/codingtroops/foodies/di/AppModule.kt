package com.codingtroops.foodies.di

import com.codingtroops.foodies.data.remote.api.Service
import com.codingtroops.foodies.data.repository.FoodMenuRepository
import com.codingtroops.foodies.data.repository.impl.FoodMenuRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    companion object {
        const val API_URL = "https://www.themealdb.com/api/json/v1/1/"
    }

    @Provides
    @Singleton
    fun provideAuthInterceptorOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideFoodMenuApiService(
        retrofit: Retrofit
    ): Service {
        return retrofit.create(Service::class.java)
    }

    @Singleton
    @Provides
    fun provideFoodMenuRepository(service: Service): FoodMenuRepository =
        FoodMenuRepositoryImpl(service)
}