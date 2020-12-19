package com.alie.submission.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.alie.submission.R
import com.alie.submission.model.User
import com.alie.submission.utils.Listener
import com.alie.submission.view.DetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FavoriteAdapter internal constructor(

    private val context: Context,
    var listFavorite : ArrayList<User> = arrayListOf()


): RecyclerView.Adapter<FavoriteAdapter.Myholder>(),Listener{



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return Myholder(view)
    }

    override fun getItemCount() = this.listFavorite.size

    override fun onBindViewHolder(holder: Myholder, position: Int) {
        holder.bind(listFavorite[position])
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

        val list = listFavorite[position].name
        Toast.makeText(context, list, Toast.LENGTH_SHORT).show()

        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.DATA_USER, listFavorite[position])
        context.startActivity(intent)
    }
}