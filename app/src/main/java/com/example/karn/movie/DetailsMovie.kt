package com.example.karn.movie

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.karn.movie.movieapi.BaseUrl
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_movie.*


class DetailsMovie : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_movie)
        var subtitle = intent.extras!!.getString("KEY_DATA") // การส่ง Intent มา
        var subpath = intent.extras?.getString("KEY_BACKDROPPATH", "") // การส่ง Intent มา
        sub.setText(subtitle)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val setbaseurl = BaseUrl()
        Picasso.with(this)
                .load(setbaseurl.textbaseurl + subpath)
                .error(R.drawable.loading)
                .into(imageMoview)
    }


}
