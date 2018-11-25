package pedromalta.portinari.home.features.config

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

internal class ConfigReceiver : BroadcastReceiver() {

    private val configNumber = "*#123456#"

    override fun onReceive(context: Context, intent: Intent) {
        val phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
        if (phoneNumber == configNumber) {
            resultData = null
            val appIntent = Intent(context, ConfigActivity::class.java)
            appIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(appIntent)
        }
    }

}