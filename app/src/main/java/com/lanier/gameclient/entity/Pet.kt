package com.lanier.gameclient.entity

data class Pet(
    val age: Int? = 0,
    val birthday: String? = "",
    val cleanliness: Int? = 0,
    val coin: Int = 0,
    val emotion: Int? = 0,
    val healthy: Int? = 0,
    val intelligence: Int? = 0,
    val muscle: Int? = 0,
    val name: String? = "",
    val petId: Int? = 0,
    val phase: Int? = 0,
    val satiety: Int? = 0,
    val userId: Int? = 0,
    val lackExp : Int = 0,
    val currentPlantExp: Int? = 0,
    val currentLevel: PlantLevel = PlantLevel(),
    val unlockedLandCount : Int = 0,
    val usedLandCount : Int = 0,
    val maxLandCount : Int = 0,
    val def: Boolean = false,
) {

    companion object {

        @JvmStatic
        val def = Pet(def = true)
    }

    var selected = false
}
