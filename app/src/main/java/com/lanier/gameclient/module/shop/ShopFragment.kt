package com.lanier.gameclient.module.shop

import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.lanier.gameclient.R
import com.lanier.gameclient.base.BaseFra
import com.lanier.gameclient.databinding.FragmentShopBinding
import org.lanier.gameserve2.entity.PropType

class ShopFragment(
    override val layoutId: Int = R.layout.fragment_shop
) : BaseFra<FragmentShopBinding>() {

    private val tabs = mutableListOf<PropType>()
    private var currentTabIndex = 0

    private val viewmodel by viewModels<ShopVM>()

    override fun onBind() {
        tabs.add(PropType(typeId = PropType.FOOD, typeName = getString(R.string.food)))
        tabs.add(PropType(typeId = PropType.TOILETRIES, typeName = getString(R.string.toiletries)))
        tabs.add(PropType(typeId = PropType.DRUG, typeName = getString(R.string.drug)))
        tabs.add(PropType(typeId = PropType.SEED, typeName = getString(R.string.seed)))
        tabs.add(PropType(typeId = PropType.FERTILIZER, typeName = getString(R.string.fertilizer)))
        viewbinding.tabLayout.apply {
            tabs.forEach { tab ->
                val mTab = viewbinding.tabLayout.newTab()
                mTab.text = tab.typeName
                addTab(mTab)
            }
        }
    }

    private val onTabSelectedList = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            val index = tab.position
            if (currentTabIndex != index) {
                currentTabIndex = index
            }
            viewmodel
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }
    }
}