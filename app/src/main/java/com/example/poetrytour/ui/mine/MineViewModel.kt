package com.example.poetrytour.ui.mine

import androidx.lifecycle.*
import com.example.poetrytour.model.User
import com.example.poetrytour.network.PostNet
import com.example.poetrytour.network.UserNet
import com.example.poetrytour.ui.post.PostItem
import kotlinx.coroutines.Dispatchers

class MineViewModel:ViewModel() {

    fun setUserIdLiveData(userId: Long){
        userIdLiveData.value=userId
    }

    private var userIdLiveData=MutableLiveData<Long>()

    val userLiveData=Transformations.switchMap(userIdLiveData){
        getUser(it)
    }

    val lovedPostItemLiveData=Transformations.switchMap(userIdLiveData){
        getLovedPostItem(it)
    }

    val collectPostItemLiveData=Transformations.switchMap(userIdLiveData){
        getCollectPostItem(it)
    }

    private fun getUser(userId :Long):LiveData<User>{
        val user= liveData<User>(Dispatchers.IO){
            val user=UserNet.getUserById(userId)
            emit(user)
        }
        return user
    }

    private fun getLovedPostItem(userId: Long):LiveData<List<PostItem>>{
        val rs= liveData<List<PostItem>>(Dispatchers.IO){
            val rs=try {
                PostNet.getLovedPostItem(userId)
            }catch (e:Exception){
                e.printStackTrace()
                listOf<PostItem>()
            }
            emit(rs)
        }
        return rs
    }


    private fun getCollectPostItem(userId: Long):LiveData<List<PostItem>>{
        val rs= liveData<List<PostItem>>(Dispatchers.IO){
            val rs=try {
                PostNet.getCollectPostItem(userId)
            }catch (e:Exception){
                e.printStackTrace()
                listOf<PostItem>()
            }
            emit(rs)
        }
        return rs
    }

}