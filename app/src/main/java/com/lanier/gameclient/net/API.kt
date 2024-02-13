package com.lanier.gameclient.net

object API {

    var requestHostUrl = ""
        set(value) {
            field = value
            APIHelper.commonScheme = value
        }

    const val USER_LOGIN = "/user/login"
    const val USER_REGISTER = "/user/register"
    const val USER_GET_INFO = "/user/getInfo"
}