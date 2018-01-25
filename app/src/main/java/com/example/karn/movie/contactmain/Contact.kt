package com.example.karn.movie.contactmain

import com.example.karn.movie.modelmovies.MoviesResponse
import retrofit2.Call

class Contact {
    interface Dialog {
        fun showDialog(textTitle: String, overview: String, backdropPath: String, view: ViewDialog)
    }

    interface ViewDialog {
        fun viewDialog(textTitle: String, overview: String, backdropPath: String)
    }

    interface ApiService {
        fun callApiService(type: Int, view: ViewResponse)
    }

    interface ViewResponse {
        fun viewResponse(api: Call<MoviesResponse>)
    }
}