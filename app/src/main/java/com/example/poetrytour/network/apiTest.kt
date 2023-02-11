package com.example.poetrytour.network

import com.example.poetrytour.model.MessageData

suspend fun main(){

//    val result1=UserNet.userLogin("3248","123456");
//    print(result1)
//    println()
//
//    val result2=UserNet.userLogin("3248","12345678");
//    print(result2)
//    println()
//
//    val result3=UserNet.userLogin("3258","12345678");
//    print(result3)
//    println()
//
//    val result4=UserNet.userRegister("3248","123456");
//    print(result4)
//    println()
//
//    val result5=UserNet.userRegister("3258","123456")
//    print(result5)
//    println()

//    val result6=UserNet.getUserById(1001);
//    print(result6)
//    println()
//
//    val result7=UserNet.getUserByTel("3258")
//    print(result7)
//    println()

//    val result8=PostNet.getPostById(1001);
//    print(result8);
//    println()

//    val result9=CommentNet.getAllComments(1001)
//    print(result9)
//    println()
//
//    val result10=CommentNet.getCommentById(1003)
//    print(result10)
//    println()
//
//    val result11=CommentNet.addComment(1010,"55",0,1002,1001)
//    print(result11)
//    println()
//
//    val result12=CommentNet.deleteComment(1004)
//    print(result12)
//    println()

    val messageData= MessageData()
    messageData.setFromUserId("1003")
    messageData.setTime("2023-02-09 21:33:53")
    messageData.setMsgData("你好")

    val messageDatas=MessageDataNet.getBothMessageData(1001,1003)
    print(messageDatas.toString())
    println()

    val user=UserNet.getUserById(1003)
    print(user.toString())




}