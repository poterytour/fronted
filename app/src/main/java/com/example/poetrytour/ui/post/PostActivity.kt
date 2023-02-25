package com.example.poetrytour.ui.post

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.example.poetrytour.ui.message.MineBasicActivity
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
    var list=ArrayList<String>()


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
            viewModel.setUserIdLiveData(System.currentTimeMillis())
        }

        viewModel.postDetail.observe(this){
            post=it
            it.publisher_id?.let { it1 -> viewModel.setPublisherIdLiveDate(it1) }
            init()
        }

        viewModel.publisher.observe(this){
            val user=it
            post_publisher_name.setText(it.user_name)
            Glide.with(ContextTool.getContext())
                .load(it.avatar)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(post_publisher_img)
            
            post_publisher_img.setOnClickListener {
                if(user.user_id!=User.user_id){
                    val intent=Intent(this,MineBasicActivity::class.java)
                    intent.putExtra("user_id",user.user_id.toString())
                    startActivity(intent)
                 
                }
            }
        }

        viewModel.postLoveList.observe(this){
            var flag=0
            for(i in it){
                if (post_id == i){
                    post_love.setImageResource(R.drawable.img_loved)
                    post_love.tag=R.drawable.img_loved
                    flag=1
                    break
                }
            }
            if(flag==0) {
                post_love.setImageResource(R.drawable.img_love)
                post_love.tag = R.drawable.img_love
            }

        }

        viewModel.postCollectList.observe(this){
            var flag=0
            for(i in it){
                if (post_id == i){
                    post_collect.setImageResource(R.drawable.img_collected)
                    post_collect.tag=R.drawable.img_collected
                    flag=1
                    break
                }
            }
            if(flag==0) {
                post_collect.setImageResource(R.drawable.img_collect)
                post_collect.tag = R.drawable.img_collect
            }
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
            Log.d(TAG,"${post_love.tag}")

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
            Log.d(TAG,"${post_collect.tag}")
            if(post_collect.tag.toString().equals("res/drawable/img_collect.png")
                || post_collect.tag.toString().equals(R.drawable.img_collect.toString())  ){
                post_collect.setImageResource(R.drawable.img_collected)
                post_collect.tag=R.drawable.img_collected
                viewModel.setPostCollectPlusLiveData(1)

            }else{
                post_collect.setImageResource(R.drawable.img_collect)
                post_collect.tag=R.drawable.img_collect
                viewModel.setPostCollectPlusLiveData(-1)
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
        
        viewModel.postImgsLiveData.observe(this){
            list.clear()
            list= it as ArrayList<String>
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

    fun init(){
        post_info.setText(post?.post_title)
        var text=post?.post_context
        if (text != null) {
            text=text.replace("\n","\n\t\t")
            text="\t\t$text"
        }
        post_context.setText(text)

        viewModel.setUserIdLiveData(System.currentTimeMillis())

        val publisher_time= post?.post_time?.let { TimeTool.transToString(it) }
            ?.let { TimeTool.getShortByString(it) }
        post_publisher_time.setText("发布于"+publisher_time)


    }


}