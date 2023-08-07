package com.example.countriesapitask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.countriesapitask.databinding.ActivityMainBinding
import com.example.countriesapitask.models.Country
import com.example.countriesapitask.viewmodels.MainViewModel
import com.example.countriesapitask.viewmodels.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding

    lateinit var mainViewModel: MainViewModel
    val countries = ArrayList<Country>()
    lateinit var navController: NavController

    @Inject
    lateinit var mainViewModelFactory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)


        val controller =
            supportFragmentManager.findFragmentById(R.id.main_controller) as NavHostFragment
        navController = controller.navController

        navController.navigate(R.id.dashboardFragment)

//        initializeProfileData()
        (application as CountryApplication).applicationComponent.injectMain(this)

        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
//
//        mainViewModel.catLiveData.observe(this,{
//            Log.d(TAG, "onCreate: /// data change occurred"+mainViewModel.catLiveData.value?.entries?.get(0)?.Description)
//        })


         fun initializeProfileData() {

//        profileData.name ="Lucile Barett"
//        profileData.location = "new york, ny"
//        profileData.alertsCount = 6
//        profileData.placesCount = 40
//        profileData.shotsCount = 60
//        profileData.friendsCount = 60
        }

    }
}
