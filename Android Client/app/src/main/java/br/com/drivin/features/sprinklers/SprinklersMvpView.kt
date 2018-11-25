package br.com.drivin.features.sprinklers

import br.com.drivin.features.base.MvpView
import br.com.drivin.model.responses.SprinklerStatus

interface SprinklersMvpView: MvpView {

    fun showLoadingStatus()
    fun updateStatus(status: SprinklerStatus)

}