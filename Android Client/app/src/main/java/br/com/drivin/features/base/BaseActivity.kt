package br.com.drivin.features.base

import android.app.ProgressDialog
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.util.Log
import br.com.drivin.util.InternalMessages
import br.com.drivin.util.Ui
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseActivity: AppCompatActivity() {

    var loadingDialog: ProgressDialog? = null

    companion object {
        var lastingLoading: ProgressDialog? = null
    }


    fun showMessage(message: String?) {
        if (message != null){
            Ui.Messages.message(this, message)
        }
    }

    fun showError(message: String?) {
        if (message != null){
            Ui.Messages.message(this, message)
        }
    }

    fun hideLoading() {
        loadingDialog?.cancel()
        loadingDialog?.dismiss()
    }

    fun showLoading(@StringRes message: Int){
        loadingDialog?.cancel()
        loadingDialog?.dismiss()
        loadingDialog = Ui.Dialogs.progress(this, message, true, false)
    }

    fun showLoadingDismissible(@StringRes message: Int){
        loadingDialog?.cancel()
        loadingDialog?.dismiss()
        loadingDialog = Ui.Dialogs.progress(this, message, true, true)
    }


    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
        InternalMessages(InternalMessages.Actions.APP_FOREGROUND)
    }

    override fun onStop() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
        InternalMessages(InternalMessages.Actions.APP_BACKGROUND)
        super.onStop()

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessage(message: String){
       Log.i("ACTION", message)
    }


}