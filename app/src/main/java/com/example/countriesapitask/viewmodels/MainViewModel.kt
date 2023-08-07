package com.example.countriesapitask.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countriesapitask.models.CountriesDataList
import com.example.countriesapitask.repositories.MainVMRepository
import kotlinx.coroutines.launch

class MainViewModel(private val mainVMRepository: MainVMRepository) : ViewModel(){


    init {
        viewModelScope.launch {
            mainVMRepository.getCountriesList()
        }
    }
    val liveList : LiveData<CountriesDataList>
        get() = mainVMRepository.liveList

}