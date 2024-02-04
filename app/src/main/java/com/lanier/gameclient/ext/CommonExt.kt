package com.lanier.gameclient.ext

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * Created by 幻弦让叶
 * Date 2024/2/5 0:55
 */
fun <T> T.toJson() : String {
    return jacksonObjectMapper().writeValueAsString(this)
}

fun <T> T.toReqJsonBody() : RequestBody {
    return toJson().toRequestBody("application/json".toMediaType())
}
