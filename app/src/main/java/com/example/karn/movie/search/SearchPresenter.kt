package com.example.karn.movie.search

import android.support.v7.widget.SearchView
import android.util.Log
import com.example.karn.movie.modelmovies.Movie
import com.example.karn.movie.modelmovies.MoviesResponse

import com.example.karn.movie.movieapi.ApiClient
import com.example.karn.movie.movieapi.ApiInterface
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchPresenter(var searchviewInterface: SearchViewInterface) : SearchPresenterInterface {

    //TODO ตรงนี้ เป็น apicall
    override fun getResultsBasedOnQuery(searchView: SearchView) {
        getObservableQuery(searchView) //TODO รับ keyword แล้วเอามา @Query
                .filter { it != "" }
                .debounce(2, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .switchMap<MoviesResponse> { s ->
                    // TODO : handler other class
                    ApiClient.client.create(ApiInterface::class.java).getMoviesBasedOnQuery(s)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith<DisposableObserver<MoviesResponse>>(getObserver())
    }

    //TODO ตรงนี้ เป็น RX ของการกรอกข้อมูลจาก user ในช่อง Search
    private fun getObservableQuery(searchView: SearchView): Observable<String> {

        val publishSubject = PublishSubject.create<String>()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                publishSubject.onNext(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                publishSubject.onNext(newText)
                return true
            }
        })

        return publishSubject
    }

    //TODO ตรงนี้เป็น RX  ของการ call Api
    fun getObserver(): DisposableObserver<MoviesResponse> = object : DisposableObserver<MoviesResponse>() {
        override fun onNext(movie: MoviesResponse) {
            searchviewInterface.displayResult(movie)
            Log.e("Next", "ShowNext-BaseURL.." + movie)
        }

        override fun onError(e: Throwable) {
            Log.e("Next", "Showerror...." + e.message)
        }

        override fun onComplete() {
            Log.e("Next", "ShowLogComplete")
        }
    }
}