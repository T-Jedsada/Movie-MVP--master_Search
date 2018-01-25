package com.example.karn.movie

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

class NavigationMain : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, Contact.ViewDialog {

    private val adapter: AlbumsAdapter by lazy {
        AlbumsAdapter().apply {
            callback = {
                presenter.showDialog(it.title!!, it.overview!!, it.backdropPath!!, this@NavigationMain)
            }
        }
    }

    lateinit var presenter: Contact.Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_main)
        setSupportActionBar(toolbar)
        presenter = Presenter(this)
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        initViews()
        recycler_view.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.search) {
            startActivity(Intent(this, SearchMovie::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        return super.onOptionsItemSelected(item)
    }


    // การทำงานเมื่อผู้ใช้กด Back //**
    override fun onBackPressed() = when (drawer_layout.isDrawerOpen(GravityCompat.START)) {
        true -> drawer_layout.closeDrawer(GravityCompat.START)
        else -> super.onBackPressed()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_movie -> {
                val type = 1
                val intent = Intent(this, RecyclerMovieView::class.java)
                intent.putExtra("KEY_DATA_MOVIE", type)
                this.startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.nav_movietwo -> {
                val type = 2
                val intent = Intent(this, RecyclerMovieView::class.java)
                intent.putExtra("KEY_DATA_MOVIE", type)
                this.startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.nav_moviethree -> {
                val type = 3
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

    // TODO : create new class
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
        recycler_view?.apply {
            layoutManager = mLayoutManager
            addItemDecoration(GridSpacingItemDecoration(2, dpToPx(10), true))
            itemAnimator = DefaultItemAnimator()
            adapter = adapter
        }
        loadJSON()
    }

    fun loadJSON() {
        val apiService = ApiClient.client.create(ApiInterface::class.java)
        val call = apiService.getMovie()
        // TODO : hard code
        call.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                val items = response.body()?.results
                recycler_view.smoothScrollToPosition(0)
                adapter.list = items ?: listOf()
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {}
        })
    }

    override fun viewDialog(textTitle: String, overview: String, backdropPath: String) {
        val intent = Intent(this, DetailsMovie::class.java)
        intent.putExtra("KEY_DATA", overview)
        intent.putExtra("KEY_BACKDROPPATH", backdropPath)
        this.startActivity(intent)

    }
}
