package com.alie.consumerapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alie.consumerapp.R
import com.alie.consumerapp.adapter.FollowAdapter
import com.alie.consumerapp.model.User
import com.alie.consumerapp.viewmodel.FollowingViewModel
import kotlinx.android.synthetic.main.fragment_follower.progressBarFollow
import kotlinx.android.synthetic.main.fragment_following.*

class FragmentFollowing : Fragment() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var followingAdapter : FollowAdapter
    private lateinit var followingViewModel: FollowingViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvFollowing)
        recyclerView.setHasFixedSize(true)
        followingViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        showRecyclerView()
        showFollowing()
    }

    private fun showRecyclerView(){
        recyclerView.apply {

            layoutManager = LinearLayoutManager(activity)
            followingAdapter = FollowAdapter(requireActivity())
            adapter = followingAdapter
        }
    }
    private fun showFollowing(){

        val getDataGithubUser = activity?.intent?.getParcelableExtra<User>(DetailActivity.DATA_USER)
        if (getDataGithubUser != null) {

            followingViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
            followingViewModel.getFollowing(context!!,getDataGithubUser.name,"1")
            followingViewModel.listFollowing.observe(viewLifecycleOwner, Observer {
                followingAdapter.setDataFollow(it)
                progressBarFollow.visibility = View.GONE
            })

            progressBarFollow.visibility = View.VISIBLE
            rvFollowing.visibility = View.VISIBLE
        }
    }
}