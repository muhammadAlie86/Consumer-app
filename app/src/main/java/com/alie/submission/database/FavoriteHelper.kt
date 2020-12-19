package com.alie.submission.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.alie.submission.database.FavoriteContract.FavoriteColumns.Companion.ID
import com.alie.submission.database.FavoriteContract.FavoriteColumns.Companion.TABLE_NAME
import java.sql.SQLException

class FavoriteHelper (context: Context) {

    companion object{
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var databaseHelper: DatabaseHelper
        private lateinit var database : SQLiteDatabase
        private var INSTANCE : FavoriteHelper? = null

        fun getInstance(context: Context) : FavoriteHelper =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: FavoriteHelper(context)
            }
    }
    init {
        databaseHelper = DatabaseHelper(context)
    }
    @Throws(SQLException::class)
    fun open(){
        database  = databaseHelper.writableDatabase
    }
    fun close(){
        databaseHelper.close()

        if (database.isOpen){
            database.close()
        }
    }

    fun queryAll() : Cursor{
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$ID ASC"
        )
    }
    fun queryById () : Cursor{

        return  database.query(
            DATABASE_TABLE,
            null,
            "ID = ?" ,
             arrayOf(ID),
            null,
            null,
            null,
            null
        )
    }
    fun insert(values: ContentValues?) : Long{
        return database.insert(DATABASE_TABLE,null,values)
    }
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$ID = '$id'", null)
    }

    fun checked (id : String) : Boolean {
        database = databaseHelper.writableDatabase
        val querySelect = "SELECT * FROM $DATABASE_TABLE WHERE $ID = ?"
        val result = database.rawQuery(querySelect, arrayOf(id))
        var checkStatus = false
        var i = 0
        do {
            if (result.moveToFirst()) {
                checkStatus = !checkStatus
            }
            i++
        } while (result.moveToNext())


        result.close()
        return checkStatus
    }
}
