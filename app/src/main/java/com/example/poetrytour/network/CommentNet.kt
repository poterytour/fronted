package com.example.poetrytour.network

import com.example.poetrytour.network.PostNet.await
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object CommentNet {

    private val commentService=ServiceCreator.create<CommentService>()

    //帖子的全部评论
    suspend fun getAllComments(postId:Long)= commentService.getAllComments(postId).await()

    //增加评论
    suspend fun addComment(id:Long,context:String,parentId:Long,userId:Long,postId:Long)=
        commentService.addComment(id,context,parentId,userId,postId).await()

    //单个评论的查找与删除
    suspend fun getCommentById(id: Long)= commentService.getCommentById(id).await()
    suspend fun deleteComment(id:Long)= commentService.deleteComment(id).await()


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