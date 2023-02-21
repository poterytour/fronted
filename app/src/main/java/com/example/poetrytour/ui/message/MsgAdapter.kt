package com.example.poetrytour.ui.message

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.poetrytour.R
import com.example.poetrytour.tool.ContextTool
import com.example.poetrytour.tool.PopupList
import com.example.poetrytour.tool.PopupList.PopupListListener


class MsgAdapter(val msgList: List<Msg>,val imgUrl:String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class LeftViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val leftMsg: TextView = view.findViewById(R.id.leftMsg)
        val leftImg:ImageView=view.findViewById(R.id.left_img)
        val leftArea:LinearLayout=view.findViewById(R.id.left_msg_area)
    }
    inner class RightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rightMsg: TextView = view.findViewById(R.id.rightMsg)
        val rightImg:ImageView=view.findViewById(R.id.right_img)
        val rightArea:LinearLayout=view.findViewById(R.id.right_msg_area)
    }
    override fun getItemViewType(position: Int): Int {
        val msg = msgList[position]
        return msg.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = if (viewType ==
        Msg.TYPE_RECEIVED) {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.msg_left_item,
            parent, false)
        LeftViewHolder(view)
    } else {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.msg_right_item,
            parent, false)
        RightViewHolder(view)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = msgList[position]
        val popupList=PopupList(ContextTool.getContext())
        val pos=position
        when (holder) {
            is LeftViewHolder -> {
                Glide.with(ContextTool.getContext()).load(imgUrl).into(holder.leftImg)
                holder.leftMsg.text = msg.content
                holder.leftMsg.minHeight=60
                popupList.bind(holder.leftArea, arrayListOf("复制","删除"), object : PopupListListener {
                    override fun showPopupList(adapterView: View?, contextView: View?, contextPosition: Int): Boolean {
                        return true
                    }
                    override fun onPopupListClick(contextView: View?, contextPosition: Int, position: Int) {
                        when (position){
                            //复制
                            0 ->copyStr(holder.leftMsg.text.toString())
                            1 ->{
                                removed(pos)
                            }
                        }
                    }
                })


            }
            is RightViewHolder -> {
                holder.rightMsg.text = msg.content
                holder.rightMsg.minHeight=60
                popupList.bind(holder.rightArea, arrayListOf("复制","删除"), object : PopupListListener {
                    override fun showPopupList(adapterView: View?, contextView: View?, contextPosition: Int): Boolean {
                        return true
                    }
                    override fun onPopupListClick(contextView: View?, contextPosition: Int, position: Int) {
                      when (position){
                            //复制
                            0 ->copyStr(holder.rightMsg.text.toString())
                            1 ->{
                                removed(pos)
                            }
                        }
                    }
                })
            }
            else -> throw IllegalArgumentException()
        }
    }
    override fun getItemCount() = msgList.size

    private fun copyStr(copyStr: String): Boolean {
        return try {
            //获取剪贴板管理器
            val cm = ContextTool.getContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            // 创建普通字符型ClipData
            val mClipData = ClipData.newPlainText("Label", copyStr)
            // 将ClipData内容放到系统剪贴板里。
            cm!!.setPrimaryClip(mClipData)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun removed(position: Int) {
        MsgActivity.msgList.removeAt(position)
        notifyItemRangeRemoved(position, 1)
    }

}