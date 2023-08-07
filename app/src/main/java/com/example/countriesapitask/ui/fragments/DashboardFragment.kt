package com.example.countriesapitask.ui.fragments

import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.countriesapitask.R
import com.example.countriesapitask.databinding.FragmentDashboardScreenBinding
import com.example.countriesapitask.models.ProfileData
import com.example.countriesapitask.utils.Constants


class DashboardFragment : Fragment(), View.OnClickListener  {
    
    lateinit var dashboardScreenBinding: FragmentDashboardScreenBinding
    private var mDetector: GestureDetector? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardScreenBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard_screen, container, false)
        return dashboardScreenBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardScreenBinding.profilePic.setOnClickListener(this)
        dashboardScreenBinding.editTV.setOnClickListener(this)
        dashboardScreenBinding.searchLL.setOnClickListener(this)
        dashboardScreenBinding.close.setOnClickListener(this)

        //data binding
        val profileData = ProfileData("Lucile Barrett",
            "New York, NY")

        Glide.
        with(requireContext())
            .load(R.drawable.img)
            .circleCrop()
            .into(dashboardScreenBinding.profilePic)

        dashboardScreenBinding.profileData = profileData
        dashboardScreenBinding.scrollView.setOnScrollChangeListener { view, i, i2, i3, i4 ->
            dashboardScreenBinding.scrollView.scrollTo(0,850)
            dashboardScreenBinding.close.visibility = View.VISIBLE
        }

        if (Constants.SELECTED_COUNTRY_NAME!=null){
            dashboardScreenBinding.countryName.setText(Constants.SELECTED_COUNTRY_NAME)
            dashboardScreenBinding.countryCode.setText("(+"+Constants.SELECTED_COUNTRY_CODE.toString()+")")
            Glide.
            with(requireContext())
                .load(Constants.SELECTED_COUNTRY_FLAG)
                .into(dashboardScreenBinding.flagIcon)

        }


        //set country if saved
        if (Constants.SELECTED_COUNTRY_NAME!=null)
        {
            dashboardScreenBinding.searchLL.visibility = View.GONE
            dashboardScreenBinding.countryDetail.visibility = View.VISIBLE
            dashboardScreenBinding.editTV.visibility = View.VISIBLE
        }
        else{
            dashboardScreenBinding.searchLL.visibility = View.VISIBLE
            dashboardScreenBinding.countryDetail.visibility = View.GONE
        }
    }

    override fun onClick(p0: View?) {
        val v = p0?.id
        when(v)
        {
            R.id.editTV ->{
                CountriesFragment.editClicked= true
                findNavController().navigate(R.id.countriesFragment)
            }
            R.id.profilePic ->{
                dashboardScreenBinding.scrollView.scrollTo(0,650)
            }
            R.id.searchLL ->{
//                CountriesFragment.editClicked = false
                findNavController().navigate(R.id.countriesFragment)
            }
            R.id.close ->{
                findNavController().navigate(R.id.dashboardFragment)
            }
        }
    }
}