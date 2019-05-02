package com.tommannson.apps.rxjavacourse

import android.os.AsyncTask
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tommannson.apps.rxjavacourse.async.FetchMockTask
import com.tommannson.apps.rxjavacourse.async.RedditFetchTask
import com.tommannson.apps.rxjavacourse.model.MockResponse

class MainActivity : AppCompatActivity(), FetchMockTask.ResultListener, RedditFetchTask.ResultListener {

    var listOfTasks = mutableListOf<AsyncTask<*, *, *>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun updateFirstTaskStatus(s: String) {
        findViewById<TextView>(R.id.first_task_status).text = s
    }

    private fun updateSecondTaskStatus(s: String) {
        findViewById<TextView>(R.id.second_task_status).text = s
    }

    override fun onResume() {
        super.onResume()
        updateFirstTaskStatus("In Progress")

        FetchMockTask(this)
            .also {
                listOfTasks.add(it)
                it.execute()
            }
    }

    override fun onPause() {
        super.onPause()

        for (item in listOfTasks) {
            item.cancel(false)
        }
    }

    override fun onFirstStageResult(response: MockResponse) {
        updateFirstTaskStatus("Finished")
        updateSecondTaskStatus("In Progress")
        if(response.configuration.disabled) {
            RedditFetchTask(this)
                .also {
                    listOfTasks.add(it)
                    it.execute(response.configuration.endpoint)
                }
        }
    }

    override fun onSecondStageResult() {
        updateSecondTaskStatus("Finished")
    }
}
