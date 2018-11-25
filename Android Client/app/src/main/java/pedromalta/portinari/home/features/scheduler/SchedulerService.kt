package pedromalta.portinari.home.features.scheduler

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.queryFirst
import com.vicpin.krealmextensions.save
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import pedromalta.portinari.home.PortinariHome
import pedromalta.portinari.home.features.notification.Notification
import pedromalta.portinari.home.model.SprinklerActions
import pedromalta.portinari.home.persistence.Schedule
import pedromalta.portinari.home.util.InternalMessages
import java.util.concurrent.TimeUnit
import javax.annotation.Nullable

class SchedulerService : Service() {

    companion object {
        private const val START_ACTION = "pedromalta.portinari.home.schedulerservice.action.start"
        private const val STOP_ACTION = "pedromalta.portinari.home.schedulerservice.action.stop"

        fun start(context: Context) {
            val startIntent = Intent(context, SchedulerService::class.java)
            startIntent.action = START_ACTION
            context.startService(startIntent)
        }

        fun stop(context: Context) {
            val startIntent = Intent(context, SchedulerService::class.java)
            startIntent.action = STOP_ACTION
            context.startService(startIntent)
        }
    }

    private val disposable = CompositeDisposable()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.apply {
            when (this.action) {
                START_ACTION -> startService()
                STOP_ACTION -> stopService()
            }
        }

        return START_STICKY
    }

    private fun startService() {

        Single.create<Schedule> {
            val schedule = Schedule().queryFirst()
            if (schedule == null || schedule.isDone) {
                stopService()
            } else {
                it.onSuccess(schedule)
            }
        }.delaySubscription(1, TimeUnit.SECONDS).repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { schedule ->
                    processSprinkler1(schedule)
                    processSprinkler2(schedule)

                }.addTo(disposable)


        val notification = Notification.getNotification(this)
        startForeground(Notification.NOTIFICATION_ID, notification)
    }

    private fun processSprinkler1(schedule: Schedule) {
        if (!schedule.sprinkler1Done) {
            if (schedule.timeRemainingSprinkler1 == 0) {
                sprinklers(SprinklerActions.STOP, 1)
                schedule.sprinkler1Done = true
                schedule.save()
                InternalMessages(InternalMessages.Actions.UPDATE_LAYOUT_STATE)
            } else {
                if (!schedule.sprinkler1Status && schedule.timeRemainingSprinkler1 > 0L) {
                    sprinklers(SprinklerActions.START, 1)
                }
            }
        }
    }

    private fun processSprinkler2(schedule: Schedule) {
        if (schedule.sprinkler1Done) {
            if (!schedule.sprinkler2Done) {
                if (schedule.timeRemainingSprinkler2 == 0) {
                    sprinklers(SprinklerActions.STOP, 2)
                    schedule.sprinkler2Done = true
                    schedule.save()
                    InternalMessages(InternalMessages.Actions.UPDATE_LAYOUT_STATE)
                } else {
                    if (!schedule.sprinkler2Status && schedule.timeRemainingSprinkler2 > 0L) {
                        sprinklers(SprinklerActions.START, 2)
                    }
                }
            }
        }
    }

    private fun sprinklers(action: SprinklerActions, id: Int) {

        PortinariHome.api.sprinklers(action.toString(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ status ->
                    val schedule = Schedule().queryFirst() ?: return@subscribe
                    schedule.sprinkler1Status = status.sprinkler1
                    schedule.sprinkler2Status = status.sprinkler2
                    schedule.save()
                    if (schedule.isDone) {
                        schedule.deleteAll()
                    }
                    InternalMessages(InternalMessages.Actions.UPDATE_LAYOUT_STATE)

                }, {
                    val schedule = Schedule().queryFirst() ?: return@subscribe
                    schedule.errorMessage = it.message
                    schedule.save()
                    InternalMessages(InternalMessages.Actions.UPDATE_LAYOUT_STATE)
                })
                .addTo(disposable)

    }

    private fun stopService() {
        Schedule().deleteAll()
        sprinklers(SprinklerActions.STOP, 1)
        sprinklers(SprinklerActions.STOP, 2)
        InternalMessages(InternalMessages.Actions.UPDATE_LAYOUT_STATE)
        stopSelf()
    }

    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return null
    }


}