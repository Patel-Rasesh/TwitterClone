package com.codepath.apps.restclienttemplate.models

import org.json.JSONObject

class User {
    var name: String = ""
    var handle: String = ""
    var userProfilePictureURL: String = ""
    companion object{
        fun fromJson(jsonObject: JSONObject) : User{
            val user = User()
            user.name = jsonObject.getString("name")
            user.handle = jsonObject.getString("screen_name")
            user.userProfilePictureURL = jsonObject.getString("profile_image_url_https")
            return user
        }
    }
}