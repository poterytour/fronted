package com.example.poetrytour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.poetrytour.tool.BottomNavigation
import com.example.poetrytour.ui.fragments.FragmentAdapter
import com.example.poetrytour.ui.fragments.MessageFragment
import com.example.poetrytour.ui.fragments.MineFragment
import com.example.poetrytour.ui.fragments.PostFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BottomNavigation.initialize(R.id.travel,view_pager,bottom_navigation)
        val listOfFragment = listOf<Fragment>(PostFragment(), MessageFragment(),MineFragment())
        val viewpager2Adapter = FragmentAdapter(this,listOfFragment)
        view_pager.adapter=viewpager2Adapter
//        view_pager.setUserInputEnabled(false);
        view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
               bottom_navigation.selectedItemId = when(position){
                    0 -> R.id.travel
                    1 -> R.id.message
                    2 -> R.id.mine
                   else -> R.id.travel
               }
                super.onPageSelected(position)
            }
        })
    }
}