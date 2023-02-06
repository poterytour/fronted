package com.example.poetrytour.ui.message

class MessageData {

    private var msgType = 0

    private var fromUserId: String? = null

    private var toUserId: String? = null

    private var msgData: String? = null

    private var time: String? = null

    fun MessageData() {}

    fun getMsgType(): Int {
        return msgType
    }

    fun setMsgType(msgType: Int) {
        this.msgType = msgType
    }

    fun getFromUserId(): String? {
        return fromUserId
    }

    fun setFromUserId(fromUserId: String?) {
        this.fromUserId = fromUserId
    }

    fun getToUserId(): String? {
        return toUserId
    }

    fun setToUserId(toUserId: String?) {
        this.toUserId = toUserId
    }

    fun getMsgData(): String? {
        return msgData
    }

    fun setMsgData(msgData: String?) {
        this.msgData = msgData
    }

    fun getTime(): String? {
        return time
    }

    fun setTime(time: String?) {
        this.time = time
    }

}