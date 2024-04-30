package com.lanier.gameclient.entity

data class MarketItem(
    val marketItemId: Int = 0,
    val name: String = "",
    val desc: String? = "",
    val price: Int = 0,
    val type: Int = 0,
    val realPropId: Int = 0,
) {

    fun priceStr() = price.toString()
}