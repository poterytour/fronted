package com.example.poetrytour.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.poetrytour.R
import com.example.poetrytour.ui.User
import com.example.poetrytour.ui.mine.MineViewModel
import kotlinx.android.synthetic.main.activity_login_reset_tel.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginResetTelActivity : AppCompatActivity() {
	val viewModel by lazy { ViewModelProvider(this).get(MineViewModel::class.java) }
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login_reset_tel)
		
		reset_tel_back.setOnClickListener { finish() }
		
		viewModel.updateLiveData.observe(this){
			Toast.makeText(this,"更改号码成功",Toast.LENGTH_SHORT).show()
			reset_tel_new.setText("")
			reset_tel_old.setText("")
			rest_tel_confirm.setText("")
			User.login_user=it
			
			//重置登录信息
			val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
			val editor = sharedPreferences.edit()
			editor.putString("login_information", it.user_tel+"_"+it.user_password)
			editor.commit()
		}
		
		reset_tel_submit.setOnClickListener {
			val old=reset_tel_old.text.toString()
			val new=reset_tel_new.text.toString()
			val confirm=rest_tel_confirm.text.toString()
			
			if(checkTel(old) || checkTel(new) || checkTel(confirm)){
				Toast.makeText(this,"号码格式输入错误",Toast.LENGTH_SHORT).show()
			}else{
				if(new.equals(confirm)){
					if(new.equals(old)){
						Toast.makeText(this,"新号码与旧号码相同",Toast.LENGTH_SHORT).show()
					}else{
						val user=User.login_user
						if (user != null) {
							user.user_tel=new
							viewModel.setUpdateUserLiveData(user)
						}
					}
				}else{
					Toast.makeText(this,"两次输入的新号码不一致",Toast.LENGTH_SHORT).show()
				}
			}
			
		}
	}
	
	private fun checkTel(tel:String):Boolean{
		val p: Pattern = Pattern.compile("^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$") //正则
		val m: Matcher = p.matcher(tel)
		if (m.matches()){
			return false
		}
		return true
	}
}