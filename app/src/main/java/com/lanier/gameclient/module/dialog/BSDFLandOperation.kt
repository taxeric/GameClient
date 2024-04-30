package com.lanier.gameclient.module.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lanier.gameclient.R
import com.lanier.gameclient.databinding.DialogLandOperationBinding
import com.lanier.gameclient.entity.Backpack
import com.lanier.gameclient.ext.visible
import com.lanier.gameclient.module.backpack.BackpackAdapter
import com.lanier.gameclient.module.plant.PlantVM

/**
 * Created by 幻弦让叶
 * Date 2024/4/4 18:29
 */
class BSDFLandOperation : BottomSheetDialogFragment() {

    companion object {

        fun show(harvestModeEnable: Boolean, fragmentManager: FragmentManager) : BSDFLandOperation {
            val args = Bundle().apply {
                putBoolean("harvestModeEnable", harvestModeEnable)
            }

            val fragment = BSDFLandOperation()
            fragment.arguments = args
            fragment.show(fragmentManager, BSDFLandOperation::class.java.simpleName)
            return fragment
        }
    }

    private val binding by lazy {
        DialogLandOperationBinding.inflate(layoutInflater)
    }

    private val vm by activityViewModels<PlantVM>()

    private var canHarvest = false

    var operationListener: LandOperationListener? = null

    private val mAdapter by lazy {
        BackpackAdapter().apply {
            setOnItemClickListener { adapter, _, position ->
                val item = adapter.getItem(position) as? Backpack
                if (binding.rbSeed.isChecked) {
                    operationListener?.plant(item)
                } else if (binding.rbFertilizer.isChecked) {
                    operationListener?.useFertilizer(item)
                }
                dismiss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        canHarvest = arguments?.getBoolean("harvestModeEnable")?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (canHarvest) {
            binding.btnHarvest.visible()
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbSeed -> vm.seeds()
                R.id.rbFertilizer -> vm.fertilizer()
            }
        }

        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        binding.btnHarvest.setOnClickListener {
            operationListener?.harvest()
            dismiss()
        }

        vm.backpackList.observe(viewLifecycleOwner) {
            if (vm.refresh) {
                mAdapter.setList(it.second)
            } else {
                mAdapter.addData(it.second)
            }

            if (it.second.isEmpty()) {
                mAdapter.loadMoreModule.loadMoreEnd()
            } else {
                mAdapter.loadMoreModule.loadMoreComplete()
            }
        }

        vm.seeds()
    }
}

interface LandOperationListener {

    fun harvest()
    fun plant(item: Backpack?)
    fun useFertilizer(item: Backpack?)
}
