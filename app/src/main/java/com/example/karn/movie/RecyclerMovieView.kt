package com.example.karn.movie

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.karn.movie.contactmain.Contact
import com.example.karn.movie.mainadapter.MoviesAdapter
import com.example.karn.movie.modelmovies.Movie
import com.example.karn.movie.modelmovies.MoviesResponse
import com.example.karn.movie.presentermain.Presenter
import com.example.karn.movie.search.SearchMovie
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class RecyclerMovieView : AppCompatActivity(), MoviesAdapter.MovieListListener, Contact.view_dialog, Contact.view_Response {
    lateinit var presenter: Contact.dialog
    lateinit var presenterApi: Contact.apiService
    var movieAdapter: MoviesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var type = intent.extras.getInt("KEY_DATA_MOVIE")
        presenterApi = Presenter(this)
        presenterApi.callApiService(type, this)

        //movies?.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)) //TODO ทำแนว linearlayou //
        movies?.setLayoutManager(LinearLayoutManager(this))
        movieAdapter = MoviesAdapter(listOf(), this)
        movieAdapter?.setOnClickCallback(this)
        movies.adapter = movieAdapter
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.search) {
            val i = Intent(this, SearchMovie::class.java)
            startActivity(i)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        return super.onOptionsItemSelected(item)
    }



    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onClick(movie: Movie) {
        presenter = Presenter(this)
        presenter.showDialog(movie.title!!, movie.overview!!, movie.backdropPath!!, this)
    }


    override fun viewdialog(texttitle: String, overview: String, backdropPath: String) {
        val intent = Intent(this, DetailsMovie::class.java)
        intent.putExtra("KEY_DATA", overview)
        intent.putExtra("KEY_BACKDROPPATH", backdropPath)
        this.startActivity(intent)
    }

    override fun viewResponse(api: Call<MoviesResponse>) {
        api.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                val movies = response.body()?.results
                movieAdapter?.setItem(movies ?: listOf())
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {}
        })

    }






}