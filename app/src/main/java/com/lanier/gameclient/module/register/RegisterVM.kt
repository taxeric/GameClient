package com.lanier.gameclient.module.register

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterVM: ViewModel() {

    var inputAccount = ObservableField("")
    var inputPassword = ObservableField("")

    val error = MutableLiveData("")

    fun register() {
        if (checkPassed().not()) {
            return
        }
    }

    private fun checkPassed() : Boolean {
        val account = inputAccount.get()
        val password = inputPassword.get()
        val passed = account.isNullOrEmpty().not() || password.isNullOrEmpty().not()
        if (passed.not()) {
            error.postValue("账号或密码为空")
        }
        return passed
    }
}