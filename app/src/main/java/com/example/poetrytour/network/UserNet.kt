package com.example.poetrytour.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object UserNet {

    private val userService=ServiceCreator.create<UserService>()

    //用户的注册和登录
    suspend fun userLogin(tel:String,password:String)= userService.userLogin(tel, password).await()
    suspend fun userRegister(tel: String,password: String)= userService.userRegister(tel, password).await()

    //用户的查找
    suspend fun getUserById(id:Long)= userService.getUserById(id).await()
    suspend fun getUserByTel(tel:String)= userService.getUserByTel(tel).await()


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