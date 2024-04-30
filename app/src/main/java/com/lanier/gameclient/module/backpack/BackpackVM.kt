package com.lanier.gameclient.module.backpack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lanier.gameclient.base.data.UserData
import com.lanier.gameclient.entity.BaseListModel
import com.lanier.gameclient.entity.Backpack
import com.lanier.gameclient.ext.launchSafe
import com.lanier.gameclient.net.API
import com.lanier.gameclient.net.APIWrapperHelper
import okhttp3.FormBody

/**
 * Created by 幻弦让叶
 * Date 2024/3/15 23:50
 */
class BackpackVM : ViewModel() {

    private val _backpackList = MutableLiveData<Pair<Boolean, List<Backpack>>>()
    val backpackList : LiveData<Pair<Boolean, List<Backpack>>> = _backpackList

    private var page : Int = 1
    val refresh : Boolean get() = page == 1

    fun foods() {
    }

    fun toiletries() {
    }

    fun drugs() {
    }

    fun fertilizer(
        refresh: Boolean = true
    ) {
        if (refresh) {
            page = 1
        } else {
            page ++
        }
        launchSafe {
            val requestBody = FormBody.Builder()
                .add("petId", UserData.curPet.petId.toString())
                .add("page", page.toString())
                .add("pageSize", "10")
                .build()
            val backpackList = APIWrapperHelper
                .getSync<BaseListModel<Backpack>>(API.BACKPACK_FERTILIZERS, requestBody)
            backpackList?.let {
                _backpackList.postValue(Pair(it.hasNext, it.list))
            }
        }
    }

    fun seeds(
        refresh: Boolean = true
    ) {
        if (refresh) {
            page = 1
        } else {
            page ++
        }
        launchSafe {
            val requestBody = FormBody.Builder()
                .add("petId", UserData.curPet.petId.toString())
                .add("page", page.toString())
                .add("pageSize", "10")
                .build()
            val backpackList = APIWrapperHelper
                .getSync<BaseListModel<Backpack>>(API.BACKPACK_SEEDS, requestBody)
            backpackList?.let {
                _backpackList.postValue(Pair(it.hasNext, it.list))
            }
        }
    }

    fun crops() {
    }
}