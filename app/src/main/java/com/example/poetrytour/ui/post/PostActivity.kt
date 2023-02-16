package com.example.poetrytour.ui.post

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.poetrytour.R
import com.example.poetrytour.model.Comment
import com.example.poetrytour.model.Post
import com.example.poetrytour.tool.ContextTool
import com.example.poetrytour.tool.TimeTool
import com.example.poetrytour.ui.User
import com.example.poetrytour.ui.fragments.PostFragment
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.activity_msg.*
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.post_item.*
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.CopyOnWriteArrayList

class PostActivity : AppCompatActivity() {

    private val TAG="PostActivity"

    private var post_id:Long?=null
    private var post:Post?=null
    private var com_lists:List<Comment>?=CopyOnWriteArrayList<Comment>()
    private var adapter:PostComAdapter?=null


    val viewModel by lazy { ViewModelProvider(this).get(PostViewModel::class.java) }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        setSupportActionBar(post_toolbar)
        post_toolbar.setNavigationOnClickListener{
            finish()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(" ")
        //recycleview
        val layoutManager = LinearLayoutManager(this)



        val intent=getIntent()
        post_id= intent.getStringExtra("post_id")?.toLong()
        post_id?.let {
            viewModel.setPostIdLiveData(it)
            viewModel.setPostComListLiveData(System.currentTimeMillis())
            viewModel.setPostReadingPlusLiveData(1)
        }

        viewModel.postDetail.observe(this){
            post=it
            it.publisher_id?.let { it1 -> viewModel.setPublisherIdLiveDate(it1) }
            init()
        }

        viewModel.publisher.observe(this){
            post_publisher_name.setText(it.user_name)
            Glide.with(ContextTool.getContext())
                .load(it.avatar)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(post_publisher_img)
        }

        //关注按钮
//        var guanzhu:Button=findViewById(R.id.post_publisher_guanzhu_button)
//        guanzhu.setOnClickListener {
//            if(guanzhu.text.toString().equals("关注")){
//                guanzhu.setBackgroundResource(R.drawable.yiguanzhu_button)
//                guanzhu.setTextColor(R.color.grey)
//                guanzhu.setText("已关注")
//            }else{
//                guanzhu.setBackgroundResource(R.drawable.guanzhu_button)
//                guanzhu.setTextColor(R.color.red)
//                guanzhu.setText(Html.fromHtml("<font color='#da4141'>关注"))
//            }
//        }

        //点赞按钮
        post_love.setOnClickListener{
            if(post_love.tag.toString().equals("res/drawable/img_love.png")
                        || post_love.tag.toString().equals(R.drawable.img_love.toString())){
                post_love.setImageResource(R.drawable.img_loved)
                post_love.tag=R.drawable.img_loved
                viewModel.setPostLovePlusLiveData(1)
            }else{
                post_love.setImageResource(R.drawable.img_love)
                post_love.tag=R.drawable.img_love
                viewModel.setPostLovePlusLiveData(-1)
            }
        }

        viewModel.updatePostLove.observe(this){
            EventBus.getDefault().post(PostFragment.UpdateLove(post_id!!,it))
        }
        viewModel.updatePostCollect.observe(this){

        }

        viewModel.updatePostReading.observe(this){
            EventBus.getDefault().post(PostFragment.UpdateReading(post_id!!,it))
        }

        //收藏按钮
        post_collect.setOnClickListener {
            if(post_collect.tag.toString().equals("res/drawable/img_collect.png")
                || post_collect.tag.toString().equals(R.drawable.img_collect.toString())  ){
                post_collect.setImageResource(R.drawable.img_collected)
                post_collect.tag=R.drawable.img_collected
                viewModel.setPostCollectPlusLiveData(1)

            }else{
                post_collect.setImageResource(R.drawable.img_collect)
                post_collect.tag=R.drawable.img_collect
                viewModel.setPostCollectPlusLiveData(1)
            }
        }

        viewModel.comList.observe(this){
            com_lists=it
            val lists=com_lists
            Log.d(TAG,lists.toString())
            adapter= lists?.let { it1 -> PostComAdapter(it1) }
            post_com_list.layoutManager = layoutManager
            post_com_list.adapter=adapter
            post_com_area.bringToFront()

            if(it.size==0){
                post_com_tip.setText("暂时还没有评论哦...")
            }else{
                post_com_tip.setText("")
            }
        }

        viewModel.postAddCom.observe(this){
            viewModel.setPostComListLiveData(System.currentTimeMillis())
        }

        //发送评论按钮
        post_com_send.setOnClickListener {
            val context=post_com_inputText.text.toString()
            if(context.isNotEmpty()){
                var comment=Comment()
                comment.com_context=context
                comment.post_id=post_id
                comment.user_id=User.user_id
                comment.parent_com_id=0
                viewModel.setPostComLiveData(comment)
            }

            post_com_inputText.setText("")
        }

    }

    fun init(){
        post_info.setText(post?.post_title)
        var text=post?.post_context
        if (text != null) {
            text=text.replace("\n","\n\t\t")
            text="\t\t$text"
        }
        post_context.setText(text)

        val publisher_time= post?.post_time?.let { TimeTool.transToString(it) }
            ?.let { TimeTool.getShortByString(it) }
        post_publisher_time.setText("发布于"+publisher_time)

        var list=ArrayList<String>()
        list.add("https://pic5.40017.cn/03/000/65/be/rBANB1x9_PWAHAKrAAIa9PeeFYU026.jpg")
        list.add("https://ts1.cn.mm.bing.net/th/id/R-C.a1d32e7f408b780897c8df5704c0b8b2?rik=YplzbHRsO2BAbw&riu=http%3a%2f%2fy1.ifengimg.com%2f52e0de6343af0d30%2f2014%2f0314%2frdn_5322bf979fc0c.jpg&ehk=ZUcoG31%2b8Cu%2bv8rZv%2bkrSP4guHn%2bYrp27lDuzrjWxeY%3d&risl=&pid=ImgRaw&r=0")
        list.add("https://youimg1.c-ctrip.com/target/100n0l000000cykmc1908_D_10000_1200.jpg?proc=autoorient")
        list.add("https://ts1.cn.mm.bing.net/th/id/R-C.10e02a293ec5be65cff48af9de3381ab?rik=gw6vf6RTBCecTg&riu=http%3a%2f%2fpic3.40017.cn%2fscenery%2fdestination%2f2015%2f05%2f18%2f15%2fw8tEhO.jpg&ehk=HQeHFU3NNasg3rcScNUVjo0MiTFLUh4o1aIozmGDqM0%3d&risl=&pid=ImgRaw&r=0")
        list.add("https://pic5.40017.cn/03/000/65/be/rBANB1x9_PWAMPyYAAIbmnlyUXM786.jpg")
        var banner: Banner<String, BannerImageAdapter<String>> = findViewById<View>(R.id.post_img_banner) as Banner<String, BannerImageAdapter<String>>
        banner.setAdapter(object : BannerImageAdapter<String>(list) {
            override fun onBindView(holder: BannerImageHolder, data: String, position: Int, size: Int) {
                //图片加载自己实现
                Glide.with(holder.itemView)
                    .load(data)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                    .into(holder.imageView) }
        }).addBannerLifecycleObserver(this).setIndicator(CircleIndicator(this))
        banner.outlineProvider
    }


}