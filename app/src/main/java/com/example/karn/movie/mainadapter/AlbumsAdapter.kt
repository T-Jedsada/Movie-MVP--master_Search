package com.example.karn.movie.mainadapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.karn.movie.modelmovies.Movie
import com.example.karn.movie.R
import com.example.karn.movie.movieapi.BaseUrl
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.album_card.view.*
import kotlin.properties.Delegates

typealias AlbumsListener = (movie: Movie) -> Unit

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.MyViewHolder>() {

    var callback: AlbumsListener? = null

    var list: List<Movie> by Delegates.observable(listOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder =
            MyViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.album_card, viewGroup, false))

    override fun onBindViewHolder(viewHolder: MyViewHolder, i: Int) = viewHolder.onBindData(list[i])

    override fun getItemCount(): Int = list.size

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun onBindData(movie: Movie) {
            itemView.title.text = movie.title
            Picasso.with(view.context)
                    .load(BaseUrl.textbaseurl + movie.backdropPath)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading)
                    .into(itemView.thumbnail)
            itemView.thumbnail.setOnClickListener { callback?.invoke(movie) }
        }
    }
}