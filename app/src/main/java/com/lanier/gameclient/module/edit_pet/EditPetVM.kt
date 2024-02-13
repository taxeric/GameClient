package com.lanier.gameclient.module.edit_pet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lanier.gameclient.base.data.UserData
import com.lanier.gameclient.entity.Pet
import com.lanier.gameclient.ext.launchSafe
import com.lanier.gameclient.net.API
import com.lanier.gameclient.net.APIWrapperHelper
import okhttp3.FormBody

class EditPetVM : ViewModel() {

    var nickname = MutableLiveData("")

    fun create(
        onSuccess: () -> Unit,
        onFailed: (String) -> Unit,
        onStart: (() -> Unit)? = null,
        onCompleted: (() -> Unit)? = null,
    ) {
        if (nickname.value.isNullOrEmpty()) {
            onFailed.invoke("给宠物起个名字吧~")
            return
        }
        onStart?.invoke()
        launchSafe {
            val requestBody = FormBody.Builder()
                .add("name", nickname.value!!)
                .add("userId", UserData.mUid.toString())
                .build()
            val result = APIWrapperHelper.postSync<Pet>(
                url = API.PET_CREATE,
                requestBody = requestBody
            )
            result.data?.let {
                UserData.notifyPet(pet = it)
                onSuccess.invoke()
            } ?: onFailed.invoke(result.message)
            onCompleted?.invoke()
        }
    }
}