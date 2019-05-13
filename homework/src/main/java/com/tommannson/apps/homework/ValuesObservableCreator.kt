package com.tommannson.apps.homework

import io.reactivex.Observable

class ValuesObservableCreator {

    fun singleValueObservable() = Observable.just("Hello")

    fun arrayObservable() = Observable.fromArray(1..5)

    fun rangeObservable() = Observable.range(500, 1000)

}
