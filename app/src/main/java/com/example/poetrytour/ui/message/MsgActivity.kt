package com.example.poetrytour.ui.message

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.poetrytour.R
import kotlinx.android.synthetic.main.activity_msg.*


class MsgActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        val msgList = ArrayList<Msg>()
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
    private var adapter: MsgAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msg)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        adapter = MsgAdapter(msgList)
        recyclerView.adapter = adapter
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
                }
            }
        }
    }

}