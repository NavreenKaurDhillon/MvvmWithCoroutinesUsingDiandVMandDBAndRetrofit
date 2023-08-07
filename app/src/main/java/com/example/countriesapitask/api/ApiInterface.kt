package com.example.countriesapitask.api

import com.example.countriesapitask.models.CountriesDataList
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

//    @GET("entries")
//    suspend fun getCatsData() : Response<DataList>

    @GET("getCountries")
    suspend fun getCountries() : Response<CountriesDataList>



}