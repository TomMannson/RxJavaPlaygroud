package com.tommannson.apps.first_module_homework

import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import io.reactivex.Observable
import io.reactivex.disposables.Disposables


class NetworkObservableCreator {


    /**
     * Implementation provided support devices with API level 24+
     */
    @TargetApi(Build.VERSION_CODES.N)
    fun observeNetworkStatus(ctx: Context): Observable<Boolean> {
        return Observable.create<Boolean> { it ->
            val observableEmiter = it;
            val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkListener = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    observableEmiter.onNext(true)
                }

                override fun onLost(network: Network?) {
                    observableEmiter.onNext(false)
                }
            }

            if (!it.isDisposed) {
                connectivityManager.registerDefaultNetworkCallback(networkListener)

                it.setDisposable(Disposables.fromAction({
                    connectivityManager.unregisterNetworkCallback(networkListener)
                }))
            }

        }
    }
}
