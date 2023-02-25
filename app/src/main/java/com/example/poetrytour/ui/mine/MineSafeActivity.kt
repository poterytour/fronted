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
import com.example.poetrytour.ui.login.LoginResetPasswordActivity
import com.example.poetrytour.ui.login.LoginResetTelActivity
import com.example.poetrytour.ui.login.RegisterActivity
import kotlinx.android.synthetic.main.activity_mine.*
import kotlinx.android.synthetic.main.activity_mine_safe.*

class MineSafeActivity : AppCompatActivity() {
    val viewModel by lazy { ViewModelProvider(this).get(MineViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_safe)
        
        mine_safe_back.setOnClickListener { finish() }
        
        mine_safe_tel.setOnClickListener {
            startActivity(Intent(this,LoginResetTelActivity::class.java))
        }
        
        mine_safe_password.setOnClickListener {
            startActivity(Intent(this,LoginResetPasswordActivity::class.java))
        }
       
    }


}