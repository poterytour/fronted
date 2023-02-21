package com.example.poetrytour.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.poetrytour.MainActivity
import com.example.poetrytour.R
import com.example.poetrytour.tool.ContextTool
import com.example.poetrytour.ui.User
import kotlinx.android.synthetic.main.activity_ads.*
import java.util.*

class AdsActivity : AppCompatActivity() {

    var countdown = 4
    var loginState = 0
    val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ads)

        Glide.with(ContextTool.getContext())
            .load("http://palpitate.wang/wp-content/uploads/2023/02/引导图片.png")
            .into(ads)

        var handler: Handler? = null
        var runnable: Runnable? = null
        var tv: TextView? = null
        val timer = Timer()



        val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        val login_information = sharedPreferences.getString("login_information",null)
        if(login_information!=null){
            viewModel.setLoginStrLiveData(login_information)
        }

        viewModel.loginResultLiveData.observe(this){
            if(it.isSuccess==true){
                User.login_user=it.data
                User.user_id= it.data!!.user_id
                User.avatar=it.data!!.avatar
                User.user_name=it.data!!.user_name
                loginState=1
            }
        }

        handler = Handler()
        handler.postDelayed(Runnable {
            var intent:Intent
            if(loginState==1) {
                intent = Intent(this, MainActivity::class.java)
            }else{
                intent=Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        }.also { runnable = it }, 3000)

        tv = findViewById<View>(R.id.tv) as TextView

        var task: TimerTask = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    countdown--
                    tv.setText("  剩余"+"$countdown"+"s  ")
                    if (countdown < 0) {
                        timer.cancel()
                        tv.setVisibility(View.GONE)
                    }
                }
            }
        }
        timer.schedule(task, 1000, 1000)

    }



}