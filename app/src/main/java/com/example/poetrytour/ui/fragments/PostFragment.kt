package com.example.poetrytour.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.poetrytour.R
import com.example.poetrytour.tool.ContextTool

import com.example.poetrytour.ui.post.PostItem
import com.example.poetrytour.ui.post.PostItemAdapater

import com.example.poetrytour.ui.post.PostItemViewModel
import kotlinx.android.synthetic.main.activity_posts.*
import java.util.concurrent.CopyOnWriteArrayList

class PostFragment: Fragment()   {

    private val TAG="PostFragment"

    private var lists:MutableList<PostItem> = CopyOnWriteArrayList()

    private var adapter: PostItemAdapater? = null

    val viewModel by lazy { ViewModelProvider(this).get(PostItemViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        var view = inflater.inflate(R.layout.activity_posts, container, false)

        var listView:ListView=view.findViewById(R.id.post_item_list)

        viewModel.setPostItemLiveData(System.currentTimeMillis())

        activity?.let {
            viewModel.postItemListLiveData.observe(it){
                for(item in it){
                    lists.add(item)
                }
                adapter=PostItemAdapater(ContextTool.getContext(),lists)
                post_item_list.adapter=adapter
            }
        }

        return view
    }


}