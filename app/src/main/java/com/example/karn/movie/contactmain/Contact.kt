package com.example.karn.movie.contactmain

import com.example.karn.movie.modelmovies.MoviesResponse
import retrofit2.Call


class Contact {
    interface dialog {
        fun showDialog(texttitle: String, overview: String, backdropPath: String, view: view_dialog)
    }

    interface view_dialog {
        fun viewdialog(texttitle: String, overview: String, backdropPath: String)
    }


    interface apiService {
        fun callApiService(type: Int, view: view_Response)
    }


    interface view_Response {
        fun viewResponse(api: Call<MoviesResponse>)
    }


}