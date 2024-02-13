package com.lanier.gameclient.entity

data class User(
    val userId: Int = 0,
    val account: String = "",
    val pets: List<Pet> = emptyList(),
    val def: Boolean = false
) {

    companion object {

        @JvmStatic
        val NOBODY = User(def = true)
    }
}
