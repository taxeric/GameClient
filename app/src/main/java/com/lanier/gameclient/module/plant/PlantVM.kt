package com.lanier.gameclient.module.plant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lanier.gameclient.base.BaseViewModel
import com.lanier.gameclient.base.data.UserData
import com.lanier.gameclient.entity.BaseListModel
import com.lanier.gameclient.entity.Land
import com.lanier.gameclient.entity.Backpack
import com.lanier.gameclient.entity.dto.PlantDto
import com.lanier.gameclient.ext.launchSafe
import com.lanier.gameclient.net.API
import okhttp3.FormBody

class PlantVM : BaseViewModel() {

    private val _landInfos = MutableLiveData<List<Land>>()
    val landInfos: LiveData<List<Land>> = _landInfos

    private val _harvestResult = MutableLiveData<Boolean>()
    val harvest: LiveData<Boolean> = _harvestResult

    private val _backpackList = MutableLiveData<Pair<Boolean, List<Backpack>>>()
    val backpackList : LiveData<Pair<Boolean, List<Backpack>>> = _backpackList

    private var page : Int = 1

    val refresh: Boolean get() = page == 1

    /**
     * 获取土地数据
     */
    fun getLandsData() {
        launchSafe {
            val requestBody = FormBody.Builder()
                .add("petId", UserData.curPet.petId.toString())
                .build()
            successGetCatch<BaseListModel<Land>>(API.LAND_INFO, requestBody)?.let {
                _landInfos.postValue(it.list)
            }
        }
    }

    fun plant(
        landId: Int,
        bpkId: Int,
        seedId: Int,
    ) {
        launchSafe {
            val dto = PlantDto(
                UserData.curPet.petId!!,
                landId,
                bpkId,
                seedId
            )
            successPostCatch<Boolean, PlantDto>(API.LAND_PLANT, dto)?.let {
                if (it) {
                    getLandsData()
                }
            }
        }
    }

    fun harvest(
        landId: Int
    ) {
        launchSafe {
            val dto = PlantDto(
                UserData.curPet.petId!!,
                landId,
            )
            successPostCatch<Boolean, PlantDto>(API.LAND_HARVEST, dto)?.let {
                _harvestResult.postValue(it)
            }
        }
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
            successGetCatch<BaseListModel<Backpack>>(API.BACKPACK_FERTILIZERS, requestBody)?.let {
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
            successGetCatch<BaseListModel<Backpack>>(API.BACKPACK_SEEDS, requestBody)?.let {
                _backpackList.postValue(Pair(it.hasNext, it.list))
            }
        }
    }
}