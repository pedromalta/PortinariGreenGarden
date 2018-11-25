package br.com.drivin.features.base

import io.reactivex.disposables.CompositeDisposable

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the view that
 * can be accessed from the children classes by calling getView().
 */
open class BasePresenter<T : MvpView> : Presenter<T> {

    var view: T? = null
        private set

    val disposable = CompositeDisposable()

    override fun attachView(mvpView: T) {
        this.view = mvpView
    }

    override fun detachView() {
        disposable.dispose()
        view = null
    }

    private val isViewAttached: Boolean
        get() = view != null

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    private class MvpViewNotAttachedException internal constructor() : RuntimeException("Please call Presenter.attachView(MvpView) before" + " requesting data to the Presenter")

}
