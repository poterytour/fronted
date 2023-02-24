package com.example.poetrytour.ui.mine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import com.example.poetrytour.R
import com.example.poetrytour.tool.ContextTool
import com.example.poetrytour.ui.User
import com.example.poetrytour.ui.post.PostActivity
import com.example.poetrytour.ui.post.PostItem
import com.example.poetrytour.ui.post.PostItemAdapater
import kotlinx.android.synthetic.main.activity_mine_publisher_post.*
import java.util.concurrent.CopyOnWriteArrayList

class MinePublisherPostActivity : AppCompatActivity() {
	
	val viewModel by lazy { ViewModelProvider(this).get(MineViewModel::class.java) }
	private var lists:MutableList<PostItem> = CopyOnWriteArrayList()
	private var adapter: PostItemAdapater? = null
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_mine_publisher_post)
		
		mine_post_back.setOnClickListener { finish() }
		
		val intent=getIntent()
		val user_id=intent.getStringExtra("user_id")?.toLong()
		user_id?.let { viewModel.setUserIdLiveData(it) }
		
		viewModel.minePostItemLiveData.observe(this){
			if(it.size==0){
				mine_post_tip.setText("暂时没有发布帖子")
			}else{
				mine_post_tip.setText("")
			}
			for(item in it){
				lists.add(item)
			}
			adapter=PostItemAdapater(ContextTool.getContext(), lists)
			mine_post_list.adapter=adapter
			mine_post_list.onItemClickListener =
				AdapterView.OnItemClickListener { parent, view, position, id ->
					val post_id= adapter!!.getItemId(position)
					var intent= Intent(this, PostActivity::class.java)
					intent.putExtra("post_id",post_id.toString())
					startActivity(intent)
				}
		}
	}
}