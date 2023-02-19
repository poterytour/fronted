package com.example.poetrytour.ui.message

import androidx.lifecycle.*
import com.example.poetrytour.network.MessageDataNet
import com.example.poetrytour.network.UserNet
import com.example.poetrytour.tool.MessageTool
import com.example.poetrytour.ui.User
import kotlinx.coroutines.Dispatchers

class MessageDataViewModel:ViewModel() {
    companion object{
        fun setFromUserId(fromUserId: Long){
            fromUserIdLiveData.value=fromUserId
        }

        private var fromUserIdLiveData= MutableLiveData<Long>()
    }


    val UserLiveDate=Transformations.switchMap(fromUserIdLiveData){
        getUser(it)
    }

    private var MsgListLiveData=Transformations.switchMap(fromUserIdLiveData){
        fromUserIdLiveData.value?.let { it1 -> getMsgLists(it1) }
    }

     fun getMsgLists(fromUserId:Long):LiveData<List<Msg>>{
        val msglists= liveData<List<Msg>>(Dispatchers.IO) {
            val rs:List<Msg>
            val messageDatas=MessageDataNet.getBothMessageData(fromUserId, User.user_id!!)
            rs=MessageTool.messageDataListToMsgList(messageDatas)
            emit(rs)
        }
        return msglists
    }

    fun getUser(fromUserId:Long):LiveData<com.example.poetrytour.model.User>{
        val user= liveData<com.example.poetrytour.model.User>(Dispatchers.IO){
            val user=UserNet.getUserById(fromUserId)
            emit(user)
        }
        return user
    }

    fun getFromUserId():LiveData<Long>{
        return fromUserIdLiveData
    }

    fun getMsgList():LiveData<List<Msg>>{
        return MsgListLiveData
    }




}