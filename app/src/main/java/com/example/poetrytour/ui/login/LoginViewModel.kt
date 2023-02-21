package com.example.poetrytour.ui.login

import androidx.lifecycle.*
import com.example.poetrytour.model.Result
import com.example.poetrytour.model.User
import com.example.poetrytour.network.UserNet
import kotlinx.coroutines.Dispatchers

class LoginViewModel:ViewModel() {

    fun getLoginStrLiveData():LiveData<String>{
        return loginStrLiveData
    }

    fun setLoginStrLiveData(string: String){
        loginStrLiveData.value=string
    }

    fun setRegisterSteLiveData(string: String){
        registerStrLiveData.value=string
    }

    private var loginStrLiveData=MutableLiveData<String>()

    private var registerStrLiveData=MutableLiveData<String>()

    val loginResultLiveData=Transformations.switchMap(loginStrLiveData){
        login(it)
    }

    val registerResultLiveData=Transformations.switchMap(registerStrLiveData){
        register(it)
    }

    private fun login(str:String):LiveData<Result<User>>{
        val rs= liveData<Result<User>>(Dispatchers.IO){
            val tel=str.split("-")[0]
            val password=str.split("-")[1]
            val rs=UserNet.userLogin(tel, password)
            emit(rs)
        }
        return rs
    }

    private fun register(str:String):LiveData<Result<User>>{
        val rs= liveData<Result<User>>(Dispatchers.IO){
            val tel=str.split("-")[0]
            val password=str.split("-")[1]
            val rs=UserNet.userRegister(tel, password)
            emit(rs)
        }
        return rs
    }
}