package com.example.poetrytour.ui.fragments

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuListView
import com.example.poetrytour.R
import com.example.poetrytour.tool.ContextTool
import com.example.poetrytour.tool.MessageTool
import com.example.poetrytour.tool.TimeTool
import com.example.poetrytour.ui.message.MessageItem
import com.example.poetrytour.ui.message.MessageListAdapter
import com.example.poetrytour.ui.message.MsgActivity
import java.util.*
import kotlin.Comparator

class MessageFragment:Fragment() {
    companion object{
        val messageItemlists: MutableList<MessageItem> = ArrayList()
        init {
            for (i in 0..5) {
                val messageItem = MessageItem()
                messageItem.image = "https://ts1.cn.mm.bing.net/th/id/R-C.29a84eb867bf75b5327e7df3b1a7e32c?rik=iW9zjAJwqTB%2fdA&riu=http%3a%2f%2ftupian.qqw21.com%2farticle%2fUploadPic%2f2019-7%2f201971622263482217.jpeg&ehk=W4G6YV7SJ1LFEFGJ3r%2bsC66stsnts%2bGu%2b7tsCcMPWGA%3d&risl=&pid=ImgRaw&r=0"
                messageItem.name = "测试$i"
                messageItem.message = "消息$i"

                val current=GregorianCalendar()
                current.time=Date()
                if(i<5){
                    current.add(GregorianCalendar.DAY_OF_MONTH,-i)
                }else{
                    current.add(GregorianCalendar.YEAR,-1)
                }
                messageItem.time = TimeTool.dateToString(current.time)
                val r = Random()
                if (i < 3) { messageItem.num = r.nextInt(10) + 1
                } else {
                    messageItem.num = 0
                }
                messageItemlists.add(messageItem)
            }
        }

        val adapter = MessageListAdapter(ContextTool.getContext(), messageItemlists)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view=inflater.inflate(R.layout.activity_message_list,container,false)

        val creator = SwipeMenuCreator { menu ->
            val deleteItem = SwipeMenuItem(ContextTool.getContext())
            deleteItem.background = ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25))
            deleteItem.width = 200
            deleteItem.title = "删除"
            deleteItem.titleSize = 18
            deleteItem.titleColor = Color.WHITE
            menu.addMenuItem(deleteItem)
        }

        val listView = view.findViewById<SwipeMenuListView>(R.id.message_list)
        listView.setMenuCreator(creator)

        listView.setOnMenuItemClickListener { position, menu, index ->
            when (index) {
                0 -> {
                    MessageTool.removeMessageItem(position)
                }
            }
            // false : close the menu; true : not close the menu
            false
        }


        listView.adapter = adapter

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                messageItemlists[position].num = 0
                val intent= Intent(context, MsgActivity::class.java)
                startActivity(intent)
                listView.adapter = adapter
            }

        return view
    }
}