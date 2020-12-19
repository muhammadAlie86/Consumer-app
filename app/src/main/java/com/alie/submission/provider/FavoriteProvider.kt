package com.alie.submission.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.alie.submission.database.FavoriteContract.AUTHORITY
import com.alie.submission.database.FavoriteContract.FavoriteColumns.Companion.CONTENT_URI
import com.alie.submission.database.FavoriteContract.FavoriteColumns.Companion.TABLE_NAME
import com.alie.submission.database.FavoriteHelper

class FavoriteProvider : ContentProvider() {

    companion object{
        private const val FAVORITE = 1
        private const val FAVORITE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var favoriteHelper: FavoriteHelper

        init {
            sUriMatcher.addURI(AUTHORITY,TABLE_NAME, FAVORITE)

            sUriMatcher.addURI(
                AUTHORITY,
                "$TABLE_NAME/#",
                FAVORITE_ID
            )
        }
    }
    override fun onCreate(): Boolean {

        favoriteHelper = FavoriteHelper.getInstance(context as Context)
        favoriteHelper.open()
        return true
    }

    override fun getType(uri: Uri): String? {
        return null
    }
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {

        val cursor : Cursor?
        when (sUriMatcher.match(uri)){
            FAVORITE -> cursor = favoriteHelper.queryAll()
            FAVORITE_ID -> cursor = favoriteHelper.queryById()
            else -> cursor = null
        }
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {

        val added : Long = when(FAVORITE){
            sUriMatcher.match(uri) -> favoriteHelper.insert(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI,null)

        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {

        val deleted: Int = when (FAVORITE_ID) {
            sUriMatcher.match(uri) -> favoriteHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }
}
