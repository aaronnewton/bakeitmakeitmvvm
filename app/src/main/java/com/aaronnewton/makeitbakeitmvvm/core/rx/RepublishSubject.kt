package com.aaronnewton.makeitbakeitmvvm.core.rx

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.internal.functions.ObjectHelper
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.subjects.Subject
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

class RepublishSubject<T> private constructor() : Subject<T>() {

    private val terminated =
        RepublishDisposable<T>(null, null)

    private val subscriber = AtomicReference<RepublishDisposable<T>>(null)
    private val value = AtomicReference<T>(null)

    private var error: Throwable? = null

    companion object {
        fun <T> create(): RepublishSubject<T> {
            return RepublishSubject()
        }
    }

    override fun hasThrowable() = subscriber.get() == terminated && error != null

    override fun hasObservers() = subscriber.get() != null

    override fun onComplete() {
        if (subscriber.get() == terminated) {
            return
        }
        subscriber.getAndSet(terminated).onComplete()
    }

    override fun onSubscribe(d: Disposable) {
        if (subscriber.get() == terminated) {
            d.dispose()
        }
    }

    override fun onError(t: Throwable) {
        ObjectHelper.requireNonNull<Throwable>(t,
            "onError called with null. Null values are generally not allowed in 2.x operators and sources.")

        if (subscriber.get() == terminated) {
            RxJavaPlugins.onError(t)
            return
        }

        error = t
        subscriber.getAndSet(terminated).onError(t)
    }

    override fun getThrowable() = if (subscriber.get() == terminated) error else null

    override fun subscribeActual(observer: Observer<in T>?) {
        if (observer == null) return

        val ps = RepublishDisposable(observer, this).also {
            observer.onSubscribe(it)
        }

        if (add(ps)) {
            if (ps.isDisposed) {
                remove(ps)
            } else {
                value.get()?.let {
                    ps.onNext(it)
                    value.set(null)
                }
            }
        } else {
            error?.let {
                observer.onError(it)
            } ?: run {
                observer.onComplete()
            }
        }
    }

    override fun onNext(t: T) {
        ObjectHelper.requireNonNull(t,
            "onNext called with null. Null values are generally not allowed in 2.x operators and sources.")

        if (subscriber.get() == terminated) {
            return
        }

        if (subscriber.get() == null) {
            value.set(t)
        } else {
            subscriber.get().onNext(t)
        }
    }

    override fun hasComplete() = subscriber.get() == terminated && error == null

    private fun add(ps: RepublishDisposable<T>): Boolean {
        val a = subscriber.get()
        if (a == terminated) {
            return false
        }

        if (subscriber.get() != null) {
            throw Error("Oops. max 1 subscriber allowed")
        } else {
            subscriber.set(ps)
            return true
        }
    }

    private fun remove(ps: RepublishDisposable<T>) {
        val a = subscriber.get()
        if (a == terminated || a == null) {
            return
        }

        if (subscriber.get() == ps) {
            subscriber.set(null)
        }
    }

    private class RepublishDisposable<in T>(
        private val actual: Observer<in T>?,
        private val parent: RepublishSubject<T>?
    ) : AtomicBoolean(), Disposable {

        fun onNext(t: T) {
            if (!get()) {
                actual?.onNext(t)
            }
        }

        fun onError(t: Throwable) {
            if (get()) {
                RxJavaPlugins.onError(t)
            } else {
                actual?.onError(t)
            }
        }

        fun onComplete() {
            if (!get()) {
                actual?.onComplete()
            }
        }

        override fun dispose() {
            if (compareAndSet(false, true)) {
                parent?.remove(this)
            }
        }

        override fun isDisposed(): Boolean {
            return get()
        }
    }
}
