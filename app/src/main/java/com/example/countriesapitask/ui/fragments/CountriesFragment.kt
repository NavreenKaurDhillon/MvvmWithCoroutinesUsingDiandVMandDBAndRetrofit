package com.example.countriesapitask.ui.fragments

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countriesapitask.CountryApplication
import com.example.countriesapitask.R
import com.example.countriesapitask.adapters.AdapterCountries
import com.example.countriesapitask.databinding.FragmentCountriesScreenBinding
import com.example.countriesapitask.interfaces.IListeners
import com.example.countriesapitask.models.Country
import com.example.countriesapitask.utils.Constants
import com.example.countriesapitask.utils.showVisibility
import com.example.countriesapitask.viewmodels.MainViewModel
import com.example.countriesapitask.viewmodels.ViewModelFactory
import javax.inject.Inject

class CountriesFragment : Fragment() {
    companion object{
        var editClicked : Boolean? = null
    }
    lateinit var countriesScreenBinding: FragmentCountriesScreenBinding
    lateinit var mainViewModel: MainViewModel
    var countryList = ArrayList<Country>()
    var selectedCountry: Country? = null
    lateinit var countriesAdapter: AdapterCountries
    val tempList = ArrayList<Country>()
    val handler= Handler()
    var counterA :Int =0
    var deletedItems = ArrayList<Country>()
    var deletedCounter : Int=1


    @Inject
    lateinit var mainViewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        countriesScreenBinding = FragmentCountriesScreenBinding.inflate(inflater)
        return countriesScreenBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.application as CountryApplication).applicationComponent.injectCountry(this)

        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

        getData()
        setListeners()
        Log.d(TAG, "onViewCreated: /// edited ="+ editClicked)
            startAutoRemoveCountries(counterA)

        countriesScreenBinding.backBT.setOnClickListener {
            findNavController().navigate(R.id.dashboardFragment)
        }

         countriesScreenBinding.swipeLayout.setOnRefreshListener{
             if (editClicked == true && deletedItems.size!=0)
             {
                 if (countryList.get(0).country_name.equals(deletedItems.get(deletedItems.size-1).country_name)==false)
                 {
                     countryList.add(0, deletedItems.get(deletedItems.size-1))
                     deletedItems.removeAt(deletedItems.size - 1)
                     countriesAdapter.notifyItemInserted(0)
                     countriesScreenBinding.countriesRV.scrollToPosition(0)
                 }

             }
            countriesScreenBinding.swipeLayout.isRefreshing = false
        }
    }

    private fun getData() {
        Log.d(ContentValues.TAG, "getData: ")
        mainViewModel.liveList.observe(viewLifecycleOwner, Observer {
            Log.d(ContentValues.TAG, "getData: //// s9ze = "+it.data.countries.size)
            countryList.clear()
            for (i in 0 until 10){
                val data = it.data.countries
                countryList.add(
                    Country(
                        data.get(i).country_code.toString(),
                        data.get(i).country_id.toString(),
                        data.get(i).country_name.toString(),
                        data.get(i).image.toString(),
                        data.get(i).iso_code_3.toString(),
                        data.get(i).phone_code.toString(),
                        data.get(i).status.toString(),
                        false
                    )
                )
            }
            Log.d(
                ContentValues.TAG, "onCreate: "+countryList.size,
            )
            initAdapter(countryList)
        })

    }
    private fun initAdapter(countryList: ArrayList<Country>) {
        Log.d(ContentValues.TAG, "initAdapter: "+countryList.size)
        countriesAdapter = AdapterCountries(countryList, object : IListeners.IClickListener {

            override fun onItemClicked(model: Any, pos: Int) {
                if (model is Country) {
                    model.isSelected = true
                    selectedCountry?.isSelected = false
                    selectedCountry = model
                    countriesScreenBinding.continueBT.showVisibility()
                    countryList.get(pos).isSelected=true
                    Log.d(TAG, "onItemClicked: ///"+countryList.get(pos).country_name)
                    Constants.SELECTED_COUNTRY_NAME= countryList.get(pos).country_name
                    Constants.SELECTED_COUNTRY_CODE= countryList.get(pos).phone_code.toString().toInt()
                    Constants.SELECTED_COUNTRY_FLAG= countryList.get(pos).image
                    countriesAdapter.updateList(countryList, null)
                    if (editClicked == true)
                    {
                        startAutoRemoveCountries(0)
                    }
                }
            }

            override fun onSearchItemClicked(model: Any, pos : Int)
            {
                Log.d(TAG, "onSearchItemClicked: ///"+pos)
            }

        })
        countriesScreenBinding.countriesRV.layoutManager = LinearLayoutManager(requireContext())
        countriesScreenBinding.countriesRV.adapter = countriesAdapter
    }
    private fun setListeners() {
        Log.d(ContentValues.TAG, "setListeners: //")
        countriesScreenBinding.searchTV.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                Log.d(ContentValues.TAG, "beforeTextChanged: ///"+p0)
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

                searchCountry(s.toString())
                Log.d(ContentValues.TAG, "onTextChanged: ///"+s)

            }

            override fun afterTextChanged(p0: Editable?) {
                Log.d(ContentValues.TAG, "afterTextChanged: ///+="+p0)
            }

        })

        countriesScreenBinding.continueBT.setOnClickListener {
            if (selectedCountry != null) {
                findNavController().navigate(R.id.dashboardFragment)
            } else {
                Toast.makeText(requireActivity(), "Please select your country", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun searchCountry(text: String?) {
        val listTemp = ArrayList<Country>()

        for (d in countryList) {
            if (text?.let { d.country_name?.lowercase()?.contains(it.lowercase()) }!!) {
                listTemp.add(d)
            }
        }
        //update recyclerview
        countriesAdapter.updateList(listTemp, text?.lowercase()!!)
    }
    private fun startAutoRemoveCountries(count: Int) {

        Log.d(TAG, "startAutoRemoveCountries: /// country size ="+countryList.size)
        Log.d(TAG, "startAutoRemoveCountries: /// live l;ist size ="+mainViewModel.liveList)
        if (countryList!=null && editClicked == true) {
            Handler(Looper.myLooper()!!).postDelayed({
                Log.d(TAG, "startAutoRemoveCountries: /// live cc list size ="+countryList.size)

                if (countryList.size!=0)
                {
                    selectedCountry = countryList.get(count)
                    Log.d(TAG, "startAutoRemoveCountries: //"+count)
                    if (selectedCountry!=null)
                    {
                        deletedItems.add(selectedCountry!!)
                        if (countryList.size!=0)
                        {
                            countryList.remove(selectedCountry)
                        }

                    }

                    countriesAdapter.notifyItemRemoved(0)
                    startAutoRemoveCountries(count )
                }
                else{
                    getData()
//            startAutoRemoveCountries(counterA)
                }
            }, 10000)
        }

    }



}

