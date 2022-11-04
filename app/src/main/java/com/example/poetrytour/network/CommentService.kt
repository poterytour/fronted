package com.example.poetrytour.network

import com.example.poetrytour.model.Comment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CommentService {

    @GET("post/comment/getAllComments")
    fun getAllComments(@Query("postId") postId:Long): Call<List<Comment>>

    @GET("post/comment/getCommentById")
    fun getCommentById(@Query("id") id:Long): Call<Comment>

    @GET("post/comment/addComment")
    fun addComment(@Query("id") id:Long, @Query("context")context:String, @Query("parentId") parentId:Long,
                   @Query("userId") userId:Long, @Query("postId")postId:Long): Call<Comment>

    @GET("post/comment/deleteComment")
    fun deleteComment(@Query("id") id:Long):Call<Int>
}