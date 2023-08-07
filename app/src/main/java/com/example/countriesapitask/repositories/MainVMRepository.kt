package com.example.countriesapitask.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.countriesapitask.api.ApiInterface
import com.example.countriesapitask.models.CountriesDataList
import javax.inject.Inject

class MainVMRepository @Inject constructor(private val apiInterface: ApiInterface) {
//    private val catMutableLiveData = MutableLiveData<DataList>()
//    val catLiveData : LiveData<DataList>
//        get() = catMutableLiveData
//    suspend fun getCatsData()  {
//       val catsData = apiInterface.getCatsData()
//        if (catsData.isSuccessful && catsData.body()!=null){
//            catMutableLiveData.postValue(catsData.body())
//        }
//    }
    private val mutableCountriesList = MutableLiveData<CountriesDataList>()
    val liveList : LiveData<CountriesDataList>
        get() = mutableCountriesList

    suspend fun getCountriesList(){
        val result = apiInterface.getCountries()
        if (result.isSuccessful && result.body()!=null){
            mutableCountriesList.postValue(result.body())
        }
    }

}