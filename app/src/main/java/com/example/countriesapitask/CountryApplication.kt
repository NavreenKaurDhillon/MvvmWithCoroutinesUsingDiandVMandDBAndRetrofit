package com.example.countriesapitask

import android.app.Application
import com.example.countriesapitask.di.ApplicationComponent
import com.example.countriesapitask.di.DaggerApplicationComponent

//define app level instances
class CountryApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder().build()
    }
}