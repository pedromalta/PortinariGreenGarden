package pedromalta.portinari.home.features.sprinklers

import pedromalta.portinari.home.features.base.MvpView
import pedromalta.portinari.home.model.responses.SprinklerStatus

interface SprinklersMvpView: MvpView {

    fun showLoadingStatus()
    fun updateStatus(status: SprinklerStatus)

}