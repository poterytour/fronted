package com.example.poetrytour.network

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


suspend fun main(){

//
//    val lists=CommentNet.getAllComments(101)
//    print(lists)
//    val list=PostNet.getCollectPostItem(1001)
//    print(list)
    val file=File("D:\\Download\\0.jfif")
    
    val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
    val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
    val rs=UserNet.uploadImg(body)
    println(rs)


}