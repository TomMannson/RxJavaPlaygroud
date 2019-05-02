package com.tommannson.apps.rxjavacourse.async

import android.app.Activity
import android.net.Uri
import android.os.SystemClock
import com.tommannson.apps.rxjavacourse.model.MockResponse
import com.tommannson.apps.rxjavacourse.utils.RetrofitAsyncTask
import com.tommannson.apps.rxjavacourse.ws.WsContract
import java.net.HttpURLConnection
import java.net.URL

class FetchMockTask(val listener: ResultListener) : RetrofitAsyncTask<String, Void, MockResponse>() {

    override fun doInBackground(vararg params: String?): MockResponse {

        SystemClock.sleep(1000)
        val ws = getRetrofitService(WsContract::class.java)
        val response = ws.getMock().execute().body() as MockResponse

        if(isCancelled){
            cancel(true);
        }

        return response;
    }

    override fun onPostExecute(result: MockResponse) {
        if(listener is Activity && listener.isDestroyed){
            return
        }

        listener.onFirstStageResult(result)
    }

    interface ResultListener {
        fun onFirstStageResult(response: MockResponse)
    }
}
