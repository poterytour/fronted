package com.example.poetrytour.network

import com.example.poetrytour.model.Result
import com.example.poetrytour.model.User
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @GET("user/login")
    fun userLogin(@Query("tel") tel:String,@Query("password") password:String): Call<Result<User>>

    @GET("user/register")
    fun userRegister(@Query("tel") tel:String,@Query("password") password:String): Call<Result<User>>


    @GET("user/getById")
    fun getUserById(@Query("id") id:Long): Call<User>

    @GET("user/getByTel")
    fun getUserByTel(@Query("tel") tel:String): Call<User>

    @GET("user/update")
    fun updateUser(@Query("user")user:String):Call<User>
    
    
    @Multipart
    @POST("upload")
    fun uploadImg(@Part body : MultipartBody.Part):Call<Result<String>>
}