package com.lanier.gameclient.module.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lanier.gameclient.base.data.UserData
import com.lanier.gameclient.entity.User
import com.lanier.gameclient.ext.launchSafe
import com.lanier.gameclient.net.API
import com.lanier.gameclient.net.APIWrapperHelper
import okhttp3.FormBody

class LoginVM : ViewModel() {

    var inputAccount = MutableLiveData("")
    var inputPassword = MutableLiveData("")

    val checkError = MutableLiveData("")

    fun login(
        onSuccess: (User) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        if (checkPassed().not()) {
            return
        }
        launchSafe(
            error = {onFailure.invoke(it.message)}
        ) {
            val requestBody = FormBody.Builder()
                .add("account", inputAccount.value!!)
                .add("password", inputPassword.value!!)
                .build()
            val user = APIWrapperHelper.postSync<User>(API.USER_LOGIN, requestBody) {
                onFailure.invoke(it)
            }
            user?.let {
                UserData.afterLogin(user)
                onSuccess.invoke(user)
            }
        }
    }

    private fun checkPassed(): Boolean {
        val account = inputAccount.value
        val password = inputPassword.value
        val passed = account.isNullOrEmpty().not() || password.isNullOrEmpty().not()
        if (passed.not()) {
            checkError.postValue("账号或密码为空")
        }
        return passed
    }
}