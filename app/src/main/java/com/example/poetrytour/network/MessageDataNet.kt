package com.example.poetrytour.network

import com.example.poetrytour.network.MessageDataNet.await
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object MessageDataNet {

    private val messageDataService=ServiceCreator.create<MessageDataService>()

    suspend fun getBothMessageData(fromUserId:Long,toUserId:Long)
    = messageDataService.getBothMessageData(fromUserId, toUserId).await()

    suspend fun initMessageItem(id:Long)= messageDataService.initMessageItem(id).await()

    suspend fun deleteOffMessageById(id: Long)= messageDataService.deleteOffMessageById(id).await()

    suspend fun getOffMessageNum(id:Long)= messageDataService.getOffMessageNum(id).await()

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