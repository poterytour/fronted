package com.example.poetrytour.ui.mine


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.poetrytour.R


class MinePrivacyActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_privacy)
        findViewById<LinearLayout>(R.id.policy_privacy).setOnClickListener(this)
        findViewById<LinearLayout>(R.id.abstract_privacy).setOnClickListener(this)
        findViewById<LinearLayout>(R.id.san_privacy).setOnClickListener(this)
        findViewById<LinearLayout>(R.id.children_privacy).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val intent = Intent(this@MinePrivacyActivity, MineFileActivity::class.java)
        intent.putExtra("key",v?.id.toString())
        startActivity(intent)
    }
}