package br.com.drivin.features.sprinklers

import br.com.drivin.PortinariHome
import br.com.drivin.features.base.BasePresenter
import br.com.drivin.model.SprinklerActions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class SprinklersPresenter : BasePresenter<SprinklersMvpView>() {


    fun sprinklers(action: SprinklerActions, id: Int? = null) {
        checkViewAttached()
        view?.showLoadingStatus()

        PortinariHome.api.sprinklers(action.toString(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ status ->
                    view?.hideLoading()
                        view?.updateStatus(status)

                }, {
                    view?.hideLoading()
                    view?.showError(it.message)
                })
                .addTo(disposable)

    }


}