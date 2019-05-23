package com.aaronnewton.makeitbakeitmvvm.ui.cakes

import android.util.Log
import androidx.lifecycle.ViewModel
import com.aaronnewton.makeitbakeitmvvm.core.rx.subscribeWithoutError
import com.aaronnewton.makeitbakeitmvvm.data.ApiRepository
import com.aaronnewton.makeitbakeitmvvm.data.entities.Cake
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject

class CakesViewModel(
    private val apiRepository: ApiRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()

    //    private val fragmentStateSubject = RepublishSubject.create<CakesState>()
    private val fragmentStateSubject = BehaviorSubject.create<CakesState>()

    fun fetchCakes() {
        //TODO following clean architecture i would wrap this repo in a UseCase.
        apiRepository
            .getCakes()
            .subscribe(::onFetchCakesSuccessful, ::onFetchCakesFailed)
            .addTo(disposables)
    }

    private fun onFetchCakesSuccessful(cakes: List<Cake>) {
        Log.d("CakesViewModel", "onFetchCakesSuccessful: ${cakes.size} $cakes")
        fragmentStateSubject.onNext(CakesState.Successful(cakes))
    }

    private fun onFetchCakesFailed(e: Throwable) {
        Log.d("CakesViewModel", "onFetchCakesFailed: $e")
    }

    fun onStateChanged(fn: (CakesState) -> Unit): Disposable = fragmentStateSubject
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWithoutError(fn)

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}