package com.example.karn.movie.mainadapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.karn.movie.modelmovies.Movie
import com.example.karn.movie.R
import com.example.karn.movie.movieapi.BaseUrl
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_movie.view.*
import kotlinx.android.synthetic.main.recyclerview_desing2.view.*

class MoviesAdapter(var movies: List<Movie>, var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var callback: MovieListListener? = null

    interface MovieListListener {
        fun onClick(movie: Movie)
    }

    fun setOnClickCallback(callBack: MovieListListener) {
        this.callback = callBack
    }

    fun setItem(items: List<Movie>) {
        movies = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.list_item_movie -> MovieViewHolder(view)
            R.layout.recyclerview_desing2 -> MovieViewHolder2(view)
            else -> MovieViewHolder3(view)
        }
    }

    override fun getItemCount(): Int = movies.size

    override fun getItemViewType(position: Int): Int = if (position == 0) {
        R.layout.recyclerview_desing2
    } else {
        R.layout.list_item_movie
    }

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) { //TODO การ set View Holder ตัวที่ 1
        fun onBindata(movie: Movie) {
            itemView.title.text = movie.title
            itemView.subtitle.text = movie.releaseDate
            itemView.rating.text = movie.voteAverage.toString()
            Glide.with(context)
                    .load(BaseUrl.textbaseurl + movie.posterPath)
                    .into(itemView.TV)
            itemView.TV.setOnClickListener { callback?.onClick(movie) }
        }
    }

    inner class MovieViewHolder2(view: View) : RecyclerView.ViewHolder(view) { //TODO การ set View Holder ตัวที่ 2
        fun onBindata2(movie: Movie) {
            Picasso.with(context)
                    .load(BaseUrl.textbaseurl + movie.backdropPath)
                    .into(itemView.imageviewtital)
        }
    }

    inner class MovieViewHolder3(view: View) : RecyclerView.ViewHolder(view) {

        fun onBindata(movie: Movie) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) { //TODO เป็นส่วนที่ ใช้ในการ is holder
        return when (holder) {
            is MovieViewHolder2 -> holder.onBindata2(movies[position])
            is MovieViewHolder -> holder.onBindata(movies[position])
            else -> (holder as MovieViewHolder3).onBindata(movies[position])
        }
    }
}