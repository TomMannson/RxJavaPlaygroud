package com.tommannson.apps.homework.utils

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

fun <T> Observable<T>.firstAndLast(): Observable<T> {
    return this.lift<T> { observer ->
        object : Observer<T> {

            var value: T? = null;

            override fun onComplete() {
                if (value == null) {
                    observer.onError(ElementNotFoundException())
                    return
                }
                observer.onNext(value as T)
                observer.onComplete()
            }

            override fun onError(e: Throwable) {
                observer.onError(e)
            }

            override fun onNext(t: T) {
                if (value == null) {
                    observer.onNext(t)
                }
                value = t
            }

            override fun onSubscribe(d: Disposable) {
                if (!d.isDisposed) {
                    observer.onSubscribe(d)
                }
            }
        }
    }
}

class ElementNotFoundException : Exception()
