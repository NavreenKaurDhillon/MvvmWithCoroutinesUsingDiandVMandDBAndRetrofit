package com.example.countriesapitask.adapters

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.countriesapitask.databinding.CountryItemBinding
import com.example.countriesapitask.interfaces.IListeners
import com.example.countriesapitask.models.Country
import com.example.countriesapitask.utils.Constants

class AdapterCountries(var list:ArrayList<Country>, val listener: IListeners.IClickListener): RecyclerView.Adapter<AdapterCountries.ViewHolderr>() {

    var searchText =""

    class ViewHolderr(val binding:CountryItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(country : Country){
            Log.d(TAG, "bind: "+country.country_name+"=="+Constants.SELECTED_COUNTRY_NAME)
            if (country.isSelected || country.country_name==Constants.SELECTED_COUNTRY_NAME){
                // set highlighted bg
                binding.selectedTV.visibility=View.VISIBLE
                binding.codeTV.visibility=View.GONE
            }else{
                binding.selectedTV.visibility=View.GONE
                binding.codeTV.visibility=View.VISIBLE

                //set  common background
            }

            Glide.
            with(binding.flagIV.context)
                .load(country.image)
                .circleCrop()
                .into(binding.flagIV)


            binding.nameTV.setText(country.country_name)
            binding.codeTV.setText("(+"+country.phone_code+")")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderr {
        val itemBinding =
            CountryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderr(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderr, position: Int) {
           holder.bind(list.get(position))
           holder.itemView.setOnClickListener {
               Log.d(TAG, "onBindViewHolder: ... clicked "+list.get(position).country_name)
               listener.onItemClicked(list.get(position),position)
           }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateList(listTemp: ArrayList<Country>, text: String?) {
        Log.d(TAG, "updateList: ///")
        list = listTemp
        if (text != null) {
            this.searchText = text
        }//highlight
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}