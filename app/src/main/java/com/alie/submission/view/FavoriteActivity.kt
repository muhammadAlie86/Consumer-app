package com.alie.submission.view

import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alie.submission.R
import com.alie.submission.adapter.FavoriteAdapter
import com.alie.submission.database.FavoriteContract.FavoriteColumns.Companion.CONTENT_URI
import com.alie.submission.database.FavoriteHelper
import com.alie.submission.model.User
import com.alie.submission.utils.MappingHelper
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }
    private lateinit var adapter: FavoriteAdapter
    private lateinit var favoriteHelper: FavoriteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        rvFavorite.setHasFixedSize(true)
        adapter = FavoriteAdapter(this)
        rvFavorite.adapter = adapter

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        imgBackFavorite.setOnClickListener {
            val intentSet = Intent(this, SettingActivity::class.java)
            startActivity(intentSet)
            finish()
        }
        val handlerThread = HandlerThread("DATA_OBSERVER")
        handlerThread.start()

        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                loadUserAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI,true,myObserver)

        if (savedInstanceState == null) {
            loadUserAsync()
        }else {
            val list = savedInstanceState.getParcelableArrayList<User>(EXTRA_STATE)
            if (list != null) {
                adapter.listFavorite = list
            }
        }
        rvFavorite.layoutManager = LinearLayoutManager(this)


    }
    private fun loadUserAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            pbFav.visibility = View.VISIBLE
            val deferredUser = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI,null,null,null,null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            pbFav.visibility = View.INVISIBLE
            val user = deferredUser.await()

            if (user.isNotEmpty()) {
                rvFavorite.adapter = FavoriteAdapter(this@FavoriteActivity, user as ArrayList<User>)
            } else {
                imageEmpty.visibility = View.VISIBLE
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE,adapter.listFavorite )
    }

    override fun onResume() {
        super.onResume()
        loadUserAsync()
    }

}





