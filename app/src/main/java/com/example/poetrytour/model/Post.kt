package com.example.poetrytour.model

import java.sql.Timestamp

class Post {

    var post_id: Long = 0
    var post_title: String? = null
    var post_context: String? = null
    var user_id: Long = 0
    var post_time: Timestamp? = null
    var post_love = 0
    var post_reading = 0
    var post_collect = 0
    var pronvice: String? = null
    var city: String? = null
    var attraction: String? = null

    override fun toString(): String {
        return "Post(post_id=$post_id, post_title=$post_title, post_context=$post_context, user_id=$user_id, post_time=$post_time, post_love=$post_love, post_reading=$post_reading, post_collect=$post_collect, pronvice=$pronvice, city=$city, attraction=$attraction)"
    }

}