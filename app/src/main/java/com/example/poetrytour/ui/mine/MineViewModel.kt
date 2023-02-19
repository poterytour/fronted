package com.example.poetrytour.ui.mine

import androidx.lifecycle.*
import com.example.poetrytour.model.User
import com.example.poetrytour.network.UserNet
import kotlinx.coroutines.Dispatchers

class MineViewModel:ViewModel() {

    fun setUserIdLiveData(userId: Long){
        userIdLiveData.value=userId
    }

    private var userIdLiveData=MutableLiveData<Long>()

    val userLiveData=Transformations.switchMap(userIdLiveData){
        getUser(it)
    }

    private fun getUser(userId :Long):LiveData<User>{
        val user= liveData<User>(Dispatchers.IO){
            val user=UserNet.getUserById(userId)
            emit(user)
        }
        return user
    }
}