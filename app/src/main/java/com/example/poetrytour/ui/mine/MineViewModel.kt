package com.example.poetrytour.ui.mine

import androidx.lifecycle.*
import com.alibaba.fastjson.JSON
import com.example.poetrytour.model.Result
import com.example.poetrytour.model.User
import com.example.poetrytour.network.PostNet
import com.example.poetrytour.network.UserNet
import com.example.poetrytour.ui.post.PostItem
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URLEncoder


class MineViewModel:ViewModel() {

    fun setUserIdLiveData(userId: Long){
        userIdLiveData.value=userId
    }
    
    fun setUpdateUserLiveData(user: User){
        updateUserLiveData.value=user
    }
    
    fun setImgPathLiveData(path:String){
        imgPathLiveData.value=path
    }
    
    private var userIdLiveData=MutableLiveData<Long>()
    
    private var updateUserLiveData=MutableLiveData<User>()
    
    private var imgPathLiveData=MutableLiveData<String>()

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
    
    val imgUrlLiveData=Transformations.switchMap(imgPathLiveData){
        uploadImg(it)
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
    
    
    private fun uploadImg(path:String):LiveData<Result<String>>{
        val rs= liveData<Result<String>>(Dispatchers.IO){
            val file=File(path)

            val imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val imageBodyPart = MultipartBody.Part.createFormData("file", URLEncoder.encode(file.getName(),"UTF-8"), imageBody)
            val rs=UserNet.uploadImg(imageBodyPart)
            emit(rs)
        }
        return rs
    }
    
    
}