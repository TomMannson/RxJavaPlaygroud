package com.tommannson.apps.first_module_homework


import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    val networkObservableCreator = NetworkObservableCreator()
    val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disposable.add(
            findViewById<EditText>(R.id.et_length)
                .textSizeObservable()
                .subscribe({
                    val length = it;
                    Toast.makeText(this@MainActivity, "First field has $length characters", Toast.LENGTH_SHORT).show()
                }, {
                    Toast.makeText(this@MainActivity, "Some error accured", Toast.LENGTH_SHORT).show()
                })
        )

        disposable.add(
            findViewById<EditText>(R.id.et_email)
                .emailValiationObservable()
                .subscribe({
                    val isEmail = it;
                    Toast.makeText(this@MainActivity, "Second field email = $isEmail", Toast.LENGTH_SHORT).show()
                }, {
                    Toast.makeText(this@MainActivity, "Some error accured", Toast.LENGTH_SHORT).show()
                })
        )

        disposable.add(
            networkObservableCreator.observeNetworkStatus(this)
                .subscribe({

                    findViewById<ImageView>(R.id.iv_internet_indicator).setImageDrawable(getDrawableForinternetStatus(it))
                }, {

                })
        );


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
