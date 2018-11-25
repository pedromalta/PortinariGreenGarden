package pedromalta.portinari.home.features.sprinklers

import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.ImageViewCompat
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.queryFirst
import com.vicpin.krealmextensions.save
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pedromalta.portinari.home.PortinariHome
import pedromalta.portinari.home.R
import pedromalta.portinari.home.features.base.BaseActivity
import pedromalta.portinari.home.features.scheduler.SchedulerService
import pedromalta.portinari.home.persistence.Schedule
import pedromalta.portinari.home.util.InternalMessages
import java.util.concurrent.TimeUnit


class SprinklersActivity : BaseActivity() {

    private lateinit var btnIniciar: Button
    private lateinit var btnParar: Button
    private lateinit var iconSprinkler1: ImageView
    private lateinit var iconSprinkler2: ImageView
    private lateinit var timeSprinkler1: TextView
    private lateinit var timeSprinkler2: TextView

    private lateinit var timeSchedule: TextView
    private lateinit var timeSelectTime: SeekBar

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sprinklers)

        btnIniciar = findViewById(R.id.btn_iniciar)
        btnParar = findViewById(R.id.btn_parar)
        iconSprinkler1 = findViewById(R.id.sprinkler1_status)
        iconSprinkler2 = findViewById(R.id.sprinkler2_status)
        timeSprinkler1 = findViewById(R.id.sprinkler1_label_timer)
        timeSprinkler2 = findViewById(R.id.sprinkler2_label_timer)
        timeSchedule = findViewById(R.id.scheduler_time)
        timeSelectTime = findViewById(R.id.scheduler_seekbar)

        setupTimeSelect()
        setupBtnIniciar()
        setupBtnParar()
    }


    private fun setupBtnIniciar(){
        btnIniciar.setOnClickListener {
            showLoading(R.string.dialog_loading_sprinkler_status)
            val schedule = Schedule()
            schedule.time = timeSelectTime.progress * 60
            schedule.save()
            SchedulerService.start(this)
            updateCountDowns()
        }
    }

    private fun setupBtnParar(){
        btnParar.setOnClickListener {
            SchedulerService.stop(this)
            stopCountDowns()
        }
    }

    private fun updateCountDowns(){
        Single.create<Schedule> {
            val schedule = Schedule().queryFirst()
            if (schedule == null || schedule.isDone) {
                stopCountDowns()
            } else {
                it.onSuccess(schedule)
            }
        }.delaySubscription(1, TimeUnit.SECONDS).repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { schedule ->
                    timeSprinkler1.text = fromIntToTimeString(schedule.timeRemainingSprinkler1)
                    timeSprinkler2.text = fromIntToTimeString(schedule.timeRemainingSprinkler2)
                }.addTo(disposable)
    }

    private fun stopCountDowns(){
        disposable.dispose()
    }

    private fun setupTimeSelect(){
        timeSelectTime.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                timeSchedule.text = fromIntToTimeString(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    private fun fromIntToTimeString(time: Int): String{
        val seconds = time % 60
        val minutes = time / 60
        val formatSeconds = if (seconds > 9) "$seconds" else "0$seconds"
        val formatMinutes = if (minutes > 9) "$minutes" else "0$minutes"
        return "$formatMinutes:$formatSeconds"
    }

    private fun updateLayoutState() {
        hideLoading()
        val schedule = Schedule().queryFirst()
        if (schedule == null) {
            resetLayout()
        } else {
           runningLayout(schedule)
        }
    }

    private fun runningLayout(schedule: Schedule){
        btnIniciar.visibility = View.GONE
        btnParar.visibility = View.VISIBLE
        toggleIconSprinkler1(schedule.sprinkler1Status)
        toggleIconSprinkler2(schedule.sprinkler2Status)
        timeSprinkler1.text = fromIntToTimeString(schedule.timeRemainingSprinkler1)
        timeSprinkler2.text = fromIntToTimeString(schedule.timeRemainingSprinkler2)
        timeSelectTime.isEnabled = false
    }

    private fun resetLayout() {
        btnIniciar.visibility = View.VISIBLE
        btnParar.visibility = View.GONE
        toggleIconSprinkler1(false)
        toggleIconSprinkler2(false)
        timeSprinkler1.text = getString(R.string.timer)
        timeSprinkler2.text = getString(R.string.timer)
        timeSelectTime.progress = 0
        timeSelectTime.isEnabled = true
    }

    private fun toggleIconSprinkler1(toggle: Boolean){
        if (toggle) {
            iconSprinkler1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.greenDark))
        } else {
            iconSprinkler1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey))
        }
    }

    private fun toggleIconSprinkler2(toggle: Boolean){
        if (toggle) {
            iconSprinkler2.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.greenDark))
        } else {
            iconSprinkler2.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey))
        }
    }

    override fun onResume() {
        super.onResume()
        updateLayoutState()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onInternalMessages(internalMessages: InternalMessages) {
        when (internalMessages.action) {
            InternalMessages.Actions.UPDATE_LAYOUT_STATE -> updateLayoutState()
        }
    }

}
