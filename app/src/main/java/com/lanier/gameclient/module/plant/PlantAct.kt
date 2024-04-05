package com.lanier.gameclient.module.plant

import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lanier.gameclient.R
import com.lanier.gameclient.base.BaseAct
import com.lanier.gameclient.base.ViewStatus
import com.lanier.gameclient.databinding.ActivityPlantBinding
import com.lanier.gameclient.entity.Land
import com.lanier.gameclient.entity.Backpack
import com.lanier.gameclient.ext.startAct
import com.lanier.gameclient.module.dialog.BSDFLandOperation
import com.lanier.gameclient.module.dialog.LandOperationListener
import com.lanier.gameclient.module.shop.ShopAct

class PlantAct(
    override val layoutId: Int = R.layout.activity_plant
) : BaseAct<ActivityPlantBinding>() {

    private val vm by viewModels<PlantVM>()

    private val mAdapter by lazy {
        LandAdapter().apply {
            setOnItemChildClickListener { adapter, _, position ->
                val land = adapter.getItem(position) as? Land
                land?.let {
                    operationAction(it)
                }
            }
        }
    }

    override fun onBind() {
        viewbinding.btnShop.setOnClickListener {
            startAct<ShopAct> {  }
        }

        viewbinding.recyclerView.adapter = mAdapter
        viewbinding.recyclerView.layoutManager = LinearLayoutManager(this)
        vm.landInfos.observe(this) {
            mAdapter.setList(it)
        }
        vm.viewStatus.observe(this) {
            when (it) {
                ViewStatus.Completed -> { dismissLoading() }
                ViewStatus.Loading -> { showLoading() }
            }
        }

        vm.getLandsData()
    }

    private fun operationAction(land: Land) {
        val dialog = BSDFLandOperation.show(land.canHarvest(), supportFragmentManager)
        dialog.operationListener = object : LandOperationListener {
            override fun harvest() {
                vm.harvest(land.landId)
            }

            override fun plant(item: Backpack?) {
                item?.let {
                    vm.plant(land.landId, land.bpkId, it.realPropId)
                }
            }

            override fun useFertilizer(item: Backpack?) {
            }
        }
    }
}