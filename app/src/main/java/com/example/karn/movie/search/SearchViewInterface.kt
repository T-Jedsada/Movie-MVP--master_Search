package com.example.karn.movie.search


import com.example.karn.movie.modelmovies.Movie
import com.example.karn.movie.modelmovies.MoviesResponse

interface SearchViewInterface {

    fun displayResult(movie: MoviesResponse)


}
