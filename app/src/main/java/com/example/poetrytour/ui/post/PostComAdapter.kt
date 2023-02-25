package com.example.poetrytour.ui.post

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.poetrytour.R
import com.example.poetrytour.model.Comment
import com.example.poetrytour.model.Post
import com.example.poetrytour.tool.ContextTool
import com.example.poetrytour.tool.TimeTool
import com.example.poetrytour.ui.User
import com.example.poetrytour.ui.message.MineBasicActivity

class PostComAdapter(val comList: List<Comment>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ComViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img:ImageView=view.findViewById(R.id.post_com_publisher_img)
        val cname:TextView=view.findViewById(R.id.post_com_publisher_name)
        val ccontext:TextView=view.findViewById(R.id.post_com_context)
        val ctime:TextView=view.findViewById(R.id.post_com_publisher_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.post_com,
            parent, false)
        return ComViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val com=comList.get(position)
        when(holder){
            is ComViewHolder->{
                Glide.with(ContextTool.getContext())
                    .load(com.publisher?.avatar)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(holder.img)
                holder.img.setOnClickListener {
                    val userId= com.publisher?.user_id
                    if(userId!=User.user_id&&userId!=null) {
                        val intent = Intent(ContextTool.getContext(), MineBasicActivity::class.java)
                        intent.putExtra("user_id", userId.toString())
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        ContextTool.getContext().startActivity(intent)
                    }
                }
                holder.cname.setText(com.publisher?.user_name)
                holder.ccontext.setText(com.com_context)
                val time= com.com_time?.let { TimeTool.transToString(it) }
                holder.ctime.setText(time?.let { TimeTool.getShortByString(it) })
            }
        }
    }

    override fun getItemCount(): Int {
        return comList.size
    }

}