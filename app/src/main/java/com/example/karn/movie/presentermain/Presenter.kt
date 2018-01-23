package com.example.karn.movie.presentermain

import android.content.Context
import com.droidbyme.dialoglib.DroidDialog
import com.example.karn.movie.contactmain.Contact
import com.example.karn.movie.movieapi.ApiClient
import com.example.karn.movie.movieapi.ApiInterface


class Presenter(var context: Context) : Contact.dialog,Contact.apiService {
    override fun showDialog(texttitle: String, overview: String, backdropPath: String, view: Contact.view_dialog) {
        DroidDialog.Builder(context)
                .title(texttitle)
                .content(overview)
                .cancelable(false, false)
                .positiveButton("อ่านต่อ") { droidDialog ->
                    droidDialog.dismiss()
                    view.viewdialog(texttitle, overview, backdropPath)
                }
                .negativeButton("ไม่") { droidDialog ->
                    droidDialog.dismiss()
                }

                .show()

    }


    override fun callApiService(type: Int,view:Contact.view_Response) {
        if(type==1){
            var api=ApiClient.client.create(ApiInterface::class.java)
            view.viewResponse(api.getTopRatedMovie())
        }else if(type==2){
            var api=ApiClient.client.create(ApiInterface::class.java)
            view.viewResponse(api.getUpcoming())
        }else{
            var api=ApiClient.client.create(ApiInterface::class.java)
            view.viewResponse(api.getMovie())
        }
    }





}
