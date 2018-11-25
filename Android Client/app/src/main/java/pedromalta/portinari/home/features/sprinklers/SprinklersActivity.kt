package pedromalta.portinari.home.features.sprinklers

import android.os.Bundle
import pedromalta.portinari.home.R
import pedromalta.portinari.home.features.base.BaseActivity
import pedromalta.portinari.home.model.SprinklerActions
import pedromalta.portinari.home.model.responses.SprinklerStatus


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
        presenter.status()
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
