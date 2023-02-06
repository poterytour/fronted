package com.example.poetrytour.ui.message

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuListView
import com.example.poetrytour.R
import com.example.poetrytour.tool.ContextTool.Companion.getContext
import java.util.*

class MessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_list)

        val creator = SwipeMenuCreator { menu ->
                val openItem = SwipeMenuItem(applicationContext)
                openItem.background = ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE))
                openItem.width = 150
                openItem.title = "置顶"
                openItem.titleSize = 18
                openItem.titleColor = Color.WHITE
                menu.addMenuItem(openItem)

                val deleteItem = SwipeMenuItem(applicationContext)
                deleteItem.background = ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25))
                deleteItem.width = 150
                deleteItem.title = "删除"
                deleteItem.titleSize = 18
                deleteItem.titleColor = Color.WHITE
                menu.addMenuItem(deleteItem)
            }

        val listView = findViewById<SwipeMenuListView>(R.id.message_list)
        listView.setMenuCreator(creator)

        listView.setOnMenuItemClickListener { position, menu, index ->
            when (index) {
                0 -> {}
                1 -> {}
            }
            // false : close the menu; true : not close the menu
            false
        }

        val data = arrayOfNulls<String>(30)
        for (i in data.indices) {
            data[i] = "測试数据:$i"
        }

        val lists: MutableList<MessageItem> = ArrayList()
        for (i in 0..5) {
            val messageItem = MessageItem()
            messageItem.image = "https://ts1.cn.mm.bing.net/th/id/R-C.29a84eb867bf75b5327e7df3b1a7e32c?rik=iW9zjAJwqTB%2fdA&riu=http%3a%2f%2ftupian.qqw21.com%2farticle%2fUploadPic%2f2019-7%2f201971622263482217.jpeg&ehk=W4G6YV7SJ1LFEFGJ3r%2bsC66stsnts%2bGu%2b7tsCcMPWGA%3d&risl=&pid=ImgRaw&r=0"
            messageItem.name = "测试$i"
            messageItem.message = "消息$i"
            messageItem.time = "2022-10-06 09:30:0$i"
            val r = Random()
            if (i < 3) { messageItem.num = r.nextInt(10) + 1
            } else {
                messageItem.num = 0
            }
            lists.add(messageItem)
        }

        val adapter = MessageListAdapter(getContext(), lists)
        listView.adapter = adapter

        listView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
                lists[position].num = 0
                lists[position].time = "2022-10-7 10:22:11"
                lists.sortWith(Comparator { t1: MessageItem, t2: MessageItem ->
                    t2.time!!.compareTo(t1.time!!)
                })
                listView.adapter = adapter
            }
    }
}