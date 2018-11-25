package pedromalta.portinari.home.util

import org.greenrobot.eventbus.EventBus

data class InternalMessages(
        val action: String,
        var message: String? = null
) {
    init {
        EventBus.getDefault().post(this)
    }

    object Actions {
        const val APP_FOREGROUND = "app_foreground"
        const val APP_BACKGROUND = "app_background"
        const val UPDATE_LAYOUT_STATE = "update_layout_state"
    }
}

