package pedromalta.portinari.home.features.splash

import android.content.Intent
import android.os.Bundle

import pedromalta.portinari.home.features.base.BaseActivity
import pedromalta.portinari.home.features.sprinklers.SprinklersActivity


class SplashActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, SprinklersActivity::class.java))
    }
}