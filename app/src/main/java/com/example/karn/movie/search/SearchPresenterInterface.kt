package com.example.karn.movie.search

import android.support.v7.widget.SearchView



interface SearchPresenterInterface {
    fun getResultsBasedOnQuery(searchView: SearchView)
}
