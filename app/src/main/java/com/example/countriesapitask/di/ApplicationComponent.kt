package com.example.countriesapitask.di

import com.example.countriesapitask.MainActivity
import com.example.countriesapitask.ui.fragments.CountriesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ApplicationComponent {

    fun injectMain (mainActivity: MainActivity)
    fun injectCountry (countriesFragment: CountriesFragment)
}