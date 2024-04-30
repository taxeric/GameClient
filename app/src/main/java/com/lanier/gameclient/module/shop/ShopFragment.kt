package com.lanier.gameclient.module.shop

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.lanier.gameclient.R
import com.lanier.gameclient.base.BaseFra
import com.lanier.gameclient.base.ViewStatus
import com.lanier.gameclient.databinding.FragmentShopBinding
import com.lanier.gameclient.ext.post
import com.lanier.gameclient.ext.toast
import com.lanier.gameclient.flowbus.event.Purchase
import com.lanier.gameclient.module.dialog.BSDFPurchase
import org.lanier.gameserve2.entity.PropType

class ShopFragment(
    override val layoutId: Int = R.layout.fragment_shop
) : BaseFra<FragmentShopBinding>() {

    companion object {
        fun newInstance(): ShopFragment {
            val args = Bundle()
            val fragment = ShopFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val tabs = mutableListOf<PropType>()
    private var currentTabIndex = 0

    private val viewmodel by viewModels<ShopVM>()
    private val mAdapter by lazy { ShopAdapter() }

    override fun onBind() {
//        tabs.add(PropType(typeId = PropType.FOOD, typeName = getString(R.string.food)))
//        tabs.add(PropType(typeId = PropType.TOILETRIES, typeName = getString(R.string.toiletries)))
//        tabs.add(PropType(typeId = PropType.DRUG, typeName = getString(R.string.drug)))
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
        mAdapter.setOnItemClickListener { _, _, position ->
            mAdapter.getItemOrNull(position)?.let {
                BSDFPurchase.show(parentFragmentManager, it) { type, propId, marketItemId, number ->
                    viewmodel.purchase(
                        type,
                        propId,
                        number,
                        marketItemId,
                    )
                }
            } ?: "商品暂时不可购买哦".toast()
        }
        viewbinding.recyclerView.adapter = mAdapter
        viewbinding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
        viewmodel.datas.observe(this) {
            if (viewmodel.refresh) {
                mAdapter.setList(it.second)
            } else {
                mAdapter.addData(it.second)
            }
            if (it.first) {
                mAdapter.loadMoreModule.loadMoreComplete()
            } else {
                mAdapter.loadMoreModule.loadMoreEnd()
            }
        }
        viewmodel.purchaseResult.observe(viewLifecycleOwner) {
            if (it.second.isNotEmpty()) {
                Purchase().post()
                it.second.toast()
            }
        }
        viewmodel.viewStatus.observe(viewLifecycleOwner) {
            when (it) {
                ViewStatus.Completed -> { dismissLoading() }
                ViewStatus.Loading -> { showLoading() }
            }
        }
    }

    override fun initData() {
        viewmodel.getSeeds()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewbinding.tabLayout.removeOnTabSelectedListener(onTabSelectedList)
    }

    private fun handleTabIndex() {
        when (currentTabIndex) {
            0 -> {
                viewmodel.getSeeds()
            }
            1 -> {
                viewmodel.getFertilizer()
            }
        }
    }

    private val onTabSelectedList = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            println(">>>> handle tab ${tab.position}")
            val index = tab.position
            if (currentTabIndex != index) {
                currentTabIndex = index
            }
            handleTabIndex()
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }
    }
}