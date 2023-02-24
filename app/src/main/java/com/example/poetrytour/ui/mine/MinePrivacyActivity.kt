package com.example.poetrytour.ui.mine


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.poetrytour.R
import kotlinx.android.synthetic.main.activity_mine_privacy.*


class MinePrivacyActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_privacy)
        findViewById<LinearLayout>(R.id.policy_privacy).setOnClickListener(this)
        findViewById<LinearLayout>(R.id.abstract_privacy).setOnClickListener(this)
        findViewById<LinearLayout>(R.id.san_privacy).setOnClickListener(this)
        findViewById<LinearLayout>(R.id.quanxian).setOnClickListener{
            gotoAppDetailIntent(this@MinePrivacyActivity)
        }
        mine_privacy_back.setOnClickListener { finish() }
    }

    override fun onClick(v: View?) {
        val intent = Intent(this@MinePrivacyActivity, MineFileActivity::class.java)
        intent.putExtra("key",v?.id.toString())
        startActivity(intent)
    }
    fun gotoAppDetailIntent(activity: Activity) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.parse("package:" + activity.packageName)
        activity.startActivity(intent)
    }

}