package com.example.karn.movie

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.karn.movie.search.SearchMovie
import kotlinx.android.synthetic.main.activity_navigation_main.*
import kotlinx.android.synthetic.main.app_bar_navigation_main.*

class Navigation : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_main)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
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

    override fun onBackPressed() = if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
        drawer_layout.closeDrawer(GravityCompat.START)
    } else {
        super.onBackPressed()
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
                val type: Int = 3
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
}