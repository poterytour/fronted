package com.example.poetrytour.ui.mine

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.poetrytour.R
import java.io.InputStream

class MineFileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_file)
        val text: TextView = findViewById(R.id.privacy_text)
        val s = intent.getStringExtra("key")
        val get = GetPrivacyFile()
        val inputStream:InputStream
        when(s?.let { Integer.parseInt(it) }){
            R.id.policy_privacy->{
                inputStream = resources.openRawResource(R.raw.policy_privacy)
                text.text =get.getString(inputStream)
            }
            R.id.abstract_privacy->{
                inputStream = resources.openRawResource(R.raw.abstract_privacy)
                text.text =get.getString(inputStream)
            }
            R.id.san_privacy->{
                inputStream = resources.openRawResource(R.raw.san_privacy)
                text.text =get.getString(inputStream)
            }
            R.id.children_privacy->{
                inputStream = resources.openRawResource(R.raw.children_privacy)
                text.text =get.getString(inputStream)
            }
        }
    }
}