package com.codepath.apps.restclienttemplate.models

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codepath.apps.restclienttemplate.R
import com.codepath.apps.restclienttemplate.TweetsAdapter
import com.codepath.apps.restclienttemplate.TwitterApplication
import com.codepath.apps.restclienttemplate.TwitterClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

class TimelineActivity : AppCompatActivity() {

    lateinit var rvTweets : RecyclerView
    lateinit var adapter : TweetsAdapter
    lateinit var swipeContainer: SwipeRefreshLayout
    val tweets= ArrayList<Tweet>()

    lateinit var client:TwitterClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)

        client = TwitterApplication.getRestClient(this)

        // Initializing Recycler View for time line activity
        rvTweets = findViewById(R.id.rvTweets)
        adapter = TweetsAdapter(tweets)
        swipeContainer = findViewById(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener {
            Log.i(TAG, "Refreshing feed")
            populateHomeTimeline()
        }
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        rvTweets.layoutManager = LinearLayoutManager(this)
        rvTweets.adapter = adapter

        populateHomeTimeline()
    }
    fun populateHomeTimeline(){
        client.getHomeTimeline(object : JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.i(TAG, "Failed attempt")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successful attempt $json")
                val jsonArray = json.jsonArray
                try {
                    adapter.clear()
                    val listOfTweets = Tweet.fromJsonArray(jsonArray)
                    tweets.addAll(listOfTweets)
                    adapter.notifyDataSetChanged()
                    // Now we call setRefreshing(false) to signal refresh has finished
                    swipeContainer.setRefreshing(false)
                }catch(e: JSONException){
                    Log.e(TAG, "JSON Exception occured $e")
                }

            }
        })
    }
    companion object{
        val TAG = "TimelineActivity"
    }
}