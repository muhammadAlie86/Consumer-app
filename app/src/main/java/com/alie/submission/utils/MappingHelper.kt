package com.alie.submission.utils

import android.database.Cursor
import com.alie.submission.database.FavoriteContract.FavoriteColumns.Companion.ID
import com.alie.submission.database.FavoriteContract.FavoriteColumns.Companion.IMG_FAVORITE
import com.alie.submission.database.FavoriteContract.FavoriteColumns.Companion.USERNAME
import com.alie.submission.model.User

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