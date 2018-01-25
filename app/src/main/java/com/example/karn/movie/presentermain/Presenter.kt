package com.example.karn.movie.presentermain

import android.content.Context
import com.droidbyme.dialoglib.DroidDialog
import com.example.karn.movie.contactmain.Contact
import com.example.karn.movie.movieapi.ApiClient
import com.example.karn.movie.movieapi.ApiInterface

class Presenter(var context: Context) : Contact.Dialog, Contact.ApiService {
    override fun showDialog(textTitle: String, overview: String, backdropPath: String, view: Contact.ViewDialog) {
        DroidDialog.Builder(context)
                .title(textTitle)
                .content(overview)
                .cancelable(false, false)
                .positiveButton("อ่านต่อ") { droidDialog ->
                    droidDialog.dismiss()
                    view.viewDialog(textTitle, overview, backdropPath)
                }.negativeButton("ไม่") { droidDialog -> droidDialog.dismiss() }.show()
    }

    override fun callApiService(type: Int, view: Contact.ViewResponse) {
        when (type) {
            1 -> view.viewResponse(ApiClient.client.create(ApiInterface::class.java).getTopRatedMovie())
            2 -> view.viewResponse(ApiClient.client.create(ApiInterface::class.java).getUpcoming())
            else -> view.viewResponse(ApiClient.client.create(ApiInterface::class.java).getMovie())
        }
    }
}