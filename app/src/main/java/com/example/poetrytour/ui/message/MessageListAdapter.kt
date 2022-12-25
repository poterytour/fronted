package com.example.poetrytour.ui.message

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.poetrytour.R
import com.example.poetrytour.tool.ContextTool


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


        fun init(messageItem: MessageItem){
            name_.setText(messageItem.name)
            time.setText(messageItem.time)
            message.setText(messageItem.message)
            Glide.with(ContextTool.getContext()).load(messageItem.image).into(image)

        }

    }

}