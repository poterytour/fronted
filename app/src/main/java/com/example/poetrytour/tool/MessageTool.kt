package com.example.poetrytour.tool

import com.example.poetrytour.ui.fragments.MessageFragment
import com.example.poetrytour.ui.message.MessageItem


object MessageTool {

    fun addMessageItem(messageItem: MessageItem){
        MessageFragment.messageItemlists.add(messageItem)
        MessageFragment.adapter.notifyDataSetChanged()
    }

    fun removeMessageItem(position:Int){
        MessageFragment.messageItemlists.removeAt(position)
        MessageFragment.adapter.notifyDataSetChanged()
    }
}