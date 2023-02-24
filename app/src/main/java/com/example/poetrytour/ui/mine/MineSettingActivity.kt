package com.example.poetrytour.ui.mine

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.poetrytour.R
import com.example.poetrytour.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_mine_setting.*
import kotlin.system.exitProcess


class MineSettingActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_setting)
        findViewById<LinearLayout>(R.id.safe).setOnClickListener(this)
        findViewById<LinearLayout>(R.id.assist).setOnClickListener(this)
        findViewById<LinearLayout>(R.id.about).setOnClickListener(this)
        findViewById<LinearLayout>(R.id.qiehuan).setOnClickListener(this)
        findViewById<LinearLayout>(R.id.exit).setOnClickListener(this)
        
        mine_setting_back.setOnClickListener { finish() }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.exit->{
                val intent = Intent(this, LoginActivity::class.java)
                val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.clear()
                editor.commit()
                startActivity(intent)
                finish()
            }
            R.id.qiehuan->{
                startActivity(Intent(this@MineSettingActivity,LoginActivity::class.java))
            }
            R.id.safe->{
                startActivity(Intent(this@MineSettingActivity,MineSafeActivity::class.java))
            }
            R.id.assist->{
                startActivity(Intent(this@MineSettingActivity,MineIntroActivity::class.java))
            }
            R.id.about->{
                startActivity(Intent(this@MineSettingActivity,MineAboutActivity::class.java))
            }
        }
    }


}