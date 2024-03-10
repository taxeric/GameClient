package com.lanier.gameclient.module.main

import androidx.activity.viewModels
import com.lanier.gameclient.R
import com.lanier.gameclient.base.BaseAct
import com.lanier.gameclient.client.OkWebSocketClientManager
import com.lanier.gameclient.databinding.ActivityMainBinding
import com.lanier.gameclient.ext.launchSafe
import com.lanier.gameclient.ext.listenAllPreferences
import com.lanier.gameclient.ext.startAct
import com.lanier.gameclient.module.plant.PlantAct
import com.lanier.gameclient.preference.getBooleanValue
import com.lanier.gameclient.preference.getStringValue

class MainAct(
    override val layoutId: Int = R.layout.activity_main
) : BaseAct<ActivityMainBinding>() {

    private var cacheUrl = ""
    private var isChecked = false

    private val vm by viewModels<MainVM>()

    override fun onBind() {
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

        viewbinding.btnPlant.setOnClickListener {
            startAct<PlantAct> {  }
        }
    }
}