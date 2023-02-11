package com.example.poetrytour.ui.message

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.nekocode.badge.BadgeDrawable
import com.bumptech.glide.Glide
import com.cwj.oftenview.view.DotView
import com.example.poetrytour.R
import com.example.poetrytour.tool.ContextTool
import com.example.poetrytour.tool.TimeTool
import com.google.android.material.badge.BadgeUtils
import java.util.*


class MessageListAdapter(context:Context,lists:List<MessageItem>): BaseAdapter() {

    private var inflater: LayoutInflater? = null
    private var lists:List<MessageItem>?=null

    init {
        this.inflater=LayoutInflater.from(context)
        this.lists=lists
    }

    override fun getCount(): Int {
        return lists?.size!!
    }

    override fun getItem(position: Int): MessageItem? {
        return lists?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView:ItemView
        var view:View
        if (convertView==null){
            view= inflater?.inflate(R.layout.message_item,parent,false) !!
            itemView=ItemView(view)
            view.tag=itemView
        }else{
            view=convertView
            itemView=view.tag as ItemView
        }
        getItem(position)?.let { itemView.init(it) }
        return view
    }


    class ItemView(view:View){

        var name_:TextView=view.findViewById(R.id.message_user_name_item)
        var time:TextView=view.findViewById(R.id.message_latest_time_item)
        var message:TextView=view.findViewById(R.id.message_context_item)
        var image:ImageView=view.findViewById(R.id.message_avatar_item)
        var num:TextView=view.findViewById(R.id.message_unread_num)


        fun init(messageItem: MessageItem){
            name_.setText(messageItem.name)

            val timeShort= messageItem.time?.let { TimeTool.getShortByString(it) }
            time.setText(timeShort)

            Timer().schedule(object : TimerTask() {
                override fun run() {
                    val timeShort= messageItem.time?.let { TimeTool.getShortByString(it) }
                    time.setText(timeShort)
                }
            }, 60000)

            message.setText(messageItem.message)
            Glide.with(ContextTool.getContext()).load(messageItem.image).into(image)
            val number=messageItem.num

            if (number != null) {
                if(number >0){
                    var drawable =BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_NUMBER)
                        .number(number)
                        .build()
                    num.setText(drawable.toSpannable())
                }else{
                    num.setText(" ")
                }
            }
        }
    }


}