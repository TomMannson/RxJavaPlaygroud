package com.tommannson.apps.homework

import android.widget.EditText
import com.tommannson.apps.homework.utils.SimpleTextWatcher
import com.tommannson.apps.homework.utils.isEmail
import io.reactivex.Observable
import io.reactivex.disposables.Disposables

fun EditText.textSizeValidationObservable(): Observable<CharSequence> {
    return Observable.create<CharSequence> {

        val watcher = object : SimpleTextWatcher() {

            override fun textChanged(s: CharSequence) {
                it.onNext(s)
            }
        }
        if (!it.isDisposed) {
            this.addTextChangedListener(watcher)
            it.setDisposable(Disposables.fromAction({
                this@textSizeValidationObservable.removeTextChangedListener(watcher)
            }))
        }
    }
}
