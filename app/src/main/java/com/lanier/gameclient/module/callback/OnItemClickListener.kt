package com.lanier.gameclient.module.callback

interface OnItemClickListener<T> {

    fun onItemClick(index: Int, data: T)
}