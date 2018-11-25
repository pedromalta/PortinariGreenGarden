package pedromalta.portinari.home.features.scheduler

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import pedromalta.portinari.home.features.notification.Notification
import javax.annotation.Nullable

class SchedulerService : Service() {

    companion object {
        private const val START_ACTION = "pedromalta.portinari.home.schedulerservice.action.start"
        private const val STOP_ACTION = "pedromalta.portinari.home.schedulerservice.action.stop"

        fun start(context: Context) {
            val startIntent = Intent(context, this::class.java)
            startIntent.action = START_ACTION
            context.startService(startIntent)
        }

        fun stop(context: Context) {
            val startIntent = Intent(context, this::class.java)
            startIntent.action = STOP_ACTION
            context.stopService(startIntent)
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.apply {
            when (this.action) {
                START_ACTION -> startService()
                STOP_ACTION -> stopService()
            }
        }

        return START_STICKY
    }

    private fun startService(){

        val notification = Notification.getNotification(this)

        startForeground(Notification.NOTIFICATION_ID, notification)
    }

    private fun stopService(){
        stopSelf()
    }

    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return null
    }


}