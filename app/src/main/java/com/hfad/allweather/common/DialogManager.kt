package com.hfad.allweather.common

import android.app.AlertDialog
import android.content.Context

object DialogManager {

    fun locationSettingsDialog(context: Context, listener:Listener) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle("Enable Location")
        dialog.setMessage("Location disabled, do you want enable location?")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
            dialog.dismiss()
            listener.onClick(null)
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { _, _ ->
            dialog.dismiss()

        }
        dialog.show()
    }

    interface Listener{
        fun onClick(name:String?)
    }
}