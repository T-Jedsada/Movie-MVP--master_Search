package com.example.karn.movie.modelmovies

import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class Movie(@SerializedName("poster_path")var posterPath: String?,
            @SerializedName("adult") var isAdult: Boolean,
            @SerializedName("overview") var overview: String?,
            @SerializedName("release_date") var releaseDate: String?, genreIds: List<Int>,
            @SerializedName("id") var id: Int?,
            @SerializedName("original_title") var originalTitle: String?,
            @SerializedName("original_language") var originalLanguage: String?,
            @SerializedName("title") var title: String?,
            @SerializedName("backdrop_path") var backdropPath: String?,
            @SerializedName("popularity") var popularity: Double?,
            @SerializedName("vote_count") var voteCount: Int?,
            @SerializedName("video") var video: Boolean?,
            @SerializedName("vote_average") var voteAverage: Double?) {

    @SerializedName("genre_ids")
    var genreIds: List<Int> = ArrayList()

    // TODO : init มันจะถูกเรียกใช้ ก่อน constructor
    init {
        this.genreIds = genreIds
    }
}


class MoviesResponse {
    var results: List<Movie>? = null
}
