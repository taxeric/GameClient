package com.lanier.gameclient.module.shop

import com.lanier.gameclient.R
import com.lanier.gameclient.base.BaseAct
import com.lanier.gameclient.databinding.ActivityShopBinding

class ShopAct(
    override val layoutId: Int = R.layout.activity_shop
) : BaseAct<ActivityShopBinding>() {

    override fun onBind() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, ShopFragment.newInstance())
            .commit()
        viewbinding.fragmentContainer
    }
}