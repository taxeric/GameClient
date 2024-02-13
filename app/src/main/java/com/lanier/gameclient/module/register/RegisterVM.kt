package com.lanier.gameclient.module.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lanier.gameclient.ext.launchSafe
import com.lanier.gameclient.net.API
import com.lanier.gameclient.net.APIWrapperHelper
import okhttp3.FormBody

class RegisterVM: ViewModel() {

    var inputAccount = MutableLiveData("")
    var inputPassword = MutableLiveData("")

    val error = MutableLiveData("")

    fun register(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        onStart: (() -> Unit)? = null,
        onCompleted: (() -> Unit)? = null,
    ) {
        if (checkPassed().not()) {
            return
        }
        onStart?.invoke()
        launchSafe {
            val requestBody = FormBody.Builder()
                .add("account", inputAccount.value!!)
                .add("password", inputPassword.value!!)
                .build()
            val result = APIWrapperHelper.postSync<Boolean>(API.USER_REGISTER, requestBody)
            result.data?.let {
                if (it) {
                    onSuccess.invoke()
                } else {
                    onFailure.invoke(result.message)
                }
            }
            onCompleted?.invoke()
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