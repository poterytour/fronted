package com.example.poetrytour.model

import java.io.Serializable

/**
 * JSON 返回模型
 */
class Result<D> : Serializable {
    var isSuccess = false
        private set
    var code: String? = null
        private set
    var message: String? = null
        private set
    var data: D? = null
        private set

    companion object {
        fun <T> create(): Result<T> {
            return Result()
        }
    }

    override fun toString(): String {
        return "Result(isSuccess=$isSuccess, code=$code, message=$message, data=$data)"
    }

}