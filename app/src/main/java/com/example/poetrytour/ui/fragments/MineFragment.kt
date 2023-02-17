package com.example.poetrytour.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.poetrytour.MainActivity
import com.example.poetrytour.R
import com.example.poetrytour.ui.User
import com.example.poetrytour.ui.mine.MineMaterialActivity
import com.makeramen.roundedimageview.RoundedImageView

class MineFragment: Fragment(), View.OnClickListener {
    private lateinit var intent:Intent
    private lateinit var mine_name:TextView
    private lateinit var mine_nicheng:TextView
    private lateinit var mine_tx: RoundedImageView
    private lateinit var linearLayout1:LinearLayout
    private lateinit var linearLayout2:LinearLayout
    private lateinit var linearLayout3:LinearLayout
    private lateinit var linearLayout4:LinearLayout
    private lateinit var linearLayout5:LinearLayout
    private lateinit var linearLayout6:LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_mine, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        mine_name = activity?.findViewById(R.id.mine_name)!!
        mine_nicheng = activity?.findViewById(R.id.mine_nicheng)!!
        mine_tx = activity?.findViewById(R.id.mine_tx)!!
        User.user_name = "张天宇"
        mine_name.text = User.user_name
        mine_nicheng.text = User.user_name
        mine_tx.setImageResource(R.drawable.img_tx)
        linearLayout1 = activity?.findViewById(R.id.mine_material)!!
        linearLayout2 = activity?.findViewById(R.id.mine_collection)!!
        linearLayout3 = activity?.findViewById(R.id.mine_text)!!
        linearLayout4 = activity?.findViewById(R.id.mine_error)!!
        linearLayout5 = activity?.findViewById(R.id.mine_privacy)!!
        linearLayout6 = activity?.findViewById(R.id.mine_setting)!!
        linearLayout1.setOnClickListener(this)
        linearLayout2.setOnClickListener(this)
        linearLayout3.setOnClickListener(this)
        linearLayout4.setOnClickListener(this)
        linearLayout5.setOnClickListener(this)
        linearLayout6.setOnClickListener(this)
        super.onActivityCreated(savedInstanceState)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.mine_material->{
                startActivity(Intent(this.activity, MineMaterialActivity::class.java))
            }
            R.id.mine_collection->{

            }
            R.id.mine_text->{

            }
            R.id.mine_error->{

            }
            R.id.mine_privacy->{

            }
            R.id.mine_setting->{

            }
        }
    }
}
