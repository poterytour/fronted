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

    @GET("post/search")
    fun searchPost(@Query("key")key:String):Call<List<PostItem>>
    
    @GET("post/getByPublisher")
    fun getPostsByPublisher(@Query("id") userId: Long):Call<List<PostItem>>
    

    @GET("post/getLovedPost")
    fun getLovedPostItem(@Query("userId") userId: Long):Call<List<PostItem>>

    @GET("post/getCollectedPost")
    fun getCollectPostItem(@Query("userId") userId: Long):Call<List<PostItem>>


    @GET("post/updatePostLoveNum")
    fun updatePostLove(@Query("id") id:Long,@Query("plus")plus:Int):Call<Int>

    @GET("post/updatePostCollectNum")
    fun updatePostCollect(@Query("id") id:Long,@Query("plus")plus:Int):Call<Int>

    @GET("post/updatePostReadingNum")
    fun updatePostReading(@Query("id") id:Long,@Query("plus")plus:Int):Call<Int>

    @GET("post/addPostLove")
    fun addPostLove(@Query("userId") userId:Long,@Query("postId") postId:Long):Call<Int>

    @GET("post/deletePostLove")
    fun deletePostLove(@Query("userId") userId:Long,@Query("postId") postId:Long):Call<Int>

    @GET("post/getPostLoveList")
    fun getPostLoveList(@Query("userId") userId:Long):Call<List<Long>>


    @GET("post/addPostCollect")
    fun addPostCollect(@Query("userId") userId:Long,@Query("postId") postId:Long):Call<Int>

    @GET("post/deletePostCollect")
    fun deletePostCollect(@Query("userId") userId:Long,@Query("postId") postId:Long):Call<Int>

    @GET("post/getPostCollectList")
    fun getPostCollectList(@Query("userId") userId:Long):Call<List<Long>>
}