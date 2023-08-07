package com.example.countriesapitask.models

data class Country(
    val country_code: String,
    val country_id: String,
    val country_name: String,
    val image: String,
    val iso_code_3: String,
    val phone_code: String,
    val status: String,
    var isSelected : Boolean = false

)