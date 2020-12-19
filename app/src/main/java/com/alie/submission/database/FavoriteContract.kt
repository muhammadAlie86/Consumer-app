package com.alie.submission.database

import android.net.Uri
import android.provider.BaseColumns

object FavoriteContract {

    const val AUTHORITY ="com.alie.submission"
    const val SCHEME = "content"

    class FavoriteColumns : BaseColumns {

        companion object{

            const val TABLE_NAME = "favorite"
            const val ID = "id"
            const val USERNAME = "username"
            const val IMG_FAVORITE = "img_favorite"

            val CONTENT_URI : Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()

        }

    }
}