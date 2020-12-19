package com.alie.submission.view

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alie.submission.R
import com.alie.submission.adapter.PagerAdapter
import com.alie.submission.database.FavoriteContract.FavoriteColumns.Companion.CONTENT_URI
import com.alie.submission.database.FavoriteContract.FavoriteColumns.Companion.ID
import com.alie.submission.database.FavoriteContract.FavoriteColumns.Companion.IMG_FAVORITE
import com.alie.submission.database.FavoriteContract.FavoriteColumns.Companion.USERNAME
import com.alie.submission.database.FavoriteHelper
import com.alie.submission.model.User
import com.alie.submission.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val DATA_USER = "detailUser"
    }

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var favoriteHelper: FavoriteHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbarDetail)
        setSupportActionBar(toolbar)

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        favoriteHelper = FavoriteHelper.getInstance(this)
        favoriteHelper.open()

        setupViewPager()
        getDataUser()
        progressBarDetail.visibility = View.VISIBLE

        imgBack.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)


        }
    }
    private fun setupViewPager(){

        viewPager.adapter = PagerAdapter(this)

        TabLayoutMediator(tabLayout,viewPager,
            TabLayoutMediator.TabConfigurationStrategy{tab, position ->
                when (position){
                    0 -> tab.text = "Followers"
                    1 -> tab.text = "Following"
                }

            }).attach()
    }

    private fun getDataUser(){

        val getDataUser = intent.getParcelableExtra<User>(DATA_USER)


        if (getDataUser != null) {
            detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
            detailViewModel.getUser(this,getDataUser.name)

            detailViewModel.userDetail.observe(this, Observer {
                Glide.with(this)
                    .load(getDataUser.avatarUrl)
                    .apply(RequestOptions().override(100,100))
                    .into(detailImageView)

                detailUsername.text = it.username
                if (it.company.isNullOrEmpty()){
                    detailCompany.visibility = View.GONE
                }else{
                    detailCompany.text = it.company
                    detailCompany.visibility = View.VISIBLE
                }
                if (it.location.isNullOrEmpty()){
                    detailLocation.visibility = View.GONE
                }else{
                    detailLocation.text = it.location
                    detailLocation.visibility = View.VISIBLE
                }
                if (it.followers.toString().isEmpty()){
                    detailFollowers.visibility = View.GONE
                }else{
                    detailFollowers.text = it.followers.toString()
                    detailFollowers.visibility = View.VISIBLE
                }
                if (it.following.toString().isEmpty()){
                    detailFollowing.visibility = View.GONE
                }else{
                    detailFollowing.text = it.following.toString()
                    detailFollowing.visibility = View.VISIBLE
                }
                if (it.repo.toString().isEmpty()){
                    detailRepos.visibility = View.GONE
                }else{
                    detailRepos.text = it.repo.toString()
                    detailRepos.visibility = View.VISIBLE
                }
                detailImageView.visibility = View.VISIBLE
                detailUsername.visibility = View.VISIBLE
                detailFollowers.visibility = View.VISIBLE
                detailFollowing.visibility = View.VISIBLE
                detailRepos.visibility = View.VISIBLE
                detailCompany.visibility = View.VISIBLE
                detailLocation.visibility = View.VISIBLE
                progressBarDetail.visibility = View.INVISIBLE
                btnFav.visibility = View.VISIBLE
            })

            if (favoriteHelper.checked(getDataUser.id.toString())){

                btnFav.visibility = View.GONE
                btnUnFav.visibility = View.VISIBLE
            }
            btnFav.setOnClickListener {

                val cv = ContentValues()

                cv.put(ID,getDataUser.id)
                cv.put(USERNAME,getDataUser.name)
                cv.put(IMG_FAVORITE,getDataUser.avatarUrl)

                contentResolver.insert(CONTENT_URI,cv)

                btnFav.visibility = View.INVISIBLE
                btnUnFav.visibility = View.VISIBLE
            }
            btnUnFav.setOnClickListener {

                contentResolver.delete(Uri.parse(CONTENT_URI.toString() + "/" + getDataUser.id),null,null)

                btnFav.visibility = View.VISIBLE
                btnUnFav.visibility = View.INVISIBLE
            }

        }
    }



}