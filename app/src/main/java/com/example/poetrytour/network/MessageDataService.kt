package com.example.poetrytour.network

import com.example.poetrytour.model.MessageData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MessageDataService {

    @GET("message/getBothMessage")
    fun getBothMessageData(@Query("fromUserId") fromUserId:Long,@Query("toUserId") toUserId:Long): Call<List<MessageData>>

    @GET("message/initMessageItem")
    fun initMessageItem(@Query("id") userId:Long):Call<List<MessageData>>


    @GET("message/deleteOffMessageById")
    fun deleteOffMessageById(@Query("id") userId:Long):Call<Int>

    @GET("message/getOffMessageNum")
    fun getOffMessageNum(@Query("id") toUserId:Long):Call<Int>
}