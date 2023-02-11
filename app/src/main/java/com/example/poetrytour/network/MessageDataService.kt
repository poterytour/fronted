package com.example.poetrytour.network

import com.example.poetrytour.model.MessageData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MessageDataService {

    @GET("post/message/getBothMessage")
    fun getBothMessageData(@Query("fromUserId") fromUserId:Long,@Query("toUserId") toUserId:Long): Call<List<MessageData>>
}