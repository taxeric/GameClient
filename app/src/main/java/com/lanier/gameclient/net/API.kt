package com.lanier.gameclient.net

object API {

    var requestHostUrl = ""
        set(value) {
            val mValue = if (value.last() == '/') value.substring(0, value.length - 1) else value
            field = mValue
            APIHelper.commonScheme = mValue
        }
        get() = APIHelper.commonScheme

    const val USER_LOGIN = "/user/login"
    const val USER_REGISTER = "/user/register"
    const val USER_GET_INFO = "/user/getInfo"
}