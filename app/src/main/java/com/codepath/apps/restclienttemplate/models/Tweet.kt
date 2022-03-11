package com.codepath.apps.restclienttemplate.models

import org.json.JSONArray
import org.json.JSONObject

class Tweet {
    var body: String = ""
    var postedOn: String = ""
    var user: User? = null
    companion object{
        fun fromJson(jsonObject: JSONObject) : Tweet{
            val tweet = Tweet()
            tweet.body = jsonObject.getString("text")
            tweet.postedOn = jsonObject.getString("created_at")
            // user is a parent JSON object
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"))
            return tweet
        }
        // Converts the list of JSON objects to list of Tweet (object)
        fun fromJsonArray(jsonArray : JSONArray): List<Tweet>{
            val tweets = ArrayList<Tweet>()
            for (i in 0 until jsonArray.length()){
                tweets.add(fromJson(jsonArray.getJSONObject(i)))
            }
            return tweets
        }
    }
}