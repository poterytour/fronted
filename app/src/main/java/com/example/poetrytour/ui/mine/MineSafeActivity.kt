package com.example.poetrytour.ui.mine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.poetrytour.R
import com.example.poetrytour.tool.ContextTool
import com.example.poetrytour.ui.User
import com.example.poetrytour.ui.login.LoginActivity
import com.example.poetrytour.ui.login.RegisterActivity
import kotlinx.android.synthetic.main.activity_mine.*
import kotlinx.android.synthetic.main.activity_mine_safe.*

class MineSafeActivity : AppCompatActivity(), View.OnClickListener {
    val viewModel by lazy { ViewModelProvider(this).get(MineViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_safe)
        viewModel.setUserIdLiveData(User.user_id!!)
        this.let {
            viewModel.userLiveData.observe(it) {
                zhanghu_nicheng.text = it.user_name

                Glide.with(ContextTool.getContext())
                    .load(it.avatar)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(zhanghu_tx)
            }
        }
        findViewById<LinearLayout>(R.id.add_zhanghu).setOnClickListener(this)
        findViewById<LinearLayout>(R.id.exit_zhanghu).setOnClickListener(this)
        findViewById<LinearLayout>(R.id.new_zhanghu).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.exit_zhanghu->{
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("closeType", 1)
                startActivity(intent)
                finish()
            }
            R.id.add_zhanghu->{
                startActivity(Intent(this@MineSafeActivity,LoginActivity::class.java))
            }
            R.id.new_zhanghu->{
                startActivity(Intent(this@MineSafeActivity,RegisterActivity::class.java))
            }
        }
    }
}