package com.example.poetrytour.model

import java.sql.Timestamp

class User {
    var user_id: Long = 0
    var user_tel: String? = null
    var user_name: String? = null
    var register_time: Timestamp? = null
    var user_password: String? = null
    var avatar: String? = null
    var sex: String? = null
    var intro: String? = null
    var collect_post: String? = null

    override fun toString(): String {
        return "User(user_id=$user_id, user_tel=$user_tel, user_name=$user_name, register_time=$register_time, user_password=$user_password, avatar=$avatar, sex=$sex, intro=$intro, collect_post=$collect_post)"
    }


}