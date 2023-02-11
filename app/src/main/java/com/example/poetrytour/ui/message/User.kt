package com.example.poetrytour.ui.message

import com.example.poetrytour.model.User

object User {
    var user_id: Long = 0
    var user_name: String? = null
    var avatar: String? = null

    fun setUser(user:User){
        if(user!=null){
            user_id=user.user_id
            user_name=user.user_name
            avatar=user.avatar
        }
    }
}