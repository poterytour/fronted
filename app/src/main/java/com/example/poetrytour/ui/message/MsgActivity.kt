package com.example.poetrytour.ui.message

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.poetrytour.R
import com.example.poetrytour.model.MessageData
import com.example.poetrytour.tool.MessageTool
import com.example.poetrytour.ui.User
import com.example.poetrytour.ui.fragments.MessageFragment
import kotlinx.android.synthetic.main.activity_msg.*
import org.greenrobot.eventbus.EventBus
import java.sql.Timestamp
import java.text.SimpleDateFormat


class MsgActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        var msgList = ArrayList<Msg>()
        init {
            val msg1 = Msg("这是我下决心研究RecyclerView的原因.", Msg.TYPE_RECEIVED)
            msgList.add(msg1)
            val msg2 = Msg("在上篇文章中说过对于像等等此类在有限屏幕中展示大量内容的控件,复用的逻辑就是其核心的逻辑,而关于复用导致最常见的bug就是复用错乱.在大上周我就遇到了一个很奇怪的问题,这也是我下决心研究RecyclerView的原因.", Msg.TYPE_SEND)
            msgList.add(msg2)
            val msg3 = Msg("在上篇文章中说过对于像等等此类在有限屏幕中展示大量内容的控件,复用的逻辑就是其核心的逻辑,而关于复用导致最常见的bug就是复用错乱.在大上周我就遇到了一个很奇怪的问题,这也是我下决心研究RecyclerView的原因.", Msg.TYPE_RECEIVED)
            msgList.add(msg3)
            val msg4 = Msg("这是我下决心研究RecyclerView的原因.", Msg.TYPE_RECEIVED)
            msgList.add(msg4)
        }
    }
    private val TAG="MsgActivity"
    private var fromUserId:String?=null
    private var fromUserImg:String?=null
    private var adapter: MsgAdapter? = null
    val viewModel by lazy { ViewModelProvider(this).get(MessageDataViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msg)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val intent = getIntent()
        fromUserId=intent.getStringExtra("fromUserId")
        fromUserImg=intent.getStringExtra("fromUserImg")
        Log.d(TAG,"$fromUserImg")
        Log.d(TAG,"$fromUserId")

        viewModel.getMsgList().observe(this){
            msgList= it as ArrayList<Msg>
            adapter = MsgAdapter(msgList,fromUserImg!!)
            recyclerView.adapter=adapter
            recyclerView.scrollToPosition(adapter!!.getItemCount()-1);
            Log.d(TAG, msgList.toString())
        }

        send.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v) {
            send -> {
                val content = inputText.text.toString()
                if (content.isNotEmpty()) {
                    val msg = Msg(content, Msg.TYPE_SEND)
                    msgList.add(msg)
                    adapter?.notifyItemInserted(msgList.size - 1) // 当有新消息时， 刷新RecyclerView中的显示
                    recyclerView.scrollToPosition(msgList.size - 1) // 将RecyclerView 定位到最后一行
                    inputText.setText("") // 清空输入框中的内容
                    sendMsg(content)
                    MessageTool.getMessageItem(fromUserId!!).message=content
                    MessageFragment.adapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun sendMsg(content:String){
        var messageData= MessageData()
        messageData.setMsgData(content)
        messageData.setToUserId(fromUserId)
        messageData.setFromUserId(User.user_id.toString())
        messageData.setMsgType(1)
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val now = Timestamp(System.currentTimeMillis())
        val time = df.format(now)
        messageData.setTime(time)
        EventBus.getDefault().post(messageData)
    }

}