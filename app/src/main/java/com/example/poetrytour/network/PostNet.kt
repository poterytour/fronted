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
    suspend fun getPostItemList()= postService.getPostItemList().await()

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