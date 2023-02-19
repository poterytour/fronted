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

    fun setPostLovePlusLiveData(plus:Int){
        postLovePlusLiveData.value=plus
    }

    fun setPostCollectPlusLiveData(plus: Int){
        postCollectPlusLiveData.value=plus
    }

    fun setPostReadingPlusLiveData(plus: Int){
        postReadingPlusLiveData.value=plus
    }

    fun setUserIdLiveData(time: Long){
        userIdLiveData.value=time
    }

    private var postIdLiveData = MutableLiveData<Long>()

    private var publisherIdLiveData = MutableLiveData<Long>()

    private var postComLiveData=MutableLiveData<Comment>()

    private var postComListLiveData=MutableLiveData<Long>()

    private var postLovePlusLiveData=MutableLiveData<Int>()

    private var postCollectPlusLiveData=MutableLiveData<Int>()

    private var postReadingPlusLiveData=MutableLiveData<Int>()

    private var userIdLiveData=MutableLiveData<Long>()

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

    val updatePostLove=Transformations.switchMap(postLovePlusLiveData){
        updatePostLove(it)
    }

    val updatePostCollect=Transformations.switchMap(postCollectPlusLiveData){
        updatePostCollect(it)
    }

    val updatePostReading=Transformations.switchMap(postReadingPlusLiveData){
        updatePostReading(it)
    }

    val postLoveList=Transformations.switchMap(userIdLiveData){
        getPostLoveList(com.example.poetrytour.ui.User.user_id!!)
    }

    val postCollectList=Transformations.switchMap(userIdLiveData){
        getPostCollectList(com.example.poetrytour.ui.User.user_id!!)
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

    private fun updatePostLove(plus:Int):LiveData<Int>{
        val rs= liveData<Int>(Dispatchers.IO){
            val love=PostNet.updatePostLove(postIdLiveData.value!!,plus)
            var flag=0
            val list=PostNet.getPostLoveList(com.example.poetrytour.ui.User.user_id!!)
            for(i in list){
                if(postIdLiveData.value ==i){
                    flag =1
                    break
                }
            }
            if(flag==1){
                PostNet.deletePostLove(com.example.poetrytour.ui.User.user_id!!, postIdLiveData.value!!)
            }else{
                PostNet.addPostLove(com.example.poetrytour.ui.User.user_id!!, postIdLiveData.value!!)
            }
            emit(love)
        }
        return rs
    }

    private fun updatePostCollect(plus:Int):LiveData<Int>{
        val rs= liveData<Int>(Dispatchers.IO){
            val collect=PostNet.updatePostCollect(postIdLiveData.value!!,plus)
            var flag=0

            val list=PostNet.getPostCollectList(com.example.poetrytour.ui.User.user_id!!)
            for(i in list){
                if(postIdLiveData.value ==i){
                    flag =1
                    break
                }
            }
            if(flag==1){
                PostNet.deletePostCollect(com.example.poetrytour.ui.User.user_id!!, postIdLiveData.value!!)
            }else{
                PostNet.addPostCollect(com.example.poetrytour.ui.User.user_id!!, postIdLiveData.value!!)
            }

            emit(collect)
        }
        return rs
    }

    private fun updatePostReading(plus:Int):LiveData<Int>{
        val rs= liveData<Int>(Dispatchers.IO){
            val collect=PostNet.updatePostReading(postIdLiveData.value!!,plus)
            emit(collect)
        }
        return rs
    }

    private fun getPostLoveList(userId:Long):LiveData<List<Long>>{
        val postIds= liveData<List<Long>>(Dispatchers.IO){
            val rs=PostNet.getPostLoveList(userId)
            emit(rs)
        }
        return postIds
    }

    private fun getPostCollectList(userId:Long):LiveData<List<Long>>{
        val postIds= liveData<List<Long>>(Dispatchers.IO){
            val rs=PostNet.getPostCollectList(userId)
            emit(rs)
        }
        return postIds
    }

}