package org.evgem.android.drachukeugenesapp.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import org.evgem.android.drachukeugenesapp.R

class AddSiteDialog : DialogFragment() {
    private lateinit var editText: EditText
    private var onSiteAdded: ((url: String) -> Unit)? = null
    private val isValidSite: Boolean get() = Patterns.WEB_URL.matcher(editText.text).matches()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
            .setTitle(R.string.add_site)
            .setView(getDialogView())
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val url = editText.text.toString()
                onSiteAdded?.invoke(url)
            }
            .setNegativeButton(android.R.string.cancel) { _, _ ->
                dismiss()
            }.create().apply {
                setOnShowListener {
                    getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = isValidSite
                }
            }
    }

    private fun getDialogView(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_site, null)
        editText = view.findViewById(R.id.site_edit_text)
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val button = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
                button.isEnabled = isValidSite
            }
        })
        return view
    }

    companion object {
        fun newInstance(onSiteAdded: (url: String) -> Unit): AddSiteDialog {
            val fragment = AddSiteDialog()
            fragment.onSiteAdded = onSiteAdded
            return fragment
        }
    }
}