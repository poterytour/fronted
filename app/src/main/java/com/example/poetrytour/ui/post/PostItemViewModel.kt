package com.example.poetrytour.ui.post

import android.util.Log
import androidx.lifecycle.*
import com.example.poetrytour.network.PostNet
import kotlinx.coroutines.Dispatchers

class PostItemViewModel :ViewModel(){

    companion object{
        fun getPostItemLiveData():LiveData<Int>{
            return postItemLiveData
        }

        fun setPostItemLiveData(page:Int){
            postItemLiveData.value=page
        }

        private var postItemLiveData = MutableLiveData<Int>()

    }

    val postItemListLiveData=Transformations.switchMap(postItemLiveData){
        getPostItemList(it)
    }

    private fun getPostItemList(page: Int):LiveData<List<PostItem>>{
        val lists= liveData<List<PostItem>> (Dispatchers.IO){
            var rs=try {
                PostNet.getPostItemList(page)
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