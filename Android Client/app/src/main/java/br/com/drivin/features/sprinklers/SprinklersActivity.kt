package br.com.drivin.features.sprinklers

import android.os.Bundle
import br.com.drivin.R
import br.com.drivin.features.base.BaseActivity
import br.com.drivin.model.SprinklerActions
import br.com.drivin.model.responses.SprinklerStatus


class SprinklersActivity : BaseActivity(), SprinklersMvpView {


    private val presenter = SprinklersPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sprinklers)
        presenter.attachView(this)

    }

    override fun onResume() {
        super.onResume()
        getStatus()
    }

    private fun getStatus(){
        presenter.sprinklers(SprinklerActions.STATUS)
    }

    override fun updateStatus(status: SprinklerStatus) {

    }


    override fun showLoadingStatus() {
        showLoading(R.string.dialog_loading_sprinkler_status)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}
