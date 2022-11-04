package com.example.poetrytour.network

import com.example.poetrytour.model.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {

    @GET("post/getPostById")
    fun getPostById(@Query("id") id:Long): Call<Post>

    @GET("post/deletePost")
    fun deletePost(@Query("id") id:Long): Call<Post>
}