package com.lanier.gameclient.module.main

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import com.lanier.gameclient.R
import com.lanier.gameclient.client.OkWebSocketClientManager
import com.lanier.gameclient.ext.launchSafe
import com.lanier.gameclient.ext.listenAllPreferences
import com.lanier.gameclient.ext.startAct
import com.lanier.gameclient.module.wsedit.WBEditAct
import com.lanier.gameclient.preference.getBooleanValue
import com.lanier.gameclient.preference.getStringValue

class MainActivity : AppCompatActivity() {

    private val tvStatus : TextView by lazy {
        findViewById(R.id.tvStatus)
    }

    private val btn1 : Button by lazy {
        findViewById(R.id.editAct)
    }

    private val btn2 : Button by lazy {
        findViewById(R.id.link)
    }

    private val btn3 : Button by lazy {
        findViewById(R.id.mock1)
    }

    private val btn4 : Button by lazy {
        findViewById(R.id.mock2)
    }

    private val tvPush : TextView by lazy {
        findViewById(R.id.tvPush)
    }

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
                tvStatus.text = buildSpannedString {
                    if (it.isEmpty()) {
                        append("未连接")
                    } else {
                        append("连接到: ")
                        color(Color.BLUE) {
                            append(it)
                        }
                    }
                }
            }
        }

        launchSafe {
            OkWebSocketClientManager.pushMessageFlow.collect {
                if (it.def.not()) {
                    tvPush.text = it.text ?: "空数据"
                }
            }
        }

        btn1.setOnClickListener {
            startAct<WBEditAct> {  }
        }

        btn2.setOnClickListener {
            link()
        }

        btn3.setOnClickListener {
            vm.mock1()
        }

        btn4.setOnClickListener {
            vm.mock2()
        }
    }

    private fun link() {
        if (cacheUrl.isEmpty()) {
            cacheUrl = OkWebSocketClientManager.currentUrl
            if (cacheUrl.isEmpty()) {
                return
            }
        }
        OkWebSocketClientManager.newWebSocket(cacheUrl)
    }
}