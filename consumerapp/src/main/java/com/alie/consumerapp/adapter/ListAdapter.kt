package com.alie.consumerapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.alie.consumerapp.R
import com.alie.consumerapp.model.User
import com.alie.consumerapp.utils.Listener
import com.alie.consumerapp.view.DetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class ListAdapter internal constructor(
    private val context: Context,
    private var listData: List<User> = ArrayList()

): RecyclerView.Adapter<ListAdapter.Myholder>(),
    Listener {


    fun setData(listData: List<User>) {
        this.listData = listData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return Myholder(view)
    }

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: Myholder, position: Int) {
        holder.bind(listData[position])
        holder.itemView.setOnClickListener { onClick(position) }
    }

    inner class Myholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        private val imageList: ImageView = itemView.findViewById(R.id.imageList)

        fun bind(user: User) {
            tvUsername.text = user.name

            Glide.with(context)
                .load(user.avatarUrl)
                .apply(RequestOptions().override(150, 150))
                .into(imageList)
        }
    }

    override fun onClick(position: Int) {

        val list = listData[position].name
        Toast.makeText(context, list, Toast.LENGTH_SHORT).show()

        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.DATA_USER, listData[position])
        context.startActivity(intent)
    }
}