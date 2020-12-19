package com.alie.submission.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.alie.submission.database.FavoriteContract.FavoriteColumns.Companion.TABLE_NAME

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION){

    companion object {
        private const val DATABASE_NAME = "favorite.db"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_FAVORITE = "CREATE TABLE $TABLE_NAME" +
                " (${FavoriteContract.FavoriteColumns.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${FavoriteContract.FavoriteColumns.USERNAME} TEXT NOT NULL," +
                " ${FavoriteContract.FavoriteColumns.IMG_FAVORITE} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_FAVORITE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}