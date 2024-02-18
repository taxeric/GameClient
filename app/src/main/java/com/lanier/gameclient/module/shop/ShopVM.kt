package com.lanier.gameclient.module.shop

import androidx.lifecycle.ViewModel
import com.lanier.gameclient.entity.articles.Drug
import com.lanier.gameclient.entity.articles.Food
import com.lanier.gameclient.entity.articles.Toiletries
import org.lanier.gameserve2.entity.PropType

class ShopVM : ViewModel() {

    private val foods = mutableListOf<Food>()
    private val drugs = mutableListOf<Drug>()
    private val toiletries = mutableListOf<Toiletries>()
    private val seeds = mutableListOf<Food>()
    private val fertilizer = mutableListOf<Food>()

    private val datas = mutableListOf<Any>()

    /**
     * 更新当前选中的商店数据
     */
    fun getDataByType(type: Int) {
        datas.clear()
        when (type) {
            PropType.FOOD -> {
                if (foods.isNotEmpty()) {
                    datas.addAll(foods)
                } else {
                    _getDataByType(type)
                }
            }
            PropType.TOILETRIES -> {
                if (toiletries.isNotEmpty()) {
                    datas.addAll(toiletries)
                } else {
                    _getDataByType(type)
                }
            }
            PropType.DRUG -> {
                if (drugs.isNotEmpty()) {
                    datas.addAll(drugs)
                } else {
                    _getDataByType(type)
                }
            }
            PropType.SEED -> {
                if (seeds.isNotEmpty()) {
                    datas.addAll(seeds)
                } else {
                    _getDataByType(type)
                }
            }
            PropType.FERTILIZER -> {
                if (fertilizer.isNotEmpty()) {
                    datas.addAll(fertilizer)
                } else {
                    _getDataByType(type)
                }
            }
        }
    }

    private fun _getDataByType(type: Int) {
    }
}