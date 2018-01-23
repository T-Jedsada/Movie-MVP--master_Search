package com.example.karn.movie.movieapi

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var retrofit: Retrofit? = null
    val builder = OkHttpClient.Builder() //TODO
    val okHttpClient = builder.build()  //TODO
    val client: Retrofit
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl("http://api.themoviedb.org/3/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //TODO
                        .client(okHttpClient) //TODO
                        .build() }
            return retrofit!!
        }
}
