package com.example.poetrytour.ui.message

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.poetrytour.R
import com.example.poetrytour.model.User
import com.example.poetrytour.tool.ContextTool
import com.example.poetrytour.ui.fragments.MessageFragment
import com.example.poetrytour.ui.fragments.PostFragment
import com.example.poetrytour.ui.mine.MineCollectActivity
import com.example.poetrytour.ui.mine.MineLovedActivity
import com.example.poetrytour.ui.mine.MinePublisherPostActivity
import com.example.poetrytour.ui.mine.MineViewModel
import kotlinx.android.synthetic.main.activity_mine_basic.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.math.min

class MineBasicActivity : AppCompatActivity() {
	

	
	val viewModel by lazy { ViewModelProvider(this).get(MineViewModel::class.java) }
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_mine_basic)
		val intent=getIntent()
		val user_id=intent.getStringExtra("user_id")?.toLong()
		user_id?.let { viewModel.setUserIdLiveData(it) }
		viewModel.userLiveData.observe(this){
			mine_basic_intro.setText(it.intro)
			mine_basic_name.setText(it.user_name)
			Glide.with(ContextTool.getContext())
				.load(it.avatar)
				.into(mine_basic_tx)
		}
		
		mine_basic_back.setOnClickListener { finish() }
		
		mine_basic_message.setOnClickListener {
			val intent= Intent(this, MsgActivity::class.java)
			startActivity(intent)
			user_id?.let { it1 -> MessageDataViewModel.setFromUserId(it1) }
			
		}
		
		mine_basic_collection.setOnClickListener {
			val intent=Intent(this, MineCollectActivity::class.java)
			intent.putExtra("user_id", user_id.toString())
			startActivity(intent)
		}
		
		mine_basic_love.setOnClickListener {
			val intent=Intent(this, MineLovedActivity::class.java)
			intent.putExtra("user_id", user_id.toString())
			startActivity(intent)
		}
		
		mine_basic_text.setOnClickListener {
			val intent=Intent(this, MinePublisherPostActivity::class.java)
			intent.putExtra("user_id", user_id.toString())
			startActivity(intent)
		}
	}
	

	
}