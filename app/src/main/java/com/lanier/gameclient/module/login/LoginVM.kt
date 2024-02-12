package com.lanier.gameclient.module.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lanier.gameclient.net.APIHelper

class LoginVM : ViewModel() {

    var inputAccount = MutableLiveData("")
    var inputPassword = MutableLiveData("")

    val error = MutableLiveData("")

    fun login() {
        if (checkPassed().not()) {
            return
        }

    }

    private fun checkPassed() : Boolean {
        val account = inputAccount.value
        val password = inputPassword.value
        val passed = account.isNullOrEmpty().not() || password.isNullOrEmpty().not()
        if (passed.not()) {
            error.postValue("账号或密码为空")
        }
        return passed
    }
}