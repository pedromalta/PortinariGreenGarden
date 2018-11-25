package pedromalta.portinari.home.features.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

import pedromalta.portinari.home.features.base.BaseActivity
import pedromalta.portinari.home.features.sprinklers.SprinklersActivity


class SplashActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissions()
    }

    private fun startScheduler() {
        startActivity(Intent(this, SprinklersActivity::class.java))
        finish()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(baseContext, Manifest.permission.PROCESS_OUTGOING_CALLS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.PROCESS_OUTGOING_CALLS),
                    13423)
        } else {
            startScheduler()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            13423 -> {
                var permissionsOk = true
                for (result in grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        permissionsOk = false
                    }
                }

                if (!permissionsOk) {
                    checkPermissions()
                } else {
                    startScheduler()
                }

                return
            }
        }
    }
}