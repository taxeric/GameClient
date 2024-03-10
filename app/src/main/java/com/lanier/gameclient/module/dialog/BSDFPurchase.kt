package com.lanier.gameclient.module.dialog

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lanier.gameclient.databinding.DialogPurchaseBinding
import com.lanier.gameclient.entity.MarketItem

/**
 * Created by 幻弦让叶
 * Date 2024/3/2 16:18
 */
class BSDFPurchase(
    private val marketItem: MarketItem?
) : BottomSheetDialogFragment() {

    constructor() : this(null)

    companion object {
        fun show(
            fragmentManager: FragmentManager,
            marketItem: MarketItem,
            onPurchase: ((type: Int, propId: Int, marketItemId: Int, number: Int,) -> Unit)? = null
        ) {
            val dialog = BSDFPurchase(marketItem).apply {
                this.onPurchase = onPurchase
            }
            dialog.show(fragmentManager, BSDFPurchase::class.java.simpleName)
        }
    }

    private val viewbinding : DialogPurchaseBinding by lazy {
        DialogPurchaseBinding.inflate(layoutInflater)
    }

    var onPurchase: ((type: Int, propId: Int, marketItemId: Int, number: Int,) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return viewbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view.parent as? View)?.setBackgroundColor(Color.TRANSPARENT)

        if (marketItem != null) {
            viewbinding.item = marketItem
        }
        viewbinding.etNumber.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                val number = s.toString()
                if (number.isNullOrEmpty().not()) {
                    viewbinding.tvTotal.text = "${number.toInt() * (marketItem?.price?:0)}"
                }
            }
        })
        viewbinding.btnPurchase.setOnClickListener {
            val number = try {
                viewbinding.etNumber.text.toString().toInt()
            } catch (e: Throwable) {
                -1
            }
            if (number <= 0) {
                return@setOnClickListener
            }
            marketItem?.let {
                onPurchase?.invoke(
                    it.type,
                    it.realPropId,
                    it.marketItemId,
                    number
                )
            }
        }
    }
}