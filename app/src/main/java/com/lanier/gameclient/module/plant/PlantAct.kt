package com.lanier.gameclient.module.plant

import com.lanier.gameclient.R
import com.lanier.gameclient.base.BaseAct
import com.lanier.gameclient.databinding.ActivityPlantBinding
import com.lanier.gameclient.ext.startAct
import com.lanier.gameclient.module.shop.ShopAct

class PlantAct(
    override val layoutId: Int = R.layout.activity_plant
) : BaseAct<ActivityPlantBinding>() {

    override fun onBind() {
        viewbinding.btnShop.setOnClickListener {
            startAct<ShopAct> {  }
        }
    }
}