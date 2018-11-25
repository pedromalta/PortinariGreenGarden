package pedromalta.portinari.home.features.sprinklers

import pedromalta.portinari.home.PortinariHome
import pedromalta.portinari.home.features.base.BasePresenter
import pedromalta.portinari.home.model.SprinklerActions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class SprinklersPresenter : BasePresenter<SprinklersMvpView>() {


    fun sprinklers(action: SprinklerActions, id: Int) {
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

    fun status() {
        checkViewAttached()
        view?.showLoadingStatus()

        PortinariHome.api.status()
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