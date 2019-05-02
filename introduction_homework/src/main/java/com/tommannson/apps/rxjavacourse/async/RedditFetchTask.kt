package com.tommannson.apps.rxjavacourse.async

import android.app.Activity
import android.os.SystemClock
import com.tommannson.apps.rxjavacourse.model.MockResponse
import com.tommannson.apps.rxjavacourse.utils.RetrofitAsyncTask
import com.tommannson.apps.rxjavacourse.ws.WsContract
import java.net.URI

class RedditFetchTask(val listener: ResultListener) : RetrofitAsyncTask<String, Void, Any>() {

    override fun doInBackground(vararg params: String?): Any {

        SystemClock.sleep(1000)
        val connection = URI(params[0]).toURL().openConnection()
        connection.connect()
        val stream = connection.getInputStream()
        stream.close()
        return Any();
    }

    override fun onPostExecute(result: Any) {
        if (listener is Activity && listener.isDestroyed) {
            return
        }

        listener.onSecondStageResult()
    }

    interface ResultListener {
        fun onSecondStageResult()
    }


}
