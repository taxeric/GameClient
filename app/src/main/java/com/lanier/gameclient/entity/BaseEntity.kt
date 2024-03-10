package com.lanier.gameclient.entity

class BaseEntity<T> : BaseResponse<T> {

    var code: String = ""
    var message: String = ""
    var serverTime: Long = 0L
    var data: T? = null

    companion object {

        const val CODE_SUCCESS = "000000"
    }

    override fun getResponse(): T? {
        return data
    }

    override fun isSuccess(): Boolean {
        return code == CODE_SUCCESS
    }
}