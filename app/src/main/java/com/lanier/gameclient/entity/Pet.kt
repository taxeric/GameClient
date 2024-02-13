package com.lanier.gameclient.entity

data class Pet(
    val age: Int? = 0,
    val birthday: String? = "",
    val cleanliness: Int? = 0,
    val emotion: Int? = 0,
    val healthy: Int? = 0,
    val intelligence: Int? = 0,
    val muscle: Int? = 0,
    val name: String? = "",
    val petId: Int? = 0,
    val phase: Int? = 0,
    val satiety: Int? = 0,
    val userId: Int? = 0,
    val def: Boolean = false,
) {

    companion object {

        @JvmStatic
        val def = Pet(def = true)
    }
}
