package com.example.karn.movie.mainadapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.karn.movie.modelmovies.Movie
import com.example.karn.movie.R
import com.example.karn.movie.movieapi.BaseUrl
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.album_card.view.*


class AlbumsAdapter(var albumList: List<Movie>, var mContext: Context) : RecyclerView.Adapter<AlbumsAdapter.MyViewHolder>() {
    var callback: AlbumsListener? = null

    interface AlbumsListener {
        fun onClick(movie: Movie)
    }

    fun setOnClickCallback(callBack: AlbumsListener) {
        this.callback = callBack
    }

    fun setItem(items: List<Movie>) {
        albumList = items
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.album_card, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, i: Int) = viewHolder.onBindata(albumList[i])

    override fun getItemCount(): Int {
        return albumList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun onBindata(movie: Movie) {
            itemView.title.setText(movie.title)
            val setbaseurl = BaseUrl()
            Picasso.with(mContext)
                    .load(setbaseurl.textbaseurl + movie.backdropPath)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading)
                    .into(itemView.thumbnail)

            itemView.thumbnail.setOnClickListener {
                callback?.onClick(movie)
            }
        }
    }
}
