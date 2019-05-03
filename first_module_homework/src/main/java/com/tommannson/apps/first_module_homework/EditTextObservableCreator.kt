package com.tommannson.apps.first_module_homework

import android.widget.EditText
import com.tommannson.apps.first_module_homework.utils.SimpleTextWatcher
import com.tommannson.apps.first_module_homework.utils.isEmail
import io.reactivex.Observable
import io.reactivex.disposables.Disposables


fun EditText.textSizeObservable(): Observable<Int> {
    return Observable.create<Int> {

        val watcher = object : SimpleTextWatcher() {

            override fun textChanged(s: CharSequence) {
                if (!s.isEmpty()) {
                    it.onNext(s.length)
                }
            }
        }
        if (!it.isDisposed) {
            this.addTextChangedListener(watcher)
            it.setDisposable(Disposables.fromAction({
                this@textSizeObservable.removeTextChangedListener(watcher)
            }))
        }
    }
}

fun EditText.emailValiationObservable(): Observable<Boolean> {
    return Observable.create<Boolean> {

        val watcher = object : SimpleTextWatcher() {

            override fun textChanged(s: CharSequence) {
                it.onNext(s.isEmail())
            }
        }

        if (!it.isDisposed) {
            this.addTextChangedListener(watcher)

            it.setDisposable(Disposables.fromAction({
                this@emailValiationObservable.removeTextChangedListener(watcher)
            }))
        }
    }
}
