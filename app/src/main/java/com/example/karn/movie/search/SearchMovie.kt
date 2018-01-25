package com.example.karn.movie.search

import android.app.SearchManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.example.karn.movie.R
import com.example.karn.movie.mainadapter.MoviesAdapter
import com.example.karn.movie.mainadapter.MoviesAdapterSearch
import com.example.karn.movie.modelmovies.MoviesResponse
import kotlinx.android.synthetic.main.activity_main.*

class SearchMovie : AppCompatActivity(), SearchViewInterface {

    var searchView: SearchView? = null
    var searchPresenter: SearchPresenter? = null

    private lateinit var adapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_search)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        searchPresenter = SearchPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search)
                .actionView as SearchView
        searchView?.setSearchableInfo(searchManager
                .getSearchableInfo(componentName))
        searchView?.maxWidth = Integer.MAX_VALUE
        searchView?.queryHint = "Enter Movie name.."
        searchPresenter?.getResultsBasedOnQuery(searchView!!) //TODO ต้องกลับมาแก้ !!
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }

    //TODO ส่วน ของการShow RecyclerView //TODO เหลือ เอา movie   // เดี่ยวกลัยไปดู movirespon //
    override fun displayResult(movie: MoviesResponse) {
        adapter = MoviesAdapterSearch(movie.results!!, this)
        movies.adapter = adapter
    }
}
