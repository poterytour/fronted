package com.example.poetrytour.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    //    private const val BASE_URL="http://192.168.43.38:8080/"
//    private const val BASE_URL="http://192.168.0.182:8080/"
//    private const val BASE_URL="http://10.0.2.2:8080/"
    private const val BASE_URL="http:192.168.2.217:8080/"
    private val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass:Class<T>):T= retrofit.create(serviceClass)

    inline fun <reified T> create():T= create(T::class.java)

}