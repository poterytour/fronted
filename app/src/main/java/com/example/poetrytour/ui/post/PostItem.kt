package com.example.poetrytour.ui.post

import com.example.poetrytour.model.User

class PostItem {
    var publisher: User? = null
    var post_id: Long ? = null
    var post_title: String? = null
    var post_context: String? = null
    var post_time: String? = null
    var post_reading :Int? =0
    var post_love :Int? =0
    var post_com_num :Int? =0
    var post_local: String? = null
    var post_display_img: String? = null
    override fun toString(): String {
        return "PostItem(publisher=$publisher, post_id=$post_id, post_title=$post_title, post_context=$post_context, post_time=$post_time, post_reading=$post_reading, post_love=$post_love, post_com_num=$post_com_num, post_local=$post_local, post_display_img=$post_display_img)"
    }


}