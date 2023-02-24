package com.example.poetrytour.ui.mine

import androidx.lifecycle.*
import com.alibaba.fastjson.JSON
import com.example.poetrytour.model.User
import com.example.poetrytour.network.PostNet
import com.example.poetrytour.network.UserNet
import com.example.poetrytour.ui.post.PostItem
import kotlinx.coroutines.Dispatchers

class MineViewModel:ViewModel() {

    fun setUserIdLiveData(userId: Long){
        userIdLiveData.value=userId
    }
    
    fun setUpdateUserLiveData(user: User){
        updateUserLiveData.value=user
    }
    
    private var userIdLiveData=MutableLiveData<Long>()
    
    private var updateUserLiveData=MutableLiveData<User>()

    val userLiveData=Transformations.switchMap(userIdLiveData){
        getUser(it)
    }
    
    val updateLiveData=Transformations.switchMap(updateUserLiveData){
        updateUser(it)
    }

    val lovedPostItemLiveData=Transformations.switchMap(userIdLiveData){
        getLovedPostItem(it)
    }

    val collectPostItemLiveData=Transformations.switchMap(userIdLiveData){
        getCollectPostItem(it)
    }
    
    val minePostItemLiveData=Transformations.switchMap(userIdLiveData){
        getPostByPublisher(it)
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
    
    private fun updateUser(user: User):LiveData<User>{
        val user = liveData<User>(Dispatchers.IO){
            val userStr=JSON.toJSONString(user)
            val rs=UserNet.updateUser(userStr)
            emit(rs)
        }
        return user
    }
    
    private fun getPostByPublisher(userId: Long):LiveData<List<PostItem>>{
        val rs= liveData<List<PostItem>>(Dispatchers.IO){
            val rs=try {
                PostNet.getPostByPublisher(userId)
            }catch (e:Exception){
                e.printStackTrace()
                listOf<PostItem>()
            }
            emit(rs)
        }
        return rs
    }
    
    
}