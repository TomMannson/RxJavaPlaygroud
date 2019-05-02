package com.tommannson.apps.rxjavacourse.utils

import android.os.AsyncTask
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class RetrofitAsyncTask<Param, Progress, Result> : AsyncTask<Param, Progress, Result>() {

    inline fun <reified T> getRetrofitService(clazz: Class<T>): T {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://www.mocky.io/v2/")
            .build().create(T::class.java)
    }

}


