package com.lanier.gameclient.entity

/**
 * Created by 幻弦让叶
 * Date 2024/3/2 17:10
 */
interface BaseResponse<T> {

    fun getResponse() : T?
    fun isSuccess() : Boolean
}