package com.example.poetrytour.ui

import com.example.poetrytour.model.User

object User {
    var user_id: Long? = null
    var user_name: String? = null
    var avatar: String? = null

    var login_user:User?=null

    fun setUser(user:User){
        if(user!=null){
            user_id =user.user_id
            user_name =user.user_name
            avatar =user.avatar

            user.user_password=" "
            login_user =user
        }
    }
}