package com.example.poetrytour.network

import com.example.poetrytour.model.Post
import com.example.poetrytour.ui.post.PostItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {

    @GET("post/getPostById")
    fun getPostById(@Query("id") id:Long): Call<Post>

    @GET("post/deletePost")
    fun deletePost(@Query("id") id:Long): Call<Post>

    @GET("post/getPostItemList")
    fun getPostItemList(@Query("page")page:Int):Call<List<PostItem>>

    @GET("post/updatePostLoveNum")
    fun updatePostLove(@Query("id") id:Long,@Query("plus")plus:Int):Call<Int>

    @GET("post/updatePostCollectNum")
    fun updatePostCollect(@Query("id") id:Long,@Query("plus")plus:Int):Call<Int>

    @GET("post/updatePostReadingNum")
    fun updatePostReading(@Query("id") id:Long,@Query("plus")plus:Int):Call<Int>
}