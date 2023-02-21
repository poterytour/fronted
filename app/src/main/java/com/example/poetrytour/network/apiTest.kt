package com.example.poetrytour.network

import com.example.poetrytour.model.MessageData

suspend fun main(){

//
//    val lists=CommentNet.getAllComments(101)
//    print(lists)
    val list=PostNet.getCollectPostItem(1001)
    print(list)




}