package com.example.poetrytour.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.poetrytour.R
import com.example.poetrytour.tool.ContextTool
import com.example.poetrytour.ui.User
import com.example.poetrytour.ui.mine.*
import kotlinx.android.synthetic.main.activity_mine.*

class MineFragment: Fragment(), View.OnClickListener {



    val viewModel by lazy { ViewModelProvider(this).get(MineViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.activity_mine, container, false)
        User.user_id?.let { viewModel.setUserIdLiveData(it) }

        activity?.let {
            viewModel.userLiveData.observe(it){
                mine_name.setText(it.user_name)
                mine_nicheng.setText(it.intro)

                Glide.with(ContextTool.getContext())
                    .load(it.avatar)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(mine_tx)
            }
        }

        var linearLayout1 = view.findViewById<LinearLayout>(R.id.mine_material)!!
        var linearLayout2 = view.findViewById<LinearLayout>(R.id.mine_collection)!!
        var linearLayout3 = view.findViewById<LinearLayout>(R.id.mine_text)!!
        var linearLayout4 = view.findViewById<LinearLayout>(R.id.mine_error)!!
        var linearLayout5 = view.findViewById<LinearLayout>(R.id.mine_privacy)!!
        var linearLayout6 = view.findViewById<LinearLayout>(R.id.mine_setting)!!
        var linearLayout7 = view.findViewById<LinearLayout>(R.id.mine_love)!!
        linearLayout1.setOnClickListener(this)
        linearLayout2.setOnClickListener(this)
        linearLayout3.setOnClickListener(this)
        linearLayout4.setOnClickListener(this)
        linearLayout5.setOnClickListener(this)
        linearLayout6.setOnClickListener(this)
        linearLayout7.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.mine_material->{
                startActivity(Intent(this.activity, MineMaterialActivity::class.java))
            }
            R.id.mine_collection->{
                val intent=Intent(this.activity,MineCollectActivity::class.java)
                intent.putExtra("user_id",User.user_id.toString())
                startActivity(intent)
            }
            R.id.mine_love->{
                val intent=Intent(this.activity, MineLovedActivity::class.java)
                intent.putExtra("user_id",User.user_id.toString())
                startActivity(intent)
            }
            R.id.mine_text->{
                val intent=Intent(this.activity, MinePublisherPostActivity::class.java)
                intent.putExtra("user_id",User.user_id.toString())
                startActivity(intent)
            }
            R.id.mine_error->{
                startActivity(Intent(this.activity,MineFeedbackActivity::class.java))
            }
            R.id.mine_privacy->{
                startActivity(Intent(this.activity,MinePrivacyActivity::class.java))
            }
            R.id.mine_setting->{
                startActivity(Intent(this.activity,MineSettingActivity::class.java))
            }
        }
    }
}