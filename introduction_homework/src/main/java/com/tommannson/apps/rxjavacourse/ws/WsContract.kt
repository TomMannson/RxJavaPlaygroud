package com.tommannson.apps.rxjavacourse.ws

import com.tommannson.apps.rxjavacourse.model.MockResponse
import retrofit2.Call
import retrofit2.http.GET

interface WsContract {

    @GET("5ae1bd592d000028009d7e25")
    fun getMock(): Call<MockResponse>
}
