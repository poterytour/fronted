package com.example.poetrytour.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.poetrytour.R
import com.example.poetrytour.ui.User
import com.example.poetrytour.ui.mine.MineViewModel
import kotlinx.android.synthetic.main.activity_login_reset_password.*

class LoginResetPasswordActivity : AppCompatActivity() {
    
    val viewModel by lazy { ViewModelProvider(this).get(MineViewModel::class.java) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_reset_password)
        
        viewModel.updateLiveData.observe(this){
            Toast.makeText(this,"重置密码成功",Toast.LENGTH_SHORT).show()
            reset_password_new.setText("")
            reset_password_confirm.setText("")
            User.login_user=it
    
            //重置登录信息
            val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("login_information", it.user_tel+"_"+it.user_password)
            editor.commit()
        }
        
        reset_password_back.setOnClickListener { finish() }
        
        reset_password_submit.setOnClickListener {
            val new=reset_password_new.text.toString()
            val confirm=reset_password_confirm.text.toString()
            if(new.isNullOrEmpty() || confirm.isNullOrEmpty()){
                Toast.makeText(this,"输入不能为空",Toast.LENGTH_SHORT).show()
            }else{
                if(new.equals(confirm)){
                    if(new.length>=6){
                        var user=User.login_user
                        if (user != null) {
                            user.user_password=new
                            viewModel.setUpdateUserLiveData(user)
                        }
                    }else{
                        Toast.makeText(this,"密码至少为6位",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this,"两次输入不一样",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}