package com.example.poetrytour.model

import java.sql.Timestamp

class Comment {

    var com_id: Long = 0
    var com_context: String? = null
    var com_time: Timestamp? = null
    var parent_com_id: Long = 0
    var user_id: Long = 0
    var post_id: Long = 0
    var com_love = 0

    override fun toString(): String {
        return "Comment(com_id=$com_id, com_context=$com_context, com_time=$com_time, parent_com_id=$parent_com_id, user_id=$user_id, post_id=$post_id, com_love=$com_love)"
    }


}