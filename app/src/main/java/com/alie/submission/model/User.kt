package com.alie.submission.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(

    @SerializedName("id")
    val id: Int,

    @SerializedName("login")
    val name: String,

    @SerializedName("avatar_url")
    val avatarUrl: String


) : Parcelable
