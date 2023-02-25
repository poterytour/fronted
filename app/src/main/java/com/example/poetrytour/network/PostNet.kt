package com.example.poetrytour.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object PostNet {

    private val postService=ServiceCreator.create<PostService>()

    suspend fun getPostById(id:Long)= postService.getPostById(id).await()
    suspend fun deletePost(id:Long)= postService.deletePost(id).await()
    suspend fun getPostItemList(page:Int)= postService.getPostItemList(page).await()
    suspend fun searchPostItem(key:String)= postService.searchPost(key).await()
    suspend fun getLovedPostItem(userId: Long)= postService.getLovedPostItem(userId).await()
    suspend fun getCollectPostItem(userId: Long)= postService.getCollectPostItem(userId).await()
    suspend fun getPostByPublisher(userId: Long)= postService.getPostsByPublisher(userId).await()
    suspend fun getPostImg(id:Long)= postService.getPostImg(id).await()
    

    suspend fun updatePostLove(id: Long,plus:Int)= postService.updatePostLove(id, plus).await()
    suspend fun updatePostCollect(id: Long,plus:Int)= postService.updatePostCollect(id,plus).await()

    suspend fun updatePostReading(id: Long,plus:Int)= postService.updatePostReading(id, plus).await()

    suspend fun addPostLove(userId:Long,postId:Long)= postService.addPostLove(userId, postId).await()
    suspend fun deletePostLove(userId: Long,postId: Long)= postService.deletePostLove(userId, postId).await()
    suspend fun getPostLoveList(userId: Long)= postService.getPostLoveList(userId).await()

    suspend fun addPostCollect(userId:Long,postId:Long)= postService.addPostCollect(userId, postId).await()
    suspend fun deletePostCollect(userId: Long,postId: Long)= postService.deletePostCollect(userId, postId).await()
    suspend fun getPostCollectList(userId: Long)= postService.getPostCollectList(userId).await()


    //     数据接收处理
    suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null"))
                }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}