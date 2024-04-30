package com.lanier.gameclient.module.main

import com.lanier.gameclient.base.BaseViewModel
import com.lanier.gameclient.base.data.UserData
import com.lanier.gameclient.client.OkWebSocketClientManager
import com.lanier.gameclient.entity.Pet
import com.lanier.gameclient.ext.launchSafe
import com.lanier.gameclient.net.API
import okhttp3.FormBody

/**
 * Created by 幻弦让叶
 * Date 2024/1/28 18:42
 */
class MainVM : BaseViewModel() {

    private fun link(cacheUrl: String) {
        var mUrl = ""
        if (cacheUrl.isEmpty()) {
            mUrl = OkWebSocketClientManager.currentUrl
            if (mUrl.isEmpty()) {
                return
            }
        }
        OkWebSocketClientManager.newWebSocket(mUrl)
    }

    fun getPetInfo() {
        launchSafe {
            val requestBody = FormBody.Builder()
                .add("petId", UserData.curPet.petId.toString())
                .build()
            successGetCatch<Pet>(API.PET_INFO, requestBody)?.let {
                UserData.notifyPet(it)
            }
        }
    }
}