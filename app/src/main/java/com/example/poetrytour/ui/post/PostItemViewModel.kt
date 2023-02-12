package com.example.poetrytour.ui.post

import android.util.Log
import androidx.lifecycle.*
import com.example.poetrytour.network.PostNet
import kotlinx.coroutines.Dispatchers

class PostItemViewModel :ViewModel(){


    fun getPostItemLiveData(): LiveData<Long> {
        return postItemLiveData
    }

    fun setPostItemLiveData(id:Long){
        postItemLiveData.value=id
    }

    private var postItemLiveData = MutableLiveData<Long>()



    val postItemListLiveData=Transformations.switchMap(postItemLiveData){
        getPostItemList()
    }

    private fun getPostItemList():LiveData<List<PostItem>>{
        val lists= liveData<List<PostItem>> (Dispatchers.IO){
            var rs=try {
                PostNet.getPostItemList()
            }catch (e: Exception){
                e.printStackTrace()
                Log.d("getPostItemList","failure_exception")
                listOf<PostItem>()
            }
            emit(rs)
        }
        return lists
    }
}