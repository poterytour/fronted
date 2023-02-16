package com.example.poetrytour.ui.message

import android.util.Log
import androidx.lifecycle.*
import com.example.poetrytour.model.MessageData
import com.example.poetrytour.network.MessageDataNet
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

        private var userIdLiveData=MutableLiveData<Long>()


    }


    val messageItemLiveData=Transformations.switchMap(messageDataLiveData){
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


    val initMessageItemListLiveData=Transformations.switchMap(userIdLiveData){
        initMessageItem(it)
    }

    fun setUserIdLiveDate(id:Long){
        userIdLiveData.value=id
    }


    fun initMessageItem(id:Long):LiveData<List<MessageItem>>{
        val lists= liveData<List<MessageItem>>(Dispatchers.IO){
            var list=ArrayList<MessageItem>()
            val messages=MessageDataNet.initMessageItem(id);
            for( message in messages){
                val rs= MessageItem()
                val fromUserId=message.getFromUserId()!!.toLong()
                val user=UserNet.getUserById(fromUserId)
                if (user != null) {
                    rs.userId= user.user_id.toString()
                    rs.name=user.user_name
                    rs.image=user.avatar
                }
                rs.message=message.getMsgData()
                rs.time=message.getTime()
                //获取离线信息数量
                val num=MessageDataNet.deleteOffMessageById(fromUserId)
                rs.num=num
                Log.d("MessageItem","$num")
                list.add(rs)
            }
            emit(list)
        }
        return lists
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