package com.example.poetrytour.ui.message

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.poetrytour.R
import com.example.poetrytour.model.MessageData
import com.example.poetrytour.tool.ContextTool
import com.example.poetrytour.tool.MessageTool
import com.example.poetrytour.ui.User
import com.example.poetrytour.ui.fragments.MessageFragment
import kotlinx.android.synthetic.main.activity_msg.*
import kotlinx.android.synthetic.main.activity_post.*
import org.greenrobot.eventbus.EventBus
import java.sql.Timestamp
import java.text.SimpleDateFormat


class MsgActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        var msgList = ArrayList<Msg>()
    }
    private val TAG="MsgActivity"
    private var fromUserId:String?=null
    private var fromUserImg:String?=null
    private var adapter: MsgAdapter? = null

    private var user:com.example.poetrytour.model.User?=null
    val viewModel by lazy { ViewModelProvider(this).get(MessageDataViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msg)

        setSupportActionBar(msg_toolbar)
        msg_toolbar.setNavigationOnClickListener{
            finish()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager


        viewModel.UserLiveDate.observe(this){
            msg_name.setText("${it.user_name}")
            Glide.with(ContextTool.getContext())
                .load(it.avatar)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(msg_img)
            fromUserImg=it.avatar
            fromUserId=it.user_id.toString()
        }

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