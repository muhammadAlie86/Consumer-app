package com.alie.consumerapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alie.consumerapp.model.DetailUser
import com.alie.consumerapp.repository.DetailRepository

class DetailViewModel : ViewModel(){

    private val detailRepository = DetailRepository()
    val userDetail : LiveData<DetailUser>

    init {
        this.userDetail = detailRepository.userDetail
    }

    fun getUser(context: Context,username : String){
        detailRepository.getUser(context,username)
    }
}