package com.lanier.gameclient.module.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lanier.gameclient.base.BaseViewModel
import com.lanier.gameclient.base.ViewStatus
import com.lanier.gameclient.base.data.UserData
import com.lanier.gameclient.entity.BaseListModel
import com.lanier.gameclient.entity.MarketItem
import com.lanier.gameclient.ext.launchSafe
import com.lanier.gameclient.net.API
import com.lanier.gameclient.net.APIWrapperHelper
import okhttp3.FormBody

class ShopVM : BaseViewModel() {

    private val _datas = MutableLiveData<Pair<Boolean, List<MarketItem>>>()
    val datas: LiveData<Pair<Boolean, List<MarketItem>>> = _datas

    private val _purchaseResult = MutableLiveData<Pair<Boolean, String>>()
    val purchaseResult: LiveData<Pair<Boolean, String>> = _purchaseResult

    private var page = 1
    val refresh: Boolean get() = page == 1

    fun getSeeds(refresh: Boolean = true) {
        if (refresh) page = 1 else page++
        launchSafe {
            val requestBody = FormBody.Builder()
                .add("page", page.toString())
                .add("pageSize", "10")
                .build()
            val marketItems = APIWrapperHelper
                .getSync<BaseListModel<MarketItem>>(API.MARKET_SEEDS, requestBody) {
                    println(">>>> failed $it")
                }
            marketItems?.let {
                _datas.postValue(Pair(it.hasNext, it.list))
            }
        }
    }

    fun getFertilizer(refresh: Boolean = true) {
        if (refresh) page = 1 else page++
        launchSafe {
            val requestBody = FormBody.Builder()
                .add("page", page.toString())
                .add("pageSize", "10")
                .build()
            val marketItems = APIWrapperHelper
                .getSync<BaseListModel<MarketItem>>(API.MARKET_FERTILIZERS, requestBody) {
                    println(">>>> failed $it")
                }
            marketItems?.let {
                _datas.postValue(Pair(it.hasNext, it.list))
            }
        }
    }

    fun purchase(
        type: Int,
        realPropId: Int,
        quantity: Int,
        marketItemId: Int,
    ) {
        launchSafe {
            setStatus(ViewStatus.Loading)
            val requestBody = FormBody.Builder()
                .add("petId", UserData.curPet.petId.toString())
                .add("userId", UserData.mUid.toString())
                .add("type", type.toString())
                .add("realPropId", realPropId.toString())
                .add("quantity", quantity.toString())
                .add("marketItemId", marketItemId.toString())
                .build()
            successPostCatch<Boolean>(API.MARKET_PURCHASE, requestBody, showToast = true)?.let {
                _purchaseResult.value = Pair(it, if (it) "购买成功~" else "商品暂时不能购买哦~")
            }
            setStatus(ViewStatus.Completed)
        }
    }
}