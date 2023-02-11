package com.example.poetrytour.ui.message

class Msg(val content:String ,val type: Int){
    companion object{
        const val TYPE_RECEIVED=0
        const val TYPE_SEND=1
    }

    override fun toString(): String {
        return "Msg(content='$content', type=$type)"
    }


}