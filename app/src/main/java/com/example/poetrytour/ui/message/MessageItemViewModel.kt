package com.example.poetrytour.ui.message

import androidx.lifecycle.*
import com.example.poetrytour.model.MessageData
import com.example.poetrytour.network.UserNet
import kotlinx.coroutines.Dispatchers


class MessageItemViewModel : ViewModel(){
    companion object{
        fun getMessageData(): LiveData<MessageData> {
            return messageDataLiveData
        }

        fun setMessageData(messageData: MessageData){
            messageDataLiveData.value=messageData
        }
        private var messageDataLiveData=MutableLiveData<MessageData>()
    }


    private var messageItemLiveData=Transformations.switchMap(messageDataLiveData){
        messageDataLiveData.value?.let { it1 -> getMessageItem(it1) }
    }


    fun getMessageItem(messageData: MessageData):LiveData<MessageItem>{
        val messageItem=liveData<MessageItem>(Dispatchers.IO){
            val user= messageData.getFromUserId()?.let { UserNet.getUserById(it.toLong()) }
            val rs= MessageItem()
            if (user != null) {
                rs.userId= user.user_id.toString()
                rs.name=user.user_name
                rs.image=user.avatar
            }
            rs.message=messageData.getMsgData()
            rs.time=messageData.getTime()
            emit(rs)
        }
        return messageItem
    }

    fun getMessageData(): LiveData<MessageData> {
        return messageDataLiveData
    }

    fun setMessageData(messageData: MessageData){
        messageDataLiveData.value=messageData
    }

    fun getMessageItem(): LiveData<MessageItem> {
        return messageItemLiveData
    }




}