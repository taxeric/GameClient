package com.lanier.gameclient.entity

import org.lanier.gameserve2.entity.PropType

data class Market(

    val propId: Int = 0,
    val propType: Int = PropType.FOOD,
    val name: String = "",
    val desc: String = "",
    val price: Int = 0,
    val effect: String = "",
)