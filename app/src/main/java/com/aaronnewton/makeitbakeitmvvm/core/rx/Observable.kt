package com.aaronnewton.makeitbakeitmvvm.core.rx

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * Subscribe to the Observable ignoring the error
 */
fun <T> Observable<T>.subscribeWithoutError(success: (T) -> Unit): Disposable =
    subscribe(success, { /**Ignore the error**/ })