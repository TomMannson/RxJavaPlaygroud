package com.tommannson.apps.homework


import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.widget.ViewAnimator
import androidx.appcompat.app.AppCompatActivity
import com.tommannson.apps.homework.utils.plusAssign
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    val networkObservableCreator = NetworkObservableCreator()
    val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disposable += findViewById<EditText>(R.id.et_length)
            .textContentObservable()
            .map { it.length > 0 }
            .distinctUntilChanged()
            .subscribe({ valid ->
                runOnUiThread {
                    animateValidation(valid)
                }
            }, {
                Toast.makeText(this@MainActivity, "Some error accured", Toast.LENGTH_SHORT).show()
            })

        disposable += findViewById<EditText>(R.id.et_email)
            .emailValiationObservable()
            .subscribe({ isEmail ->
                Toast.makeText(this@MainActivity, "Second field email = $isEmail", Toast.LENGTH_SHORT).show()
            }, {
                Toast.makeText(this@MainActivity, "Some error accured", Toast.LENGTH_SHORT).show()
            })


        disposable += networkObservableCreator.observeNetworkStatus(this)
            .subscribe({ isNetworkAvailable ->
                findViewById<ImageView>(R.id.iv_internet_indicator).setImageDrawable(
                    getDrawableForinternetStatus(
                        isNetworkAvailable
                    )
                )
            }, {
            })

    }

    fun animateValidation(invalid: Boolean) {
        findViewById<ViewAnimator>(R.id.iv_text_valid)
            .run {
                if (invalid) showPrevious() else showNext()
            }
    }


    fun getDrawableForinternetStatus(internetStatus: Boolean) = if (internetStatus) {
        getDrawable(android.R.drawable.presence_online)
    } else {
        getDrawable(android.R.drawable.presence_invisible)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
