package com.lanier.gameclient.module.main

import androidx.lifecycle.ViewModel
import com.lanier.gameclient.client.OkWebSocketClientManager

/**
 * Created by 幻弦让叶
 * Date 2024/1/28 18:42
 */
class MainVM : ViewModel() {

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
}