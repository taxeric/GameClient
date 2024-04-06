package com.lanier.gameclient.base.data

import com.lanier.gameclient.entity.Pet
import com.lanier.gameclient.entity.User

object UserData {

    private var mUser: User = User.NOBODY

    var mUid : Int = 0
        private set

    var mAccount: String = ""
        private set

    val pets : MutableList<Pet> = mutableListOf()

    val emptyPets: Boolean
        get() = pets.isEmpty()

    var curPet: Pet = Pet.def
        private set

    fun afterLogin(user: User) {
        notifyUser(user)
        notifyPets(user.pets)
    }

    fun afterLogout() {
    }

    private fun notifyUser(user: User) {
        mUser = user.copy(def = false)
        mUid = user.userId
        mAccount = user.account
    }

    private fun notifyPets(ps: List<Pet>, autoChooseIfCheckPassed: Boolean = true) {
        pets.clear()
        pets.addAll(ps)
        if (autoChooseIfCheckPassed) {
            if (pets.isNotEmpty() && pets.size == 1) {
                notifyPet(pet = pets[0])
            }
        }
    }

    fun notifyPet(pet: Pet) {
        curPet = pet.copy(def = false)
    }

    fun notifyPetPlantInfo(pet: Pet) {
        curPet = curPet.copy(
            def = false,
            currentPlantExp = pet.currentPlantExp,
            lackExp = pet.lackExp,
            coin = pet.coin,
            unlockedLandCount = pet.unlockedLandCount,
            usedLandCount = pet.usedLandCount,
            maxLandCount = pet.maxLandCount,
            currentLevel = pet.currentLevel
        )
    }
}