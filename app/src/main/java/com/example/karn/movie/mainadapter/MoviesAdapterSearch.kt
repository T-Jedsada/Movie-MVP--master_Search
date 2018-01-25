package com.example.karn.movie.mainadapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.karn.movie.R
import com.example.karn.movie.modelmovies.Movie
import com.example.karn.movie.movieapi.BaseUrl
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_movie.view.*


class MoviesAdapterSearch(var movies: List<Movie>, var context: Context) : RecyclerView.Adapter<MoviesAdapterSearch.MovieViewHolder>() {


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.onBindata(movies[position])

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun onBindata(movie: Movie) {
            itemView.title.setText(movie.title)
            itemView.subtitle.setText(movie.releaseDate)
            itemView.rating.setText(movie.voteAverage.toString())

            val setbaseurl = BaseUrl()
            Picasso.with(context)
                    .load(setbaseurl.textbaseurl + movie.posterPath)
                    .into(itemView.TV)


            itemView.TV.setOnClickListener {
                callback?.onClick(movie)

            }


        }

    }
}