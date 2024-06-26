package com.lanier.gameclient.entity

import android.text.SpannedString
import androidx.core.graphics.toColorInt
import androidx.core.text.buildSpannedString
import androidx.core.text.color

/**
 * Created by 黄瓜
 * Date 2024/2/14 17:40
 */
data class Land(
    val landId: Int = -1,
    val petId: Int = -1,
    val bpkId: Int = -1, // 背包道具id
    val cropId: Int = -1,
    val seed: Seed? = null, // 当前种植的作物
    val status: Int = 0, // 土地状态, 1=未解锁, 2=种植中, 3=空闲
    var currentStage: Int = 0, // 作物当前所处的阶段
    val maxStage: Int = 0, // 最大阶段
    var nextStageAllTime: String = "", // 下一阶段开始时间点, json字符串
    var nextStageStartTime: Long = 0L, // 下一阶段的开始时间, 单位ms
    var nextStageRemainTime: Int = 0, // 到下一阶段剩余时间, 单位s
    val lastHarvestTime: Long = 0L, // 上一次收获时间
    val maxHarvestCount: Int = 0, // 作物最大可收获次数
    val harvestCount: Int = 0, // 作物已收获次数
) {

    var calcCanHarvest = false

    fun seedStr() = if (locked()) "待解锁~" else seed?.name ?: "空闲中~"

    fun locked() = status == LOCKED

    fun canHarvest(): Boolean {
        return status == PLANTING &&
                currentStage + 1 == maxStage &&
                nextStageRemainTime <= 0 &&
                harvestCount < maxHarvestCount
    }

    fun buildStageInfoStr(): SpannedString {
        seed?.seedStageInfo?.let { ssi ->
            return buildSpannedString {
                repeat(ssi.stageName.size) {
                    if (currentStage == it) {
                        color("#415F91".toColorInt()) {
                            append(ssi.stageName[it])
                        }
                    } else {
                        append(ssi.stageName[it])
                    }
                    append(" ")
                }
            }
        }
        return buildSpannedString {  }
    }

    fun statusStr() = when (status) {
        LOCKED -> "(￣▽￣)/"
        PLANTING -> "୧(๑•̀⌄•́๑)૭"
        else -> "o(〃'▽'〃)o"
    }

    companion object {

        //未解锁
        const val LOCKED = 1

        //种植中
        const val PLANTING = 2

        //空闲
        const val IDLE = 3
    }
}
