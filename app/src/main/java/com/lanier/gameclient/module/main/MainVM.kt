package com.lanier.gameclient.module.main

import androidx.lifecycle.ViewModel
import com.lanier.gameclient.ext.launchSafe
import com.lanier.gameclient.net.APIHelper
import okhttp3.FormBody
import kotlin.random.Random

/**
 * Created by 幻弦让叶
 * Date 2024/1/28 18:42
 */
class MainVM : ViewModel() {

    fun mock1() {
        launchSafe {
            val body = FormBody.Builder()
                .add("msg", "后端下发数据 - 推送消息 - 随机数 ${Random.nextInt(128)}")
                .build()
            val response = APIHelper.request(
                url = "http://localhost:8080/push/send",
                method = "POST",
                body = body
            )
        }
    }

    fun mock2() {
        launchSafe {
            val body = FormBody.Builder()
                .add("type", "后端下发数据 - 推送消息 - 系统消息 恭喜获得【圣龙阿布 100级】")
                .build()
            val response = APIHelper.request(
                url = "http://localhost:8080/push/send/type",
                method = "POST",
                body = body
            )
        }
    }
}