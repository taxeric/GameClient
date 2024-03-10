package com.lanier.gameclient.module.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lanier.gameclient.base.BaseViewModel
import com.lanier.gameclient.base.ViewStatus
import com.lanier.gameclient.base.data.UserData
import com.lanier.gameclient.entity.BaseListModel
import com.lanier.gameclient.entity.MarketItem
import com.lanier.gameclient.ext.launchSafe
import com.lanier.gameclient.net.API
import com.lanier.gameclient.net.APIWrapperHelper
import okhttp3.FormBody
import org.lanier.gameserve2.entity.PropType

class ShopVM : BaseViewModel() {

    private val _datas = MutableLiveData<List<MarketItem>>()
    val datas: LiveData<List<MarketItem>> = _datas

    private val _purchaseResult = MutableLiveData<Pair<Boolean, String>>()
    val purchaseResult: LiveData<Pair<Boolean, String>> = _purchaseResult

    var refresh: Boolean = true
        private set

    private var seedPage: Int = 1

    fun getSeeds() {
        launchSafe {
            refresh = true
            val mPage = seedPage
            val requestBody = FormBody.Builder()
                .add("page", mPage.toString())
                .add("pageSize", "10")
                .build()
            val marketItems = APIWrapperHelper
                .getSync<BaseListModel<MarketItem>>(API.MARKET_SEEDS, requestBody) {
                    println(">>>> failed $it")
                }
            marketItems?.let {
                _datas.postValue(it.list)
                for (i in it.list) {
                    println(">>>> ss ${i.name} ${i.price}")
                }
                if (it.hasNext) {
                    refresh = false
                    seedPage++
                }
            }
        }
    }

    fun getFertilizer() {
        launchSafe {
            refresh = true
            val mPage = seedPage
            val requestBody = FormBody.Builder()
                .add("page", mPage.toString())
                .add("pageSize", "10")
                .build()
            val marketItems = APIWrapperHelper
                .getSync<BaseListModel<MarketItem>>(API.MARKET_FERTILIZERS, requestBody) {
                    println(">>>> failed $it")
                }
            marketItems?.let {
                _datas.postValue(it.list)
                if (it.hasNext) {
                    refresh = false
                    seedPage++
                }
            }
        }
    }

    fun purchase(
        type: Int,
        realPropId: Int,
        quantity: Int,
        marketItemId: Int,
        onFailure: (() -> Unit)? = null,
        onSuccess: (() -> Unit)? = null,
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
            successCatch<Boolean>(API.MARKET_PURCHASE, requestBody)?.let {
                    if (it) {
                        onSuccess?.invoke()
                    }
                } ?: onFailure?.invoke()
            setStatus(ViewStatus.Completed)
        }
    }
}