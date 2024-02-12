package com.lanier.gameclient.module.dialog

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lanier.gameclient.databinding.DialogEditHostBinding

class BSDFEditHost : BottomSheetDialogFragment() {

    companion object {

        fun show(fragmentManager: FragmentManager, defHost: String = "", onHostChanged: ((host: String) -> Unit)? = null) {
            val dialog = BSDFEditHost().apply {
                this.onHostChanged = onHostChanged
            }
            dialog.arguments = Bundle().apply {
                putString("defHost", defHost)
            }
            dialog.show(fragmentManager, this::class.java.simpleName)
        }
    }

    private val viewbinding : DialogEditHostBinding by lazy {
        DialogEditHostBinding.inflate(layoutInflater)
    }

    private var defHost: String = ""

    var onHostChanged : ((host: String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        defHost = requireArguments().getString("defHost")?: ""
    }

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

        viewbinding.etHost.text = SpannableStringBuilder(defHost)

        viewbinding.btnSave.setOnClickListener {
            saveAndDismiss()
        }
    }

    private fun saveAndDismiss() {
        val host = viewbinding.etHost.text.toString()
        if (host.isNotEmpty()) {
            onHostChanged?.invoke(host)
        }
        dismiss()
    }
}