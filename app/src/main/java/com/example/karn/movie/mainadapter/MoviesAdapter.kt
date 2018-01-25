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
        if (viewType == R.layout.list_item_movie) {
            return MovieViewHolder(view)
        }else if(viewType == R.layout.recyclerview_desing2){
            return MovieViewHolder2(view)
        } else {
            return MovieViewHolder3(view)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }


    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return R.layout.recyclerview_desing2
        }else{
             return R.layout.list_item_movie
        }




    }

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) { //TODO การ set View Holder ตัวที่ 1
        fun onBindata(movie: Movie) {
            itemView.title.setText(movie.title)
            itemView.subtitle.setText(movie.releaseDate)
            itemView.rating.setText(movie.voteAverage.toString())

            val setbaseurl = BaseUrl()
            Glide.with(context)
                    .load(setbaseurl.textbaseurl + movie.posterPath)
                    .into(itemView.TV)


            itemView.TV.setOnClickListener {
                callback?.onClick(movie)

            }
        }

    }


    inner class MovieViewHolder2(view: View) : RecyclerView.ViewHolder(view) { //TODO การ set View Holder ตัวที่ 2

        fun onBindata2(movie: Movie) {
            val setbaseurl = BaseUrl()
            Picasso.with(context)
                    .load(setbaseurl.textbaseurl + movie.backdropPath)
                    .into(itemView.imageviewtital)
        }
    }


    inner class MovieViewHolder3(view: View):RecyclerView.ViewHolder(view){

        fun  onBindata(movie: Movie){

        }
    }




    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) { //TODO เป็นส่วนที่ ใช้ในการ is holder
        if (holder is MovieViewHolder2) {
            return (holder as MovieViewHolder2).onBindata2(movies[position])
        } else if(holder is MovieViewHolder) {
            return (holder as MovieViewHolder).onBindata(movies[position])
        }else{
            return (holder as MovieViewHolder3).onBindata(movies[position])
        }

    }


}