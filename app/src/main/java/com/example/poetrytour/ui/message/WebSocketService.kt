package com.example.poetrytour.ui.message

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.alibaba.fastjson.JSON
import com.example.poetrytour.model.MessageData
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.java_websocket.client.WebSocketClient
import org.java_websocket.enums.ReadyState
import java.net.URISyntaxException

class WebSocketService:Service() {

    private lateinit var webSocketClient: WebSocketClient
    private lateinit var netThread: NetWorkThread

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        if (!this::webSocketClient.isInitialized){
            webSocketClient = MyWebSocket("ws://192.168.3.25:8080/webSocket/${User.user_id}")
        }

        netThread = NetWorkThread(webSocketClient)
        netThread.start()
        Log.d("ServiceNet","已连接")

        EventBus.getDefault().register(this)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ServiceNet",webSocketClient.readyState.toString())
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        if (this::webSocketClient.isInitialized){
            webSocketClient.closeBlocking()
        }
        netThread.exit = false
        super.onDestroy()
        EventBus.getDefault().unregister(this);
    }



    class NetWorkThread(var SocketClient: WebSocketClient) : Thread(){
        var exit: Boolean = true
        override fun run() {
            super.run()
            try {
                if (SocketClient.connectBlocking()) {
                    Log.i("WebSocketService", "run: 连接服务器成功")
                } else {
                    Log.i("WebSocketService", "run: 连接服务器失败")
                }
                while(exit){
                    sleep(2000)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
        }
    }


    class updateMessageItem(val messageData: MessageData){}
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updateMessageList(message: updateMessageItem){
        val messageData=message.messageData
        Log.d("update","生效")
        MessageItemViewModel.setMessageData(messageData)
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun sendMessage(messageData: MessageData){
        if (messageData!=null && this::webSocketClient.isInitialized){
            if (webSocketClient.readyState == ReadyState.OPEN){
                try {
                    webSocketClient.send(JSON.toJSONString(messageData))
                    Log.d("WebSocketService","发送消息${JSON.toJSONString(messageData)}")
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }






}