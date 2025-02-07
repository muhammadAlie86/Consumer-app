package com.alie.consumerapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alie.consumerapp.model.User
import com.alie.consumerapp.repository.FollowersRepository

class FollowerViewModel : ViewModel(){


    private val repository = FollowersRepository()
    val listFollower : LiveData<List<User>>
    init {
        this.listFollower = repository.listFollower
    }

    fun getFollower(context: Context,username: String,part : String){
        repository.getFollower(context,username,part)
    }
}