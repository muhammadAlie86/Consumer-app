package com.alie.consumerapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alie.consumerapp.model.User
import com.alie.consumerapp.repository.MainRepository

class MainViewModel: ViewModel() {

    private val repository = MainRepository()
    val searchData: LiveData<List<User>>


    init {
        this.searchData = repository.searchData

    }

    fun getSearch(context: Context,query : String) {
        repository.getSearch(context,query)
    }
}