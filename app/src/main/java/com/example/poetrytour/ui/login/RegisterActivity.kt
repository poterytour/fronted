package com.example.poetrytour.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.poetrytour.MainActivity
import com.example.poetrytour.R
import com.example.poetrytour.tool.ContextTool
import com.example.poetrytour.ui.User

import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class RegisterActivity : AppCompatActivity() {

    val imgSrc="https://m1-cdn.imeijian.cn/f96394e78166fcd2c780cab3e04e8769.png?x-oss-process=style/1000wh_j"
    val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        Glide.with(ContextTool.getContext()).load(imgSrc).into(register_display_picture)


        viewModel.registerResultLiveData.observe(this){
            if(it.isSuccess==true){
                User.login_user=it.data
                User.user_id= it.data!!.user_id
                User.avatar=it.data!!.avatar
                User.user_name=it.data!!.user_name
                val intent= Intent(this, MainActivity::class.java)
                startActivity(intent)

                val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
                //获取Editor对象的引用
                val editor = sharedPreferences.edit()
                //将获取过来的值放入文件
                editor.putString("login_information", viewModel.getLoginStrLiveData().value)
                // 提交数据
                editor.commit()

            }else{
                Toast.makeText(ContextTool.getContext(),"${it.message}",Toast.LENGTH_SHORT).show()
            }
        }

        register_back_to_login.setOnClickListener {
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        register_button.setOnClickListener {
            val tel=register_number.text.toString()
            val password=register_password.text.toString()
            val p: Pattern = Pattern.compile("^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$") //正则
            val m: Matcher = p.matcher(tel)
            if (m.matches()) {
               if(password.isNullOrEmpty()){
                   Toast.makeText(applicationContext, "密码不能为空" ,Toast.LENGTH_LONG).show()
               }else {
                   if (password.length >= 6) {
                       viewModel.setRegisterSteLiveData(tel+"-"+password)
                   } else {
                       Toast.makeText(applicationContext, "密码至少为6位", Toast.LENGTH_LONG).show()
                   }
               }
            } else {
                Toast.makeText(applicationContext, "手机号输入不正确" ,Toast.LENGTH_LONG).show()
            }
        }
    }
}