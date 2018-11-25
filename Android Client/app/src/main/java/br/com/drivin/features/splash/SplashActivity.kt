package br.com.drivin.features.splash

import android.content.Intent
import android.os.Bundle

import br.com.drivin.features.base.BaseActivity
import br.com.drivin.features.sprinklers.SprinklersActivity


class SplashActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, SprinklersActivity::class.java))
    }
}