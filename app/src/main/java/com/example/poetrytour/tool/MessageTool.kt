package com.example.poetrytour.tool

import com.example.poetrytour.model.MessageData
import com.example.poetrytour.network.MessageDataNet
import com.example.poetrytour.ui.fragments.MessageFragment
import com.example.poetrytour.ui.message.MessageItem
import com.example.poetrytour.ui.message.Msg
import com.example.poetrytour.ui.message.User


object MessageTool {

    fun addMessageItem(messageItem: MessageItem){
        MessageFragment.messageItemlists.add(messageItem)
        MessageFragment.adapter.notifyDataSetChanged()
    }

    fun removeMessageItem(position:Int){
        MessageFragment.messageItemlists.removeAt(position)
        MessageFragment.adapter.notifyDataSetChanged()
    }


    fun getMessageItem(userId:String):MessageItem{
        for(item in MessageFragment.messageItemlists){
            if(item.userId==userId){
                return item
            }
        }
        return MessageItem()
    }

    fun messageDataToMsg(messageData: MessageData):Msg{
        if (messageData.getFromUserId()!!.toLong()!=User.user_id){
            return Msg(messageData.getMsgData()!!,0)
        }else{
            return Msg(messageData.getMsgData()!!,1)
        }
    }

    fun messageDataListToMsgList(list:List<MessageData>):List<Msg>{
        val rs:MutableList<Msg> = ArrayList()
        for(item in list){
            rs.add(0, messageDataToMsg(item))
        }
        return rs
    }

}