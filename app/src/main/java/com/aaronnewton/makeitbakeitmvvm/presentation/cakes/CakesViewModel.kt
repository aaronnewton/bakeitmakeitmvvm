package com.aaronnewton.makeitbakeitmvvm.presentation.cakes

import androidx.lifecycle.ViewModel
import com.aaronnewton.makeitbakeitmvvm.core.rx.subscribeWithoutError
import com.aaronnewton.makeitbakeitmvvm.domain.repository.ApiRepository
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
    private val fragmentStateSubject = BehaviorSubject.create<CakesState>()

    fun fetchCakes() {
        //TODO following clean architecture I would wrap this repo in a UseCase.
        apiRepository
            .getCakes()
            .subscribe(::onFetchCakesSuccessful, ::onFetchCakesFailed)
            .addTo(disposables)
    }

    private fun onFetchCakesSuccessful(cakes: List<Cake>) {
        fragmentStateSubject.onNext(CakesState.Successful(removeDuplicateCakes(cakes)))
    }

    private fun onFetchCakesFailed(e: Throwable) {
        fragmentStateSubject.onNext(CakesState.Error(e.message!!))
    }

    private fun removeDuplicateCakes(cakes: List<Cake>): List<Cake> = cakes
        .distinctBy { it.title }.sortedBy { it.title }

    fun onStateChanged(fn: (CakesState) -> Unit): Disposable = fragmentStateSubject
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWithoutError(fn)

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}