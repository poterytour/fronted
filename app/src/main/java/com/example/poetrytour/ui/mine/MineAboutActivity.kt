package com.example.poetrytour.ui.mine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.poetrytour.R
import java.io.InputStream

class MineAboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_about)
        val text:TextView = findViewById(R.id.app_jieshao)
        val img:ImageView = findViewById(R.id.app_icon)
        val get = GetPrivacyFile()
        val inputStream:InputStream=resources.openRawResource(R.raw.abstract_privacy)
        text.text = get.getString(inputStream)
        img.setImageResource(R.drawable.img_tx)
    }
}