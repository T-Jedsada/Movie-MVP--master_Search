package com.example.karn.movie

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.karn.movie.movieapi.ApiClient
import com.example.karn.movie.movieapi.ApiInterface
import com.example.karn.movie.mainadapter.AlbumsAdapter
import com.example.karn.movie.contactmain.Contact
import com.example.karn.movie.modelmovies.Movie
import com.example.karn.movie.modelmovies.MoviesResponse
import com.example.karn.movie.presentermain.Presenter
import com.example.karn.movie.search.SearchMovie
import kotlinx.android.synthetic.main.activity_card_view_movie.*
import kotlinx.android.synthetic.main.activity_navigation_main.*
import kotlinx.android.synthetic.main.app_bar_navigation_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NavigationMain : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, AlbumsAdapter.AlbumsListener, Contact.view_dialog {


    var adapter: AlbumsAdapter? = null
    lateinit var presenter: Contact.dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_main)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        initViews()

        adapter = AlbumsAdapter(listOf(), this)
        adapter?.setOnClickCallback(this)
        recycler_view.adapter = adapter

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
        }

        return super.onOptionsItemSelected(item)
    }





    override fun onBackPressed() { // การทำงานเมื่อผู้ใช้กด Back //**
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_movie -> {
                val type: Int = 1
                val intent = Intent(this, RecyclerMovieView::class.java)
                intent.putExtra("KEY_DATA_MOVIE", type)
                this.startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.nav_movietwo -> {
                val type: Int = 2
                val intent = Intent(this, RecyclerMovieView::class.java)
                intent.putExtra("KEY_DATA_MOVIE", type)
                this.startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.nav_moviethree -> {
                val type: Int = 2
                val intent = Intent(this, RecyclerMovieView::class.java)
                intent.putExtra("KEY_DATA_MOVIE", type)
                this.startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.nav_search -> {
                val intent = Intent(this, SearchMovie::class.java)
                this.startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }

        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    inner class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount
                outRect.right = (column + 1) * spacing / spanCount

                if (position < spanCount) {
                    outRect.top = spacing
                }
                outRect.bottom = spacing
            } else {
                outRect.left = column * spacing / spanCount
                outRect.right = spacing - (column + 1) * spacing / spanCount
                if (position >= spanCount) {
                    outRect.top = spacing
                }
            }
        }
    }


    //** ตัวกำหนด ขนาดรูป
    fun dpToPx(dp: Int): Int {
        val r = resources
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics))
    }

    fun initViews() {

        val mLayoutManager = GridLayoutManager(this, 2)
        recycler_view.setLayoutManager(mLayoutManager)
        recycler_view.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(10), true))
        recycler_view.setItemAnimator(DefaultItemAnimator())
        recycler_view.setAdapter(adapter)
        loadJSON()
    }

    fun loadJSON() {
        var apiService = ApiClient.client.create(ApiInterface::class.java)
        var call = apiService.getMovie()
        call.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                val items = response.body()?.results
                recycler_view.smoothScrollToPosition(0)
                adapter?.setItem(items ?: listOf())

            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {}
        })
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





}
