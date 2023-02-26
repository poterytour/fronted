package com.example.poetrytour.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.poetrytour.MainActivity
import com.example.poetrytour.R
import com.example.poetrytour.tool.ContextTool
import com.example.poetrytour.ui.User
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    val imgSrc="https://m1-cdn.imeijian.cn/f96394e78166fcd2c780cab3e04e8769.png?x-oss-process=style/1000wh_j"

    val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val context="没有账号?<font color='#5de0ab'>注册"
        to_register.setText(Html.fromHtml(context))
        Glide.with(ContextTool.getContext()).load(imgSrc).into(display_picture)

        val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        val login_information = sharedPreferences.getString("login_information",null)
        if(login_information!=null){
            viewModel.setLoginStrLiveData(login_information)
        }

        to_register.setOnClickListener {
            val intent=Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        viewModel.loginResultLiveData.observe(this){
            if(it.isSuccess==true){
                User.login_user=it.data
                User.user_id= it.data!!.user_id
                User.avatar=it.data!!.avatar
                User.user_name=it.data!!.user_name
                val intent=Intent(this, MainActivity::class.java)
                startActivity(intent)
                
                val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
                //获取Editor对象的引用
                val editor = sharedPreferences.edit()
                //将获取过来的值放入文件
                editor.putString("login_information", viewModel.getLoginStrLiveData().value)
                // 提交数据
                editor.commit()
                finish()

            }else{
                Toast.makeText(ContextTool.getContext(),"${it.message}",Toast.LENGTH_SHORT).show()
            }
        }


        login_button.setOnClickListener{
            val tel=login_number.text.toString()
            val password=login_password.text.toString()
            viewModel.setLoginStrLiveData(tel+"-"+password)
        }

    }
}