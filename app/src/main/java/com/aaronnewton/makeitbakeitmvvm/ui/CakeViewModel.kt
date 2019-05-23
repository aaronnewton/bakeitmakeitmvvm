package com.aaronnewton.makeitbakeitmvvm.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.aaronnewton.makeitbakeitmvvm.data.ApiRepository
import com.aaronnewton.makeitbakeitmvvm.data.entities.Cake
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class CakeViewModel(
    private val apiRepository: ApiRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()

    fun fetchCakes() {
        //TODO following clean architecture i would wrap this repo in a UseCase.
        apiRepository
            .getCakes()
            .subscribe(::onFetchCakesSuccessful, ::onFetchCakesFailed)
            .addTo(disposables)
    }

    private fun onFetchCakesSuccessful(cakes: List<Cake>) {
        Log.d("CakeViewModel", "onFetchCakesSuccessful: ${cakes.size} $cakes")
    }

    private fun onFetchCakesFailed(e: Throwable) {
        Log.d("CakeViewModel", "onFetchCakesFailed: $e")
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}