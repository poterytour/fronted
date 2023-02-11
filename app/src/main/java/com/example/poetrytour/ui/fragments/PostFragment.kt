package com.example.poetrytour.ui.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.ListFragment
import com.example.poetrytour.R

class PostFragment: ListFragment() {
    private val quanziList = ArrayList<Quanzi>()
//    private var listView: ListView? =null
    companion object{
        @SuppressLint("StaticFieldLeak")
        var text: TextView? = null
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        var view = inflater.inflate(R.layout.activity_posts, container, false)
//        setContentView(R.layout.activity_posts)
        initQuanzi()
//        listView = view.findViewById(R.id.listview)
//        val adapter =QuanziAdapter(this.activity,R.layout.item,quanziList)
//        listView?.adapter = adapter
        return inflater.inflate(R.layout.activity_posts, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter =QuanziAdapter(this.activity,R.layout.item,quanziList)
        this.listAdapter = adapter
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {

// TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState)
    }

    class Quanzi(val text1:String,val text2:String,val text3:String,val text4:String,val text5:String,val text6:String,val text7:String,val text8:String,val text9:String,val text10:String,val img:Int,val img1:Int,val img2:Int,val img3:Int,val img4:Int,val img5:Int,val img6:Int)
    class QuanziAdapter(activity: Activity?, val resourceId:Int, val data: List<Quanzi>):
        ArrayAdapter<Quanzi>(activity!!,resourceId,data),View.OnClickListener{
        override fun getCount(): Int {
            return data.size
        }

        override fun getItem(position: Int): Quanzi {
            return data[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
        @SuppressLint("ViewHolder", "CutPasteId")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = LayoutInflater.from(context).inflate(resourceId,parent,false)
            val text1: TextView = view.findViewById(R.id.text1)
            val text2: TextView = view.findViewById(R.id.text2)
            val text3: TextView = view.findViewById(R.id.text3)
            val text4: TextView = view.findViewById(R.id.text4)
            val text5: TextView = view.findViewById(R.id.text5)
            val text6: TextView = view.findViewById(R.id.text6)
            val text7: TextView = view.findViewById(R.id.text7)
            val text8: TextView = view.findViewById(R.id.text8)
            val text9: TextView = view.findViewById(R.id.text9)
            val text10: TextView = view.findViewById(R.id.text10)
            val img: ImageView = view.findViewById(R.id.ip)
            val img1: ImageView = view.findViewById(R.id.img_1)
            val img2: ImageView = view.findViewById(R.id.img_2)
            val img3: ImageView = view.findViewById(R.id.img_3)
            val img4: ImageView = view.findViewById(R.id.img_4)
            val img5: ImageView = view.findViewById(R.id.img_5)
            val img6: ImageView = view.findViewById(R.id.img_6)
            val tp1: TextPaint = text1.paint
            val tp2: TextPaint = text6.paint
            val quanzi =getItem(position)
            text = view.findViewById(R.id.text9)
            if(quanzi!=null){
                text1.text = quanzi.text1
                text2.text = quanzi.text2
                text3.text = quanzi.text3
                text4.text = quanzi.text4
                text5.text = quanzi.text5
                text6.text = quanzi.text6
                text7.text = quanzi.text7
                text8.text = quanzi.text8
                text9.text = quanzi.text9
                text10.text = quanzi.text10
                if(quanzi.text3.contains("♀")){
                    text3.setBackgroundResource(R.drawable.w_shape)
                }else{
                    text3.setBackgroundResource(R.drawable.shape)
                }
                text4.setBackgroundResource(R.drawable.l_shape)
                img.setImageResource(quanzi.img)
                img1.setImageResource(quanzi.img1)
                img2.setImageResource(quanzi.img2)
                img3.setImageResource(quanzi.img3)
                img4.setImageResource(quanzi.img4)
                img5.setImageResource(quanzi.img5)
                img5.tag = quanzi.img5.toString()+" "+position.toString()
                img6.setImageResource(quanzi.img6)
                tp1.isFakeBoldText = true
                tp2.isFakeBoldText = true

//                img3.setImageBitmap(DrawableUtils.SetRoundCornerBitmap( DrawableUtils.DrawableToBitmap(getResources().getDrawable( R.drawable.img_15)), 60))
            }
            img5.setOnClickListener(this)
            return view
        }
        @SuppressLint("SetTextI18n")
        override fun onClick(v: View?) {
            //点击事件，但是由于需要进行对listview的动态更新才能实现对点击事件的反复使用，使用需要后面通过数据库一起进行使用，后面还会在做修改
            when(v?.id){
                //处理这个还有几个图片以及文字都需要点击事件
                R.id.img_5-> {
                    //点点小红心，同时次数加一或者减一
                    val imgs: ImageView = v.findViewById(v.id)
                    val mes = v?.tag.toString().split(" ")
                    if(mes[0]==R.drawable.img_11.toString()){
                        imgs.setImageResource(R.drawable.img_10)
                        Companion.text?.text=(text?.text.toString().toInt()+1).toString()
                        v.tag=R.drawable.img_10
                    }else{
                        imgs.setImageResource(R.drawable.img_11)
                        Companion.text?.text=(text?.text.toString().toInt()-1).toString()
                        v.tag=R.drawable.img_11
                    }
                }
                //建好数据库之后，需要利用数据库才能动态修改，点击事件再利用v.id和tag中的position对数据库对应数据进行修改，再利用adapter.notifyDataSetChange();更新，
                //对于数据库，到时候就将数据通过initQuanzi（）将数据库的数据传进列表中
            }
        }
//        companion object{
//            class ViewHolder{
//                val textView:TextView ?= null
//                val imageView:ImageView ?=null
//            }
//        }
    }
    private fun initQuanzi(){
        //临时数据，后面使用数据库进行操作
        repeat(3){
            quanziList.add(Quanzi("官方活动","杭州市","♀27","Lv.5","15分钟前","杭州橙色跑•相约钱塘江","橙色跑是由CRISN官方举办的一项橙色趣味跑步活动","178","293","79",R.drawable.img_14,R.drawable.img_12,R.drawable.img_13,R.drawable.img_15,R.drawable.img_3,R.drawable.img_11,R.drawable.img_7))
            quanziList.add(Quanzi("官方活动","杭州市","♂27","Lv.5","15分钟前","杭州橙色跑•相约钱塘江","橙色跑是由CRISN官方举办的一项橙色趣味跑步活动","178","293","79",R.drawable.img_14,R.drawable.img_12,R.drawable.img_13,R.drawable.img_15,R.drawable.img_3,R.drawable.img_10,R.drawable.img_7))
        }
    }
}