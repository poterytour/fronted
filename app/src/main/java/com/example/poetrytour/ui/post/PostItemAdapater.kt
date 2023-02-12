package com.example.poetrytour.ui.post

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
import com.example.poetrytour.tool.TimeTool
import com.example.poetrytour.ui.message.MessageListAdapter


class PostItemAdapater(context: Context,lists:List<PostItem>):BaseAdapter() {

    private var inflater: LayoutInflater? = null
    private var lists:List<PostItem>?=null

    init {
        this.inflater=LayoutInflater.from(context)
        this.lists=lists
    }


    override fun getCount(): Int {
        return lists?.size!!
    }

    override fun getItem(position: Int): PostItem? {
        return lists?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.post_id!!
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView: ItemView
        var view:View
        if (convertView==null){
            view= inflater?.inflate(R.layout.post_item,parent,false) !!
            itemView= ItemView(view)
            view.tag=itemView
        }else{
            view=convertView
            itemView=view.tag as ItemView
        }
        getItem(position)?.let { itemView.init(it) }
        return view
    }



    class ItemView(view:View){
        var publisher_img:ImageView=view.findViewById(R.id.post_item_publisher_img)
        var publisher_name:TextView = view.findViewById(R.id.post_item_publisher_name)
        var local:TextView = view.findViewById(R.id.post_item_localtion)
        var time:TextView =view.findViewById(R.id.post_item_time)
        var display_img:ImageView=view.findViewById(R.id.post_item_display_img)
        var title:TextView=view.findViewById(R.id.post_item_title)
        var pcontext:TextView=view.findViewById(R.id.post_item_context)
        var preading:TextView=view.findViewById(R.id.post_item_reading)
        var plove:TextView=view.findViewById(R.id.post_item_love)
        var pcom_num:TextView=view.findViewById(R.id.post_item_com_num)


        fun init(postItem: PostItem){
            Glide.with(ContextTool.getContext()).load(postItem.publisher?.avatar).into(publisher_img)
            publisher_name.setText(postItem.publisher?.user_name)
            local.setText(postItem.post_local)

            val ptime= postItem.post_time?.let { TimeTool.getShortByString(it) }
            time.setText(ptime)

            Glide.with(ContextTool.getContext()).load(postItem.post_display_img).into(display_img)
            title.setText(postItem.post_title)
            pcontext.setText(postItem.post_context)
            preading.setText(postItem.post_reading.toString())
            plove.setText(postItem.post_love.toString())
            pcom_num.setText(postItem.post_com_num.toString())

        }
    }

}