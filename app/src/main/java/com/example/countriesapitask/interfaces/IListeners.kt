package com.example.countriesapitask.interfaces


interface IListeners {


    interface IClickListener {
        fun onItemClicked(model: Any, pos: Int)
        fun onSearchItemClicked(model: Any, pos: Int)
    }




}
