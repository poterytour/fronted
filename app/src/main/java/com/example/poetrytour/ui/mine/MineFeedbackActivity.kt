package com.example.poetrytour.ui.mine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.poetrytour.R
import kotlinx.android.synthetic.main.activity_mine_feedback.*

class MineFeedbackActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var editText:EditText
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_feedback)
        editText= findViewById(R.id.feedback)
        button = findViewById(R.id.feedback_button)
        button.setOnClickListener(this)
        mine_feedback_back.setOnClickListener { finish() }
    }

    override fun onClick(v: View?) {
        Toast.makeText(this,"提交成功",Toast.LENGTH_SHORT).show()
    }
}