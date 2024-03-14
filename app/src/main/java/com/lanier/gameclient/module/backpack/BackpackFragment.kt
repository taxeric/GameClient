package com.lanier.gameclient.module.backpack

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.lanier.gameclient.R
import com.lanier.gameclient.base.BaseFra
import com.lanier.gameclient.databinding.FragmentBackpackBinding
import org.lanier.gameserve2.entity.PropType

/**
 * Created by 幻弦让叶
 * Date 2024/3/15 0:15
 */
class BackpackFragment(
    override val layoutId: Int = R.layout.fragment_backpack
) : BaseFra<FragmentBackpackBinding>() {

    companion object {

        fun newInstance(): BackpackFragment {
            val args = Bundle()

            val fragment = BackpackFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val tabs = mutableListOf<PropType>()
    private var currentTabIndex = 0

    override fun onBind() {
        tabs.add(PropType(typeId = PropType.SEED, typeName = getString(R.string.seed)))
        tabs.add(PropType(typeId = PropType.FERTILIZER, typeName = getString(R.string.fertilizer)))
        viewbinding.tabLayout.apply {
            tabs.forEach { tab ->
                val mTab = viewbinding.tabLayout.newTab()
                mTab.text = tab.typeName
                addTab(mTab)
            }
            addOnTabSelectedListener(onTabSelectedList)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewbinding.tabLayout.removeOnTabSelectedListener(onTabSelectedList)
    }

    private val onTabSelectedList = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            println(">>>> handle tab ${tab.position}")
            val index = tab.position
            if (currentTabIndex != index) {
                currentTabIndex = index
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }
    }
}