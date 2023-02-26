package com.example.poetrytour.ui.mine

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.poetrytour.R
import com.makeramen.roundedimageview.RoundedImageView
import java.io.InputStream

class MineAboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_about)
        val text:TextView = findViewById(R.id.app_jieshao)
        val img:RoundedImageView = findViewById(R.id.app_icon)
        val get = GetPrivacyFile()
        val inputStream:InputStream=resources.openRawResource(R.raw.app_introduce)
        text.setTypeface(Typeface.SERIF,Typeface.BOLD)
        text.text = get.getString(inputStream)
        img.setImageResource(R.drawable.app_icon)
    }
}