package pedromalta.portinari.home.features.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import pedromalta.portinari.home.R
import pedromalta.portinari.home.features.sprinklers.SprinklersActivity


object Notification {

    const val NOTIFICATION_ID = 2435
    private const val NOTIFICATION_CHANNEL = "location_channel"

    private var notification: Notification? = null

    fun getNotification(context: Context): Notification {
        notification?.apply {
            return this
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(NOTIFICATION_CHANNEL, "Agendamento Irrigação", NotificationManager.IMPORTANCE_LOW)
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val notificationIntent = Intent(context, SprinklersActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK;
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)

        notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
                .setContentTitle("Portinari Sprinklers")
                .setContentText("A horta está sendo irrigada.")
                .setSmallIcon(R.mipmap.icon_main)
                .setContentIntent(pendingIntent)
                .setOngoing(true).build()

        return notification!!

    }
}
