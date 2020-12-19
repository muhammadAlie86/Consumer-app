package com.alie.consumerapp.utils

import android.database.Cursor
import com.alie.consumerapp.database.FavoriteContract.FavoriteColumns.Companion.ID
import com.alie.consumerapp.database.FavoriteContract.FavoriteColumns.Companion.IMG_FAVORITE
import com.alie.consumerapp.database.FavoriteContract.FavoriteColumns.Companion.USERNAME
import com.alie.consumerapp.model.User

object MappingHelper {

    fun mapCursorToArrayList(favoriteCursor: Cursor?): List<User> {
        val favoriteList = ArrayList<User>()
        favoriteCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(ID))
                val username = getString(getColumnIndexOrThrow(USERNAME))
                val avatarUrl = getString(getColumnIndexOrThrow(IMG_FAVORITE))
                favoriteList.add(User(id,username,avatarUrl))
            }
        }
        return favoriteList
    }
}