package com.lanier.gameclient.module.wsedit

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.lanier.gameclient.R
import com.lanier.gameclient.client.OkWebSocketClientManager
import com.lanier.gameclient.ext.collect
import com.lanier.gameclient.ext.launchSafe
import com.lanier.gameclient.ext.post
import com.lanier.gameclient.ext.saveToPreferences
import com.lanier.gameclient.message.WebSocketMessage
import com.lanier.gameclient.preference.getBooleanKey
import com.lanier.gameclient.preference.getStringKey

class WBEditAct : AppCompatActivity() {

    private val etEditWBUrl: EditText by lazy {
        findViewById(R.id.etWBUrl)
    }

    private val cbSave: CheckBox by lazy {
        findViewById(R.id.cbSave)
    }

    private val btnLink: Button by lazy {
        findViewById(R.id.btnLink)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_edit_websocket)

        btnLink.setOnClickListener {
            saveAndLink()
        }

        launchSafe {
            OkWebSocketClientManager.isConnectedFlow.collect {
                if (it) {
                    val listener = DialogInterface.OnClickListener { dialog, which ->
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            finish()
                            dialog?.dismiss()
                        }
                    }
                    AlertDialog.Builder(this@WBEditAct)
                        .setMessage("已连接到\n${OkWebSocketClientManager.currentUrl}")
                        .setCancelable(false)
                        .setPositiveButton("确定", listener)
                        .show()
                }
            }
        }
    }

    private fun saveAndLink() {
        val url = etEditWBUrl.text.toString()
        if (url.isNullOrEmpty()) {
            return
        }
        if (!url.contains("ws")) {
            return
        }
        saveToPreferences {
            it[getBooleanKey("save_websocket_url")] = cbSave.isChecked
            if (cbSave.isChecked) {
                it[getStringKey("websocket_url")] = url
            }
        }
        WebSocketMessage(
            action = WebSocketMessage.MSG_ACTION_REQUEST_LINK,
            text = url
        ).post()
    }
}