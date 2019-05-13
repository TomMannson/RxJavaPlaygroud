package com.tommannson.apps.homework


import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tommannson.apps.homework.utils.plusAssign
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val networkObservableCreator = NetworkObservableCreator()
    val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disposable += findViewById<EditText>(R.id.et_length)
            .textSizeValidationObservable()
            .map { it.length > 0 }
            .debounce(1, TimeUnit.SECONDS)
            .distinctUntilChanged()
            .subscribe({
                val valid = it;
                runOnUiThread {
                    // this will be fixed soon
                    Toast.makeText(this@MainActivity, "value = $valid", Toast.LENGTH_SHORT).show()
                }
                //Some animation will be started soon
            }, {
                Toast.makeText(this@MainActivity, "Some error accured", Toast.LENGTH_SHORT).show()
            })
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
