package com.alie.consumerapp.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.alie.consumerapp.api.RetrofitInstance
import com.alie.consumerapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingRepository {


    val listFollowing : MutableLiveData<List<User>> = MutableLiveData()

    fun getFollowing(context: Context, username : String, part: String) {

        val service = RetrofitInstance.apiClient.getFollowing(username,part)

        service.enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(context,"something wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    listFollowing.value = response.body()
                }
            }
        })
    }
}