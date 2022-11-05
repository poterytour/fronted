package com.example.poetrytour.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.poetrytour.R
import com.example.poetrytour.tool.ContextTool
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val imgSrc="https://m1-cdn.imeijian.cn/f96394e78166fcd2c780cab3e04e8769.png?x-oss-process=style/1000wh_j"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val context="没有账号?<font color='#5de0ab'>注册"
        to_register.setText(Html.fromHtml(context))
        Glide.with(ContextTool.getContext()).load(imgSrc).into(display_picture)
        forget_password.setOnClickListener{
            val intent=Intent(this,LoginResetPasswordActivity::class.java)
            startActivity(intent)
        }
        to_register.setOnClickListener {
            val intent=Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}