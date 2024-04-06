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

    const val PET_CREATE = "/pet/create"
    const val PET_INFO = "/pet/info"
    const val PET_PLANT_INFO = "/pet/get-plant-info"

    const val BACKPACK_SEEDS = "/backpack/seeds"
    const val BACKPACK_FERTILIZERS = "/backpack/fertilizer"

    const val MARKET_SEEDS = "/market/seeds"
    const val MARKET_FERTILIZERS = "/market/fertilizer"
    const val MARKET_PURCHASE = "/market/purchase"

    const val LAND_INFO = "/land/get-info"
    const val LAND_PLANT = "/land/plant"
//    const val LAND_USE_FERTILIZER = "/land/use-fertilizer"
    const val LAND_HARVEST = "/land/harvest"
}