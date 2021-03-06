package com.example.karn.movie.movieapi


import com.example.karn.movie.modelmovies.Movie
import com.example.karn.movie.modelmovies.MoviesResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("movie/top_rated?api_key=8476a7ab80ad76f0936744df0430e67c&language=en-")
    fun getTopRatedMovie(): Call<MoviesResponse>

    @GET("movie/upcoming?api_key=8476a7ab80ad76f0936744df0430e67c&language=en-")
    fun getUpcoming(): Call<MoviesResponse>

    @GET("movie/popular?api_key=8476a7ab80ad76f0936744df0430e67c&language=en-")
    fun getMovie(): Call<MoviesResponse>


    @GET("search/movie?api_key=004cbaf19212094e32aa9ef6f6577f22")
    fun getMoviesBasedOnQuery(@Query("query") q: String): Observable<MoviesResponse>


}
