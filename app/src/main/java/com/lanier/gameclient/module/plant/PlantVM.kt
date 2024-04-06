package com.lanier.gameclient.module.plant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.lanier.gameclient.base.BaseViewModel
import com.lanier.gameclient.base.ViewStatus
import com.lanier.gameclient.base.data.UserData
import com.lanier.gameclient.entity.BaseListModel
import com.lanier.gameclient.entity.Land
import com.lanier.gameclient.entity.Backpack
import com.lanier.gameclient.entity.BaseEntity
import com.lanier.gameclient.entity.LandCropStageInfo
import com.lanier.gameclient.entity.Pet
import com.lanier.gameclient.entity.SeedStageInfo
import com.lanier.gameclient.entity.dto.PlantDto
import com.lanier.gameclient.ext.launchSafe
import com.lanier.gameclient.net.API
import okhttp3.FormBody
import kotlin.math.abs

class PlantVM : BaseViewModel() {

    private val _landInfos = MutableLiveData<List<Land>>()
    val landInfos: LiveData<List<Land>> = _landInfos

    private val _harvestResult = MutableLiveData<Boolean>()
    val harvestResult: LiveData<Boolean> = _harvestResult

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
            setStatus(ViewStatus.Loading)
            successGetCatch<BaseListModel<Land>>(API.LAND_INFO, requestBody, showLoading = false)?.let {
                processLandPlants(it.list)
                setStatus(ViewStatus.Completed)
                _landInfos.postValue(it.list)
            }
        }
    }

    fun getPlantInfo(
        onSuccess: (() -> Unit)? = null
    ) {
        launchSafe {
            val requestBody = FormBody.Builder()
                .add("petId", UserData.curPet.petId.toString())
                .build()
            successGetCatch<Pet>(API.PET_PLANT_INFO, requestBody, showLoading = false)?.let {
                UserData.notifyPetPlantInfo(it)
                onSuccess?.invoke()
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
            val body = FormBody.Builder()
                .add("petId", UserData.curPet.petId.toString())
                .add("landId", landId.toString())
                .build()
            successPostCatch<Boolean>(API.LAND_HARVEST, body)?.let {
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

    private fun processLandPlants(lands: List<Land>) {
        val jacksonMapper = jacksonObjectMapper()
        lands.forEach { land ->
            if (land.status == Land.PLANTING) {
                refreshImpl(land)
            }
            land.seed?.let { seed ->
                val ssi = try {
                    jacksonMapper.readValue(seed.stageInfo, SeedStageInfo::class.java)
                } catch (e: Exception) {
                    null
                }
                seed.seedStageInfo = ssi
            }
        }
    }

    private fun refreshImpl(curLandInfo: Land) {
        val objectMapper = jacksonObjectMapper()
        try {
            // 当前阶段值
            var currentStage: Int = curLandInfo.currentStage
            val nextStageStartTime: Long
            val nextStageRemainTime: Int
            val canHarvest: Boolean
            val nextStageAllTime: String = curLandInfo.nextStageAllTime
            val cropStageTimeInfo = objectMapper.readValue(nextStageAllTime, LandCropStageInfo::class.java)
            val calcStage = calculateStage(
                cropStageTimeInfo.nextStageAllTime,
                currentStage
            )
            currentStage = calcStage.realCurrentStage
            nextStageStartTime = calcStage.realNextStageStartTime
            nextStageRemainTime = calcStage.realNextStageRemainTime
            canHarvest = calcStage.canHarvest
            curLandInfo.currentStage = currentStage
            curLandInfo.nextStageStartTime = nextStageStartTime
            curLandInfo.nextStageRemainTime = nextStageRemainTime
            curLandInfo.calcCanHarvest = canHarvest
        } catch (e: JsonProcessingException) {
            println(">>>> process refresh failed ${e.message}")
        }
    }

    /**
     * 计算当前时间对应的阶段和时间
     *
     * @param allNextStageTime 下个阶段开始时间节点
     * @param currentStage     当前阶段index
     * @return 实际的阶段, 实际的开始时间, 实际的剩余时间, 是否可以收获
     */
    private fun calculateStage(
        allNextStageTime: List<Long>,
        currentStage: Int
    ): CalcStage {
        var canHarvest = false // 已经过了最终阶段, 可以收获了
        val curTime = System.currentTimeMillis() // 获取当前时间
        var realCurrentStage = 0
        var realNextStageStartTime = 0L
        var realNextStageRemainTime = 0
        val lastStageTime = allNextStageTime[allNextStageTime.size - 1]
        if (curTime < lastStageTime) { // 说明还未处于可收获阶段
            for (i in currentStage until allNextStageTime.size) {
                val nextStageTime = allNextStageTime[i] // 下一阶段的开始时间戳
                if (curTime < nextStageTime) { // 就处于当前阶段
                    realCurrentStage = i
                    realNextStageStartTime = nextStageTime
                    val remainTimeToNextStage = abs(((curTime - nextStageTime) / 1000).toDouble())
                        .toInt() // 当前时间到下一阶段的剩余秒数
                    realNextStageRemainTime = remainTimeToNextStage
                    break
                }
            }
        } else {
            canHarvest = true
            realCurrentStage = allNextStageTime.size - 1
            realNextStageStartTime = 0
            realNextStageRemainTime = 0
        }
        val calcStage = CalcStage()
        calcStage.realCurrentStage = realCurrentStage
        calcStage.realNextStageStartTime = realNextStageStartTime
        calcStage.realNextStageRemainTime = realNextStageRemainTime
        calcStage.canHarvest = canHarvest
        return calcStage
    }

    private class CalcStage {
        /**
         * 真实的阶段
         */
        var realCurrentStage: Int = 0

        /**
         * 真实的下阶段开始时间
         */
        var realNextStageStartTime: Long = 0

        /**
         * 真实的距离下阶段的剩余时间
         */
        var realNextStageRemainTime: Int = 0

        /**
         * 是否可以收获
         */
        var canHarvest: Boolean = false
    }
}