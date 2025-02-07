package com.alie.consumerapp.api

import com.alie.consumerapp.model.DetailUser
import com.alie.consumerapp.model.ResponseUser
import com.alie.consumerapp.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    @GET("search/users?")
    fun getSearch(@Query("q") query : String) : Call<ResponseUser>

    @GET("users/{username}")
    fun getUser(@Path ("username") username : String): Call<DetailUser>

    @GET("users/{username}/followers")
    fun getFollower(@Path("username") username: String,
                    @Query("part") part : String) : Call<List<User>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String,
                     @Query("part") part : String) : Call<List<User>>
}