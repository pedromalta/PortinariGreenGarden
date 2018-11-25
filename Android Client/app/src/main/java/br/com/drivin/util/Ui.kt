package br.com.drivin.util

import android.app.ProgressDialog
import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


object Ui {

    object Dialogs {

        fun progress(context: Context, message: String, show: Boolean = true, cancelable: Boolean = false): ProgressDialog {
            val progressBar = ProgressDialog(context)
            progressBar.setMessage(message)
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progressBar.setCancelable(cancelable)
            if (show) {
                progressBar.show()
            }
            return progressBar
        }

        fun progress(context: Context, @StringRes message: Int, show: Boolean = true, cancelable: Boolean = false): ProgressDialog {
            return progress(context, context.getString(message), show, cancelable)
        }

    }

    object Messages {
        private fun proccessBreakLines(message: String?): String? {
            if (message != null) {
                return message.replace("<br>", "\n")
            }
            return null
        }

        fun message(context: Context, message: String?, show: Boolean = true): Toast {
            val toast = Toast.makeText(context, proccessBreakLines(message), Toast.LENGTH_LONG)
            if (show) {
                toast.show()
            }
            return toast
        }

        fun message(context: Context, @StringRes message: Int, show: Boolean = true): Toast {
            return message(context, context.getString(message), show)
        }
    }


    object Date {
        val formatDefault = SimpleDateFormat("hh:mm", Locale("pt", "BR"))
        val formatWithSeconds = SimpleDateFormat("hh:mm", Locale("pt", "BR"))
        val formatWithYear = SimpleDateFormat("dd/MM/YYYY - hh:mm", Locale("pt", "BR"))
    }

}