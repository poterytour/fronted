package com.example.poetrytour.ui.post

import android.util.Log
import androidx.lifecycle.*
import com.example.poetrytour.model.Comment
import com.example.poetrytour.model.Post
import com.example.poetrytour.model.User
import com.example.poetrytour.network.CommentNet
import com.example.poetrytour.network.PostNet
import com.example.poetrytour.network.UserNet
import kotlinx.coroutines.Dispatchers
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PostViewModel:ViewModel() {


    fun setPostIdLiveData(postId :Long){
        postIdLiveData.value=postId
    }

    fun setPublisherIdLiveDate(publisherId: Long){
        publisherIdLiveData.value=publisherId
    }

    fun setPostComLiveData(comment: Comment) {
        postComLiveData.value = comment
    }

    fun setPostComListLiveData(flag :Long){
        postComListLiveData.value=flag
    }

    private var postIdLiveData = MutableLiveData<Long>()

    private var publisherIdLiveData = MutableLiveData<Long>()

    private var postComLiveData=MutableLiveData<Comment>()

    private var postComListLiveData=MutableLiveData<Long>()

    val postAddCom=Transformations.switchMap(postComLiveData){
        addCom(it)
    }

    val postDetail=Transformations.switchMap(postIdLiveData){
        getPost(it)
    }

    val comList=Transformations.switchMap(postComListLiveData){
        postIdLiveData.value?.let { it1 -> getComList(it1) }
    }

    val publisher=Transformations.switchMap(publisherIdLiveData){
        getPublisher(it)
    }

    private fun getPublisher(publisherId: Long):LiveData<User>{
        val publisher= liveData<User>(Dispatchers.IO){
            val rs=try {
                UserNet.getUserById(publisherId)
            }catch (e:Exception){
                e.printStackTrace()
                Log.d("getPublisher","failure_exception")
                User()
            }
            emit(rs)
        }
        return publisher
    }

    private fun getPost(postId: Long):LiveData<Post>{
        val post= liveData<Post>(Dispatchers.IO) {
            val rs=try {
                PostNet.getPostById(postId)
            }catch (e: Exception){
                e.printStackTrace()
                Log.d("getPost","failure_exception")
                Post()
            }
            emit(rs)
        }
        return post
    }


    private fun getComList(postId: Long):LiveData<List<Comment>>{
        val list= liveData<List<Comment>> (Dispatchers.IO){
            val rs=try {
                CommentNet.getAllComments(postId)
            }catch (e:Exception){
                e.printStackTrace()
                Log.d("getComList","failure_exception")
                listOf<Comment>()
            }
            emit(rs)
        }
        return list
    }


    private fun addCom(comment: Comment):LiveData<Int>{
        val rs= liveData<Int>(Dispatchers.IO) {
            val flag = CommentNet.addComment(
                comment.com_context!!, 0, comment.user_id!!,
                comment.post_id!!
            )
            Log.d("PostViewModel","添加")
            emit(flag)
        }
        return rs
    }

}