package com.alie.consumerapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alie.consumerapp.R
import com.alie.consumerapp.adapter.ListAdapter
import com.alie.consumerapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var listAdapter : ListAdapter
    private lateinit var imgWallpaper : ImageView
    private lateinit var tvWallpaper : TextView
    private lateinit var searchView: SearchView
    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        imgWallpaper = findViewById(R.id.wallpaper)
        tvWallpaper = findViewById(R.id.tvWallpaper)
        recyclerView = findViewById(R.id.recycler_view)
        searchView = findViewById(R.id.searchView)
        recyclerView.setHasFixedSize(true)

        listAdapter = ListAdapter(this)
        searchUser()
        showRecyclerView()

        imgSettingMain.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)

        }
    }
    private fun showRecyclerView(){
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            listAdapter = ListAdapter(this@MainActivity)
            adapter = listAdapter
        }
    }
    private fun searchUser(){

        searchView.queryHint = "search user"
        searchView.requestFocusFromTouch()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    mainViewModel.getSearch(this@MainActivity,query.toString())
                    mainViewModel.searchData.observe(this@MainActivity, Observer {

                        listAdapter.setData(it)
                        progressBar.visibility = View.GONE
                    })
                    tvWallpaper.visibility = View.INVISIBLE
                    imgWallpaper.visibility = View.INVISIBLE
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.VISIBLE
                }
                else{
                    return false
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}





