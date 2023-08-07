package com.example.countriesapitask.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.countriesapitask.repositories.MainVMRepository
import javax.inject.Inject

class ViewModelFactory @Inject constructor
(private val mainVMRepository: MainVMRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(mainVMRepository) as T
    }

}