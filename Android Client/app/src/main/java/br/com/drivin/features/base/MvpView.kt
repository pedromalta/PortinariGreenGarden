package br.com.drivin.features.base

import android.support.annotation.StringRes

/**
 * Base interface that any class that wants to act as a View in the MVP (Model View Presenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment.
 */
interface MvpView {
    fun showLoading(@StringRes message: Int)
    fun showError(message: String?)
    fun hideLoading()
}
