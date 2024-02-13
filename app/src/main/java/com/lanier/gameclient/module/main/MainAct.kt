package com.lanier.gameclient.module.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.lanier.gameclient.R
import com.lanier.gameclient.client.OkWebSocketClientManager
import com.lanier.gameclient.ext.launchSafe
import com.lanier.gameclient.ext.listenAllPreferences
import com.lanier.gameclient.preference.getBooleanValue
import com.lanier.gameclient.preference.getStringValue

class MainAct : AppCompatActivity() {

    private var cacheUrl = ""
    private var isChecked = false

    private val vm by viewModels<MainVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listenAllPreferences {
            cacheUrl = it.getStringValue("websocket_url")
            isChecked = it.getBooleanValue("save_websocket_url")
        }

        launchSafe {
            OkWebSocketClientManager.currentUrlFlow.collect {
            }
        }

        launchSafe {
            OkWebSocketClientManager.pushMessageFlow.collect {
                if (it.def.not()) {
                }
            }
        }
    }
}