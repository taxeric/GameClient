package com.lanier.gameclient.entity

class BaseEntity<T> {

    var code: Int = 0
    var message: String = ""
    var serviceTime: Long = 0L
    var data: T? = null

    companion object {

        const val CODE_SUCCESS = 0
    }
}