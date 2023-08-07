package com.example.countriesapitask.di

import com.example.countriesapitask.api.ApiInterface
import com.example.countriesapitask.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//class to provide instances
@Module
class AppModule {

    @Singleton  //single object creation
    @Provides
    fun getRetrofitInstance() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun createApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}