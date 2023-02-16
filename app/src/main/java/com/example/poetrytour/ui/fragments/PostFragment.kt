package com.example.poetrytour.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.poetrytour.R
import com.example.poetrytour.tool.ContextTool
import com.example.poetrytour.ui.post.PostActivity


import com.example.poetrytour.ui.post.PostItem
import com.example.poetrytour.ui.post.PostItemAdapater

import com.example.poetrytour.ui.post.PostItemViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.CopyOnWriteArrayList

class PostFragment: Fragment()   {

    private val TAG="PostFragment"

    private var lists:MutableList<PostItem> = CopyOnWriteArrayList()

    private var adapter: PostItemAdapater? = null


    private val viewModel by lazy { ViewModelProvider(this).get(PostItemViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        var view = inflater.inflate(R.layout.activity_posts, container, false)

        var listView:ListView=view.findViewById(R.id.post_item_list)

        PostItemViewModel.setPostItemLiveData(1)

        EventBus.getDefault().register(this)

        activity?.let {
            viewModel.postItemListLiveData.observe(it){
                for(item in it){
                    lists.add(item)
                }
                if(PostItemViewModel.getPostItemLiveData().value==1) {
                    adapter = PostItemAdapater(ContextTool.getContext(), lists)
                    listView.adapter = adapter
                }else{
                    adapter?.notifyDataSetChanged()
                }
            }
        }

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val post_id= adapter!!.getItemId(position)
                var intent=Intent(context,PostActivity::class.java)
                intent.putExtra("post_id",post_id.toString())
                startActivity(intent)
            }

        var myScrollListener=MyScrollListener(listView)
        listView.setOnScrollListener(myScrollListener)

        return view
    }

    class MyScrollListener(listView: ListView):AbsListView.OnScrollListener{
        val listView=listView
        override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
            val lastVisibleIndex=listView.lastVisiblePosition
            if(scrollState==AbsListView.OnScrollListener.SCROLL_STATE_IDLE &&
                    lastVisibleIndex==listView.adapter.count-1){
                val num=(listView.adapter.count/10)+1
                PostItemViewModel.setPostItemLiveData(num)
            }
        }

        override fun onScroll(
            view: AbsListView?,
            firstVisibleItem: Int,
            visibleItemCount: Int,
            totalItemCount: Int
        ) {

        }

    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    fun updateLove(data:UpdateLove){
        val date=data
        for(item in lists){
            if(item.post_id==date.id){
                item.post_love=data.num
                adapter?.notifyDataSetChanged()
                break
            }
        }
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    fun updateReading(data:UpdateReading){
        val date=data
        for(item in lists){
            if(item.post_id==date.id){
                item.post_reading=data.num
                adapter?.notifyDataSetChanged()
                break
            }
        }
    }


    class UpdateLove(id:Long,num:Int){
        val id= id
        var num=num
    }

    class UpdateReading(id:Long,num:Int){
        val id= id
        var num=num
    }



}