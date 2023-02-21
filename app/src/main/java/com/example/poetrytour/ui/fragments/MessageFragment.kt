package com.example.poetrytour.ui.fragments

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuListView
import com.example.poetrytour.R
import com.example.poetrytour.tool.ContextTool
import com.example.poetrytour.tool.MessageTool
import com.example.poetrytour.tool.TimeTool
import com.example.poetrytour.ui.User
import com.example.poetrytour.ui.message.*
import kotlinx.android.synthetic.main.message_item.view.*
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.Comparator

class MessageFragment:Fragment() {

    private val TAG="MessageFragment"
    companion object{
        val messageItemlists: MutableList<MessageItem> = CopyOnWriteArrayList()
        val adapter = MessageListAdapter(ContextTool.getContext(), messageItemlists)
    }

    val viewModel by lazy { ViewModelProvider(this).get(MessageItemViewModel::class.java) }

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
                MessageDataViewModel.setFromUserId(messageItemlists[position].userId!!.toLong())
                adapter.notifyDataSetChanged()
                listView.adapter= adapter
            }

        User.user_id?.let { viewModel.setUserIdLiveDate(it) }

        activity?.let{

            viewModel.initMessageItemListLiveData.observe(it){
                if(messageItemlists.size>0) {
                    for (item in messageItemlists) {
                        messageItemlists.removeAll { true }
                    }
                }
                for(item in it){
                    messageItemlists.add(item)
                    sort()
                    adapter.notifyDataSetChanged()
                    Log.d(TAG+"1", messageItemlists.toString())
                }
            }

            viewModel.messageItemLiveData.observe(it){ messageItem->
                for (item in messageItemlists) {
                    if (item.userId!!.toLong()== messageItem.userId!!.toLong()){
                        messageItem.num=item.num
                        messageItemlists.remove(item)
                    }
                }
                messageItem.num= messageItem.num!!.plus(1)
                messageItemlists.add(0,messageItem)
                sort()
                adapter.notifyDataSetChanged()
                Log.d(TAG+"2","${messageItem.num}")
                Log.d(TAG+"2", messageItemlists.toString())
            }
        }


        return view
    }



    fun sort(){
        messageItemlists.sortByDescending { it.time }
    }


}